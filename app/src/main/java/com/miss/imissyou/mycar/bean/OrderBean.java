package com.miss.imissyou.mycar.bean;

import com.miss.imissyou.mycar.bean.BaseBean;

import java.util.Date;

/**
 * 订单BeanModle
 * Created by Imissyou on 2016/3/28.
 */
public class OrderBean extends BaseBean {
    /** 预约单ID */
    private String orderID;
    /** 车ID */
    private String carID;
    /** 用户ID */
    private String userID;
    /** 加油站名 */
    private String gasStationName;
    /** 加油站地址 */
    private String gasStationAddress;
    /** 加油站类型*/
    private String gasStationType;
    /** 油类型 */
    private String gasType;
    /** 加油单位 */
    private String gasUnit;
    /** 加油数量*/
    private Integer gasNumber = 0;
    /** 总价格*/
    private float totalPrice;
    /** 订单状态*/
    private int  orderState;
    /** 下单时间*/
    private Date orderTime;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getGasStationName() {
        return gasStationName;
    }

    public void setGasStationName(String gasStationName) {
        this.gasStationName = gasStationName;
    }

    public String getGasStationAddress() {
        return gasStationAddress;
    }

    public void setGasStationAddress(String gasStationAddress) {
        this.gasStationAddress = gasStationAddress;
    }

    public String getGasStationType() {
        return gasStationType;
    }

    public void setGasStationType(String gasStationType) {
        this.gasStationType = gasStationType;
    }

    public String getGasType() {
        return gasType;
    }

    public void setGasType(String gasType) {
        this.gasType = gasType;
    }

    public String getGasUnit() {
        return gasUnit;
    }

    public void setGasUnit(String gasUnit) {
        this.gasUnit = gasUnit;
    }

    public Integer getGasNumber() {
        return gasNumber;
    }

    public void setGasNumber(Integer gasNumber) {
        this.gasNumber = gasNumber;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }
}
