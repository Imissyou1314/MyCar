package com.miss.imissyou.mycar.view.fragment;

import android.content.DialogInterface;
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
import com.miss.imissyou.mycar.ui.MissDialog;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.SPUtils;
import com.miss.imissyou.mycar.util.StringUtil;

/**
 * 更改用户密码
 * Created by Imissyou on 2016/5/7.
 */
public class ChangePasswordFragment extends BaseFragment {

    private EditText oldPassword;
    private EditText passwordOne;
    private EditText passwordTwo;
    private Button submit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_change_password, inflater, container, savedInstanceState);
    }

    @Override protected void initView(View view) {
        oldPassword = (EditText) view.findViewById(R.id.changePassword_old_input);
        passwordOne = (EditText) view.findViewById(R.id.changePassword_new_input);
        passwordTwo = (EditText) view.findViewById(R.id.changePassword_new_input_ag);

        submit = (Button) view.findViewById(R.id.changePassword_submit);
    }

    @Override protected void initData() {

    }

    @Override protected void addViewsListener() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                doChangePassword();
            }
        });
    }

    private void doChangePassword() {
        String oldPasswords = oldPassword.getText().toString();
        String newPassWordOne = passwordOne.getText().toString();
        String newPasswordTwo = passwordTwo.getText().toString();
        if (check(oldPasswords) || check(newPasswordTwo) || check(newPassWordOne))

        if (newPassWordOne.equals(newPasswordTwo)) {
            sendToService(oldPasswords,newPassWordOne);
        }

    }

    private void sendToService(String oldPasswords, String newPassWordOne) {
        String md5oldPassword = StringUtil.encryptMd5(oldPasswords);
        final String md5newPassword = StringUtil.encryptMd5(newPassWordOne);

        HttpParams params = new HttpParams();
        params.putHeaders("cookie", Constant.COOKIE);
        params.put("id",Constant.userBean.getId() + "");
        params.put("oldPassword", md5oldPassword);
        params.put("newPassword", md5newPassword);

        String url = Constant.SERVER_URL + "users/changePassword";

        RxVolley.post(url, params, new HttpCallback() {
            @Override public void onSuccess(String t) {
                ResultBean resultBean = GsonUtils.getResultBean(t);
                if (resultBean.isServiceResult()) {
                    dialogToUser("操作成功","更改密码成功" );
                    Constant.userBean.setPassword(md5newPassword);
                    savePasswordToSPU(md5newPassword);
                } else {
                    dialogToUser("操作失败", resultBean.getResultInfo());
                }

            }
            @Override public void onFailure(int errorNo, String strMsg) {

                LogUtils.d("错误码:" + errorNo + "::错误信息" + strMsg);
                dialogToUser("连接错误", strMsg);
            }
        });

    }

    /**
     * 更新保存的用户密码
     * @param md5newPassword
     */
    private void savePasswordToSPU(String md5newPassword) {
        SPUtils.init(getActivity());
        SPUtils.putUserData(Constant.UserPassID, md5newPassword);
    }

    /**
     * 检查密码输入是否为空
     * @param password
     * @return
     */
    private boolean check(String password) {
        if (password.equals("") && password == null) {
            dialogToUser("警告","请检查不为空再输入！");
            return false;
        }
            return true;
    }

    /**
     * 弹框提示用户
     * @param title
     * @param message
     */
    private void dialogToUser(String title, String message) {
        MissDialog.Builder builder = new  MissDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setSingleButton(true)
                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                oldPassword.setText("");
                passwordOne.setText("");
                passwordTwo.setText("");
            }
        });
        builder.create().show();
    }

    @Override public void onDestroy() {
        super.onDestroy();
    }
}
