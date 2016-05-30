package com.miss.imissyou.mycar.presenter;

import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.view.MessageView;

/**
 * 消息的Presenter
 * Created by Imissyou on 2016/5/2.
 */
public interface MessagePresenter extends MainPresenter<MessageView>{

    /**
     * 删除信息
     * @param id
     */
    void deteleMessage(int id);

    /**
     * 删除成功执行回调
     * @param resultBean
     */
    void deteleSuccess(ResultBean resultBean);

    /**
     * 该变信息的状态
     * @param id
     */
    void changeStateToService(Long id);

    /**
     * 获取用户所有未读的信息
     */
    void getUserUnReadMessage();

    /**
     * 获取用户的所有信息
     */
    void getUserAllMessage();

}
