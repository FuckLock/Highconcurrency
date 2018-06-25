package com.bao.miaosha.validator;


import com.bao.miaosha.domain.MiaoshaUser;
import com.bao.miaosha.interceptor.UserContext;
import com.bao.miaosha.redis.AccessLimitKey;
import com.bao.miaosha.redis.RedisService;
import com.bao.miaosha.result.CodeMsg;
import com.bao.miaosha.result.Result;
import com.bao.miaosha.test.HttpServer.version2.CloseUtil;
import com.bao.miaosha.util.ObjectMapperUtil;
import com.sun.tools.javac.jvm.Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class SimpleAccessLimit {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisService redisService;

    public boolean requestLimit(HttpServletRequest request, HandlerMethod method) {
        logger.info("========================================access拦截器");
        AccessLimit accessLimit = method.getMethodAnnotation(AccessLimit.class);
        if (accessLimit == null){
            return true;
        }

        int seconds = accessLimit.seconds();
        int maxCount = accessLimit.maxCount();
        String uri = request.getRequestURI();
        StringBuilder sb = new StringBuilder();
        MiaoshaUser user = UserContext.get();
        String key = sb.append(user.getId()).append("_").append(uri).toString();
        Integer count = redisService.get(AccessLimitKey.accessLimitKey, key, Integer.class);
        if (count == null){
            redisService.setEx(AccessLimitKey.accessLimitKey, key, 1, seconds);
        } else if (count < maxCount){
            redisService.incr(AccessLimitKey.accessLimitKey, key);
        } else {
            redisService.delete(AccessLimitKey.accessLimitKey, key);
            return false;
        }

        return true;
    }
}
