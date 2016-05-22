package com.miss.imissyou.mycar.view.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.MaterialDialog;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.OrderBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.impl.OrderPresenterImpl;
import com.miss.imissyou.mycar.presenter.OrderPresenter;
import com.miss.imissyou.mycar.ui.adapterutils.CommonAdapter;
import com.miss.imissyou.mycar.ui.adapterutils.ViewHolder;
import com.miss.imissyou.mycar.ui.sidemenu.interfaces.ScreenShotable;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.view.OrderListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单列表
 * Created by Imissyou on 2016/4/20.
 */
public class OrderFragment extends ListFragment implements OrderListView, ScreenShotable {

    private OrderPresenter mOrderPresenter;
    private List<OrderBean> mOrders = new ArrayList<>();
    private List<OrderBean> orders;         //定单列表

    public static final String  OrderAddrress= "order";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mOrderPresenter = new OrderPresenterImpl(this);
        mOrderPresenter.loadServiceData(Constant.userBean);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mOrderPresenter.detchView();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        final OrderBean order = orders.get(position);
        new AlertDialogWrapper.Builder(getActivity()).setTitle(order.getBrandName());
        new AlertDialogWrapper.Builder(getActivity()).setMessage(order.getAddress());
        new AlertDialogWrapper.Builder(getActivity()).setNegativeButton(R.string.go, new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                BaseFragment NaviFragment = new NaviViewFragment();
                Bundle bundle = new Bundle();
                bundle.putString("address", order.getAddress());
                bundle.putString("statioName",order.getStationName());
                bundle.putString("brandName",order.getBrandName());

                NaviFragment.getArguments().putBundle(OrderAddrress, bundle);
            }
        });
        new AlertDialogWrapper.Builder(getActivity()).setPositiveButton(R.string.ok, new Dialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        new AlertDialogWrapper.Builder(getActivity()).show();
    }

    @Override
    public void showResultSuccess(List<OrderBean> orders) {
        //TODO 给mOrders填充数据
        this.orders = orders;
        setListAdapter(new CommonAdapter<OrderBean>(getActivity(),
                orders,
                R.layout.fragment_order_list) {
            @Override
            public void convert(ViewHolder holder, final OrderBean orderBean) {
                holder.setText(R.id.order_list_gasNumber, null);
                holder.setText(R.id.order_list_stationName, null);
                holder.setText(R.id.order_list_gasType, null);
                holder.setText(R.id.order_list_price, null);

                holder.setOnClickListener(R.id.order_list_goStation, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //定位加油站并导航
                        String address = orderBean.getAddress();        //地址
                        NaviViewFragment naviViewFragment = new NaviViewFragment();
                        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("address", address);
                        naviViewFragment.setArguments(bundle);
                        fm.replace(R.id.container_frame, naviViewFragment).commit();
                    }
                });

                holder.setOnClickListener(R.id.order_list_callStation, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent callIntent = new Intent();
                        callIntent.setAction("Android.intent.action.CALL");
                        callIntent.setData(Uri.parse("tel:" + "你的电话"));
                        startActivity(callIntent);
                    }
                });
            }
        });
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}
