package com.miss.imissyou.mycar.view.activity;

import android.os.Bundle;

import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.ui.TitleFragment;
import com.miss.imissyou.mycar.util.FindViewById;
import com.miss.imissyou.mycar.util.SPUtils;
import com.miss.imissyou.mycar.view.BackHandledInterface;
import com.miss.imissyou.mycar.view.fragment.BaseFragment;
import com.miss.imissyou.mycar.view.fragment.SettingFragment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 设置页面
 * Created by Imissyou on 2016/5/22.
 */
public class SettingActivity extends BaseActivity implements BackHandledInterface {

    @FindViewById(id = R.id.stting_title)
    private TitleFragment titleView;

    private boolean isQuit;
    private Timer timer = new Timer();

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_setting);
        SPUtils.init(this);

    }

    @Override protected void initData() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.setting_page, new SettingFragment())
                .commit();
    }

    @Override public void addListeners() {
        titleView.setTitleText("设置");
    }

    @Override public void onBackPressed() {
        if (isQuit == false) {
            isQuit = true;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.setting_page, new SettingFragment())
                    .commit();
            TimerTask task = null;
            task = new TimerTask() {
                @Override
                public void run() {
                    isQuit = false;
                }
            };
            timer.schedule(task, 2000);
        } else {
            finish();
        }

    }

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {
        //Todo
        LogUtils.d("================>");
    }
}
