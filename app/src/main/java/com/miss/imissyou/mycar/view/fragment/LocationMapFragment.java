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
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;


/**
 * 定位信息
 * Created by Imissyou on 2016/4/27.
 */
public class LocationMapFragment extends BaseFragment implements AMapLocationListener, LocationSource ,RadioGroup.OnCheckedChangeListener {

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private MapView mMapView;
    private AMap mMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  super.onCreateView(R.layout.fragment_location_map, inflater, container, savedInstanceState);
        //在activity执行onCreate时执行mMapView.onC
        mMapView.onCreate(savedInstanceState);
        return view;
    }

    @Override
    protected void initView(View view) {
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

    @Override
    protected void initData() {



        locationClient.setLocationOption(locationOption);
        locationClient.startLocation();
    }

    @Override
    protected void addViewsListener() {
        mMap = mMapView.getMap();
        mMap.setLocationSource(this);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        mMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式，参见类AMap。
        mMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

        // 设置定位监听
        locationClient.setLocationListener(this);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (null != aMapLocation) {
            StringBuffer result = new StringBuffer();
            result.append(aMapLocation.getAddress());
            result.append("::::").append(aMapLocation.getLatitude() + ":::" + aMapLocation.getLatitude());
            LogUtils.d("点位的地址为:" + result.toString() );
        }

        LogUtils.d("错误码:" + aMapLocation.getErrorCode());
        LogUtils.d("错误信息:" + aMapLocation.getErrorInfo());

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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }
}
