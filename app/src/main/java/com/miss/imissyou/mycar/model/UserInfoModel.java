package com.miss.imissyou.mycar.model;


/**
 * 用户的模型层
 * Created by Imissyou on 2016/4/26.
 */
public interface UserInfoModel {


    /**
     * 检查用户安全码是否正确
     * @param safePasswordInput  安全码
     */
    void checkSafePassword(CharSequence safePasswordInput);

    /**
     * 更新用户头像
     * @param ImagePath 头像地址
     */
    void updataUserImage(String ImagePath);
}
