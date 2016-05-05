package com.miss.imissyou.mycar.presenter.impl;

import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.ChangePhoneNumberModle;
import com.miss.imissyou.mycar.model.impl.ChangePhoneNumberModelImpl;
import com.miss.imissyou.mycar.presenter.ChangePhoneNumberPresenter;
import com.miss.imissyou.mycar.view.ChangePhoneNumberView;
import com.miss.imissyou.mycar.view.activity.ChangePhoneNumberActivity;

/**
 * 更改手机号的控制层
 * Created by Imissyou on 2016/5/5.
 */
public class ChangePhoneNumberPresenterImpl implements ChangePhoneNumberPresenter{

    private ChangePhoneNumberView mChangePhoneNumberView;
    private ChangePhoneNumberModle mChangePhoneNumberModle;

    public ChangePhoneNumberPresenterImpl(ChangePhoneNumberActivity changePhoneNumberView) {
        attachView(changePhoneNumberView);
        mChangePhoneNumberModle = new ChangePhoneNumberModelImpl(this);
    }

    @Override
    public void onFailure(int errorNo, String strMsg) {
        mChangePhoneNumberView.showResultError(errorNo, strMsg);
    }

    @Override
    public void onSuccess(BaseBean resultBean) {
        mChangePhoneNumberView.showResultSuccess((ResultBean) resultBean);
    }

    @Override
    public void loadServiceData(BaseBean useBean) {

    }

    @Override public void attachView(ChangePhoneNumberView view) {
        this.mChangePhoneNumberView = view;
    }

    @Override
    public void detchView() {
        mChangePhoneNumberView = null;
    }

    @Override public void getCode(String phoneNumber) {
        //认证手机号码
        if (!phoneNumber.equals("")) {
            mChangePhoneNumberModle.getCode(phoneNumber);
        }
    }

    @Override public void submit(String phoneNumber, String code) {
        if (!phoneNumber.equals("") || !code.equals(""))
            mChangePhoneNumberModle.submit(phoneNumber, code);
    }
}
