package com.miss.imissyou.mycar.model.impl;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.ChangePhoneNumberModle;
import com.miss.imissyou.mycar.presenter.ChangePhoneNumberPresenter;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.RxVolleyUtils;

import java.util.Map;

/**
 * Created by Imissyou on 2016/5/5.
 */
public class ChangePhoneNumberModelImpl implements ChangePhoneNumberModle {
    private ChangePhoneNumberPresenter  mChangePhoneNumberPresenter;

    public ChangePhoneNumberModelImpl(ChangePhoneNumberPresenter changePhoneNUmberPresenter) {
        this.mChangePhoneNumberPresenter = changePhoneNUmberPresenter;
    }

    @Override public void getCode(String phoneNumber) {

        String url = Constant.SERVER_URL + "verifyCode/phoneNumber=" + phoneNumber;

        RxVolley.get(url, new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                mChangePhoneNumberPresenter.onFailure(errorNo, strMsg);
            }

            @Override public void onSuccess(String t) {
                LogUtils.d(t);
                ResultBean resultBean = GsonUtils.Instance().fromJson(t, ResultBean.class);
                if (resultBean.isServiceResult()) {
                    mChangePhoneNumberPresenter.onSuccess(resultBean);
                } else {
                    if (resultBean.getResultInfo().equals(Constant.FileCOOKIE)) {
                        RxVolleyUtils.getInstance().restartLogin();
                    } else {
                        onFailure(Constant.WARE_ERROR, resultBean.getResultInfo());
                    }
                }
            }
        });

    }

    @Override public void changeUserPhone(String phoneNumber, String code) {
        String url = Constant.SERVER_URL + "users/update";

        HttpParams params = new HttpParams();
        params.putHeaders("Set-Cookie", Constant.COOKIE);
        params.put("id", Constant.userBean.getId() + "");
        params.put("phone", phoneNumber);
        params.put("verifyCode", code);
        if (null != Constant.userBean.getUsername())
            params.put("username",Constant.userBean.getUsername());
        if (null != Constant.userBean.getRealName())
            params.put("realName",Constant.userBean.getRealName());
        if (null != Constant.userBean.getSafePassword())
            params.put("safePassword",Constant.userBean.getSafePassword());

        submit(phoneNumber, code, url,params);
    }

    @Override public void changeUserdersaPhone(String phoneNumber, String code) {
        String url = Constant.SERVER_URL + "users/changeRelatedPhone";

        HttpParams params = new HttpParams();
        params.putHeaders("Set-Cookie", Constant.COOKIE);
        params.put("id", Constant.userBean.getId() + "");
        params.put("newPhone", phoneNumber);
        params.put("verifyCode", code);
        submit(phoneNumber, code, url,params);
    }

   public void submit(String phoneNumber, String code, String url, HttpParams params) {

       LogUtils.w("验证码:" + code + "手机号：" + phoneNumber);


       LogUtils.w(url);
        RxVolleyUtils.getInstance().post(url, params, new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                LogUtils.w("错误码:" + errorNo + ">>>::错误信息:" + strMsg);
                mChangePhoneNumberPresenter.onFailure(errorNo, strMsg);
            }

            @Override public void onSuccess(String t) {
                LogUtils.w(t);
                ResultBean resultBean = GsonUtils.Instance().fromJson(t, ResultBean.class);
                if (resultBean.isServiceResult()) {
                    mChangePhoneNumberPresenter.onSuccess(resultBean);
                } else {
                    if (resultBean.getResultInfo().equals(Constant.FileCOOKIE)) {
                        RxVolleyUtils.getInstance().restartLogin();
                    } else {
                        onFailure(Constant.WARE_ERROR, resultBean.getResultInfo());
                    }
                }
            }
        });
    }


}
