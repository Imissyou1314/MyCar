package com.miss.imissyou.mycar.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.miss.imissyou.mycar.R;

/**
 * 选择导航路线
 * Created by ImissYou on 2016/5/30.
 */
public class RouteSelectFragment extends BaseFragment implements View.OnClickListener {


    private TextView startRouteInput;           //输入起点路径
    private TextView endRouteInput;             //输入终点路径
    private ImageButton changeBtn;                    //改变起点和终点路径
    private TextView goBack;                      //返回上一页

    private final int StartTag = 1;                       //起点的标志
    private final int EndTag = 0;                       //终点标志

    private String startPlace;
    private String endPlace;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_navi_route, inflater, container, savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        startRouteInput = (TextView) view.findViewById(R.id.select_route_start_input);
        endRouteInput = (TextView) view.findViewById(R.id.select_route_end_input);

        changeBtn = (ImageButton) view.findViewById(R.id.select_route_changeSeclet);
        goBack = (TextView) view.findViewById(R.id.select_route_go_back);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void addViewsListener() {
        changeBtn.setOnClickListener(this);
        goBack.setOnClickListener(this);
        startRouteInput.setOnClickListener(this);
        endRouteInput.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_route_changeSeclet:
                changeInput();
                break;
            case R.id.select_route_end_input:
                toInputFragment(EndTag);
                break;
            case R.id.select_route_start_input:
                toInputFragment(StartTag);
                break;
            case R.id.select_route_go_back:
                backToFragment();
                break;
        }
    }

    /**
     * 放回上一个页面
     */
    private void backToFragment() {
        getActivity().getSupportFragmentManager()
                .beginTransaction().remove(this).commit();
    }

    /**
     * 跳转到地址输入Fragment
     *
     * @param tag
     */
    private void toInputFragment(int tag) {
        InputRouteFragment inputRouteFragment = new InputRouteFragment();
        Bundle bundle = new Bundle();
        String title = "";
        if (tag == 0) {
            title = "终点";
        } else {
            title = "起点";
        }
        bundle.putString("title", title);
        inputRouteFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_frame, inputRouteFragment).commit();
    }

    /**
     * 装换起点和终点
     */
    private void changeInput() {

        //TODO
        String tmpStr = startPlace;
        startPlace = endPlace;
        endPlace = tmpStr;
    }
}
