package com.miss.imissyou.mycar.bean;

import java.util.Date;

/**
 * 订单BeanModle
 * Created by Imissyou on 2016/3/28.
 */
public class OrderBean extends BaseBean {


    /** 预约单ID */
    private String id;
    /** 车ID */
    private String carId;
    /** 用户ID */
    private String userId;
    /** 加油站名 */
    private String stationName;
    /** 加油站地址 */
    private String address;
    /** 加油站类型*/
    private String brandName;
    /** 油类型 */
    private String type;
    /** 加油单位 */
    private String units;
    /** 价格*/
    private float price;
    /** 加油数量*/
    private Integer number;
    /**定单总价*/
    private int  amounts;
    /** 订单状态*/
    private Integer state;
    /** 下单时间*/
    private String agreementTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAgreementTime() {
        return agreementTime;
    }

    public void setAgreementTime(String agreementTime) {
        this.agreementTime = agreementTime;
    }

    public int getAmounts() {
        return amounts;
    }

    public void setAmounts(int amounts) {
        this.amounts = amounts;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
