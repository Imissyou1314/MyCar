package com.miss.imissyou.mycar.view;


/**
 * Created by Imissyou on 2016/4/26.
 */
public interface UserInfoView extends MainView {

    /**
     * 更新成功后的回调信息
     * @param resultMessage
     */
    void onUpdateSuccess(String resultMessage);

}
