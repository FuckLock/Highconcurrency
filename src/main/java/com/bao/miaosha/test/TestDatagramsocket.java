package com.bao.miaosha.test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class TestDatagramsocket {

    public void test1() throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();
        String str = "hello world";
        DatagramPacket datagramPacket = new DatagramPacket(str.getBytes(), str.length(), InetAddress.getByName("192.168.1.5"), 1155);
        datagramSocket.send(datagramPacket);
        datagramSocket.close();
    }

    public void test2()  {
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                if (line.equals("886") ){
                    break;
                }
                DatagramPacket datagramPacket = new DatagramPacket(line.getBytes(), line.length(), InetAddress.getByName("192.168.1.5"), 1155);
                datagramSocket.send(datagramPacket);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            datagramSocket.close();
        }
    }

    public static void main(String[] args)  {
        TestDatagramsocket testDatagramsocket = new TestDatagramsocket();
        testDatagramsocket.test2();
    }
}
