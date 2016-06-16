package com.miss.imissyou.mycar.view;

import com.miss.imissyou.mycar.bean.ResultBean;

/**
 * 处理业务需要的那些方法
 *
 * Activity 的接口类
 *
 * Created by Imissyou on 2016/3/30.
 */
public interface MainView {

    /**
     * 提示错误信息
     * @param errorNo  错误码
     * @param errorMag 错误信息
     */
    void showResultError(int errorNo, String errorMag);

    /**
     * 提示请求成功信息
     * @param resultBean
     */
    void showResultSuccess(ResultBean resultBean);
    /**
     * 显示Progress
     */
    void showProgress();

    /**
     * 隐藏Progress
     */
    void hideProgress();
}