package com.miss.imissyou.mycar.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.impl.CarInfoPresenterImpl;
import com.miss.imissyou.mycar.ui.ToggleButton;
import com.miss.imissyou.mycar.ui.circleProgress.CircleProgress;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.DialogUtils;
import com.miss.imissyou.mycar.view.CarInfoView;
import com.miss.imissyou.mycar.view.CarInfoPresenter;


/**
 * 单架车的具体信息
 * Created by Imissyou on 2016/5/3.
 */
public class CarInfoFragment extends BaseFragment implements CarInfoView{



    private CircleProgress progress;


    private CarInfoPresenter mCarInfoPresenter;

    private ToggleButton alarmBtn;
    private ToggleButton stateBtn;
    private Long mCarId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_car_info, inflater, container, savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        progress = (CircleProgress) view.findViewById(R.id.load_carInfo_progress);
        alarmBtn = (ToggleButton) view.findViewById(R.id.carinfo_alarm_button);
        stateBtn = (ToggleButton) view.findViewById(R.id.carinfo_state_button);
    }


    @Override protected void initData() {
        LogUtils.w(getArguments()+"");
        Long userId = getArguments().getLong(Constant.USER_ID);
        Long carId = getArguments().getLong(Constant.CAR_ID);
        this.mCarId = carId;

        mCarInfoPresenter = new CarInfoPresenterImpl(this);
        if (!(carId.equals("") && userId.equals(""))) {
            LogUtils.d("userId:" + userId + ">>>carId:" + carId);
            mCarInfoPresenter.loadCarInfo(userId, carId);
        }
    }

    @Override protected void addViewsListener() {
                                 alarmBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
                                     @Override
                                     public void onToggle(boolean on) {
                                         mCarInfoPresenter.changeCarAlarmState(mCarId);
                                     }
                                 });
        stateBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                        mCarInfoPresenter.changeCarState(mCarId);
            }
        });
    }

    @Override public void showResultError(int errorNo, String errorMag) {
        String title = "提示";
        if (errorNo == 0) {
            title = "警告";
        } else if (errorNo == Constant.SUCCESS_NO){
            title = Constant.SUCCESS_TITLE;
        }
      new DialogUtils(getActivity()).errorMessage(errorMag, title);

    }

    @Override public void showResultSuccess(ResultBean resultBean) {
        if (resultBean.isServiceResult()) {
            showResultError(Constant.SUCCESS_NO, resultBean.getResultInfo());
        }
    }

    @Override public void showProgress() {
        progress.startAnim();
    }

    @Override public void hideProgress() {
        progress.stopAnim();
    }

    @Override
    public void onDestroy() {
        mCarInfoPresenter.detchView();
        super.onDestroy();
    }

    @Override
    public void showResultSuccess(CarInfoBean resultBean) {
        LogUtils.d(resultBean.getBrand());
    }
}
