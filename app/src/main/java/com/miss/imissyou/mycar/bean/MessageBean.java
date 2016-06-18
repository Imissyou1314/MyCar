package com.miss.imissyou.mycar.bean;

/**
 * 消息类的属性
 * Created by Imissyou on 2016/5/2.
 */
public class MessageBean extends BaseBean{

    private int id;
    private int type;     //消息类型
    private boolean state;     //消息是否阅读
    private String title;//消息标题
    private String content;  //消息的主消息
    private String createTime;  //信息当前的信息

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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


    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

//    public String getSystemData() {
//        if (null == createTime) {
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
//            createTime = formatter.format(new Date(System.currentTimeMillis()));
//        }
//        return  createTime;
//    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}