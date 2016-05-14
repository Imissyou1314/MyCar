package com.miss.imissyou.mycar.util;

import com.miss.imissyou.mycar.bean.UserBean;

/**
 * 全局常量
 * Created by Imissyou on 2016/4/14.
 */
public class Constant {

    /**服务器URL*/
    public static final String SERVER_URL = "http://192.168.1.101:8080/";

    public static final String GASSTION_BRAND_ZHONGSHIYOU ="中石油";
    public static final String GASSTION_BRAND_ZHONGSHIHUAN = "中石化";

    public static UserBean userBean = new UserBean();        //用户实体类对象

    /**
     * Music Seriver
     */
    public static final int MUSIC_CLICK_START =1;
    public static final int MUSIC_SEEK=0;
    public static final int MUSIC_BUTTON_PAUSE =2;
    public static final int MUSIC_BUTTON_START=3;
    public static final int MUSIC_NEXT=5;
    public static final int MUSIC_PREVIOUS =4;
    public static final String MUSIC_TIME = "com.missyou.music";

    /**
     * 进行储存用户数据的
     */
    public static String COOKIE = null;
    public static final String UserPassID = "MissYourPass";
    public static final String UserAccountID = "MissYourAccountID";
    public static final String UserPassLength = "passLength";

    public static final String CAR_ID = "car_Id";
    public static final String USER_ID = "user_Id";

    /**启动应用*/
    public static final String EXTRA_BUNDLE = "launchBundle";

    /**
     * 提示网络错误信息
     */
    public static final int NETWORK_STATE = -1;
    public static final String NOTNETWORK = "网络连接异常";



    /**
     * 设置获取加油站的默认选项
     */
    public static int GET_GASSTATION_R = 10000;         //设置半径为10公里
    public static final String GET_GASSTATION_KEY = "key";

    /**
     * 定位的起点坐标和终点坐标
     */
    public static final String startLatitude = "startLatitude";
    public static final String startLongitude = "startLongitude";
    public static final String endLatitude = "endLatitude";
    public static final String endLongitude = "endLongitude";
}
