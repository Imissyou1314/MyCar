package com.miss.imissyou.mycar.presenter;

import android.location.Location;

import com.miss.imissyou.mycar.view.GasStationListView;

/**
 * 加油站的Presenter
 * Created by Imissyou on 2016/4/24.
 */
public interface GasStationPresenter extends MainPresenter<GasStationListView> {

    /**
     * 加载服务器数据或者缓存数据
     */
    void loadServiceData(Location location);

}
