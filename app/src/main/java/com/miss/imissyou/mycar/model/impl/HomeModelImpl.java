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

/**
 * Created by Imissyou on 2016/5/14.
 */
public class HomeModelImpl implements HomeModel {

    private HomePresenter homePresenter;

    public HomeModelImpl(HomePresenter homePresenter) {
        this.homePresenter = homePresenter;
    }

    @Override public void loadDataFormService(String carId) {
        HttpParams params = new HttpParams();
        params.putHeaders("cookie", Constant.COOKIE);

        //TODO

        String url = Constant.SERVER_URL + "url";
        LogUtils.d("请求的路径" + url);
        RxVolley.post(url, params, new HttpCallback() {
            @Override public void onSuccess(String t) {
                LogUtils.d("接受的数据:" + t);
                ResultBean resultBean = GsonUtils.getResultBean(t);
                homePresenter.onSuccess(resultBean);
            }

            @Override public void onFailure(int errorNo, String strMsg) {
                if (errorNo == Constant.NETWORK_STATE)
                    strMsg = Constant.NOTNETWORK;
                homePresenter.onFailure(errorNo, strMsg);
            }
        });
    }

}
