package com.miss.imissyou.mycar.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.AddNewCarInputPresenter;
import com.miss.imissyou.mycar.presenter.impl.AddNewCarInputPresenterImpl;
import com.miss.imissyou.mycar.ui.MissDialog;
import com.miss.imissyou.mycar.ui.RoundImageView;
import com.miss.imissyou.mycar.ui.TitleFragment;
import com.miss.imissyou.mycar.ui.ToggleButton;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.FindViewById;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.SPUtils;
import com.miss.imissyou.mycar.view.AddNewCarInputView;

/**
 * 确认添加新车
 * Created by Imissyou on 2016/4/8.
 */
public class AddNewCarInputActivity extends BaseActivity implements AddNewCarInputView {

    @FindViewById( id = R.id.addCar_inputCarInfo_submit)
    private Button submitBtn;

    @FindViewById(id = R.id.addCar_inputCarInfo_UserImage)
    private RoundImageView userHeadImage;
    @FindViewById(id = R.id.addnewCarInput_title)
    private TitleFragment titleView;

    @FindViewById(id = R.id.activity_add_newCar_brand_input)
    private EditText brandEdit;             //品牌
    @FindViewById(id = R.id.activity_add_newCar_models_input)
    private EditText modelsEdit;            //品牌型号
    @FindViewById(id = R.id.activity_add_newCar_vin_input)
    private EditText vinEdit;               //车架号
    @FindViewById(id = R.id.activity_add_newCar_engineNumber_input)
    private EditText engineNumberEdit;      //发动机号
    @FindViewById(id = R.id.activity_add_newCar_rank_input)
    private EditText rankEdit;              //车身等级
    @FindViewById(id = R.id.activity_add_newCar_plateNumber_input)
    private EditText plateNumber;           //车牌

    @FindViewById(id = R.id.activity_add_newCar_enginProperty_input)
    private EditText enginPropertyEdit;         //发动机性能
    @FindViewById(id = R.id.activity_add_newCar_transmission_input)
    private EditText transmissionEdit;          //变速器性能
    @FindViewById(id = R.id.activity_add_newCar_carLight_input)
    private EditText carLightEdit;              //车灯

    @FindViewById(id = R.id.activity_add_newCar_carState_input)
    private EditText carStateEdit;           //车状态
    @FindViewById(id = R.id.activity_add_newCar_carAlarm_input)
    private EditText carAlarmEdit;          //车警报状态
    @FindViewById(id = R.id.activity_add_newCar_carLight_input)
    private EditText carLightEidt;          //车灯状态

    @FindViewById(id = R.id.activity_add_newCar_alarmMessage_button)
    private ToggleButton alarmMessage;      //获取警报信息
    @FindViewById(id = R.id.activity_add_newCar_propertyMessage_button)
    private ToggleButton propertyMessage;
    @FindViewById(id = R.id.activity_add_newCar_stateMessage_button)
    private ToggleButton stateMessage;

    @FindViewById(id = R.id.activity_add_newCar_milleage_input)
    private EditText mileageInput;
    @FindViewById(id = R.id.activity_add_newCar_oilBox_input)
    private EditText oilMaxInputInput;
    @FindViewById(id = R.id.activity_add_newCar_oil_input)
    private EditText oilInput;
    @FindViewById(id = R.id.activity_add_newCar_temperature_input)
    private EditText temperatureInput;


    private static final String resultJson = "missKey";
    MissDialog.Builder builder;

