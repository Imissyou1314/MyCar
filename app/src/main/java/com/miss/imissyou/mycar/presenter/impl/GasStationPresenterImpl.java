package com.miss.imissyou.mycar.presenter.impl;

import android.location.Location;

import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.model.GasStationModel;
import com.miss.imissyou.mycar.model.impl.GasStationModelImpl;
import com.miss.imissyou.mycar.presenter.GasStationPresenter;
import com.miss.imissyou.mycar.view.fragment.GasStationListFragment;
import com.miss.imissyou.mycar.view.GasStationListView;

/**
 * 加油站列表的PresenterImpl
 * Created by Imissyou on 2016/4/24.
 */
public class GasStationPresenterImpl implements GasStationPresenter {

    private  GasStationListView mGasStationListView;
    private GasStationModel mGasStationModelImpl;


    public GasStationPresenterImpl(GasStationListFragment gasStationList) {
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

    @Override public void attachView(GasStationListView view) {
        this.mGasStationListView = view;
        mGasStationListView.showProgress();
    }

    @Override public void detchView() {
        mGasStationListView = null;
    }

    @Override public void loadServiceData(Location location) {
        mGasStationModelImpl.loadData(location);
    }
}
