package com.miss.imissyou.mycar.view;

import com.miss.imissyou.mycar.bean.GasStationBean;

import java.util.List;

/**
 * 加油列表
 * Created by Imissyou on 2016/5/9.
 */
public interface GasStationView extends MainView {

    /**
     * 加油站的列表回调
     * @param resultBeans
     */
    void showResultSuccess(List<GasStationBean> resultBeans);
}
