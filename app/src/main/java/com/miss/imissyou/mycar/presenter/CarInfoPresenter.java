package com.miss.imissyou.mycar.presenter;

import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.view.CarInfoView;
import com.miss.imissyou.mycar.view.MainView;

/**
 * 加载具体的车辆信息控制中心
 * Created by Imissyou on 2016/5/3.
 */
public interface CarInfoPresenter extends MainPresenter<CarInfoView>{

    /**
     * ===> Model
     * 加载车辆信息
     * @param userId  用户ID
     * @param carId  车辆ID
     */
    void loadCarInfo(Long userId, Long carId);

    /**
     * ===>Model
     * 控制车辆警报状态
     * @param carId 车辆ID
     */
    void changeCarAlarmState(Long carId);

    /**
     * ===>Model
     * 控制车辆启动和熄火状态
     * @param carId  车辆Id
     */
    void changeCarState(Long carId);

    /**
     * ===>Model
     * 对当前车辆进行熄火
     * @param carId  车辆ID
     */
    void changeCarStop(Long carId);

    /**
     * ===>Model
     * 设置为当前车辆
     * @param userId 用户Id
     * @param carId 车辆Id
     */
    void setCuurentCar(Long userId, Long carId);

    /**
     *  View <===
     * 设置为当前状态成功
     * @param resultBean  回调的结果
     */
    void setCurrentCarSuccess(ResultBean resultBean);

    /**
     * ===> Model
     * 获取用户当前车辆
     * @param userId  用户ID
     */
    void getCurrentCar(Long userId);

    /**
     * ===>Model
     * 更改车辆车牌号信息
     * @param carInfoBean
     */
    void changeCarPlatNumber(CarInfoBean carInfoBean);
}
