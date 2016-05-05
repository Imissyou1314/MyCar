package com.miss.imissyou.mycar.model;

import com.miss.imissyou.mycar.bean.UserBean;

/**
 * 消息来源的数据层
 * Created by Imissyou on 2016/5/2.
 */
public interface MessageModle {
    /**
     * 从服务器加载消息
     */
    void loadMessageForService(UserBean userBean);


}
