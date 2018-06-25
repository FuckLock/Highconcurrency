package com.bao.miaosha.test.HttpServer.version2;

public class RegisterServlet extends Servlet {

    @Override
    public void doGet(Request req,Response rep) throws Exception {

    }

    @Override
    public void doPost(Request request, Response response) throws Exception {
        response.buildHead(200);
        response.buildContent("<html><head><title>返回注册</title>");
        response.buildContent("</head><body>");
        response.buildContent("你的用户名为:" + request.getParam("username"));
        response.buildContent("</body></html>");
    }
}
