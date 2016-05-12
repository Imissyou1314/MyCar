package com.miss.imissyou.mycar.util;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.ui.MissDialog;

import java.util.Map;

/**
 * Created by Imissyou on 2016/5/4.
 */
public class LinkService {

    private static RxVolley.Builder builder;

    public synchronized static RxVolley.Builder Instance() {
        if (builder == null) {
            builder = new RxVolley.Builder();
        }
        return builder;
    }

    /**
     * GET 方法获取请求
     *
     * @param url
     * @param callback
     */
    public static void get(String url, HttpCallback callback) {
        LogUtils.d("请求路径：" + url);
        builder.callback(callback)
                .httpMethod(RxVolley.Method.GET)
                .url(url)
                .timeout(6000)
                .encoding("utf-8")
                .shouldCache(true)
                .cacheTime(5)
                .doTask();
    }

    /**
     * GET 方法获取请求
     *
     * @param url
     * @param callback
     */
    public static void post(String url, HttpParams params, HttpCallback callback) {
        LogUtils.d("请求路径：" + url);
        LogUtils.d("请求参数:" + params.toString());
        builder.callback(callback)
                .httpMethod(RxVolley.Method.POST)
                .url(url)
                .contentType(RxVolley.ContentType.JSON)
                .params(params)
                .timeout(6000)
                .encoding("utf-8")
                .shouldCache(false)
                .doTask();
    }

    /**
     * 获取网络图片
     * @param context
     * @param url
     * @return
     */
    public static Bitmap getBitmap(final Context context, final String url) {
        String ImageURL = Constant.SERVER_URL + url;
        LogUtils.d("请求图片的地址:" + ImageURL);
        final Bitmap[] mBitmap = new Bitmap[]{};
        RxVolley.get(ImageURL, new HttpCallback() {
            @Override public void onSuccess(Map<String, String> headers, Bitmap bitmap) {
                if (bitmap != null) {
                    mBitmap[0] = bitmap;
                } else {
                    return;
                }
            }
            @Override public void onFailure(int errorNo, String strMsg) {
                new MissDialog.Builder(context)
                        .setTitle("请求失败")
                        .setMessage("网络异常")
                        .setSingleButton(true)
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });
        return mBitmap[0];
    }

}
