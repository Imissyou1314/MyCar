package com.miss.imissyou.mycar.model.impl;

import com.kymjs.rxvolley.client.HttpCallback;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.OrderInfoModel;
import com.miss.imissyou.mycar.presenter.OrderInfoPresenter;
import com.miss.imissyou.mycar.presenter.impl.OrderInfoPresenterImpl;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.RxVolleyUtils;

import java.util.Map;

/**
 * 请求服务器获取订单信息
 * Created by Imissyou on 2016/6/6.
 */
public class OrderInfoModelImpl implements OrderInfoModel {

    private OrderInfoPresenter mOrderInfoPresenter;
    public OrderInfoModelImpl(OrderInfoPresenterImpl orderInfoPresenter) {
        this.mOrderInfoPresenter = orderInfoPresenter;
    }

    @Override
    public void loadOrderFormService(long orderId) {
        String url = Constant.SERVER_URL +"order/getOrderById=" + orderId;

        LogUtils.w("请求路径:" + url);

        HttpCallback callback = new HttpCallback() {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                mOrderInfoPresenter.onFailure(errorNo, strMsg);
            }

            @Override
            public void onSuccess(Map<String, String> headers, byte[] t) {
                Constant.COOKIE = headers.get("cookie");
            }

            @Override
            public void onSuccess(String t) {

                LogUtils.d("获取到的数据" + t);
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (resultBean.isServiceResult()) {
                    mOrderInfoPresenter.onSuccess(resultBean);
                } else {
                    if (resultBean.getResultInfo().equals(Constant.FileCOOKIE)) {
                        RxVolleyUtils.getInstance().restartLogin();
                    } else {
                        mOrderInfoPresenter.onFailure(0, resultBean.getResultInfo());
                    }
                }
            }
        };

        RxVolleyUtils.getInstance().get(url, null, callback);

//        new RxVolley.Builder()
//                .shouldCache(false)
//                .url(url)
//                .callback(callback)
//                .httpMethod(RxVolley.Method.GET)
//                .cacheTime(0)
//                .doTask();

    }
}
