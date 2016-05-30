package com.miss.imissyou.mycar.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 消息类的属性
 * Created by Imissyou on 2016/5/2.
 */
public class MessageBean extends BaseBean{

    private int id;
    private int type;     //消息类型
    private boolean state;     //消息是否阅读
    private String messageTitle;//消息标题
    private String content;  //消息的主消息
    private String nowData;  //信息当前的信息

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessageTitle() {
        if (getType() == 1) {
            messageTitle = "维修警报";
        } else if (getType() == 2) {
            messageTitle = "加油警告";
        } else {
            messageTitle ="无关警告";
        }
        return messageTitle;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getSystemData() {
        if (null == nowData) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            nowData = formatter.format(new Date(System.currentTimeMillis()));
        }
        return  nowData;
    }
}