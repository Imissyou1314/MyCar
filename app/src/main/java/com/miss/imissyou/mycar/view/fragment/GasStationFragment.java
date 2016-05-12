package com.miss.imissyou.mycar.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.GasStationBean;
import com.miss.imissyou.mycar.bean.OilBean;
import com.miss.imissyou.mycar.bean.OrderBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.impl.GasStationPresenterImpl;
import com.miss.imissyou.mycar.ui.MissDialog;
import com.miss.imissyou.mycar.ui.adapterutils.CommonAdapter;
import com.miss.imissyou.mycar.ui.adapterutils.ViewHolder;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.view.GasStationView;

import com.miss.imissyou.mycar.presenter.GasStationPresenter;

import java.util.List;

/**
 * 加油站的信息列表
 * Created by Imissyou on 2016/5/9.
 */
public class GasStationFragment extends BaseFragment implements GasStationView {

    private ListView gasListView;       //加油站的列表
    private GasStationPresenter mGasStationPresenter;
    private List<GasStationBean> gasStationBeens;       //加油站的列表

    @Nullable
    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_gasstation, inflater, container, savedInstanceState);
    }

    @Override protected void initView(View view) {
        gasListView = (ListView) view.findViewById(R.id.gasStation_ListView);
    }

    @Override protected void initData() {
        mGasStationPresenter = new GasStationPresenterImpl(this);
    }

    @Override protected void addViewsListener() {
        gasListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SumBitIndentFragment gasFragment = new SumBitIndentFragment();
                GasStationBean gasStationBean = gasStationBeens.get(position);

                Bundle bundle = new Bundle();
                bundle.putDouble("lat", gasStationBean.getLat());
                bundle.putDouble("lot", gasStationBean.getLon());
                bundle.putString("address", gasStationBean.getAddress());
                bundle.putString("oilType", GsonUtils.Instance().toJson(gasStationBean.getPrice()));
                gasFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, gasFragment)
                        .commit();
            }
        });
    }

    @Override public void showResultSuccess(List<GasStationBean> resultBeans) {

        new CommonAdapter<GasStationBean>(getActivity(), resultBeans, R.layout.item_gasstionlist) {

            @Override
            public void convert(ViewHolder holder, GasStationBean gasStationBean) {
                StringBuffer oilTypeStr = new StringBuffer();
                if (gasStationBean != null) {
                    if (gasStationBean.getPrice().size() != 0) {
                        for (OilBean oilBean : gasStationBean.getPrice())
                            oilTypeStr.append(oilBean.getOilType() + ",");
                    }
                }
                holder.setText(R.id.gasSattion_Item_stationName, gasStationBean.getName());
                holder.addText(R.id.gasSattion_Item_diatance, (Double.parseDouble(gasStationBean.getDistance()) / 1000) + "");
                holder.setText(R.id.gasSattion_Item_stationType, gasStationBean.getBrandname());
                holder.setText(R.id.gasSattion_Item_oilType, oilTypeStr.substring(0, oilTypeStr.length() - 1).toString());
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

    @Override public void showResultError(int errorNo, String errorMag) {

    }

    @Override public void showResultSuccess(ResultBean resultBean) {

    }

    @Override public void showProgress() {

    }

    @Override public void hideProgress() {

    }
}
