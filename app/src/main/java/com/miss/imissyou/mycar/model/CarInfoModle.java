package com.miss.imissyou.mycar.model;

import com.cheshouye.api.client.json.CarInfo;
import com.miss.imissyou.mycar.bean.CarInfoBean;

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
     * @param state  状态码
     * @param carId 车Id
     */
    void changeCarState(int state, Long carId);

    /**
     * 更改为当前车辆信息
     * @param carId 车Id
     * @param userId 用户Id
     */
    void setCurrentCar(Long userId, Long carId);

    /**
     * 获取用户当前车辆
     * @param userId  用户ID
     */
    void getUserCurrentCar(Long userId);

    /****
     * 控制车当前熄火状态
     * @param carId 车Id
     */
    void changeCartoStop(Long carId);

    /**
     * 更改车牌号
     * @param carInfoBean  车辆实体
     */
    void changeCarPlatNumber(CarInfoBean carInfoBean);
}
