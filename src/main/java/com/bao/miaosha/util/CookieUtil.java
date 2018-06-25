package com.bao.miaosha.util;



import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public final static String COOKIE_NAME = "dong_login_token";
    private final static String PATH = "/";
    private final static int MAX_AGE = 60 * 60 * 24 * 365;


    public static void addCookie(HttpServletResponse response, String token){
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setMaxAge(MAX_AGE);
        cookie.setPath(PATH);
        response.addCookie(cookie);
    }
}
