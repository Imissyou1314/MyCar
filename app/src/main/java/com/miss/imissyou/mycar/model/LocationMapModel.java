package com.miss.imissyou.mycar.model;

/**
 * Created by Imissyou on 2016/5/19.
 */
public interface LocationMapModel {

    /**
     * 获取加油站信息
     */
    void loadOilStation();

    /**
     * 获取停车场信息
     */
    void loadStopStation();

    /**
     * 获取维修站的信息
     */
    void loadServiceStation();
}
