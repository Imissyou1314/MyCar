package com.miss.imissyou.mycar.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    private EditText brandEdit;
    @FindViewById(id = R.id.activity_add_newCar_brand_input)
    private EditText modelsEdit;
    @FindViewById(id = R.id.activity_add_newCar_plateNumber_input)
    private EditText plateNumberEdit;
    @FindViewById(id = R.id.activity_add_newCar_vin_input)
    private EditText vinEdit;
    @FindViewById(id = R.id.activity_add_newCar_engineNumber_input)
    private EditText engineNumberEdit;
    @FindViewById(id = R.id.activity_add_newCar_rank_input)
    private EditText rankEdit;

    @FindViewById(id = R.id.activity_add_newCar_enginProperty_input)
    private EditText enginPropertyEdit;
    @FindViewById(id = R.id.activity_add_newCar_transmission_input)
    private EditText transmissionEdit;
    @FindViewById(id = R.id.activity_add_newCar_carLight_input)
    private EditText carLightEdit;

    @FindViewById(id = R.id.activity_add_newCar_carState_input)
    private EditText carStateEdit;
    @FindViewById(id = R.id.activity_add_newCar_carAlarm_input)
    private EditText carAlarmEdit;

    @FindViewById(id = R.id.activity_add_newCar_alarmMessage_button)
    private ToggleButton alarmMessage;
    @FindViewById(id = R.id.activity_add_newCar_propertyMessage_button)
    private ToggleButton propertyMessage;
    @FindViewById(id = R.id.activity_add_newCar_stateMessage_button)
    private ToggleButton stateMessage;


    private static final String resultJson = "missKey";
    MissDialog.Builder builder;

    private AddNewCarInputPresenter mAddAddNewCarInputPresenter;
    private CarInfoBean resultBean;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_add_newcar_input);
    }

    /**
     * 于服务器的交互
     */
    @Override public void addListeners() {
        titleView.setTitleText("确认车辆信息");
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                LogUtils.d("提交数据");
                resultBean.setAlarmMessage(alarmMessage.getToggleOn());
                resultBean.setPropertyMessage(propertyMessage.getToggleOn());
                resultBean.setStateMessage(stateMessage.getToggleOn());
                if(resultBean == null ) {
                    return;
                } else if(Constant.userBean == null) {
                    toDoLogin();
                }

                resultBean.setUserId(Long.parseLong(Constant.userBean.getId()));
                mAddAddNewCarInputPresenter.sentCarInfoToService(resultBean);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SPUtils.init(this);
        String jsonBean = SPUtils.getSp_cache().getString(resultJson,"");
        if (jsonBean.equals("")) {
            resultBean = GsonUtils.Instance().fromJson(jsonBean, CarInfoBean.class);
        }

    }

    /**
     * 登陆页面
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

    @Override
    protected void initData() {
        mAddAddNewCarInputPresenter = new AddNewCarInputPresenterImpl(this);
        builder =new  MissDialog.Builder(this);
        if (resultBean == null) {
            String ping = this.getIntent().getStringExtra("result");
            //如果扫描的路径为空就不去请求
            if (ping == null) {
                String url = Constant.SERVER_URL + ping;
                LogUtils.d("请求路径:" + url);
                mAddAddNewCarInputPresenter.loadCar(url);
            }
        } else {
            showResultSuccess(resultBean);
        }

    }

    @Override public void showResultError(int errorNo, String errorMag) {
        builder.setTitle("信息")
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

            builder.create().show();
            return;
        }

        this.resultBean = resultBean;
        brandEdit.setText(resultBean.getBrand());
        modelsEdit.setText(resultBean.getModles());
        plateNumberEdit.setText(resultBean.getPlateNumber());
        vinEdit.setText(resultBean.getVin());
        engineNumberEdit.setText(resultBean.getEngineNumber() + "");
        enginPropertyEdit.setText(resultBean.getEnginProperty());
        rankEdit.setText(resultBean.getRank());
        transmissionEdit.setText(resultBean.getTransmission());
        carLightEdit.setText(resultBean.getCarLight());

        carStateEdit.setText(resultBean.getCarState());
        carAlarmEdit.setText(resultBean.getCarAlarm());

        LogUtils.d("回调的信息" + GsonUtils.Instance().toJson(resultBean));
    }
}
