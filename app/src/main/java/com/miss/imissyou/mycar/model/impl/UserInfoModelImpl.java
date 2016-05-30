package com.miss.imissyou.mycar.model.impl;

import android.util.Log;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.bean.UserBean;
import com.miss.imissyou.mycar.presenter.UserInfoPresenter;
import com.miss.imissyou.mycar.model.UserInfoModel;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.StringUtil;

/**
 *
 * 获取用户数据
 * Created by Imissyou on 2016/4/26.
 */
public class UserInfoModelImpl implements UserInfoModel{

    private UserInfoPresenter mUserInfoPresenter;

    public UserInfoModelImpl(UserInfoPresenter userInfoPresenter) {
        this.mUserInfoPresenter = userInfoPresenter;
    }

    @Override public void loadUserInfo(String userId) {

        String url = Constant.SERVER_URL + "users/id=" + userId;
        LogUtils.d("请求路径：" + url);

        HttpCallback callBack = new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                mUserInfoPresenter.onFailure(errorNo, strMsg);
            }

            @Override public void onSuccess(String t) {
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (resultBean.isServiceResult()) {
                    mUserInfoPresenter.onSuccess(resultBean);
                } else {
                    onFailure(1, resultBean.getResultInfo());
                }
            }
        };

        new RxVolley.Builder()
                .shouldCache(false)
                .httpMethod(RxVolley.Method.GET)
                .cacheTime(0)
                .url(url)
                .callback(callBack)
                .doTask();

    }

    @Override public void ChangeUserInfo(UserBean userbean) {
        String url = Constant.SERVER_URL + "users/update";
        LogUtils.d("请求路径" + url);

        HttpParams params = new HttpParams();

        params.put("loginid", userbean.getLoginid());
        if (null != userbean.getUsername()) {
            params.put("username", userbean.getUsername());
        }
        if (null != userbean.getRealName()) {
            params.put("realName", userbean.getRealName());
        }

        if (null != userbean.getUserImg()) {
             params.put("userImage", userbean.getUserImg());
        }
        if (null != userbean.getSafePassword()) {
            params.put("safePassword", userbean.getSafePassword());
        }

        RxVolley.post(url, params, new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                mUserInfoPresenter.onFailure(errorNo, strMsg);
            }

            @Override public void onSuccess(String t) {
                LogUtils.d(t);
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (resultBean.isServiceResult()) {
                    mUserInfoPresenter.onUpdateSuccess(resultBean);
                } else {
                    onFailure(Constant.WARE_ERROR, resultBean.getResultInfo());
                }
            }
        });
    }

}
