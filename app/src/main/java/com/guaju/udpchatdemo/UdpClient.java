package com.guaju.udpchatdemo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * Created by guaju on 2017/11/3.
 */

public class UdpClient {
    /**
     * InetAddress
     * DatagramPacket
     * DatagramSocket
     */
    Scanner scanner;
    InetAddress inetAddress;
    DatagramPacket  dp;
    int port=7777;
    DatagramSocket ds;
    public UdpClient() throws IOException {
        //初始化
        scanner=new Scanner(System.in);
        scanner.useDelimiter("\n");
        inetAddress=InetAddress.getByName("192.168.38.168");
        ds=new DatagramSocket(port,inetAddress);
        /**
         * 参数1：输入数据字节数组
         * 参数2：数组长度
         * 参数3：服务端的inetAddress
         * 参数4：服务端端口号
         */
        inetAddress=InetAddress.getByName("192.168.38.164");
        while(true){
        String clientMsg=scanner.next();
        byte[] bytes = clientMsg.getBytes();
        dp=new DatagramPacket(bytes,bytes.length,inetAddress,6666);
        ds.send(dp);
        //接受一下服务端的数据
        byte[] buf=new byte[1024];
        DatagramPacket dp2 = new DatagramPacket(buf, buf.length);
        ds.receive(dp2);
        byte[] data = dp2.getData();
        String serverMsg=new String(data);
        System.out.println(serverMsg+"\n");
        }
    }

    public static void main(String[] args) {
        try {
            UdpClient udpClient = new UdpClient();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
