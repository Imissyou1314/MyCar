package com.miss.imissyou.mycar.presenter;

import com.miss.imissyou.mycar.view.ChangePhoneNumberView;

/**
 * 更改手机号码
 * Created by Imissyou on 2016/5/5.
 */
public interface ChangePhoneNumberPresenter extends MainPresenter<ChangePhoneNumberView> {

    /**
     * ===>Model
     * 获取验证码
     * @param phoneNumber  手机号
     */
    void getCode(String phoneNumber);

    /**
     * ===> Model
     * 提交更改
     * @param phoneNumber   手机号
     * @param code          验证码
     * @param TAG 0 用户自己改手机 1 更改亲人手机
     */
    void submit(String phoneNumber, String code, int TAG);
}
