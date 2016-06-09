package com.miss.imissyou.mycar.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.AddNewCarInputPresenter;
import com.miss.imissyou.mycar.presenter.impl.AddNewCarInputPresenterImpl;
import com.miss.imissyou.mycar.ui.MissDialog;
import com.miss.imissyou.mycar.ui.TitleFragment;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.FindViewById;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.SPUtils;
import com.miss.imissyou.mycar.util.ToastUtil;
import com.miss.imissyou.mycar.view.AddNewCarInputView;

/**
 * 确认添加新车
 * Created by Imissyou on 2016/4/8.
 */
public class AddNewCarInputActivity extends BaseActivity implements AddNewCarInputView {

    @FindViewById(id = R.id.activity_addNew_sumbit_tonewPage)
    private Button submitBtn;

    @FindViewById(id = R.id.addCar_inputCarInfo_UserImage)
    private ImageView carBrandImage;
    @FindViewById(id = R.id.addnewCarInput_title)
    private TitleFragment titleView;


    @FindViewById(id = R.id.activity_add_newCar_brand_input)
    private TextView brandEdit;             //品牌型号
    @FindViewById(id = R.id.activity_add_newCar_vin_input)
    private TextView vinEdit;               //车架号
    @FindViewById(id = R.id.activity_add_newCar_engineNumber_input)
    private TextView engineNumberEdit;      //发动机号
    @FindViewById(id = R.id.activity_add_newCar_rank_input)
    private TextView rankEdit;              //车身等级
//    @FindViewById(id = R.id.activity_add_newCar_plateNumber_input)
//    private EditText plateNumber;           //车牌

    @FindViewById(id = R.id.activity_add_newCar_enginProperty_message)
    private TextView enginPropertyMessage;         //发动机性能
    @FindViewById(id = R.id.activity_add_newCar_transmission_message)
    private TextView transmissionMessage;          //变速器性能
    @FindViewById(id = R.id.activity_add_newCar_carLight_message)
    private TextView carLightMessage;              //车灯
    @FindViewById(id = R.id.activity_add_newCar_carState_message)
    private TextView carStateMessage;           //车状态
    @FindViewById(id = R.id.activity_add_newCar_carAlarm_message)
    private TextView carAlarmMessage;          //车警报状态
    @FindViewById(id = R.id.activity_add_newCar_SRS_message)
    private TextView srsMessage;       //安全气囊

    @FindViewById(id = R.id.activity_add_newCar_milleage_input)
    private TextView mileageInput;
    @FindViewById(id = R.id.activity_add_newCar_oilBox_input)
    private TextView oilMaxInputInput;
    @FindViewById(id = R.id.activity_add_newCar_oil_input)
    private TextView oilInput;
    @FindViewById(id = R.id.activity_add_newCar_temperature_input)
    private TextView temperatureInput;

    @FindViewById(id = R.id.activity_add_newCar_oilNumber)
    private ProgressBar oilLink;            //油量进度条


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
    @Override
    public void addListeners() {
        titleView.setTitleText("确认车辆信息");

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogUtils.w("提交数据");
//                doInput();
//                resultBean.setAlarmMessage(alarmMessage.getToggleOn());
//                resultBean.setPropertyMessage(propertyMessage.getToggleOn());
//                resultBean.setStateMessage(stateMessage.getToggleOn());

                if (resultBean == null) {
                    LogUtils.d("车辆实体为空");
                    ToastUtil.asLong("车辆信息已经过时");
                    return;
                } else if (Constant.userBean == null) {
                    toDoLogin();
                }
                if (null != Constant.userBean && null != Constant.userBean.getId()) {

                    LogUtils.w("用户Id:" + Constant.userBean.getId() + "");
                    resultBean.setUserId(Constant.userBean.getId());
                    gotoInputBrandPage(resultBean);
//                    mAddAddNewCarInputPresenter.sentCarInfoToService(resultBean);
                }
            }
        });
    }

    /**
     * 跳转到输入页面
     * @param resultBean
     */
    private void gotoInputBrandPage(CarInfoBean resultBean) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("carInfo",resultBean);
        intent.putExtra("carInfoBean",bundle);
        intent.setClass(this, InputBrandPageActivity.class);
        startActivity(intent);
        this.finish();

    }

