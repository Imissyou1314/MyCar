package com.miss.imissyou.mycar.view.activity;

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
import com.miss.imissyou.mycar.ui.RoundImageView;
import com.miss.imissyou.mycar.ui.TitleFragment;
import com.miss.imissyou.mycar.util.FindViewById;
import com.miss.imissyou.mycar.util.GsonUtils;
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
    @FindViewById(id = R.id.activity_add_newCar_carId_input)
    private EditText carid;

    private AddNewCarInputPresenter mAddAddNewCarInputPresenter;
    private CarInfoBean resultBean;
    private String carInfoJson;         //上一页传来的Json

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_add_newcar_input);
        mAddAddNewCarInputPresenter = new AddNewCarInputPresenterImpl(this);
    }

    /**
     * 于服务器的交互
     */
    @Override public void addListeners() {
        titleView.setTitleText("确认车辆信息");
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                LogUtils.d("提交数据");

                if(carInfoJson.isEmpty()) return;
                mAddAddNewCarInputPresenter.saveCarInfo(carInfoJson);
                mAddAddNewCarInputPresenter.sentCarInfoToService(carInfoJson);
            }
        });
        carid.setText(carInfoJson);
    }

    @Override public void showProgress() {

    }

    @Override public void hideProgress() {

    }

    @Override protected void onDestroy() {
        mAddAddNewCarInputPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void initData() {
        carInfoJson = getIntent().getStringExtra("result");
        mAddAddNewCarInputPresenter.loadCar(carInfoJson);
    }

    @Override public void showResultError(int errorNo, String errorMag) {
        //TODO
    }

    @Override public void showResultSuccess(ResultBean resultBean) {

    }

    @Override public void showResultSuccess(CarInfoBean resultBean) {
        this.resultBean = resultBean;
        LogUtils.d("回调的信息" + GsonUtils.Instance().toJson(resultBean));
    }
}
