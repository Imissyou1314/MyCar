package com.miss.imissyou.mycar.view.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.bean.UserBean;
import com.miss.imissyou.mycar.presenter.UserInfoPresenter;
import com.miss.imissyou.mycar.presenter.impl.UserInfoPresenterImpl;
import com.miss.imissyou.mycar.ui.LinearText;
import com.miss.imissyou.mycar.ui.RoundImageView;
import com.miss.imissyou.mycar.ui.circleProgress.CircleProgress;
import com.miss.imissyou.mycar.util.BlurTransformation;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.DialogUtils;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.ToastUtil;
import com.miss.imissyou.mycar.view.UserInfoView;
import com.miss.imissyou.mycar.view.activity.ChangePhoneNumberActivity;
import com.miss.imissyou.mycar.view.activity.LoginActivity;
import com.miss.imissyou.mycar.view.activity.UserBaseActivity;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by Imissyou on 2016/4/26.
 */
public class UserInfoFragment extends BaseFragment implements View.OnClickListener, UserInfoView {

    private ImageView userHeadBackground;           //用户头像背景图
    private RoundImageView userHeadRound;           //用户头像圆形图

    private LinearText userBaseLinear;              //用户的基本信息
    private LinearText userSaftCarInfo;             //用户车辆安全信息
    private Intent intent;                              //启动
    public final static int REQUEST_PHOTO = 2;          //页面photo
    private String photo_path ="";                      //图片地址
    private CharSequence safePasswordInput;             //用户输入的安全码

    private UserInfoPresenter mUserInfoPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_user_info, inflater, container, savedInstanceState);
    }


    @Override
    public boolean onBackPressed() {
        LogUtils.d("返回事件到这里了");
        return false;
    }

    @Override protected void initView(View view) {
        mUserInfoPresenter = new UserInfoPresenterImpl(this);

        userHeadBackground = (ImageView) view.findViewById(R.id.user_info_userhendImage);
        userHeadRound = (RoundImageView) view.findViewById(R.id.user_info_roundImage);

        userBaseLinear = (LinearText) view.findViewById(R.id.user_info_userBaseInfo);
        userSaftCarInfo = (LinearText) view.findViewById(R.id.user_info_userBaseSaft);
    }

    @Override
    protected void initData() {
        userBaseLinear.setTitle("基本信息").setTitleSize(16).setMessage("");
        userSaftCarInfo.setTitle("车辆与安全").setTitleSize(16).setMessage("");
        if (null != Constant.userBean && null != Constant.userBean.getUserImg()) {
            Glide.with(this).load(Constant.SERVER_URL + Constant.userBean.getUserImg()).into(userHeadRound);
            Glide.with(this).load(Constant.SERVER_URL + Constant.userBean.getUserImg())
                    .transform(new BlurTransformation(getActivity(),100)).crossFade().into(userHeadBackground);
        } else {
            LogUtils.w("用户没有图片");
        }
    }

    @Override
    protected void addViewsListener() {
        userBaseLinear.setOnClickListener(this);
        userHeadRound.setOnClickListener(this);
        userSaftCarInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        intent = new Intent();

        switch (v.getId()) {
            case R.id.user_info_userBaseInfo:
                toBasePage();
                break;
            case R.id.user_info_userBaseSaft:
                toBaseSaftPage();
                break;
            case R.id.user_info_roundImage:
                toSelectPhoto();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PHOTO:
                    //获取选中图片的路径
                    Cursor cursor = getActivity().getContentResolver().query(data.getData(), null, null, null, null);
                    if (cursor.moveToFirst()) {
                        photo_path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        LogUtils.w("图片地址:" + photo_path);
                        // TODO: 2016/6/11 图片上传
                        if (!photo_path.equals("")) {
                            mUserInfoPresenter.updataUserImage(photo_path);
                        } else {
                            Toast.makeText(getActivity(), "无法解析图片地址", Toast.LENGTH_SHORT).show();
                        }
                    }
                    cursor.close();
                    break;
            }
        }
    }

    /**
     * 去选择图片
     */
    private void toSelectPhoto() {
        //直接调用相册的图片
        Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        //"android.intent.action.GET_CONTENT"
        innerIntent.setType("image/*");
        Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");
        this.startActivityForResult(wrapperIntent, REQUEST_PHOTO);
    }

    /**
     * 去车辆安全页面
     */
    private void toBaseSaftPage() {
        // TODO: 2016-06-11 车辆与安全页面
        final EditText safePassword = new EditText(getActivity());
        safePassword.setHint("请输入安全码");
        safePassword.setTextSize(20);
        safePassword.setTextColor(R.color.color_back);
        new MaterialDialog.Builder(getActivity()).title("请输入安全码").content("车辆安全码")
                .inputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT)
                .input("请输入安全码","", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        dialog.dismiss();
                        safePasswordInput = input;
                        LogUtils.d("输入的安全码：" + safePasswordInput);
                        mUserInfoPresenter.checkSafePassword(safePasswordInput);
                    }
                }).show();
    }



    @Override
    public void onUpdateSuccess(String resultMessage) {
        Toast.makeText(getActivity(), resultMessage.equals("")?"更新成功":resultMessage,
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showResultError(int errorNo, String errorMag) {

    }

    @Override
    public void showResultSuccess(ResultBean resultBean) {
        if (resultBean.isServiceResult()) {
            intent.setClass(getActivity(), UserBaseActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), resultBean.getResultInfo().equals("") ? "安全码不正确" : resultBean.getResultInfo()
                    , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    /**
     * 去有户详情页面
     */
    private void toBasePage() {
        LogUtils.d("用户信息" + GsonUtils.Instance().toJson(Constant.userBean));
        if (null == Constant.userBean  && null == Constant.userBean.getId()) {
            intent.setClass(getActivity(), LoginActivity.class);
        }  else {
            intent.setClass(getActivity(),UserBaseActivity.class);
        }
        startActivity(intent);
    }
}
