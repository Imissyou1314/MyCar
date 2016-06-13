package com.miss.imissyou.mycar.presenter.impl;

import com.amap.api.maps.model.LatLng;
import com.lidroid.xutils.util.DoubleKeyValueMap;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.GasStationBean;
import com.miss.imissyou.mycar.bean.GasStationResultBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.NaviViewModle;
import com.miss.imissyou.mycar.model.impl.NaviViewModleImpl;
import com.miss.imissyou.mycar.presenter.NaviViewPresenter;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.view.NaviVieFragmentView;

import java.util.List;

/**
 * Created by Imissyou on 2016/6/12.
 */
public class NaviViewPresenterImpl implements NaviViewPresenter {

    private NaviVieFragmentView mNaviView;
    private NaviViewModle mNaviViewModle;

    public NaviViewPresenterImpl(NaviVieFragmentView naviViewFragment) {
        this.mNaviView = naviViewFragment;
        mNaviViewModle = new NaviViewModleImpl(this);
    }

    @Override
    public void loadSuccess(String Tag, ResultBean resultBean) {
        LogUtils.d("分装数据");
        switch (Tag) {
            case Constant.MAP_MAINTAIN:
                mNaviView.loadSuccessRepairSHop(resultBean);
                break;
            case Constant.MAP_PARK:
                mNaviView.loadSucccessPark(resultBean);
                break;
        }
    }

    @Override
    public void loadSuccessGasStation(GasStationResultBean resultBean) {
        if (resultBean.getResultcode() == Constant.HTTP_OK) {
            mNaviView.loadSuccessGasStation(resultBean);
        } else if (resultBean.getResultcode() == 205 ){
            mNaviView.loadFail(2, "附近10公里没有加油站");
        } else {
            mNaviView.loadFail(1,"获取不到数据");
        }
    }

    @Override
    public void loadFail(int errorNumber, String errMsg) {
        LogUtils.d("获取数据失败"+ errMsg);
        mNaviView.loadFail(errorNumber, errMsg);
    }

    @Override
    public void loadPack(LatLng latLng) {
        if (null != mNaviViewModle) {
            mNaviViewModle.loadPack(latLng);
        }
    }

    @Override
    public void loadGasStation(Double lon, Double lat) {
        if (null != mNaviViewModle) {
            mNaviViewModle.loadGasStation(lon, lat);
        }
    }

    @Override
    public void loadRepairShop(LatLng latLng) {
        if (null != mNaviViewModle) {
            mNaviViewModle.loadRepairShop(latLng);
        }
    }
}
