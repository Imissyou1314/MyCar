package com.miss.imissyou.mycar.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

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
 * Created by Imissyou on 2016/4/20.
 */
public class OrderFragment extends ListFragment implements OrderListView ,ScreenShotable {

    private OrderPresenter mOrderPresenter;
    private List<OrderBean> mOrders = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mOrderPresenter = new OrderPresenterImpl(this);
        mOrderPresenter.loadServiceData(Constant.userBean);

    }

    @Override public void showResultError(int errorNo, String errorMag) {

    }

    @Override public void showResultSuccess(ResultBean resultBean) {


    }

    @Override public void showProgress() {

    }

    @Override public void hideProgress() {

    }

    @Override public void onDestroy() {
        super.onDestroy();
        mOrderPresenter.detchView();
    }

    @Override public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public void showResultSuccess(List<OrderBean> orders) {
        //TODO 给mOrders填充数据
        setListAdapter(new CommonAdapter<OrderBean>(getActivity(),
                orders,
                R.layout.fragment_order_list) {
            @Override public void convert(ViewHolder holder, OrderBean orderBean) {
                holder.setText(R.id.order_list_gasNumber,null);
                holder.setText(R.id.order_list_stationName,null);
                holder.setText(R.id.order_list_gasType,null);
                holder.setText(R.id.order_list_price,null);

                holder.setOnClickListener(R.id.order_list_goStation, new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        //定位加油站并导航
                    }
                });

                holder.setOnClickListener(R.id.order_list_callStation,new View.OnClickListener(){

                    @Override public void onClick(View v) {
                        Intent callIntent = new Intent();
                        callIntent.setAction("Android.intent.action.CALL");
                        callIntent.setData(Uri.parse("tel:" + "你的电话"));
                        startActivity(callIntent);
                    }
                });
            }
        });
    }

    @Override public void takeScreenShot() {

    }

    @Override public Bitmap getBitmap() {
        return null;
    }
}
