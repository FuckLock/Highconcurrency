package com.bao.miaosha.test.HttpServer.version2;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class WebApp {

    private static ServletContext contxt;

    static {
        contxt = new ServletContext();
        Map<String, String> mapping = contxt.getMapping();
        mapping.put("/login", "login");
        mapping.put("/register", "register");

        Map<String, Servlet> servlet = contxt.getServlet();
        servlet.put("login", new LoginServlet());
        servlet.put("register", new RegisterServlet());
    }

    public static Servlet getServlet(String url){
        if((StringUtils.isBlank(url))){
            return null;
        }

        return contxt.getServlet().get(contxt.getMapping().get(url));
    }

}
