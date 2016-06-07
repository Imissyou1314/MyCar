package com.miss.imissyou.mycar.model.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.miss.imissyou.mycar.bean.GasStationResultBean;
import com.miss.imissyou.mycar.model.GasStationModle;
import com.miss.imissyou.mycar.presenter.GasStationPresenter;
import com.miss.imissyou.mycar.presenter.impl.GasStationPresenterImpl;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.util.GsonUtils;

/**
 * 负者网络请求
 * Created by Imissyou on 2016/4/24.
 */
public class GasStationModelImpl implements GasStationModle {

    private GasStationPresenter gasStation;

    public GasStationModelImpl(GasStationPresenterImpl mGasStationPresenter) {
        this.gasStation = mGasStationPresenter;
    }

    @Override public void loadGasStationData(double lon, double lat, int r, int page, String key, int format) {

        HttpParams param = new HttpParams();
       // param.put("lon",lon + "");
        //param.put("lat",lat + "");

        param.put("lon","121.538123");
        param.put("lat","31.677123");
        param.put("r", + r);
        param.put("page", page);
        param.put("key", key);
        param.put("format", format);

        String url = "http://apis.juhe.cn/oil/local";
        LogUtils.w("请求路径：" + url);

        RxVolley.post(url, param, new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                gasStation.onFailure(errorNo, strMsg);
            }

            @Override public void onSuccess(String t) {
                LogUtils.d("获取加油站的信息：" + t);

                Gson gsonBuilder =new GsonBuilder().create();
                GasStationResultBean gasStationResultBean = gsonBuilder.fromJson(t, GasStationResultBean.class);
               // GasStationResultBean  gasStationResultBean = GsonUtils.Instance().fromJson(t, GasStationResultBean.class);
                LogUtils.w("获取加油站的信息:" + t);

                if (gasStationResultBean != null) {
                    gasStation.onSuccess(gasStationResultBean);
                }
            }
        });
    }
}
