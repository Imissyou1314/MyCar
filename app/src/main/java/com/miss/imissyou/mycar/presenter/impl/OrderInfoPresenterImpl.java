package com.miss.imissyou.mycar.presenter.impl;

import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.OrderBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.OrderInfoModel;
import com.miss.imissyou.mycar.model.impl.OrderInfoModelImpl;
import com.miss.imissyou.mycar.presenter.OrderInfoPresenter;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.view.OrderInfoView;

/**
 * Created by Imissyou on 2016/6/6.
 */
public class OrderInfoPresenterImpl implements OrderInfoPresenter {

    private OrderInfoView mOrderInfoView;
    private OrderInfoModel mOrderInfoModel;

    public OrderInfoPresenterImpl(OrderInfoView orderInfoFragment) {
        attachView(orderInfoFragment);
        mOrderInfoModel = new OrderInfoModelImpl(this);
    }

    @Override
    public void loadOrderFormService(Long OrderId) {
        mOrderInfoModel.loadOrderFormService(OrderId);
    }

    @Override
    public void onFailure(int errorNo, String strMsg) {
        mOrderInfoView.showResultError(errorNo, strMsg);
    }

    @Override
    public void onSuccess(BaseBean resultBean) {
        OrderBean orderBean = GsonUtils.getParam((ResultBean)resultBean, "order", OrderBean.class);
        if (null != orderBean) {
            mOrderInfoView.callBackOderBean(orderBean);
        } else {
            onFailure(0,"订单为空");
        }
    }

//    @Override
//    public void loadServiceData(BaseBean useBean) {
//
//    }

    @Override
    public void attachView(OrderInfoView view) {
        this.mOrderInfoView = view;
        mOrderInfoView.showProgress();
    }

    @Override
    public void detchView() {

    }
}
