package com.miss.imissyou.mycar.ui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miss.imissyou.mycar.R;

/**
 * 自定义Activity 的Title
 * Created by Imissyou on 2016/4/22.
 */
public class TitleFragment extends FrameLayout {

    private LinearLayout gBackTv;
    private TextView textTitleTv;
    public TitleFragment(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.fragment_title,this);

        setUpview();
        gBackTv.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                ((Activity) getContext()).finish(); //退出当前页面
            }
        });
    }

    /**
     * 获取页面控件
     */
    private void setUpview() {
        gBackTv = (LinearLayout) findViewById(R.id.activity_title_back);
        textTitleTv = (TextView) findViewById(R.id.activity_title);
    }

    public void setTitleText(String title) {
        textTitleTv.setText(title);
    }


    public void setBackOnClick(OnClickListener click) {
        gBackTv.setOnClickListener(click);
    }

    public void setBackBackGround(int color) {
        gBackTv.setBackgroundColor(color);
    }
}
