package com.miss.imissyou.mycar.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.view.activity.AddNewCarActivity;

/**
 *
 * 第一次进入添加新车的页面
 * Created by ImissYou on 2016/6/3.
 */
public class FirstAddNewCarFragment extends BaseFragment {

    private ImageButton addButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_first_add_car, inflater, container, savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        addButton = (ImageButton) view.findViewById(R.id.first_add_image_btn);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void addViewsListener() {
                      addButton.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              toAddcarInfoPage();
                          }
                      });
    }

    private void toAddcarInfoPage() {
        getActivity().startActivity(new Intent(getActivity(), AddNewCarActivity.class));
    }
}
