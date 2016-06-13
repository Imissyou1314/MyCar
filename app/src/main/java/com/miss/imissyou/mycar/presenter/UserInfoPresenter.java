package com.miss.imissyou.mycar.presenter;

import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.bean.UserBean;
import com.miss.imissyou.mycar.view.UserInfoView;

/**
 * 用户的Presenter
 * Created by Imissyou on 2016/4/26.
 */
public interface UserInfoPresenter extends MainPresenter<UserInfoView>{


    void onUpdateSuccess(ResultBean resultBean);

    /**
     * 检查用户车辆安全码是否正确
     * @param safePasswordInput  安全码
     */
    void checkSafePassword(CharSequence safePasswordInput);

    /**
     * 检查用户安全码正确回调
     * @param resultBean  结果集
     */
    void checkSafePasswordSuccess(ResultBean resultBean);

    /**
     * 更新用户头像
     * @param Imagepath  图片地址
     */
    void updataUserImage(String Imagepath);
}
