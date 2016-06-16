package com.miss.imissyou.mycar.view;

import com.miss.imissyou.mycar.bean.OilBean;
import com.miss.imissyou.mycar.bean.ServiceStation;
import com.miss.imissyou.mycar.bean.StopStation;

import java.util.List;

/**
 * 定位加油站或者维修站或者停车场的位置视图
 * Created by Imissyou on 2016/5/19.
 */
public interface LocationView extends MainView {

    /**
     * 获取加油站位置成功
     * @param oilBeanList 油量列表
     */
    void showOilStationSuccess (List<OilBean> oilBeanList);

    /**
     * 获取维修站信息成功
     * @param ServiceList  维修站列表
     */
    void showServiceStationSuccess(List<ServiceStation> ServiceList);

    /**
     * 获取停车场信息成功
     * @param stopStations  停车场列表
     */
    void showStopStationSuccess(List<StopStation> stopStations);
}
