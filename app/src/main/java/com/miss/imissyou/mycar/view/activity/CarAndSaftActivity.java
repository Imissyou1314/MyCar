package com.miss.imissyou.mycar.view.activity;

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
import com.miss.imissyou.mycar.view.CarInfoView;
import com.miss.imissyou.mycar.view.fragment.FirstAddNewCarFragment;
import com.miss.imissyou.mycar.view.fragment.LocationMapFragment;

/**
 * 车辆与安全
 * Created by Administrator on 2016-06-11.
 */
public class CarAndSaftActivity extends BaseActivity implements CarInfoView, View.OnClickListener {

    @FindViewById(id = R.id.car_and_saft_title)
    private TitleFragment title;
    @FindViewById(id = R.id.car_and_saft_startBtn)
    private ToggleButton startBtn;
//    @FindViewById(id = R.id.car_and_saft_stopBtn)
//    private ToggleButton stopBtn;
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
//            stopBtn.setToggleSate(!mCar.isCarState());
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
                    mCarInfoPresenter.changeCarState(mCar.getId());
                } else {
                    mCarInfoPresenter.changeCarStop(mCar.getId());
                }
            }
        });

        //警报状态
        wareBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {
                    mCarInfoPresenter.changeCarAlarmState(mCar.getId());
                }
            }
        });
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

}
