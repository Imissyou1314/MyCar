package com.miss.imissyou.mycar.model.impl;

import com.miss.imissyou.mycar.bean.OrderBean;
import com.miss.imissyou.mycar.model.SumbitIndentModel;
import com.miss.imissyou.mycar.presenter.SumbitIndentPresenter;

/**
 * 向服务器递交订单
 * Created by Imissyou on 2016/4/25.
 */
public class SumbitIndentModelImpl implements SumbitIndentModel {

    private SumbitIndentPresenter mSumbitIndentPresenter;

    public SumbitIndentModelImpl(SumbitIndentPresenter sumbitIndentPresenter) {
        mSumbitIndentPresenter = sumbitIndentPresenter;
    }

    @Override public void sentIndentToService(OrderBean orderBean) {
        //TODO
    }
}
