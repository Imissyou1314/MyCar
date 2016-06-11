package com.miss.imissyou.mycar.model;

import com.miss.imissyou.mycar.bean.BaseBean;

/**
 * 订单模型获取服务器订单
 * Created by Imissyou on 2016/4/20.
 */
public interface OrderModel {

    /**
     * 根据用户信息加载用户订单数据
     * @param useBean
     */
    void loadOrderData(BaseBean useBean);

    /**
     * 删除订单
     * @param orderId 订单号
     */
    void delectOrder(Long orderId);
}
