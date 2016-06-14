package com.miss.imissyou.mycar.view.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alipay.tscenter.biz.rpc.vkeydfp.result.BaseResult;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.ui.TitleFragment;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.FindViewById;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.ToastUtil;
import com.pingplusplus.android.PaymentActivity;
import com.pingplusplus.android.PingppLog;

import java.util.Map;

/**
 * 支付页面
 * Created by Imissyou on 2016/6/14.
 */
public class PayActivity extends BaseActivity implements View.OnClickListener {

    @FindViewById(id = R.id.pay_title)
    private TitleFragment title;
    @FindViewById(id = R.id.pay_zhifubao_linear)
    private LinearLayout  zhifubaoLinear;
    @FindViewById(id = R.id.pay_weixi_linear)
    private LinearLayout weixiLinear;
    @FindViewById(id = R.id.pay_zhifubao_image)
    private ImageView zhifubaoImage;
    @FindViewById(id = R.id.pay_weixi_image)
    private ImageView weixiImage;

    private String channel = "";
    private String ordersId = "";

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final String url = Constant.SERVER_URL + "order/pay";   //TODO 等待接口

    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";
    private static final String CHANNEL_ALIPAY = "alipay";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_pay);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void initData() {
        title.setTitleText("订单支付");
        Intent intent = getIntent();
        ordersId = intent.getStringExtra("orderId");
    }

    @Override
    public void addListeners() {
        zhifubaoLinear.setOnClickListener(this);
        zhifubaoImage.setOnClickListener(this);
        weixiLinear.setOnClickListener(this);
        weixiImage.setOnClickListener(this);
        PingppLog.DEBUG = true;             //开启日志
    }

    @Override
    public void onClick(View v) {
        //按键点击之后的禁用，防止重复点击
        weixiImage.setOnClickListener(null);
        zhifubaoImage.setOnClickListener(null);

        if (v.getId() == R.id.pay_zhifubao_image || v.getId() == R.id.pay_zhifubao_linear) {
            channel = CHANNEL_ALIPAY;

        } else if(v.getId() == R.id.pay_weixi_image || v.getId() == R.id.pay_weixi_linear) {
            channel = CHANNEL_WECHAT;
        } else {
            Toast.makeText(this, "请选择支付方式...", Toast.LENGTH_LONG).show();
            return ;
        }
        if (null != ordersId) {
        // TODO: 2016/6/14 等待下一步
            HttpParams params = new HttpParams();
            LogUtils.d("请求参数:" + ordersId + "::::" + channel);
            params.put("orderId", ordersId);
            params.put("channel", channel);

            LogUtils.d("请求路径:" + url);
            RxVolley.post(url, params, callback);
        } else {
            LogUtils.d("定单没有ID");
        }
    }

    /**
     * 调用Ping++ 的SDK
     */
    public void onDataReturn(String taskTag, BaseResult result, String json) {
        if(null==json){
            Toast.makeText(this, "支付失败...", Toast.LENGTH_LONG).show();
            showMsg("请求出错", "请检查URL", "URL无法获取charge");
            return;
        }
        /**
         * 获得服务端的charge ==>json，调用ping++ sdk。
         */
        int start = json.indexOf("charge");
        String charge =(String) json.subSequence(start + 8, json.length() -2);
        Log.v("test===>", charge);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PaymentActivity.EXTRA_CHARGE, charge);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    HttpCallback callback = new HttpCallback() {
        @Override
        public void onSuccess(String t) {
            LogUtils.d("t");
            ResultBean resultBean = GsonUtils.getResultBean(t);
            if (resultBean.isServiceResult()) {
                //TODO 获取charge
                goPayActivity(resultBean.getResultParm());
            } else {
                onFailure(0, resultBean.getResultInfo().equals("") ? "调用接口失败" : resultBean.getResultInfo());
            }
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {
            ToastUtil.asLong(strMsg);
        }
    };

    /**
     * 解析charge
     * @param resultParm
     */
    private void goPayActivity(Map<String, Object> resultParm) {
        String json = GsonUtils.Instance().toJson(resultParm.get("charge"));
        int start = json.indexOf("charge");
        String charge =(String) json.subSequence(start + 8, json.length() -2);
        Log.v("test===>", charge);

        Intent intent = new Intent(PayActivity.this, PaymentActivity.class);
        intent.putExtra(PaymentActivity.EXTRA_CHARGE, charge);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    /**
     * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。
     * 最终支付成功根据异步通知为准
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        zhifubaoImage.setOnClickListener(PayActivity.this);
        weixiImage.setOnClickListener(PayActivity.this);
        //支付页面返回处理
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                Log.d("miss", result);
                /* 返回值处理
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                showMsg(result, errorMsg, extraMsg);
            }
        }
    }

    /**
     * 显示返回的信息
     *
     * @return void
     */
    public void showMsg(String title, String msg1, String msg2) {
        String str = title;
        if (null !=msg1 && msg1.length() != 0) {
            str += "\n" + msg1;
        }
        if (null !=msg2 && msg2.length() != 0) {
            str += "\n" + msg2;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(str);
        builder.setTitle("提示");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO 下一步的执行页面
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


}
