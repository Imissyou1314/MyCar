package com.miss.imissyou.mycar.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.bean.UserBean;
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
import java.util.Map;


/**
 * 车辆信息 车库
 * Created by Imissyou on 2016/3/22.
 */
public class CarListFragment extends BaseFragment implements CarListFragmentView{

    private CarListPresenter mCarListPresenter;     //

    private CircleProgress progress;                //加载中
    private ListView carInfoList;                   //列表视图
    private ActionButton addCarButton;              //悬浮按钮

    private List<CarInfoBean> cars = new ArrayList<CarInfoBean>();                //所有车辆
    private DialogUtils dialog;

    @Override public View onCreateView(LayoutInflater inflater,
                                       ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(R.layout.fragment_car_list_action,inflater,container,savedInstanceState);
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

    @Override protected void initData() {
        mCarListPresenter = new CarListPresenterImpl(this);
        LogUtils.d("加载车库信息:UserId == " + Constant.userBean.getId());
        if (null != Constant.userBean) {
            mCarListPresenter.loadServiceData(Constant.userBean.getId());   //获取用户车辆
        }
    }

    @Override protected void addViewsListener() {

        final Intent intent = new Intent();
        intent.setClass(getActivity(), AddNewCarActivity.class);

        addCarButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                //跳转到扫描页面
                getActivity().startActivity(intent);
            }
        });

        carInfoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转到具体车辆页面
                LogUtils.d("点击了那个" + position);
                android.support.v4.app.FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                CarInfoFragment carInfoFragment = new CarInfoFragment();
                //跳转到车辆信息那个页面
                Bundle bundle = new Bundle();
                bundle.putString(Constant.CAR_ID,cars.get(position).getId());
                bundle.putString(Constant.USER_ID, cars.get(position).getUserId() + "");
                carInfoFragment.setArguments(bundle);
                fm.beginTransaction().replace(R.id.content_frame, carInfoFragment).commit();
            }
        });

    }

    @Override public void showProgress() {
        progress.setRadius(0.1f);
        progress.startAnim();
    }

    @Override public void hideProgress() {
        progress.stopAnim();
    }

    @Override public void showResultError(int errorNo, String errorMag) {
        dialog = new DialogUtils(getActivity());

                dialog.errorMessage(errorMag, "获取车辆信息出错")
                .show();
    }

    @Override
    public void showResultSuccess(ResultBean resultBean) {

    }

    @Override public void showResultSuccess(List<CarInfoBean> resultBean) {
        this.cars = resultBean;

        carInfoList.setAdapter(new CommonAdapter<CarInfoBean>(getActivity(), resultBean,
                R.layout.carinfo_listview_item) {
            @Override public void convert(final ViewHolder holder, CarInfoBean car) {
                holder.replaceText(R.id.carinfo_item_carState, "null", isGood(car.isCarState()));
                LogUtils.d("车牌号" + car.getPlateNumber());
                holder.replaceText(R.id.carinfo_item_carId, "null", car.getPlateNumber());
                holder.replaceText(R.id.carinfo_item_carName, "null",car.getBrand() + car.getModles());
                holder.replaceText(R.id.carinfo_item_carOil, "null", car.getOil() + "");
                if (!car.getMark().equals("")) {
                    LogUtils.d("请求图片的地址:" + Constant.SERVER_URL + car.getMark());
                    RxVolley.get(Constant.SERVER_URL + car.getMark(), new HttpCallback() {
                        @Override public void onSuccess(Map<String, String> headers, Bitmap bitmap) {
                            if (bitmap != null)
                                holder.setImage(R.id.carInfo_item_carImage, bitmap);
                        }

                        @Override public void onFailure(int errorNo, String strMsg) {
                            LogUtils.d("连接服务器异常:" + strMsg);
                        }
                    });
                }
            }
        });
    }

    @Override public void onDestroy() {
        dialog.onDestroy();
        super.onDestroy();
    }

    private String isGood(boolean check) {
        return check ? "好": "坏";
    }
}
