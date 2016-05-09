package com.miss.imissyou.mycar.broadcastReceiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.view.activity.MessageActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * 极光推送广播
 * Created by Imissyou on 2016/5/5.
 */
public class JpushReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        LogUtils.d("JPushReceiver " + intent.getAction());
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
        }else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {          //自定义信息
            LogUtils.d("Title" + bundle.getString(JPushInterface.EXTRA_TITLE));
            LogUtils.d("message" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            //设置点击通知栏的动作为启动另外一个广播
            Intent broadcastIntent = new Intent(context, NotificationReceiver.class);
            broadcastIntent.putExtras(bundle);
            PendingIntent pendingIntent = PendingIntent.
                    getBroadcast(context, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

            builder.setContentTitle(bundle.getString(JPushInterface.EXTRA_TITLE))
                    .setTicker(JPushInterface.EXTRA_MESSAGE)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(android.R.drawable.ic_lock_idle_charging);

            //获取通知管理器对象
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(0, builder.build());

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {      //通知
            //设置点击通知栏的动作为启动另外一个广播
            Intent broadcastIntent = new Intent(context, NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.
                    getBroadcast(context, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

            builder.setContentTitle(bundle.getString(JPushInterface.EXTRA_TITLE))
                    .setTicker(JPushInterface.EXTRA_MESSAGE)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(android.R.drawable.ic_lock_idle_charging);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            System.out.println("用户点击打开了通知");
            // 在这里可以自己写代码去定义用户点击后的行为
            Intent i = new Intent(context, MessageActivity.class);  //自定义打开的界面
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtras(bundle);
            context.startActivity(i);
        } else {
            LogUtils.d("Unhandled intent - " + intent.getAction());
        }
    }
}
