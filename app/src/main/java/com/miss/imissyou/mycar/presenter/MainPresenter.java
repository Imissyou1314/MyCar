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
     * 加载失败
     * @param errorNo
     * @param strMsg
     */
    void onFailure(int errorNo, String strMsg);

    /**
     * 加载成功
     * @param resultBean
     */
    void onSuccess(BaseBean resultBean);

    /**
     * 加载服务器数据或者缓存数据
     */
    void loadServiceData(BaseBean useBean);

    /**
     * 装载View
     * @param view
     */
    void attachView(V view);

    /**
     * 清空View
     */
    void detchView();


}
