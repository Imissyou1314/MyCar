package com.miss.imissyou.mycar.util;

import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.UserBean;

/**
 * 全局常量
 * Created by Imissyou on 2016/4/14.
 */
public class Constant {

    /**
     * 服务器URL
     */
    public static String SERVER_URL = "http://192.168.1.106:8080/";
    public static final int HTTP_OK = 200;

    public static final String GASSTION_BRAND_ZHONGSHIYOU = "中石油";
    public static final String GASSTION_BRAND_ZHONGSHIHUAN = "中石化";

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

    /**
     * 进行储存用户数据的
     */
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
    public static int GET_GASSTATION_R = 10000;         //设置半径为50公里
    public static final String GET_GASSTATION_KEY = "b752fa0f5e45383bb66b8b8758040c72";

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
}
