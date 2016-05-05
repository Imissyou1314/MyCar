package com.miss.imissyou.mycar.model;

import android.location.Location;

/**
 *
 * 加油站Model的接口
 * Created by Imissyou on 2016/4/24.
 */
public interface GasStationModel{

    /**
     * 加载加油站列表
     * @param location
     */
    void loadData(Location location);



}
