package com.miss.imissyou.mycar.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.MainActivity;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.SystemUtils;
import com.miss.imissyou.mycar.view.activity.MessageActivity;

/**
 * 系统通知提醒
 * 如果APP后台存活就进入MessageActivity
 * APP退出后台就进入MainActivity
 * Created by Imissyou on 2016/4/10.
 */
public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (SystemUtils.isAppAlive(context, "com.miss.imissyou.mycar")) {
            //如果存活的话，MessageActivity，但要考虑一种情况，就是app的进程虽然仍然在
            //但Task栈已经空了，比如用户点击Back键退出应用，但进程还没有被系统回收，如果直接启动
            //MessageActivity,再按Back键就不会返回MainActivity了。所以在启动
            //MessageActivity，要先启动MainActivity。
            LogUtils.i("NotificationReceiver the app process is alive");
            Intent mainIntent = new Intent(context, MainActivity.class);
            //将MainAtivity的launchMode设置成SingleTask, 或者在下面flag中加上Intent.FLAG_CLEAR_TOP,
            //如果Task栈中有MainActivity的实例，就会把它移到栈顶，把在它之上的Activity都清理出栈，
            //如果Task栈不存在MainActivity实例，则在栈顶创建
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Intent detailIntent = new Intent(context, MessageActivity.class);

            Intent[] intents = {mainIntent, detailIntent};

            context.startActivities(intents);
        }else {
            //如果app进程已经被杀死，先重新启动app，将MessageActivity的启动参数传入Intent中，参数经过
            //MainActivity传入MessageActivity，此时app的初始化已经完成，在MainActivity中就可以根据传入             //参数跳转到DetailActivity中去了
            LogUtils.i("NotificationReceiver the app process is dead重新启动App");
            Intent launchIntent = context.getPackageManager().
                    getLaunchIntentForPackage("com.miss.imissyou.mycar");
            launchIntent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

            launchIntent.putExtra(Constant.EXTRA_BUNDLE, (int[]) null);
            context.startActivity(launchIntent);
        }
    }
}
