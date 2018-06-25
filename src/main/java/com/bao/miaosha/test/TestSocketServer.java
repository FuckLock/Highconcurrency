package com.bao.miaosha.test;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TestSocketServer {

    public void test1() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();

        int len = 0;
        byte[] bytes = new byte[1024];
        len = inputStream.read(bytes);
        String str = new String(bytes, 0, len);
        System.out.println("ip" + socket.getInetAddress().getHostAddress() + str);

        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("shoudao".getBytes());
        socket.close();
//      serverSocket.close();
    }

    public void test2() throws IOException{
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket socket = serverSocket.accept();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String line = null;
        while ((line = bufferedReader.readLine()) != null){
            System.out.println(line);
        }
        socket.close();
    }

    public void test3() throws IOException{
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket socket = serverSocket.accept();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("a.txt", true));

        String line = null;
        while ((line = bufferedReader.readLine()) != null){
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }

        bufferedWriter.close();
        socket.close();
    }

    public void test4() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket socket = serverSocket.accept();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("a.txt", true));

        String line = null;
        while ((line = bufferedReader.readLine()) != null){
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }

        BufferedWriter bufferedWriter1 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedWriter1.write("文件成功上传");
        bufferedWriter1.flush();

        bufferedWriter.close();
        socket.close();
    }

    public void test5() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket socket = serverSocket.accept();

        BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("a.jpg"));
        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len = bufferedInputStream.read(bytes)) != -1){
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.flush();
        }

        BufferedOutputStream bufferedOutputStream1 = new BufferedOutputStream(socket.getOutputStream());
        bufferedOutputStream1.write("图拍成功了".getBytes());
        bufferedOutputStream1.flush();
        bufferedOutputStream.close();
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        TestSocketServer testSocketServer = new TestSocketServer();
//        testSocketServer.test1();
        testSocketServer.test5();
    }

}
