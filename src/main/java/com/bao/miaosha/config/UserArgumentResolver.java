package com.bao.miaosha.config;

import com.bao.miaosha.domain.MiaoshaUser;
import com.bao.miaosha.service.MiaoshaUserService;
import com.bao.miaosha.util.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

//@Service
public class UserArgumentResolver /** implements HandlerMethodArgumentResolver**/ {

//    @Autowired
//    MiaoshaUserService userService;
//
//    @Override
//    public boolean supportsParameter(MethodParameter methodParameter) {
//        Class<?> clazz = methodParameter.getParameterType();
//        return clazz == MiaoshaUser.class;
//    }
//
//    @Override
//    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
//        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
//        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
//
//        String paramToken = request.getParameter(CookieUtil.COOKIE_NAME);
//        String cookieToken = getCookieValue(request, CookieUtil.COOKIE_NAME);
//        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
//            return null;
//        }
//        String token = StringUtils.isEmpty(paramToken) ? cookieToken:paramToken;
//        return userService.getUserByToken(response, token);
//    }
//
//    private  String getCookieValue(HttpServletRequest request, String cookiName) {
//        Cookie[]  cookies = request.getCookies();
//        if (cookies == null || cookies.length <= 0){
//            return null;
//        }
//
//        for(Cookie cookie : cookies) {
//            if(cookie.getName().equals(cookiName)) {
//                return cookie.getValue();
//            }
//        }
//
//        return null;
//    }
}
