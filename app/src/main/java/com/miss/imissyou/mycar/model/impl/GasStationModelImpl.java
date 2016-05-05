package com.miss.imissyou.mycar.model.impl;

import android.location.Location;

import com.miss.imissyou.mycar.model.GasStationModel;
import com.miss.imissyou.mycar.presenter.GasStationPresenter;
import com.miss.imissyou.mycar.presenter.impl.GasStationPresenterImpl;

/**
 * Created by Imissyou on 2016/4/24.
 */
public class GasStationModelImpl implements GasStationModel{

    private GasStationPresenter gasStation;

    public GasStationModelImpl(GasStationPresenterImpl gasStationPresenter) {
        this.gasStation = gasStationPresenter;
    }

    @Override public void loadData(Location location) {
        //TODO  获取附近的加油站列表
        if (location == null) {
            gasStation.onFailure(0,"位置信息为空");
        } else {
            getGasStation(location);
        }


    }

    /**
     * 加载附近的加油站
     * @param location
     */
    private void getGasStation(Location location) {
    }
}
