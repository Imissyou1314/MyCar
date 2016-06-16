package com.miss.imissyou.mycar.presenter;

import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.OrderBean;
import com.miss.imissyou.mycar.bean.ResultBean;

/**
 * 订单列表信息控制中心
 * Created by Imissyou on 2016/4/20.
 */
public interface OrderPresenter<V> extends MainPresenter{

    /**
     * ===>Model
     * 删除订单
     * @param orderId 订单ID
     */
    void delectOrder(Long orderId);

    /**
     * View<===
     * 删除订单成功
     * @param resultBean 返回结果
     */
    void delectOrderSuccess(ResultBean resultBean);

    /**
     * ===>Model
     * 加载点单
     * @param useBean 用户实体
     */
    void loadServiceData(BaseBean useBean);
}
