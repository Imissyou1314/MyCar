package com.miss.imissyou.mycar.presenter.impl;

import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.model.GasStationModle;
import com.miss.imissyou.mycar.model.impl.GasStationModelImpl;
import com.miss.imissyou.mycar.presenter.GasStationPresenter;
import com.miss.imissyou.mycar.view.GasStationView;
import com.miss.imissyou.mycar.view.fragment.GasStationFragment;

/**
 * 加油站列表的PresenterImpl
 * Created by Imissyou on 2016/4/24.
 */
public class GasStationPresenterImpl implements GasStationPresenter {

    private  GasStationView mGasStationListView;
    private GasStationModle mGasStationModelImpl;


    public GasStationPresenterImpl(GasStationFragment gasStationList) {
        attachView(gasStationList);
        mGasStationModelImpl = new GasStationModelImpl(this);

    }

    @Override public void onFailure(int errorNo, String strMsg) {
        mGasStationListView.hideProgress();
    }

    @Override public void onSuccess(BaseBean resultBean) {
        mGasStationListView.hideProgress();
    }

    @Override public void loadServiceData(BaseBean useBean) {

    }

    @Override public void attachView(GasStationView view) {
        this.mGasStationListView = view;
        mGasStationListView.showProgress();
    }

    @Override public void detchView() {
        mGasStationListView = null;
    }


    @Override
    public void loadServiceData(double lon, double lat, int r, String key, int page, int format) {

    }
}
