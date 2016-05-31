package com.miss.imissyou.mycar.model.impl;


import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.AddNewCarInputModel;
import com.miss.imissyou.mycar.presenter.AddNewCarInputPresenter;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
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

    @Override public void sentToService(CarInfoBean carInfoBean) {

        HttpParams params = new HttpParams();
//        params.put("id", carInfoBean.getId());
//        params.put("userId",carInfoBean.getUserId()+"");
//        params.put("brand", carInfoBean.getBrand());
//        params.put("models",carInfoBean.getModles());
//        params.put("plateNumber",carInfoBean.getPlateNumber());
//        params.put("engineNumber", carInfoBean.getEngineNumber());
//        params.put("enginProperty", carInfoBean.isEnginProperty());
//        params.put("rank", carInfoBean.getRank());
//        params.put("vin", carInfoBean.getVin());
//        params.put("mark", carInfoBean.getMark());
//        params.put("mileage",carInfoBean.getMilleage() + "");
//        params.put("oilBox", carInfoBean.getOilBox() + "");
//        params.put("oil",carInfoBean.getOil() + "");
//        params.put("temperature", carInfoBean.getTemperature() + "");
//        params.put("transmission", carInfoBean.getTransmission());
//        params.put("carLight", carInfoBean.getCarLight());
//        params.put("carState", carInfoBean.getCarState());
//        params.put("carAlarm", carInfoBean.getCarAlarm());

//        params.put("lat", carInfoBean.getLat() + "");
//        params.put("lon", carInfoBean.getLon() + "");
        params.putJsonParams(GsonUtils.Instance().toJson(carInfoBean));

        LogUtils.w("newCar:" + GsonUtils.Instance().toJson(carInfoBean));
        String url = Constant.SERVER_URL + "car/saveCar";
        LogUtils.w("请求路径：" + url);

        HttpCallback callback =  new HttpCallback() {
            @Override public void onFailure(int errorNo, String strMsg) {
                addNewCarInputPresenter.onFailure(errorNo,strMsg);
            }
            @Override public void onSuccess(String t) {
                addNewCarInputPresenter.onAddCarSuccess(t);
            }
        };

        new RxVolley.Builder()
                .contentType(RxVolley.ContentType.JSON)
                .httpMethod(RxVolley.Method.POST)
                .encoding("utf-8")
                .url(url)
                .params(params)
                .callback(callback)
                .doTask();
    }

    @Override public void saveToSPU(BaseBean baseBean, String key) {
        CarInfoBean carInfoBean = (CarInfoBean) baseBean;
        SPUtils.putObject(key,carInfoBean);
    }

    @Override public void loadCar(String url) {

        LogUtils.w("请求路径：" + url);
//        String missurl = Constant.SERVER_URL + "car/vin=123456";
//        LogUtils.d("请求路径：" + missurl);


        HttpCallback httpCallback = new HttpCallback() {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                addNewCarInputPresenter.onFailure(errorNo, strMsg);
            }

            @Override
            public void onSuccess(String t) {

                            LogUtils.w("返回结果："+t);
                addNewCarInputPresenter.onSuccess(t);
            }
        };

        new RxVolley.Builder()
                .shouldCache(false)
                .url(url)
                .callback(httpCallback)
                .timeout(6000)
                .httpMethod(RxVolley.Method.GET)
                .doTask();
    }
}
