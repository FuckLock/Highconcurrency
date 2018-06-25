package com.bao.miaosha.test.HttpServer.version2;

public class LoginServlet extends Servlet {

    @Override
    public void doGet(Request request, Response response) throws Exception {
        String username = request.getParam("username");
        String password = request.getParam("password");
        if(login(username, password)){
            response.buildContent("登录成功");
        }else{
            response.buildContent("登录失败");
        }
    }


    public boolean login(String name, String pwd){
        return name.equals("bao") && pwd.equals("12346");
    }

    @Override
    public void doPost(Request request, Response response) throws Exception {

    }
}
