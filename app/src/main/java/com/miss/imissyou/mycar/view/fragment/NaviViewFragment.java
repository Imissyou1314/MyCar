package com.miss.imissyou.mycar.view.fragment;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.test.mock.MockCursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;

import com.amap.api.maps.overlay.PoiOverlay;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.ui.RoundImageView;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.ToastUtil;
import com.miss.imissyou.mycar.view.activity.NaviViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 导航
 * Created by Imissyou on 2016/5/2.
 */
public class NaviViewFragment extends BaseFragment implements View.OnClickListener,
        LocationSource, AMapLocationListener, PoiSearch.OnPoiSearchListener, TextWatcher, AMap.OnMarkerClickListener, AMap.InfoWindowAdapter {


    private TextView searchBtn;       //搜索按钮
    private AutoCompleteTextView searchInput;// 输入搜索关键字
    //    private EditText searchCityInput;  //搜索输入
    private Button goHere;        //到这里去
    private Button formHere;       //从这里来

    private String keyWord = "";

    private String cityWord = "";

    // 起点终点的经纬度
    private double mStartLat;
    private double mStartLon;
    private double mEndLat;
    private double mEndLon;

    //搜索模块
    private PoiResult poiResult; // poi返回的结果

    private int currentPage = 1;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索

    // 地图和定位资源
    private MapView mMapView;
    private AMap mAMap;
    private LatLng locLatlng;
    private OnLocationChangedListener mLocation;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    private String addressCode;       //

    private final String searchService = "交通设施服务|汽车维修|道路附属设施|地名地址信息|公共设施|汽车服务|汽车销售|生活服务";
    private String cityName;            //城市名称
    private String cityCode;            //城市编码

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(R.layout.fragment_navi, inflater, container, savedInstanceState);

        mMapView.onCreate(savedInstanceState);
        return view;
    }

    @Override
    protected void initView(View view) {
        mMapView = (MapView) view.findViewById(R.id.navi_mapView);

        searchInput = (AutoCompleteTextView) view.findViewById(R.id.navi_mapView_keyWord);
//        searchCityInput = (EditText) view.findViewById(R.id.navi_mapView_search_edit);

        searchBtn = (TextView) view.findViewById(R.id.navi_mapView_search_btn);
        goHere = (Button) view.findViewById(R.id.navi_View_goButton);
        formHere = (Button) view.findViewById(R.id.navi_View_BackButton);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void addViewsListener() {
        mAMap = mMapView.getMap();
        mAMap.setLocationSource(this);
        mAMap.getUiSettings().setZoomPosition(1);
        mAMap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER );// 设置默认定位按钮是否显示
        mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式，参见类AMap。
        //跟随模式
        mAMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
        mAMap.setOnMarkerClickListener(this);

        searchInput.addTextChangedListener(this);
        searchBtn.setOnClickListener(this);
        goHere.setOnClickListener(this);
        formHere.setOnClickListener(this);

        mAMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.navi_mapView_search_btn:
                searchMap();
                break;
            case R.id.navi_View_goButton:
                toNaviMap(mStartLat, mStartLon, mEndLat, mEndLon);
                break;
            case R.id.navi_View_BackButton:
                toNaviMap(mEndLat, mEndLon, mStartLat, mStartLon);
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
     * 进行搜索
     */
    private void searchMap() {
        //TODO
        keyWord = searchInput.getText().toString();
        //隐藏键盘
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        if ("".equals(keyWord)) {
            ToastUtil.asShort("请输入搜索关键字");
            return;
        } else {
            currentPage = 0;
            // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
//            query = new PoiSearch.Query(keyWord, "", searchCityInput.getText().toString());
//                               搜索
            LogUtils.w("搜索关键词:" + keyWord);
            query = new PoiSearch.Query(keyWord, searchService, cityCode);

            query.setPageSize(12);
            query.setPageNum(currentPage);

            query.setCityLimit(true);
            LogUtils.w("搜索的城市名:" + query.getCity());

            poiSearch = new PoiSearch(getActivity().getApplication(), query);
            poiSearch.setOnPoiSearchListener(this);
            LogUtils.w("经度" + mStartLat + "纬度:" + mStartLon);
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(mStartLat, mStartLon), Constant.SEARCH_RADIO, true));//设置周边搜索的中心点以及区域
            poiSearch.searchPOIAsyn();
        }
    }

    /**
     * 定位成功后回调函数
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (null != aMapLocation && null != mLocation) {
            if (null != aMapLocation && 0 == aMapLocation.getErrorCode()) {
                //获取开始的经纬度
                mStartLon = aMapLocation.getLongitude();
                mStartLat = aMapLocation.getLatitude();
                LogUtils.w("定位成功：" + aMapLocation.getAddress());
                LogUtils.w("获取城市编码：" + aMapLocation.getAdCode());
                addressCode = aMapLocation.getAdCode();
                cityName = aMapLocation.getCity();
                LogUtils.w("城市名称" + cityName);
                cityCode = aMapLocation.getCityCode();
                LogUtils.w("城市名称" + cityCode);
                mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude()), 17));
                mLocation.onLocationChanged(aMapLocation);      //显示系统小蓝点
            } else {
                LogUtils.w("定位失败" + aMapLocation.getErrorCode() + ":" + aMapLocation.getErrorCode());
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
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
           mLocationOption.setInterval(5000);
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
     * POI 信息查询回调
     *
     * @param result
     * @param rCode
     */
    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        LogUtils.w("搜索返回信息" + result.getPageCount() + "搜索返回状态吗:" + rCode);

        if (rCode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果

                mAMap.moveCamera(CameraUpdateFactory.zoomTo(16));
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始

                    LogUtils.w("搜索的大小：" + poiItems.size());
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    if (poiItems != null && poiItems.size() > 0) {
                        mAMap.clear();// 清理之前的图标
                        PoiOverlay poiOverlay = new PoiOverlay(mAMap, poiItems);
                        poiOverlay.removeFromMap();
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();
                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        showSuggestCity(suggestionCities);
                    } else {
                        ToastUtil.asShort("没有结果");
                        LogUtils.d("没有结果");
                    }
                }
            } else {
                ToastUtil.asShort("没有结果");
                LogUtils.d("没有结果");
            }
        } else if (rCode == 27) {
            ToastUtil.asLong("网络异常");
            LogUtils.d("网络异常");
        } else if (rCode == 32) {
            ToastUtil.asShort("key 错误");
        } else {
            ToastUtil.asLong("其他错误 " + rCode);
            LogUtils.d("其他错误 " + rCode);
        }

    }

    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size(); i++) {
            infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
                    + cities.get(i).getCityCode() + "城市编码:"
                    + cities.get(i).getAdCode() + "\n";
        }
        ToastUtil.asLong(infomation);
        LogUtils.d(infomation);
    }


    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    /**
     * 采用高德官方提供的输入自动提示
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = s.toString().trim();
        LogUtils.w("更改的信息提示是:" + newText);

        Inputtips inputTips = new Inputtips(getActivity(),
                new Inputtips.InputtipsListener() {

                    @Override
                    public void onGetInputtips(List<Tip> tipList, int rCode) {

                        if (rCode == 1000) {// 正确返回
                            LogUtils.w("放回来的数据是:" + tipList + "放回的代码是:" + rCode);
                            List<String> listString = new ArrayList<String>();
                            for (int i = 0; i < tipList.size(); i++) {
                                listString.add(tipList.get(i).getName());
                            }
                            ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
                                    getActivity().getApplicationContext(),
                                    R.layout.route_inputs, listString);
                            searchInput.setAdapter(aAdapter);
                            aAdapter.notifyDataSetChanged();
                        } else {
                            LogUtils.w("返回的代码是:" + rCode);
                        }
                    }
                });

        try {
            // 第一个参数表示提示关键字，第二个参数默认代表全国，也可以为城市区号
//            inputTips.requestInputtips(newText, searchCityInput.getText().toString());
//            inputTips.setQuery(new InputtipsQuery(keyWord, cityName));
            inputTips.requestInputtips(newText, cityName);
        } catch (AMapException e) {
            LogUtils.d("错误" + e.toString());
            e.printStackTrace();
        }
    }


    @Override
    public void afterTextChanged(Editable s) {

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
        return null;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();

        LogUtils.w("你点击的是哪个:" + marker.getTitle());
        mEndLon = marker.getPosition().longitude;
        mEndLat = marker.getPosition().latitude;
        LogUtils.w("传过去的经度:" + mEndLat + "传过去的纬度：" + mEndLon);
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        deactivate();
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
}


