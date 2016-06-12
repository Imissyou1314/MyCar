package com.miss.imissyou.mycar.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.ui.MissDialog;
import com.miss.imissyou.mycar.ui.TitleFragment;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.FindViewById;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.StringUtil;

/**
 * 更改用户密码和安全码
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
                changeMyPassword( setParams());
            }
        });
    }

    /**
     * 设置请求 Params
     */
    private HttpParams setParams() {

        oldPassword = passwordInput1.getText().toString();
        if (passwordInput2.getVisibility() == View.VISIBLE) {           //可见状态获取第二次输入
            newPassword = passwordInput2.getText().toString();
        } else {
            newPassword = oldPassword;
        }

        LogUtils.w("新密码:" + newPassword + "::>>>>旧密码:" + oldPassword );

        HttpParams params = new HttpParams();
        params.put("id",Constant.userBean.getId()+"");

        switch (Tag) {
            case 0:
                params.put("newPassword",newPassword);
                params.put("oldPassword",oldPassword);
                break;
            case 1:             //更新安全码
                params.put("oldSafePassword",oldPassword);
                params.put("newSafePassword",newPassword);
                break;
            case 2:                 //设置安全码
                //params.put("oldSafePassword",oldPassword);          //不需要
                params.put("newSafePassword",oldPassword);
                break;
        }
        return params;
    }

    /**
     * 更改密码
     * @param params  请求参数
     */
    private void changeMyPassword(HttpParams params) {
        LogUtils.w("请求连接:" + url);

        if (params == null)
            return;

        LogUtils.d("更改密码操作:" + url);
        HttpCallback callBack = new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                LogUtils.d("获取到的数据:" + t);
                ResultBean resultBean = GsonUtils.getResultBean(t);

                if (resultBean.isServiceResult()) {
                    Toast.makeText(ChangePasswordActivity.this,
                            resultBean.getResultInfo(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangePasswordActivity.this,
                            resultBean.getResultInfo(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                LogUtils.d("错误码:" + errorNo + ":::>>>" + strMsg);
            }
        };
        RxVolley.post(url,params,callBack);
    }

    /**
     * 填充数据 并生成URL
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
                passwordInput1.setHint("请输入安全码");
                passwordInput2.setVisibility(View.GONE);
                break;
        }
    }

    void showMessage(String title, String message) {
        new MissDialog.Builder(this).setTitle(title).setMessage(message).setSingleButton(true).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                goBack();
            }
        }).create().show();
    }

    /**
     * 返回上一页
     */
    private void goBack() {

        if(Tag == 0) {
            Constant.userBean.setPassword(StringUtil.encryptMd5(newPassword));
        } else {
            Constant.userBean.setSafePassword(StringUtil.encryptMd5(newPassword));
        }
        this.finish();
    }
}
