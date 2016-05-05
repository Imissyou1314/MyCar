package com.miss.imissyou.mycar.service.impl;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.lidroid.xutils.util.LogUtils;

/**
 * 启动监听后台更新的服务
 * Created by Imissyou on 2016/4/7.
 */
public class PollService extends IntentService{
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    private static final String TAG="PollService";
    private static final long POLL_INTERVAL = 1000 * 15;


    public PollService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isNetWorkAvailabel = cm.getBackgroundDataSetting()
                && cm.getAllNetworkInfo() != null;
        /**没有网络连接*/
        if (!isNetWorkAvailabel) return;
        LogUtils.d("Miss  PollService");
    }

    /**
     * 使用AlarmManager 推时运行服务
     * @param context
     * @param isOn
     */
    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent intent = new Intent(context,PollService.class);
        PendingIntent pi = PendingIntent.getService(context,0,intent,0);

        AlarmManager alarmManger = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);

        if (isOn) {
            alarmManger.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), POLL_INTERVAL, pi);
        } else {
            alarmManger.cancel(pi);
            pi.cancel();
        }
    }
}
