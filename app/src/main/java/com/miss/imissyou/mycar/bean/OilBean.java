package com.miss.imissyou.mycar.bean;

/**
 * 加油站返回油的基本信息
 * Created by Imissyou on 2016/4/24.
 */
public class OilBean extends BaseBean {

    private String oilType;     //有类型
    private String price;          //油价格

    public String getOilType() {
        return oilType;
    }

    public void setOilType(String oilType) {
        this.oilType = oilType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
