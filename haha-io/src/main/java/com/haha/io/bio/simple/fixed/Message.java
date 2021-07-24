package com.haha.io.bio.simple.fixed;

import java.io.Serializable;

/**
 * @description: 定长报文包装
 * @author: 张文旭
 * @create: 2021-07-18 23:03
 **/
public class Message implements Serializable {

    private int lengeth;
    private byte[] content;

    public int getLengeth() {
        return lengeth;
    }

    public void setLengeth(int lengeth) {
        this.lengeth = lengeth;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
