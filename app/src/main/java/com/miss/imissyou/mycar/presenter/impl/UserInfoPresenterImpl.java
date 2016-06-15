package com.miss.imissyou.mycar.presenter.impl;

import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.bean.UserBean;
import com.miss.imissyou.mycar.model.UserInfoModel;
import com.miss.imissyou.mycar.model.impl.UserInfoModelImpl;
import com.miss.imissyou.mycar.presenter.UserInfoPresenter;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.view.UserInfoView;
import com.miss.imissyou.mycar.view.fragment.UserInfoFragment;

/**
 * Created by Imissyou on 2016/4/26.
 */
public class UserInfoPresenterImpl implements UserInfoPresenter{
    private UserInfoView mUserInfoView;
    private UserInfoModel mUserInfoModel;

    public UserInfoPresenterImpl(UserInfoView userInfoFragment) {
        attachView(userInfoFragment);
        mUserInfoModel = new UserInfoModelImpl(this);

    }

    @Override
    public void onFailure(int errorNo, String strMsg) {
        mUserInfoView.hideProgress();
        mUserInfoView.showResultError(errorNo, strMsg);
    }

    @Override
    public void onSuccess(BaseBean resultBean) {
        mUserInfoView.hideProgress();
        mUserInfoView.showResultSuccess((ResultBean) resultBean);
    }

//    @Override
//    public void loadServiceData(BaseBean useBean) {
//
//    }

    @Override public void attachView(UserInfoView view) {
        this.mUserInfoView = view;
        mUserInfoView.showProgress();
    }

    @Override public void detchView() {
        mUserInfoView.hideProgress();
        mUserInfoView = null;
    }

    @Override public void onUpdateSuccess(ResultBean resultBean) {
        UserBean user = GsonUtils.getParam(resultBean,"user", UserBean.class);
        if (user != null) {
            Constant.userBean = user;
        }
        mUserInfoView.onUpdateSuccess(resultBean.getResultInfo());
    }

    @Override
    public void checkSafePassword(CharSequence safePasswordInput) {
        mUserInfoModel.checkSafePassword(safePasswordInput);
    }

    @Override
    public void checkSafePasswordSuccess(ResultBean resultBean) {
        mUserInfoView.showSafePasswordSucess(resultBean);
    }

    @Override
    public void updataUserImage(String Imagepath) {
        mUserInfoModel.updataUserImage(Imagepath);
    }

}
