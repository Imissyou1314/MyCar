package com.miss.imissyou.mycar.bean;

import java.util.Date;

/**
 * 消息类的属性
 * Created by Imissyou on 2016/5/2.
 */
public class MessageBean extends BaseBean{

    private int messageTag;     //消息类型
    private boolean isRead;     //消息是否阅读
    private String messageTitle;//消息标题
    private String messageMsg;  //消息的主消息
    private Date  messageTime;  //消息的时间

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getMessageMsg() {
        return messageMsg;
    }

    public void setMessageMsg(String messageMsg) {
        this.messageMsg = messageMsg;
    }

    public int getMessageTag() {
        return messageTag;
    }

    public void setMessageTag(int messageTag) {
        this.messageTag = messageTag;
    }

    public Date getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }
}
