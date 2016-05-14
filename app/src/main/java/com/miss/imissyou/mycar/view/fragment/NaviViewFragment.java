package com.miss.imissyou.mycar.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.util.TTSController;

import java.util.ArrayList;

/**
 * 导航
 * Created by Imissyou on 2016/5/2.
 */
public class NaviViewFragment extends BaseFragment implements AMapNaviListener {


    // 起点终点坐标
    private NaviLatLng mNaviStart = new NaviLatLng(39.989614, 116.481763);
    private NaviLatLng mNaviEnd = new NaviLatLng(39.983456, 116.3154950);
    // 起点终点列表
    private ArrayList<NaviLatLng> mStartPoints = new ArrayList<NaviLatLng>();
    private ArrayList<NaviLatLng> mEndPoints = new ArrayList<NaviLatLng>();

    // 规划线路
    private RouteOverLay mRouteOverLay;
    private TTSController ttsManager;
    private AMapNavi aMapNavi;

    // 地图和导航资源
    private MapView mMapView;
    private AMap mAMap;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        aMapNavi = AMapNavi.getInstance(getActivity());
        aMapNavi.addAMapNaviListener(this);
//        aMapNavi.addAMapNaviListener(ttsManager);
        aMapNavi.setEmulatorNaviSpeed(150);
        View  view = super.onCreateView(R.layout.fragment_navi, inflater, container,
               savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        return view;
    }

    @Override protected void initView(View view) {
        mMapView = (MapView) view.findViewById(R.id.navi_mapView);
    }

    @Override protected void initData() {

    }

    @Override protected void addViewsListener() {
        mAMap = mMapView.getMap();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        aMapNavi.destroy();
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


    @Override public void onInitNaviFailure() {

    }

    @Override public void onInitNaviSuccess() {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onGetNavigationText(int i, String s) {

    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onArriveDestination() {

    }

    @Override
    public void onCalculateRouteSuccess() {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override public void onArrivedWayPoint(int i) {

    }

    @Override public void onGpsOpenStatus(boolean b) {

    }

    @Override public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

    }

    @Override public void onNaviInfoUpdate(NaviInfo naviInfo) {

    }

    @Override public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void hideLaneInfo() {

    }

    @Override public void onCalculateMultipleRoutesSuccess(int[] ints) {

    }

    @Override public void notifyParallelRoad(int i) {

    }

    @Override public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }
}


