package com.miss.imissyou.mycar.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.AMapNaviViewOptions;

import com.amap.api.navi.enums.NaviType;

import com.amap.api.navi.enums.PathPlanningStrategy;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.MainActivity;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.FindViewById;
import com.miss.imissyou.mycar.util.TTSController;
import com.miss.imissyou.mycar.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 高德地图的导航页面
 * Created by Imissyou on 2016/5/14.
 */
public class NaviViewActivity extends BaseActivity implements AMapNaviViewListener, AMapNaviListener {

    @FindViewById(id = R.id.navi_map_View_activity)
    AMapNaviView mapNaviView;

    AMapNavi mAMapNavi;
    TTSController mTtsManager;
    //NaviLatLng mEndLatlng;
    // NaviLatLng mStartLatlng;
    NaviLatLng mEndLatlng = new NaviLatLng(21.150477, 110.307201);
    NaviLatLng mStartLatlng = new NaviLatLng(21.150468, 110.306202);
    List<NaviLatLng> mStartList = new ArrayList<NaviLatLng>();      //起点位置坐标
    List<NaviLatLng> mEndList = new ArrayList<NaviLatLng>();       //目标地坐标
    List<NaviLatLng> mWayPointList;             //途径路径点

    private double startLon;
    private double startLat;
    private double endLon;
    private double endLat;
    private Boolean NaviTag = false;        //导航模式

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_navi_map_view);
        mapNaviView.onCreate(savedInstanceState);
        mapNaviView.setAMapNaviViewListener(this);

        mTtsManager = TTSController.getInstance(getApplicationContext());
        mTtsManager.init();
        mTtsManager.startSpeaking();

        // 开启模拟导航
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());

        mAMapNavi.addAMapNaviListener(this);
        mAMapNavi.addAMapNaviListener(mTtsManager);
        mAMapNavi.setEmulatorNaviSpeed(150);


    }

    @Override
    protected void initData() {

        // 设置模拟速度
        AMapNavi.getInstance(this).setEmulatorNaviSpeed(100);
        // 开启模拟导航
        AMapNavi.getInstance(this).startNavi(AMapNavi.EmulatorNaviMode);
        setNaviState();             //获取导航点并设置导航方式
        //getLocationLatLng();
        if (mEndLatlng != null) {
            LogUtils.w("可以导航了");
        } else {
            LogUtils.d("传进的经纬度有问题都相同，或者为空");
        }
    }

    /**
     * 获取本地导航
     */
    private void getLocationLatLng() {
        /**构造导航的起点和终点*/
        startLon = getIntent().getDoubleExtra(Constant.startLongitude, 0);
        startLat = getIntent().getDoubleExtra(Constant.startLatitude, 0);
        endLon = getIntent().getDoubleExtra(Constant.endLongitude, 0);
        endLat = getIntent().getDoubleExtra(Constant.endLatitude, 0);
        NaviTag = getIntent().getBooleanExtra(Constant.NO_START_NAVI, true);
        if (!NaviTag) {
            LogUtils.d("有起点导航");
            mStartLatlng = new NaviLatLng(startLat, startLon);
            mStartList.add(mStartLatlng);
        } else {
            LogUtils.d("无起点导航");
            AMapNavi.getInstance(this).startGPS();
        }
        LogUtils.w("开始经度：" + startLat + "开始纬度:" + startLon);
        LogUtils.w("结束经度：" + endLat + "结束纬度:" + endLon);
        mEndLatlng = new NaviLatLng(endLat, endLon);
        mEndList.add(mEndLatlng);
        LogUtils.w("测试导航功能:");
        Log.w("miss", "getLocationLatLng:测试导航功能: ");
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void onNaviSetting() {

    }

    /**
     * 导航界面左下角返回按钮回调
     */
    @Override
    public void onNaviCancel() {
        Intent intent = new Intent(NaviViewActivity.this,
                MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }

    @Override
    public void onNaviMapMode(int i) {

    }

    @Override
    public void onNaviTurnClick() {

    }

    @Override
    public void onNextRoadClick() {

    }

    @Override
    public void onScanViewButtonClick() {

    }

    @Override
    public void onLockMap(boolean b) {

    }

    @Override
    public void onNaviViewLoaded() {
        LogUtils.d("wlx" + "导航页面加载成功");
        LogUtils.d("wlx" + "请不要使用AMapNaviView.getMap().setOnMapLoadedListener();会overwrite导航SDK内部画线逻辑");
    }

    /**
     * 返回键盘监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(NaviViewActivity.this,
                    MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapNaviView.onResume();
        mStartList.add(mStartLatlng);
        mEndList.add(mEndLatlng);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapNaviView.onPause();
        mTtsManager.stopSpeaking();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapNaviView.onDestroy();
        //不再在naviview destroy的时候自动执行AMapNavi.stopNavi();
        //请自行执行
        mAMapNavi.stopNavi();
        mAMapNavi.destroy();
        mTtsManager.destroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapNaviView.onSaveInstanceState(outState);
    }


    @Override
    public void onInitNaviFailure() {
        LogUtils.d("启动导航失败");
        ToastUtil.asLong("init navi Failed");
    }

    @Override
    public void onInitNaviSuccess() {
        /**
         * 启动导航
         * 模式设置为默认驾车
         */
        if (null == mapNaviView) {
            return;
        }
        AMapNaviViewOptions viewOptions = new AMapNaviViewOptions();
        viewOptions.setReCalculateRouteForYaw(true);//设置偏航时是否重新计算路径
        viewOptions.setReCalculateRouteForTrafficJam(true);//前方拥堵时是否重新计算路径

        LogUtils.d("设置导航状态：：：：：");
        viewOptions.setAutoChangeZoom(true);
        viewOptions.setAutoDrawRoute(true);
        viewOptions.setTrafficInfoUpdateEnabled(true);//设置交通播报是否打开
        viewOptions.setCameraInfoUpdateEnabled(true);//设置摄像头播报是否打开
        viewOptions.setScreenAlwaysBright(true);//设置导航状态下屏幕是否一直开启。
        mapNaviView.setViewOptions(viewOptions);

        //种驾车算路calculateDriveRoute的重载函数，算路方法中，起点坐标和终点坐标可以以列表形式存放，
        // 按车行方向排列，带有方向信息，可有效避免算路到马路的另一侧，也可以只传入一个坐标。
        /**选择省钱的算路方法*/


        LogUtils.d("开启导航计算路径模式");
        boolean resultState = mAMapNavi.calculateDriveRoute(mStartList, mEndList, mWayPointList, PathPlanningStrategy.DRIVING_SAVE_MONEY);

        if (resultState) {
            Toast.makeText(this, "设置计算路线成功", Toast.LENGTH_LONG).show();
            LogUtils.d("设置计算路线成功");
        } else {
            LogUtils.d("设置计算路线失败");
        }
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
        LogUtils.d("计算路径成功");
        //naviFlag为AMapNavi.GPSNaviMode表示真实导航，naviFlag为AMapNavi.EmulatorNaviMode表示模拟导航

        mAMapNavi.startNavi(NaviType.GPS);
    }

    @Override
    public void onCalculateRouteFailure(int i) {
        LogUtils.d("计算路径失败" + i);
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
    public void onCalculateMultipleRoutesSuccess(int[] ints) {

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

    /**
     * 设置导航方式 并获取计算导航路线
     */
    private void setNaviState() {
        boolean naviType = getIntent().getBooleanExtra(Constant.NO_START_NAVI, false);
        if (naviType) {
            AMapNavi.getInstance(this).startGPS();
        } else {
            startLat = getIntent().getDoubleExtra(Constant.startLatitude, 21.150468);
            startLon = getIntent().getDoubleExtra(Constant.startLongitude, 110.307201);
            mStartLatlng = new NaviLatLng(startLat, startLon);
        }
        endLat = getIntent().getDoubleExtra(Constant.endLatitude, 21.150668);
        endLon = getIntent().getDoubleExtra(Constant.endLongitude, 110.305201);
        mEndLatlng = new NaviLatLng(endLat,endLon);
        LogUtils.w("开始经度：" + startLat + "开始纬度:" + startLon);
        LogUtils.w("结束经度：" + endLat + "结束纬度:" + endLon);
    }

}
