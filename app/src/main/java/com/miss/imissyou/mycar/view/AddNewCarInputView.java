package com.miss.imissyou.mycar.view;

import com.miss.imissyou.mycar.bean.CarInfoBean;

/**
 * Created by Imissyou on 2016/4/18.
 */
public interface AddNewCarInputView  extends MainView{

    /**
     * 提示请求成功信息
     * @param resultBean
     */
    void showResultSuccess(CarInfoBean resultBean);
}
