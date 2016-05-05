package com.miss.imissyou.mycar.presenter.impl;

import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.bean.UserBean;
import com.miss.imissyou.mycar.model.MessageModle;
import com.miss.imissyou.mycar.model.impl.MessageModelImpl;
import com.miss.imissyou.mycar.presenter.MessagePresenter;
import com.miss.imissyou.mycar.view.MessageView;
import com.miss.imissyou.mycar.view.activity.MessageActivity;

/**
 * Created by Imissyou on 2016/5/2.
 */
public class MessagePresenterImpl implements MessagePresenter {

    private MessageView mMessageView;
    private MessageModle mMessageModle;

    public MessagePresenterImpl(MessageActivity messageActivity) {
        attachView(messageActivity);
        mMessageModle = new MessageModelImpl(this);
    }

    @Override
    public void onFailure(int errorNo, String strMsg) {
        mMessageView.hideProgress();
        mMessageView.showResultError(errorNo, strMsg);
    }

    @Override
    public void onSuccess(BaseBean resultBean) {
        mMessageView.hideProgress();
        mMessageView.showResultSuccess((ResultBean) resultBean);
    }

    @Override
    public void loadServiceData(BaseBean useBean) {
        if (useBean == null) {
            onFailure(0, "数据为空");
            return;
        }
        mMessageModle.loadMessageForService((UserBean) useBean);
    }

    @Override
    public void attachView(MessageView view) {
        this.mMessageView = view;
        mMessageView.showProgress();
    }

    @Override
    public void detchView() {
        mMessageView = null;
    }
}
