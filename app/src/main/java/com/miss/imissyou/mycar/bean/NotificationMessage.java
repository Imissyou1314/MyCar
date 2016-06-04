package com.miss.imissyou.mycar.bean;

/**
 * 提示栏提示的信息
 * Created by Imissyou on 2016/5/31.
 */
public class NotificationMessage {

    private int messagetype;            //消息类型
    private String message;             //消息


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(int messagetype) {
        this.messagetype = messagetype;
    }
}
