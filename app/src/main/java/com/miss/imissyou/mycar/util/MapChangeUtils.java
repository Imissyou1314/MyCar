package com.miss.imissyou.mycar.util;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.model.LatLng;

/**
 * 进行经纬度装换
 * Created by 青玉 on 2016/5/28.
 */
public class MapChangeUtils {

    private static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    /**
     * 中国正常坐标系GCJ02协议的坐标，转到 百度地图对应的 BD09 协议坐标
     *
     * @param lat
     * @param lng
     */
    public static void Convert_GCJ02_To_BD09(double lat, double lng) {
        double x = lng, y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        lng = z * Math.cos(theta) + 0.0065;
        lat = z * Math.sin(theta) + 0.006;
    }

    public static double Convert_GCJ02_To_BD09_Lat(double lat, double lng) {
        double x = lng, y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        lng = z * Math.cos(theta) + 0.0065;
        lat = z * Math.sin(theta) + 0.006;
        return lat;
    }

    public static double Convert_GCJ02_To_BD09_Lng(double lat, double lng) {
        double x = lng, y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        lng = z * Math.cos(theta) + 0.0065;
        lat = z * Math.sin(theta) + 0.006;
        return lng;
    }

    /**
     * 百度地图对应的 BD09 协议坐标，转到 中国正常坐标系GCJ02协议的坐标
     * @param lat
     * @param lng
     * @param activity
     */
    public static LatLng Convert_BD09_To_GCJ02(double lat, double lng, FragmentActivity activity) {
        double x = lng - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        lng = z * Math.cos(theta);
        lat = z * Math.sin(theta);
        return null;
    }

    /**
     * 百度经纬度转高德经纬度
     * @param lat
     * @param lon
     * @param mContext
     * @return
     */
    public static LatLng Convert_BD0911_TO_GCJ02(double lat, double lon, Context mContext) {
        LatLng latLng = new LatLng(lat, lon);
        return Convert_BD0911_TO_GCJ02(latLng, mContext);
    }

    private static LatLng Convert_BD0911_TO_GCJ02(LatLng latLng,Context mContext) {
        return new CoordinateConverter(mContext)
                .from(CoordinateConverter.CoordType.BAIDU)
                .coord(latLng)
                .convert();
    }

}
