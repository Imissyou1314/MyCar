package com.miss.imissyou.mycar.model.impl;

import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.OrderBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.SumbitIndentModel;
import com.miss.imissyou.mycar.presenter.SumbitIndentPresenter;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;

/**
 * 向服务器递交订单
 * Created by Imissyou on 2016/4/25.
 */
public class SumbitIndentModelImpl implements SumbitIndentModel {

    private SumbitIndentPresenter mSumbitIndentPresenter;

    public SumbitIndentModelImpl(SumbitIndentPresenter sumbitIndentPresenter) {
        mSumbitIndentPresenter = sumbitIndentPresenter;
    }

    @Override
    public void sentIndentToService(OrderBean orderBean) {
        //TODO
        String url = Constant.SERVER_URL + "order/saveOrder";
        HttpParams params = new HttpParams();
        params.put("userId", orderBean.getUserId() + "");
        params.put("carId", orderBean.getCarId() + "");
        params.put("stationName", orderBean.getStationName());
        params.put("address", orderBean.getAddress());
        params.put("brandName", orderBean.getBrandName());
        params.put("agreementTime", orderBean.getAgreementTime());
        params.put("type", orderBean.getType());
        params.put("units", orderBean.getUnits());
        params.put("price", orderBean.getPrice() + "");
        params.put("number", orderBean.getNumber() + "");
        params.put("amounts", orderBean.getAmounts());
        params.put("state", orderBean.getState());

        LogUtils.d("提交订单的URL:" + url);
        RxVolley.post(url, params, new HttpCallback() {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                mSumbitIndentPresenter.onFailure(errorNo, strMsg);
            }

            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                ResultBean resultBean = GsonUtils.Instance().fromJson(t, ResultBean.class);
                if (resultBean != null) {
                    mSumbitIndentPresenter.onSuccess(resultBean);
                } else {
                    mSumbitIndentPresenter.onFailure(0, "解析数据失败");
                }
            }
        });
    }

    @Override
    public void deleteOrder(int userId, int orderId) {

        //等待测试
        String url = Constant.SERVER_URL + "order/delete";
        HttpParams params = new HttpParams();
        params.put("id", orderId);

        RxVolley.post(url, params, new HttpCallback() {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                if (errorNo == Constant.NETWORK_STATE)
                    strMsg = Constant.NOTNETWORK;
                mSumbitIndentPresenter.onFailure(errorNo, strMsg);
            }

            @Override
            public void onSuccess(String t) {
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (resultBean.isServiceResult()) {
                    mSumbitIndentPresenter.onSuccess(resultBean);
                } else {
                    onFailure(0, resultBean.getResultInfo());
                }
            }
        });

    }
}
