package com.miss.imissyou.mycar.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miss.imissyou.mycar.R;

/**
 * Created by Administrator on 2016-06-10.
 */
public class LinearText extends FrameLayout {

    private TextView title;         //标题
    private TextView message;       //信息
    private LinearLayout listOnClick;    //监听点击

    public LinearText(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        LayoutInflater.from(context).inflate(R.layout.fragment_linear_text, this);
        setupView();
    }


    /**
     * 初始化控件
     */
    private void setupView() {
        title = (TextView) findViewById(R.id.linearTiex_title_tv);
        message = (TextView) findViewById(R.id.linearText_showMessage_tv);
        listOnClick = (LinearLayout) findViewById(R.id.linearText_linear_Linear);


    }

    /**
     * 设置标题
     * @param titleStr  标题
     * @return this
     */
    public LinearText setTitle(String titleStr) {
        if (null != title) {
            title.setText(titleStr);
        }
        return this;
    }

    /**
     * 设置信息
     * @param messageStr  信息
     * @return  this
     */
    public LinearText setMessage(String messageStr) {
        if (null != message) {
            message.setText(messageStr);
        }
        return this;
    }

    /**
     * 设置信息意思
     * @param resId  资源ID
     * @return this
     */
    public LinearText setMessageColor(int resId) {
        if (null != message) {
            message.setTextColor(getResources().getColor(resId));
        }
        return this;
    }

    /**
     * 设置标题意思
     * @param resId  资源ID
     * @return this
     */
    public LinearText setTitleColor(int resId) {
        if (null != title) {
            title.setTextColor(getResources().getColor(resId));
        }
        return this;
    }

    /**
     * 设置标题大小
     * @param size  大小
     * @return  this
     */
    public LinearText setTitleSize(float size) {
        if (null != title) {
            title.setTextSize(size);
        }
        return this;
    }

    /**
     * 设置信息大小
     * @param size  大小
     * @return  this
     */
    public LinearText setMessageSize(float size) {
        if (null != message) {
            message.setTextSize(size);
        }
        return this;
    }

    /**
     * 设置按钮的点击事件
     * @param click 点击事件
     * @return this
     */
    public LinearText setOnClickGo(View.OnClickListener click) {
        if (null != listOnClick) {
            listOnClick.setOnClickListener(click);
        }
        return this;
    }
}
