package com.miss.imissyou.mycar.view;

import com.miss.imissyou.mycar.bean.GasStationResultBean;
import com.miss.imissyou.mycar.bean.ResultBean;

/**
 * 导航页面的页面视图
 * Created by Imissyou on 2016/6/12.
 */
public interface NaviVieFragmentView {


    /**
     * 获取附近停车场
     * @param resultBean 放回结果
     */
    void loadSucccessPark(ResultBean resultBean);

    /**
     * 获取附近维修站
     * @param resultBean  放回结果
     */
    void loadSuccessRepairSHop(ResultBean resultBean);

    /**
     * 获取附近十公里内的加油站
     * @param resultBean  放回结果
     */
    void loadSuccessGasStation(GasStationResultBean resultBean);

    /**
     * 获取数据失败
     * @param errorNumber   错误吗
     * @param errMsg       错误信息
     */
    void loadFail(int errorNumber, String errMsg);
}
