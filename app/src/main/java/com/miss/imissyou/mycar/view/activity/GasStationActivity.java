package com.miss.imissyou.mycar.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.ui.TitleFragment;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.FindViewById;
import com.miss.imissyou.mycar.view.fragment.GasStationFragment;
import com.miss.imissyou.mycar.view.fragment.SumBitIndentFragment;

/**
 * 为了解决Fragment切换不完美的方法
 * 加油站的基类  ===>(GasStationList)  SumbitOrderList
 * Created by Imissyou on 2016/6/19.
 */
public class GasStationActivity extends BaseActivity{

    @FindViewById(id = R.id.gasSattion_baseView_title)
    TitleFragment title;

    private String type = "list";           //信息类别
    private String gasStationBean;  //转发到提交订单页面的数据
    private Fragment gasFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_gastaion_baseview);
    }

    @Override
    protected void initData() {
        type = getIntent().getStringExtra("type");

        if (type.equals("list")) {
            title.setTitleText("加油站列表");
            showGasStionListPage();
        } else {
            title.setTitleText("填写订单");
            gasStationBean = getIntent().getStringExtra("gasStation");
            showSumbitOrderPage();
        }

    }

    @Override
    public void addListeners() {

    }

    /**
     * 展现提交订单页面
     */
    private void showSumbitOrderPage() {

        FragmentManager fm = getSupportFragmentManager();
        gasFragment = fm.findFragmentByTag(Constant.SumbitOrderFragment);

        if (null == gasFragment) {
            gasFragment = new SumBitIndentFragment();
        }

        Bundle bundle = new Bundle();

        bundle.putString("gasStation", gasStationBean);
        bundle.putString("goback","list");
        gasFragment.setArguments(bundle);

        fm.beginTransaction()
                .addToBackStack(Constant.SumbitOrderFragment)
                .replace(R.id.gasSattion_baseView_overly, gasFragment, SumBitIndentFragment.TAG)
                .commit();
    }

    /**
     *  展现加油站列表
     */
    private void showGasStionListPage() {
        FragmentManager fm = getSupportFragmentManager();
        gasFragment = fm.findFragmentByTag(Constant.GasStationFragmetn);

        if (null == gasFragment) {
            gasFragment = new GasStationFragment();
        }

        fm.beginTransaction()
                .addToBackStack(Constant.GasStationFragmetn)
                .replace(R.id.gasSattion_baseView_overly, gasFragment, SumBitIndentFragment.TAG)
                .commit();
    }
}
