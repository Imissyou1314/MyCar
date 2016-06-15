package com.miss.imissyou.mycar.view.activity;

import android.content.DialogInterface;
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
import com.miss.imissyou.mycar.util.ToastUtil;
import com.miss.imissyou.mycar.view.MessageView;
import com.miss.imissyou.mycar.view.fragment.StationMapViewFragment;

import java.util.ArrayList;
import java.util.List;

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
    //@FindViewById(id = R.id.laod_message_progress)
    //private CircleProgress progress;   //加载视图


    private MessagePresenter mMessagePresenter;
    List<MessageBean> messages = new ArrayList<MessageBean>();

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_message);
    }

    /**
     * 加载数据
     */
    @Override public void initData() {
        mMessagePresenter = new MessagePresenterImpl(this);
        frame.setVisibility(View.GONE);
        SPUtils.init(this);
        if (SPUtils.getSp_set().getBoolean(Constant.MESSAGEALL, false)) {
            LogUtils.w("获取所有未用户信息");
            mMessagePresenter.getUserAllMessage();
        } else {
            LogUtils.w("获取用户所有未读信息");
            mMessagePresenter.getUserUnReadMessage();
        }
        //changeStateToService();
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
        CommonAdapter adapter = new CommonAdapter<MessageBean>(this, messages,
                R.layout.item_message) {
            @Override public void convert(ViewHolder holder, final MessageBean messageBean) {
                if (messageBean.isState()) {
                    /**设置没点击过的背景色*/
                    holder.setBackGround(R.color.color_blue);
                }


                //// TODO: 2016/6/12 添加到附近加油站和维修站

                if (messageBean.getType() == 2) {
                    holder.setText(R.id.message_item_stateTask,"附近维修站");
                    holder.setOnClickListener(R.id.message_item_stateTask, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goMapService(Constant.MAP_MAINTAIN);           //到附近维修站
                        }
                    });
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
                LogUtils.d("信息的时间" + messageBean.getSystemData());
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
        Bundle bundle = new Bundle();
        if (mapType.equals(Constant.MAP_GASSTATION)) {
            bundle.putString("type", Constant.MAP_GASSTATION);
        } else {
            bundle.putString("type", Constant.MAP_MAINTAIN);
        }
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.message_over,fragment).commit();
    }

    /**
     * 改变状态
     */
    private void changeStateToService() {

        if (null != Constant.userBean && 0 !=  Constant.userBean.getId())
            mMessagePresenter.changeStateToService(Constant.userBean.getId());
    }

    @Override public void showProgress() {
//        progress.startAnim();
//        progress.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right,
//                                       int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                v.removeOnLayoutChangeListener(this);
//                AnimatorView.showView(progress);
//            }
//        });
    }

    @Override public void hideProgress() {
//        progress.stopAnim();
//        progress.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override public void onLayoutChange(View v, int left, int top, int right, int bottom,
//                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                v.removeOnLayoutChangeListener(this);
//                AnimatorView.disShowView(progress);
//            }
//        });
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        mMessagePresenter.detchView();
    }

    @Override public void deleteSucces(String resultMessage) {
        LogUtils.d("删除成功");
        Toast.makeText(MessageActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 删除信息
     * @param dismissPosition
     */
    private void removeMessage(int dismissPosition) {
        this.messages.remove(dismissPosition);
    }
}
