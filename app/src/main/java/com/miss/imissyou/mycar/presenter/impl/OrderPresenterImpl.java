package com.miss.imissyou.mycar.presenter.impl;

import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.OrderModel;
import com.miss.imissyou.mycar.model.impl.OrderModelImpl;
import com.miss.imissyou.mycar.presenter.OrderPresenter;
import com.miss.imissyou.mycar.view.MainView;
import com.miss.imissyou.mycar.view.fragment.OrderFragment;

/**
 * Created by Imissyou on 2016/4/20.
 */
public class OrderPresenterImpl implements OrderPresenter<MainView> {
    private OrderFragment mOrderFragment;
    private OrderModel mOrderModel;

    public OrderPresenterImpl(OrderFragment orderFragment) {
        attachView(orderFragment);
        mOrderModel = new OrderModelImpl(this);
        mOrderFragment.hideProgress();
    }

    @Override public void onFailure(int errorNo, String strMsg) {
        mOrderFragment.hideProgress();
        mOrderFragment.showResultError(errorNo, strMsg);
    }

    @Override public void onSuccess(BaseBean resultBean) {
        mOrderFragment.hideProgress();
        if (resultBean != null) {
            mOrderFragment.showResultSuccess((ResultBean) resultBean);
        } else {
            mOrderFragment.showResultError(0, "json异常");
        }
    }

    @Override public void loadServiceData(BaseBean useBean) {
        if (useBean != null)
            mOrderModel.loadData(useBean);

    }

    @Override public void attachView(Object view) {
        this.mOrderFragment = (OrderFragment) view;
    }


    @Override public void detchView() {
        this.mOrderFragment = null;
    }
}
