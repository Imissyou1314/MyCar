package com.miss.imissyou.mycar.util.zxing.camera;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Service的工具类
 * Created by Imissyou on 2016/8/22.
 */
public class ServiceUtils {

    private static ServiceUtils utils;
    private ServiceUtils(){

    }

    /**
     * 单利构造方法
     * @return
     */
    public static ServiceUtils  Instance() {
        if (null == utils)
            utils = new ServiceUtils();
        return utils;
    }


    /**
     * 查找指定服务器是否运行
     * @param mContext
     * @param className
     * @return true if service Running
     *         false if service Not running
     */
    public Boolean isServiceRunning(Context mContext, String className){
        ActivityManager manager = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo serviceInfo : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (className.equals(serviceInfo.service.getClassName()))
                return true;
        }
        return false;
    }
}
