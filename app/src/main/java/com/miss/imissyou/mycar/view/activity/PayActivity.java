package com.miss.imissyou.mycar.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.OrderBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.OrderInfoPresenter;
import com.miss.imissyou.mycar.presenter.impl.OrderInfoPresenterImpl;
import com.miss.imissyou.mycar.ui.OnPasswordInputFinish;
import com.miss.imissyou.mycar.ui.PasswordView;
import com.miss.imissyou.mycar.ui.TitleFragment;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.ToastUtil;
import com.miss.imissyou.mycar.view.BackHandledInterface;
import com.miss.imissyou.mycar.view.OrderInfoView;
import com.miss.imissyou.mycar.view.fragment.BaseFragment;
import com.miss.imissyou.mycar.view.fragment.OrderInfoFragment;
import com.pingplusplus.android.Pingpp;
import com.pingplusplus.android.PingppLog;

import java.util.Map;

/**
 * 支付页面
 * Created by Imissyou on 2016/6/14.
 */
public class PayActivity extends BaseActivity implements View.OnClickListener, OrderInfoView,
        BackHandledInterface {

    private TitleFragment title;
    private LinearLayout  zhifubaoLinear;
    private LinearLayout weixiLinear;
    private ImageView zhifubaoImage;
    private ImageView weixiImage;

    private String channel = "";
    private Long ordersId;
    private OrderInfoPresenter mOrderInfoPresenter;

    private static final String url = Constant.SERVER_URL + "order/payOrder";

    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";
    private static final String CHANNEL_ALIPAY = "alipay";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initView();
        addListeners();
        initData();
    }

    /**
     * 加载View
     */
    private void initView() {
        title = (TitleFragment) findViewById(R.id.pay_title);
        zhifubaoLinear = (LinearLayout) findViewById(R.id.pay_zhifubao_linear);
        zhifubaoImage = (ImageView) findViewById(R.id.pay_zhifubao_image);
        weixiLinear = (LinearLayout) findViewById(R.id.pay_weixi_linear);
        weixiImage = (ImageView) findViewById(R.id.pay_weixi_image);
    }

    /**
     * 加载页面的数据
     */
    protected void initData() {
        title.setTitleText("订单支付");
        ordersId = getIntent().getLongExtra("orderId",0);
        mOrderInfoPresenter = new OrderInfoPresenterImpl(this);
    }

    /**
     * 添加页面控件的监听事件
     */
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
            params.put("orderId", ordersId + "");
            params.put("channel", channel);
            LogUtils.d("请求路径:" + url);
            RxVolley.post(url, params, callback);
        } else {
            LogUtils.e("Error ===============>定单没有ID");
        }
    }

    /**
     * 调用Ping++ 的SDK的回调构造函数
     */
    HttpCallback callback = new HttpCallback() {
        @Override
        public void onSuccess(String t) {
            LogUtils.d("t" + t);
            ResultBean resultBean = GsonUtils.getResultBean(t);
            if (resultBean.isServiceResult()) {
                goPayActivity(resultBean.getResultParm());
            } else {
                LogUtils.e("Error ===============>请求charge不成功");
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

        String charge = resultParm.get("charge") != null ?
                GsonUtils.Instance().toJson(resultParm.get("charge")) : "";

        LogUtils.d("charge==>" + charge);

        charge = charge.replace(".0","");

        LogUtils.d("Charge===>" + charge);
//        Intent intent = new Intent(this, PaymentActivity.class);
//        String packageName = getPackageName();
//        ComponentName componentName = new ComponentName(packageName,
//                packageName + ".wxapi.WXPayEntryActivity");
//        intent.setComponent(componentName);
//        intent.putExtra(PaymentActivity.EXTRA_CHARGE, charge);
        //启动到支付页面
//        startActivityForResult(intent, Pingpp.REQUEST_CODE_PAYMENT);
//        Pingpp.createPayment(PayActivity.this, charge);
        //TODO 添加弹框输入密码
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentview = inflater.inflate(R.layout.pop_input_password_dialog, null);

        contentview.setFocusable(true); // 这个很重要
        contentview.setFocusableInTouchMode(true);
        final PopupWindow popupWindow = new PopupWindow(contentview
                , LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);

        popupWindow.showAtLocation(contentview,  Gravity.BOTTOM, 0, 0);

        //TODO 添加输入完成监听
        ((PasswordView)((RelativeLayout)popupWindow.getContentView()).getChildAt(0))
                .setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish() {
                popupWindow.dismiss();
                //TODO更新订单状态
                ToastUtil.asLong("支付成功");
                mOrderInfoPresenter.updateOrderInfo(ordersId, 0);
            }
        });
    }

    /**
     * 跳转到定单详型页面
     * @param ordersId
     */
    private void toOrderInfoPage(Long ordersId) {
        OrderInfoFragment orderInfo = new OrderInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("orderId", ordersId);
        orderInfo.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.pay_activity_fragment, orderInfo)
                .commit();
    }

    /**
     * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。
     * 最终支付成功根据异步通知为准
     * @param requestCode 回执响应吗
     * @param resultCode  结果响应码
     * @param data  携带的回执信息
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        zhifubaoImage.setOnClickListener(PayActivity.this);
        weixiImage.setOnClickListener(PayActivity.this);
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                Log.d("miss", result);

                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                showMsg(result, errorMsg, extraMsg);
            }
        }
    }

    /**
     * 显示返回的信息
     * @param title 标题
     * @param msg1
     * @param msg2
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
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void callBackOderBean(OrderBean orderBean) {

    }

    @Override
    public void updateSuccess(ResultBean resultBean) {
        Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
        toOrderInfoPage(ordersId);
    }

    @Override
    public void updateFaile(Integer errorCode, String errorStr) {
        ToastUtil.asLong("支付不成功");
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

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {

    }
}
