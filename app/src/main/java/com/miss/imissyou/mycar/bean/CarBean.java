package com.miss.imissyou.mycar.bean;

/**
 * PO
 *
 * Created by Imissyou on 2016/4/2.
 */
public class CarBean extends BaseBean {

    private String carID;       //车ID
    private String carBrand;    //车品牌
    private String carType;     //车型号

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }
}
