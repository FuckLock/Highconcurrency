package com.bao.miaosha.test.HttpServer.version1;

public class Servlet {

    public void service(Request request, Response response){
        String username = request.getParam("username");
        response.buildContent("<html><head><title>HTTP响应示例</title></head><body>Hello baba!");
        response.buildContent("huanying: =====");
        response.buildContent(username);
        response.buildContent("</body></html>");
        response.buildHead(200);
    }

}
