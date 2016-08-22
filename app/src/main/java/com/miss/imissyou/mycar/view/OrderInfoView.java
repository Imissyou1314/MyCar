package com.miss.imissyou.mycar.view;

import com.miss.imissyou.mycar.bean.OrderBean;
import com.miss.imissyou.mycar.bean.ResultBean;

/**
 * 订单详情界面视图
 * Created by Imissyou on 2016/6/6.
 */
public interface OrderInfoView extends MainView {

    /**
     * 获取订单状态回调接口
     * @param orderBean 订单
     */
    void callBackOderBean(OrderBean orderBean);

    /**
     * 更新成功后的返回值
     *
     * @param resultBean
     */
    void updateSuccess(ResultBean resultBean);

    /**
     * 更新失败的信息
     * @param errorCode
     * @param errorStr
     */
    void updateFaile(Integer errorCode, String errorStr);
}
