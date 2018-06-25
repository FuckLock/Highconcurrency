package com.bao.miaosha.test.HttpServer.version1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server3 {

    private ServerSocket server;

    /**
     * @param args
     */
    public static void main(String[] args) {
        Server3 server3 = new Server3();
        server3.start();
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
            Response response = new Response(client);
            Servlet servlet = new Servlet();
            servlet.service(request, response);
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
