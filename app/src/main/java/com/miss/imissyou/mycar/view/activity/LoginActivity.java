package com.miss.imissyou.mycar.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.MainActivity;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.bean.UserBean;
import com.miss.imissyou.mycar.ui.MissDialog;
import com.miss.imissyou.mycar.ui.RoundImageView;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.FindViewById;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.SPUtils;
import com.miss.imissyou.mycar.util.StringUtil;
import com.miss.imissyou.mycar.util.zxing.camera.LoadImageView;

import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 登陆页面
 * Created by Imissyou on 2016/3/26.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @FindViewById(id = R.id.login_doLogin_Btn)
    private Button loginBtn;
    @FindViewById(id = R.id.login_doRegister_Btn)
    private Button registerBtn;
    @FindViewById(id = R.id.login_findPassword_textView)
    private TextView findPasswordTv;
    @FindViewById(id = R.id.login_savePassword_checkBox)
    private CheckBox savePasswordCk;
    @FindViewById(id = R.id.login_account_Edit)
    private EditText accountEt;
    @FindViewById(id = R.id.login_password_Edit)
    private EditText passwordEt;
    @FindViewById(id = R.id.login_userHead_image)
    private RoundImageView userHeadImage;

    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 密码长度
     */
    private int length = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_login);
    }

    /**
     * 加载数据
     */

    @Override
    public void initData() {
        SPUtils.init(this);
        password = SPUtils.getSp_user().getString(Constant.UserPassID, "");
        account = SPUtils.getSp_user().getString(Constant.UserAccountID, "");
        length = SPUtils.getSp_user().getInt(Constant.UserPassLength, 0);


        if (!account.equals("") && !password.equals("")) {
            accountEt.setText(account);
            //构造加密码
            passwordEt.setText("11111111");
        }
        if (!SPUtils.getSp_user().getString(Constant.UserBeanID, "").equals("")) {
            Constant.userBean = GsonUtils.Instance()
                    .fromJson(SPUtils.getSp_user().getString(Constant.UserBeanID, ""), UserBean.class);
            if (null != Constant.userBean && null != Constant.userBean.getUserImg()) {
                //加载用户图片
                LogUtils.d("登录加载用户图片");
                String url = Constant.SERVER_URL + Constant.userBean.getUserImg();
                LogUtils.d("加载图片的地址" + url);
               Glide.with(this)
                        .load(Constant.SERVER_URL + Constant.userBean.getUserImg())
                       .centerCrop()
                       .into(userHeadImage);
            }

        } else {
            LogUtils.d("用户为新用户");
        }


    }

    @Override
    public void addListeners() {
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        findPasswordTv.setOnClickListener(this);
        //加载数据
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_doLogin_Btn:
                doLogin();
                break;
            case R.id.login_doRegister_Btn:
                doRegister();
                break;
            case R.id.login_findPassword_textView:
                doFindPassword();
                break;
            default:
                break;
        }
    }

    /**
     * 登录处理
     */
    private boolean doLogin() {
        /**弹框*/
        final MissDialog.Builder builder = new MissDialog.Builder(this);

        /**
         * 自己输入时
         */
        String tmpaccount = getInput(accountEt);
        String tmpPassword = getInput(passwordEt);

        LogUtils.d("账号:" + tmpaccount + ">>>>密码:" + tmpPassword);
        if (!tmpaccount.equals(account) || tmpPassword.length() != length) {
            account = tmpaccount;
            password = tmpPassword;
            length = password.length();
            password = StringUtil.strToMD5(password);
        }

        /**
         * 验证密码
         */
        if (account.equals("") || password.equals("")
                && (length > 4 && length < 16)) {
            builder.setTitle("请正确输入")
                    .setMessage("密码和账号不能为空，而且密码不能少于4位数，大于16位数")
                    .setSingleButton(true)
                    .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            passwordEt.setText("");
                            accountEt.setText("");
                        }
                    });
            builder.create().show();
            return false;
        }

        HttpParams params = new HttpParams();
        LogUtils.d("加密后的密码" + password);
        params.put("password", password);
        params.put("loginid", account);
        if (null != Constant.COOKIE)
            params.put("cookie", Constant.COOKIE);
        //服务器URL
        String url = Constant.SERVER_URL + "users/doLogin";
        RxVolley.post(url, params, new HttpCallback() {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                builder.setTitle("登录出错")
                        .setMessage(strMsg)
                        .setSingleButton(true)
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }

            @Override
            public void onSuccess(Map<String, String> headers, byte[] t) {
                ResultBean resultBean = GsonUtils.Instance().fromJson(StringUtil.bytesToString(t), ResultBean.class);
                LogUtils.d("收到的数据::" + StringUtil.bytesToString(t));
                if (resultBean.isServiceResult()) {
                    Constant.COOKIE = headers.get("Set-Cookie");
                    Constant.userBean = GsonUtils.getParam(resultBean, "user", UserBean.class);
                    savePassWord(password, account, GsonUtils.Instance().toJson(Constant.userBean));
                    setAlias(Constant.userBean.getId());
                    toMainView();
                } else {
                    builder.setTitle("登录出错")
                            .setMessage(resultBean.getResultInfo())
                            .setSingleButton(true)
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }

            }
        });

        if (savePasswordCk.isChecked() && length < 16) {
            savePassWord(password, account, GsonUtils.Instance().toJson(Constant.userBean));
        }
        return true;
    }

    /**
     * JPushInterface绑定别名
     *
     * @param id
     */
    private void setAlias(Long id) {
        LogUtils.w("用户ID:" + id);

        if (id != null && !id.toString().equals("")) {
            String usesrId = id.toString();
            if (usesrId.contains(".")) {
                usesrId = usesrId.substring(0, usesrId.indexOf("."));
            }
            JPushInterface.setAlias(this, usesrId, new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    LogUtils.w("JPushInterface设置状态:" + i);
                }
            });
        }
    }

    /**
     * 跳转到主页面
     */
    private void toMainView() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * 转到注册页面
     */
    private void doRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    /**
     * 转到找回秘密页面
     */
    private void doFindPassword() {
        startActivity(new Intent(this, FindPasswordActivity.class));
    }

    /**
     * 获取控件的输入
     *
     * @param inputView EditText
     * @return
     */
    private String getInput(EditText inputView) {
        return null == inputView ? null : inputView.getText().toString();
    }

    /**
     * 保存用户密码账号
     *
     * @param password
     * @param account
     */
    private void savePassWord(String password, String account, String userBeanJson) {
        SPUtils.putUserData(this, Constant.UserPassID, password);
        SPUtils.putUserData(this, Constant.UserAccountID, account);
        SPUtils.putUserData(this, Constant.UserBeanID, userBeanJson);

        if (length < 16 && length > 4) {
            SPUtils.putCacheData(this, Constant.UserPassLength, length);
        }
    }
    @Override
    protected void onDestroy() {
        LoadImageView.releaseImageViewResouce(userHeadImage);
        super.onDestroy();
    }
}
