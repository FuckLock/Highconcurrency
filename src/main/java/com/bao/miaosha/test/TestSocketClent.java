package com.bao.miaosha.test;

import sun.rmi.rmic.IndentingWriter;

import java.io.*;
import java.net.Socket;

public class TestSocketClent {

    public void test1() throws IOException {
        Socket socket = new Socket("192.168.1.5", 8888);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("发生地方".getBytes());


        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len = inputStream.read(bytes);
        String str = new String(bytes, 0, len);
        System.out.println(str);
        socket.close();
    }

    public void test2() throws IOException{
        Socket socket = new Socket("192.168.1.5", 8888);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        String line = null;
        while ((line = bufferedReader.readLine()) != null){
            if (line.equals("886")){
                break;
            }
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }

        socket.close();
    }

    public void test3() throws IOException {
        Socket socket = new Socket("192.168.1.5", 8888);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        String line = null;
        while ((line = bufferedReader.readLine()) != null){
            if (line.equals("886")){
                break;
            }
            bufferedWriter.write(line);
            bufferedWriter.flush();
            bufferedWriter.newLine();
        }
        socket.close();
    }

    public void test4() throws IOException {
        Socket socket = new Socket("192.168.1.5", 8888);
        BufferedReader bufferedReader = new BufferedReader(new FileReader("miaosha.iml"));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        String line = null;
        while ((line = bufferedReader.readLine()) != null){
            bufferedWriter.write(line);
            bufferedWriter.flush();
            bufferedWriter.newLine();
        }

        socket.shutdownOutput();
        BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String str = bufferedReader1.readLine();
        System.out.println(str);

        bufferedReader.close();
        socket.close();
    }

    public void test5() throws IOException {
        Socket socket = new Socket("192.168.1.5", 8888);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("dada.png"));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());

        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = bufferedInputStream.read(bytes)) != -1){
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.flush();
        }

        socket.shutdownOutput();
        BufferedInputStream bufferedInputStream1 = new BufferedInputStream(socket.getInputStream());
        len = bufferedInputStream1.read(bytes);
        String str = new String(bytes, 0, len);
        System.out.println(str);
        bufferedInputStream.close();
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        TestSocketClent testSocketClent = new TestSocketClent();
//        testSocketClent.test1();
        testSocketClent.test5();
    }

}