//    /**
//     * 获取输入
//     */
//    private void doInput() {
//        if (null == resultBean) {
//            resultBean = new CarInfoBean();
//        }
//        resultBean.setPlateNumber(plateNumber.getText().toString() + "");
//        resultBean.setBrand(brandEdit.getText().toString());
//        resultBean.setModles(modelsEdit.getText().toString());
//        resultBean.setVin(vinEdit.getText().toString());
//        resultBean.setEngineNumber(engineNumberEdit.getText().toString());
//        resultBean.setRank(rankEdit.getText().toString());
//    }

    @Override
    protected void onResume() {
        super.onResume();
        SPUtils.init(this);
        String jsonBean = SPUtils.getSp_cache().getString(resultJson, "");
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
        SPUtils.putCacheData(this, resultJson, resultStr);

        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    protected void onDestroy() {
        mAddAddNewCarInputPresenter.onDestroy();
        SPUtils.init(this);
        SPUtils.RemoveCacheData(resultJson);
        super.onDestroy();
    }

    @Override
    protected void initData() {
        mAddAddNewCarInputPresenter = new AddNewCarInputPresenterImpl(this);
        builder = new MissDialog.Builder(this);


        String resultUrl = this.getIntent().getStringExtra("result");
        //如果扫描的路径为空就不去请求
        if (resultUrl != null) {
            String url = Constant.SERVER_URL + resultUrl;
            LogUtils.d("请求路径:" + url);
            Toast.makeText(getApplicationContext(), "url", Toast.LENGTH_LONG).show();
            mAddAddNewCarInputPresenter.loadCar(url);
        } else if (null != resultBean && null != resultBean.getId()) {
            showResultSuccess(resultBean);
        }
    }

    @Override
    public void showResultError(int errorNo, String errorMag) {
        String title = "连接错误";
        if (errorNo == 0)
            title = "程序错误";

        builder.setTitle(title)
                .setMessage(errorMag)
                .setSingleButton(true)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    @Override
    public void showResultSuccess(ResultBean resultBean) {
        if (resultBean.isServiceResult()) {
            this.finish();
        }
    }

    @Override
    public void showResultSuccess(CarInfoBean resultBean) {

        if (resultBean == null) {
            builder.setTitle("连接失败")
                    .setMessage("获取服务器数据失败")
                    .setSingleButton(true)
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
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
            loadImage(carBrandImage, resultBean.getMark());
//            Glide.with(this).load(url).into(userHeadImage);
        }
//        modelsEdit.setText(resultBean.getModles());

        brandEdit.setText(resultBean.getBrand());
        vinEdit.setText(resultBean.getVin());
        engineNumberEdit.setText(resultBean.getEngineNumber() + "");
        rankEdit.setText(resultBean.getRank());


//                enginPropertyEdit.setText(isGood(resultBean.isEnginProperty()));
//                transmissionEdit.setText(isGood(resultBean.isTransmission()));
//                carLightEdit.setText(isGood(resultBean.isCarLight()));
        setState(carLightMessage, resultBean.isCarLight());
        setState(enginPropertyMessage, resultBean.isEngineProperty());
        setState(transmissionMessage, resultBean.isTransmission());

        mileageInput.setText(resultBean.getMileage() + "");
        oilInput.setText(resultBean.getOil() + "");
        oilMaxInputInput.setText(resultBean.getOilBox() + "");
        temperatureInput.setText(resultBean.getTemperature() + "");

        setOFFState(carAlarmMessage,resultBean.isAlarmMessage());
        setOFFState(carStateMessage, resultBean.isCarState());
        setOFFState(srsMessage, resultBean.isSrs());

        setProgress(oilLink,resultBean.getOil(),resultBean.getOilBox());


        LogUtils.d("回调的信息" + GsonUtils.Instance().toJson(resultBean));
    }

    /**
     * 设置状态信息  良好和损坏
     *
     * @param text
     * @param state
     */
    private void setState(TextView text, boolean state) {
        if (state) {
            text.setText("良好");
            text.setTextColor(getResources().getColor(R.color.color_blue_background));
        } else {
            text.setText("损坏");
            text.setTextColor(getResources().getColor(R.color.color_red_background));
        }
    }

    /**
     * 设置状态信息关闭
     *
     * @param text
     * @param state
     */
    private void setOFFState(TextView text, boolean state) {
        if (state) {
            text.setText("启动");
            text.setTextColor(getResources().getColor(R.color.color_blue_background));
        } else {
            text.setText("关闭");
            text.setTextColor(getResources().getColor(R.color.color_red_background));
        }
    }

    /**
     * 设置油量进度条
     *
     * @param carOilProgress
     * @param oil
     * @param oilBox
     */
    private void setProgress(ProgressBar carOilProgress, double oil, double oilBox) {
        int nowOil = ((int) oil * 100) / ((int) oilBox);

        carOilProgress.setProgress(nowOil);
        oilInput.setText(nowOil + "%");
        LogUtils.w("油量为:" + nowOil);

        ClipDrawable color ;

        if (carOilProgress.getProgress() > 20 && carOilProgress.getProgress() < 50)  {
            oilInput.setTextColor(getResources().getColor(R.color.color_progress_yello));
            carOilProgress.setBackgroundResource(R.drawable.progress_yellow_background);
            carOilProgress.setProgressDrawable(getResources()
                    .getDrawable(R.drawable.progress_yellow_background));
        } else if (carOilProgress.getProgress() >= 50) {
            oilInput.setTextColor(getResources().getColor(R.color.color_progress_greed));
            //carOilProgress.setBackgroundResource(R.color.color_progress_greed);
            carOilProgress.setProgressDrawable(getResources()
                    .getDrawable(R.drawable.progress_gree_background));
        } else {
            oilInput.setTextColor(getResources().getColor(R.color.color_progress_red));
            //carOilProgress.setBackgroundResource(R.color.color_progress_red);
            carOilProgress.setProgressDrawable(getResources().getDrawable(R.drawable.progress_red_background));
        }
       // carOilProgress.setProgressDrawable(color);
    }

    /**
     * 加载图片
     *
     * @param carImage
     * @param imageUrl
     */
    private void loadImage(ImageView carImage, String imageUrl) {
        LogUtils.w("加载图片地址:" + imageUrl);
        Glide.with(this).load(Constant.SERVER_URL + imageUrl).asBitmap().into(carImage);
    }

}
