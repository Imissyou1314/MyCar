package com.miss.imissyou.mycar.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.ui.ToggleButton;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.SPUtils;

/**
 * Created by Imissyou on 2016/3/23.
 */
public class SettingFragment extends BaseFragment {

    private ToggleButton allMessageBtn;         //获取所有信息
    private ToggleButton errorMessageBtn;       //获取错误信息
    private ToggleButton wareMessageBtn;        //获取警报信息

    private ToggleButton noReadMessageBtn;      //获取未读信息
    private ToggleButton startMusic;            //是否播放音乐

    private boolean allMessageState;
    private boolean errorMessageState;
    private boolean wareMessageState;
    private boolean noReadMessageState;
    private boolean stratMusicState;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_setting, inflater, container, savedInstanceState);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    protected void initView(View view) {

        noReadMessageBtn = (ToggleButton) view.findViewById(R.id.setting_noReadMessage_button);
        allMessageBtn = (ToggleButton) view.findViewById(R.id.setting_allMessage_button);
        errorMessageBtn = (ToggleButton) view.findViewById(R.id.setting_errorMessage_button);
        wareMessageBtn = (ToggleButton) view.findViewById(R.id.setting_wareMessage_button);
        startMusic = (ToggleButton) view.findViewById(R.id.setting_startMusic_button);
    }

    @Override protected void initData() {
        allMessageState =  SPUtils.getSp_set().getBoolean(Constant.MESSAGEALL,true);
        errorMessageState = SPUtils.getSp_set().getBoolean(Constant.MESSAGEERROR, true);
        wareMessageState = SPUtils.getSp_set().getBoolean(Constant.MESSAGEWARE, true);
        noReadMessageState = SPUtils.getSp_set().getBoolean(Constant.MESSAGENOREAD, true);
        stratMusicState = SPUtils.getSp_set().getBoolean(Constant.MESSAGEMUSIC,true);

        allMessageBtn.setToggleSate(allMessageState);
        errorMessageBtn.setToggleSate(errorMessageState);
        wareMessageBtn.setToggleSate(wareMessageState);
        noReadMessageBtn.setToggleSate(noReadMessageState);
        startMusic.setToggleSate(stratMusicState);
    }

    @Override
    protected void addViewsListener() {

        allMessageBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {

                }
            }
        });

        errorMessageBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {

                }
            }
        });

        wareMessageBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {

                }
            }
        });

        noReadMessageBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {

                }
            }
        });
    }


    /**
     * 离开这个页面时保存数据
     */
    @Override public void onPause() {
        allMessageState = allMessageBtn.getToggleOn();
        errorMessageState = errorMessageBtn.getToggleOn();
        wareMessageState = wareMessageBtn.getToggleOn();
        noReadMessageState = noReadMessageBtn.getToggleOn();
        stratMusicState = startMusic.getToggleOn();

        SPUtils.putSetData(Constant.MESSAGEALL, allMessageState);
        SPUtils.putSetData(Constant.MESSAGEERROR, errorMessageState);
        SPUtils.putSetData(Constant.MESSAGENOREAD, noReadMessageState);
        SPUtils.putSetData(Constant.MESSAGEWARE, wareMessageState);
        SPUtils.putSetData(Constant.MESSAGEMUSIC, stratMusicState);
        super.onPause();
    }

}
