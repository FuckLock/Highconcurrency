package com.bao.miaosha.test.HttpServer.version1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server4 {

    private ServerSocket server;
    private boolean isShutDown;

    /**
     * @param args
     */
    public static void main(String[] args) {
        Server4 server4 = new Server4();
        server4.start();
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
                new Thread(new Dispatcher(client)).start();


        } catch (IOException e) {
        }
    }

    /**
     * 听着服务器
     */
    public void stop(){

    }

}
