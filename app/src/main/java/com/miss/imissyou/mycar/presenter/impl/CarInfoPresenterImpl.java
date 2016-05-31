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
import com.miss.imissyou.mycar.view.MainView;
import com.miss.imissyou.mycar.view.CarInfoPresenter;
import com.miss.imissyou.mycar.view.fragment.CarInfoFragment;

/**
 * Created by Imissyou on 2016/5/3.
 */
public class CarInfoPresenterImpl implements CarInfoPresenter {

    private CarInfoView mCarInfoView;
    private CarInfoModle mCarInfoModle;


    public CarInfoPresenterImpl(CarInfoFragment carInfoFragment) {
        attachView(carInfoFragment);
        mCarInfoModle =new CarInfoModleImpl(this);
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
        CarInfoBean carInfoBean =  GsonUtils.getParam((ResultBean) resultBean, "car", CarInfoBean.class);
        if (carInfoBean != null) {
            mCarInfoView.showResultSuccess(carInfoBean);
        } else {
            mCarInfoView.showResultError(0, "获取数据出错");
        }
    }

    @Override public void loadServiceData(BaseBean useBean) {

    }

    @Override public void attachView(MainView view) {
        this.mCarInfoView = (CarInfoView) view;
        mCarInfoView.showProgress();
    }

    @Override
    public void detchView() {
        mCarInfoView = null;
    }

    @Override public void loadCarInfo(Long userId, Long carId) {
        if (userId.equals("") || carId.equals("")){
            LogUtils.d("请求车辆信息不能为空");
            return;
        }
        mCarInfoModle.loadCarInfoFormService(userId, carId);
    }

    @Override
    public void changeCarAlarmState(Long carId) {
        if (carId >= 0) {
                mCarInfoModle.changeCarAlarmState(0, carId);
        }         else {
            mCarInfoView.showResultError(0,"传入的车辆Id不符合要求");
        }
    }

    @Override
    public void changeCarState(Long carId) {
        if (carId >= 0) {
            mCarInfoModle.changeCarState(0, carId);
        }         else {
            mCarInfoView.showResultError(0,"传入的车辆Id不符合要求");
        }
    }
}
