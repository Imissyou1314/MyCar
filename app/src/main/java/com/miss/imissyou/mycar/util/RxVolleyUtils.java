package com.miss.imissyou.mycar.util;

import android.util.Log;

import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import com.kymjs.rxvolley.RxVolley;
import com.lidroid.xutils.util.LogUtils;

import java.util.Map;

/**
 * RxVolley 的工具类
 * Created by Imissyou on 2016/6/17.
 */
public class RxVolleyUtils {


    private static RxVolleyUtils rxVolleyUtils;

    private  HttpParams params;
    private  String url;
    private  HttpCallback callback;

    /**
     * 单例
     * @return RxVolleyUtils
     */
    public static RxVolleyUtils getInstance() {
        return null == rxVolleyUtils? new RxVolleyUtils() : rxVolleyUtils;
    }

    private RxVolleyUtils(){

    }


    /**
     *带缓存的Get请求
     * @param url  请求路径
     * @param params  请求参数
     * @param callback  回调函数
     */
    public void hasCacheget(String url, HttpParams params, HttpCallback callback) {

        LogUtils.d("请求路径:===>" + url);

        if (null == params) {
            params = new HttpParams();
        }
        this.url = url;
        this.params = params;
        this.callback = callback;

        //设置Cookie
        params.putHeaders("cookie",Constant.COOKIE);

            new RxVolley
                    .Builder()
                    .params(params)
                    .url(url)
                    .httpMethod(RxVolley.Method.GET)
                    .callback(callback)
                    .shouldCache(true)
                    .timeout(5000)
                    .doTask();
    }


    /**
     *不带缓存的Get请求
     * @param url  请求路径
     * @param params  请求参数
     * @param callback  回调函数
     */
    public void get(String url, HttpParams params, HttpCallback callback) {

        LogUtils.d("请求路径:===>" + url);

        if (null == params) {
            params = new HttpParams();
        }
        this.url = url;
        this.params = params;
        this.callback = callback;

        //设置Cookie
        params.putHeaders("cookie",Constant.COOKIE);
        new RxVolley
                .Builder()
                .params(params)
                .url(url)
                .httpMethod(RxVolley.Method.GET)
                .callback(callback)
                .shouldCache(false)
                .timeout(5000)
                .doTask();
    }


    /**
     *Post请求
     * @param url  请求路径
     * @param params  请求参数
     * @param callback  回调函数
     */
    public void post(String url, HttpParams params, HttpCallback callback) {

        LogUtils.d("请求路径:===>" + url);

        if (null == params) {
            params = new HttpParams();
        }
        this.url = url;
        this.params = params;
        this.callback = callback;

        //设置Cookie
        params.putHeaders("cookie",Constant.COOKIE);
        new RxVolley
                .Builder()
                .params(params)
                .httpMethod(RxVolley.Method.POST)
                .url(url)
                .callback(callback)
                .shouldCache(true)
                .timeout(5000)
                .doTask();
    }

    /**
     * 重新登录
     * 刷新Cookie
     * 重新请求
     */
    public void restartLogin() {
        String loginurl = Constant.SERVER_URL + "user/doLogin";
        HttpParams loginparams = new HttpParams();
        if (null != Constant.userBean && null != Constant.userBean.getId())
        params.put("loginid",Constant.userBean.getLoginid());
        params.put("password",Constant.userBean.getPassword());

        HttpCallback logincallback = new HttpCallback() {
            @Override
            public void onSuccess(Map<String, String> headers, byte[] t) {
                Constant.COOKIE = headers.get("cookie");
//                重新请求
                Log.d("RxVolleyTools===>resute", "重新请求" + url);
                post(url,params,callback);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
            }
        };
        Log.d("RxVolleyTools==>doLogin", "刷新Cookie");
        post(loginurl, loginparams, logincallback);
    }
}
