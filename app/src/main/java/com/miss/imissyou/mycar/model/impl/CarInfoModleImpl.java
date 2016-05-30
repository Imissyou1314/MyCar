package com.miss.imissyou.mycar.model.impl;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.CarInfoModle;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.view.CarInfoPresenter;
import com.miss.imissyou.mycar.presenter.impl.CarInfoPresenterImpl;

/**
 * 获取单架车辆的信息
 * Created by Imissyou on 2016/5/3.
 */
public class CarInfoModleImpl implements CarInfoModle {

    private CarInfoPresenter mCarInfoPresenter;

    public CarInfoModleImpl(CarInfoPresenterImpl carInfoPresenter) {
        this.mCarInfoPresenter = carInfoPresenter;
    }


    @Override public void loadCarInfoFormService(Long userId, Long carId) {

        String  url = Constant.SERVER_URL + "car/car=" + carId;
        LogUtils.w("请求路径" + url);
        RxVolley.get(url,new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                mCarInfoPresenter.onFailure(errorNo, strMsg);
            }

            @Override public void onSuccess(String t) {
                LogUtils.w(t);
                ResultBean resultBean = GsonUtils.Instance().fromJson(t, ResultBean.class);
                mCarInfoPresenter.onSuccess(resultBean);
            }
        });
    }

    @Override
    public void changeCarAlarmState(int state, Long carId) {
        String url = Constant.SERVER_URL + "car/updateCarAlarm"   ;
        HttpParams params = new HttpParams();
        params.put("id",carId + "");

        LogUtils.w("请求路径:" + url);
        RxVolley.post(url, params, new HttpCallback() {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                mCarInfoPresenter.onFailure(errorNo, strMsg);
            }

            @Override
            public void onSuccess(String t) {
                LogUtils.w(t);
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (!resultBean.isServiceResult()) {
                    onFailure(0, resultBean.getResultInfo());
                }             else {
                    mCarInfoPresenter.onSuccess(resultBean);
                }

            }
        });

    }

    @Override
    public void changeCarState(int state, Long carId) {
        String url = Constant.SERVER_URL + "car/updateCarState"   ;
        HttpParams params = new HttpParams();
        params.put("id",carId + "");

        LogUtils.w("请求路径:" + url);

        RxVolley.post(url, params, new HttpCallback() {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                mCarInfoPresenter.onFailure(errorNo, strMsg);
            }

            @Override
            public void onSuccess(String t) {
                LogUtils.w(t);
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (!resultBean.isServiceResult()) {
                    onFailure(0, resultBean.getResultInfo());
                }             else {
                    mCarInfoPresenter.onSuccess(resultBean);
                }

            }
        });

    }
}
