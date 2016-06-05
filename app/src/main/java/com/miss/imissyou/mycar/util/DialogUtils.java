package com.miss.imissyou.mycar.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.miss.imissyou.mycar.ui.MissDialog;

/**
 * 弹框的封装
 * Created by Imissyou on 2016/5/2.
 */
public class DialogUtils {

    private MissDialog.Builder dialog;
    private String message;
    private String title;
    private Context mContext;

    public DialogUtils(Context mContext) {
        this.mContext = mContext;
        if (dialog == null)
            dialog = new MissDialog.Builder(mContext);
    }

    /**
     * 单按钮显示错误信息
     * @return
     */
    public MissDialog errorMessage(String message, String title) {

        if (title == null  || title.equals("") )
            title = "提示";
        if (message.equals("") || message == null)
            message = "异常退出";

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

    public void onDestroy() {
        if (null != dialog) {
            dialog =null;
        }
    }

    public MissDialog showSucces(String message, String title, final Class<?> cls) {
        if (title == null  || title.equals("") )
        title = "提示";
        if (message.equals("") || message == null)
            message = "异常退出";

        dialog.setMessage(message)
                .setTitle(title)
                .setSingleButton(true)
                .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, cls);
                        mContext.startActivity(intent);
                        dialog.dismiss();
                    }
                });
        return dialog.create();
    }
}
