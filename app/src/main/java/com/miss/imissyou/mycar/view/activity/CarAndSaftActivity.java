package com.miss.imissyou.mycar.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.CarInfoPresenter;
import com.miss.imissyou.mycar.presenter.impl.CarInfoPresenterImpl;
import com.miss.imissyou.mycar.ui.LinearText;
import com.miss.imissyou.mycar.ui.TitleFragment;
import com.miss.imissyou.mycar.ui.ToggleButton;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.FindViewById;
import com.miss.imissyou.mycar.util.SystemUtils;
import com.miss.imissyou.mycar.view.BackHandledInterface;
import com.miss.imissyou.mycar.view.CarInfoView;
import com.miss.imissyou.mycar.view.fragment.BaseFragment;
import com.miss.imissyou.mycar.view.fragment.FirstAddNewCarFragment;
import com.miss.imissyou.mycar.view.fragment.LocationMapFragment;

import cn.jpush.android.api.JPushInterface;

/**
 * 车辆与安全
 * Created by Administrator on 2016-06-11.
 */
public class CarAndSaftActivity extends BaseActivity implements CarInfoView, View.OnClickListener , BackHandledInterface {

    @FindViewById(id = R.id.car_and_saft_title)
    private TitleFragment title;
    @FindViewById(id = R.id.car_and_saft_startBtn)
    private ToggleButton startBtn;
    @FindViewById(id = R.id.car_and_saft_wareBtn)
    private ToggleButton wareBtn;
    @FindViewById(id = R.id.car_and_saft_LocationCar)
    private LinearText goLocation;

    @FindViewById(id = R.id.car_and_saft_back)
    private LinearLayout back;
    @FindViewById(id = R.id.car_and_saft_frame)
    private LinearLayout frame;



    private CarInfoPresenter mCarInfoPresenter;
    private CarInfoBean mCar;      //当前车辆信息
    private MyCarAndSafeReceiver receiver;          //设置广播

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_car_and_saft);
        mCarInfoPresenter = new CarInfoPresenterImpl(this);
    }

    @Override
    protected void initData() {
        title.setTitleText("当前车辆与安全");
        goLocation.setTitle("定位当前车辆").setTitleSize(14).setMessage("");

        if (null == Constant.carBean) {
            back.setVisibility(View.GONE);
            frame.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.car_and_saft_frame,new FirstAddNewCarFragment())
                    .commit();
        } else {
            mCar = Constant.carBean;
            startBtn.setToggleSate(mCar.isCarState());
            wareBtn.setToggleSate(mCar.isCarAlarm());
        }
    }

    @Override
    public void addListeners() {
        goLocation.setOnClickListener(this);
        //启动车辆
        startBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {

                if (on) {
                    Constant.carBean.setCarState(true);
                    mCarInfoPresenter.changeCarState(mCar.getId());
                } else {
                    Constant.carBean.setCarState(false);
                    mCarInfoPresenter.changeCarStop(mCar.getId());
                }
            }
        });

        //警报状态
        wareBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {
                    Constant.carBean.setCarAlarm(true);
                    mCarInfoPresenter.changeCarAlarmState(mCar.getId());
                } else {
                    Constant.carBean.setCarAlarm(false);
                }
            }
        });

        //动态注册广播
        receiver = new MyCarAndSafeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("cn.jpush.android.intent.NOTIFICATION_RECEIVED");
        filter.addAction("cn.jpush.android.intent.NOTIFICATION_OPENED");
        filter.addCategory("com.miss.imissyou.mycar");
        this.registerReceiver(receiver,filter);
    }

    @Override
    public void showResultSuccess(CarInfoBean resultBean) {

    }

    @Override
    public void showResultError(int errorNo, String errorMag) {

    }

    @Override
    public void showResultSuccess(ResultBean resultBean) {
        if (resultBean.isServiceResult()) {
            LogUtils.w("更改成功" + resultBean.getResultInfo());
        }
        Toast.makeText(CarAndSaftActivity.this, resultBean.getResultInfo().equals("")
                ? "更改状态成功": resultBean.getResultInfo(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onClick(View v) {
        //启动定位页面
        if (null != Constant.carBean && 0 != Constant.carBean.getLat()
                && 0 != Constant.carBean.getLon()) {
            LocationMapFragment location = new LocationMapFragment();
            //给fragment进行传经纬度值
            Bundle bundle = new Bundle();
            bundle.putDouble(Constant.startLatitude,Constant.carBean.getLat());
            bundle.putDouble(Constant.startLongitude, Constant.carBean.getLon());
            location.setArguments(bundle);

            back.setVisibility(View.GONE);
            frame.setVisibility(View.VISIBLE);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.car_and_saft_frame,new LocationMapFragment())
                    .commit();
        }

    }

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {

    }

    public class MyCarAndSafeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            LogUtils.w("MyCarAndSafe" + intent.getAction());

            //接收到Jpush后自动刷新页面
            if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                System.out.println("用户接收到Jpush通知");
                if (SystemUtils.isForeground(context,
                        "com.miss.imissyou.mycar.view.activity.MessageActivity")) {
                    LogUtils.d("只进Message行刷新页面====================>");

                }
            }
        }
    }

}
