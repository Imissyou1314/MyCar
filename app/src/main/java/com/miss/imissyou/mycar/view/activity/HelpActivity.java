package com.miss.imissyou.mycar.view.activity;

import android.os.Bundle;

import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.ui.TitleFragment;
import com.miss.imissyou.mycar.util.FindViewById;

/**
 * 帮助页面
 * Created by Imissyou on 2016/3/23.
 */
public class HelpActivity extends BaseActivity{
    @FindViewById(id = R.id.help_title)
    private TitleFragment titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_help);
        setContentView(R.layout.activity_help);
    }

    @Override public void addListeners() {
        titleView.setTitleText("帮助");
    }
}
