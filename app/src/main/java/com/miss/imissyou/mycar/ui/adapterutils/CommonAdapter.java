package com.miss.imissyou.mycar.ui.adapterutils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 *
 * 通用Adapter
 * Created by Imissyou on 2016/3/21.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int layoutId;

    /**
     * 默认构造方法
     * @param context
     * @param layoutId R.layout.xxx 对应listView的
     * @param datas  List<T> 泛型list数据接口
     */
    public CommonAdapter(Context context, List<T> datas,int layoutId) {
        this.mDatas = datas;
        this.mContext = context;
        this.layoutId = layoutId;
        mInflater = LayoutInflater.from(context);
    }

    @Override public int getCount() {
        return mDatas.size();
    }

    @Override public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder(mContext, parent, layoutId, position);
        convert(holder, getItem(position));
        return holder.getmConverView();
    }

    public abstract void convert(ViewHolder holder, T t);
}
