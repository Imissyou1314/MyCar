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
        if (scrollY == 0 && scrollX == 0 && null != onScrollToBottom) {
            onScrollToBottom.onScrollTopListener(clampedY);
        }

        //更新实现可以下拉刷新
        if (clampedY && scrollY > 0) {
            onScrollToBottom.onScrollBottomListener(clampedY);
        }
    }

    public void setOnScrollToBottomLintener(OnScrollToBottomListener listener) {
        onScrollToBottom = listener;
    }

    public interface OnScrollToBottomListener {
        void onScrollTopListener(boolean isTop);

        /**
         * 滑动到底部了
         * @param isBootom
         */
        void onScrollBottomListener(boolean isBootom);
    }

}
