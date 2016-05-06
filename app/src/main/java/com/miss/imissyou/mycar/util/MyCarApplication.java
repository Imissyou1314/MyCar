package com.miss.imissyou.mycar.util;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义Application
 * Created by Imissyou on 2016/5/5.
 */
public class MyCarApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
