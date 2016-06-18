package com.miss.imissyou.mycar.model.impl;

import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.CarListModel;
import com.miss.imissyou.mycar.presenter.CarListPresenter;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.RxVolleyUtils;

import java.util.Map;

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
                if (resultBean.isServiceResult()) {
                    mCarListPresenter.onSuccess(resultBean);
                } else {
                    if (resultBean.getResultInfo().equals(Constant.FileCOOKIE)) {
                        RxVolleyUtils.getInstance().restartLogin();
                    } else {
                        onFailure(0, resultBean.getResultInfo());
                    }
                }
            }
        };
        LogUtils.w("请求路径:" + url);
        RxVolleyUtils.getInstance().get(url, null, callback);
    }

    @Override public void delectCar(Long carId) {
        String url = Constant.SERVER_URL + "car/delete";
        LogUtils.w("请求地址:" + url + "车辆Id" + carId);

        HttpParams params = new HttpParams();
        params.put("id",carId+"");

        RxVolleyUtils.getInstance().post(url, params, new HttpCallback() {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                if (errorNo == Constant.NETWORK_STATE)
                    strMsg = Constant.NOTNETWORK;
                mCarListPresenter.onFailure(errorNo,strMsg);
            }


            @Override
            public void onSuccess(String t) {
                LogUtils.w("请求结果:" + t);
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (resultBean.isServiceResult()) {
                    mCarListPresenter.delectSuccess(resultBean);
                } else {
                    if (resultBean.getResultInfo().equals(Constant.FileCOOKIE)) {
                        RxVolleyUtils.getInstance().restartLogin();
                    } else {
                        onFailure(0, resultBean.getResultInfo());
                    }
                }
            }
        });
    }
}
