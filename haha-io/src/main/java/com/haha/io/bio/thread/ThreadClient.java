package com.haha.io.bio.thread;

import com.haha.io.serialize.SerializeUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @description: 线程模式的客户端
 * @author: 张文旭
 * @create: 2021-07-17 23:03
 **/
public class ThreadClient {
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public static void main(String[] args) {
        ThreadClient threadClient = new ThreadClient();
        Socket conn = threadClient.conn("127.0.0.1", 8888, 60 * 1000);
        new ClientWriteTask(conn).start();
        new ClientReadTask(conn).start();
    }


    /**
     * 连接服务器
     *
     * @param port
     */
    public Socket conn(String host, int port, int timeout) {
        try {
            socket = new Socket();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
            socket.connect(inetSocketAddress, timeout);
            System.out.println("connect on address = " + host + ":" + port + "   success !");
            return socket;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class ClientReadTask extends Thread {
    private Socket accept;
    private InputStream inputStream;

    public ClientReadTask(Socket accept) {
        this.accept = accept;
    }

    @Override
    public void run() {
        while (true) {
            try {
                inputStream = accept.getInputStream();
                byte[] _length = new byte[4];
                int read = inputStream.read(_length);
                int length = ByteBuffer.wrap(_length).getInt();
                byte[] bytes = new byte[length];
                inputStream.read(bytes);
                System.out.println("报文长度:" + length + "     服务端回复：" + new String(bytes, StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class ClientWriteTask extends Thread {
    private Socket accept;
    private OutputStream outputStream;

    public ClientWriteTask(Socket accept) {
        this.accept = accept;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("请输入： ");
                String s = scanner.nextLine();
                byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
                int l = bytes.length;
                byte[] length_bytes = ByteBuffer.allocate(4).putInt(l).array();
                byte[] data = SerializeUtils.byteMerger(length_bytes, bytes);
                outputStream = accept.getOutputStream();
                outputStream.write(data);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
