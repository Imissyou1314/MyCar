package com.miss.imissyou.mycar.model;

import com.miss.imissyou.mycar.bean.BaseBean;

/**
 * 操作数据模型
 * Created by Imissyou on 2016/4/18.
 */
public interface AddNewCarInputModel {

    /**
     * 发送车辆信息给服务器
     * @param baseBean
     */
    void sentToService(BaseBean baseBean);

    /**
     * 保存车辆信息到本地
     * @param baseBean
     * @param key
     */
    void saveToSPU(BaseBean baseBean, String key);
}
