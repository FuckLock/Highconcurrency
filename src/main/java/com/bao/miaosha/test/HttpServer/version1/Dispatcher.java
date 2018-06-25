package com.bao.miaosha.test.HttpServer.version1;

import java.io.IOException;
import java.net.Socket;

public class Dispatcher implements Runnable{

    private Socket client;
    private Request request;
    private Response response;

    public Dispatcher(Socket client){
        this.client = client;
        request = new Request(client);
        response = new Response(client);
    }

    @Override
    public void run() {
        Servlet servlet = new Servlet();
        servlet.service(request, response);
        try {
            response.pushToClient();
        } catch (IOException e) {

        }
        CloseUtil.closeSocket(client);
    }
}
