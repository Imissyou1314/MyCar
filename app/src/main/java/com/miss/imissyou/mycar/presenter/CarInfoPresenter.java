package com.miss.imissyou.mycar.presenter;

import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.view.CarInfoView;
import com.miss.imissyou.mycar.view.MainView;

/**
 * 加载具体的车辆信息
 * Created by Imissyou on 2016/5/3.
 */
public interface CarInfoPresenter extends MainPresenter<CarInfoView>{

    /**
     * 加载车辆信息
     * @param userId  用户ID
     * @param carId  车辆ID
     */
    void loadCarInfo(Long userId, Long carId);

    /**
     * 控制车辆警报状态
     * @param carId 车辆ID
     */
    void changeCarAlarmState(Long carId);

    /**
     * 控制车辆启动和熄火状态
     * @param carId  车辆Id
     */
    void changeCarState(Long carId);

    /**
     * 设置为当前车辆
     * @param userId 用户Id
     * @param carId 车辆Id
     */
    void setCuurentCar(Long userId, Long carId);

    /**
     * 设置为当前状态成功
     * @param resultBean  回调的结果
     */
    void setCurrentCarSuccess(ResultBean resultBean);

    /**
     * 获取用户当前车辆
     * @param userId  用户ID
     */
    void getCurrentCar(Long userId);
}
