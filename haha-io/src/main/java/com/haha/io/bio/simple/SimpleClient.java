package com.haha.io.bio.simple;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @description: 简单模式的客户端
 * @author: 张文旭
 * @create: 2021-07-17 23:03
 **/
public class SimpleClient {
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;


    public static void main(String[] args) {
        SimpleClient simpleClient = new SimpleClient();
        simpleClient.connection();
        simpleClient.send(new Scanner(System.in).nextLine());
        simpleClient.get();
        simpleClient.close();
    }


    public void connection() {
        socket = new Socket();
        SocketAddress address = new InetSocketAddress("127.0.0.1", 8888);
        try {
            socket.connect(address, 10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String str) {
        try {
            outputStream = socket.getOutputStream();
            outputStream.write(str.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.shutdownOutput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void get() {
        try {
            inputStream = socket.getInputStream();
            int read_index = -1;
            byte[] bytes = new byte[1024];
            while ((read_index = inputStream.read(bytes)) != -1) {
                System.out.println("服务端返回：" + new String(bytes, 0, read_index, StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
