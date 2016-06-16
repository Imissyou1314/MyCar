package com.miss.imissyou.mycar.view;

import com.miss.imissyou.mycar.bean.GasStationBean;

import java.util.List;

/**
 * 加油列表视图
 * Created by Imissyou on 2016/5/9.
 */
public interface GasStationView extends MainView {

    /**
     * 加油站获取加油站列表成功
     * @param resultBeans   加油站列表
     */
    void showResultSuccess(List<GasStationBean> resultBeans);
}
