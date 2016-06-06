package com.miss.imissyou.mycar.view.fragment;


import android.graphics.Bitmap;

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

        Bundle bundle = new Bundle();
        bundle.putLong("orderId",order.getId());

        OrderInfoFragment orderInfoFragment = new OrderInfoFragment();
        orderInfoFragment.setArguments(bundle);

        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_frame, orderInfoFragment)
                .commit();

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
                holder.setText(R.id.order_item_orderId, orderBean.getId() + "");
//                holder.setText(R.id.order_item_carBrand, orderBean.getC)
                holder.setText(R.id.order_item_orderState, getOrderState(orderBean.getState()));
                holder.setText(R.id.order_item_orderTime, orderBean.getAgreementTime());
                holder.setText(R.id.order_item_orderTotalPrice, "￥ " + orderBean.getPrice());
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

    /**
     * 根据订单状态获取订单的中文状态
     * @param playState
     */
    private String getOrderState(Integer playState) {
        String title = "未支付";

        switch (playState) {
            case 0 :
                title = "已支付";
                break;
            case 1:
                title = "未支付";
                break;
            case 2:
                title ="已过期";
                break;
        }
        return title;
    }


}
