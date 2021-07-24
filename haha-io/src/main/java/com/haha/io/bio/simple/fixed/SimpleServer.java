package com.haha.io.bio.simple.fixed;


import com.haha.io.serialize.SerializeUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

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
        while (true) {
            simpleServer.read();
            String s = new Scanner(System.in).nextLine();
            if ("exit".endsWith(s)) {
                break;
            }
            byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
            int length = bytes.length;
            byte[] length_bytes = ByteBuffer.allocate(4).putInt(length).array();
            byte[] data = SerializeUtils.byteMerger(length_bytes, bytes);
            simpleServer.back(data);
        }
//        simpleServer.close();
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
            byte[] int_head = new byte[4];
            int read = inputStream.read(int_head);
            int length = ByteBuffer.wrap(int_head).getInt();
            System.out.println("报文长度:" + length);
            byte[] bytes = new byte[length];
            inputStream.read(bytes);
            System.out.println("客户端输入：" + new String(bytes, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void back(byte[] data) {
        try {
            outputStream = accept.getOutputStream();
            outputStream.write(data);
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
