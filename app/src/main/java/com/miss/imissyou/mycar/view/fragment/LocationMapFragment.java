package com.miss.imissyou.mycar.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
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

import java.util.ArrayList;
import java.util.List;


/**
 * 定位信息
 * Created by Imissyou on 2016/4/27.
 */
public class LocationMapFragment extends BaseFragment implements LocationView, AMapLocationListener, LocationSource {

    private MapView mapView;
    private AMap aMap;
    //         private LocationManagerProxy mLocationManagerProxy;
    private LocationSource.OnLocationChangedListener mListener;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private LatLng locLatlng;

    @Nullable
    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(R.layout.fragment_location_map, inflater, container, savedInstanceState);
        mapView.onCreate(savedInstanceState);
        return view;
    }

    @Override protected void addViewsListener() {
        locationClient = new AMapLocationClient(getActivity().getApplicationContext());
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置定位监听
        locationClient.setLocationListener(this);
    }

    @Override protected void initView(View view) {
        mapView = (MapView) view.findViewById(R.id.location_mapView);

    }

    @Override protected void initData() {
        if (null == aMap) {
            aMap = mapView.getMap();
        }

        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式：定位（AMap.LOCATION_TYPE_LOCATE）、跟随（AMap.LOCATION_TYPE_MAP_FOLLOW）
        // 地图根据面向方向旋转（AMap.LOCATION_TYPE_MAP_ROTATE）三种模式
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
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

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        // TODO Auto-generated method stub
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
            //获取位置信息
            Double geoLat = aMapLocation.getLatitude();
            Double geoLng = aMapLocation.getLongitude();
            Log.d("Miss", "Latitude = " + geoLat.doubleValue() + ", Longitude = " + geoLng.doubleValue());
            // 通过 AMapLocation.getExtras() 方法获取位置的描述信息，包括省、市、区以及街道信息，并以空格分隔。
            String desc = "";
            Bundle locBundle = aMapLocation.getExtras();
            if (locBundle != null) {
                desc = locBundle.getString("desc");
                Log.d("Miss", "desc = " + desc);
            }
            mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点

            // 如果定位成功，记录下位置
            locLatlng = new LatLng(aMapLocation.getLatitude(),
                    aMapLocation.getLongitude());

            // 如果地图没问题，添加到地图上
            if (aMap != null) {
                addLocationMarker();
            }
        } else {
            ToastUtil.asLong(aMapLocation.getErrorInfo());
        }
    }

    /**
     * 添加定位小图标到地图上
     */
    private void addLocationMarker() {
        
        if (locLatlng != null) {
            aMap.clear();
        } else {
            locLatlng = new LatLng(110.364977,21.274898);
        }

            aMap.addMarker(new MarkerOptions().position(locLatlng).icon(
                    BitmapDescriptorFactory
                            .fromResource(R.mipmap.user_icon)));
            aMap.moveCamera(CameraUpdateFactory.newLatLng(locLatlng));
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }

//    /**
//     * 标上获取到的站点
//     * @param myStations
//     * @param tag
//     */
//    private void setMarkerList(List<Station> myStations, String tag) {
//        if (null != markers && markers.size() > 0) {
//            markers.clear();
//            mMap.clear();
//        }
//
//        for ( Station tempStation : myStations) {
//           MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.title(tempStation.getStationTitle())
//                    .position(tempStation.getLatLng()).snippet(tempStation.getStationMessage());
//            markers.add(markerOptions);
//        }
//
//        mMap.addMarkers((ArrayList<MarkerOptions>) markers, true);
//    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
