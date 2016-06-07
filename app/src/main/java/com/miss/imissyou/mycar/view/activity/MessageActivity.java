package com.miss.imissyou.mycar.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;


import com.lidroid.xutils.util.LogUtils;
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
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.FindViewById;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.SPUtils;
import com.miss.imissyou.mycar.util.ToastUtil;
import com.miss.imissyou.mycar.view.MessageView;

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

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_message);
        initData();
    }

    /**
     * 加载数据
     */
    @Override public void initData() {
        mMessagePresenter = new MessagePresenterImpl(this);
        SPUtils.init(this);

        if (SPUtils.getSp_set().getBoolean(Constant.MESSAGEALL, false)) {
            mMessagePresenter.getUserAllMessage();
        } else {
            mMessagePresenter.getUserUnReadMessage();
        }
        //changeStateToService();
    }

    @Override public void addListeners() {

        listView.setOnDismissCallback(new MissSwipDismissListView.OnDismissCallback() {
            @Override
            public void onDismiss(int dismissPosition) {
                LogUtils.d("删除第几项:" + dismissPosition);
                mMessagePresenter.deteleMessage(messages.get(dismissPosition).getId());
                removeMessage(dismissPosition);
            }
        });
    }

    private void removeMessage(int dismissPosition) {
        this.messages.remove(dismissPosition);
    }

    @Override
    public void showResultError(int errorNo, String errorMag) {
        String title ="警告";
        if (errorNo == 0) {
            title = "程序异常";
        } else if (errorNo == Constant.NETWORK_STATE){
            title = "网络异常";
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
    private void setListData(List<MessageBean> messages) {
        CommonAdapter adapter = new CommonAdapter<MessageBean>(this, messages,
                R.layout.item_message) {
            @Override public void convert(ViewHolder holder, final MessageBean messageBean) {
                if (messageBean.isState()) {
                    /**设置没点击过的背景色*/
                    holder.setBackGround(R.color.color_blue);
                }
                holder.setText(R.id.message_item_msg_text, messageBean.getContent());
                holder.setText(R.id.message_item_time_text, messageBean.getSystemData());
                holder.setText(R.id.message_item_title_text, messageBean.getMessageTitle());
            }
        };
        listView.setAdapter(adapter);
    }

    private void changeStateToService() {

        if (null != Constant.userBean && 0 !=  Constant.userBean.getId())
            mMessagePresenter.changeStateToService(Constant.userBean.getId());
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
