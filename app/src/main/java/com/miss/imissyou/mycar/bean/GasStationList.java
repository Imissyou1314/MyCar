package com.miss.imissyou.mycar.bean;

import java.util.List;

/**
 * 解析加油站信息数据的接口
 * Created by Imissyou on 2016/5/19.
 */
public class GasStationList {

    private List<GasStationBean> data;

    public List<GasStationBean> getData() {
        return data;
    }

    public void setData(List<GasStationBean> data) {
        this.data = data;
    }
}
