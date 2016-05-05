package com.miss.imissyou.mycar.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.MessageBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.MessagePresenter;
import com.miss.imissyou.mycar.presenter.impl.MessagePresenterImpl;
import com.miss.imissyou.mycar.ui.AnimatorView;
import com.miss.imissyou.mycar.ui.MissDialog;
import com.miss.imissyou.mycar.ui.adapterutils.CommonAdapter;
import com.miss.imissyou.mycar.ui.adapterutils.ViewHolder;
import com.miss.imissyou.mycar.ui.circleProgress.CircleProgress;
import com.miss.imissyou.mycar.util.FindViewById;
import com.miss.imissyou.mycar.view.MessageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息页面
 * Created by Imissyou on 2016/5/2.
 */
public class MessageActivity extends BaseActivity implements MessageView {

    @FindViewById(id = R.id.message_allmessage_listView)
    private ListView listView;          //消息列
    @FindViewById(id = R.id.laod_message_progress)
    private CircleProgress  progress;   //加载视图

    private MessagePresenter mMessagePresenter;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_message);
        initData();
    }

    /**
     * 加载数据
     */
    private void initData() {
        mMessagePresenter = new MessagePresenterImpl(this);
    }

    @Override public void addListeners() {

    }

    @Override public void showResultError(int errorNo, String errorMag) {

        /**
         * 弹框提示用户
         */
        MissDialog.Builder dialog = new MissDialog.Builder(this);
        dialog.setSingleButton(true)
                .setTitle("获取消息出错")
                .setMessage(errorMag)
                .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.create().show();


    }

    @Override public void showResultSuccess(ResultBean resultBean) {
        List<MessageBean> messages = new ArrayList<MessageBean>();
        CommonAdapter<MessageBean> adapter = new CommonAdapter<MessageBean>(this, messages, R.layout.item_message) {
            @Override public void convert(ViewHolder holder, final MessageBean messageBean) {
                holder.setText(R.id.message_item_msg_text, messageBean.getMessageMsg());
                holder.setText(R.id.message_item_time_text, messageBean.getMessageTime().toString());
                holder.setText(R.id.message_item_title_text, messageBean.getMessageTitle());

                holder.setOnClickListener(holder.getmPosition(), new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        messageBean.setRead(true);
                    }
                });
            }
        };
    }

    @Override public void showProgress() {
        progress.startAnim();
        progress.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override public void onLayoutChange(View v, int left, int top, int right,
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
}
