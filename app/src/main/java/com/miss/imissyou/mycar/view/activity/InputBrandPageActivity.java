package com.miss.imissyou.mycar.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.util.FindViewById;

/**
 * Created by Imissyou on 2016/6/4.
 */
public class InputBrandPageActivity extends BaseActivity implements View.OnClickListener {

    @FindViewById( id = R.id.input_add_brand_input)
    private EditText brandInput;        //车牌号输入
    @FindViewById(id = R.id.input_add_brand_sumbit)
    private Button sumbit;              //提交按钮

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_input_barnd_page);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void addListeners() {
        sumbit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


    }
}
