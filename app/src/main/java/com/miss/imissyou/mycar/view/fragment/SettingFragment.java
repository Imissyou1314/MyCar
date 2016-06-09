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
public class SettingFragment extends BaseFragment{

    private ToggleButton allMessageBtn;
    private ToggleButton errorMessageBtn;
    private ToggleButton wareMessageBtn;

    private ToggleButton noReadMessageBtn;

    private LinearLayout goMyHome;
    private LinearLayout setSavePassword;

    private boolean allMessageState;
    private boolean errorMessageState;
    private boolean wareMessageState;
    private boolean noReadMessageState;

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

        goMyHome = (LinearLayout) view.findViewById(R.id.setting_goMyHome);
        setSavePassword = (LinearLayout) view.findViewById(R.id.setting_setSavePassword);
    }

    @Override protected void initData() {
        allMessageState =  SPUtils.getSp_set().getBoolean(Constant.MESSAGEALL,true);
        errorMessageState = SPUtils.getSp_set().getBoolean(Constant.MESSAGEERROR, true);
        wareMessageState = SPUtils.getSp_set().getBoolean(Constant.MESSAGEWARE, true);
        noReadMessageState = SPUtils.getSp_set().getBoolean(Constant.MESSAGENOREAD, true);

        allMessageBtn.setToggleSate(allMessageState);
        errorMessageBtn.setToggleSate(errorMessageState);
        wareMessageBtn.setToggleSate(wareMessageState);
        noReadMessageBtn.setToggleSate(noReadMessageState);
    }

    @Override
    protected void addViewsListener() {
        /**跳转到设置用户信息的主页*/
        goMyHome.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                toUserHome();
            }
        });

        /**设置安全码*/
        setSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                toSetSavePassword();
            }
        });

    }

    /**
     * 到设置安全码界面
     */
    private void toSetSavePassword() {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.setting_page, new SafePasswordFragment())
                .commit();
    }

    /**
     * 到用户主页
     */
    private void toUserHome() {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.setting_page,new UserInfoFragment())
                .commit();
    }

    /**
     * 离开这个页面时保存数据
     */
    @Override public void onPause() {
        allMessageState = allMessageBtn.getToggleOn();
        errorMessageState = errorMessageBtn.getToggleOn();
        wareMessageState = wareMessageBtn.getToggleOn();
        noReadMessageState = noReadMessageBtn.getToggleOn();

        SPUtils.putSetData(Constant.MESSAGEALL, allMessageState);
        SPUtils.putSetData(Constant.MESSAGEERROR, errorMessageState);
        SPUtils.putSetData(Constant.MESSAGENOREAD, noReadMessageState);
        SPUtils.putSetData(Constant.MESSAGEWARE, wareMessageState);
        super.onPause();
    }
}
