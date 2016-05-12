package com.miss.imissyou.mycar.presenter.impl;


import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.CarListModel;
import com.miss.imissyou.mycar.model.impl.CarListModelImpl;
import com.miss.imissyou.mycar.presenter.CarListPresenter;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.view.CarListFragmentView;

import java.util.List;

/**
 * Created by Imissyou on 2016/4/20.
 */
public class CarListPresenterImpl implements CarListPresenter<CarListFragmentView> {

    private CarListFragmentView mCarListFragmentView;
    private CarListModel mCarListModel;

    public CarListPresenterImpl(CarListFragmentView carListFragment) {
        attachView( carListFragment);
        mCarListModel = new CarListModelImpl(this);
    }

    @Override public void attachView(CarListFragmentView view) {
        this.mCarListFragmentView = view;
        mCarListFragmentView.showProgress();
    }

    @Override public void onFailure(int errorNo, String strMsg) {

        mCarListFragmentView.hideProgress();
        mCarListFragmentView.showResultError(errorNo, strMsg);
    }

    @Override public void onSuccess(BaseBean resultBean) {

        mCarListFragmentView.hideProgress();

        if (((ResultBean) resultBean).isServiceResult()) {
            List<CarInfoBean> cars = GsonUtils.getParams((ResultBean)
                    resultBean, "car", CarInfoBean[].class);
            if (cars != null) {
                mCarListFragmentView.showResultSuccess(cars);
            } else {
                mCarListFragmentView.showResultError(0, "用户没有车辆");
            }
        } else {
            LogUtils.d("解析数据出错");
        }
    }

    @Override public void loadServiceData(String userId) {
        mCarListModel.loadData(userId);
    }

    @Override public void detchView() {

    }
}
