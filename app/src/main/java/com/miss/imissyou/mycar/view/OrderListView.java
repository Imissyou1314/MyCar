package com.miss.imissyou.mycar.view;

import com.miss.imissyou.mycar.bean.OrderBean;

import java.util.List;

/**
 * Created by Imissyou on 2016/4/20.
 */
public interface OrderListView extends MainView{
    
    void showResultSuccess(List<OrderBean> orders);
}
