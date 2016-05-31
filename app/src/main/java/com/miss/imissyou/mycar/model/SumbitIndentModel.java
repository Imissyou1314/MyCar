package com.miss.imissyou.mycar.model;

import com.miss.imissyou.mycar.bean.OrderBean;

/**
 * 提交订单的接口
 * Created by Imissyou on 2016/4/25.
 */
public interface SumbitIndentModel  {

    /**
     * 提交订单到服务器
     * @param orderBean
     */
    void sentIndentToService(OrderBean orderBean);

    /**
     * 根据订单Id删除订单
     * @param userId
     * @param orderId
     */
    void deleteOrder(int userId, int orderId);
}
