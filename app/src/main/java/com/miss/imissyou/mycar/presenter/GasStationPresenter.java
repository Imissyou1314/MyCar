package com.miss.imissyou.mycar.presenter;

import com.miss.imissyou.mycar.view.GasStationView;

/**
 * 加油站的Presenter
 * Created by Imissyou on 2016/4/24.
 */
public interface GasStationPresenter extends MainPresenter<GasStationView> {

    /**
     * ===> Model
     * 加载服务器数据或者缓存数据
     * @param lon  经纬(如:121.538123)
     * @param lat  纬度(如：31.677132)
     * @param r    搜索范围，单位M，默认3000，最大10000
     * @param key  应用APPKEY(应用详细页查询)
     * @param page 页数,默认1
     * @param format  格式选择1或2，默认1
     */
    void loadServiceData(double lon, double lat, int r, String key, int page, int format);

}
