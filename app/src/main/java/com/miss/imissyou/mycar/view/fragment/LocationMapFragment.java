package com.miss.imissyou.mycar.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.OilBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.bean.ServiceStation;
import com.miss.imissyou.mycar.bean.Station;
import com.miss.imissyou.mycar.bean.StopStation;
import com.miss.imissyou.mycar.presenter.LoactionMapPresenter;
import com.miss.imissyou.mycar.presenter.impl.LocationMapPresenterImpl;
import com.miss.imissyou.mycar.util.ToastUtil;
import com.miss.imissyou.mycar.view.LocationView;

import org.slf4j.MarkerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * 定位信息
 * Created by Imissyou on 2016/4/27.
 */
public class LocationMapFragment extends BaseFragment implements AMapLocationListener,
        LocationSource ,RadioGroup.OnCheckedChangeListener, LocationView {

    private OnLocationChangedListener mListener;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private MapView mMapView;
    private AMap mMap;

    private LoactionMapPresenter loactionMapPresenter;
    private List<MarkerOptions> markers = new ArrayList<MarkerOptions>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  super.onCreateView(R.layout.fragment_location_map, inflater,
                container, savedInstanceState);
        //在activity执行onCreate时执行mMapView.onC
        mMapView.onCreate(savedInstanceState);
        loactionMapPresenter = new LocationMapPresenterImpl(this);
        return view;
    }

    @Override protected void initView(View view) {
        //获取地图控件引用
        mMapView = (MapView) view.findViewById(R.id.location_mapView);
        
        locationClient = new AMapLocationClient(getActivity().getApplicationContext());
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        initLoactionOption();
    }

    private void initLoactionOption() {
        locationOption.setGpsFirst(true);
        locationOption.setOnceLocation(false);
        locationOption.setNeedAddress(true);
        locationOption.setInterval(2000);
    }

    @Override protected void initData() {
        locationClient.setLocationOption(locationOption);
        locationClient.startLocation();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(21.274898,110.364977)));
    }

    @Override
    protected void addViewsListener() {
        mMap = mMapView.getMap();
        mMap.setLocationSource(this);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        mMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式，参见类AMap。
        /**
         * 设置为跟随模式
         */
        mMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);

        // 设置定位监听
        locationClient.setLocationListener(this);
    }

    @Override public void onLocationChanged(AMapLocation aMapLocation) {

        if (null != aMapLocation) {
            StringBuffer result = new StringBuffer();
            result.append(aMapLocation.getAddress());
            result.append("::::").append(aMapLocation.getLatitude() + ":::" + aMapLocation.getLatitude());
            LogUtils.d("点位的地址为:" + result.toString() );
            ToastUtil.asLong("点位的地址为:" + result.toString());
        }

        LogUtils.d("错误码:" + aMapLocation.getErrorCode());
        LogUtils.d("错误信息:" + aMapLocation.getErrorInfo());
        ToastUtil.asLong("错误信息:" + aMapLocation.getErrorInfo());
    }

    @Override public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        if (null != locationClient) {
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    @Override public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override public void deactivate() {

    }

    @Override public void onCheckedChanged(RadioGroup group, int checkedId) {

    }


    /**
     * 指定的获取经纬度
     */
    @Override public void showOilStationSuccess(List<OilBean> oilBeanList) {

    }

    @Override public void showServiceStationSuccess(List<ServiceStation> ServiceList) {

    }

    @Override public void showStopStationSuccess(List<StopStation> stopStations) {

    }

    @Override public void showResultError(int errorNo, String errorMag) {

    }

    @Override public void showResultSuccess(ResultBean resultBean) {

    }

    @Override public void showProgress() {

    }

    @Override public void hideProgress() {

    }

    /**
     * 标上获取到的站点
     * @param myStations
     * @param tag
     */
    private void setMarkerList(List<Station> myStations, String tag) {
        if (null != markers && markers.size() > 0) {
            markers.clear();
            mMap.clear();
        }

        for ( Station tempStation : myStations) {
           MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title(tempStation.getStationTitle())
                    .position(tempStation.getLatLng()).snippet(tempStation.getStationMessage());
            markers.add(markerOptions);
        }

        mMap.addMarkers((ArrayList<MarkerOptions>) markers, true);
    }
}
