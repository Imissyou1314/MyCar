package com.miss.imissyou.mycar.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.MessageBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.MessagePresenter;
import com.miss.imissyou.mycar.presenter.impl.MessagePresenterImpl;
import com.miss.imissyou.mycar.ui.MissDialog;
import com.miss.imissyou.mycar.ui.MissSwipDismissListView;
import com.miss.imissyou.mycar.ui.TitleFragment;
import com.miss.imissyou.mycar.ui.adapterutils.CommonAdapter;
import com.miss.imissyou.mycar.ui.adapterutils.ViewHolder;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.FindViewById;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.SPUtils;
import com.miss.imissyou.mycar.util.StringUtil;
import com.miss.imissyou.mycar.util.SystemUtils;
import com.miss.imissyou.mycar.util.ToastUtil;
import com.miss.imissyou.mycar.view.MessageView;
import com.miss.imissyou.mycar.view.fragment.StationMapViewFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * 消息页面
 * Created by Imissyou on 2016/5/2.
 */
public class MessageActivity extends BaseActivity implements MessageView {

    @FindViewById(id = R.id.message_title)
    private TitleFragment titleView;
    @FindViewById(id = R.id.message_allmessage_listView)
    private MissSwipDismissListView listView;          //消息列
    @FindViewById(id = R.id.message_over)
    FrameLayout frame;


    private MessagePresenter mMessagePresenter;
    List<MessageBean> messages = new ArrayList<MessageBean>();
    private MyMessageCastReceiver receiver;             //本地广播

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_message);

    }

    /**
     * 加载数据
     */
    @Override public void initData() {
        if (null == mMessagePresenter) {
            mMessagePresenter = new MessagePresenterImpl(this);
        }
        frame.setVisibility(View.GONE);
        SPUtils.init(this);
        if (SPUtils.getSp_set().getBoolean(Constant.MESSAGEALL, false)) {
            LogUtils.w("获取所有未用户信息");
            mMessagePresenter.getUserAllMessage();
        } else {
            LogUtils.w("获取用户所有未读信息");
            mMessagePresenter.getUserUnReadMessage();
        }
    }

    @Override public void addListeners() {

        titleView.setTitleText("我的信息");         //设置标题

        listView.setOnDismissCallback(new MissSwipDismissListView.OnDismissCallback() {
            @Override
            public void onDismiss(int dismissPosition) {
                LogUtils.d("删除第几项:" + dismissPosition);
                mMessagePresenter.deteleMessage(messages.get(dismissPosition).getId());
                removeMessage(dismissPosition);
            }
        });

        //动态注册广播
        receiver = new MyMessageCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("cn.jpush.android.intent.NOTIFICATION_RECEIVED");
        filter.addAction("cn.jpush.android.intent.NOTIFICATION_OPENED");
        filter.addCategory("com.miss.imissyou.mycar");
        this.registerReceiver(receiver,filter);
    }



    @Override
    public void showResultError(int errorNo, String errorMag) {
        String title ="警告";
        if (errorNo == 0) {
            title = "程序异常";
        } else if (errorNo == Constant.NETWORK_STATE){
            title = "网络异常";
        } else {
            title = "请求异常";
        }
        /**弹框提示用户*/
        MissDialog.Builder dialog = new MissDialog.Builder(this);
        dialog.setSingleButton(true)
                .setTitle(title)
                .setMessage(errorMag)
                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog.create().show();

    }

    @Override public void showResultSuccess(ResultBean resultBean) {
            this.messages.addAll(GsonUtils.getParams(resultBean, "message", MessageBean.class));
            if (messages.size() > 0) {
                setListData(messages);
            } else {
                ToastUtil.asLong("没有信息提示");
            }
    }

    /**
     * 加载ListView的数据
     * @param messages
     */
    private void setListData(final List<MessageBean> messages) {
        LogUtils.d("获取到的订单列表:" + GsonUtils.Instance().toJson(messages));

        //对数据进行倒叙
        Collections.reverse(messages);

        CommonAdapter adapter = new CommonAdapter<MessageBean>(this, messages,
                R.layout.item_message) {
            @Override public void convert(ViewHolder holder, final MessageBean messageBean) {
                if (messageBean.isState()) {
                    /**设置没点击过的背景色*/
                    holder.setBackGround(R.color.color_blue);
                }

                //设置到附近维修站
                if (messageBean.getType() == 2) {
                    holder.setText(R.id.message_item_stateTask,"附近维修站");
                    holder.setOnClickListener(R.id.message_item_stateTask, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goMapService(Constant.MAP_MAINTAIN);           //到附近维修站
                        }
                    });
                    //设置到附近加油站
                } else if(messageBean.getType() == 1){
                    holder.setText(R.id.message_item_stateTask,"附近加油站");
                    holder.setOnClickListener(R.id.message_item_stateTask, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goMapService(Constant.MAP_GASSTATION);                //到附近加油站
                        }
                    });
                }

                holder.setText(R.id.message_item_msg_text, "   " + messageBean.getContent());
                LogUtils.d("信息的时间==>" + messageBean.getCreateTime());
                holder.setText(R.id.message_item_time_text, messageBean.getCreateTime());
                holder.setText(R.id.message_item_title_text, messageBean.getTitle());
            }
        };
        listView.setAdapter(adapter);
    }

    /**
     * 到引导导航页面
     * @param mapType  引导类型
     */
    private void goMapService(String mapType) {
        frame.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        StationMapViewFragment fragment = new StationMapViewFragment();
        if (mapType.equals(Constant.MAP_GASSTATION)) {
            fragment.setType(Constant.MAP_GASSTATION);
        } else {
            fragment.setType(Constant.MAP_MAINTAIN);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.message_over,fragment).commit();
    }

    @Override public void showProgress() {

    }

    @Override public void hideProgress() {

    }



    @Override protected void onDestroy() {
        super.onDestroy();
        mMessagePresenter.detchView();
       if (null != receiver)
           this.unregisterReceiver(receiver);
    }

    @Override public void deleteSucces(String resultMessage) {
        LogUtils.d("删除成功");
        Toast.makeText(MessageActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateMessageList() {
        LogUtils.d("刷新Message列表");
    }

    /**
     * 删除信息
     * @param dismissPosition
     */
    private void removeMessage(int dismissPosition) {
        this.messages.remove(dismissPosition);
    }

    /**
     * 广播在活动中设置UI参数
     */
    public class MyMessageCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            LogUtils.w("MyMessageCastReceiver " + intent.getAction());

            //接收到Jpush后自动刷新页面
            if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                System.out.println("用户接收到Jpush通知");
                if (SystemUtils.isForeground(context,
                        "com.miss.imissyou.mycar.view.activity.MessageActivity")) {
                    LogUtils.d("只进Message行刷新页面====================>");
                    if (null != mMessagePresenter) {
                        mMessagePresenter.getUserAllMessage();
                    } else {
                        LogUtils.d("没初始化号===>数据为空");
                    }
                }
            }
        }
    }
}
