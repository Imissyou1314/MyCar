package com.miss.imissyou.mycar.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.ui.TitleFragment;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.FindViewById;

/**
 * Created by Administrator on 2016-06-10.
 */
public class ChangePasswordActivity extends BaseActivity{

    @FindViewById(id = R.id.change_password_title)
    private TitleFragment title;
    @FindViewById(id = R.id.change_passowrd_sumbit)
    private Button sumbit;
    @FindViewById(id = R.id.change_password_password_Input)
    private EditText passwordInput1;
    @FindViewById(id = R.id.change_password_password_Input2)
    private EditText passwordInput2;

    private int Tag;
    private String url = Constant.SERVER_URL;
    private String oldPassword;
    private String newPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_change_password);
        passwordInput2.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        String titleStr = getIntent().getStringExtra("title");
        Tag = getIntent().getIntExtra("Tag",0);
        title.setTitleText(titleStr);
        setUpView(Tag);
    }

    @Override
    public void addListeners() {
        sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMyPassword();
            }
        });
    }

    private void changeMyPassword() {
        LogUtils.w("请求连接:" + url);

        HttpParams params = new HttpParams();
        params.put("id",Constant.userBean.getId()+"");
        params.put("oldSafePassword",oldPassword);
        params.put("newSafePassword",newPassword);

        HttpCallback callBack = new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
            }
        };

        RxVolley.post(url,params,callBack);

    }

    /**
     * 填充数据
     * @param Tag 订单
     */
    private void setUpView(int Tag) {
        switch (Tag) {
            case 0 :
                url = url + "user/changePassword";
                break;
            case 1:
                url = url + "user/changeSafePassword";
                break;
            case 2:
                url = url + "user/changeSafePassword";
                passwordInput2.setVisibility(View.GONE);
                break;
        }

    }
}
