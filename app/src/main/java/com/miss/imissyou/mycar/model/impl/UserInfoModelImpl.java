package com.miss.imissyou.mycar.model.impl;


import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.UserInfoPresenter;
import com.miss.imissyou.mycar.model.UserInfoModel;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.RxVolleyUtils;
import com.miss.imissyou.mycar.util.StringUtil;

import java.io.File;

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

    @Override public void checkSafePassword(CharSequence safePasswordInput) {

        String url = Constant.SERVER_URL + "users/confirmSafePassword";
        LogUtils.d("请求路径：" + url);

        HttpParams params = new HttpParams();
        params.put("id",Constant.userBean.getId() + "");
        LogUtils.d("用户id 和加密后的安全码：" + StringUtil.strToMD5(safePasswordInput.toString()
                + ">>>>>ID::" + Constant.userBean.getId()));
        params.put("safePassword",StringUtil.strToMD5(safePasswordInput.toString()));

        HttpCallback callBack = new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                mUserInfoPresenter.onFailure(errorNo, strMsg);
            }

            @Override public void onSuccess(String t) {
                LogUtils.d("获取到的数据" + t);
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (resultBean.isServiceResult()) {
                    mUserInfoPresenter.checkSafePasswordSuccess(resultBean);
                } else {
                    if (resultBean.getResultInfo().equals(Constant.FileCOOKIE)) {
                        RxVolleyUtils.getInstance().restartLogin();
                    } else {
                        onFailure(0, resultBean.getResultInfo());
                    }
                }
            }
        };

        RxVolleyUtils.getInstance().post(url,params,callBack);

    }

    @Override public void updataUserImage(String ImagePath) {
        String url = Constant.SERVER_URL + "users/updateImg";
        LogUtils.d("请求路径" + url);

        HttpParams params = new HttpParams();

        if (Constant.userBean == null || null == Constant.userBean.getId()) {
            return;
        }

        LogUtils.d("上传图片地址:" + ImagePath);
        params.put("id",Constant.userBean.getId() + "");
        params.put("userfile", new File(ImagePath));
        HttpCallback callback = new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                mUserInfoPresenter.onFailure(errorNo, strMsg);
            }

            @Override public void onSuccess(String t) {
                LogUtils.d(t);
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (resultBean.isServiceResult()) {
                    mUserInfoPresenter.onUpdateSuccess(resultBean);
                } else {
                    if (resultBean.getResultInfo().equals(Constant.FileCOOKIE)) {
                        RxVolleyUtils.getInstance().restartLogin();
                    } else {
                        onFailure(Constant.WARE_ERROR, resultBean.getResultInfo());
                    }
                }
            }
        };

        RxVolleyUtils.getInstance().post(url,params,callback);

    }

}
