package com.miss.imissyou.mycar.model;

import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.CarInfoBean;

/**
 * 添加车辆操作数据模型
 * Created by Imissyou on 2016/4/18.
 */
public interface AddNewCarInputModel {

    /**
     * 发送车辆信息给服务器
     * @param baseBean
     */
    void sentToService(CarInfoBean baseBean);

    /**
     * 保存车辆信息到本地
     * @param baseBean
     * @param key
     */
    void saveToSPU(BaseBean baseBean, String key);

    /**
     * 获取扫描车辆信息
     * @param url
     */
    void loadCar(String url);
}
