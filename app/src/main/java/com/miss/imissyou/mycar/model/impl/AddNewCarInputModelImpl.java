package com.miss.imissyou.mycar.model.impl;


import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.AddNewCarInputModel;
import com.miss.imissyou.mycar.presenter.AddNewCarInputPresenter;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.RxVolleyUtils;
import com.miss.imissyou.mycar.util.SPUtils;

import java.util.Map;

/**
 * 扫描车辆信息
 * Created by Imissyou on 2016/4/18.
 */
public class AddNewCarInputModelImpl implements AddNewCarInputModel {

    private AddNewCarInputPresenter addNewCarInputPresenter;

    public AddNewCarInputModelImpl( AddNewCarInputPresenter addNewCarInputPresenter) {
        this.addNewCarInputPresenter = addNewCarInputPresenter;
    }

    @Override public void sentToService(CarInfoBean carInfoBean) {

        HttpParams params = new HttpParams();
        params.putJsonParams(GsonUtils.Instance().toJson(carInfoBean));

        LogUtils.w("newCar:" + GsonUtils.Instance().toJson(carInfoBean));
        String url = Constant.SERVER_URL + "car/saveCar";
        LogUtils.w("请求路径：" + url);

        HttpCallback callback =  new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                addNewCarInputPresenter.onFailure(errorNo,strMsg);
            }

            @Override public void onSuccess(String t) {
                LogUtils.d("收到的数据"  + t);

                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (resultBean.isServiceResult()) {
                    addNewCarInputPresenter.onAddCarSuccess(t);
                } else {
                    if (resultBean.getResultInfo().equals(Constant.FileCOOKIE)) {
                        RxVolleyUtils.getInstance().restartLogin();
                    } else {
                        onFailure(0, resultBean.getResultInfo());
                    }
                }
            }
        };

        RxVolleyUtils.getInstance().post(url,params,callback);

    }

    @Override public void saveToSPU(BaseBean baseBean, String key) {
        CarInfoBean carInfoBean = (CarInfoBean) baseBean;
        SPUtils.putObject(key,carInfoBean);
    }

    @Override public void loadCar(String url) {

        LogUtils.w("请求路径：::" + url);
//        String missurl = Constant.SERVER_URL + "car/vin=123456";
//        LogUtils.d("请求路径：" + missurl);


        HttpCallback httpCallback = new HttpCallback() {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                addNewCarInputPresenter.onFailure(errorNo, strMsg);
            }

            @Override
            public void onSuccess(String t) {

                            LogUtils.w("返回结果："+t);
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (resultBean.isServiceResult()) {
                    addNewCarInputPresenter.onSuccess(t);
                } else {
                    if (resultBean.getResultInfo().equals(Constant.FileCOOKIE)) {
                        RxVolleyUtils.getInstance().restartLogin();
                    } else {
                        onFailure(0,resultBean.getResultInfo());
                    }
                }
            }
        };

        RxVolleyUtils.getInstance().get(url,null, httpCallback);

    }
}
