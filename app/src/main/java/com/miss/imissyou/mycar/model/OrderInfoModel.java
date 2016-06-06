package com.miss.imissyou.mycar.model;

/**
 * 获取订单详情的Model
 * Created by Imissyou on 2016/6/6.
 */
public interface OrderInfoModel {

    /**
     * 根据订单号获取订单
     * @param orderId
     */
    void loadOrderFormService(long orderId);
}
