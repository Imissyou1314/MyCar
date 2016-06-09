package com.miss.imissyou.mycar.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.MainActivity;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.AddNewCarInputPresenter;
import com.miss.imissyou.mycar.presenter.impl.AddNewCarInputPresenterImpl;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.DialogUtils;
import com.miss.imissyou.mycar.util.FindViewById;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.StringUtil;
import com.miss.imissyou.mycar.view.AddNewCarInputView;

/**
 * Created by Imissyou on 2016/6/4.
 */
public class InputBrandPageActivity extends BaseActivity implements View.OnClickListener ,AddNewCarInputView {

    @FindViewById( id = R.id.input_add_brand_input)
    private EditText brandInput;        //车牌号输入
    @FindViewById(id = R.id.input_add_brand_sumbit)
    private Button sumbit;              //提交按钮

    private AddNewCarInputPresenter mAddNewCarInputPresenter;
    private CarInfoBean carInfoBean;            //获取上一页面传进来的对象

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_input_barnd_page);
    }

    @Override
    protected void initData() {
        mAddNewCarInputPresenter = new AddNewCarInputPresenterImpl(this);
        Bundle bundle = getIntent().getBundleExtra("carInfoBean");
        carInfoBean = (CarInfoBean) bundle.getSerializable("carInfo");
        LogUtils.d("车辆信息:" + GsonUtils.Instance().toJson(carInfoBean));
    }

    @Override
    public void addListeners() {
        sumbit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String carBrand = brandInput.getText().toString();
        LogUtils.d("提交车辆"+ carBrand);
        if (null == carBrand && "".equals(carBrand)) {
            LogUtils.d("车牌号为空");
            showResultError(0,"请输入车牌号");
        } else {
            carInfoBean.setPlateNumber(carBrand);
            // TODO: 2016/6/5 打印车辆信息
            LogUtils.w("提交车辆的信息:" + GsonUtils.Instance().toJson(carInfoBean));
            mAddNewCarInputPresenter.sentCarInfoToService(carInfoBean);
        }
    }

    @Override
    public void showResultSuccess(CarInfoBean resultBean) {

    }

    @Override
    public void showResultError(int errorNo, String errorMag) {
        String title ="提示";
        if (errorNo == Constant.WARE_ERROR) {
            title = Constant.WARE_ERROR_COSTANT;
        } else if (errorNo == Constant.NETWORK_STATE){
            title = Constant.NOTNETWORK;
        }
        new DialogUtils(this).errorMessage(errorMag,title);
    }

    @Override
    public void showResultSuccess(ResultBean resultBean) {
        LogUtils.d("添加车辆成功" + GsonUtils.Instance().toJson(resultBean));
        if (resultBean.isServiceResult()) {
            // TODO: 2016/6/5 提示添加车辆成功
            //new DialogUtils(this).showSucces(resultBean.getResultInfo(),
               //     Constant.SUCCESS_TITLE, MainActivity.class);\
            //Intent intent = new Intent();
            //intent.setClass(this,MainActivity.class);
           // startActivity(intent);
            this.finish();
        } else {
            showResultError(0, resultBean.getResultInfo());
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
