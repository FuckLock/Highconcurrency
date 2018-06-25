package com.bao.miaosha.test;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


public class TestRerciveDatagramSocket {

    public void test1() throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(1155);
        byte[] bytes = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
        datagramSocket.receive(datagramPacket);

        byte[] bytes1 = datagramPacket.getData();
        String str = new String(bytes, 0, bytes1.length);
        System.out.println(datagramPacket.getAddress().getHostAddress() + str);
        datagramSocket.close();
    }

    public void test2(){

        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket(1155);
            while (true){
                byte[] bytes = new byte[1024];
                DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
                datagramSocket.receive(datagramPacket);

                byte[] bytes1 = datagramPacket.getData();
                String str = new String(bytes, 0, bytes1.length);
                System.out.println(datagramPacket.getAddress().getHostAddress() + "发送数据" + str);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            datagramSocket.close();
        }


    }

    public static void main(String[] args) {
        TestRerciveDatagramSocket testRerciveDatagramSocket = new TestRerciveDatagramSocket();
        testRerciveDatagramSocket.test2();
    }
}
