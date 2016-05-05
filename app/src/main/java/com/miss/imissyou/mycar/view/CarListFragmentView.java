package com.miss.imissyou.mycar.view;

import com.miss.imissyou.mycar.bean.CarInfoBean;

import java.util.List;

/**
 * Created by Imissyou on 2016/5/4.
 */
public interface CarListFragmentView extends MainView{


    void showResultSuccess(List<CarInfoBean> resultBean);
}
