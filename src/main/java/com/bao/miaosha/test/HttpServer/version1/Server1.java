package com.bao.miaosha.test.HttpServer.version1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server1 {

    private ServerSocket server;

    /**
     * @param args
     */
    public static void main(String[] args) {
        Server1 server = new Server1();
        server.start();
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
            Socket client = server.accept();
            byte[] data = new byte[20480];
            int len = client.getInputStream().read(data);
            String requestInfo = new String(data,0,len).trim();
            System.out.println(requestInfo);

            //响应
            Response response = new Response(client);
            response.buildContent("<html><head><title>HTTP响应示例</title></head><body>Hello server!</body></html>");
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
