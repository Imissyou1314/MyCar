package com.miss.imissyou.mycar.presenter.impl;

import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.bean.UserBean;
import com.miss.imissyou.mycar.model.MessageModle;
import com.miss.imissyou.mycar.model.impl.MessageModelImpl;
import com.miss.imissyou.mycar.presenter.MessagePresenter;
import com.miss.imissyou.mycar.util.Constant;
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

    @Override public void onFailure(int errorNo, String strMsg) {
        mMessageView.hideProgress();
        mMessageView.showResultError(errorNo, strMsg);
    }

    @Override
    public void onSuccess(BaseBean resultBean) {
        mMessageView.hideProgress();
        mMessageView.showResultSuccess((ResultBean) resultBean);
    }

    @Override public void loadServiceData(BaseBean useBean) {

        if (Constant.userBean == null) {
            this.onFailure(0, "用户没有登录");
            return;
        }
        mMessageModle.loadMessageForService();
    }

    @Override public void attachView(MessageView view) {
        this.mMessageView = view;
        mMessageView.showProgress();
    }

    @Override public void detchView() {
        mMessageView = null;
    }

    @Override public void deteleMessage(String id) {
        if (null == id || id.equals("")) {
            onFailure(0, "删除数据不存在");
        }
        mMessageModle.deleteMessage(id);

    }

    @Override public void deteleSuccess(ResultBean resultBean) {
        if (resultBean.isServiceResult()) {
            mMessageView.deleteSucces("删除成功");
        } else {
            mMessageView.deleteSucces("删除失败");
        }
    }
}
