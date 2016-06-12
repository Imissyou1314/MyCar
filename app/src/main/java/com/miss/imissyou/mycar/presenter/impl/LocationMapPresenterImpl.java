package com.miss.imissyou.mycar.presenter.impl;

import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.model.LocationMapModel;
import com.miss.imissyou.mycar.model.impl.LocationMapModelImpl;
import com.miss.imissyou.mycar.presenter.LoactionMapPresenter;
import com.miss.imissyou.mycar.view.LocationView;
import com.miss.imissyou.mycar.view.fragment.LocationMapFragment;

/**
 * Created by Imissyou on 2016/5/19.
 */
public class LocationMapPresenterImpl implements LoactionMapPresenter {

    private LocationView locationView;
    private LocationMapModel mLocationMapModel;

    public LocationMapPresenterImpl(LocationView locationMapFragment) {
        attachView(locationMapFragment);
        mLocationMapModel = new LocationMapModelImpl(this);
    }

    @Override
    public void onFailure(int errorNo, String strMsg) {

    }

    @Override
    public void onSuccess(BaseBean resultBean) {

    }

//    @Override
//    public void loadServiceData(BaseBean useBean) {
//
//    }

    @Override
    public void attachView(LocationView view) {
        this.locationView = locationView;
    }

    @Override
    public void detchView() {

    }
}
