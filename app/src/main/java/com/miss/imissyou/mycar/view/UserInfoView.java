package com.miss.imissyou.mycar.view;

import com.miss.imissyou.mycar.bean.UserBean;

/**
 * Created by Imissyou on 2016/4/26.
 */
public interface UserInfoView extends MainView {

    /**
     * 更新成功后的回调信息
     * @param resultMessage
     */
    void onUpdateSuccess(String resultMessage);

    /**
     * 获取用户的信息
     * @param userBean
     */
    void showResultOnSuccess(UserBean userBean);
}
