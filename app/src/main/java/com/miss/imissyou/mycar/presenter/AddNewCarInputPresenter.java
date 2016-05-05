package com.miss.imissyou.mycar.presenter;

import com.miss.imissyou.mycar.bean.ResultBean;

/**
 * 添加新车的提交页面
 * Created by Imissyou on 2016/4/18.
 */
public interface AddNewCarInputPresenter {

    /**
     * 保存车辆信息到本地
     */
    void saveCarInfo(String jsonString);

    /**
     * 发送车辆信息到服务器
     * @param result
     */
    void sentCarInfoToService(String result);

    /**
     * 销毁对象退出当前Activity
     *
     */
    void onDestroy();

    /**
     * 加载前一个页面传进来的CarInfo
     * @return
     */
    ResultBean initCarDate();

    void onFailure(int errorNo, String strMsg);
    void onSuccess(String t);
}
