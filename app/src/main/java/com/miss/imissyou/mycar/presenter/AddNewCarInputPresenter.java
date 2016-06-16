package com.miss.imissyou.mycar.presenter;

import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;

/**
 * 添加新车的提交控制中心
 * Created by Imissyou on 2016/4/18.
 */
public interface AddNewCarInputPresenter {

    /**
     * 发送车辆信息到服务器
     * @param resultBean
     *
     *  ===> Model
     */
    void sentCarInfoToService(CarInfoBean resultBean);

    /**
     * 销毁对象退出当前Activity
     *
     */
    void onDestroy();

    /**
     *  ===> Model
     * 获取扫描的车辆信息
     * @param url  扫描获取的连接
     */
    void loadCar(String url);

    /**
     * 获取消息出错    View   <==
     * @param errorNo
     * @param strMsg
     */
    void onFailure(int errorNo, String strMsg);

    void onSuccess(String t);

    /**
     *  View <==
     * 添加成功车辆
     * @param t  添加车辆成功信息
     */
    void onAddCarSuccess(String t);
}
