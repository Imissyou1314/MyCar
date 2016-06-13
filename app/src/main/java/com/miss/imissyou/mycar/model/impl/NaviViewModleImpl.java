package com.miss.imissyou.mycar.model.impl;

import com.amap.api.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.GasStationResultBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.NaviViewModle;
import com.miss.imissyou.mycar.presenter.NaviViewPresenter;
import com.miss.imissyou.mycar.presenter.impl.NaviViewPresenterImpl;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;

/**
 * Created by Imissyou on 2016/6/12.
 */
public class NaviViewModleImpl implements NaviViewModle {

    private NaviViewPresenter mNaviViewPressenter;

    @Override
    public void loadGasStation(Double lon, Double lat) {


        //Todo  获取加油站要调用其他的接口
        HttpParams param = new HttpParams();
         param.put("lon",lon + "");
        param.put("lat",lat + "");

//        param.put("lon","121.538123");
//        param.put("lat","31.677123");
        param.put("r", + Constant.GET_GASSTATION_R);
        param.put("page", 1);
        param.put("key", Constant.GET_GASSTATION_KEY);
        param.put("format", 1);

        String url = "http://apis.juhe.cn/oil/local";
        LogUtils.w("请求路径：" + url);

        RxVolley.post(url, param, new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                mNaviViewPressenter.loadFail(errorNo, strMsg);
            }

            @Override public void onSuccess(String t) {
                LogUtils.d("获取加油站的信息：" + t);

                Gson gsonBuilder =new GsonBuilder().create();
                GasStationResultBean gasStationResultBean = gsonBuilder.fromJson(t, GasStationResultBean.class);
                LogUtils.w("获取加油站的信息:" + t);

                if (gasStationResultBean != null) {
                     mNaviViewPressenter.loadSuccessGasStation(gasStationResultBean);
                }
            }
        });
    }

    @Override
    public void loadPack(LatLng latLng) {
        String url = Constant.SERVER_URL + "park/getAroundPark";
        LogUtils.d("请求URL" + url);
        loadMessage(latLng, url, Constant.MAP_PARK);
    }

    @Override
    public void loadRepairShop(LatLng latLng) {
        String url = Constant.SERVER_URL + "repairShop/getAroundShop";
        LogUtils.d("请求URL" + url);
        loadMessage(latLng, url, Constant.MAP_MAINTAIN);
    }

    public NaviViewModleImpl(NaviViewPresenter naviViewPresenter) {
        this.mNaviViewPressenter = naviViewPresenter;
    }


    /**
     * 请求网络连接
     * @param latLng  经纬度
     * @param url     请求地址
     * @param Tag     标志
     */
    private void loadMessage(LatLng latLng, String url, final String Tag) {
        if (latLng == null ) {
            LogUtils.d("请求失败");
            return;
        }
        LogUtils.w("请求的经纬度：：：" + latLng.latitude  + "::"
                + latLng.longitude + "》请求类型：：" + Tag);
        HttpParams params = new HttpParams();
        params.put("lat",latLng.latitude + "");
        params.put("lon",latLng.longitude + "");


        HttpCallback callback = new HttpCallback() {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                if (errorNo == Constant.NETWORK_STATE)
                    strMsg = Constant.NOTNETWORK;
                mNaviViewPressenter.loadFail(errorNo,strMsg);
            }

            @Override
            public void onSuccess(String t) {
                LogUtils.d("获取到的数据是:" + t);
                ResultBean result = GsonUtils.getResultBean(t);
                if (result.isServiceResult()) {
                    mNaviViewPressenter.loadSuccess(Tag, result);
                } else {
                    onFailure(0,result.getResultInfo().equals("") ? "没有数据": result.getResultInfo());
                }
            }
        };

        new RxVolley.Builder()
                .params(params)
                .url(url)
                .httpMethod(RxVolley.Method.GET)
                .shouldCache(false)
                .callback(callback)
                .cacheTime(0)
                .doTask();
    }

}
