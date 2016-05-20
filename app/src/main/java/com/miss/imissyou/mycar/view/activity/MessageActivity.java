package com.miss.imissyou.mycar.view.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;


import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.MessageBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.MessagePresenter;
import com.miss.imissyou.mycar.presenter.impl.MessagePresenterImpl;
import com.miss.imissyou.mycar.ui.AnimatorView;
import com.miss.imissyou.mycar.ui.MissDialog;
import com.miss.imissyou.mycar.ui.MissSwipDismissListView;
import com.miss.imissyou.mycar.ui.adapterutils.CommonAdapter;
import com.miss.imissyou.mycar.ui.adapterutils.ViewHolder;
import com.miss.imissyou.mycar.ui.circleProgress.CircleProgress;
import com.miss.imissyou.mycar.util.FindViewById;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.ToastUtil;
import com.miss.imissyou.mycar.view.MessageView;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息页面
 * Created by Imissyou on 2016/5/2.
 */
public class MessageActivity extends BaseActivity implements MessageView {

    @FindViewById(id = R.id.message_allmessage_listView)
    private MissSwipDismissListView listView;          //消息列
    @FindViewById(id = R.id.laod_message_progress)
    private CircleProgress progress;   //加载视图

    private MessagePresenter mMessagePresenter;
    List<MessageBean> messages = new ArrayList<MessageBean>();

    @SuppressLint("MissingSuperCall")
    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_message);
        initData();
    }

    /**
     * 加载数据
     */
    @Override public void initData() {
        mMessagePresenter = new MessagePresenterImpl(this);
        mMessagePresenter.loadServiceData(null);
    }

    @Override public void addListeners() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO
            }
        });

        listView.setOnDismissCallback(new MissSwipDismissListView.OnDismissCallback() {
            @Override
            public void onDismiss(int dismissPosition) {
                messages.remove(dismissPosition);

                mMessagePresenter.deteleMessage(messages.get(dismissPosition).getId());

                setListData(messages);

            }
        });
    }

    @Override
    public void showResultError(int errorNo, String errorMag) {
        /**弹框提示用户*/
        MissDialog.Builder dialog = new MissDialog.Builder(this);
        dialog.setSingleButton(true)
                .setTitle("获取消息出错")
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

        this.messages = GsonUtils.getParams(resultBean, "message", MessageBean[].class);
        if (messages.size() > 0) {
           setListData(messages);
        } else {
            showResultError(0, "没有信息");
        }
    }

    /**
     * 加载ListView的数据
     * @param messages
     */
    private void setListData(List<MessageBean> messages) {
        CommonAdapter adapter = new CommonAdapter<MessageBean>(this, messages,
                R.layout.item_message) {
            @Override public void convert(ViewHolder holder, final MessageBean messageBean) {
                holder.setText(R.id.message_item_msg_text, messageBean.getMessageMsg());
                holder.setText(R.id.message_item_time_text, messageBean.getMessageTime().toString());
                holder.setText(R.id.message_item_title_text, messageBean.getMessageTitle());

                holder.setOnClickListener(holder.getmPosition(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        messageBean.setRead(true);
                    }
                });
            }
        };
        listView.setAdapter(adapter);
    }

    @Override public void showProgress() {
        progress.startAnim();
        progress.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right,
                                       int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                AnimatorView.showView(progress);
            }
        });
    }

    @Override public void hideProgress() {
        progress.stopAnim();
        progress.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                AnimatorView.disShowView(progress);
            }
        });
    }

    @Override protected void onDestroy() {
        mMessagePresenter.detchView();
        super.onDestroy();
    }

    @Override public void deleteSucces(String resultMessage) {
        ToastUtil.asShort(resultMessage);
    }
}
