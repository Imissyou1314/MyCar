package com.miss.imissyou.mycar.presenter;

import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.view.MessageView;

/**
 * 消息的Presenter
 * Created by Imissyou on 2016/5/2.
 */
public interface MessagePresenter extends MainPresenter<MessageView>{

    /**
     * ===>Model
     * 删除信息
     * @param id  信息ID
     */
    void deteleMessage(int id);

    /**
     * View <=== 删除成
     * 删除成功执行回调
     * @param resultBean 信息响应实体
     */
    void deteleSuccess(ResultBean resultBean);

    /**
     * ===>View
     * 该变信息的状态
     * @param id  信息ID
     */
    void changeStateToService(Long id);

    /**
     * ===> Model
     * 获取用户所有未读的信息
     */
    void getUserUnReadMessage();

    /**
     * ===> Model
     * 获取用户的所有信息
     */
    void getUserAllMessage();

}
