package com.miss.imissyou.mycar.model.impl;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.CarListModel;
import com.miss.imissyou.mycar.presenter.CarListPresenter;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;

/**
 * 进行网络数据请求
 * CarInfo的  Model
 * Created by Imissyou on 2016/4/20.
 */
public class CarListModelImpl implements CarListModel {
    private CarListPresenter mCarListPresenter;

    public CarListModelImpl(CarListPresenter carListPresenter) {
        this.mCarListPresenter = carListPresenter;
    }


    @Override
    public void loadAllCarInfoData(String userId) {
        String url = Constant.SERVER_URL + "car/userId=" + userId;
        HttpCallback callback = new HttpCallback() {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                mCarListPresenter.onFailure(errorNo, strMsg);
            }

            @Override
            public void onSuccess(String t) {

                LogUtils.w(t);
                ResultBean resultBean = GsonUtils.Instance().fromJson(t, ResultBean.class);
                mCarListPresenter.onSuccess(resultBean);
            }
        };


        LogUtils.w("请求路径:" + url);
        new RxVolley.Builder()
                .httpMethod(RxVolley.Method.GET)
                .encoding("utf-8")
                .url(url)
                .callback(callback)
                .timeout(5000)
                .shouldCache(false)
                .cacheTime(0)
                .doTask();
    }
}
