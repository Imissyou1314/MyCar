package com.miss.imissyou.mycar.service.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;



/**
 * Created by Imissyou on 2016/4/14.
 */
public class MyBroadCastService extends BroadcastReceiver{


    @Override public void onReceive(Context context, Intent intent) {
        int type = intent.getIntExtra("type",0);
        switch (type) {
            case 0:
                int time = intent.getIntExtra("time",0);

        }
    }
}
