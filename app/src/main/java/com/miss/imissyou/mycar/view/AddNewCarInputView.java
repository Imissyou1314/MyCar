package com.miss.imissyou.mycar.view;

import com.miss.imissyou.mycar.bean.ResultBean;

/**
 * Created by Imissyou on 2016/4/18.
 */
public interface AddNewCarInputView  extends MainView{

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
}
