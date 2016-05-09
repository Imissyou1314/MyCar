package com.miss.imissyou.mycar.bean;

import java.util.List;

/**
 * 通过聚合数据获取加油站的信息
 * Created by Imissyou on 2016/5/9.
 */
public class GasStationResultBean extends BaseBean{

    private int resultcode;
    private String reason;
    private List<GasStationBean> data;

    public List<GasStationBean> getData() {
        return data;
    }

    public void setData(List<GasStationBean> data) {
        this.data = data;
    }

    public int getResultcode() {
        return resultcode;
    }

    public void setResultcode(int resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
