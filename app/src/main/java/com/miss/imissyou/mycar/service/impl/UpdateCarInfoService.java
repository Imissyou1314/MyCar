package com.miss.imissyou.mycar.service.impl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 更新车信息的服务
 *
 * Created by Imissyou on 2016/3/11.
 */
public class UpdateCarInfoService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
