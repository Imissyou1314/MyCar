package com.miss.imissyou.mycar.presenter;

import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.ResultBean;

/**
 * 获取用户车辆控制中心
 * Created by Imissyou on 2016/4/20.
 */
public interface CarListPresenter<V> {


    /**
     *
     * 装载View
     * @param view
     */
    void attachView(V view);

    /**
     * 清空View
     */
    void detchView();

    /**
     * View <===
     * 加载失败
     * @param errorNo 错误码
     * @param strMsg  错误信息
     */
    void onFailure(int errorNo, String strMsg);

    /**
     * View <===
     * 加载成功
     * @param resultBean  ResultBean
     */
    void onSuccess(BaseBean resultBean);

    /**
     * 加载服务器数据或者缓存数据
     */
    void loadServiceData(String userId);

    /**
     * ===>Model
     * 删除车辆
     * @param carId 车辆Id
     */
    void delectCar(Long carId);

    /**
     *
     * View<===
     * 删除成功
     * @param resultBean  回调请求实体
     */
    void delectSuccess(ResultBean resultBean);

}
