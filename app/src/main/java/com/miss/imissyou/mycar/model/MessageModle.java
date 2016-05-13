package com.miss.imissyou.mycar.model;

/**
 * 消息来源的数据层
 * Created by Imissyou on 2016/5/2.
 */
public interface MessageModle {
    /**
     * 从服务器加载消息
     * 用户信息
     */
    void loadMessageForService();


    /**
     * 删除用户的信息
     */
    void deleteMessage(String id);
}
