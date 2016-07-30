package com.miss.imissyou.mycar.view.fragment;



import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.ikidou.fragmentBackHandler.FragmentBackHandler;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.OrderBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.impl.OrderPresenterImpl;
import com.miss.imissyou.mycar.presenter.OrderPresenter;
import com.miss.imissyou.mycar.ui.MissPopWindows;
import com.miss.imissyou.mycar.ui.adapterutils.CommonAdapter;
import com.miss.imissyou.mycar.ui.adapterutils.ViewHolder;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.view.OrderListView;

import java.util.List;

/**
 * 订单列表
 * Created by Imissyou on 2016/4/20.
 */
public class OrderFragment extends BaseFragment implements
        OrderListView, AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener, FragmentBackHandler {

    private OrderPresenter mOrderPresenter;
    private List<OrderBean> orders;         //定单列表
    private ListView orderListView;       //订单列表视图

    MissPopWindows missPopWindows;                                      //底部弹框
    private int delectOrderId;                                            //删除订单Id
    private CommonAdapter<OrderBean> adapter;
    private boolean handleBackPressed = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_order_list,inflater, container, savedInstanceState);
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override protected void initView(View view) {
        orderListView = (ListView) view.findViewById(R.id.order_listView);
        orderListView.addFooterView(getFooterView());
    }

    @Override protected void initData() {
        if (mOrderPresenter == null) {
            mOrderPresenter = new OrderPresenterImpl(this);
        }
        mOrderPresenter.loadServiceData(Constant.userBean);
    }

    @Override protected void addViewsListener() {
        orderListView.setOnItemLongClickListener(this);
        orderListView.setOnItemClickListener(this);
    }

    @Override public void showResultError(int errorNo, String errorMag) {

    }

    @Override public void showResultSuccess(ResultBean resultBean) {
        if (resultBean.isServiceResult()) {
            Toast.makeText(getActivity(), resultBean.getResultInfo().equals("")?
                    "删除第"+ delectOrderId + "条订单成功": resultBean.getResultInfo()
                    , Toast.LENGTH_SHORT).show();
            orders.remove(delectOrderId);
            adapter.notifyDataSetChanged();
        }
    }

    @Override public void showProgress() {

    }

    @Override public void hideProgress() {

    }

    @Override public void onDestroy() {
        super.onDestroy();
    }

    @Override public void showResultSuccess(List<OrderBean> orders) {

        this.orders = orders;

        adapter = new CommonAdapter<OrderBean>
                (getActivity(), orders, R.layout.item_order) {
            @Override public void convert(ViewHolder holder, final OrderBean orderBean) {
                holder.setText(R.id.order_item_orderId, orderBean.getId() + "");
                holder.setText(R.id.order_item_carBrand, orderBean.getPlateNumber());
                LogUtils.d("获取的订单状态:" + orderBean.getState());
                holder.setText(R.id.order_item_orderState, getOrderState(orderBean.getState()));
                holder.setText(R.id.order_item_orderTime, orderBean.getAgreementTime());
                holder.setText(R.id.order_item_orderTotalPrice, "￥ " + orderBean.getAmounts());
            }
        };
        orderListView.setAdapter(adapter);
    }

    @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtils.d("点击的：" + position);
        OrderBean order = orders.get(position);
        LogUtils.d("订单信息ID " + order.getId());
        android.support.v4.app.FragmentManager fm = getActivity()
                .getSupportFragmentManager();
        OrderInfoFragment orderInfoFragment = new OrderInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("orderId", order.getId());
        orderInfoFragment.setArguments(bundle);

        fm.beginTransaction()
                .addToBackStack(Constant.OrderFragment)
                .replace(R.id.content_frame,orderInfoFragment)
                .commit();
    }

    /**
     * 根据订单状态获取订单的中文状态
     * @param playState  订单的状态
     */
    private String getOrderState(Integer playState) {
        String title = "未支付";
        switch (playState) {
            case 0:
                title = "已支付";
                break;
            case 1:
                title = "未支付";
                break;
            case 2:
                title = "已过期";
                break;
        }
        return title;
    }

    /**
     * 添加一个View
     * @return View
     */
    private View getFooterView() {
        AbsListView.LayoutParams lp = new
                AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,20);

        View view = new View(getActivity());
        view.setLayoutParams(lp);
        view.setBackgroundResource(R.color.color_gui);
        return view;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtils.w("点击了那个" + position);
        missPopWindows = new MissPopWindows(getActivity(),itemOnClick);
        delectOrderId = position;
        //显示弹窗的位置
        missPopWindows.showAtLocation(getActivity().findViewById(R.id.container_frame),
                Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
        return true;
    }

    /**弹框的监听事件*/
    private View.OnClickListener itemOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            missPopWindows.dismiss();
            if (v.getId() == R.id.btn_pop_delect) {
                delectOrder();
            }
        }
    };

    /**
     * 删除订单
     */
    private void delectOrder() {
        mOrderPresenter.delectOrder(orders.get(delectOrderId).getId());
    }

    /**
     * 监控返回键的触发事件
     * @return
     */
    @Override public boolean onBackPressed() {
        if (handleBackPressed) {

            return true;
        } else {
            return false;
        }
    }
}
