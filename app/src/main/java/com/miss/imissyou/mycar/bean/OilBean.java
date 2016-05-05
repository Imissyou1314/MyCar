package com.miss.imissyou.mycar.bean;

/**
 * 加油站返回油的基本信息
 * Created by Imissyou on 2016/4/24.
 */
public class OilBean extends BaseBean {

    private String oilType;     //有类型
    private int price;          //油价格

    public String getOilType() {
        return oilType;
    }

    public void setOilType(String oilType) {
        this.oilType = oilType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
