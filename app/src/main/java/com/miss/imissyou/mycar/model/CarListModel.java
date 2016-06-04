package com.miss.imissyou.mycar.model;

/**
 * 车辆信息
 * 用户获取用户所有
 * Created by Imissyou on 2016/4/20.
 */
public interface CarListModel {

    /**
     * 加载用户所有的车辆数据
     * @param userId
     */
    void loadAllCarInfoData(String userId);
}
