package com.miss.imissyou.mycar.model;

/**
 * 根据用户Id和车辆Id获取当前车辆信息
 * Created by Imissyou on 2016/5/3.
 */
public interface CarInfoModle {

    /**
     * 从服务器加载车辆信息
     * @param userId  用户ID
     * @param carId   车辆ID
     */
    void loadCarInfoFormService(Long userId, Long carId);

    /**
     * 控制车当前的警报状态
     * @param state     警报状态
     * @param carId     车Id
     */
    void changeCarAlarmState(int state, Long carId);

    /**
     * 控制车当前起动和熄火状态
     * @param state
     * @param carId
     */
    void changeCarState(int state, Long carId)    ;
}
