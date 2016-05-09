package com.miss.imissyou.mycar.model.impl;


import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.model.AddNewCarInputModel;
import com.miss.imissyou.mycar.presenter.AddNewCarInputPresenter;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.SPUtils;

/**
 * 扫描车辆信息
 * Created by Imissyou on 2016/4/18.
 */
public class AddNewCarInputModelImpl implements AddNewCarInputModel {

    private AddNewCarInputPresenter addNewCarInputPresenter;

    public AddNewCarInputModelImpl( AddNewCarInputPresenter addNewCarInputPresenter) {
        this.addNewCarInputPresenter = addNewCarInputPresenter;
    }

    @Override public void sentToService(CarInfoBean baseBean) {
        CarInfoBean carInfoBean = (CarInfoBean)baseBean;
        HttpParams params = new HttpParams();
        params.put("id", carInfoBean.getId());
        params.put("userId",carInfoBean.getUserId()+"");
        params.put("brand", carInfoBean.getBrand());
        params.put("models",carInfoBean.getModles());
        params.put("plateNumber",carInfoBean.getPlateNumber());
        params.put("engineNumber", carInfoBean.getEngineNumber());
        params.put("enginProperty", carInfoBean.getEnginProperty());
        params.put("rank", carInfoBean.getRank());
        params.put("vin", carInfoBean.getVin());
        params.put("mark", carInfoBean.getMark());
        params.put("mileage",carInfoBean.getMilleage() + "");
        params.put("oilBox", carInfoBean.getOilBox() + "");
        params.put("oil",carInfoBean.getOil() + "");
        params.put("temperature", carInfoBean.getTemperature() + "");
        params.put("transmission", carInfoBean.getTransmission());
        params.put("carLight", carInfoBean.getCarLight());
        params.put("carState", carInfoBean.getCarState());
        params.put("carAlarm", carInfoBean.getCarAlarm());

        params.put("lat", carInfoBean.getLat() + "");
        params.put("lon", carInfoBean.getLon() + "");


        String url = Constant.SERVER_URL + "addNewCar";
        LogUtils.d("请求路径：" + url);
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

    @Override public void loadCar(String url) {
        LogUtils.d("请求路径：" + url);
        RxVolley.get(url, new HttpCallback() {
            @Override public void onSuccess(String t) {
                addNewCarInputPresenter.onSuccess(t);
            }

            @Override public void onFailure(int errorNo, String strMsg) {
                addNewCarInputPresenter.onFailure(errorNo, strMsg);
            }
        });
    }
}
