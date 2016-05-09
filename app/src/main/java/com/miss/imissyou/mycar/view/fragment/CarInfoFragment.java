package com.miss.imissyou.mycar.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.impl.CarInfoPresenterImpl;
import com.miss.imissyou.mycar.ui.circleProgress.CircleProgress;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.view.CarInfoView;
import com.miss.imissyou.mycar.view.CarInfoPresenter;


/**
 * 单架车的具体信息
 * Created by Imissyou on 2016/5/3.
 */
public class CarInfoFragment extends BaseFragment implements CarInfoView {



    private CircleProgress progress;


    private CarInfoPresenter mCarInfoPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_car_info, inflater, container, savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        progress = (CircleProgress) view.findViewById(R.id.load_carInfo_progress);
    }

    @Override protected void initData() {
        String userId = getArguments().getString(Constant.USER_ID);
        String carId = getArguments().getString(Constant.CAR_ID);

        mCarInfoPresenter = new CarInfoPresenterImpl(this);
        if (!(carId.equals("") && userId.equals(""))) {
            LogUtils.d("userId:" + userId + ">>>carId:" + carId);
            mCarInfoPresenter.loadCarInfo(userId, carId);
        }
    }

    @Override protected void addViewsListener() {

    }

    @Override public void showResultError(int errorNo, String errorMag) {

    }

    @Override public void showResultSuccess(ResultBean resultBean) {
    }

    @Override public void showProgress() {
        progress.startAnim();
    }

    @Override public void hideProgress() {
        progress.stopAnim();
    }

    @Override
    public void onDestroy() {
        mCarInfoPresenter.detchView();
        super.onDestroy();
    }

    @Override
    public void showResultSuccess(CarInfoBean resultBean) {
        LogUtils.d(resultBean.getBrand());
    }
}
