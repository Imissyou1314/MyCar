package com.miss.imissyou.mycar.util;

import android.widget.Toast;

/**
 * Toast 工具类
 * Created by Imissyou on 2016/3/23.
 */
public class ToastUtil {

    /**
     * 短时间
     * @param msg   打印的信息
     */
    public static void asShort(String msg) {
        if (null != msg && null != MissApplication.getContext())
            Toast.makeText(MissApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间
     * @param msg  打印的信息
     */
    public static void asLong(String msg) {
        if (null != msg && null != MissApplication.getContext())
            Toast.makeText(MissApplication.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
