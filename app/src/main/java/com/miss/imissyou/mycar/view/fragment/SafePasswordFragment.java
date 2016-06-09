package com.miss.imissyou.mycar.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.DialogUtils;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.StringUtil;
import com.miss.imissyou.mycar.view.SafePasswordView;

/**
 * 设置安全码
 * Created by Imissyou on 2016/5/14.
 */
public class SafePasswordFragment extends BaseFragment implements SafePasswordView {

    private EditText passWordInput;
    private EditText safePasswordInput;
    private EditText safePasswordTwoInput;
    private Button submitBtn;
    private String password;
    private String safePassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_safe_passord_view, inflater, container, savedInstanceState);
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

    @Override
    protected void initView(View view) {
        passWordInput = (EditText) view.findViewById(R.id.safepassword_password_Edit);
        safePasswordInput = (EditText) view.findViewById(R.id.safepassword_safepasswordOne_Edit);
        safePasswordTwoInput = (EditText) view.findViewById(R.id.safepassword_safepasswordTwo_Edit);
        submitBtn = (Button) view.findViewById(R.id.safePassword_sumbit);
    }

    @Override
    protected void initData() {
        if (null != Constant.userBean && null != Constant.userBean.getSafePassword()) {
            passWordInput.setHint("请输入旧安全码");
        }
    }

    @Override
    protected void addViewsListener() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInput();
            }
        });
    }

    private void getInput() {
        password = passWordInput.getText().toString();
        if (!StringUtil.checkUserIsLogin()) {
            showErrorMessage("警告", "请去登录");
        }
        if (checkInput()) {
            safePassword = safePasswordTwoInput.getText().toString();
        } else {
            showErrorMessage("警告", "安全码不一致");
        }

        if (checkNoNUll()) {
            showErrorMessage("警告", "输入不能为空");
        }
        sentToService(password, safePassword);
    }


    /**
     * 更改安全码提交到服务器
     *
     * @param password
     * @param safePassword
     */
    private void sentToService(String password, String safePassword) {

        HttpParams params = new HttpParams();
        params.putHeaders("cookie", Constant.COOKIE);

        params.put("id", Constant.userBean.getId() + "");
        params.put("newSafePassword", safePassword);

        String url = Constant.SERVER_URL + "users/confirmSafePassword";     //添加安全码

        if (null != Constant.userBean && null != Constant.userBean.getSafePassword()) {
            params.put("oldSafePassword", password);
            url = Constant.SERVER_URL + "users/changeSafePassword";     //更改安全码
        }

        RxVolley.post(url, params, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                ResultBean resultBean = GsonUtils.Instance().fromJson(t, ResultBean.class);
                LogUtils.d(t);
                showErrorMessage("操作成功", resultBean.getResultInfo());
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                LogUtils.d(strMsg);
                showErrorMessage("操作失败", strMsg);
            }
        });
    }

    /**
     * 提示错误信息
     *
     * @param title
     * @param message
     */
    private void showErrorMessage(String title, String message) {
        new DialogUtils(getActivity())
                .errorMessage(title, message)
                .show();
    }

    /**
     * 检查两次输入的安全码是否相同
     *
     * @return
     */
    private boolean checkInput() {

        return safePasswordTwoInput.getText().toString()
                .equals(safePasswordInput.getText().toString()) ? true : false;
    }

    /**
     * 检查输入不为空
     *
     * @return
     */
    private boolean checkNoNUll() {
        return StringUtil.isEmpty(password) && StringUtil.isEmpty(safePassword);
    }
}
