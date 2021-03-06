package com.miss.imissyou.mycar.util;

import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.UserBean;

import java.util.List;

/**
 * 全局常量
 * Created by Imissyou on 2016/4/14.
 */
public class Constant {



    /**
     * 服务器URL
     */
//    public static String SERVER_URL = "http://192.168.1.106:8080/";
    public static String SERVER_URL = "http://www.earltech.cn:8084/";
    public static final int HTTP_OK = 200;

    public static UserBean userBean = new UserBean();        //用户实体类对象
    public static CarInfoBean carBean = new CarInfoBean();   //用户的车辆实体

    /**
     * Music Seriver
     */
    public static final int MUSIC_CLICK_START = 1;
    public static final int MUSIC_SEEK = 0;
    public static final int MUSIC_BUTTON_PAUSE = 2;
    public static final int MUSIC_BUTTON_START = 3;
    public static final int MUSIC_NEXT = 5;
    public static final int MUSIC_PREVIOUS = 4;
    public static final String MUSIC_TIME = "com.missyou.music";
    public static final String Message = "com.missyou.message";

    /**
     * 进行储存用户数据的
     */
    public static String FileCOOKIE = "用户未登录";
    public static String COOKIE = null;
    public static final String UserPassID = "MissYourPass";
    public static final String UserAccountID = "MissYourAccountID";
    public static final String UserPassLength = "passLength";
    public static final String UserBeanID = "userBeanId";


    /**用户设置进行储存的key*/
    public static final String MESSAGEERROR = "ERRORMESSAGE";
    public static final String MESSAGEWARE = "WAREMESSAGE";
    public static final String MESSAGENOREAD = "NOREADMESSAGE";
    public static final String MESSAGEALL = "ALLMESSAGE";
    public static final String MESSAGEMUSIC = "Music";

    public static final String CAR_ID = "car_Id";
    public static final String USER_ID = "user_Id";

    /**
     * 启动应用
     */
    public static final String EXTRA_BUNDLE = "launchBundle";

    /**
     * 提示网络错误信息
     */
    public static int SHOW_ERROR_TOAST = 0xf3;          //提示弹Toast
    public static final int SUCCESS_NO = 0xff;          //提示谈Daolog
    public static final String SUCCESS_TITLE = "操作成功";
    public static final int NETWORK_STATE = -1;
    public static final String NOTNETWORK = "网络连接异常";

    public static final int WARE_USERDO_ERROR = 1;
    public static final String USER_UBLOGIN = "用户没登录";
    public static final int WARE_ERROR = 0;
    public static final String WARE_USER_UNDO = "存在不合法存在";
    public static final String WARE_ERROR_COSTANT = "操作异常警告";

    /**
     * 设置获取加油站的默认选项
     */
    public static int GET_GASSTATION_R = 10000;         //设置半径为10公里最大数值
    public static final String GET_GASSTATION_KEY = "4998d9971059992c8e20577878f5a6d3";

    /**
     * 定位的起点坐标和终点坐标
     */
    public static final String startLatitude = "startLatitude";
    public static final String startLongitude = "startLongitude";
    public static final String endLatitude = "endLatitude";
    public static final String endLongitude = "endLongitude";
    public static final int HAVESTART_Navi = 1;               //设置了起点和目地的导航方式
    public static final int DONTHAVESTART_Navi = 0;         //没有起点的导航方式
    //搜索范围20公里
    public static int SEARCH_RADIO = 20000;
    public static double MyLatitude= 0d;                      //默认起点位置
    public static double MyLongitude = 0d;
    public static String NO_START_NAVI = "NO_START_NAVI";
    public static String GASSTION_PHONE = "13763012723";

    //设置Fragment的Tag
    public final static String FirstAddCarFragment = "FirstAddCarFragment";              //第一次进入页面
    public final static String CarInfoFragment = "CarInfoFragment";                     //车辆信息
    public final static String CarListFragment = "CarListFragment";                     //车辆列表
    public final static String NaviViewFragment = "NaviViewFragment";                   //导航页面
    public final static String LocationMapFragment ="LocationMapFragment";
    public final static String MusicFragment = "MusicFragment";                         //音乐播放页面
    public final static String UserInfoFragment = "UserInfoFragment";
    public final static String WeiZhanChaXunFragment = "WeiZhanChaXunFragment";
    public static final String MAP_GASSTATION_FRAGMENT = "MAP_GASSTATION_FRAGMENT";     //加油站列表表
    public static final String STATION_MAP_FRAGMENT = "STATION_MAP_FRAGMENT";           //站点信息
    public final static String OrderFragment = "OrderFragment";
    public final static String GasStationFragmetn = "GasStationFragmetn";
    public static String StationMapViewFragment = "StationMapViewFragment";
    public final static String SumbitOrderFragment = "SumbitOrderFragment";             //定单列表


    public static final String  MAP_MAINTAIN = "MainTain";          //附近维修站
    public static final String MAP_GASSTATION = "GasStation";       //附近加油站


    public static final String MAP_PARK = "park";                   //附近停车场
}
