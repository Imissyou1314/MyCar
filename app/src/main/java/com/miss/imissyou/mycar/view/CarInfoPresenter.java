package com.miss.imissyou.mycar.view;

import com.miss.imissyou.mycar.presenter.MainPresenter;

/**
 * 加载具体的车辆信息
 * Created by Imissyou on 2016/5/3.
 */
public interface CarInfoPresenter extends MainPresenter<MainView>{

    /**
     * 加载车辆信息
     * @param userId  用户ID
     * @param carId  车辆ID
     */
    void loadCarInfo(String userId, String carId);

}