    private AddNewCarInputPresenter mAddAddNewCarInputPresenter;
    private CarInfoBean resultBean = new CarInfoBean();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_add_newcar_input);
    }

    /**
     * 于服务器的交互
     */
    @Override public void addListeners() {
        titleView.setTitleText("确认车辆信息");

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                LogUtils.w("提交数据");
                doInput();
                resultBean.setAlarmMessage(alarmMessage.getToggleOn());
                resultBean.setPropertyMessage(propertyMessage.getToggleOn());
                resultBean.setStateMessage(stateMessage.getToggleOn());

                if(resultBean == null ) {
                    return;
                } else if(Constant.userBean == null) {
                    toDoLogin();
                }
                if (null != Constant.userBean && null != Constant.userBean.getId()) {
                    String userId = Constant.userBean.getId() +"";
//                    userId = userId.substring(0, userId.indexOf("."));
                    LogUtils.w(userId);
                    resultBean.setUserId(Long.parseLong(userId));
                    mAddAddNewCarInputPresenter.sentCarInfoToService(resultBean);
                }
            }
        });
    }

    /**
     * 获取输入
     */
    private void doInput() {
        if (null == resultBean) {
            resultBean = new CarInfoBean();
        }
        resultBean.setPlateNumber(plateNumber.getText().toString() + "");
        resultBean.setBrand(brandEdit.getText().toString());
        resultBean.setModles(modelsEdit.getText().toString());
        resultBean.setVin(vinEdit.getText().toString());
        resultBean.setEngineNumber(engineNumberEdit.getText().toString());
        resultBean.setRank(rankEdit.getText().toString());
    }

    @Override protected void onResume() {
        super.onResume();
        SPUtils.init(this);
        String jsonBean = SPUtils.getSp_cache().getString(resultJson,"");
        if (jsonBean.equals("")) {
            resultBean = GsonUtils.Instance().fromJson(jsonBean, CarInfoBean.class);
        }
    }

    /**
     * 跳转登陆页面
     */
    private void toDoLogin() {
        LogUtils.d("用户要登陆");
        String resultStr = GsonUtils.Instance().toJson(resultBean);
        SPUtils.init(this);
        SPUtils.putCacheData(this,resultJson, resultStr);

        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override public void showProgress() {

    }

    @Override public void hideProgress() {

    }

    @Override protected void onDestroy() {
        mAddAddNewCarInputPresenter.onDestroy();
        SPUtils.init(this);
        SPUtils.RemoveCacheData(resultJson);
        super.onDestroy();
    }

    @Override protected void initData() {
        mAddAddNewCarInputPresenter = new AddNewCarInputPresenterImpl(this);
        builder =new  MissDialog.Builder(this);


        String resultUrl = this.getIntent().getStringExtra("result");
        //如果扫描的路径为空就不去请求
        if (resultUrl != null) {
            String url = Constant.SERVER_URL + resultUrl;
            LogUtils.d("请求路径:" + url);
            Toast.makeText(getApplicationContext(), "url" ,Toast.LENGTH_LONG).show();
            mAddAddNewCarInputPresenter.loadCar(url);
        } else if (null != resultBean && null != resultBean.getId()) {
            showResultSuccess(resultBean);
        }
    }

    @Override public void showResultError(int errorNo, String errorMag) {
        String title = "连接错误";
        if (errorNo == 0)
            title="程序错误";

        builder.setTitle(title)
                .setMessage(errorMag)
                .setSingleButton(true)
                .setNegativeButton("确定", new DialogInterface.OnClickListener(){
                    @Override public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    @Override public void showResultSuccess(ResultBean resultBean) {
        if (resultBean.isServiceResult()){
            this.finish();
        }
    }

    @Override public void showResultSuccess(CarInfoBean resultBean) {

        if (resultBean == null) {
            builder.setTitle("连接失败")
                    .setMessage("获取服务器数据失败")
                    .setSingleButton(true)
                    .setNegativeButton("确定", new DialogInterface.OnClickListener(){
                        @Override public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            return;
        }

        this.resultBean = resultBean;

        if (null != resultBean.getMark()) {
            String url = Constant.SERVER_URL + resultBean.getMark();
            LogUtils.d("加载图片的URL:" + url);
            //// TODO: 2016/5/29
//            Glide.with(this).load(url).into(userHeadImage);
        }

        brandEdit.setText(resultBean.getBrand());
        modelsEdit.setText(resultBean.getModles());
        vinEdit.setText(resultBean.getVin());
        engineNumberEdit.setText(resultBean.getEngineNumber() + "");
        rankEdit.setText(resultBean.getRank());


        enginPropertyEdit.setText(isGood(resultBean.isEnginProperty()));
        transmissionEdit.setText(isGood(resultBean.isTransmission()));
        carLightEdit.setText(isGood(resultBean.isCarLight()));

        mileageInput.setText(resultBean.getMileage() + "");
        oilInput.setText(resultBean.getOil() +"");
        oilMaxInputInput.setText(resultBean.getOilBox() + "");
        temperatureInput.setText(resultBean.getTemperature() + "");

        carStateEdit.setText(isGood(resultBean.isCarState()));
        carAlarmEdit.setText(isGood(resultBean.isCarAlarm()));
        carLightEdit.setText(isGood(resultBean.isCarLight()));

        LogUtils.d("回调的信息" + GsonUtils.Instance().toJson(resultBean));
    }

    private String isGood(boolean check) {
        return check ? "好": "坏";
    }
}
