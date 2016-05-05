package com.miss.imissyou.mycar.model;

/**
 * Created by Imissyou on 2016/5/3.
 */
public interface CarInfoModle {

    /**
     * 从服务器加载车辆信息
     * @param userId  用户ID
     * @param carId   车辆ID
     */
    void loadCarInfoFormService(String userId, String carId);
}
