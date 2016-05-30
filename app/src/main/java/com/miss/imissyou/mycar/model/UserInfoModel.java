package com.miss.imissyou.mycar.model;

import com.miss.imissyou.mycar.bean.UserBean;

/**
 * 用户的模型层
 * Created by Imissyou on 2016/4/26.
 */
public interface UserInfoModel {

    /**
     * 获取服务器用户数据
     * @param userId 用户Id
     */
    void loadUserInfo(String userId);

    /**
     * 更新服务器用户数据
     * @param userbean
     */
    void ChangeUserInfo(UserBean userbean);
}
