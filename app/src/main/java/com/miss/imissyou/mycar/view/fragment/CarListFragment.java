package com.miss.imissyou.mycar.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.CarListPresenter;
import com.miss.imissyou.mycar.presenter.impl.CarListPresenterImpl;
import com.miss.imissyou.mycar.ui.adapterutils.CommonAdapter;
import com.miss.imissyou.mycar.ui.adapterutils.ViewHolder;
import com.miss.imissyou.mycar.ui.circleProgress.CircleProgress;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.DialogUtils;
import com.miss.imissyou.mycar.view.CarListFragmentView;
import com.miss.imissyou.mycar.view.activity.AddNewCarActivity;
import com.software.shell.fab.ActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * 车辆信息 车库
 * Created by Imissyou on 2016/3/22.
 */
public class CarListFragment extends BaseFragment implements CarListFragmentView {

    private CarListPresenter mCarListPresenter;     //

    private CircleProgress progress;                //加载中
    private ListView carInfoList;                   //列表视图
    private ActionButton addCarButton;              //悬浮按钮

    private List<CarInfoBean> cars = new ArrayList<CarInfoBean>();                //所有车辆
    private DialogUtils dialog;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(R.layout.fragment_car_list_action, inflater, container, savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        carInfoList = (ListView) view.findViewById(R.id.carlist_myCar_ListView);
        progress = (CircleProgress) view.findViewById(R.id.load_carList_progress);
        //设置悬浮按钮
        addCarButton = (ActionButton) view.findViewById(R.id.carlist_fab_button);

        addCarButton.setShadowColor(getActivity()
                .getResources()
                .getColor(R.color.button_shape_normal));
        addCarButton.setShadowRadius(5.0f);
        addCarButton.setShadowXOffset(3.5f);
        addCarButton.setShadowYOffset(3.0f);
        addCarButton.setImageResource(R.drawable.ic_add_back_30dp);
    }

    @Override
    protected void initData() {
        mCarListPresenter = new CarListPresenterImpl(this);

        LogUtils.w("加载车库信息:UserId == " + Constant.userBean.getId());
        if (null != Constant.userBean) {
            mCarListPresenter.loadServiceData(Constant.userBean.getId() + "");   //获取用户车辆
        }
    }

    @Override
    protected void addViewsListener() {

        final Intent intent = new Intent();
        intent.setClass(getActivity(), AddNewCarActivity.class);

        addCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到扫描页面
                getActivity().startActivity(intent);
            }
        });

        carInfoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转到具体车辆页面
                LogUtils.w("点击了那个" + position);
                android.support.v4.app.FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                CarInfoChangeStateFragment carInfoChangeFragment = new CarInfoChangeStateFragment();
                //跳转到车辆信息那个页面
                Bundle bundle = new Bundle();
                bundle.putLong(Constant.CAR_ID, cars.get(position).getId());
                bundle.putLong(Constant.USER_ID, cars.get(position).getUserId());
                carInfoChangeFragment.setArguments(bundle);
                fm.beginTransaction().replace(R.id.content_frame,
                        carInfoChangeFragment).commit();
            }
        });

    }

    @Override
    public void showProgress() {
        progress.setRadius(0.1f);
        progress.startAnim();
    }

    @Override
    public void hideProgress() {
        progress.stopAnim();
    }


    @Override
    public void showResultError(int errorNo, String errorMag) {
        if (errorNo == Constant.SHOW_ERROR_TOAST) {
            Toast.makeText(getActivity(), errorMag, Toast.LENGTH_LONG).show();
        } else {
            dialog = new DialogUtils(getActivity());
            dialog.errorMessage(errorMag, "获取车辆信息出错")
                    .show();
        }
    }

    @Override
    public void showResultSuccess(ResultBean resultBean) {

    }

    @Override
    public void showResultSuccess(List<CarInfoBean> resultBean) {
        this.cars = resultBean;


        carInfoList.setAdapter(new CommonAdapter<CarInfoBean>(getActivity(), resultBean,
                R.layout.item_carinfo_list) {
            @Override
            public void convert(final ViewHolder holder, CarInfoBean car) {
                                if (car.isCurrentCar()) {
                                   holder.setVisibility(R.id.car_list_item_car_isSelect);
                                }
                LogUtils.d("车牌号" + car.getPlateNumber());
                holder.addText(R.id.car_list_item_carbarnd, car.getPlateNumber());
                String oilNumber = ((int) car.getOil()) * 100 / ((int)car.getOilBox()) + "%";

                LogUtils.d("油量比" + oilNumber);
                holder.addText(R.id.car_list_item_carOil_text, oilNumber);
                holder.addText(R.id.car_list_item_carMalied_text,car.getMileage() + "");

                //加载图片
                LogUtils.d("加载图片地址：" + Constant.SERVER_URL + car.getMark());
               Glide.with(getActivity())
                       .load(Constant.SERVER_URL + car.getMark())
                       .into((ImageView) holder.getView(R.id.car_list_item_carImage));
                // TODO: 2016/6/4  待测试的页面
                /**设置背景色*/
                //int nowList = holder.getmPosition();
                //if (nowList % 4 == 0) {
                 //   holder.setViewBackGround(R.id.car_list_item_carBackground,R.color.color_gree_background);
               // } else if (nowList % 3 == 0) {
               //     holder.setViewBackGround(R.id.car_list_item_carBackground,R.color.color_yellow_background);
                //} else if(nowList % 2 == 0 ) {
                 //   holder.setViewBackGround(R.id.car_list_item_carBackground,R.color.color_red_background);
                //} else {
                //    holder.setViewBackGround(R.id.car_list_item_carBackground, R.color.color_blue_background);
               // }

               // if (!car.getMark().equals("")) {
                    //Glide.with(this).load(Constant.SERVER_URL + car.getMark()).into()
               // }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private String isGood(boolean check) {
        return check ? "好" : "坏";
    }
}
