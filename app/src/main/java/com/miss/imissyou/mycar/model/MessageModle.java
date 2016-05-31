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
    void getUserAllMessage(String userId);


    /**
     * 删除用户的信息
     */
    void deleteMessage(int id);

    /**
     * 更改服务器信息的状态
     * @param userId
     * @param messageId
     */
    void changeStateToService(String userId, String messageId);

    /**
     * 获取用户未读信息
     * @param userId 用户Id
     */
    void getUserUnReadMessage(String userId);
}
