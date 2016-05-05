package com.miss.imissyou.mycar.util;

import android.app.Application;
import android.content.Context;

/**
 * 获取系统Context
 * Created by Imissyou on 2016/3/23.
 */
public class MissApplication extends Application {

    private static Context mContext;

    @Override public void onCreate() {
        super.onCreate();
        this.mContext = getApplicationContext();
    }

    /**
     * 获取系统Context
     * @return mContext  Context
     */
    public static Context getContext() {

        return mContext;
    }
}
