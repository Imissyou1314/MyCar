package com.miss.imissyou.mycar.util;

import android.content.Context;
import android.content.DialogInterface;

import com.miss.imissyou.mycar.ui.MissDialog;

/**
 * 弹框的封装
 * Created by Imissyou on 2016/5/2.
 */
public class DialogUtils {

    private MissDialog.Builder dialog;
    private String message;
    private String title;

    public DialogUtils(Context mContext) {
        if (dialog == null)
            dialog = new MissDialog.Builder(mContext);
    }

    /**
     * 单按钮显示错误信息
     * @return
     */
    public MissDialog errorMessage(String message, String title) {
        dialog.setMessage(message)
                .setTitle(title)
                .setSingleButton(true)
                .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return dialog.create();
    }
}
