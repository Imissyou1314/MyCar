package com.miss.imissyou.mycar.model;

/**
 * 更改手机号的模型
 * Created by Imissyou on 2016/5/5.
 */
public interface ChangePhoneNumberModle {

    /**
     * 获取验证码
     * @param phoneNumber
     */
    void getCode(String phoneNumber);

    /**
     * 提交验证码和手机号
     * @param phoneNumber
     * @param code
     */
    void changeUserPhone(String phoneNumber,String code);

    /**
     * 更改用户亲人手机
     * @param phoneNumber
     * @param code
     */
    void changeUserdersaPhone(String phoneNumber, String code);
}
