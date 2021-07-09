package com.haha.cpu;

/**
 * @description: 运行此代码，cpu会飙高
 * @author: 张文旭
 * @create: 2021-06-23 14:27
 **/
public class Math {

    public static final int initData = 666;

    public int compute() {  //一个方法对应一块栈帧内存区域
        int a = 1;
        int b = 2;
        int c = (a + b) * 10;
        return c;
    }

    public static void main(String[] args) {
        Math math = new Math();
        while (true) {
            math.compute();
        }

    }
}
