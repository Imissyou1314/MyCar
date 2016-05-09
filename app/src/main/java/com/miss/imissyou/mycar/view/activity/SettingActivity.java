package com.miss.imissyou.mycar.view.activity;

import android.os.Bundle;

import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.ui.TitleFragment;
import com.miss.imissyou.mycar.ui.ToggleButton;
import com.miss.imissyou.mycar.util.FindViewById;

/**
 * Created by Imissyou on 2016/3/23.
 */
public class SettingActivity extends BaseActivity{

    @FindViewById(id = R.id.setting_allMessage_button)
    private ToggleButton allMessageBtn;
    @FindViewById(id = R.id.setting_errorMessage_button)
    private ToggleButton errorMessageBtn;
    @FindViewById(id = R.id.setting_wareMessage_button)
    private ToggleButton wareMessageBtn;
    @FindViewById(id = R.id.stting_title)
    private TitleFragment titleView;


    @Override protected void initData() {

    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_setting);

    }

    @Override public void addListeners() {
        titleView.setTitleText("设置");
    }
}
