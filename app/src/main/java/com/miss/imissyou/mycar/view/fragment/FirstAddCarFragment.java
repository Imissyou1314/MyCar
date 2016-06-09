package com.miss.imissyou.mycar.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.view.activity.AddNewCarActivity;

/**
 * 没有车辆进入时显示的页面
 * Created by Imissyou on 2016/6/3.
 */
public class FirstAddCarFragment extends BaseFragment{

    private ImageView go_addBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_first_add_car,inflater, container, savedInstanceState);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    protected void initView(View view) {
        go_addBtn = (ImageView) view.findViewById(R.id.first_add_image_btn);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void addViewsListener() {
        go_addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_addNewCarPage();
            }
        });
    }

    /**
     * 跳转到添加新车的页面
     */
    private void go_addNewCarPage() {
        getActivity().startActivity(new Intent(getActivity(), AddNewCarActivity.class));
    }
}
