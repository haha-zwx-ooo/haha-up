package com.haha.io.bio.simple;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @description: 简单模式的server
 * @author: 张文旭
 * @create: 2021-07-17 23:02
 **/
public class SimpleServer {

    private ServerSocket serverSocket;
    private Socket accept;
    private InputStream inputStream;
    private OutputStream outputStream;

    public static void main(String[] args) {
        SimpleServer simpleServer = new SimpleServer();
        simpleServer.bind(8888);
        simpleServer.accept();
        simpleServer.read();
        simpleServer.back();
        simpleServer.close();
    }

    public void bind(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("listen port success = " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void accept() {
        try {
            accept = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read() {
        try {
            inputStream = accept.getInputStream();
            int read;
            byte[] read_byte = new byte[1024];
            while ((read = inputStream.read(read_byte)) != -1) {
                System.out.println(new String(read_byte, 0, read, StandardCharsets.UTF_8));
            }
            System.out.println("读取数据结束");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void back() {
        try {
            outputStream = accept.getOutputStream();
            outputStream.write(("hello" + accept.getInetAddress()).getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            outputStream.close();
            inputStream.close();
            accept.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
