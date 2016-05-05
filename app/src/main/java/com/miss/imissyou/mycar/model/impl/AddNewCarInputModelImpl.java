package com.miss.imissyou.mycar.model.impl;


import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.model.AddNewCarInputModel;
import com.miss.imissyou.mycar.presenter.AddNewCarInputPresenter;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.SPUtils;

/**
 * Created by Imissyou on 2016/4/18.
 */
public class AddNewCarInputModelImpl implements AddNewCarInputModel {

    private AddNewCarInputPresenter addNewCarInputPresenter;

    public AddNewCarInputModelImpl( AddNewCarInputPresenter addNewCarInputPresenter) {
        this.addNewCarInputPresenter = addNewCarInputPresenter;
    }

    @Override public void sentToService(BaseBean baseBean) {
        CarInfoBean carInfoBean = (CarInfoBean)baseBean;
        HttpParams params = new HttpParams();

        String url = Constant.SERVER_URL + "addNewCar";
        RxVolley.post(url, params, new HttpCallback() {

            @Override public void onFailure(int errorNo, String strMsg) {
                addNewCarInputPresenter.onFailure(errorNo,strMsg);
            }

            @Override public void onSuccess(String t) {
                addNewCarInputPresenter.onSuccess(t);
            }
        });
    }

    @Override public void saveToSPU(BaseBean baseBean, String key) {
        CarInfoBean carInfoBean = (CarInfoBean) baseBean;
        SPUtils.putObject(key,carInfoBean);
    }
}
