package com.miss.imissyou.mycar.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.GasStationBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.impl.GasStationPresenterImpl;
import com.miss.imissyou.mycar.ui.adapterutils.CommonAdapter;
import com.miss.imissyou.mycar.ui.adapterutils.ViewHolder;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.view.GasStationView;

import com.miss.imissyou.mycar.presenter.GasStationPresenter;

import java.util.List;

/**
 * 加油站的信息
 * Created by Imissyou on 2016/5/9.
 */
public class GasStationFragment extends BaseFragment implements GasStationView {

    private ListView gasListView;       //加油站的列表
    private GasStationPresenter mGasStationPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_gasstation, inflater, container, savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        gasListView = (ListView) view.findViewById(R.id.gasStation_ListView);
    }

    @Override
    protected void initData() {
        mGasStationPresenter = new GasStationPresenterImpl(this);
    }

    @Override
    protected void addViewsListener() {

    }

    @Override
    public void showResultSuccess(List<GasStationBean> resultBeans) {

        new CommonAdapter<GasStationBean>(getActivity(), resultBeans, R.layout.item_gasstionlist) {

            @Override
            public void convert(ViewHolder holder, GasStationBean gasStationBean) {
                holder.setText(R.id.gasSattion_Item_stationName, gasStationBean.getName());
                holder.addText(R.id.gasSattion_Item_diatance, (Double.parseDouble(gasStationBean.getDistance()) / 1000) + "");
                holder.setText(R.id.gasSattion_Item_stationType, gasStationBean.getBrandname());
                if (gasStationBean.getBrandname() == Constant.GASSTION_BRAND_ZHONGSHIYOU) {
                    //TODO
                    holder.setImage(R.id.gasSattion_Item_Icon, 0);
                } else {
                    //TODO
                    holder.setImage(R.id.gasSattion_Item_Icon, 0);
                }
            }
        };

    }

    @Override
    public void showResultError(int errorNo, String errorMag) {

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
}
