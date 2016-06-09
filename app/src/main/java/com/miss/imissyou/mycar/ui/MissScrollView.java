package com.miss.imissyou.mycar.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.lidroid.xutils.util.LogUtils;

/**
 * 自定义滑到底部更新的ScrollView
 * Created by Administrator on 2016-06-10.
 */
public class MissScrollView extends ScrollView {


    private OnScrollToBottomListener onScrollToBottom;

    public MissScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MissScrollView(Context context) {
        super(context);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
                                  boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        LogUtils.d("刷新信息" + scrollX + ">>>>" + scrollY + ">>>>" + clampedX + ">>>>>" + clampedY);
        if (scrollY == 0 && null != onScrollToBottom) {
            onScrollToBottom.onScrollBottomListener(clampedX);
        }
    }

    public void setOnScrollToBottomLintener(OnScrollToBottomListener listener) {
        onScrollToBottom = listener;
    }

    public interface OnScrollToBottomListener {
        public void onScrollBottomListener(boolean isBottom);
    }

}
