package com.miss.imissyou.mycar.presenter;

import com.amap.api.maps.model.LatLng;
import com.miss.imissyou.mycar.bean.GasStationResultBean;
import com.miss.imissyou.mycar.bean.ResultBean;

/**
 * 导航页面的控制类
 * Created by Imissyou on 2016/6/12.
 */
public interface NaviViewPresenter {

    /**
     * View <===
     *  获取成功
     * @param Tag   标准信息
     * @param resultBean   返回结果
     */
    void loadSuccess(String Tag, ResultBean resultBean);
    /**
     * 获取附近十公里内的加油站
     * @param resultBean  放回结果
     */
    void loadSuccessGasStation(GasStationResultBean resultBean);

    /**
     * View <===
     * 获取失败
     * @param errorNumber   错误码
     * @param errMsg      错误信息
     */
    void loadFail(int errorNumber, String errMsg);

    /**
     * ===>Model
     * 获取附近停车场信息
     * @param latLng   经纬度
     */
    void loadPack(LatLng latLng);

    /**
     * ===>model
     * 获取附近加油站信息
     * @param lon  经纬度
     * @param  lat 纬度
     */
    void loadGasStation(Double lon, Double lat);

    /**
     * ===>model
     * 获取附近维修站信息
     * @param latLng  坐标点
     */
    void loadRepairShop(LatLng latLng);
}
