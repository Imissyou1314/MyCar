package com.miss.imissyou.mycar.view.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.bean.UserBean;
import com.miss.imissyou.mycar.presenter.UserInfoPresenter;
import com.miss.imissyou.mycar.presenter.impl.UserInfoPresenterImpl;
import com.miss.imissyou.mycar.ui.RoundImageView;
import com.miss.imissyou.mycar.ui.circleProgress.CircleProgress;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.DialogUtils;
import com.miss.imissyou.mycar.view.UserInfoView;
import com.miss.imissyou.mycar.view.activity.ChangePhoneNumberActivity;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by Imissyou on 2016/4/26.
 */
public class UserInfoFragment extends BaseFragment implements UserInfoView, View.OnClickListener {

    private EditText userName;          //用户名
    private EditText userReadName;      //用户的真实姓名
    private EditText userAccount;       //用户账号
    private EditText userPhone;         //用户手机号
    private EditText userDersasPhone;   //用户亲人手机号
    private Button submit;              //更新用户操作
    private RoundImageView userheadView;          //用户头像
    //private CircleProgress progress;

    private UserBean mUserBean;

    private LinearLayout goChagenUserPhone;     //跳转到更改用户手机号的页面
    private LinearLayout goChagenUserdersaPhone;//跳转到更改用户亲人手机号的页面


    private UserInfoPresenter mUserInfoPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(R.layout.fragment_user_info, inflater, container, savedInstanceState);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override protected void initView(View view) {

        submit = (Button) view.findViewById(R.id.userInfo_submit);
        userheadView =(RoundImageView) view.findViewById(R.id.userInfo_userHead_Image);
        userName = (EditText) view.findViewById(R.id.userInfo_userName_input);
        userName.setEnabled(false);
        userReadName = (EditText) view.findViewById(R.id.userInfo_userRealName_input);
        userReadName.setEnabled(false);

        userAccount = (EditText) view.findViewById(R.id.userInfo_userdaccount_input);
        userAccount.setEnabled(false);
        userPhone = (EditText) view.findViewById(R.id.userInfo_userdPhone_input);
        userPhone.setEnabled(false);
        userDersasPhone = (EditText) view.findViewById(R.id.userInfo_userdersaPhone_Input);
        userDersasPhone.setEnabled(false);

        goChagenUserPhone = (LinearLayout) view.findViewById(R.id.userInfo_userdPhone_goChagne);
        goChagenUserdersaPhone = (LinearLayout) view.findViewById(R.id.userInfo_userdersaPhone_goChagen);

        //progress = (CircleProgress) view.findViewById(R.id.userInfo_progress);
    }

    @Override
    protected void initData() {
        mUserInfoPresenter = new UserInfoPresenterImpl(this);
        mUserInfoPresenter.loadServiceData(null);
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

        submit.setOnClickListener(this);
        goChagenUserPhone.setOnClickListener(this);
        goChagenUserdersaPhone.setOnClickListener(this);
    }

    @Override
    public void showResultError(int errorNo, String errorMag) {
        String title = "标题";
        if (errorNo == Constant.WARE_ERROR || errorNo == Constant.WARE_USERDO_ERROR) {
            title = "警告";
        } else {
            title = "错误";
        }
       showDialog(title, errorMag);
    }

    @Override public void onUpdateSuccess(String resultMessage) {
        showDialog("操作成功", resultMessage);
    }

    private void showDialog(String title, String errorMag) {
        new DialogUtils(getActivity()).errorMessage(title, errorMag);
    }

    @Override
    public void showResultOnSuccess(UserBean userBean) {
        setUpdate(userBean);
    }

    @Override
    public void showResultSuccess(ResultBean userBean) {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override public void showProgress() {
        //progress.startAnim();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override public void hideProgress() {
        //progress.stopAnim();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        hideProgress();
        mUserInfoPresenter.detchView();
    }

    @Override public void onClick(View v) {
        Intent intent = new Intent();
        String title ="";
        int TAG = 0;
        intent.setClass(getActivity(), ChangePhoneNumberActivity.class);
        switch (v.getId()) {
            case R.id.userInfo_userdersaPhone_goChagen:
                title="更改亲人手机";
                TAG = 1;
                break;
            case R.id.userInfo_userdPhone_goChagne:
                title="更改用户手机";

                break;
            case R.id.userInfo_submit:
                changeUserInfo();
                return;
        }
        intent.putExtra("title", title);
        intent.putExtra("TAG", TAG);
        getActivity().startActivity(intent);
    }

    /**
     * 提交信息到服务器
     */
    private void changeUserInfo() {
        if (null != mUserBean) {
            mUserBean.setRealName(userReadName.getText().toString());
            mUserBean.setUsername(userName.getText().toString());
            mUserInfoPresenter.changeUserInfo(mUserBean);
        } else {
            showResultError(Constant.WARE_ERROR, Constant.WARE_ERROR_COSTANT);
        }
    }

    /**
     * 转载页面数据
     * @param userBean
     */
    private void setUpdate(UserBean userBean) {
        this.mUserBean = userBean;
        if (null != userBean && null != userBean.getUserImg()) {
            LogUtils.d("获取图片地址:" + Constant.SERVER_URL + userBean.getUserImg());
            Glide.with(this).load(Constant.SERVER_URL + userBean.getUserImg()).into(userheadView);
        }
        userName.setText(mUserBean.getUsername());
        userName.setEnabled(true);

        userReadName.setText(mUserBean.getRealName());
        userReadName.setEnabled(true);

        userAccount.setText(mUserBean.getLoginid());

        userPhone.setText(mUserBean.getPhone());
        userDersasPhone.setText(mUserBean.getRelatedPhone());
    }
}
