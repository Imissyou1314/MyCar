package com.miss.imissyou.mycar.model.impl;

import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.bean.UserBean;
import com.miss.imissyou.mycar.model.ChangePhoneNumberModle;
import com.miss.imissyou.mycar.presenter.ChangePhoneNumberPresenter;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.LinkService;

/**
 * Created by Imissyou on 2016/5/5.
 */
public class ChangePhoneNumberModelImpl implements ChangePhoneNumberModle {
    private ChangePhoneNumberPresenter  mChangePhoneNumberPresenter;

    public ChangePhoneNumberModelImpl(ChangePhoneNumberPresenter changePhoneNUmberPresenter) {
        this.mChangePhoneNumberPresenter = changePhoneNUmberPresenter;
    }

    @Override public void getCode(String phoneNumber) {

        //TODO
        String url = Constant.SERVER_URL + "verifyCode/phoneNumber=" + phoneNumber;

        LinkService.get(url, new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                mChangePhoneNumberPresenter.onFailure(errorNo, strMsg);
            }

            @Override public void onSuccess(String t) {
                ResultBean resultBean = GsonUtils.Instance().fromJson(t, ResultBean.class);
                mChangePhoneNumberPresenter.onSuccess(resultBean);
            }
        });

    }

    @Override public void submit(String phoneNumber, String code) {
        HttpParams params = new HttpParams();
        params.putHeaders("cookie", Constant.COOKIE);
        params.put("id", Constant.userBean.getId());
        params.put("phoneNumber", phoneNumber);
        params.put("code", code);

        String url = Constant.SERVER_URL + "users/changeRelatedPhone";

        LinkService.post(url, params, new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                mChangePhoneNumberPresenter.onFailure(errorNo, strMsg);
            }

            @Override public void onSuccess(String t) {
                ResultBean resultBean = GsonUtils.Instance().fromJson(t, ResultBean.class);
                mChangePhoneNumberPresenter.onSuccess(resultBean);
            }
        });
    }


}
