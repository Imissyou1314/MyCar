package com.miss.imissyou.mycar.view.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miss.imissyou.mycar.ui.sidemenu.interfaces.ScreenShotable;
import com.miss.imissyou.mycar.view.BackHandledInterface;


/**
 * 使用Fragment的基类
 * Created by Imissyou on 2016/4/24.
 */
public abstract class BaseFragment extends Fragment implements ScreenShotable {

    protected BackHandledInterface mBackHandledInterface;

    /**
     * 返回按键事件
     * @return 返回按键
     */
    public abstract boolean onBackPressed();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof BackHandledInterface)) {
            throw new ClassCastException("你没有实现BackHandledInterface接口");
        } else {
            this.mBackHandledInterface = (BackHandledInterface) getActivity();
        }
    }


    @Nullable
    public View onCreateView(@Nullable int layoutResID,@Nullable LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(layoutResID,container, false);
        initView(view);         //加载页面控件
        addViewsListener();     //添加页面监听事件

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBackHandledInterface.setSelectedFragment(this);
        initData();             //加载数据
    }

    /**
     * 加载页面控件
     * @param view
     */
    protected abstract void initView(View view);

    /**
     * 加载数据
     */
    protected abstract void initData();

    /**
     * 添加页面监听事件
     */
    protected abstract void addViewsListener();

    @Override public void takeScreenShot() {

    }

    @Override public Bitmap getBitmap() {
        return null;
    }

}
