package com.miss.imissyou.mycar.broadcastReceiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;

/**
 * Created by Imissyou on 2016/4/10.
 */
public class ShowNotificationReceiver extends BroadcastReceiver {

    private static final String TAG = "ShowNotificationReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {

        LogUtils.d("ShowNotificationReceiver inReceive");

        //设置点击通知栏的动作为启动另外一个广播
        Intent broadcastIntent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.
                getBroadcast(context, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("这就是通知的头")
                .setTicker("这是通知的ticker")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);

        Log.i("repeat", "showNotification");
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(2, builder.build());

    }
}
