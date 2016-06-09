package com.miss.imissyou.mycar.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.OrderBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.OrderInfoPresenter;
import com.miss.imissyou.mycar.presenter.impl.OrderInfoPresenterImpl;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.view.OrderInfoView;

/**
 * 订单详细页面
 * Created by Imissyou on 2016/6/6.
 */
public class OrderInfoFragment extends BaseFragment implements OrderInfoView {

    private TextView orderState;            //订单状态
    private TextView orderPrice;            //订单总价

    private TextView oilType;               //订单类型
    private TextView address;               //加油站地址

    private TextView userName;              //用户名
    private TextView carBrand;              //车牌号
    private TextView orderTime;             //订单时间
    private TextView orderId;               //订单号

    private ImageView orderCodeImage;       //订单二维码

    private OrderInfoPresenter mOrderInfoPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_orderinfo,
                inflater, container, savedInstanceState);
    }

    @Override protected void initView(View view) {
        orderState = (TextView) view.findViewById(R.id.orderInfo_orderState_tv);
        orderPrice = (TextView) view.findViewById(R.id.orderInfo_orderTotalPrice);

        oilType = (TextView) view.findViewById(R.id.orderInfo_oilType);
        address = (TextView) view.findViewById(R.id.orderInfo_oilStationAddress);

        userName = (TextView) view.findViewById(R.id.orderInfo_orderUserName);
        carBrand = (TextView) view.findViewById(R.id.orderInfo_carBrand);

        orderId = (TextView) view.findViewById(R.id.orderInfo_orderId);
        orderTime = (TextView) view.findViewById(R.id.orderInfo_orderTime);
        orderCodeImage = (ImageView) view.findViewById(R.id.orderInfo_orderCode_image);
    }

    @Override protected void initData() {
        Long orderId = getArguments().getLong("orderId");
        LogUtils.w("获取到的订单Id:" + orderId);
        mOrderInfoPresenter = new OrderInfoPresenterImpl(this);
        mOrderInfoPresenter.loadOrderFormService(orderId);
    }

    @Override
    protected void addViewsListener() {

    }

    @Override
    public void callBackOderBean(OrderBean orderBean) {
        setupPage(orderBean);
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

    private void setupPage(OrderBean orderBean) {

        orderPrice.setText(orderBean.getPrice() + "");
        orderState.setText(getOrderState(orderBean.getState()));

        oilType.setText(orderBean.getType());
        userName.setText(orderBean.getUserName());
        address.setText(orderBean.getAddress());

        carBrand.setText(orderBean.getBrandName());
        orderId.setText(orderBean.getId() + "");
        orderTime.setText(orderBean.getAgreementTime());

        String imageUrl = Constant.SERVER_URL + orderBean.getId() + ".png";
        loadOrderCodeImage(imageUrl);
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

    /**
     * 加载订单二维码
     * @param imageUrl
     */
    private void loadOrderCodeImage(String imageUrl) {
       // Glide.with(this).load(imageUrl).into(orderCodeImage);
    }

    /**
     * 根据订单状态获取订单的中文状态
     * @param state
     */
    private String getOrderState(Integer state) {
        String title = "未支付";

        switch (state) {
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
