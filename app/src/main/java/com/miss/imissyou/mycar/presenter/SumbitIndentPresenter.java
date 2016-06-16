package com.miss.imissyou.mycar.presenter;

import com.miss.imissyou.mycar.bean.OrderBean;
import com.miss.imissyou.mycar.view.fragment.BaseFragment;

/**
 * 提交订单控制中心
 * Created by Imissyou on 2016/4/25.
 */
public interface SumbitIndentPresenter extends MainPresenter<BaseFragment> {

    /**
     * ===>Model
     * 提交订单到服务器
     * @param orderBean  订单实体
     */
    void submitOrderToService(OrderBean orderBean);
}
