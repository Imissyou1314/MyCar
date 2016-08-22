package com.miss.imissyou.mycar.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.Text;
import com.amap.api.maps.overlay.PoiOverlay;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.JZLocationConverter;
import com.miss.imissyou.mycar.util.ToastUtil;
import com.miss.imissyou.mycar.view.activity.NaviViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择导航路线
 * Created by ImissYou on 2016/5/30.
 */
public class RouteSelectFragment extends BaseFragment implements
        View.OnClickListener,
        PoiSearch.OnPoiSearchListener,
        AMapLocationListener,
        TextWatcher,
        GeocodeSearch.OnGeocodeSearchListener {


    private AutoCompleteTextView startRouteInput;           //输入起点路径
    private AutoCompleteTextView endRouteInput;             //输入终点路径
    private final String searchService = "交通设施服务|汽车维修|道路附属设施|地名地址信息|" +
            "公共设施|汽车服务|汽车销售|生活服务";

    private ImageButton changeBtn;              //改变起点和终点路径

    //搜索模块
    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 1;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索

    // 起点终点的经纬度
    private double mStartLat;
    private double mStartLon;
    private double mEndLat;
    private double mEndLon;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private GeocodeSearch geocoderSearch;

//    private TextView goBack;                      //返回上一页

    private final int StartTag = 1;                       //起点的标志
    private String cityName;                            //城市名

    private final int EndTag = 0;                       //终点标志

    private String startAd;
    private String endAd;

    private Integer selectDriveType = 0;
    private String startPlace;

    private String endPlace;
    //选择导航方式
    private TextView selectFirst;       //选择了
    private TextView selectTime;        //选择了时间
    private TextView selectMoney;       //选择了金钱

    private TextView selectDistance;    //选择了距离

    private TextView selectText;        //显示选择的类型
    private Button goNaviBtn;       //去导航
    private boolean setStart;       //设置导航的起点坐标

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initLoactionClicent();
        mlocationClient.startLocation();
        return super.onCreateView(R.layout.fragment_navi_route,
                inflater, container, savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        startRouteInput = (AutoCompleteTextView) view.findViewById(R.id.select_route_start_input);
        endRouteInput = (AutoCompleteTextView) view.findViewById(R.id.select_route_end_input);

        changeBtn = (ImageButton) view.findViewById(R.id.select_route_changeSeclet);
//        goBack = (TextView) view.findViewById(R.id.select_route_go_back);

        selectDistance = (TextView) view.findViewById(R.id.input_distance);
        selectFirst = (TextView) view.findViewById(R.id.input_first);
        selectMoney = (TextView) view.findViewById(R.id.input_money);
        selectTime = (TextView) view.findViewById(R.id.input_time);

        goNaviBtn = (Button) view.findViewById(R.id.navi_route_goNavi);
        selectText = (TextView) view.findViewById(R.id.navi_route_select);
    }

    @Override
    protected void initData() {
        selectDistance.setBackgroundResource(R.mipmap.selelt_distance);
        selectText.setText("距离最短路线");
        selectDriveType = 3;
    }

    @Override
    protected void addViewsListener() {
        changeBtn.setOnClickListener(this);
//        goBack.setOnClickListener(this);
        startRouteInput.setOnClickListener(this);
        endRouteInput.setOnClickListener(this);

        selectDistance.setOnClickListener(this);
        selectFirst.setOnClickListener(this);
        selectTime.setOnClickListener(this);
        selectMoney.setOnClickListener(this);

        goNaviBtn.setOnClickListener(this);

        startRouteInput.addTextChangedListener(this);
        endRouteInput.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_route_changeSeclet:
                changeInput();
                break;
            case R.id.select_route_end_input:
                toInputSelect(EndTag);
                break;
            case R.id.select_route_start_input:
                toInputSelect(StartTag);
                break;
//            case R.id.select_route_go_back:
//                backToFragment();
//                break;
            case R.id.input_first:
                resetSelect();
                selectFirst.setBackgroundResource(R.mipmap.selelt_first);
                selectDriveType = 0;
                selectText.setText("速度优先路线");
                break;
            case R.id.input_time:
                resetSelect();
                selectTime.setBackgroundResource(R.mipmap.select_time);
                selectDriveType = 1;
                selectText.setText("时间最短路线");
                break;
            case R.id.input_money:
                resetSelect();
                selectMoney.setBackgroundResource(R.mipmap.select_money);
                selectDriveType = 2;
                selectText.setText("不走收费道路路线");
                break;
            case R.id.input_distance:
                resetSelect();
                selectDistance.setBackgroundResource(R.mipmap.selelt_distance);
                selectDriveType = 3;
                selectText.setText("距离最短路线");
                break;
            case R.id.navi_route_goNavi:
                //TODO 起点和终点的经纬度
                setStart = true;
                getLocation(startRouteInput.getText().toString() ,startAd);
                setStart = false;
                getLocation(endRouteInput.getText().toString(), endAd);
                toNaviMap(mStartLat, mStartLon, mEndLat, mEndLon);
                break;
        }
    }

    /**
     * 编码获取经纬度
     *
     * @param cityName 地址名
     * @param cityAd   城市编号
     */
    private void getLocation(String cityName, String cityAd) {
        geocoderSearch = new GeocodeSearch(getActivity());
        geocoderSearch.setOnGeocodeSearchListener(this);
        GeocodeQuery query = new GeocodeQuery(cityName, cityAd);
        geocoderSearch.getFromLocationNameAsyn(query);
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
            mLocationOption.setInterval(5000);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();
        }
    }

    /**
     * 放回上一个页面
     */
    private void backToFragment() {
        getActivity().getSupportFragmentManager()
                .beginTransaction().remove(this).commit();
    }

    /**
     * 跳转到地址输入Fragment
     *
     * @param tag
     */
    private void toInputSelect(int tag) {
        poiSearch = new PoiSearch(getActivity().getApplication(), query);
        poiSearch.setOnPoiSearchListener(this);
        LogUtils.w("经度" + mStartLat + "纬度:" + mStartLon);
//        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(mStartLat, mStartLon),
//                Constant.SEARCH_RADIO, true));//设置周边搜索的中心点以及区域
//        poiSearch.searchPOIAsyn();
    }

    /**
     * 装换起点和终点
     */
    private void changeInput() {
        String tmpString = startRouteInput.getText().toString();
        startRouteInput.setText(endRouteInput.getText().toString());
        endRouteInput.setText(tmpString);
    }

    /**
     * 重置选择器
     */
    private void resetSelect() {
        selectFirst.setBackgroundResource(R.mipmap.not_selete_first);
        selectTime.setBackgroundResource(R.mipmap.not_select_time);
        selectMoney.setBackgroundResource(R.mipmap.not_select_money);
        selectDistance.setBackgroundResource(R.mipmap.not_selet_diatanc);

        selectText.setText("");
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
        LogUtils.d(mStartLat + "," + mStartLon + "," + mEndLat + "," + mEndLon);

        if (0d != mEndLat && 0d != mEndLon && 0d != mStartLat && 0d != mStartLon) {

            Intent intent = new Intent();
            intent.setClass(getActivity(), NaviViewActivity.class);

            intent.putExtra(Constant.startLongitude, mStartLon);

            intent.putExtra(Constant.startLatitude, mStartLat);
            intent.putExtra(Constant.endLongitude, mEndLon);
            intent.putExtra(Constant.endLatitude, mEndLat);
            intent.putExtra("driverModel", selectDriveType);

            getActivity().startActivity(intent);
        } else {
            LogUtils.d("存在空的经纬度");
        }
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (null == result)
            return;
        LogUtils.w("搜索返回信息" + result.getPageCount() + "搜索返回状态吗:" + rCode);

        if (rCode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果

                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();
                    // 取得第一页的poiitem数据，页数从数字0开始

                    LogUtils.w("搜索的大小：" + poiItems.size());
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();
                    // 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

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

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (null != aMapLocation && 0 == aMapLocation.getErrorCode()) {
            LogUtils.w("停止定位");
            //获取开始的经纬度
            Constant.MyLongitude = aMapLocation.getLongitude();
            Constant.MyLatitude = aMapLocation.getLatitude();
            mStartLon = aMapLocation.getLongitude();
            mStartLat = aMapLocation.getLatitude();
            LogUtils.w("定位的经纬度:" + Constant.MyLongitude + "::::" + Constant.MyLatitude);
            LogUtils.w("定位成功：" + aMapLocation.getAddress());
            LogUtils.w("获取城市编码：" + aMapLocation.getAdCode());
            cityName = aMapLocation.getCity();

        } else {
            // TODO: 2016-06-06 测试
            LogUtils.w("定位失败" + aMapLocation.getErrorCode() + ":" + aMapLocation.getErrorCode());
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

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

                            if (startRouteInput.hasFocus()) {
                                startRouteInput.setAdapter(aAdapter);
                                startAd = tipList.get(startRouteInput.getListSelection() -1)
                                        .getAdcode();
//                                getLocation(tipList
//                                        .get(startRouteInput.getListSelection())
//                                        .getName(), tipList
//                                        .get(startRouteInput.getListSelection())
//                                        .getAdcode());
                            } else if (endRouteInput.hasFocus()) {
                                endRouteInput.setAdapter(aAdapter);
                                endAd = tipList.get(endRouteInput.getListSelection() -1)
                                        .getAdcode();
//                                getLocation(tipList
//                                        .get(endRouteInput.getListSelection())
//                                        .getName(), tipList
//                                        .get(endRouteInput.getListSelection()).getAdcode());

                            }
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

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == 0) {
            if (result != null && result.getRegeocodeAddress() != null &&
                    result.getRegeocodeAddress().getFormatAddress() != null) {
                String addressName = result.getRegeocodeAddress().getFormatAddress() + "附近";
                Toast.makeText(getActivity(), addressName, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "操作成功", Toast.LENGTH_LONG).show();
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

    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getGeocodeAddressList() != null && result.getGeocodeAddressList().size() > 0) {
                GeocodeAddress address = result.getGeocodeAddressList().get(0);
                if (setStart) {
                    mStartLat = address.getLatLonPoint().getLatitude();
                    mStartLon = address.getLatLonPoint().getLongitude();
                    setStart = false;
                } else {
                    mEndLat = address.getLatLonPoint().getLatitude();
                    mEndLon = address.getLatLonPoint().getLongitude();
                    setStart = true;
                }

                String addressName = "经纬度值:" + address.getLatLonPoint() + "\n位置描述:" +
                        address.getFormatAddress();
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
}
