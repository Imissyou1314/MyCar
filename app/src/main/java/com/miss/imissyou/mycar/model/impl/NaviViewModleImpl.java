package com.miss.imissyou.mycar.model.impl;

import com.amap.api.maps.model.LatLng;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
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
    public void loadGasStation(LatLng latLng) {

        String url = Constant.SERVER_URL + "repairShop/getAroundShop";
        LogUtils.d("获取附近加油站" + url);
        //Todo  获取加油站要调用其他的接口
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
        HttpParams params = new HttpParams();
        params.put("latitude",latLng.latitude + "");
        params.put("longitudel",latLng.longitude + "");


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
