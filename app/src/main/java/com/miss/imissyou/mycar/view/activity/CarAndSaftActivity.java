package com.miss.imissyou.mycar.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

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

/**
 * 车辆与安全
 * Created by Administrator on 2016-06-11.
 */
public class CarAndSaftActivity extends BaseActivity implements CarInfoView, View.OnClickListener, ToggleButton.OnToggleChanged {

    @FindViewById(id = R.id.car_and_saft_title)
    private TitleFragment title;
    @FindViewById(id = R.id.car_and_saft_startBtn)
    private ToggleButton startBtn;
    @FindViewById(id = R.id.car_and_saft_stopBtn)
    private ToggleButton stopBtn;
    @FindViewById(id = R.id.car_and_saft_wareBtn)
    private ToggleButton wareBtn;
    @FindViewById(id = R.id.car_and_saft_LocationCar)
    private LinearText goLocation;

    private CarInfoPresenter mCarInfoPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_car_and_saft);
        mCarInfoPresenter = new CarInfoPresenterImpl(this);
    }

    @Override
    protected void initData() {
        title.setTitleText("车辆与安全");
        goLocation.setTitle("定位当前车辆").setTitleSize(14).setMessage("");

        if (null == Constant.carBean) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.car_and_saft_back,new FirstAddNewCarFragment())
                    .commit();
        }
    }

    @Override
    public void addListeners() {
        goLocation.setOnClickListener(this);

        startBtn.setOnToggleChanged(this);
        stopBtn.setOnToggleChanged(this);
        wareBtn.setOnToggleChanged(this);
    }

    @Override
    public void showResultSuccess(CarInfoBean resultBean) {

    }

    @Override
    public void showResultError(int errorNo, String errorMag) {

    }

    @Override
    public void showResultSuccess(ResultBean resultBean) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onToggle(boolean on) {

    }
}
