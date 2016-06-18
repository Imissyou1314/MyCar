package com.miss.imissyou.mycar.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;

/**
 * 自定义Activity 的Title
 * Created by Imissyou on 2016/4/22.
 */
public class FragmentTitleFragment extends FrameLayout {

    private LinearLayout fragmentgBackTv;
    private TextView textTitleTv;
    public FragmentTitleFragment(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.fragment_fragment_title,this);

        setUpview();
    }

    /**
     * 获取页面控件
     */
    private void setUpview() {
        fragmentgBackTv = (LinearLayout) findViewById(R.id.fragment_title_back);
        textTitleTv = (TextView) findViewById(R.id.fragment_title);
    }

    public void setTitleText(String title) {
        textTitleTv.setText(title);
    }


    public void setBackOnClick(OnClickListener click) {
        LogUtils.d("进入这里===>");
        fragmentgBackTv.setOnClickListener(click);
        LogUtils.d("退出这里<====");
    }

    public void setBackBackGround(int color) {
        fragmentgBackTv.setBackgroundColor(color);
    }
}
