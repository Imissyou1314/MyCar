package com.miss.imissyou.mycar.bean;

import java.util.Map;

/**
 * 加油站的信息
 * Created by Imissyou on 2016/4/24.
 */
public class GasStationBean extends BaseBean{

    private String name;        //加油站名称
    private String area;        //城市邮编
    private String areaname;    //城市区域
    private String address;     //加油站地址
    private String brandname;   //运营商类型
    private String type;        //加油站类型
    private String discount;    //是否打折加油站
    private double lat;         //百度地图纬度
    private double lon;         //百度地图经度

    private String fwlsmc;      //加油卡信息
    private String distance;    //与坐标的距离，单位M
    private Map<String, String> price;   //省控基准油价
    private Map<String, String> gastprice;  //加油站油价（仅供参考，实际以加油站公布价为准）

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getFwlsmc() {
        return fwlsmc;
    }

    public void setFwlsmc(String fwlsmc) {
        this.fwlsmc = fwlsmc;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getGastprice() {
        return gastprice;
    }

    public void setGastprice(Map<String, String> gastprice) {
        this.gastprice = gastprice;
    }

    public Map<String, String> getPrice() {
        return price;
    }

    public void setPrice(Map<String, String> price) {
        this.price = price;
    }
}
