package com.miss.imissyou.mycar.bean;

/**
 * 订单BeanModle
 * Created by Imissyou on 2016/3/28.
 */
public class OrderBean extends BaseBean {


    /** 预约单ID */
    private Long id;
    /** 车ID */
    private Long carId;
    /** 用户ID */
    private Long userId;
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
    private Double price;
    /** 加油数量*/
    private Double number;
    /**定单总价*/
    private Double  amounts;
    /** 订单状态*/
    private Integer state;
    /** 下单时间*/
    private String agreementTime;
    /**订单用户名*/
    private String userName;
    /**车牌号*/
    private String plateNumber;

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Long getId() {
        return id;
    }

    public Long getCarId() {
        return carId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
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

    public Double getAmounts() {
        return amounts;
    }

    public void setAmounts(Double amounts) {
        this.amounts = amounts;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
