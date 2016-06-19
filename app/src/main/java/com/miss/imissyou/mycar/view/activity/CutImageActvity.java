package com.miss.imissyou.mycar.view.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.edmodo.cropper.CropImageView;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.UserInfoPresenter;
import com.miss.imissyou.mycar.presenter.impl.UserInfoPresenterImpl;
import com.miss.imissyou.mycar.ui.TitleFragment;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.FindViewById;
import com.miss.imissyou.mycar.view.UserInfoView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Imissyou on 2016/6/15.
 */
public class CutImageActvity extends BaseActivity implements UserInfoView {

    @FindViewById(id = R.id.cut_image_title)
    private TitleFragment title;
    @FindViewById(id = R.id.cut_image_crop)
    private CropImageView cropImageView;
    @FindViewById(id = R.id.cut_image_sumbit)
    private Button sumbit;

    private String path;            //图片地址
    private String formPath;         //获取到的图片地址
    private UserInfoPresenter mUserInfoPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_cut_imge);
        title.setTitleText("裁剪图片");
    }

    @Override
    protected void initData() {
        formPath =  getIntent().getStringExtra("path");
        mUserInfoPresenter = new UserInfoPresenterImpl(this);
        LogUtils.d("获取到的图片地址:" + formPath);
        if (null != formPath) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(formPath);
            } catch (FileNotFoundException e) {
                LogUtils.d("文件不存在");
            }

            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            cropImageView.setImageBitmap(bitmap);
        } else {
            LogUtils.d("获取不到图片");
        }
    }

    @Override
    public void addListeners() {
        sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumbit.setEnabled(false);
                sumbit.setBackgroundColor(R.color.md_btn_selected);
                setOnResult();
            }
        });
    }

    /**
     * 返回信息
     */
    private void setOnResult() {

        final Bitmap croppedImage = cropImageView.getCroppedImage();
        String path = saveBitmap(croppedImage);
        if (null != path) {
            mUserInfoPresenter.updataUserImage(path);
        }
    }

    /**
     * 保存上传图片
     * @param bitmap
     * @return
     */
    private String saveBitmap(Bitmap bitmap) {
        LogUtils.d("保存图片");
        //用ID作为图片保存
        File imageFir = new File(Environment.getExternalStorageDirectory(), Constant.userBean.getId() + "");

        if (!imageFir.exists()) {
            imageFir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(imageFir, fileName);
        String path = file.getPath();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            LogUtils.d("保存图片出错" + e.toString());
            return null;
        } catch (IOException e) {
            LogUtils.d("保存图片出错" + e.toString());
            return null;
        }
        LogUtils.d("文件名:" + path);
        return path;
    }

    @Override
    public void onUpdateSuccess(String resultMessage) {
        LogUtils.d("更新成功" + resultMessage);
        finish();
    }

    @Override
    public void showSafePasswordSucess(ResultBean resultBean) {

    }

    @Override
    public void showResultError(int errorNo, String errorMag) {

    }

    @Override
    public void showResultSuccess(ResultBean resultBean) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
