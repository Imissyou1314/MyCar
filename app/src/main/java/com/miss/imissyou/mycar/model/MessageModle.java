package com.miss.imissyou.mycar.model;


/**
 * 消息来源的数据层
 * Created by Imissyou on 2016/5/2.
 */
public interface MessageModle {
    /**
     * 从服务器加载用户的所有消息
     * 用户信息
     */

    void getUserAllMessage(Long userId);


    /**
     * 删除用户的信息
     */
    void deleteMessage(int id);

    /**
     * 根据用户Id更改服务器信息的状态
     * @param userId
     * @param messageId
     */
    void changeStateToService(Long userId, Long messageId);

    /**
     * 获取用户未读信息
     * @param userId 用户Id
     */
    void getUserUnReadMessage(Long userId);
}
