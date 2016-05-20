package com.miss.imissyou.mycar.bean;

import com.amap.api.maps.model.LatLng;

/**
 * 站点信息
 * Created by Imissyou on 2016/5/19.
 */
public abstract class Station extends BaseBean{

    private String stationName;     //站的维修名字
    private String stationTitle;    //站的标题
    private String stationMessage;  //站的介绍

    private LatLng latLng;          //站的经纬度

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getStationMessage() {
        return stationMessage;
    }

    public void setStationMessage(String stationMessage) {
        this.stationMessage = stationMessage;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationTitle() {
        if (null == stationTitle && stationTitle.equals(""))
            stationTitle = stationName;
        return stationTitle;
    }

    public void setStationTitle(String stationTitle) {
        this.stationTitle = stationTitle;
    }
}
