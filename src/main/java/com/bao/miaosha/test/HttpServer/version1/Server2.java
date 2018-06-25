package com.bao.miaosha.test.HttpServer.version1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {
    private ServerSocket server;

    /**
     * @param args
     */
    public static void main(String[] args) {
        Server2 server2 = new Server2();
        server2.start();
    }
    /**
     * 启动方法
     */
    public void start(){
        try {
            server = new ServerSocket(8888);
            this.receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 接收客户端
     */
    private void receive(){
        try {
            //接收客户端的请求信息
            Socket client =  server.accept();
            Request request = new Request(client);
            String username = request.getParam("username");
            //响应
            Response response = new Response(client);
            response.buildContent("<html><head><title>HTTP响应示例</title></head><body>Hello server!</body></html>");
            response.buildContent(username.toString());
            response.buildHead(200);
            response.pushToClient();
        } catch (IOException e) {
        }
    }

    /**
     * 听着服务器
     */
    public void stop(){

    }
}
