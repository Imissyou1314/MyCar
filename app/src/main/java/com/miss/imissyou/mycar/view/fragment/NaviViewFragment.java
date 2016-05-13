package com.miss.imissyou.mycar.view.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;

import java.io.File;

/**
 * 导航
 * Created by Imissyou on 2016/5/2.
 */
public class NaviViewFragment extends BaseFragment{

    private String APP_FOLDER_NAME = "MYCAR";
    private String mSDCardPath = null;

    public static final String ROUTE_PLAN_NODE = "routePlanNode";
    public static final String SHOW_CUSTOM_ITEM = "showCustomItem";
    public static final String RESET_END_NODE = "resetEndNode";
    public static final String VOID_MODE = "voidMode";

    @Nullable
    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_navi, inflater, container, savedInstanceState);
    }

    @Override protected void initView(View view) {

    }

    @Override protected void initData() {
        //获取订单页面传过来的地址
        String address = getArguments()
                .getBundle(OrderFragment.OrderAddrress).getString("address");
    }

    @Override
    protected void addViewsListener() {
        initNavi();
    }

    private void initNavi() {
        initDirs();
        BaiduNaviManager.getInstance().init(getActivity() , mSDCardPath, APP_FOLDER_NAME,
                new BaiduNaviManager.NaviInitListener() {
                    @Override public void onAuthResult(int status, String msg) {
                        LogUtils.d("status:" + status + ">>>msg:" + msg);
                        if (0 == status) {
                            LogUtils.d("key校验成功!");
                        } else {
                            LogUtils.d("key校验失败, " + msg);
                        }
                        getActivity().runOnUiThread(new Runnable() {

                            @Override public void run() {
                                Toast.makeText(getActivity(), "打印", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    public void initSuccess() {
                        Toast.makeText(getActivity(), "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                    }

                    public void initStart() {
                        Toast.makeText(getActivity(), "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
                    }

                    public void initFailed() {
                        Toast.makeText(getActivity(), "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
                    }
                }, null /*mTTSCallback*/);
    }

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState()
                .equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }
}
