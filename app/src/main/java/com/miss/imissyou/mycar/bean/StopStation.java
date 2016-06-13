package com.miss.imissyou.mycar.bean;

/**
 * 停车场的实体类
 * Created by Imissyou on 2016/5/19.
 */
public class StopStation extends Station {

    private Long id;              // 停车场或者维修店面
    private String name;          //场地名
    private String img;           //场地图片
    private String introduce;     //场地介绍
    private String address;       //场地地址
    private String phoneNumber;   //联系方式
    private Double lat;           //经度
    private Double lon;           //纬度

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
