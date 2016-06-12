package com.miss.imissyou.mycar.presenter;

import com.amap.api.maps.model.LatLng;
import com.miss.imissyou.mycar.bean.ResultBean;

/**
 * 导航页面的控制类
 * Created by Imissyou on 2016/6/12.
 */
public interface NaviViewPresenter {

    /**
     *  获取成功
     * @param Tag   标准信息
     * @param resultBean   返回结果
     */
    void loadSuccess(String Tag, ResultBean resultBean);

    /**
     * 获取失败
     * @param errorNumber   错误码
     * @param errMsg      错误信息
     */
    void loadFail(int errorNumber, String errMsg);

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
