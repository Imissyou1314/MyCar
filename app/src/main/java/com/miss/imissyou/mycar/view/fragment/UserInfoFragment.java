package com.miss.imissyou.mycar.view.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.UserInfoPresenter;
import com.miss.imissyou.mycar.presenter.impl.UserInfoPresenterImpl;
import com.miss.imissyou.mycar.ui.AnimatorView;
import com.miss.imissyou.mycar.ui.circleProgress.CircleProgress;
import com.miss.imissyou.mycar.view.UserInfoView;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by Imissyou on 2016/4/26.
 */
public class UserInfoFragment extends BaseFragment implements UserInfoView {

    private EditText userName;          //用户名
    private EditText userReadName;      //用户的真实姓名
    private EditText userCarNember;     //用户车数量
    private EditText userAccount;       //用户账号
    private EditText userPhone;         //用户手机号
    private EditText userDersasPhone;   //用户亲人手机号
    private View userheadView;          //用户头像
    private CircleProgress progress;


    private UserInfoPresenter mUserInfoPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(R.layout.fragment_user_info, inflater, container, savedInstanceState);
    }

    @Override protected void initView(View view) {

        userheadView = view.findViewById(R.id.userInfo_userHead_Image);
        userName = (EditText) view.findViewById(R.id.userInfo_userName_input);
        userName.setEnabled(false);
        userReadName = (EditText) view.findViewById(R.id.userInfo_userRealName_input);
        userReadName.setEnabled(false);
        userCarNember = (EditText) view.findViewById(R.id.userInfo_usercarNumber_Input);
        userCarNember.setEnabled(false);
        userAccount = (EditText) view.findViewById(R.id.userInfo_userdaccount_input);
        userAccount.setEnabled(false);
        userPhone = (EditText) view.findViewById(R.id.userInfo_userdPhone_input);
        userPhone.setEnabled(false);
        userDersasPhone = (EditText) view.findViewById(R.id.userInfo_userdersaPhone_Input);
        userDersasPhone.setEnabled(false);

        progress = (CircleProgress) view.findViewById(R.id.userInfo_progress);
    }

    @Override
    protected void initData() {
        mUserInfoPresenter = new UserInfoPresenterImpl(this);
    }

    @Override protected void addViewsListener() {
        //添加动画效果
        final Spring spring = SpringSystem.create().createSpring();
        SpringConfig config = new SpringConfig(40, 3);
        spring.setSpringConfig(config);
        final float maxScale = 0.1f;
        spring.addListener(new SimpleSpringListener(){
            @Override
            public void onSpringUpdate(Spring spring) {
                super.onSpringUpdate(spring);
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * maxScale);

                ViewHelper.setScaleX(userheadView, scale);
                ViewHelper.setScaleY(userheadView, scale);
                //或者这样
//                userheadView.setScaleX(scale);
//                userheadView.setScaleY(scale);
            }
        });

        userheadView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int key = event.getAction();
                switch (key) {
                    case MotionEvent.ACTION_DOWN:
                        spring.setEndValue(1.0);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        spring.setEndValue(0.0);
                        break;
                    default:
                        break;
                }

                return false;
            }
        });
    }

    @Override
    public void showResultError(int errorNo, String errorMag) {

    }

    @Override
    public void showResultSuccess(ResultBean resultBean) {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override public void showProgress() {
        progress.startAnim();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override public void hideProgress() {
        progress.stopAnim();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        hideProgress();
        mUserInfoPresenter.detchView();
    }
}
