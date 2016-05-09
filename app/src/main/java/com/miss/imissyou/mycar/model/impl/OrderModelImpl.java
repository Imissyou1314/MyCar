package com.miss.imissyou.mycar.model.impl;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.OrderModel;
import com.miss.imissyou.mycar.presenter.OrderPresenter;
import com.miss.imissyou.mycar.presenter.impl.OrderPresenterImpl;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.view.MainView;

/**
 * Created by Imissyou on 2016/4/20.
 */
public class OrderModelImpl implements OrderModel {
    private OrderPresenter mOrderPresenter;
    public OrderModelImpl(OrderPresenter orderPresenter) {
        this.mOrderPresenter = orderPresenter;
    }

    @Override public void loadData(BaseBean useBean) {

        String url = Constant.SERVER_URL + "order/getAll";
        LogUtils.d("请求路径:" + url);
        RxVolley.get(url, new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                mOrderPresenter.onFailure(errorNo, strMsg);
            }

            @Override public void onSuccess(String t) {
                LogUtils.d(t);
                ResultBean resultBean = GsonUtils.Instance().fromJson(t, ResultBean.class);
                mOrderPresenter.onSuccess(resultBean);
            }
        });

    }
}
