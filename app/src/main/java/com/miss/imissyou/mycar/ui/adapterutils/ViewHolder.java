package com.miss.imissyou.mycar.ui.adapterutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miss.imissyou.mycar.util.StringUtil;

/**
 * 通用的ViewHolder
 *
 * Created by Imissyou on 2016/3/21.
 */
public class ViewHolder {

    private int mPosition;
    private View mConverView;
    private SparseArray<View> mViews;

    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<>();
        mConverView = LayoutInflater.from(context).inflate(layoutId,parent,false);
        mConverView.setTag(this);
    }

    /**
     * 默认构造方法
     * @param context
     * @param converView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View converView, ViewGroup parent,
                                 int layoutId, int position) {
        if (null == converView ) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder holder = (ViewHolder) converView.getTag();
            return  holder;
        }
    }

    /**
     *
     * @return mConverView View
     */
    public View getmConverView() {
        return mConverView;
    }

    /**
     *
     * @return int
     */
    public int getmPosition() {
        return mPosition;
    }

    /**
     * 通过Id获取控件
     * @param viewId R.id.xxx
     * @param <T>  extends View
     * @return  T extends View
     */
    public <T extends View> T getView (int viewId) {
        View view = mViews.get(viewId);
        if (null == view) {
            view = mConverView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T)view;
    }

    /**
     * 通过ViewId 直接设值
     * @param viewId  R.id.xxxx
     * @param text   String
     * @return   this
     */
    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 通过ViewId 直接添加值
     * @param viewId  R.id.xxxx
     * @param text   String
     * @return   this
     */
    public ViewHolder addText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(tv.getText() + text);
        return this;
    }

    /**
     * 通过ViewId 直接替换值
     * @param viewId  R.id.xxxx
     * @param oldText String
     * @param newText   String
     * @return   this
     */
    public ViewHolder replaceText(int viewId, String oldText, String newText) {
        TextView tv = getView(viewId);
        if (newText != null && !newText.equals("")) {
            tv.setText(tv.getText().toString().replace(oldText, newText));
        }
        return this;
    }
    /**
     * 通过ViewId 直接设值
     * @param viewId  R.id.xxxx
     * @param bitmap   Bitmap
     * @return   this
     */
    public ViewHolder setImage(int viewId, Bitmap bitmap) {
        ImageView image = getView(viewId);
        image.setImageBitmap(bitmap);
        return this;
    }
    /**
     * 通过ViewId 直接设值
     * @param viewId  R.id.xxxx
     * @param resId   R.drown.xxx
     * @return   this
     */
    public ViewHolder setImage(int viewId, int resId) {
        ImageView image = getView(viewId);
        image.setImageResource(resId);
        return this;
    }

    /**
     * 通过ViewId设置图片背景
     * @param viewId
     * @param resId
     * @return
     */
    public ViewHolder setViewBackGround(int viewId, int resId){
        View view = getView(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    /**
     * 关于事件的
     */
    public ViewHolder setOnClickListener(int viewId, View.OnClickListener listener)
    {
        View view = getView(viewId);
        if (listener != null) {
            Log.d("ViewHold>>>>", "listener = null");
            view.setOnClickListener(listener);
        }

        return this;
    }

    /**
     * 设置触发监听
     * @param viewId
     * @param listener
     * @return
     */
    public ViewHolder setOnTouchListener(int viewId,
                                         View.OnTouchListener listener)
    {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    /**
     * 设置长按触发
     * @param viewId
     * @param listener
     * @return
     */
    public ViewHolder setOnLongClickListener(int viewId,
                                             View.OnLongClickListener listener)
    {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    /**
     * 设置Item的背景色
     * @param colorSrc
     * @return
     */
    public ViewHolder setBackGround(int colorSrc) {
        getmConverView().setBackgroundColor(colorSrc);
        return this;
    }
}
