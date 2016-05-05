package com.miss.imissyou.mycar.model.impl;

import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.UserBean;
import com.miss.imissyou.mycar.model.MessageModle;
import com.miss.imissyou.mycar.presenter.MessagePresenter;
import com.miss.imissyou.mycar.presenter.impl.MessagePresenterImpl;

/**
 * 消息获取的实现层
 * Created by Imissyou on 2016/5/2.
 */
public class MessageModelImpl implements MessageModle {
    private MessagePresenter mMessagePresenter;

    @Override public void loadMessageForService(UserBean userBean) {
        LogUtils.d(userBean.toString());
    }

    public MessageModelImpl(MessagePresenterImpl messagePresenter) {
        this.mMessagePresenter = messagePresenter;
    }
}
