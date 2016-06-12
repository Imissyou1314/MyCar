package com.miss.imissyou.mycar.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.UserBean;
import com.miss.imissyou.mycar.ui.LinearText;
import com.miss.imissyou.mycar.ui.TitleFragment;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.FindViewById;

import static com.miss.imissyou.mycar.R.id.userBase_info_relatedPhone;
import static com.miss.imissyou.mycar.R.id.userBase_info_userRealeName;

/**
 * Created by Administrator on 2016-06-10.
 */
public class UserBaseActivity extends BaseActivity implements View.OnClickListener {


    @FindViewById(id = R.id.userBase_info_title)
    private TitleFragment title;            //标题

    @FindViewById(id = R.id.userBase_info_userName)
    private LinearText userNameText;        //用户昵称
    @FindViewById(id = R.id.userBase_info_userRealeName)
    private LinearText userReadleText;      //用户真实姓名
    @FindViewById(id = R.id.userBase_info_account)
    private LinearText userAccountText;     //用户账号

    @FindViewById(id = R.id.userBase_info_userPhone)
    private LinearText userPhoneText;       //用户电话
    @FindViewById(id = userBase_info_relatedPhone)
    private LinearText relatedPhoneText;    //用户亲人手机
    @FindViewById(id = R.id.userBase_info_sentSaftPassword)
    private LinearText sentSaftPassword;    //设置安全码
    @FindViewById(id = R.id.userBase_info_changePassword)
    private LinearText changePassword;      //修改密码

    /**
     * 用户实体
     */
    UserBean userBean = new UserBean();

    public static final int USER_NAME_TAG = 0;            //更改用户昵称
    public static final int USER_REALE_NAME = 1;        //更改用户名
    public static final int USER_PHONE_TAG = 0;         //更改用户手机号
    public static final int REALE_PHONE_YAG = 1;       //更改亲人手机号
    public static final int ACCOUNT_PASSWOED = 0;       //账户密码
    public static final int SATF_PASSOWRD = 1;          //安全码
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_userbase_info);
    }

    @Override
    protected void initData() {
        title.setTitleText("用户信息");
        //取得用户实体
        if (null != Constant.userBean && null != Constant.userBean.getId()) {
            userBean = Constant.userBean;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initViewData(userBean);         //填充数据
    }

    @Override
    public void addListeners() {
        userNameText.setOnClickListener(this);
        userReadleText.setOnClickListener(this);
        //userAccountText.setOnClickListener(this);  //不可以更改

        userPhoneText.setOnClickListener(this);
        relatedPhoneText.setOnClickListener(this);
        sentSaftPassword.setOnClickListener(this);
        changePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        intent = new Intent();
        switch (v.getId()) {
            case R.id.userBase_info_userName:
                changeUserName(USER_NAME_TAG);
                break;
            case userBase_info_userRealeName:
                changeUserName(USER_REALE_NAME);
                break;
            case R.id.userBase_info_userPhone:
                changePhone(USER_PHONE_TAG);
                break;
            case userBase_info_relatedPhone:
                changePhone(REALE_PHONE_YAG);
                break;
            case R.id.userBase_info_sentSaftPassword:
                changeUserPassword(SATF_PASSOWRD);
                break;
            case R.id.userBase_info_changePassword:
                changeUserPassword(ACCOUNT_PASSWOED);
                break;
        }
    }

    /**
     * 更改用户密码
     *
     * @param Tag 标志
     */
    private void changeUserPassword(int Tag) {
        LogUtils.d("标志");
        String title = "更改账户密码";
        if (Tag != ACCOUNT_PASSWOED) {
            if (null != userBean.getSafePassword()) {
                title = "更改安全码";
            } else {
                title = "设置安全码";
                Tag = 2;
            }
        }
        intent.putExtra("title", title);
        intent.putExtra("Tag", Tag);
        intent.setClass(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    /**
     * 更改用户或者亲人手机号
     *
     * @param Tag 标志
     */
    private void changePhone(int Tag) {
        String title = "更改手机号";
        if (Tag == USER_PHONE_TAG) {
            title = "更改绑定手机";
        } else {
            title = "更改亲人手机";
        }

        intent.putExtra("title", title);
        intent.putExtra("Tag", Tag);
        intent.setClass(this, ChangePhoneNumberActivity.class);
        startActivity(intent);
    }

    /**
     * 更改用户名
     *
     * @param nameTag 标志
     */
    private void changeUserName(int nameTag) {
        // TODO: 2016-06-10 更改用户名
        String title = "用户名";
        if (nameTag == USER_NAME_TAG) {
            title = "更改用户昵称";
        } else {
            title = "更改用户名";
        }

        intent.putExtra("title", title);
        intent.putExtra("Tag", nameTag);
        intent.setClass(this, ChangeUserNameActivity.class);
        startActivity(intent);
    }


    private void initViewData(UserBean userBean) {

        userNameText.setTitle("昵称").setMessage(userBean.getUsername());
        userReadleText.setTitle("真实姓名").setMessage(userBean.getRealName());
        userAccountText.setTitle("账号").setMessage(userBean.getLoginid() + "");

        userPhoneText.setTitle("绑定手机")
                .setMessage(null != userBean.getPhone() ? userBean.getPhone() : "未绑定");
        relatedPhoneText.setTitle("亲人手机").setMessage(null != userBean.getRelatedPhone()
                ? userBean.getRelatedPhone() : "未绑定");
        sentSaftPassword.setTitle("设置安全密码")
                .setMessage(null != userBean.getSafePassword() ? "已设置" : "未设置");
        changePassword.setTitle("修改账户密码").setMessage("");
    }
}
