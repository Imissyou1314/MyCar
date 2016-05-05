package com.miss.imissyou.mycar.presenter;

import com.miss.imissyou.mycar.view.ChangePhoneNumberView;

/**
 * 更改手机号码
 * Created by Imissyou on 2016/5/5.
 */
public interface ChangePhoneNumberPresenter extends MainPresenter<ChangePhoneNumberView> {

    /**
     * 获取验证码
     * @param phoneNumber
     */
    void getCode(String phoneNumber);

    /**
     * 提交更改
     * @param phoneNumber
     * @param code
     */
    void submit(String phoneNumber, String code);
}
