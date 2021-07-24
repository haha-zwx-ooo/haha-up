package com.haha.io.bio.simple.fixed;

import com.haha.io.serialize.ProtostuffSerialize;
import com.haha.io.serialize.SerializeUtils;
import io.protostuff.LinkedBuffer;
import sun.nio.ch.IOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
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

    //避免每次序列化都重新申请Buffer空间
    private static LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);


    public static void main(String[] args) {
        SimpleClient simpleClient = new SimpleClient();
        simpleClient.connection();
        while (true) {
            String s = new Scanner(System.in).nextLine();
            if ("exit".endsWith(s)) {
                break;
            }
            byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
//            Message message = new Message();
//            message.setLengeth(bytes.length);
//            message.setContent(bytes);
//            byte[] serialize = SerializeUtils.objectToByteArray(message);
            int length = bytes.length;
            byte[] length_bytes = ByteBuffer.allocate(4).putInt(length).array();
            byte[] data = SerializeUtils.byteMerger(length_bytes, bytes);
            simpleClient.send(data);
            simpleClient.get();
        }
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

    public void send(byte[] data) {
        try {
            outputStream = socket.getOutputStream();
            outputStream.write(data);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void get() {
        try {
            inputStream = socket.getInputStream();
            byte[] int_head = new byte[4];
            int read = inputStream.read(int_head);
            int length = ByteBuffer.wrap(int_head).getInt();
            System.out.println("报文长度:" + length);
            byte[] bytes = new byte[length];
            inputStream.read(bytes);
            System.out.println("服务端返回：" + new String(bytes, StandardCharsets.UTF_8));
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
