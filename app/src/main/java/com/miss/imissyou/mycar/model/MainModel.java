package com.miss.imissyou.mycar.model;

import com.miss.imissyou.mycar.bean.BaseBean;

/**
 * Created by Imissyou on 2016/4/2.
 */
public interface MainModel {
    /**
     * 加载数据
     */
    void loadData(String ID);

    /**
     * 保存数据
     * @param mBaseBean
     */
    void saveData(String ID,BaseBean mBaseBean);
}
