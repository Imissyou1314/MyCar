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
    void deteleMessage(String id);

    /**
     * 删除成功执行回调
     * @param resultBean
     */
    void deteleSuccess(ResultBean resultBean);

}
