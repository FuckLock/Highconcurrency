package com.bao.miaosha.test.HttpServer.version2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class Response {

    //两个常量
    public static final String CRLF="\r\n";
    public static final String BLANK=" ";

    //存储头信息
    private StringBuilder headInfo;

    //正文
    private StringBuilder content;

    //存储正文长度
    private int len;

    //流
    private BufferedWriter bw ;

    private Response(){
        headInfo = new StringBuilder();
        content = new StringBuilder();
    }

    public Response(Socket client){
        this();
        try {
            bw= new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建响应头
     * @param code
     */
    public void buildHead(int code){
        headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
        switch (code) {
            case 200:
                headInfo.append("OK");
                break;
            case 404:
                headInfo.append("NOT FOUND");
                break;
            case 505:
                headInfo.append("SEVER ERROR");
                break;
            default:
                break;
        }

        headInfo.append(CRLF);

        //2)  响应头(Response Head)
        headInfo.append("Server:dong Server/0.0.1").append(CRLF);
        headInfo.append("Date:").append(new Date()).append(CRLF);
        headInfo.append("Content-type:text/html;charset=GBK").append(CRLF);
        //正文长度 ：字节长度
        headInfo.append("Content-Length:").append(len).append(CRLF);
        headInfo.append(CRLF); //分隔符
    }

    /**
     * 构建正文
     */
    public Response buildContent(String info){
        content.append(info);
        len += info.getBytes().length;
        return this;
    }

    //推送到客户端
    public void pushToClient() throws IOException{
        bw.write(headInfo.append(content).toString());
        bw.flush();
        bw.close();
    }

    public void close(){
        CloseUtil.closeIO(bw);
    }

}
