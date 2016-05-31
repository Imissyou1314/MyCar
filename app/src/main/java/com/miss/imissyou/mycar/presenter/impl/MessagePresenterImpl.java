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

    }

    @Override public void attachView(MessageView view) {
        this.mMessageView = view;
        mMessageView.showProgress();
    }

    @Override public void detchView() {
        mMessageView = null;
    }

    @Override public void deteleMessage(int id) {

        mMessageModle.deleteMessage(id);

    }

    @Override public void deteleSuccess(ResultBean resultBean) {
        if (resultBean.isServiceResult()) {
            mMessageView.deleteSucces("删除成功");
        } else {
            mMessageView.deleteSucces("删除失败");
        }
    }

    @Override public void changeStateToService(String id) {
        if (null != Constant.userBean.getId()){
            mMessageModle.changeStateToService(Constant.userBean.getId() ,id);
        }
    }

    @Override public void getUserUnReadMessage() {
        if (null != Constant.userBean && null != Constant.userBean.getId()) {
            mMessageModle.getUserUnReadMessage(Constant.userBean.getId());
        } else {
            mMessageView.showResultError(1, "用户没登录");
        }
    }
    @Override public void getUserAllMessage() {
        if (null != Constant.userBean && null != Constant.userBean.getId()) {
            mMessageModle.getUserAllMessage(Constant.userBean.getId());
        } else {
            mMessageView.showResultError(1, "用户没登录");
        }
    }
}
