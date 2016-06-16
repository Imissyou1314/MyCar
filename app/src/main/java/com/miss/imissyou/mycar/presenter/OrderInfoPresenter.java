package com.miss.imissyou.mycar.presenter;

import com.miss.imissyou.mycar.view.OrderInfoView;

/**
 * 订单信息控制类
 * Created by Imissyou on 2016/6/6.
 */
public interface OrderInfoPresenter extends MainPresenter<OrderInfoView>{

    /**
     * ===>Model
     * 从服务器获取订单信息
     * @param OrderId 订单Id
     */
    void loadOrderFormService(Long OrderId);
}
