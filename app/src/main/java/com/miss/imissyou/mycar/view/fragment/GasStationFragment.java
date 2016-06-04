package com.miss.imissyou.mycar.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.GasStationBean;

import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.impl.GasStationPresenterImpl;
import com.miss.imissyou.mycar.ui.adapterutils.CommonAdapter;
import com.miss.imissyou.mycar.ui.adapterutils.ViewHolder;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.DialogUtils;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.MapChangeUtils;
import com.miss.imissyou.mycar.view.GasStationView;

import com.miss.imissyou.mycar.presenter.GasStationPresenter;

import java.util.List;

/**
 * 加油站的信息列表
 * Created by Imissyou on 2016/5/9.
 */
public class GasStationFragment extends BaseFragment implements GasStationView, AMapLocationListener {

    private static final String TAG = "GASSTATIONFRAGMENT" ;
    private ListView gasListView;       //加油站的列表
    private GasStationPresenter mGasStationPresenter;
    private List<GasStationBean> gasStationBeens;       //加油站的列表

    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    @Nullable
    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_gasstation, inflater, container, savedInstanceState);
    }

    @Override protected void initView(View view) {
        gasListView = (ListView) view.findViewById(R.id.gasStation_ListView);
        initLoactionClicent();
        mlocationClient.startLocation();
    }

    @Override protected void initData() {
        mGasStationPresenter = new GasStationPresenterImpl(this);
                double lat = MapChangeUtils.Convert_GCJ02_To_BD09_Lat(Constant.MyLatitude,Constant.MyLongitude);
                double lng = MapChangeUtils.Convert_GCJ02_To_BD09_Lng(Constant.MyLatitude,Constant.MyLongitude);
        mGasStationPresenter.loadServiceData(lat, lng, 10000, Constant.GET_GASSTATION_KEY ,1, 1 );
    }

    @Override protected void addViewsListener() {
        gasListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.d("点击了那个" + position);
                FragmentManager fm = getActivity().getSupportFragmentManager();

                SumBitIndentFragment gasFragment = new SumBitIndentFragment();
                GasStationBean gasStationBean = gasStationBeens.get(position);

                Bundle bundle = new Bundle();
                LogUtils.d("加油站的信息" + GsonUtils.Instance().toJson(gasStationBean));
                bundle.putString("gasStation", GsonUtils.Instance().toJson(gasStationBean));
                gasFragment.setArguments(bundle);

                fm.beginTransaction()
                        .replace(R.id.container_frame, gasFragment, SumBitIndentFragment.TAG)
                        .commit();
            }
        });
    }

    @Override public void showResultSuccess(List<GasStationBean> resultBeans) {
        this.gasStationBeens = resultBeans;
        gasListView.setAdapter(new CommonAdapter<GasStationBean>(getActivity(), resultBeans, R.layout.item_gasstionlist) {

            @Override public void convert(ViewHolder holder, GasStationBean gasStationBean) {
                StringBuffer oilTypeStr = new StringBuffer();
                if (gasStationBean.getGastprice() != null) {
                    for (String key : gasStationBean.getGastprice().keySet()) {
                        oilTypeStr.append(key + ":" + gasStationBean.getGastprice().get(key) + ",");
                    }
                }
                holder.setText(R.id.gasSattion_Item_stationName, gasStationBean.getName());
                holder.addText(R.id.gasSattion_Item_diatance, (Double.parseDouble(gasStationBean.getDistance()) / 1000) + "");
                holder.setText(R.id.gasSattion_Item_stationType, gasStationBean.getBrandname());
                if (oilTypeStr.length() > 3) {
                    holder.setText(R.id.gasSattion_Item_oilType, oilTypeStr.substring(0, oilTypeStr.length() - 1).toString());
                } else {
                    holder.setText(R.id.gasSattion_Item_oilType,"未知");
                }

                if (gasStationBean.getBrandname().equals(Constant.GASSTION_BRAND_ZHONGSHIYOU)) {
                    holder.setImage(R.id.gasSattion_Item_Icon, R.mipmap.ic_petrochina_station_icon);
                } else {
                    holder.setImage(R.id.gasSattion_Item_Icon, R.mipmap.ic_sinopec_station_icon);
                }
            }
        });

    }

    @Override public void showResultError(int errorNo, String errorMag) {
        new DialogUtils(getActivity())
                .errorMessage("错误", errorMag)
                .show();
    }

    @Override public void showResultSuccess(ResultBean resultBean) {

    }

    @Override public void showProgress() {

    }

    @Override public void hideProgress() {

    }

    /**
     * 初始化定位功能
     */
    private void initLoactionClicent() {
        if (null == mlocationClient) {
            mlocationClient = new AMapLocationClient(getActivity());
            mLocationOption = new AMapLocationClientOption();

            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置只定位一次
            mLocationOption.setOnceLocation(true);
            //设置定位间隔的时间
//            mLocationOption.setInterval(5000);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();
        }
    }

    /**
     * 定位成功后回调函数
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

            if (null != aMapLocation && 0 == aMapLocation.getErrorCode()) {
                LogUtils.w("停止定位");

                //获取开始的经纬度
                Constant.MyLongitude = aMapLocation.getLongitude();
                Constant.MyLatitude = aMapLocation.getLatitude();
                LogUtils.w("定位的经纬度:" +Constant.MyLongitude + "::::" + Constant.MyLatitude );
                LogUtils.w("定位成功：" + aMapLocation.getAddress());
                LogUtils.w("获取城市编码：" + aMapLocation.getAdCode());
            } else {
                LogUtils.w("定位失败" + aMapLocation.getErrorCode() + ":" + aMapLocation.getErrorCode());
            }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mlocationClient.onDestroy();
    }

    @Override
    public void onStart() {
        if (null != mlocationClient)
        mlocationClient.startLocation();
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != mlocationClient)
            mlocationClient.stopLocation();
    }
}
