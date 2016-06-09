package com.miss.imissyou.mycar.model.impl;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.CarInfoModle;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.presenter.CarInfoPresenter;

/**
 * 获取单架车辆的信息
 * Created by Imissyou on 2016/5/3.
 */
public class CarInfoModleImpl implements CarInfoModle {

    private CarInfoPresenter mCarInfoPresenter;

    public CarInfoModleImpl(CarInfoPresenter carInfoPresenter) {
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

    @Override public void changeCarAlarmState(int state, Long carId) {
        String url = Constant.SERVER_URL + "car/updateCarAlarm"   ;
        HttpParams params = new HttpParams();
        params.put("id",carId + "");

        LogUtils.w("请求路径:" + url);
        RxVolley.post(url, params, new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                mCarInfoPresenter.onFailure(errorNo, strMsg);
            }

            @Override public void onSuccess(String t) {
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

    @Override public void changeCarState(int state, Long carId) {
        String url = Constant.SERVER_URL + "car/updateCarState"   ;
        HttpParams params = new HttpParams();
        params.put("id",carId + "");

        LogUtils.w("请求路径:" + url);

        RxVolley.post(url, params, new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                mCarInfoPresenter.onFailure(errorNo, strMsg);
            }

            @Override public void onSuccess(String t) {
                LogUtils.w(t);
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (!resultBean.isServiceResult()) {
                    onFailure(0, resultBean.getResultInfo());
                } else {
                    mCarInfoPresenter.onSuccess(resultBean);
                }
            }
        });

    }

    @Override public void setCurrentCar(Long userId, Long carId) {
        LogUtils.d("用户更改车辆Id:" + userId + ":::::" + carId);
        HttpParams params = new HttpParams();
        params.put("userId",userId+"");
        params.put("id",carId+"");

        String url = Constant.SERVER_URL + "car/updateUserCurrentCar";
        LogUtils.d("请求网络连接:" + url);
        RxVolley.post(url, params, new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                if (Constant.NETWORK_STATE == errorNo)
                    strMsg = "网络连接异常";
                mCarInfoPresenter.onFailure(errorNo,strMsg);
            }

            @Override public void onSuccess(String t) {
                LogUtils.d("获取到的数据" + t  + "车辆信息");
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (null != resultBean && resultBean.isServiceResult()) {
                    mCarInfoPresenter.setCurrentCarSuccess(resultBean);
                } else {
                    onFailure(0, resultBean.getResultInfo());
                }
            }
        });
    }
}
