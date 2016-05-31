package com.miss.imissyou.mycar.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Tip;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Imissyou on 2016/5/30.
 */
public class InputRouteFragment extends BaseFragment implements TextWatcher, View.OnClickListener, AdapterView.OnItemClickListener {

    private TextView goBack;                    //返回上一个页面
    private AutoCompleteTextView autoCompleteTextView;              //自动输入提示
    private ListView inputListView;                                     //获取输入提交的列表
    private String title;           //标题
    private String cityName;         //城市名

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_input_route, inflater, container, savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        inputListView = (ListView) view.findViewById(R.id.input_route_listView);
        autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.input_route_autoText);
        goBack = (TextView) view.findViewById(R.id.input_route_goBack);
    }

    @Override
    protected void initData() {
        title = getArguments().getString("title");
        cityName = getArguments().getString("cityName");
    }

    @Override
    protected void addViewsListener() {
        autoCompleteTextView.addTextChangedListener(this);
        goBack.setOnClickListener(this);
        inputListView.setOnItemClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = s.toString().trim();
        LogUtils.w("更改的信息提示是:" + newText);
        Inputtips inputTips = new Inputtips(getActivity(),
                new Inputtips.InputtipsListener() {

                    @Override
                    public void onGetInputtips(List<Tip> tipList, int rCode) {
                        if (rCode == 1000) {// 正确返回
                            LogUtils.w("放回来的数据是:" + tipList + "放回的代码是:" + rCode);
                            List<String> listString = new ArrayList<String>();
                            for (int i = 0; i < tipList.size(); i++) {
                                String district = tipList.get(i).getDistrict();
                                LogUtils.w(district);
                                String adcode = tipList.get(i).getAdcode();
                                LogUtils.w(adcode);
                                listString.add(tipList.get(i).getName());
                            }
                            ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
                                    getActivity().getApplicationContext(),
                                    R.layout.route_inputs, listString);
                            autoCompleteTextView.setAdapter(aAdapter);
                            aAdapter.notifyDataSetChanged();
                        } else {
                            LogUtils.w("返回的代码是:" + rCode);
                        }
                    }
                });

        try {
            // 第一个参数表示提示关键字，第二个参数默认代表全国，也可以为城市区号
//            inputTips.requestInputtips(newText, searchCityInput.getText().toString());
//            inputTips.setQuery(new InputtipsQuery(keyWord, cityName));
            inputTips.requestInputtips(newText, cityName);
        } catch (AMapException e) {
            LogUtils.d("错误" + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
