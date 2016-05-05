package com.miss.imissyou.mycar.presenter.impl;

import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.AddNewCarInputModel;
import com.miss.imissyou.mycar.model.impl.AddNewCarInputModelImpl;
import com.miss.imissyou.mycar.presenter.AddNewCarInputPresenter;

import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.view.AddNewCarInputView;
import com.miss.imissyou.mycar.view.activity.AddNewCarInputActivity;

/**
 * Created by Imissyou on 2016/4/18.
 */
public class AddNewCarInputPresenterImpl implements AddNewCarInputPresenter {

    private AddNewCarInputActivity  addNewCarInputActivity;
    private AddNewCarInputModel addNewCarInputModel;

    public AddNewCarInputPresenterImpl(AddNewCarInputView addNewCarInputView) {
        this.addNewCarInputActivity = (AddNewCarInputActivity) addNewCarInputView;
        addNewCarInputModel = new AddNewCarInputModelImpl(this);
    }


    @Override public void saveCarInfo(String jsonString) {

    }

    @Override public void sentCarInfoToService(String jsonString) {
        CarInfoBean carInfoBean = GsonUtils.Instance()
                .fromJson(jsonString, CarInfoBean.class);
        if (carInfoBean == null) {
            addNewCarInputActivity.showResultError(0, "Json解析出错");
            return;
        } else {
            addNewCarInputModel.sentToService(carInfoBean);
        }
    }


    @Override public void onDestroy() {
        addNewCarInputActivity.hideProgress();
        addNewCarInputActivity = null;
    }

    @Override public ResultBean initCarDate() {
        String  result = addNewCarInputActivity.getIntent().getExtras().getString("result","");
        ResultBean resultBean = new ResultBean();
        resultBean.setResultInfo(result);
        return resultBean;
    }

    @Override public void onFailure(int errorNo, String strMsg) {
        //TODO
        addNewCarInputActivity.showResultError(errorNo, strMsg);
        LogUtils.d("连接服务器出错>>>>" + errorNo + ">>>错误信息:::" +strMsg);
    }

    @Override public void onSuccess(String t) {


//        ResultBean resultBean = GsonUtils.Instance()
//                .fromJson(t, ResultBean.class);
//        if (resultBean != null)
//            addNewCarInputActivity.showResultSuccess(resultBean);
        LogUtils.d("连接服务器返回信息>>>>"  + t);
    }
}
