package com.miss.imissyou.mycar.view;

/**
 * 消息视图
 * Created by Imissyou on 2016/5/2.
 */
public interface MessageView extends MainView{

    /**
     * 删除成功回调
     * @param resultMessage
     */
    void deleteSucces(String resultMessage);
}
