package com.guaju.udpchatdemo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * Created by guaju on 2017/11/3.
 */

public class UdpServer {
    /**
     * 需要用到的类
     * InetAddress ：表示具备一些ip相关属性的类 192.168.38.164
     * DatagramPacket： 数据包，我们做数据交互的时候需要用到这个包
     * DatagramSocket：传输数据数据的单元
     */
    InetAddress inetAddress;
    //端口号：表明我这个server的标识
    int port=6666;
    DatagramPacket datagramPacket;
    DatagramSocket datagramSocket;
    Scanner scanner;
    //通过构造方法将三个对象初始化
    public UdpServer() throws IOException {
        scanner=new Scanner(System.in);
        //输入回车确定输入的文字
        scanner.useDelimiter("\n");
        //初始化inetAddress
        inetAddress=InetAddress.getLocalHost();
        //初始化传输类
        datagramSocket=new DatagramSocket(port,inetAddress);
        //构造一个数据，长度可以自定义
        byte[] buf=new byte[1024];
        //初始化传输数据包
        datagramPacket=new DatagramPacket(buf,buf.length);
        //我要不停的去监听客户端的请求
        while(true){
            //接受数据,如果客户端一直不发数据,receive方法就阻塞住了，知道客户端吧数据传过来
            datagramSocket.receive(datagramPacket);
            //如果有数据就直接从 datagramPacket拿数据
            byte[] data = datagramPacket.getData();
            int length = datagramPacket.getLength();
            String clientAddressInfo=datagramPacket.getAddress().getHostName()+":"+datagramPacket.getPort();
            System.out.println(new String(data)+"---"+clientAddressInfo+"\n");
            // 给客户端发送数据
            String serverMsg=scanner.next();
            //转字节数组
            byte[] bytes = serverMsg.getBytes();
            InetAddress clientAddress = datagramPacket.getAddress();

            DatagramPacket clientdp = new DatagramPacket(bytes,bytes.length,clientAddress,datagramPacket.getPort());
            datagramSocket.send(clientdp);

        }
    }

}
