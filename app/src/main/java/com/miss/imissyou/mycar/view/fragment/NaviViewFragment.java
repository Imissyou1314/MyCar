package com.miss.imissyou.mycar.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.PathPlanningStrategy;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.model.RouteOverlayOptions;
import com.amap.api.navi.view.RouteOverLay;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.util.TTSController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导航
 * Created by Imissyou on 2016/5/2.
 */
public class NaviViewFragment extends BaseFragment implements AMapNaviListener, AMap.OnMapClickListener {

    /**导航View*/
    private AMapNaviView mAMapNaviView;


    private EditText secherEditText;          //搜索输入框
    private Button secherBtn;           //搜索提交按钮




    /**导航*/
    private AMapNavi mAMapNavi;
    /**起点的坐标*/
    private List<NaviLatLng> mStartList;
    /**目标地的坐标*/
    private List<NaviLatLng> mEndList;
    private List<NaviLatLng> mWayPointList;
    /**导航的路数*/
    private int[] routeIds;
    private Map< Integer, RouteOverLay> routeOverLays = new HashMap<Integer, RouteOverLay>();
    private boolean calculateSuccess = false;

    /**语音功能*/
    private TTSController mTtsManager;
    private AMap mAMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  super.onCreateView(R.layout.fragment_navi, inflater, container, savedInstanceState);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMap = mAMapNaviView.getMap();
        mAMapNavi.addAMapNaviListener(this);
        mAMapNavi.addAMapNaviListener(mTtsManager);
        mAMap.setOnMapClickListener(this);
        return view;
    }

    @Override protected void initView(View view) {
        mAMapNaviView = (AMapNaviView) view.findViewById(R.id.navi_view);
        secherEditText = (EditText) view.findViewById(R.id.navi_View_Input);
        secherBtn = (Button) view.findViewById(R.id.navi_view_seacher_btn);
        mAMapNavi = AMapNavi.getInstance(getActivity().getApplicationContext());
        if (mAMapNavi == null) {
            LogUtils.d("mAMapNavi 为空");
        }
    }

    @Override protected void initData() {

        //计算路线
        mAMapNavi.calculateDriveRoute(mStartList, mEndList, mWayPointList, PathPlanningStrategy.DRIVING_DEFAULT);
    }

    @Override protected void addViewsListener() {

        //语音
        mTtsManager = TTSController.getInstance(getActivity().getApplicationContext());
        mTtsManager.startSpeaking();
    }

    @Override public void onPause() {
        mAMapNaviView.onPause();
        mTtsManager.stopSpeaking();
        super.onPause();
    }

    @Override public void onDestroy() {
        mAMapNaviView.onDestroy();
        mAMapNavi.stopNavi();
        super.onDestroy();
    }

    @Override public void onResume() {
        mAMapNaviView.onResume();
        super.onResume();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        mAMapNaviView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onInitNaviSuccess() {

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
        mAMapNavi.startNavi(AMapNavi.EmulatorNaviMode);
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

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onGpsOpenStatus(boolean b) {

    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

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

    @Override
    public void onCalculateMultipleRoutesSuccess(int[] routeIds) {
        this.routeIds = routeIds;

        for (int i = 0; i < routeIds.length; i++) {
            //通过对应的路径ID获得一条道路路径AMapNaviPath
            AMapNaviPath path = (mAMapNavi.getNaviPaths()).get(routeIds[i]);

            //通过这个AMapNaviPath生成一个RouteOverLay用于加在地图上
            RouteOverLay routeOverLay = new RouteOverLay(mAMapNaviView.getMap(), path,getActivity());
            routeOverLay.setTrafficLine(true);
            routeOverLay.addToMap();

            routeOverLays.put(routeIds[i], routeOverLay);
        }

        routeOverLays.get(routeIds[0]).zoomToSpan();
        calculateSuccess = true;

    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        NaviLatLng endLatLng = new NaviLatLng(latLng.latitude, latLng.longitude);
        mEndList.add(0, endLatLng);
    }
}
