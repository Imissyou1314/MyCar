package com.miss.imissyou.mycar.view;


import com.miss.imissyou.mycar.bean.ResultBean;

/**
 * Created by Imissyou on 2016/4/26.
 */
public interface UserInfoView extends MainView {

    /**
     * 更新成功后的回调信息
     * @param resultMessage
     */
    void onUpdateSuccess(String resultMessage);

    /**
     * 验证安全码通过
     * @param resultBean  结果集
     */
    void showSafePasswordSucess(ResultBean resultBean);
}
