package com.miss.imissyou.mycar.view;

import com.miss.imissyou.mycar.bean.CarInfoBean;

/**
 * 添加新车的页面
 * Created by Imissyou on 2016/4/18.
 */
public interface AddNewCarInputView  extends MainView{

    /**
     * 提示请求成功信息
     * @param resultBean  -车辆信息
     */
    void showResultSuccess(CarInfoBean resultBean);
}
