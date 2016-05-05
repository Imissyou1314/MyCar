package com.miss.imissyou.mycar.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定位信息
 * Created by Imissyou on 2016/4/27.
 */
public class LocationMapFragment extends BaseFragment implements LocationSource , AMapLocationListener{


    public AMapLocationClient mLocationClient;
    public AMapLocationClientOption mLocationClientOption = null;
    private MapView mapView;
    private AMap map;

    private double longitude;       //经度
    private double latitude;        //纬度


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = super.onCreateView(R.layout.fragment_location_map, inflater, container, savedInstanceState);
        mapView.onCreate(savedInstanceState);
        if (map == null ) {
            map = mapView.getMap();
            map.setLocationSource(this);
            map.setMyLocationEnabled(true);
        }
        this.addMapViewListener();
        return view;
    }

    @Override protected void initView(View view) {
        mapView = (MapView) view.findViewById(R.id.loction_map);
    }

    @Override
    protected void initData() {

    }

    private void addMapViewListener() {
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        mLocationClientOption = new AMapLocationClientOption();

        mLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);        //高精度
        mLocationClientOption.setNeedAddress(true);
        mLocationClientOption.setOnceLocation(false);       //只定位一次
        mLocationClientOption.setWifiActiveScan(true);
        mLocationClientOption.setMockEnable(true);
        mLocationClientOption.setInterval(2500);
        mLocationClient.setLocationOption(mLocationClientOption);
        mLocationClient.startLocation();

        CameraPosition cp = map.getCameraPosition();
        CameraPosition cpNew = CameraPosition.fromLatLngZoom(new LatLng(31.22, 121.48), cp.zoom);
        CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cpNew);
        map.moveCamera(cu);

        /**
         * 定位回调
         */
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() != 0) {
                        LogUtils.d("ErrorCode" + aMapLocation.getErrorCode());
                        //定位成功回调信息，设置相关的信息
                        aMapLocation.getLocationType();
                        longitude = aMapLocation.getLongitude();
                        latitude = aMapLocation.getLatitude();
                        LogUtils.d("经度:" + longitude + "纬度:" + latitude);
                        aMapLocation.getAccuracy();

                        SimpleDateFormat df = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                        Date date = new Date(aMapLocation.getTime());
                        df.format(date);        //定位时间

                        String address = aMapLocation.getAddress();

                        String  country = aMapLocation.getCountry();
                        String  city = aMapLocation.getCity();
                        String  district = aMapLocation.getDistrict();
                        String street = aMapLocation.getStreet();
                        String streetNum = aMapLocation.getStreetNum();
                        LogUtils.d("地址:" + address);
                        LogUtils.d("地址:" + country + "." +city+ "." + district + "." + street +"." + streetNum);

                        aMapLocation.getCityCode();
                        aMapLocation.getAdCode();
                        aMapLocation.getAoiName();

                        Message msg = mHandler.obtainMessage();
                        msg.obj = aMapLocation;
                        msg.what = 0;
                        mHandler.sendMessage(msg);

                    } else {
                        LogUtils.d("AMapError Location Error, " +
                                "errorCode" + aMapLocation.getErrorCode() +
                                "error info::" + aMapLocation.getErrorInfo());
                    }
                }
            }
        });
    }

    @Override
    protected void addViewsListener() {

    }

    @Override public void onStop() {
        mLocationClient.stopLocation();
        super.onStop();
    }

    @Override public void onDestroy() {

        super.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
            mapView.onDestroy();
            mLocationClient = null;
            mLocationClientOption = null;
        }

    }

    @Override public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override public void onResume() {
        mLocationClient.startLocation();
        mapView.onResume();
        super.onResume();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 活动的方法
     * @param listerner
     */
    @Override
    public void activate(OnLocationChangedListener listerner) {
        LogUtils.d("活动");

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void onLocationChanged(AMapLocation location) {
        if (location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();


        }
    }

    Handler mHandler = new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            if (msg.what == 0) {
                ToastUtil.asLong(((AMapLocation)msg.obj).getAddress());
            }
        }
    };
}
