package com.miss.imissyou.mycar.presenter.impl;

import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.CarInfoModle;
import com.miss.imissyou.mycar.model.impl.CarInfoModleImpl;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.view.CarInfoView;
import com.miss.imissyou.mycar.presenter.CarInfoPresenter;

/**
 * 或者更新车辆状态
 * 获取车辆信息
 * Created by Imissyou on 2016/5/3.
 */
public class CarInfoPresenterImpl implements CarInfoPresenter {

    private CarInfoView mCarInfoView;
    private CarInfoModle mCarInfoModle;

    @Override
    public void changeCarStop(Long carId) {
        mCarInfoModle.changeCartoStop(carId);
    }

    public CarInfoPresenterImpl(CarInfoView carInfoFragment) {
        attachView(carInfoFragment);
        mCarInfoModle = new CarInfoModleImpl(this);
    }

    @Override
    public void onFailure(int errorNo, String strMsg) {
        mCarInfoView.hideProgress();
        if (errorNo == Constant.NETWORK_STATE) {
            strMsg = Constant.NOTNETWORK;
        }
        mCarInfoView.showResultError(errorNo, strMsg);
    }

    @Override
    public void onSuccess(BaseBean resultBean) {
        mCarInfoView.hideProgress();

        CarInfoBean carInfoBean = GsonUtils.getParam((ResultBean) resultBean, "car", CarInfoBean.class);
        if (carInfoBean != null) {

            mCarInfoView.showResultSuccess(carInfoBean);
        } else {
            mCarInfoView.showResultError(0, "获取数据出错");
        }
    }

    @Override
    public void attachView(CarInfoView view) {
        this.mCarInfoView = view;
        mCarInfoView.showProgress();
    }

    @Override
    public void detchView() {
        mCarInfoView = null;
    }

    @Override
    public void loadCarInfo(Long userId, Long carId) {
        if (userId.equals("") || carId.equals("")) {
            LogUtils.d("请求车辆信息不能为空");
            return;
        }
        mCarInfoModle.loadCarInfoFormService(userId, carId);
    }

    @Override
    public void changeCarAlarmState(Long carId) {
        if (carId >= 0) {
            mCarInfoModle.changeCarAlarmState(0, carId);
        } else {
            mCarInfoView.showResultError(0, "传入的车辆Id不符合要求");
        }
    }

    @Override
    public void changeCarState(Long carId) {
        if (carId >= 0) {
            mCarInfoModle.changeCarState(0, carId);
        } else {
            mCarInfoView.showResultError(0, "传入的车辆Id不符合要求");
        }
    }

    @Override
    public void setCuurentCar(Long userId, Long carId) {
        mCarInfoModle.setCurrentCar(userId, carId);
    }

    @Override
    public void setCurrentCarSuccess(ResultBean resultBean) {
        LogUtils.d("获取的信息:" + GsonUtils.Instance().toJson(resultBean));
        mCarInfoView.showResultSuccess(resultBean);
    }

    @Override
    public void getCurrentCar(Long userId) {
        mCarInfoModle.getUserCurrentCar(userId);
    }

    @Override
    public void changeCarPlatNumber(CarInfoBean carInfoBean) {
        mCarInfoModle.changeCarPlatNumber(carInfoBean);
    }
}
