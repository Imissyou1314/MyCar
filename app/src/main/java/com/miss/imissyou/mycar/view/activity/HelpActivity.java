package com.miss.imissyou.mycar.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
    @FindViewById(id = R.id.help_call_TV)
    private TextView call;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_help);
        setContentView(R.layout.activity_help);
    }
    @Override protected void initData() {

    }

    @Override public void addListeners() {
        call.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent callIntent = new Intent();
                callIntent.setAction("Android.intent.action.CALL");
                callIntent.setData(Uri.parse("tel:"+ call.getText()));
                startActivity(callIntent);
            }
        });

        titleView.setTitleText("帮助");
    }
}
