package com.miss.imissyou.mycar.bean;

/**
 * 车基本信息 Bean
 * Created by Imissyou on 2016/3/28.
 */
public class CarInfoBean extends BaseBean {

    private String id;                      //车ID
    private long userId;
    private String brand;                   //车品牌
    private String models;                  //品牌型号
    private String plateNumber;             //车牌号
    private String mark;                    //图片地址
    private String vin;                       //车架号
    private String engineNumber;              //发动机号
    private String rank;                    //车身等级
    private long milleage;                  //里程数
    private double oilBox;
    private double oil;
    private double temperature;             //温度
    private String enginProperty;           //发动机性能
    private String transmission;            //变速器性能
    private String carLight;                //车灯性能
    private String carState;                //车状态
    private String carAlarm;                //车警报

    /**
     * 警告信息
     */
    private boolean alarmMessage;           //是否发送行警报信息
    private boolean propertyMessage;        //是否发送行性能信息
    private boolean stateMessage;           //是否发送车状态信息
    private boolean currentCar;             //是否当前车辆

    private double lon;                     //经度
    private double lat;                     //纬度

    public boolean isAlarmMessage() {
        return alarmMessage;
    }

    public void setAlarmMessage(boolean alarmMessage) {
        this.alarmMessage = alarmMessage;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCarAlarm() {
        return carAlarm;
    }

    public void setCarAlarm(String carAlarm) {
        this.carAlarm = carAlarm;
    }

    public String getCarLight() {
        return carLight;
    }

    public void setCarLight(String carLight) {
        this.carLight = carLight;
    }

    public String getCarState() {
        return carState;
    }

    public void setCarState(String carState) {
        this.carState = carState;
    }

    public boolean isCurrentCar() {
        return currentCar;
    }

    public void setCurrentCar(boolean currentCar) {
        this.currentCar = currentCar;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getEnginProperty() {
        return enginProperty;
    }

    public void setEnginProperty(String enginProperty) {
        this.enginProperty = enginProperty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public long getMilleage() {
        return milleage;
    }

    public void setMilleage(long milleage) {
        this.milleage = milleage;
    }

    public String getModles() {
        return models;
    }

    public void setModles(String modles) {
        this.models = modles;
    }

    public double getOil() {
        return oil;
    }

    public void setOil(double oil) {
        this.oil = oil;
    }

    public double getOilBox() {
        return oilBox;
    }

    public void setOilBox(long oilBox) {
        this.oilBox = oilBox;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public boolean isPropertyMessage() {
        return propertyMessage;
    }

    public void setPropertyMessage(boolean propertyMessage) {
        this.propertyMessage = propertyMessage;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public boolean isStateMessage() {
        return stateMessage;
    }

    public void setStateMessage(boolean stateMessage) {
        this.stateMessage = stateMessage;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
