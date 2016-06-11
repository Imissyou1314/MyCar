package com.miss.imissyou.mycar.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.miss.imissyou.mycar.R;

/**
 * 底部弹框提示
 * Created by Administrator on 2016-06-11.
 */
public class MissPopWindows extends PopupWindow {

    private Button btn_delect, btn_secanl;

    private View mMenuView;

    public MissPopWindows(Context context, View.OnClickListener clickListener) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMenuView = inflater.inflate(R.layout.aler_dialog,null);

        btn_secanl = (Button) mMenuView.findViewById(R.id.btn_pop_seclen);
        btn_delect = (Button) mMenuView.findViewById(R.id.btn_pop_delect);

        btn_secanl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_delect.setOnClickListener(clickListener);

        this.setContentView(mMenuView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.popwindow_anim_style);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);

        /**添加监听事件，如果点击不是弹框内，就隐藏弹框*/
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                View view = mMenuView.findViewById(R.layout.aler_dialog);
                if (null == view) {
                    dismiss();
                } else {
                    int height = mMenuView.findViewById(R.layout.aler_dialog).getTop();
                    int y = (int) event.getY();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (y < height) {
                            dismiss();
                        }
                    }
                }
                return true;
            }
        });
    }

    /**
     * 给按钮添加监听事件
     * @param clickListener 监听事件
     */
    public void setDelectOnListener(View.OnClickListener clickListener) {
        btn_delect.setOnClickListener(clickListener);
    }
}
