package com.miss.imissyou.mycar.util;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;

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

}
