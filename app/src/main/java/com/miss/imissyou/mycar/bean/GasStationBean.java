package com.miss.imissyou.mycar.bean;

import android.graphics.Bitmap;

import java.util.List;

/**
 * 加油站的信息
 * Created by Imissyou on 2016/4/24.
 */
public class GasStationBean extends BaseBean{

    private String UNKONW = "<未知>";

    private int distance;       //距离

    private String stationName; //加油站名

    private String stationType; //加油站的类别

    private List<String> oilType;   //加油站的油型

    private Bitmap stationIcon;     //加油站的图标

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType;
    }

    public String getStationName() {
        if (!stationName.equals(""))
            return stationName;
        return UNKONW;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getDistance() {
        if (distance != 0)
            return distance + "KM";
        return UNKONW;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getOilType() {
        StringBuffer buffer = new StringBuffer();
        for(String str : oilType) {
            buffer.append(str).append(",");
        }

        if (buffer.length() > 0) {
            return buffer.substring(0, buffer.length() -1).toString();
        } else {
            return "<未知>";
        }
    }

    public void setOilType(List<String> oilType) {
        this.oilType = oilType;
    }

    public Bitmap getStationIcon() {
        return stationIcon;
    }

    public void setStationIcon(Bitmap stationIcon) {
        this.stationIcon = stationIcon;
    }
}
