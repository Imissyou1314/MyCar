package com.miss.imissyou.mycar.model.impl;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.miss.imissyou.mycar.bean.UserBean;
import com.miss.imissyou.mycar.presenter.UserInfoPresenter;
import com.miss.imissyou.mycar.model.UserInfoModel;
import com.miss.imissyou.mycar.util.Constant;

/**
 *
 * 获取用户数据
 * Created by Imissyou on 2016/4/26.
 */
public class UserInfoModelImpl implements UserInfoModel{
    private UserInfoPresenter mUserInfoPresenter;
    private String url = Constant.SERVER_URL + "URL";    //TODO

    public UserInfoModelImpl(UserInfoPresenter userInfoPresenter) {
        this.mUserInfoPresenter = userInfoPresenter;
    }

    @Override public void loadUserInfo() {
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
            }

            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
            }
        });
    }

    @Override public void ChangeUserInfo(UserBean userbean) {

    }
}
