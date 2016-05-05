package com.miss.imissyou.mycar.model.impl;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.CarListModel;
import com.miss.imissyou.mycar.presenter.CarListPresenter;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.LinkService;
import com.miss.imissyou.mycar.util.StringUtil;

import java.nio.charset.Charset;

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
    public void loadData(String userId) {
        //TODO
        String url = Constant.SERVER_URL + "car/getAll";
        LinkService.Instance();

            LinkService.get(url, new HttpCallback() {
                @Override
                public void onFailure(int errorNo, String strMsg) {
                    mCarListPresenter.onFailure(errorNo, strMsg);
                }
                @Override
                public void onSuccess(String t) {
                    LogUtils.d(t);
                    ResultBean resultBean = GsonUtils.Instance().fromJson(t, ResultBean.class);
                    mCarListPresenter.onSuccess(resultBean);
                }
            });
    }
}
