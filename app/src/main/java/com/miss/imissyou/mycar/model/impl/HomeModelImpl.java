package com.miss.imissyou.mycar.model.impl;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.HomeModel;
import com.miss.imissyou.mycar.presenter.HomePresenter;
import com.miss.imissyou.mycar.presenter.impl.HomePresenterImpl;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.RxVolleyUtils;

import java.util.Map;

/**
 * Created by Imissyou on 2016/5/14.
 */
public class HomeModelImpl implements HomeModel {

    private HomePresenter homePresenter;

    public HomeModelImpl(HomePresenter homePresenter) {
        this.homePresenter = homePresenter;
    }

    @Override public void loadDataFormService(String carId) {

        String url = Constant.SERVER_URL + "car/car=" + carId;
        LogUtils.d("请求的路径" + url);
        HttpCallback callback = new HttpCallback() {
            @Override public void onSuccess(String t) {
                LogUtils.d("接受的数据:" + t);
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (resultBean.isServiceResult()) {
                    homePresenter.onSuccess(resultBean);
                } else {
                    if (resultBean.getResultInfo().equals(Constant.FileCOOKIE)) {
                        RxVolleyUtils.getInstance().restartLogin();
                    } else {
                        onFailure(0,resultBean.getResultInfo());
                    }
                }
            }

            @Override public void onFailure(int errorNo, String strMsg) {
                if (errorNo == Constant.NETWORK_STATE)
                    strMsg = Constant.NOTNETWORK;
                homePresenter.onFailure(errorNo, strMsg);
            }
        };

        RxVolleyUtils.getInstance().get(url, null, callback);
    }

}
