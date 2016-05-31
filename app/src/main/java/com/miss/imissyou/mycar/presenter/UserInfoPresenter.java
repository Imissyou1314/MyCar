package com.miss.imissyou.mycar.presenter;

import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.bean.UserBean;
import com.miss.imissyou.mycar.view.UserInfoView;

/**
 * 用户的Presenter
 * Created by Imissyou on 2016/4/26.
 */
public interface UserInfoPresenter extends MainPresenter<UserInfoView>{

    void changeUserInfo(UserBean userBean);

    void onUpdateSuccess(ResultBean resultBean);

}
