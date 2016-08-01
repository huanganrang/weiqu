package rml.model;

/**
 * Simple to Introduction
 * author:Jianghui
 * date:2016/8/1 9:23
 */
public class Message {
    private Byte head=0x68;
    private Integer length;
    private String data;
    private Byte tail=0x16;

    public Byte getHead() {
        return head;
    }

    public void setHead(Byte head) {
        this.head = head;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Byte getTail() {
        return tail;
    }

    public void setTail(Byte tail) {
        this.tail = tail;
    }
}
