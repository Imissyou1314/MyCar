package com.miss.imissyou.mycar.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.AddNewCarInputPresenter;
import com.miss.imissyou.mycar.presenter.impl.AddNewCarInputPresenterImpl;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.DialogUtils;
import com.miss.imissyou.mycar.util.FindViewById;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.view.AddNewCarInputView;
import com.rey.material.widget.Spinner;

/**
 * 车牌号输入页面
 * Created by Imissyou on 2016/6/4.
 */
public class InputBrandPageActivity extends BaseActivity implements View.OnClickListener ,AddNewCarInputView{

    @FindViewById( id = R.id.input_add_brand_input)
    private EditText brandInput;        //车牌号输入
    @FindViewById(id = R.id.input_add_brand_sumbit)
    private Button sumbit;              //提交按钮

    @FindViewById(id = R.id.input_barnd_page_SelectEn)
    private Spinner selectEn;       //选择英文
    @FindViewById(id = R.id.input_brand_page_selectZH)
    private Spinner selectCH;      //选择中文

    private AddNewCarInputPresenter mAddNewCarInputPresenter;
    private CarInfoBean carInfoBean;            //获取上一页面传进来的对象

    String[] cityName = {"贵","豫","鲁","川","苏","青","新","闽","浙","鄂","藏","粤","云","京", "陕",
            "甘","冀","吉","宁","湘","皖","蒙","沪","晋","琼","辽","渝","黑","津","桂","赣",};
    String[] cityEn = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O",
            "P","Q","R","S","T","U","V","W","X","Y","Z",};
    private String brandCityName;  //城市名
    private String brandEnName;    //英文字

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

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this, R.layout.row_spn, cityName);
        cityAdapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        selectCH.setAdapter(cityAdapter);
        ArrayAdapter<String> cityEnAdapter = new ArrayAdapter<String>(this, R.layout.row_spn, cityEn);
        cityEnAdapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        selectEn.setAdapter(cityEnAdapter);
    }

    @Override
    public void addListeners() {

        sumbit.setOnClickListener(this);
        selectCH.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                brandCityName = cityName[position];
            }
        });
        selectEn.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                brandEnName = cityEn[position];
            }
        });
    }

    @Override
    public void onClick(View v) {

        String carBrandInput = brandInput.getText().toString();
        String carBrand = brandCityName + brandEnName+ "·" + carBrandInput;
        LogUtils.d("提交车辆"+ carBrand);
        if (null == carBrand && "".equals(carBrand)) {
            LogUtils.d("车牌号为空");
            showResultError(0,"请输入车牌号");
        } else {
            LogUtils.d("提交的车牌号" + carBrand);
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
