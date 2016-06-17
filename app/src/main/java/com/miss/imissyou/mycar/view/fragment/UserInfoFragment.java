package com.miss.imissyou.mycar.view.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.UserInfoPresenter;
import com.miss.imissyou.mycar.presenter.impl.UserInfoPresenterImpl;
import com.miss.imissyou.mycar.ui.LinearText;
import com.miss.imissyou.mycar.ui.RoundImageView;
import com.miss.imissyou.mycar.util.BlurTransformation;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.FastBulrTransformation;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.view.UserInfoView;
import com.miss.imissyou.mycar.view.activity.CarAndSaftActivity;
import com.miss.imissyou.mycar.view.activity.CutImageActvity;
import com.miss.imissyou.mycar.view.activity.LoginActivity;
import com.miss.imissyou.mycar.view.activity.UserBaseActivity;

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
    private String photo_path = "";                      //图片地址
    private CharSequence safePasswordInput;             //用户输入的安全码

    private UserInfoPresenter mUserInfoPresenter;
    public static final int CUT_IMAGE_PAGE = 3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_user_info, inflater, container, savedInstanceState);
    }


    @Override
    public boolean onBackPressed() {
        LogUtils.d("返回事件到这里了");
        return true;
    }

    @Override
    public void showSafePasswordSucess(ResultBean resultBean) {
        LogUtils.d("安全码正确");
        if (resultBean.isServiceResult()) {
            intent.setClass(getActivity(), CarAndSaftActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), resultBean.getResultInfo().equals("") ? "安全码不正确" : resultBean.getResultInfo()
                    , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initView(View view) {
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

        LogUtils.w("图片地址：" + Constant.SERVER_URL + Constant.userBean.getUserImg());

        if (null != Constant.userBean && null != Constant.userBean.getUserImg()) {

//            Glide.with(this).load(Constant.SERVER_URL + Constant.userBean.getUserImg())
//                    .transform(new FastBulrTransformation(getActivity(), 20,
//                            userHeadBackground.getMeasuredWidth(), userHeadBackground.getMeasuredHeight()))
//                    .into(userHeadBackground);
            Glide.with(this).load(Constant.SERVER_URL + Constant.userBean.getUserImg()).into(userHeadBackground).getSize(new SizeReadyCallback() {
                @Override
                public void onSizeReady(int width, int height) {
                    LogUtils.d("宽高比" + width + "..:::" + height);
                }
            });
            LogUtils.w("图片地址：" + Constant.SERVER_URL + Constant.userBean.getUserImg());
//            Glide.with(this).load(Constant.SERVER_URL + Constant.userBean.getUserImg()).into(userHeadRound);
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
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(data.getData(), projection, null, null, null);
                    if (cursor == null )
                        return;
                    if (cursor.moveToFirst()) {
                        photo_path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        LogUtils.w("图片地址:" + photo_path);
                        Bundle bundle = new Bundle();
                        bundle.putString("path",photo_path);
                        startActivityForResult(new Intent(getActivity(), CutImageActvity.class).putExtra("path",photo_path),
                                CUT_IMAGE_PAGE, bundle);
                    }
                    cursor.close();
                    break;
                case CUT_IMAGE_PAGE:
                    // TODO: 2016/6/11 图片上传
                    LogUtils.d("准备上传图片》》》》》》》》》》》》》》》》》》》》》》》》》");
                    String path = data.getStringExtra("path");
                    if (!path.equals("")) {
                        mUserInfoPresenter.updataUserImage(path);
                    } else {
                        Toast.makeText(getActivity(), "无法解析图片地址", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    }

    @Override
    public void onUpdateSuccess(String resultMessage) {
        Toast.makeText(getActivity(), resultMessage.equals("") ? "更新成功" : resultMessage,
                Toast.LENGTH_SHORT).show();

        Glide.with(this).load(Constant.SERVER_URL + Constant.userBean.getUserImg())
                .transform(new BlurTransformation(getActivity(), 5.0f))
                .into(userHeadBackground);
        LogUtils.w("图片地址：" + Constant.SERVER_URL + Constant.userBean.getUserImg());
        Glide.with(this).load(Constant.SERVER_URL + Constant.userBean.getUserImg()).into(userHeadRound);
    }


    @Override
    public void showResultError(int errorNo, String errorMag) {
        LogUtils.w("错误信息:" + errorMag + "错误码:" + errorNo);
        Toast.makeText(getActivity(), errorMag, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showResultSuccess(ResultBean resultBean) {
        if (resultBean.isServiceResult()) {
            intent.setClass(getActivity(), CarAndSaftActivity.class);
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
        if (null == Constant.userBean && null == Constant.userBean.getId()) {
            intent.setClass(getActivity(), LoginActivity.class);
        } else {
            intent.setClass(getActivity(), UserBaseActivity.class);
        }
        startActivity(intent);
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
                .inputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT)
                .input("请输入安全码", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        dialog.dismiss();
                        safePasswordInput = input;
                        LogUtils.d("输入的安全码：" + safePasswordInput);
                        mUserInfoPresenter.checkSafePassword(safePasswordInput);
                    }
                }).show();
    }

    /**
     * 去选择图片
     */
    private void toSelectPhoto() {
        //直接调用相册的图片
//        Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        //"android.intent.action.GET_CONTENT"
//        innerIntent.setType("image/*");
//        Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
//        startActivityForResult(intent, CHOOSE_PICTURE);
        this.startActivityForResult(intent, REQUEST_PHOTO);
    }
}
