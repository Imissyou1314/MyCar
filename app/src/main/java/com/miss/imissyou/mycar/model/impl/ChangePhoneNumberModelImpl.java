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
                    onFailure(Constant.WARE_ERROR, resultBean.getResultInfo());
                }
            }
        });

    }

    @Override public void changeUserPhone(String phoneNumber, String code) {
        String url = Constant.SERVER_URL + "user/changePhone";
        submit(phoneNumber, code, url);
    }

    @Override public void changeUserdersaPhone(String phoneNumber, String code) {
        String url = Constant.SERVER_URL + "users/changeRelatedPhone";
        submit(phoneNumber, code, url);
    }

   public void submit(String phoneNumber, String code, String url) {

       LogUtils.d("验证码:" + code + "手机号：" + phoneNumber);
        HttpParams params = new HttpParams();
        params.putHeaders("cookie", Constant.COOKIE);
        params.put("id", Constant.userBean.getId());
        params.put("newPhone", phoneNumber);
        params.put("verifyCode", code);

       LogUtils.d(url);
        RxVolley.post(url, params, new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                mChangePhoneNumberPresenter.onFailure(errorNo, strMsg);
            }

            @Override public void onSuccess(String t) {
                LogUtils.d(t);
                ResultBean resultBean = GsonUtils.Instance().fromJson(t, ResultBean.class);
                if (resultBean.isServiceResult()) {
                    mChangePhoneNumberPresenter.onSuccess(resultBean);
                } else {
                    onFailure(Constant.WARE_ERROR, resultBean.getResultInfo());
                }

            }
        });
    }


}
