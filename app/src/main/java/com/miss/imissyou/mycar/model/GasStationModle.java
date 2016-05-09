package com.miss.imissyou.mycar.model;

/**
 * 请求聚合数据获取加油站的接口
 * Created by Imissyou on 2016/5/9.
 */
public interface GasStationModle {

    /**
     * 请求聚合数据的接口
     *
     * @param lon  经纬(如:121.538123)
     * @param lat  经纬(如:121.538123)
     * @param r    搜索范围，单位M，默认3000，最大10000
     * @param page 页数,默认1
     * @param key  应用APPKEY(应用详细页查询)
     * @param format
     */
    void loadGasStationData(double lon, double lat, int r, int page, String key, int format);
}
