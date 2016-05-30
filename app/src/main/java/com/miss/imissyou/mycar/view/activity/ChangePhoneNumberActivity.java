package com.miss.imissyou.mycar.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.ChangePhoneNumberPresenter;
import com.miss.imissyou.mycar.presenter.impl.ChangePhoneNumberPresenterImpl;
import com.miss.imissyou.mycar.ui.TitleFragment;
import com.miss.imissyou.mycar.util.DialogUtils;
import com.miss.imissyou.mycar.util.FindViewById;
import com.miss.imissyou.mycar.view.ChangePhoneNumberView;

/**
 * 该用户手机或者亲人手机
 * Created by Imissyou on 2016/5/5.
 */
public class ChangePhoneNumberActivity extends BaseActivity implements ChangePhoneNumberView {

    @FindViewById(id = R.id.changePhoneNumber_phoneNumber_input)
    EditText phoneNumberInput;              //更改的手机号
    @FindViewById(id = R.id.changePhoneNumber_code_input)
    EditText codeInput;                     //获取的验证码
    @FindViewById(id = R.id.changePhoneNumber_getCode_submit)
    Button getCodeSubmit;                   //获取验证码的按钮
    @FindViewById(id = R.id.changePhoneNumber_submit)
    Button submit;                          //提交的按钮
    @FindViewById(id = R.id.changePhone_title)
    TitleFragment title;


    private ChangePhoneNumberPresenter mChangePhoneNumberPresenter;
    private int TAG;
    private String titlePage = "更改手机号";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_change_phonenumber);

    }

    @Override protected void initData() {
        mChangePhoneNumberPresenter = new ChangePhoneNumberPresenterImpl(this);
    }

    @Override public void addListeners() {
        titlePage = getIntent().getStringExtra("title");
        TAG = getIntent().getIntExtra("TAG", 0);

        title.setTitleText(titlePage);

        getCodeSubmit.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mChangePhoneNumberPresenter.getCode(phoneNumberInput.getText().toString());
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mChangePhoneNumberPresenter.submit(phoneNumberInput.getText().toString(), codeInput.getText().toString(), TAG);
            }
        });
    }

    @Override
    public void showResultError(int errorNo, String errorMag) {
        String title = "";
        if (0 == errorNo) {
            title = "操作异常";
        } else {
            title = "系统异常";
        }
        showMessage(title,errorMag);
    }

    @Override
    public void showResultSuccess(ResultBean resultBean) {
            if (resultBean.isServiceResult()) {
                showMessage("操作成功",resultBean.getResultInfo());
            } else {
                showMessage("操作失败",resultBean.getResultInfo());
            }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    /**
     * 显示信息
     * @param title
     * @param message
     */
    private void showMessage(String title, String message) {
        new DialogUtils(this)
                .errorMessage(title, message)
                .show();
    }
}
