package com.miss.imissyou.mycar.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.ui.MissDialog;
import com.miss.imissyou.mycar.ui.TitleFragment;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.FindViewById;
import com.miss.imissyou.mycar.util.StringUtil;
import com.miss.imissyou.mycar.util.ToastUtil;

/**
 * 注册页面
 * Created by Imissyou on 2016/3/26.
 */
public class RegisterActivity extends BaseActivity {

    @FindViewById(id = R.id.register_doSumbit_Btn)
    private Button sumbitBtn;

    @FindViewById(id = R.id.register_account_Edit)
    private EditText accountEdit;
    @FindViewById(id = R.id.register_passwordone_Edit)
    private EditText passwordOneEdit;
    @FindViewById(id = R.id.register_passwordtwo_Edit)
    private EditText passwordTwoEdit;
    @FindViewById(id = R.id.register_title)
    private TitleFragment titleView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_register);
    }

    @Override public void addListeners() {
        titleView.setTitleText("注册");
        sumbitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });

    }

    /**
     * 登录处理
     */
    private void doRegister() {
        String account = StringUtil.getEditInput(accountEdit);
        String passwordOne = StringUtil.getEditInput(passwordOneEdit);
        String passwordTwo = StringUtil.getEditInput(passwordTwoEdit);

        LogUtils.d(account);
        LogUtils.d(passwordOne);
        LogUtils.d(passwordTwo);

        if (account.isEmpty() || passwordOne.isEmpty()) {
            propmtError("输入不能为空");
            return;
        }


        /**
         * 密码相同，账号含有@
         */
        if (passwordOne.equals(passwordTwo) && account.contains("@")) {
            String password = StringUtil.strToMD5(passwordOne);
            LogUtils.d(password);
            HttpParams params = new HttpParams();
            params.put("password",passwordOne);
            params.put("loginid",account);

            String url = Constant.SERVER_URL + "users/register";
            RxVolley.post(url, params, new HttpCallback() {
                @Override
                public void onFailure(int errorNo, String strMsg) {
                    LogUtils.d("错误码::" + errorNo);
                    LogUtils.d("错误信息::" + strMsg);
                }

                @Override
                public void onSuccess(String t) {
                    LogUtils.d("注册成功::" + t);
                    if (t.isEmpty())
                        Toast.makeText(RegisterActivity.this, t , Toast.LENGTH_SHORT).show();
                    ToastUtil.asLong("注册成功");
                }
            });
        } else {
            //提示密码或者账号不存在@
            propmtError("密码或者账号错误");
        }
    }

    /**
     * 提示错误信息
     */
    private void propmtError(String msg) {
        final MissDialog.Builder missBuilder = new MissDialog.Builder(this);
        missBuilder.setTitle("提示").setMessage(msg).setSingleButton(true);

        missBuilder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LogUtils.d("点击了确认");
                dialog.dismiss();
                return;
            }
        });
        missBuilder.create().show();
    }
}
