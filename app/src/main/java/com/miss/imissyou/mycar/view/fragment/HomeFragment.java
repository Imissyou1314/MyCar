package com.miss.imissyou.mycar.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.bean.UserBean;
import com.miss.imissyou.mycar.presenter.HomePresenter;
import com.miss.imissyou.mycar.presenter.impl.HomePresenterImpl;
import com.miss.imissyou.mycar.ui.RoundImageView;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.view.HomeView;
import com.tumblr.backboard.performer.Performer;


/**
 * Created by Imissyou on 2016/3/26.
 */
public class HomeFragment extends BaseFragment implements HomeView {


    private TextView carNameTV;         //车名
    private TextView carIdTv;           //车牌号
    private TextView carFDTv;           //车发动机号
    private TextView carAgeTv;          //车龄

    /**车的状态信息*/
    private TextView breakdownTv;
    private TextView alarmTv;
    private TextView stateTv;

    /**车的性能*/
    private TextView enginStat;
    private TextView shitfState;

    /**车的实时数据*/
    private TextView milNumberTv;
    private TextView waterNumberTv;
    private ProgressBar oilProgress;
    private ProgressBar enduranceProgress;
    RoundImageView carImageView;

    private HomePresenter homePresenter;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       return super.onCreateView(R.layout.fragment_home,
               inflater, container, savedInstanceState);
    }

    @Override protected void initView(View view) {

        carImageView = (RoundImageView) view.findViewById(R.id.home_car_image);
        carNameTV = (TextView) view.findViewById(R.id.fragment_home_carName);
        carIdTv = (TextView) view.findViewById(R.id.fragment_home_carID);
        carFDTv = (TextView) view.findViewById(R.id.fragment_home_carFD);
        carAgeTv = (TextView) view.findViewById(R.id.fragment_home_carAge);

        breakdownTv = (TextView) view.findViewById(R.id.home_fragment_breakdown_message);
        alarmTv = (TextView) view.findViewById(R.id.home_fragment_alarm_message);
        stateTv = (TextView) view.findViewById(R.id.home_fragment_state_message);

        enginStat = (TextView) view.findViewById(R.id.home_fragment_engine_state);
        shitfState = (TextView) view.findViewById(R.id.home_fragment_shitf_state);

        milNumberTv = (TextView) view.findViewById(R.id.home_fragment_mil_number);
        waterNumberTv = (TextView) view.findViewById(R.id.home_fragment_water_number);

        oilProgress = (ProgressBar) view.findViewById(R.id.home_fragment_oil_progress);
        oilProgress.setMax(100);
        enduranceProgress = (ProgressBar) view.findViewById(R.id.home_fragment_endurance_progress);
        enduranceProgress.setMax(100);
    }

    @Override protected void initData() {
        homePresenter = new HomePresenterImpl(this);
    }

    @Override protected void addViewsListener() {
        Spring spring = SpringSystem.create().createSpring();
        spring.addListener(new Performer(carImageView, View.TRANSLATION_X));


    }

    @Override public void showResultError(int errorNo, String errorMag) {

    }

    @Override public void showResultSuccess(ResultBean resultBean) {
        CarInfoBean myCar = GsonUtils.getParam(resultBean, "car", CarInfoBean.class);
        loadPageData(myCar);
    }

    /**
     * 加载页面数据
     */
    private void loadPageData(CarInfoBean myCar) {
        if (null != Constant.userBean && null != Constant.userBean.getUserImg()) {
            LogUtils.d("请求图片的地址:" + Constant.SERVER_URL + Constant.userBean.getUserImg());
            Glide.with(this).load(Constant.SERVER_URL + Constant.userBean.getUserImg());
        }

        carNameTV.setText(myCar.getBrand() + " " + myCar.getModles());
        carIdTv.setText(myCar.getPlateNumber());
        carFDTv.setText(myCar.getEngineNumber());

        breakdownTv.setText("无");
        alarmTv.setText(isGood(myCar.isAlarmMessage()));
        int progress = (int) ((myCar.getOil()/myCar.getOilBox()) * 100);
        oilProgress.setProgress(progress);
        progressSetBackGround(oilProgress);
    }

    @Override public void showProgress() {

    }

    @Override public void hideProgress() {

    }

    /**
     * 通过代码设定进度条的颜色
     * @param progressBar
     */
    private void progressSetBackGround(ProgressBar progressBar) {
        if (progressBar.getProgress() > 20 && progressBar.getProgress() < 50)  {
            progressBar.setBackgroundResource(R.color.color_progress_yello);
        } else if (progressBar.getProgress() >= 50) {
            progressBar.setBackgroundResource(R.color.color_progress_greed);
        } else {
            progressBar.setBackgroundResource(R.color.color_progress_red);
        }
    }

    private String isGood(boolean check) {
        return check ? "好": "坏";
    }
}
