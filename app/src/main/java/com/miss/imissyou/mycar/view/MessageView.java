package com.miss.imissyou.mycar.view;

/**
 * 消息中心控制
 * Created by Imissyou on 2016/5/2.
 */
public interface MessageView extends MainView{

    /**
     * View <===
     * 删除成功回调
     * @param resultMessage  回调信息
     */
    void deleteSucces(String resultMessage);
}
