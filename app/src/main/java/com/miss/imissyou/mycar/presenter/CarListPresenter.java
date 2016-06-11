package com.miss.imissyou.mycar.presenter;

import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.ResultBean;

/**
 * 获取用户
 * Created by Imissyou on 2016/4/20.
 */
public interface CarListPresenter<V> {


    /**
     * 装载View
     * @param view
     */
    void attachView(V view);

    /**
     * 清空View
     */
    void detchView();

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
    void loadServiceData(String userId);

    /**
     * 删除车辆
     * @param carId
     */
    void delectCar(Long carId);

    /**
     * 删除成功
     * @param resultBean
     */
    void delectSuccess(ResultBean resultBean);

}
