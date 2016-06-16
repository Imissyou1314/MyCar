package com.miss.imissyou.mycar.view;


import com.miss.imissyou.mycar.bean.CarInfoBean;

/**
 * 车的具体信息视图
 * Created by Imissyou on 2016/5/3.
 */
public interface CarInfoView extends MainView{

    /**
     * 展现车轮下实体
     * @param resultBean   车辆实体
     */
    void showResultSuccess(CarInfoBean resultBean);

}
