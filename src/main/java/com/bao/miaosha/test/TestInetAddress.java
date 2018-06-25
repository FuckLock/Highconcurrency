package com.bao.miaosha.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestInetAddress {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName("baodongdong");
        String hostName = inetAddress.getHostName();
        String hostAddress = inetAddress.getHostAddress();
        System.out.println("主机名" + hostName + "主机地址" + hostAddress);

        InetAddress inetAddress1 = InetAddress.getByName("192.168.1.5");
        String hostName1 = inetAddress1.getHostName();
        String hostAddress1 = inetAddress1.getHostAddress();
        System.out.println("主机名" + hostName1 + "主机地址" + hostAddress1);
    }
}
