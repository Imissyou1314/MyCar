package com.miss.imissyou.mycar.view;

import com.miss.imissyou.mycar.bean.CarInfoBean;

import java.util.List;

/**
 * 车库信息
 * Created by Imissyou on 2016/4/20.
 */
public interface CarInfoFragmentView<T> {

    /**
     * 显示Progress
     */
    void showProgress();

    /**
     * 隐藏Progress
     */
    void hideProgress();

    /**
     * 提示错误信息
     * @param errorNo  错误码
     * @param errorMag 错误信息
     */
    void showResultError(int errorNo, String errorMag);

    /**
     * 提示请求成功信息
     * @param cars  车辆列表
     */
    void showResultSuccess(List<CarInfoBean> cars);
}
