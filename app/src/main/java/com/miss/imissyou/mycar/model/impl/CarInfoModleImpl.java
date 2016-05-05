package com.miss.imissyou.mycar.model.impl;

import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.CarInfoModle;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.LinkService;
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


    @Override public void loadCarInfoFormService(String userId, String carId) {

        String  url = Constant.SERVER_URL + "car/car=" + carId;

        LinkService.Instance();
        LinkService.get(url,new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                mCarInfoPresenter.onFailure(errorNo, strMsg);
            }

            @Override public void onSuccess(String t) {
                LogUtils.d(t);
                ResultBean resultBean = GsonUtils.Instance().fromJson(t, ResultBean.class);
                mCarInfoPresenter.onSuccess(resultBean);
            }
        });
    }
}
