package com.miss.imissyou.mycar.presenter;

import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;

/**
 * 添加新车的提交页面
 * Created by Imissyou on 2016/4/18.
 */
public interface AddNewCarInputPresenter {

    /**
     * 发送车辆信息到服务器
     * @param resultBean
     */
    void sentCarInfoToService(CarInfoBean resultBean);

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

    /**
     * 获取扫描的车辆信息
     * @param url
     */
    void loadCar(String url);

    void onFailure(int errorNo, String strMsg);
    void onSuccess(String t);
}
