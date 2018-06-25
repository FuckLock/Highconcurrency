package com.bao.miaosha.test.HttpServer.version2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket server;
    private boolean isShutDown= false;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
    /**
     * 启动方法
     */
    public void start(){
        start(8888);
    }
    /**
     * 指定端口的启动方法
     */
    public void start(int port){
        try {
            server = new ServerSocket(port);
            receive();
        } catch (IOException e) {
            stop();
        }
    }
    /**
     * 接收客户端
     */
    private void receive(){
        try {
            while(!isShutDown){
                new Thread(new Dispatcher(server.accept())).start();
            }
        } catch (IOException e) {
            stop();
        }

    }
    /**
     * 停止服务器
     */
    public void stop(){
        isShutDown=true;
        CloseUtil.closeSocket(server);
    }


}
