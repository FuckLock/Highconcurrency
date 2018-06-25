package com.bao.miaosha.test.HttpServer.version2;

public abstract class Servlet {

    public void service(Request request, Response response) throws Exception{
        this.doGet(request, response);
        this.doPost(request, response);
    }

    public abstract void doGet(Request req,Response rep) throws Exception;
    public abstract void doPost(Request req,Response rep) throws Exception;
}
