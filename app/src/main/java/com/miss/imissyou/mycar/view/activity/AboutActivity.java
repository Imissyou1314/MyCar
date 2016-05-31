package com.miss.imissyou.mycar.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.ui.TitleFragment;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.FindViewById;

/**
 * 关于
 * Created by Imissyou on 2016/3/23.
 */
public class AboutActivity extends BaseActivity {
    @FindViewById(id = R.id.about_title)
    private TitleFragment titleView;

    @FindViewById(id = R.id.service_ip)
    private EditText serviceInput;
    @FindViewById(id = R.id.sumbit_service)
    Button sumbit;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_about);
    }

    @Override protected void initData() {

    }

    @Override public void addListeners() {
        titleView.setTitleText("关于");
        sumbit.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Constant.SERVER_URL = serviceInput.getText().toString();
            }
        });
    }
}
