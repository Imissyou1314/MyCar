package com.miss.imissyou.mycar.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.miss.imissyou.mycar.R;

/**
 * 违章查询
 * Created by Imissyou on 2016/4/23.
 */
public class WZCXFragment extends BaseFragment{


    private WebView mWebView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(R.layout.fragment_weizhangchaxun, inflater,
                container, savedInstanceState);
    }


    @Override protected void initView(View view) {
        mWebView = (WebView) view.findViewById(R.id.weizhangchaxun_webView);
    }

    @Override protected void initData() {
        mWebView.loadUrl("http://m.weizhangwang.com/");
    }

    @Override protected void addViewsListener() {

    }


    @Override public void onPause() {
        mWebView.onPause();
        super.onPause();
    }

    @Override public void onResume() {
        mWebView.onResume();
        super.onResume();
    }

    @Override public void onDestroy() {
        mWebView.destroy();
        mWebView = null;
        super.onDestroy();
    }
}
