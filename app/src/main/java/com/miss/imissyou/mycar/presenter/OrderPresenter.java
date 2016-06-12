package com.miss.imissyou.mycar.presenter;

import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.OrderBean;
import com.miss.imissyou.mycar.bean.ResultBean;

/**
 * 订单列表信息
 * Created by Imissyou on 2016/4/20.
 */
public interface OrderPresenter<V> extends MainPresenter{

    /**
     * 删除订单
     * @param orderId
     */
    void delectOrder(Long orderId);

    /**
     * 删除订单成功
     * @param resultBean 返回结果
     */
    void delectOrderSuccess(ResultBean resultBean);

    /**
     * 加载点单
     * @param useBean
     */
    void loadServiceData(BaseBean useBean);
}
