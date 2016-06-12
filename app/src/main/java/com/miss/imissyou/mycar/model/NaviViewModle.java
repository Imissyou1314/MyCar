package com.miss.imissyou.mycar.model;

import com.amap.api.maps.model.LatLng;

/**
 * Created by Imissyou on 2016/6/12.
 */
public interface NaviViewModle {

    /**
     * 获取附近停车场信息
     * @param latLng   经纬度
     */
    void loadPack(LatLng latLng);

    /**
     * 获取附近加油站信息
     * @param latLng  经纬度
     */
    void loadGasStation(LatLng latLng);

    /**
     * 获取附近维修站信息
     * @param latLng
     */
    void loadRepairShop(LatLng latLng);


}
