package com.miss.imissyou.mycar.presenter;

import com.miss.imissyou.mycar.bean.OrderBean;
import com.miss.imissyou.mycar.view.fragment.BaseFragment;

/**
 * 提交订单
 * Created by Imissyou on 2016/4/25.
 */
public interface SumbitIndentPresenter extends MainPresenter<BaseFragment> {

    /**
     * 提交订单到服务器
     * @param orderBean
     */
    void submitOrderToService(OrderBean orderBean);
}
