package com.miss.imissyou.mycar.bean;

/**
 * 通过聚合数据获取加油站的信息
 * Created by Imissyou on 2016/5/9.
 */
public class GasStationResultBean extends BaseBean{

    private int resultcode;
    private String reason;
    private GasStationList result;

    public GasStationList getResult() {
        return result;
    }

    public void setResult(GasStationList result) {
        this.result = result;
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
