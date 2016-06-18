package com.miss.imissyou.mycar.model.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.MessageModle;
import com.miss.imissyou.mycar.presenter.MessagePresenter;
import com.miss.imissyou.mycar.presenter.impl.MessagePresenterImpl;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.RxVolleyUtils;

import java.util.Map;

/**
 * 消息获取的实现层
 * Created by Imissyou on 2016/5/2.
 */
public class MessageModelImpl implements MessageModle {
    private MessagePresenter mMessagePresenter;


    @Override public void getUserAllMessage(@Nullable Long userId) {

        String url = Constant.SERVER_URL + "message/getAll/userId=" + userId;

        LogUtils.d("请求路径：" + url);
        HttpCallback callback =  new HttpCallback() {

            @Override public void onSuccess(String t) {
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (resultBean.isServiceResult()) {
                    mMessagePresenter.onSuccess(resultBean);
                } else {
                    if (resultBean.getResultInfo().equals(Constant.FileCOOKIE)) {
                        RxVolleyUtils.getInstance().restartLogin();
                    } else {
                        onFailure(0,resultBean.getResultInfo());
                    }
                }
            }

            @Override public void onFailure(int errorNo, String strMsg) {
                if (errorNo == Constant.NETWORK_STATE)
                    strMsg = Constant.NOTNETWORK;
                mMessagePresenter.onFailure(errorNo, strMsg);
            }
        };

        RxVolleyUtils.getInstance().get(url, null, callback);
    }

    /**
     * 删除信息
     * @param id
     */
    @Override public void deleteMessage(@NonNull int id) {

        HttpParams params = new HttpParams();
        params.putHeaders("Set-Cookie", Constant.COOKIE);
        params.put("id", id);
        String url = Constant.SERVER_URL + "message/delete";

        LogUtils.d("请求路径：" + url);

        RxVolley.post(url, params, new HttpCallback() {

            @Override public void onSuccess(String t) {
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (resultBean.isServiceResult()) {
                    LogUtils.d(t);
                    mMessagePresenter.deteleSuccess(resultBean);
                } else {
                    if (resultBean.getResultInfo().equals(Constant.FileCOOKIE)){
                        RxVolleyUtils.getInstance().restartLogin();
                    } else {
                        onFailure(0, resultBean.getResultInfo());
                    }
                }
            }

            @Override public void onFailure(int errorNo, String strMsg) {
                mMessagePresenter.onFailure(errorNo, strMsg);
            }
        });

    }

    /**
     * 该变用户信息
     * @param userId
     * @param messageId
     */

    @Override public void changeStateToService(@NonNull Long userId,@NonNull Long messageId) {
        HttpParams params = new HttpParams();
        params.putHeaders("cookie",Constant.COOKIE);
        params.put("id",userId + "");

        String url = Constant.SERVER_URL + "message/update";

        LogUtils.d("请求路径：" + url);
        RxVolley.post(url, params, new HttpCallback() {
            @Override public void onSuccess(String t) {
                LogUtils.d("更新信息状态成功");
            }

            @Override public void onFailure(int errorNo, String strMsg) {
                mMessagePresenter.onFailure(errorNo, strMsg);
            }
        });
    }

    /**
     * 获取用户未读信息
     * @param userId 用户Id
     */
    @Override public void getUserUnReadMessage(Long userId) {
        String url = Constant.SERVER_URL + "message/getUnread/userId=" + userId;
        LogUtils.w("请求路径:" + url);
       HttpCallback callback = new HttpCallback() {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                mMessagePresenter.onFailure(errorNo, strMsg);
            }

            @Override
            public void onSuccess(String t) {
                LogUtils.d(t);
                ResultBean resultBean = GsonUtils.Instance().fromJson(t, ResultBean.class);
                if (resultBean.isServiceResult()) {
                    mMessagePresenter.onSuccess(resultBean);
                } else {
                    if (resultBean.getResultInfo().equals(Constant.FileCOOKIE)) {
                        RxVolleyUtils.getInstance().restartLogin();
                    } else {
                        onFailure(2, resultBean.getResultInfo());
                    }
                }
            }
        };

        RxVolleyUtils.getInstance().get(url,null,callback);
    }

    public MessageModelImpl(MessagePresenterImpl messagePresenter) {
        this.mMessagePresenter = messagePresenter;
    }
}
