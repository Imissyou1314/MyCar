package com.miss.imissyou.mycar.bean;

/**
 * 提示栏的信息
 * Created by 青玉 on 2016/5/29.
 */
public class NotificationMessage {
    private int messagetype;
    private String message;

    public int getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(int messagetype) {
        this.messagetype = messagetype;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
