package com.miss.imissyou.mycar.view.activity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.ui.MissDialog;
import com.miss.imissyou.mycar.ui.TitleFragment;
import com.miss.imissyou.mycar.util.FindViewById;
import com.miss.imissyou.mycar.util.StringUtil;


/**
 * Created by Imissyou on 2016/3/27.
 */
public class FindPasswordActivity extends BaseActivity {

    @FindViewById(id = R.id.findpassword_doSumbit_Btn)
    private Button sumbitBtn;

    @FindViewById(id = R.id.findpassword_account_Edit)
    private EditText accountNumber;
    @FindViewById(id = R.id.findpassword_passwordOne_Edit)
    private EditText passwordOneEdit;
    @FindViewById(id = R.id.findpassword_passwordTwo_Edit)
    private EditText passwordTwoEdit;
    @FindViewById(id = R.id.findpassword_title)
    private TitleFragment titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_findpassword);
    }

    @Override public void addListeners() {
        titleView.setTitleText("找回密码");
        sumbitBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                doFindPassword();
            }
        });
    }

    private void doFindPassword() {
        String account = StringUtil.getEditInput(accountNumber);
        String passwordOne = StringUtil.getEditInput(passwordOneEdit);
        String passwordTwo = StringUtil.getEditInput(passwordTwoEdit);

        if (account.isEmpty() || passwordOne.isEmpty())
            return;
        /**
         * 密码相同，账号含有@
         */
        if (passwordOne.equals(passwordTwo) && account.contains("@")) {
            //处理找回密码事件
        } else {
            //提示密码或者账号不存在@
            propmtError();
        }

    }

    /**
     * 提示错误信息
     */
    private void propmtError() {
        final MissDialog.Builder builder = new MissDialog.Builder(this);
        builder.setTitle("提示").setMessage("密码或者账号错误").setSingleButton(true);

        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
