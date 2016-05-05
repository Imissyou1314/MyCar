package com.miss.imissyou.mycar.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.GasStationBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.GasStationPresenter;
import com.miss.imissyou.mycar.presenter.impl.GasStationPresenterImpl;
import com.miss.imissyou.mycar.ui.adapterutils.CommonAdapter;
import com.miss.imissyou.mycar.ui.adapterutils.ViewHolder;
import com.miss.imissyou.mycar.view.GasStationListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 加油站列表
 * Created by Imissyou on 2016/4/24.
 */
public class GasStationListFragment extends ListFragment implements GasStationListView{

    private GasStationPresenter gasStationPresenter;
    private List<GasStationBean> gasStationBeanList = new ArrayList<GasStationBean>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gasStationPresenter = new GasStationPresenterImpl(this);
    }

    @Override
    public void showResultError(int errorNo, String errorMag) {

    }

    @Override public void showResultSuccess(ResultBean resultBean) {
        setListAdapter(new CommonAdapter<GasStationBean>(getContext(), gasStationBeanList, R.layout.item_gasstionlist) {
            @Override public void convert(ViewHolder holder, GasStationBean gasStationBean) {
                holder.setText(R.id.gasSattion_Item_diatance, gasStationBean.getDistance());
                holder.setText(R.id.gasSattion_Item_stationType, gasStationBean.getStationType());
                holder.setText(R.id.gasSattion_Item_stationName, gasStationBean.getStationName());
                holder.setText(R.id.gasSattion_Item_oilType, gasStationBean.getOilType());
                holder.setImage(R.id.gasSattion_Item_Icon, gasStationBean.getStationIcon());
            }
        });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

    }

    private void toSumbitIndent(GasStationBean gasStation) {
        FragmentTransaction transaction = getActivity().
                getSupportFragmentManager().beginTransaction();
        SumBitIndentFragment sumBitIndent = new SumBitIndentFragment();

    }
}
