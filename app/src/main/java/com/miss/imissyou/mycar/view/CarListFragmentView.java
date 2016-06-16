package com.miss.imissyou.mycar.view;

import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;

import java.util.List;

/**
 * 车辆列表视图
 * Created by Imissyou on 2016/5/4.
 */
public interface CarListFragmentView extends MainView{


    /**
     * 获取车辆信息成功
     * @param resultBean  车辆列表
     */
    void showResultSuccess(List<CarInfoBean> resultBean);

    /**
     * 删除成功
     * @param resultBean 请求回调实体
     */
    void showDelectSuccess(ResultBean resultBean);
}
