package com.miss.imissyou.mycar.view.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import com.amap.api.maps.model.LatLng;
import com.lidroid.xutils.http.client.multipart.content.StringBody;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.GasStationBean;

import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.impl.GasStationPresenterImpl;
import com.miss.imissyou.mycar.ui.adapterutils.CommonAdapter;
import com.miss.imissyou.mycar.ui.adapterutils.ViewHolder;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.DialogUtils;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.JZLocationConverter;
import com.miss.imissyou.mycar.util.MapChangeUtils;
import com.miss.imissyou.mycar.view.GasStationView;

import com.miss.imissyou.mycar.presenter.GasStationPresenter;
import com.miss.imissyou.mycar.view.activity.NaviViewActivity;

import java.util.List;

/**
 * 加油站的信息列表
 * Created by Imissyou on 2016/5/9.
 */
public class GasStationFragment extends BaseFragment implements GasStationView, AMapLocationListener {

    private static final String TAG = "GASSTATIONFRAGMENT";
    private ListView gasListView;       //加油站的列表
    private GasStationPresenter mGasStationPresenter;
    private List<GasStationBean> gasStationBeens;       //加油站的列表

    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_gasstation, inflater, container, savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        gasListView = (ListView) view.findViewById(R.id.gasStation_ListView);
        initLoactionClicent();
        mlocationClient.startLocation();
    }

    @Override
    protected void initData() {
        mGasStationPresenter = new GasStationPresenterImpl(this);
//                double lat = MapChangeUtils.Convert_GCJ02_To_BD09_Lat(Constant.MyLatitude,Constant.MyLongitude);
//                double lng = MapChangeUtils.Convert_GCJ02_To_BD09_Lng(Constant.MyLatitude,Constant.MyLongitude);


        //   mGasStationPresenter.loadServiceData(latLng.getLatitude(), latLng.getLongitude(), 10000, Constant.GET_GASSTATION_KEY, 1, 1);
    }

    @Override
    protected void addViewsListener() {
        gasListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.d("点击了那个" + position);
                FragmentManager fm = getActivity().getSupportFragmentManager();

                SumBitIndentFragment gasFragment = new SumBitIndentFragment();
                GasStationBean gasStationBean = gasStationBeens.get(position);

                Bundle bundle = new Bundle();
                LogUtils.d("加油站的信息" + GsonUtils.Instance().toJson(gasStationBean));
                bundle.putString("gasStation", GsonUtils.Instance().toJson(gasStationBean));
                gasFragment.setArguments(bundle);

                fm.beginTransaction()
                        .replace(R.id.content_frame, gasFragment, SumBitIndentFragment.TAG)
                        .commit();
            }
        });
    }

    @Override
    public void showResultSuccess(List<GasStationBean> resultBeans) {
        this.gasStationBeens = resultBeans;
        gasListView.setAdapter(new CommonAdapter<GasStationBean>(getActivity(), resultBeans, R.layout.item_gasstionlist) {

            @Override
            public void convert(ViewHolder holder, final GasStationBean gasStationBean) {
                StringBuffer oilTypeStr = new StringBuffer();
                if (gasStationBean.getGastprice() != null) {
                    for (String key : gasStationBean.getGastprice().keySet()) {
                        oilTypeStr.append(key + ":" + gasStationBean.getGastprice().get(key) + ",");
                    }
                }
                holder.setText(R.id.gasSattion_Item_gasName, gasStationBean.getName());
                String address = setText(gasStationBean.getAddress());
                LogUtils.d("地址信息" + address);
                holder.setText(R.id.gasSattion_Item_stationAddress, address);
                holder.setText(R.id.gasSattion_Item_diatance, (Double.parseDouble(gasStationBean.getDistance()) / 1000) + "km");

                // TODO: 2016/6/5 等待测试 
                //跳转到导航哪里
                holder.setOnClickListener(R.id.gasSattion_Item_toNavi, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();

                        /**将百度经纬度装高德经纬度*/
                        LatLng latLng = MapChangeUtils
                                .Convert_BD0911_TO_GCJ02(gasStationBean.getLat(),
                                        gasStationBean.getLon(), getActivity());

                        intent.putExtra(Constant.NO_START_NAVI, true);

                        LogUtils.d("传过去的经纬度"+
                                latLng.latitude +"::::" + latLng.longitude);

                        intent.putExtra(Constant.endLatitude, latLng.latitude);
                        intent.putExtra(Constant.endLongitude, latLng.longitude);

                        intent.setClass(getActivity(), NaviViewActivity.class);
                        getActivity().startActivity(intent);
                    }
                });

                //打电话
                holder.setOnClickListener(R.id.gasSattion_Item_callPhone, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Constant.GASSTION_PHONE));
                        getActivity().startActivity(intent);
                    }
                });
            }
        });

    }



    @Override
    public void showResultError(int errorNo, String errorMag) {
        String title = "提示";
        if (errorNo == 0) {
            title = "错误";
        } else if (errorNo == 2) {
            title = "提示";
        }

        new DialogUtils(getActivity())
                .errorMessage(errorMag, title)
                .show();
    }

    @Override
    public void showResultSuccess(ResultBean resultBean) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    /**
     * 定位成功后回调函数
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (null != aMapLocation && 0 == aMapLocation.getErrorCode()) {
            LogUtils.w("停止定位");

            //获取开始的经纬度
            Constant.MyLongitude = aMapLocation.getLongitude();
            Constant.MyLatitude = aMapLocation.getLatitude();
            LogUtils.w("定位的经纬度:" + Constant.MyLongitude + "::::" + Constant.MyLatitude);
            LogUtils.w("定位成功：" + aMapLocation.getAddress());
            LogUtils.w("获取城市编码：" + aMapLocation.getAdCode());

            //高德经纬度转百度经纬度
            JZLocationConverter.LatLng latLng = new JZLocationConverter.LatLng(Constant.MyLatitude, Constant.MyLongitude);

            latLng = JZLocationConverter.gcj02ToBd09(latLng);
            LogUtils.d("转换后的经纬度:" + latLng.getLatitude() + "::::" + latLng.getLongitude());
            mGasStationPresenter.loadServiceData(latLng.getLatitude(), latLng.getLongitude(), Constant.GET_GASSTATION_R,
                    Constant.GET_GASSTATION_KEY, 1, 1);
        } else {
            // TODO: 2016-06-06 测试
            mGasStationPresenter.loadServiceData(1.0, 2.0, Constant.GET_GASSTATION_R,
                    Constant.GET_GASSTATION_KEY, 1, 1);
            LogUtils.w("定位失败" + aMapLocation.getErrorCode() + ":" + aMapLocation.getErrorCode());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //mlocationClient.onDestroy();
    }

    @Override
    public void onStart() {
        if (null != mlocationClient)
            mlocationClient.startLocation();
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != mlocationClient)
            mlocationClient.stopLocation();
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
//            mLocationOption.setInterval(5000);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();
        }
    }

    /**
     * 设置自动分行
     * @param address 传进来的字符串
     * @return
     */
    private String setText(String address) {
        StringBuffer str = new StringBuffer(address);
        if (str.length() >= 14)  {
            str.insert(14,"\n");
        } else {
            str.append("\n");
        }
        return str.toString();
    }
}
