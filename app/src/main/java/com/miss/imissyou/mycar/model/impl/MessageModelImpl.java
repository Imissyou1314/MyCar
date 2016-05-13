package com.miss.imissyou.mycar.model.impl;

import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.bean.UserBean;
import com.miss.imissyou.mycar.model.MessageModle;
import com.miss.imissyou.mycar.presenter.MessagePresenter;
import com.miss.imissyou.mycar.presenter.impl.MessagePresenterImpl;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;

/**
 * 消息获取的实现层
 * Created by Imissyou on 2016/5/2.
 */
public class MessageModelImpl implements MessageModle {
    private MessagePresenter mMessagePresenter;

    @Override public void loadMessageForService() {

        String url = Constant.SERVER_URL +
                "message/getAll/userId=" +
                Constant.userBean.getId();

        LogUtils.d("请求路径：" + url);

        RxVolley.get(url, new HttpCallback() {

            @Override public void onSuccess(String t) {
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (resultBean == null) {
                    onFailure(0, "解析数据异常");
                    return;
                }
                mMessagePresenter.onSuccess(resultBean);
            }

            @Override public void onFailure(int errorNo, String strMsg) {
                if (errorNo == Constant.NETWORK_STATE)
                    strMsg = Constant.NOTNETWORK;
                mMessagePresenter.onFailure(errorNo, strMsg);
            }
        });
    }

    @Override public void deleteMessage(String id) {

        HttpParams params = new HttpParams();
        params.putHeaders("cookie", Constant.COOKIE);
        params.put("id", id);
        String url = Constant.SERVER_URL + "message/delete";

        LogUtils.d("请求路径：" + url);

        RxVolley.post(url, params, new HttpCallback() {

            @Override public void onSuccess(String t) {
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (resultBean != null)
                    mMessagePresenter.onSuccess(resultBean);
            }

            @Override public void onFailure(int errorNo, String strMsg) {
                mMessagePresenter.onFailure(errorNo, strMsg);
            }
        });

    }

    public MessageModelImpl(MessagePresenterImpl messagePresenter) {
        this.mMessagePresenter = messagePresenter;
    }
}
