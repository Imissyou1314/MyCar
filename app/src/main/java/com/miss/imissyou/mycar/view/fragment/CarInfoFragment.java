package com.miss.imissyou.mycar.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.impl.CarInfoPresenterImpl;
import com.miss.imissyou.mycar.ui.circleProgress.CircleProgress;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.DialogUtils;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.view.CarInfoView;
import com.miss.imissyou.mycar.presenter.CarInfoPresenter;


/**
 * 单架车的具体信息
 * Created by Imissyou on 2016/5/3.
 */
public class CarInfoFragment extends BaseFragment implements CarInfoView {

    private CircleProgress progress;

    private CarInfoPresenter mCarInfoPresenter;

    /**
     * 车辆描述
     */
    private ImageView carImage;         //车辆图标图片
    private TextView carBrand;                    //车辆品牌
    private TextView carVin;                       //车架号
    private TextView carRand;                      //车身等级
    private TextView carEngineNumber;             //车发动机号

    /**
     * 车辆信息
     */
    private TextView carOil;                    //车的油量
    private ProgressBar carOilProgress;        //车辆的油量进度条
    private TextView carOilBox;                 //车辆的油容量
    private TextView carMileage;                //车辆的里程数
    private TextView carTemperature;            //车辆的温度

    /**
     * 车辆状态
     */
    private TextView carLight;                 //车灯性能
    private TextView carState;                //车状态
    private TextView carAlarm;                //车警报
    private TextView carEnginProperty;       //发动机性能
    private TextView carTransmission;        //变速器性能
    private TextView carSRS;                  //安全气囊

    private Long mCarId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(R.layout.fragment_car_info, inflater, container, savedInstanceState);
    }

    @Override
    protected void initView(View view) {

        progress = (CircleProgress) view.findViewById(R.id.load_carInfo_progress);

        /**车辆描述*/
        carImage = (ImageView) view.findViewById(R.id.car_info_carBrand_image);
        carBrand = (TextView) view.findViewById(R.id.carInfo_carBrand_input);
        carEngineNumber = (TextView) view.findViewById(R.id.carInfo_carEngineNumber_input);
        carRand = (TextView) view.findViewById(R.id.carInfo_carRank_input);
        carVin = (TextView) view.findViewById(R.id.carInfo_carVin_input);

        /**车辆信息*/
        carOil = (TextView) view.findViewById(R.id.carInfo_carOil_input);
        carOilProgress = (ProgressBar) view.findViewById(R.id.carInfo_carOil_progress);
        carTemperature = (TextView) view.findViewById(R.id.carInfo_carTemperature_input);
        carMileage = (TextView) view.findViewById(R.id.carInfo_carMileage_input);
        carOilBox = (TextView) view.findViewById(R.id.carInfo_carOilBox_input);

        /**车辆状态*/
        carLight = (TextView) view.findViewById(R.id.carInfo_carLight_input);
        carAlarm = (TextView) view.findViewById(R.id.carInfo_carAlarm_input);
        carState = (TextView) view.findViewById(R.id.carInfo_carState_input);
        carSRS = (TextView) view.findViewById(R.id.carInfo_carSRS_input);
        carTransmission = (TextView) view.findViewById(R.id.carInfo_carTransmission_input);
        carEnginProperty = (TextView) view.findViewById(R.id.carInfo_carEnginProperty_input);
    }


    @Override
    protected void initData() {
        LogUtils.w(getArguments() + "");
        Long userId = getArguments().getLong(Constant.USER_ID);
        Long carId = getArguments().getLong(Constant.CAR_ID);

        this.mCarId = carId;
        mCarInfoPresenter.loadCarInfo(userId, carId);
    }

    @Override
    protected void addViewsListener() {
        mCarInfoPresenter = new CarInfoPresenterImpl(this);
    }

    @Override
    public void showResultError(int errorNo, String errorMag) {
        String title = "提示";
        if (errorNo == 0) {
            title = "警告";
        } else if (errorNo == Constant.SUCCESS_NO) {
            title = Constant.SUCCESS_TITLE;
        }
        new DialogUtils(getActivity()).errorMessage(errorMag, title);

    }

    @Override
    public void showResultSuccess(ResultBean resultBean) {
        if (resultBean.isServiceResult()) {
            showResultError(Constant.SUCCESS_NO, resultBean.getResultInfo());
        } else {
            showPage((CarInfoBean) GsonUtils.getParam(resultBean, "car", CarInfoBean.class));
        }
    }

    @Override
    public void showProgress() {
        progress.startAnim();
    }

    @Override
    public void hideProgress() {
        progress.stopAnim();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showResultSuccess(CarInfoBean resultBean) {
        LogUtils.w(resultBean.getBrand());
        showPage(resultBean);
    }

    private void showPage(CarInfoBean carInfo) {
        carBrand.setText(carInfo.getBrand());
        carRand.setText(carInfo.getRank());
        carVin.setText(carInfo.getVin());
        carEngineNumber.setText(carInfo.getEngineNumber());


        carOilBox.setText(carInfo.getOilBox() + "");
        carTemperature.setText(carInfo.getTemperature() + "");
        carMileage.setText(carInfo.getMileage() + "");

        setState(carLight, carInfo.isCarLight());
        setState(carAlarm, carInfo.isCarAlarm());
        setState(carEnginProperty, carInfo.isEnginProperty());
        setState(carTransmission, carInfo.isTransmission());

        setOFFState(carState, carInfo.isCarState());
        setOFFState(carSRS, carInfo.isSRS());

        //加载油量进度条
        setProgress(carOilProgress, carInfo.getOil(), carInfo.getOilBox());
        //加载图片
        loadImage(carImage, carInfo.getMark());
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
            text.setTextColor(getActivity().getResources().getColor(R.color.color_blue_background));
        } else {
            text.setText("损坏");
            text.setTextColor(getActivity().getResources().getColor(R.color.color_red_background));
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
            text.setTextColor(getActivity().getResources().getColor(R.color.color_blue_background));
        } else {
            text.setText("关闭");
            text.setTextColor(getActivity().getResources().getColor(R.color.color_red_background));
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
        int nowOil = ((int) oil * 100)  / ((int) oilBox);

        carOilProgress.setProgress(nowOil);
        carOil.setText(nowOil + "%");
        LogUtils.w("油量为:" + nowOil);

        if (carOilProgress.getProgress() > 20 && carOilProgress.getProgress() < 50)  {
            carOil.setTextColor(getActivity().getResources().getColor(R.color.color_progress_yello));
            carOilProgress.setBackgroundResource(R.color.color_progress_yello);
        } else if (carOilProgress.getProgress() >= 50) {
            carOil.setTextColor(getActivity().getResources().getColor(R.color.color_progress_greed));
            carOilProgress.setBackgroundResource(R.color.color_progress_greed);
        } else {
            carOilProgress.setBackgroundResource(R.color.color_progress_red);

        }
    }

    /**
     * 加载图片
     * @param carImage
     * @param imageUrl
     */
    private void loadImage(ImageView carImage, String imageUrl) {
        LogUtils.w("加载图片地址:" + imageUrl);
//        Glide.with(this).load(Constant.SERVER_URL + imageUrl).asBitmap().into(carImage);
    }

}
