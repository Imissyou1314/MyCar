package com.miss.imissyou.mycar.util;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.EditText;

import com.lidroid.xutils.util.LogUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * 字符串的工具类
 * Created by Imissyou on 2016/3/27.
 */
public class StringUtil {




    private StringUtil(){}

    /**
     * 获取Edit输入的值
     * @param inputView  EditText
     * @return  String  null
     */
    public static String getEditInput(EditText inputView) {
        if (null != inputView) {
            return isEmpty(inputView.getText().toString()) ?
                    inputView.getText().toString() : "";
        }
        return null;
    }

    /**
     * 判断是字符串是否为空
     * @param str  String
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return str.isEmpty() || str.equals("") ? false : true;
    }

    /**
     * int 转时间
     * @param time
     * @param template  "mm:ss"
     * @return  时间串
     */
    public static String timeToString(int time,String template) {
        if (0 >= time)
            return null;
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(template);
        return format.format(date);
    }

    /**
     * MD5 加密
     * @param str 待加密的字符串
     * @return  加密后的字符串
     */
    public static String strToMD5(String str) {
        MessageDigest md5 = null;

        try{
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            LogUtils.e("MD5加密失败::::" + str, e);
            e.printStackTrace();
            return "";
        }
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i =0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append(0);
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 可逆的加密算法
     * @param str
     * @return
     */
    public static String encryptMd5(String str) {
        char[] a = str.toCharArray();
        for (int i = 0; i < a.length; i ++ ) {
            a[i] = (char) (a[i] ^ '1');
        }
        return new String(a);
    }

    /**
     * 通过byte[] 转UTF-8字符串
     * @param t
     * @return
     */
    public static String bytesToString(byte[] t) {
        String str = null;
        try {
            str = new String(t,"utf-8");
        } catch (UnsupportedEncodingException e) {
            LogUtils.e("String转型出错");
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 检查用户是否登录
     * @return
     */
    public static boolean checkUserIsLogin() {
        return null != Constant.userBean.getId() ? true : false;
    }

    /**
     * View转成Biimap
     * @param view  View视图
     * @return bitmap
     */
    public static Bitmap convertViewToBitmap(View view) {

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.buildDrawingCache();

        Bitmap bitmap = view.getDrawingCache();

        return bitmap;

    }
}
