package com.miss.imissyou.mycar.model.impl;

import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.model.OrderModel;
import com.miss.imissyou.mycar.presenter.OrderPresenter;
import com.miss.imissyou.mycar.presenter.impl.OrderPresenterImpl;
import com.miss.imissyou.mycar.view.MainView;

/**
 * Created by Imissyou on 2016/4/20.
 */
public class OrderModelImpl implements OrderModel {
    private OrderPresenter mOrderPresenter;
    public OrderModelImpl(OrderPresenter orderPresenter) {
        this.mOrderPresenter = orderPresenter;
    }

    @Override
    public void loadData(BaseBean useBean) {
        //TODO
        //加载服务器数据

    }
}
