package com.miss.imissyou.mycar.util.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miss.imissyou.mycar.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Imissyou on 2016/8/22.
 */
public class GrideViewAdpter extends BaseAdapter {

    private Context mContext;
    private List mListValue;        //数据

    //默认构造函数
    public GrideViewAdpter(Context context, List listVlave){
        this.mContext = context;
        this.mListValue = listVlave;
    }
    @Override
    public int getCount() {
        return mListValue.size();
    }

    @Override
    public Object getItem(int position) {
        return mListValue.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.item_gride, null);
        Map<String, String> map = (Map<String, String>) mListValue.get(position);
        TextView text = (TextView) view.findViewById(R.id.btn_keys);
        text.setText(map.get("name").toString());
        return view;
    }
}
