package com.miss.imissyou.mycar.presenter.impl;

import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.GasStationBean;
import com.miss.imissyou.mycar.bean.GasStationResultBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.GasStationModle;
import com.miss.imissyou.mycar.model.impl.GasStationModelImpl;
import com.miss.imissyou.mycar.presenter.GasStationPresenter;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.view.GasStationView;
import com.miss.imissyou.mycar.view.fragment.GasStationFragment;

import java.util.List;

/**
 * 加油站列表的PresenterImpl
 * Created by Imissyou on 2016/4/24.
 */
public class GasStationPresenterImpl implements GasStationPresenter {

    private  GasStationView mGasStationListView;
    private GasStationModle mGasStationModelImpl;

    public GasStationPresenterImpl(GasStationFragment gasStationList) {
        attachView(gasStationList);
        mGasStationModelImpl = new GasStationModelImpl(this);

    }

    @Override public void onFailure(int errorNo, String strMsg) {
        mGasStationListView.hideProgress();
    }

    @Override public void onSuccess(BaseBean resultBean) {
        if (((GasStationResultBean)resultBean).getResultcode() == Constant.HTTP_OK) {
            List<GasStationBean> resultBeans = ((GasStationResultBean)resultBean).getResult().getData();
            LogUtils.d("加油站的数量" + resultBeans.size());
            mGasStationListView.showResultSuccess(resultBeans);
        } else {
            mGasStationListView.showResultError(0, "连接网络失败");
        }
        mGasStationListView.hideProgress();
    }

    @Override public void loadServiceData(BaseBean useBean) {

    }

    @Override public void attachView(GasStationView view) {
        this.mGasStationListView = view;
        mGasStationListView.showProgress();
    }

    @Override public void detchView() {
        mGasStationListView = null;
    }


    @Override
    public void loadServiceData(double lon, double lat, int r, String key, int page, int format) {
        //获取经纬度
        if (r == 0 ) {
            r = Constant.GET_GASSTATION_R;
        } else if (key.equals("") || key == null) {
            key = Constant.GET_GASSTATION_KEY;
        }
        //请求网络获取加油站的信息
        LogUtils.d("获取加油站信息");
        mGasStationModelImpl.loadGasStationData(lon, lat, r, page, key, format);
    }
}
