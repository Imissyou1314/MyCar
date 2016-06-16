package com.miss.imissyou.mycar.presenter;

import com.miss.imissyou.mycar.bean.BaseBean;

/**
 *  这个接口用于连接 Modle
 * 并进行回调
 *
 * Created by Imissyou on 2016/4/2.
 */
public interface  MainPresenter<V> {

    /**
     * View <===
     * 加载失败
     * @param errorNo  错误码
     * @param strMsg   错误消息
     */
    void onFailure(int errorNo, String strMsg);

    /**
     * View <===
     * 加载成功
     * @param resultBean  响应信息实体
     */
    void onSuccess(BaseBean resultBean);

    /**
     * 装载View
     * @param view  视图
     */
    void attachView(V view);

    /**
     * 清空View
     */
    void detchView();


}
