package com.bao.miaosha.test.HttpServer.version2;

import java.io.IOException;
import java.net.Socket;

public class Dispatcher implements Runnable{

    private Socket client;
    private Request request;
    private Response response;
    private int code = 200;

    public Dispatcher(Socket client){
        this.client = client;
        request = new Request(client);
        response = new Response(client);
    }

    @Override
    public void run() {
        try {
            Servlet servlet = WebApp.getServlet(request.getUrl());
            if(servlet == null){
                this.code = 404; //找不到处理
            }else{
                servlet.service(request, response);
            }
            response.buildHead(code);
            response.pushToClient();
        }catch (Exception e) {
            this.code = 500;
            response.buildHead(code);
            try {
                response.pushToClient();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        CloseUtil.closeSocket(client);
    }
}
