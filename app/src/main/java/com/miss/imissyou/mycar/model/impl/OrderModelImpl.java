package com.miss.imissyou.mycar.model.impl;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.bean.UserBean;
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

    @Override
    public void delectOrder(Long orderId) {
        String url = Constant.SERVER_URL + "order/delectOrder";

        HttpParams params = new HttpParams();
        params.put("id",orderId + "");
        LogUtils.w("请求地址");
        RxVolley.post(url, params, new HttpCallback() {
            @Override
            public void onFailure(int errorNo, String strMsg) {

                if (errorNo == Constant.NETWORK_STATE)
                    strMsg = Constant.NOTNETWORK;
                mOrderPresenter.onFailure(errorNo,strMsg);
            }

            @Override
            public void onSuccess(String t) {
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (resultBean.isServiceResult()) {
                    mOrderPresenter.delectOrderSuccess(resultBean);
                } else {
                    mOrderPresenter.onFailure(0,resultBean.getResultInfo());
                }
            }

        });
    }

    public OrderModelImpl(OrderPresenter orderPresenter) {
        this.mOrderPresenter = orderPresenter;
    }

    @Override public void loadOrderData(BaseBean useBean) {
        // TODO: 2016-06-10 更改接口 
        if ( null != Constant.userBean && null != Constant.userBean.getId()) {
            String url = Constant.SERVER_URL + "order/getUserOrder=" + Constant.userBean.getId();
            LogUtils.d("请求路径:" + url);
            HttpCallback callback =  new HttpCallback() {
                @Override
                public void onFailure(int errorNo, String strMsg) {
                    mOrderPresenter.onFailure(errorNo, strMsg);
                }

                @Override
                public void onSuccess(String t) {
                    LogUtils.d("获取到的数据" + t);
                    ResultBean resultBean = GsonUtils.Instance().fromJson(t, ResultBean.class);
                    mOrderPresenter.onSuccess(resultBean);
                }
            };

            new RxVolley.Builder()
                    .cacheTime(0)
                    .shouldCache(false)
                    .httpMethod(RxVolley.Method.GET)
                    .callback(callback)
                    .url(url)
                    .doTask();

        } else {
            mOrderPresenter.onFailure(0,"用户没有登录");
        }
    }
}
