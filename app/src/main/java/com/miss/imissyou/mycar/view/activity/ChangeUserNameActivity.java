package com.miss.imissyou.mycar.view.activity;

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
import com.miss.imissyou.mycar.bean.UserBean;
import com.miss.imissyou.mycar.ui.TitleFragment;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.FindViewById;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.RxVolleyUtils;

import java.util.Map;

/**
 * 更改用户名
 * Created by Administrator on 2016-06-11.
 */
public class ChangeUserNameActivity extends BaseActivity {

    @FindViewById(id = R.id.change_username_input)
    private EditText input;         //输入框
    @FindViewById(id = R.id.change_username_sumbit)
    private Button sumbit;          //提交框
    @FindViewById(id = R.id.change_username_title)
    private TitleFragment title;    //标题

    private String name;            //用户名
    private int Tag;                //标志
    private String titleStr;         //标题
    private String url = Constant.SERVER_URL + "users/update";           //请求URL

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_change_username);
    }

    @Override
    protected void initData() {
        titleStr = getIntent().getStringExtra("title");
        title.setTitleText(titleStr);
        name = getIntent().getStringExtra("name");
        Tag = getIntent().getIntExtra("Tag", 0);
        if (null != name && !name.equals("")) {
            input.setText(name);
        } else {
            LogUtils.d("获取到错误的数据：");
        }
    }

    @Override
    public void addListeners() {
        sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSubmit(comfindParams(Tag));
            }
        });
    }

    /**
     * 提交请求
     *
     * @param params 请求参数
     */
    private void doSubmit(HttpParams params) {

        if (null == params)
            return;

        RxVolleyUtils.getInstance().post(url, params, new HttpCallback() {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                LogUtils.d("错误码是:" + errorNo + ">>>>错误信息:" + strMsg);
                Toast.makeText(ChangeUserNameActivity.this, strMsg,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Map<String, String> headers, byte[] t) {
                Constant.COOKIE = headers.get("cookie");
            }

            @Override
            public void onSuccess(String t) {
                LogUtils.w("getData ：" + t);
                ResultBean resultBean = GsonUtils.getResultBean(t);

                if (resultBean.isServiceResult()) {
                    goBack();
                } else {
                    if (resultBean.getResultInfo().equals(Constant.FileCOOKIE)) {
                        RxVolleyUtils.getInstance().restartLogin();
                    } else {
                        Toast.makeText(ChangeUserNameActivity.this,
                                resultBean.getResultInfo(), Toast.LENGTH_LONG).show();
                    }

                }
            }

        });
    }

    /**
     * 放回上一页
     */
    private void goBack() {
        if (Tag == UserBaseActivity.USER_NAME_TAG ) {
            Constant.userBean.setUsername(name);
        } else if (Tag == UserBaseActivity.USER_REALE_NAME) {
            Constant.userBean.setRealName(name);
        }
        this.finish();
    }

    /**
     * 配置请求参数和URL
     *
     * @param tag 标志
     */
    private HttpParams comfindParams(int tag) {

        name = input.getText().toString();

        if (name.equals("")) {
            Toast.makeText(ChangeUserNameActivity.this, "输入不能为空",
                    Toast.LENGTH_SHORT).show();
            return null;
        }

        HttpParams params = new HttpParams();
        params.put("id", Constant.userBean.getId() + "");

        if (null != Constant.userBean.getPhone())
            params.put("phone", Constant.userBean.getPhone());
        if (null != Constant.userBean.getSafePassword())
            params.put("safePassword", Constant.userBean.getSafePassword());
        if (null != Constant.userBean.getUserImg())
            params.put("userImg", Constant.userBean.getUserImg());
        if (Tag == 0) {
            params.put("username", name);
            if (null != Constant.userBean.getRealName())
                params.put("realName", Constant.userBean.getRealName());
        } else if (Tag == 1) {
            params.put("realName", name);
            if (null != Constant.userBean.getUsername())
                params.put("username", Constant.userBean.getUsername());
        }
        LogUtils.d("用户输入的是:" + name);
        return params;
    }
}
