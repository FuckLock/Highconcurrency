package com.bao.miaosha.interceptor;

import com.bao.miaosha.domain.MiaoshaUser;
import com.bao.miaosha.redis.MiaoshaUserKey;
import com.bao.miaosha.redis.RedisService;
import com.bao.miaosha.result.CodeMsg;
import com.bao.miaosha.result.Result;
import com.bao.miaosha.util.CookieUtil;
import com.bao.miaosha.util.ObjectMapperUtil;
import com.bao.miaosha.validator.SimpleAccessLimit;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisService redisService;

    @Autowired
    private SimpleAccessLimit accessLimit;

    /**
     * 考虑到登录验证重复验证，可以定义个登录拦截器进行验证（起本质在服务器对请求解析后判断有这个方法先调用这个方法,然后映射后续方法）
     *
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (handler instanceof HandlerMethod){
            // 打印下拦截到日志，以便于查问题
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            printLog(request, response, handlerMethod);
            // 判断token 如果没有token直接返回
            String paramsToken = request.getParameter(CookieUtil.COOKIE_NAME);
            String cookieToken = getCookieToken(request);
            String token = StringUtils.isNotEmpty(paramsToken) ? paramsToken : cookieToken;
            if (StringUtils.isEmpty(token)){
                Result result = Result.error(CodeMsg.USER_NOT_LOGIN);
                writeResultToClient(response, result);
                return false;
            }

            // 存在token,继续判断登录,为空直接返回，不为空说明登录了
            MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
            if (user == null){
                Result result = Result.error(CodeMsg.USER_NOT_LOGIN);
                writeResultToClient(response, result);
                return false;
            }

            // 用户已经登录把用户设置到ThreadLocal中
            UserContext.set(user);

            // 限制用户访问到请求次数
            boolean check = accessLimit.requestLimit(request, handlerMethod);
            if (!check){
                logger.info("======================", "23432423432423");
                Result result = Result.error(CodeMsg.REQUEST_FREQUENT);
                writeResultToClient(response, result);
                return false;
            }
        }
        return true;
    }

    public void printLog(HttpServletRequest request,  HttpServletResponse response, HandlerMethod handler){
        String className = handler.getBean().getClass().getSimpleName();
        String methodName = handler.getMethod().getName();
        StringBuilder sb = new StringBuilder();
        Map<String, String[]> paramsMap = request.getParameterMap();
        logger.info("====================", paramsMap);
        String paramsString = null;
        if (!(CollectionUtils.isEmpty(paramsMap))) {
            Iterator<Map.Entry<String, String[]>> iterator = paramsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String[]> entry = iterator.next();
                String key = entry.getKey();
                String[] values = entry.getValue();
                sb.append(key).append("=").append(Arrays.toString(values)).append("&");
            }
            String str = sb.toString();
            paramsString = str.substring(0, str.lastIndexOf("&"));
        }
        logger.info("拦截器拦截到到请求, className: {}, method: {}, params: {}", className, methodName, paramsString);
    }

    public String getCookieToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return null;
        }
        for (Cookie cookie:cookies) {
            if (cookie.getName().equals(CookieUtil.COOKIE_NAME)){
                return cookie.getValue();
            }
        }

        return null;
    }

    private void writeResultToClient(HttpServletResponse response, Result result) {
        String resultString = ObjectMapperUtil.beanToString(result);
        ServletOutputStream out = null;
        try {
            response.setContentType("application/json");
            out = response.getOutputStream();
            out.write(resultString.getBytes());
        } catch (IOException e) {
            logger.error("登录拦截器中写数据到客户端异常，异常为：", e);
        }finally {
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("ServletOutputStream close error", e);
                }
            }
        }

    }
}
