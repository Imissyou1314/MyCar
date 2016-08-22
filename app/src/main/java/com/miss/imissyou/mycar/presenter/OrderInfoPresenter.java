package com.miss.imissyou.mycar.presenter;

import com.miss.imissyou.mycar.bean.ResultBean;
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

    /**
     * ===>Model
     * 更新订单状态
     * @param orderId   订单号
     * @param orderStatu    订单状态
     */
    void updateOrderInfo(Long orderId,Integer orderStatu);

    /**
     * 更新成功
     * @param resultBean
     */
    void updateSuccess(ResultBean resultBean);

    /**
     * 更新失败
     * @param errorCode
     * @param errorStr
     */
    void updateFaile(Integer errorCode, String errorStr);
}
