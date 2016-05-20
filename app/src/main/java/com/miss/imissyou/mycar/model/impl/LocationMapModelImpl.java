package com.miss.imissyou.mycar.model.impl;

import com.miss.imissyou.mycar.model.LocationMapModel;
import com.miss.imissyou.mycar.presenter.LoactionMapPresenter;
import com.miss.imissyou.mycar.presenter.impl.LocationMapPresenterImpl;

/**
 * 获取数据的Model
 * Created by Imissyou on 2016/5/19.
 */
public class LocationMapModelImpl implements LocationMapModel {

    private LoactionMapPresenter mLoactionMapPresenter;

    public LocationMapModelImpl(LoactionMapPresenter locationMapPresenter) {
        mLoactionMapPresenter = locationMapPresenter;
    }

    @Override public void loadOilStation() {

    }

    @Override public void loadStopStation() {

    }

    @Override public void loadServiceStation() {

    }
}
