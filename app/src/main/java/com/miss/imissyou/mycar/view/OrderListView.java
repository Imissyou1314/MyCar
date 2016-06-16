package com.miss.imissyou.mycar.view;

import com.miss.imissyou.mycar.bean.OrderBean;

import java.util.List;

/**
 * 订单列表视图
 * Created by Imissyou on 2016/4/20.
 */
public interface OrderListView extends MainView{

    /**
     * 获取订单成功
     * @param orders  订单
     */
    void showResultSuccess(List<OrderBean> orders);
}
