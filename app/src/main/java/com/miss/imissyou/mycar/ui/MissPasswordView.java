package com.miss.imissyou.mycar.ui;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.util.adapter.GrideViewAdpter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Imissyou on 2016/8/21.
 */
public class MissPasswordView extends RelativeLayout implements View.OnClickListener {
    Context context;

    private String strPassword;     //输入的密码
    private TextView[] tvList;      //用数组保存6个TextView，为什么用数组？
    //因为就6个输入框不会变了，用数组内存申请固定空间，比List省空间（自己认为）
    private GridView gridView;    //用GrideView布局键盘，其实并不是真正的键盘，只是模拟键盘的功能
    private ArrayList<Map<String, String>> valueList;    //有人可能有疑问，为何这里不用数组了？
    //因为要用Adapter中适配，用数组不能往adapter中填充

    private TextView text_00, text_01,
            text_02, text_03,text_04,
            text_05,text_06, text_07,
            text_08, text_09,text_del;

    private ImageView imgCancel;
    private TextView tvForget;
    private int currentIndex = -1;    //用于记录当前输入密码格位置

    public MissPasswordView(Context context) {
        this(context, null);
    }

    public MissPasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View view = View.inflate(context, R.layout.pop_input_password, null);

        tvList = new TextView[6];

        tvList[0] = (TextView) view.findViewById(R.id.tv_pass1);
        tvList[1] = (TextView) view.findViewById(R.id.tv_pass2);
        tvList[2] = (TextView) view.findViewById(R.id.tv_pass3);
        tvList[3] = (TextView) view.findViewById(R.id.tv_pass4);
        tvList[4] = (TextView) view.findViewById(R.id.tv_pass5);
        tvList[5] = (TextView) view.findViewById(R.id.tv_pass6);

        text_00 = (TextView) view.findViewById(R.id.password_input_00);

        text_01 = (TextView) view.findViewById(R.id.password_input_01);
        text_02 = (TextView) view.findViewById(R.id.password_input_02);
        text_03 = (TextView) view.findViewById(R.id.password_input_03);
        text_04 = (TextView) view.findViewById(R.id.password_input_04);
        text_05 = (TextView) view.findViewById(R.id.password_input_05);
        text_06 = (TextView) view.findViewById(R.id.password_input_06);
        text_07 = (TextView) view.findViewById(R.id.password_input_07);
        text_08 = (TextView) view.findViewById(R.id.password_input_08);
        text_09 = (TextView) view.findViewById(R.id.password_input_09);
        text_del = (TextView) view.findViewById(R.id.password_input_del);

        text_00.setOnClickListener(this);
        text_01.setOnClickListener(this);
        text_02.setOnClickListener(this);
        text_03.setOnClickListener(this);
        text_04.setOnClickListener(this);
        text_05.setOnClickListener(this);
        text_06.setOnClickListener(this);
        text_07.setOnClickListener(this);
        text_08.setOnClickListener(this);
        text_09.setOnClickListener(this);
        text_del.setOnClickListener(this);

        addView(view);      //必须要，不然不显示控件
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.password_input_del:
                tvList[currentIndex--].setText("");
                break;
            default:
                TextView text = (TextView) v;
                String input = text.getText().toString();
                Integer password = Integer.parseInt(input);

        }
    }

    private void setView() {


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 11 && position != 9) {    //点击0~9按钮
                    if (currentIndex >= -1 && currentIndex < 5) {      //判断输入位置————要小心数组越界
                        tvList[++currentIndex].setText(valueList.get(position).get("name"));
                    }
                } else {
                    if (position == 11) {      //点击退格键
                        if (currentIndex - 1 >= -1) {      //判断是否删除完毕————要小心数组越界
                            tvList[currentIndex--].setText("");
                        }
                    }
                }
            }
        });
    }

    //设置监听方法，在第6位输入完成后触发
    public void setOnFinishInput(final OnPasswordInputFinish pass) {
        tvList[5].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    strPassword = "";     //每次触发都要先将strPassword置空，再重新获取，避免由于输入删除再输入造成混乱
                    for (int i = 0; i < 6; i++) {
                        strPassword += tvList[i].getText().toString().trim();
                    }
                    pass.inputFinish();    //接口中要实现的方法，完成密码输入完成后的响应逻辑
                }
            }
        });
    }

    /* 获取输入的密码 */
    public String getStrPassword() {
        return strPassword;
    }

    /* 暴露取消支付的按钮，可以灵活改变响应 */
    public ImageView getCancelImageView() {
        return imgCancel;
    }

    /* 暴露忘记密码的按钮，可以灵活改变响应 */
    public TextView getForgetTextView() {
        return tvForget;
    }
}
