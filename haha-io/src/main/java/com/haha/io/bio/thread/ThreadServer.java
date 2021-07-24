package com.haha.io.bio.thread;

import com.haha.io.serialize.SerializeUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.var;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @description: 线程模式的server
 * @author: 张文旭
 * @create: 2021-07-17 23:03
 **/
public class ThreadServer {

    private ServerSocket serverSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public static void main(String[] args) {
        ThreadServer threadServer = new ThreadServer();
        ServerSocket listen = threadServer.listen(8888);
        new ServerAcceptTask(listen).start();
    }


    /**
     * 监听端口
     *
     * @param port
     */
    public ServerSocket listen(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("listen on port = " + port + "   success !");
            return serverSocket;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取客户端连接
     */
    public void accept() {

    }
}

class ServerAcceptTask extends Thread {

    private ServerSocket serverSocket;

    public ServerAcceptTask(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket accept = serverSocket.accept();
                System.out.println("有客户端连接 - > " + accept.getInetAddress());
                new ServerReadTask(accept).start();
                new ServerWriteTask(accept).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class ServerReadTask extends Thread {
    private Socket accept;
    private InputStream inputStream;

    public ServerReadTask(Socket accept) {
        this.accept = accept;
    }

    @Override
    public void run() {
        try {
            while (true) {
                inputStream = accept.getInputStream();
                byte[] _length = new byte[4];
                int read = inputStream.read(_length);
                int length = ByteBuffer.wrap(_length).getInt();
                byte[] bytes = new byte[length];
                inputStream.read(bytes);
                System.out.println("报文长度:" + length + "     客户端输入：" + new String(bytes, StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ServerWriteTask extends Thread {
    private Socket accept;
    private OutputStream outputStream;

    public ServerWriteTask(Socket accept) {
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
