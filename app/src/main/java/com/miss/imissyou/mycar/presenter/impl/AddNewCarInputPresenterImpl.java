package com.miss.imissyou.mycar.presenter.impl;

import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.AddNewCarInputModel;
import com.miss.imissyou.mycar.model.impl.AddNewCarInputModelImpl;
import com.miss.imissyou.mycar.presenter.AddNewCarInputPresenter;

import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.view.AddNewCarInputView;

/**
 * 车辆输入
 * Created by Imissyou on 2016/4/18.
 */
public class AddNewCarInputPresenterImpl implements AddNewCarInputPresenter {

    private AddNewCarInputView addNewCarInputActivity;
    private AddNewCarInputModel addNewCarInputModel;

    public AddNewCarInputPresenterImpl(AddNewCarInputView addNewCarInputView) {
        this.addNewCarInputActivity = addNewCarInputView;
        addNewCarInputModel = new AddNewCarInputModelImpl(this);
    }


    @Override
    public void sentCarInfoToService(CarInfoBean resultBean) {
        if (resultBean == null) {
            return;
        } else {
            addNewCarInputModel.sentToService(resultBean);
        }
    }


    @Override
    public void onDestroy() {
        addNewCarInputActivity.hideProgress();
        addNewCarInputActivity = null;
    }


    @Override
    public void onFailure(int errorNo, String strMsg) {
        if (errorNo == Constant.NETWORK_STATE)
            strMsg = Constant.NOTNETWORK;
        addNewCarInputActivity.showResultError(errorNo, strMsg);
        LogUtils.d("连接服务器出错>>>>" + errorNo + ">>>错误信息:::" + strMsg);
    }

    @Override
    public void onSuccess(String t) {
        ResultBean resultBean = GsonUtils.Instance().fromJson(t, ResultBean.class);
        CarInfoBean carInfoBean = GsonUtils.getParam(resultBean, "car", CarInfoBean.class);
        if (carInfoBean == null) {
            addNewCarInputActivity.showResultError(0, resultBean.getResultInfo());
        } else {
            addNewCarInputActivity.showResultSuccess(carInfoBean);
            LogUtils.w("连接服务器返回信息>>>>" + t);
        }
    }

    @Override
    public void onAddCarSuccess(String t) {
        ResultBean resultBean = GsonUtils.getResultBean(t);
        if (null != resultBean) {
            addNewCarInputActivity.showResultSuccess(resultBean);
        } else {
            addNewCarInputActivity.showResultError(0,"获取数据异常");
        }
    }

    @Override
    public void loadCar(String url) {
        if (url != null) {
            LogUtils.d("扫描信息:" + url);
            addNewCarInputModel.loadCar(url);

        }
    }
}
