package com.miss.imissyou.mycar.model.impl;

import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.CarInfoModle;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.presenter.CarInfoPresenter;
import com.miss.imissyou.mycar.util.RxVolleyUtils;

import java.util.Map;

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

        HttpCallback callback = new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                mCarInfoPresenter.onFailure(errorNo, strMsg);
            }

            @Override
            public void onSuccess(Map<String, String> headers, byte[] t) {
                Constant.COOKIE = headers.get("cookie");
            }

            @Override public void onSuccess(String t) {
                LogUtils.w(t);
                ResultBean resultBean = GsonUtils.Instance().fromJson(t, ResultBean.class);
                if (resultBean.isServiceResult()) {
                    mCarInfoPresenter.onSuccess(resultBean);
                } else {
                    if (resultBean.getResultInfo().equals(Constant.FileCOOKIE)) {
                        RxVolleyUtils.getInstance().restartLogin();
                    } else {
                        onFailure(0, resultBean.getResultInfo());
                    }
                }
            }
        };
        String  url = Constant.SERVER_URL + "car/car=" + carId;

        LogUtils.w("请求路径:" + url);
        RxVolleyUtils.getInstance().get(url, null, callback);

//        new RxVolley.Builder()
//                .httpMethod(RxVolley.Method.GET)
//                .encoding("utf-8")
//                .url(url)
//                .callback(callback)
//                .timeout(5000)
//                .shouldCache(false)
//                .cacheTime(0)
//                .doTask();
    }

    @Override public void changeCarAlarmState(int state, Long carId) {
        String url = Constant.SERVER_URL + "car/updateCarAlarm"   ;
        HttpParams params = new HttpParams();
        params.put("id",carId + "");

        LogUtils.w("请求路径:" + url);
        RxVolleyUtils.getInstance().post(url, params, new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                mCarInfoPresenter.onFailure(errorNo, strMsg);
            }

            @Override
            public void onSuccess(Map<String, String> headers, byte[] t) {
                Constant.COOKIE = headers.get("cookie");
            }

            @Override public void onSuccess(String t) {
                LogUtils.w(t);
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (resultBean.isServiceResult()) {
                    mCarInfoPresenter.onSuccess(resultBean);
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

    @Override public void changeCarState(int state, Long carId) {
        String url = Constant.SERVER_URL + "car/updateCarState"   ;
        HttpParams params = new HttpParams();
        params.put("id",carId + "");

        LogUtils.w("请求路径:" + url);

        RxVolleyUtils.getInstance().post(url, params, new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                mCarInfoPresenter.onFailure(errorNo, strMsg);
            }

            @Override
            public void onSuccess(Map<String, String> headers, byte[] t) {
                Constant.COOKIE = headers.get("cookie");
            }

            @Override public void onSuccess(String t) {
                LogUtils.w(t);
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (resultBean.isServiceResult()) {
                    mCarInfoPresenter.onSuccess(resultBean);
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


    @Override public void setCurrentCar(Long userId, Long carId) {
        LogUtils.d("用户更改车辆Id:" + userId + ":::::" + carId);
        HttpParams params = new HttpParams();
        params.put("userId",userId+"");
        params.put("id",carId+"");

        String url = Constant.SERVER_URL + "car/updateUserCurrentCar";
        LogUtils.d("请求网络连接:" + url);
        RxVolleyUtils.getInstance().post(url, params, new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                if (Constant.NETWORK_STATE == errorNo)
                    strMsg = "网络连接异常";
                mCarInfoPresenter.onFailure(errorNo,strMsg);
            }

            @Override
            public void onSuccess(Map<String, String> headers, byte[] t) {
                Constant.COOKIE = headers.get("cookie");
            }

            @Override public void onSuccess(String t) {
                LogUtils.d("获取到的数据" + t  + "车辆信息");
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (null != resultBean && resultBean.isServiceResult()) {
                    mCarInfoPresenter.setCurrentCarSuccess(resultBean);
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

    @Override
    public void getUserCurrentCar(Long userId) {
        HttpCallback callback = new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                mCarInfoPresenter.onFailure(errorNo, strMsg);
            }

            @Override
            public void onSuccess(Map<String, String> headers, byte[] t) {
                Constant.COOKIE = headers.get("cookie");
            }

            @Override public void onSuccess(String t) {
                LogUtils.w(t);
                ResultBean resultBean = GsonUtils.Instance().fromJson(t, ResultBean.class);
                if (resultBean.isServiceResult()) {
                    mCarInfoPresenter.onSuccess(resultBean);
                } else {
                    if (resultBean.getResultInfo().equals(Constant.FileCOOKIE)) {
                        RxVolleyUtils.getInstance().restartLogin();
                    } else {
                        onFailure(0,resultBean.getResultInfo());
                    }
                }
            }
        };
        String  url = Constant.SERVER_URL + "car/currentCar=" + userId;

        LogUtils.w("请求路径:" + url);
        RxVolleyUtils.getInstance().get(url, null, callback);
//        new RxVolley.Builder()
//                .httpMethod(RxVolley.Method.GET)
//                .encoding("utf-8")
//                .url(url)
//                .callback(callback)
//                .timeout(5000)
//                .shouldCache(false)
//                .cacheTime(0)
//                .doTask();
    }

    @Override
    public void changeCartoStop(Long carId) {
        String url = Constant.SERVER_URL + "car/updateCarState"   ;
        HttpParams params = new HttpParams();
        params.put("id",carId + "");

        LogUtils.w("请求路径:" + url);

        RxVolleyUtils.getInstance().post(url, params, new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                mCarInfoPresenter.onFailure(errorNo, strMsg);
            }
            @Override
            public void onSuccess(Map<String, String> headers, byte[] t) {
                Constant.COOKIE = headers.get("cookie");
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

    @Override
    public void changeCarPlatNumber(CarInfoBean carInfoBean) {
        String url = Constant.SERVER_URL + "car/update"   ;
        HttpParams params = new HttpParams();
        params.put("id", carInfoBean.getId() + "");
        params.put("plateNumber",carInfoBean.getPlateNumber());

        LogUtils.w("请求路径:" + url);

        RxVolleyUtils.getInstance().post(url, params, new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                mCarInfoPresenter.onFailure(errorNo, strMsg);
            }

            @Override
            public void onSuccess(Map<String, String> headers, byte[] t) {
                Constant.COOKIE = headers.get("cookie");
            }

            @Override public void onSuccess(String t) {
                LogUtils.w(t);
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (resultBean.isServiceResult()) {
                    mCarInfoPresenter.onSuccess(resultBean);
                } else if (resultBean.getResultInfo() == Constant.FileCOOKIE){
                    RxVolleyUtils.getInstance().restartLogin();
                } else {
                    onFailure(0, resultBean.getResultInfo());
                }
            }
        });
    }
}
