package com.miss.imissyou.mycar.view.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.GasStationBean;
import com.miss.imissyou.mycar.bean.GasStationResultBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.bean.StopStation;
import com.miss.imissyou.mycar.presenter.NaviViewPresenter;
import com.miss.imissyou.mycar.presenter.impl.NaviViewPresenterImpl;
import com.miss.imissyou.mycar.ui.MissDialog;
import com.miss.imissyou.mycar.ui.RoundImageView;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.JZLocationConverter;
import com.miss.imissyou.mycar.util.MapChangeUtils;
import com.miss.imissyou.mycar.util.ToastUtil;
import com.miss.imissyou.mycar.view.NaviVieFragmentView;
import com.miss.imissyou.mycar.view.activity.NaviViewActivity;

import java.util.List;

/**
 * 导航
 * Created by Imissyou on 2016/5/2.
 */
public class StationMapViewFragment extends BaseFragment implements View.OnClickListener,
        LocationSource, AMapLocationListener, AMap.OnMarkerClickListener,
        AMap.InfoWindowAdapter, NaviVieFragmentView {


    // 起点终点的经纬度
    private double mStartLat;
    private double mStartLon;
    private double mEndLat;
    private double mEndLon;

    // 地图和定位资源
    private MapView mMapView;
    private AMap mAMap;
    private OnLocationChangedListener mLocation;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    private String cityName;                            //城市名称
    private String cityCode;                            //城市编码

    private NaviViewPresenter mNaviViewPresenter;       //导航控制类

    private List<GasStationBean> gasStationBeens;       //加油站的列表

    private String type;                                // 标志
    private LatLng latlng;                              //经纬度
    private Button showList;                            //展现列表
    private List<StopStation> stations;                 //场地
    private JZLocationConverter.LatLng  baiduLatlng;    //百度经纬度

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(R.layout.fragment_station_map_navi,
                inflater, container, savedInstanceState);
        mMapView.onCreate(savedInstanceState);

        return view;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    protected void initView(View view) {
        mMapView = (MapView) view.findViewById(R.id.station_navi_mapView);
        showList = (Button) view.findViewById(R.id.station_show_list);
        showList.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        mNaviViewPresenter = new NaviViewPresenterImpl(this);

        if (Constant.MAP_GASSTATION.equals(type)) {
            showList.setVisibility(View.VISIBLE);
        } else {
            showList.setVisibility(View.GONE);
        }
        LogUtils.w("获取到的类型:" + type);

    }

    @Override
    protected void addViewsListener() {
        mAMap = mMapView.getMap();
        mAMap.setLocationSource(this);
        mAMap.getUiSettings().setZoomPosition(1);
        // 设置默认定位按钮是否显示
        mAMap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        mAMap.setMyLocationEnabled(true);
        // 设置定位的类型为定位模式，参见类AMap。
        //跟随模式
        mAMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
        mAMap.setOnMarkerClickListener(this);
        showList.setOnClickListener(this);

        mAMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.station_show_list:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame,
                                new GasStationFragment(), Constant.GasStationFragmetn)
                        .commit();
                break;
            default:
                break;
        }
    }

    /**
     * 转到导航页面
     *
     * @param mStartLat
     * @param mStartLon
     * @param mEndLat
     * @param mEndLon
     */
    private void toNaviMap(double mStartLat, double mStartLon, double mEndLat, double mEndLon) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NaviViewActivity.class);

        intent.putExtra(Constant.startLongitude, mStartLon);

        intent.putExtra(Constant.startLatitude, mStartLat);
        intent.putExtra(Constant.endLongitude, mEndLon);
        intent.putExtra(Constant.endLatitude, mEndLat);

        getActivity().startActivity(intent);
    }


    /**
     * 定位成功后回调函数
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (null != aMapLocation && null != mLocation) {

            mlocationClient.stopLocation(); //停止定位
            mAMap.clear();              //清除地图上标的点

            if (null != aMapLocation && 0 == aMapLocation.getErrorCode()) {
                //获取开始的经纬度
                mStartLon = aMapLocation.getLongitude();
                mStartLat = aMapLocation.getLatitude();
                LogUtils.w("定位成功：" + aMapLocation.getAddress());
                LogUtils.w("获取城市编码：" + aMapLocation.getAdCode());
                cityName = aMapLocation.getCity();
                LogUtils.w("城市名称" + cityName);
                cityCode = aMapLocation.getCityCode();
                LogUtils.w("城市名称" + cityCode);
                mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 11));
                mLocation.onLocationChanged(aMapLocation);      //显示系统小蓝点
                JZLocationConverter.LatLng location = new JZLocationConverter
                        .LatLng(mStartLat,mStartLon);
                //转百度地图经纬度
                baiduLatlng = JZLocationConverter.gcj02ToBd09(location);

                latlng = new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude());


                LogUtils.w("返回类型" + type);
                loadMapData();
            } else {
                LogUtils.w("定位失败" + aMapLocation.getErrorCode() +
                        ":" + aMapLocation.getErrorCode());
            }
        }
    }

    /**
     * 激活定位
     *
     * @param onLocationChangedListener
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mLocation = onLocationChangedListener;
        if (null == mlocationClient) {
            mlocationClient = new AMapLocationClient(getActivity());
            mLocationOption = new AMapLocationClientOption();

            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(
                    AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setInterval(10000);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mLocation = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    /**
     * 弹出一个页面Marker点击后的
     *
     * @param marker
     * @return
     */
    @Override
    public View getInfoWindow(final Marker marker) {
        View view = getActivity()
                .getLayoutInflater()
                .inflate(R.layout.poikeywordsearch_uri, null);

        TextView title = (TextView) view.findViewById(R.id.navi_mapView_marker_title);
        TextView snippet = (TextView) view.findViewById(R.id.navi_mapView_marker_snippet);
        RoundImageView Image = (RoundImageView) view.findViewById(R.id.navi_mapView_marker_image);
        Button goHere = (Button) view.findViewById(R.id.navi_mapView_marker_goBtn);
        //设置点击弹出的信息
        LogUtils.w("Marker 标题: " + marker.getTitle());
        title.setText(marker.getTitle());

        snippet.setText(marker.getSnippet());
        LogUtils.w("Marker 标题: " + marker.getSnippet());
        Image.setImageBitmap(marker.getIcons().get(0).getBitmap());
        //到导航的页面
        goHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEndLat = marker.getPosition().latitude;
                mEndLon = marker.getPosition().longitude;
                toNaviMap(mStartLat, mStartLon, mEndLat, mEndLon);
            }
        });

        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        LogUtils.d("进入这里了");
        return null;
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        marker.showInfoWindow();
        mEndLon = marker.getPosition().longitude;
        mEndLat = marker.getPosition().latitude;

        LogUtils.w("传过去的经度:" + mEndLat + "传过去的纬度：" + mEndLon);
        LogUtils.w("你点击的是哪个:" + marker.getTitle());
        if (type == Constant.MAP_GASSTATION) {
            new MissDialog.Builder(getActivity())
                    .setTitle(marker.getTitle())
                    .setMessage(marker.getSnippet())
                    .setPositiveButton("到这里去", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            toNaviMap(mStartLat, mStartLon, mEndLat, mEndLon);
                        }
                    }).setNegativeButton("在线预约", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (type != Constant.MAP_GASSTATION) {
                        toNaviMap(mStartLat, mStartLon, mEndLat, mEndLon);
                    } else {
                        toGasStation(marker.getPeriod());
                    }
                }
            }).create().show();
        } else {
            new MissDialog.Builder(getActivity())
                    .setTitle(marker.getTitle())
                    .setMessage(marker.getSnippet())
                    .setPositiveButton("到这里去", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            toNaviMap(mStartLat, mStartLon, mEndLat, mEndLon);

                        }
                    }).setNegativeButton("电话", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (null != stations.get(marker.getPeriod()).getPhoneNumber()) {
                        Intent intent = new Intent(Intent.ACTION_CALL,
                                Uri.parse("tel:" +
                                        stations.get(marker.getPeriod()).getPhoneNumber() + ""));
                        getActivity().startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(),
                                type.equals(Constant.MAP_PARK) ?
                                        "没有该停车场的电话" :
                                        "没有该维修店的电话",
                                Toast.LENGTH_SHORT).show();
                    }

                }
            }).create().show();
        }
        return false;
    }

    @Override
    public void onDestroy() {
        if (null != this) {
            super.onDestroy();
            mMapView.onDestroy();
        }
        deactivate();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }


    @Override
    public void loadFail(int errorNumber, String errMsg) {
        LogUtils.d("错误信息:" + errMsg + "===>" + errorNumber);
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadSucccessPark(ResultBean resultBean) {
        stations = GsonUtils.getParams(resultBean, "park", StopStation.class);
        showInPage(stations);
    }

    @Override
    public void loadSuccessRepairSHop(ResultBean resultBean) {
       stations = GsonUtils.getParams(resultBean, "repairshop", StopStation.class);
        showInPage(stations);
    }

    @Override
    public void loadSuccessGasStation(GasStationResultBean resultBean) {
        if (resultBean.getResultcode() == Constant.HTTP_OK) {
            showList.setVisibility(View.VISIBLE);
            gasStationBeens = resultBean.getResult().getData();
            LogUtils.d("加油站的数量" + gasStationBeens.size());
            showGasStation(gasStationBeens);            //展示地图上的加油站
        }
    }

    /**
     * 展现加油站Maker的列表
     * @param gasStationBeens
     */
    private void showGasStation(List<GasStationBean> gasStationBeens) {
        LogUtils.d("车辆信息:" + gasStationBeens.size());
        int i = 0;
        showList.setVisibility(View.VISIBLE);
        for (GasStationBean station : gasStationBeens) {

            LatLng latLng = setLatlng(station);
            if(getActivity() == null) {
                return;
            }
            View view = View.inflate(getActivity(), R.layout.marker_oil_icon, null);
            mAMap.addMarker(new MarkerOptions()
                    .anchor(0.5f, 1)
                    .position(latLng)
                    .title(station.getName())
                    .snippet(station.getAddress())
                    .icon(BitmapDescriptorFactory.fromView(view)))
                    .setPeriod(i);
            LogUtils.d("设置第几个" + i + "加油站信息:>>>" + station.getName());
            i ++;
        }
    }

    /**
     * 展现Marker列表
     *
     * @param stations 场地列表
     */
    private void showInPage(List<StopStation> stations) {

        showList.setVisibility(View.GONE);
        if (null != stations) {
            if (type == Constant.MAP_PARK) {
                Toast.makeText(getActivity(), "附近停车场数量:" +
                        stations.size(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "附近维修站数量:" +
                        stations.size(), Toast.LENGTH_SHORT).show();
            }
            LogUtils.e("获取数据为空");
        } else {
            Toast.makeText(getActivity(),"没有数据",Toast.LENGTH_LONG).show();
            return;
        }
        LogUtils.d("订单列表:" + GsonUtils.Instance().toJson(stations));
        int i = 0;

        for (StopStation station : stations) {
            View view = null;
            if (null != station.getLat() &&
                    null != station.getLon() &&
                    null != station.getName()) {
                LatLng latLng = new LatLng(station.getLat(), station.getLon());
                if (type == Constant.MAP_PARK) {
                    view = View.inflate(getActivity(), R.layout.marker_icon, null);
                } else {
                    view = View.inflate(getActivity(), R.layout.marker_fix_icon, null);
                }
                LogUtils.d("请求图片地址:" + Constant.SERVER_URL + station.getImg());
                mAMap.addMarker(new MarkerOptions()
                        .anchor(0.5f, 1)
                        .position(latLng)
                        .title(station.getName())
                        .snippet(station.getIntroduce())
                        .icon(BitmapDescriptorFactory.fromView(view))).setPeriod(i);

                LogUtils.d("设置第几个" + i + "场地信息:>>>" + station.getName());
                i++;
            } else {
                LogUtils.d("错误信息经纬度为空");
            }
        }
    }

    /**
     * 到加油站去
     * @param id
     */
    private void toGasStation(int id) {
        SumBitIndentFragment sumbitOrder = new SumBitIndentFragment();
        LogUtils.d("获取到的ID 号是多少" + id);
        String jsonStr = GsonUtils.Instance().toJson(gasStationBeens.get(id));
        Bundle bundle = new Bundle();
        bundle.putString("gasStation",jsonStr);
        bundle.putString("goback","map");       //设置识别标让填写订单哪里能返回到这里

        sumbitOrder.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame,sumbitOrder).commit();

    }

    /**
     * 加载地图页面
     */
    private void loadMapData() {
        if (null != type ) {
            switch (type) {
                case Constant.MAP_GASSTATION:
                    //TODO 显示可点击的列表
                    mNaviViewPresenter.loadGasStation(baiduLatlng.getLongitude(),
                            baiduLatlng.getLatitude());
                    break;
                case Constant.MAP_PARK:
                    LogUtils.d("加载停车场");
                    mNaviViewPresenter.loadPack(latlng);
                    break;
                case Constant.MAP_MAINTAIN:
                    LogUtils.d("加载维修站信息");
                    mNaviViewPresenter.loadRepairShop(latlng);
                    break;
            }
        }
    }

    /**
    * 装载经纬度
    *
    * @param gasStation 加油站
    */
    private LatLng setLatlng(GasStationBean gasStation) {
        LatLng latLng = MapChangeUtils
                .Convert_BD0911_TO_GCJ02(gasStation.getLat(),
                        gasStation.getLon(), getActivity());
        return latLng;
    }

    /**
     * 类型传入
     * @param type  类型
     */
    public void setType(String type) {
        this.type = type;

        // 定位后自动 刷新页面
       if (null != mlocationClient){
            mlocationClient.startLocation();
           LogUtils.d("重新进入地图页面");
           showList.setVisibility(View.GONE);
        }
    }
}


