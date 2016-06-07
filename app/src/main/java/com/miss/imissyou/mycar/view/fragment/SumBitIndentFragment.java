package com.miss.imissyou.mycar.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.GasStationBean;
import com.miss.imissyou.mycar.bean.OilBean;
import com.miss.imissyou.mycar.bean.OrderBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.SumbitIndentPresenter;
import com.miss.imissyou.mycar.presenter.impl.SumbitIndentPresenterImpl;
import com.miss.imissyou.mycar.ui.MissDialog;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.DialogUtils;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.ToastUtil;
import com.miss.imissyou.mycar.view.SumbitIndentView;
import com.miss.imissyou.mycar.view.activity.NaviViewActivity;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * 加油订单提交的Fragment
 * Created by Imissyou on 2016/4/24.
 */
public class SumBitIndentFragment extends BaseFragment implements SumbitIndentView, View.OnClickListener {

    public static final String TAG = "SUMBITINDENTFRAGMENT";


    private TextView gastationName;         //加油站名字
    private TextView gastationAddres;       //加油站地址
    private TextView gastationDistance;     //加油站距离
    private TextView gastationType;         //加油站类型
    private TextView gastationOrderTime;    //预约时间

    private LinearLayout gastationTimeSelect;       //现在预订时间
    private LinearLayout goNavi;            //去到导航页面
    private LinearLayout selectOilType;     //选择油类型


    private EditText priceInput;            //总价格输入
    private EditText oilNumberInput;        //总油量输入

    private TextView sumbitNowPlay;           //在线支付
    private TextView sumBitDayPaly;           //预约加油

    //加油类别
    //private double oilPrice;          //选择油类型的价格（升/元)
    private double oilNumber;      //输入的油升数量
    private double price;          //输入的价格
    private OilBean oil;        //油的Bean

    private SumbitIndentPresenter mSumbitIndentPresenter;
    private OrderBean orderBean = new OrderBean();
    private double lat;            //经度
    private double lot;            //纬度
    private String address;        //地址
    private GasStationBean gasStation;

    private String date;            //日期
    private String time;           //时间

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_sumbit_indent,
                inflater, container, savedInstanceState);
    }

    @Override protected void initView(View view) {

        gastationName = (TextView) view.findViewById(R.id.sumbit_gastation_gastationName);
        gastationAddres = (TextView) view.findViewById(R.id.sumbit_gastation_address);
        gastationDistance = (TextView) view.findViewById(R.id.sumbit_gastation_gastationDistance);
        gastationType = (TextView) view.findViewById(R.id.sumbit_gastation_stationType);
        gastationOrderTime = (TextView) view.findViewById(R.id.sumbit_gastation_datatext);

        gastationTimeSelect = (LinearLayout) view.findViewById(R.id.sumbit_gastation_data_select);
        goNavi = (LinearLayout) view.findViewById(R.id.sumbit_gastation_goNaiv);
        selectOilType = (LinearLayout) view.findViewById(R.id.sumbit_gastation_linearLayout);

        oilNumberInput = (EditText) view.findViewById(R.id.sumbitIndent_iloNumber_editInput);
        oilNumberInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        priceInput = (EditText) view.findViewById(R.id.sumbitIndent_price_editInput);
        priceInput.setInputType(InputType.TYPE_CLASS_NUMBER);

        sumBitDayPaly = (TextView) view.findViewById(R.id.sumbit_play_playAfter);
        sumbitNowPlay = (TextView) view.findViewById(R.id.sumbit_play_playNow);

    }

    @Override protected void initData() {
        mSumbitIndentPresenter = new SumbitIndentPresenterImpl(this);
        LogUtils.d(getArguments().getString("gasStation"));
        gasStation = GsonUtils.Instance()
                .fromJson(getArguments().getString("gasStation"), GasStationBean.class);

        if (null != gasStation && null != gasStation.getPrice()) {
            gastationName.setText(gasStation.getName());
            gastationType.setText(gasStation.getBrandname());
            gastationAddres.setText(gasStation.getAddress());
            gastationDistance.setText((Integer.parseInt(gasStation.getDistance()) / 1000) + "公里");
            addItemView(gasStation.getPrice());
            initOrder(gasStation);
        }

        //获取到的价格不为空
//        if (null != gasStation.getPrice() && null != gasStation.getGastprice()) {
//            oilBeans = new ArrayList<OilBean>();
//            oilType = new ArrayList<String>();
//            Map<String, String> oilTypeBean;
//
//            /**
//             * 默认的是该加油站提供的信息
//             */
//            if (null != gasStation.getGastprice()) {
//                oilTypeBean = gasStation.getGastprice();
//            } else {
//                oilTypeBean = gasStation.getPrice();
//            }
//            //设置加油站有的所有的油类型
//            for (String key : oilTypeBean.keySet()) {
//                OilBean tempBean = new OilBean();
//                oilType.add(key);
//                tempBean.setOilType(key);
//                tempBean.setPrice(oilTypeBean.get(key));
//                oilBeans.add(tempBean);
//            }
//
//        } else {
//            showResultError(0, "加油站的价格为空");
//        }
    }


    @Override protected void addViewsListener() {

        goNavi.setOnClickListener(this);
        gastationTimeSelect.setOnClickListener(this);
        sumbitNowPlay.setOnClickListener(this);
        sumBitDayPaly.setOnClickListener(this);
        /**
         * 监控油量输入
         */
        priceInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                LogUtils.d("价格输入焦点该变");
                if (hasFocus) {
                    LogUtils.d("价格输入获取焦点");
                } else {
                    setOilText();
                    LogUtils.d("价格输入失去焦点");
                }

            }
        });

        priceInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                LogUtils.d(priceInput.getText().toString() + "结果");
                return false;
            }
        });

        /**
         * 监控油量输入
         */
        oilNumberInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                LogUtils.d("油量输入焦点该变");
                if (hasFocus) {
                    LogUtils.d("油量输入获取焦点");
                } else {
                    setPriceText();
                    LogUtils.d("油量输入失去焦点");
                }

            }
        });


        /**
         * 提交订单
         */
//        sumBitOrder.setOnClickListener(new View.OnClickListener() {
//            @Override public void onClick(View v) {
//                if (null == Constant.userBean.getId()) {
//                    showResultError(0, "用户没登录,请去登录");
//                } else if (null == Constant.carBean.getId()) {
//                    showResultError(0, "请添加车辆");
//                } else {
//                    orderBean.setUserId(Constant.userBean.getId());
//                    orderBean.setCarId(Constant.carBean.getId());
//
//                    orderBean.setStationName(gasStation.getName());
//                    orderBean.setAddress(gasStation.getAddress());
//                    orderBean.setBrandName(gasStation.getBrandname());
//
//                    if (null == oil) {
//                        showResultError(0, "请选择油类型先");
//                    } else {
//                        orderBean.setType(oil.getOilType());
//                        orderBean.setPrice(Float.parseFloat(oil.getPrice()));
//                        orderBean.setUnits(oil.getPrice());
//                        if (price != 0 && oilNumber != 0) {
//                            orderBean.setAmounts((int) price);
//                            orderBean.setNumber((int) oilNumber);
//                            //进行加油订单的提交
//                            mSumbitIndentPresenter.loadServiceData(orderBean);
//                        } else {
//                            showResultError(0, "请输入加油数量");
//                        }
//                    }
//                }
//            }
//        });

        /**
         * 选择油的类型
         */
//        oilTypeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int position, long id) {
//                oil = oilBeans.get(position);
//                oilTypePrice.setText(oil.getPrice() + "$/升");
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                new DialogUtils(getActivity())
//                        .errorMessage("警告", "你没有选择油性")
//                        .show();
//            }
//        });

    }



    @Override
    public void showResultError(int errorNo, String errorMag) {
        String titleMage = "";
        if (errorNo == 0) {
            titleMage = "操作出错";
        } else {
            titleMage = "错误操作";
        }
        new DialogUtils(getActivity())
                .errorMessage(titleMage, errorMag)
                .show();
    }

    @Override
    public void showResultSuccess(ResultBean resultBean) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.sumbit_play_playAfter:
                initOrder(gasStation);
                orderBean.setState(0);
                sumbitOrder();
                showDialog();
                break;
            case R.id.sumbit_play_playNow:
                initOrder(gasStation);
                orderBean.setState(1);
                sumbitOrder();
                goPlayPage();
                break;
            case R.id.sumbit_gastation_goNaiv:
                intent.setClass(getActivity(), NaviViewActivity.class);
                goNaiv(intent);
                break;
            case R.id.sumbit_gastation_data_select:
                selectTime();
                break;
        }
    }

    /**
     * 设置油量的输入
     */
    private void setOilText() {
        if (priceInput.getText().toString().equals("")) {
            LogUtils.d("你什么也没输入");
        } else {
            LogUtils.d(priceInput.getText().toString());
            price = Double.parseDouble(priceInput.getText().toString());
            oilNumber = getoilNumber(price);

            oilNumberInput.setText(oilNumber + "");
        }
    }

    /**
     * 设置价格的输入设置
     */
    private void setPriceText() {
        if (oilNumberInput.getText().toString().equals("")) {
            LogUtils.d("你什么也没输入");
        } else {
            LogUtils.d(oilNumberInput.getText().toString());
            oilNumber = Double.parseDouble(oilNumberInput.getText().toString());
            price = getPrice(oilNumber);

            priceInput.setText(price + "");
        }
    }

    /**
     * 根据价格获取油量
     *
     * @param price
     * @return
     */
    private double getoilNumber(double price) {
        if (price <= 0 || oil == null)
            return 0;
        return price / Double.parseDouble(oil.getPrice());
    }

    /**
     * 根据输入油升数获取价格
     *
     * @param oilNumber
     * @return
     */
    private double getPrice(double oilNumber) {
        if (oilNumber <= 0 || oil == null)
            return 0;
        return (double) (oilNumber * Double.parseDouble(oil.getPrice()));
    }

    // TODO: 2016/6/5  到付款页面
    private void goPlayPage() {

    }

    /**
     * 弹框显示预约成功
     */
    private void showDialog() {
        new MissDialog.Builder(getActivity())
                .setTitle("提示")
                .setMessage("预约成功")
                .setNegativeButton("到这里去", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /**跳转到导航页面*/
                        goNaiv(new Intent(getActivity(), NaviViewActivity.class));
                        dialog.dismiss();
                    }
                }).setPositiveButton("查看订单", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /**跳转到订单页面*/
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_overlay, new OrderFragment())
                        .commit();
                dialog.dismiss();
            }
        }).create().show();
    }

    /**
     * 提交订单
     */
    private void sumbitOrder() {
        if (null != orderBean) {
            LogUtils.d("准备提交的数据" + GsonUtils.Instance().toJson(orderBean));
            mSumbitIndentPresenter.submitOrderToService(orderBean);
        } else {
            ToastUtil.asLong("订单提交失败");
            LogUtils.d("订单为空");
        }
    }

    /**
     * 选择预约时间
     */
    private void selectTime() {
        /**选择日期*/
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder() {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                gastationOrderTime.setText(date);
                showselectTime();           //选择时间
                Toast.makeText(getActivity(), "Date is " + date, Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.positiveAction("确定")
                .negativeAction("取消");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getFragmentManager(), null);
    }

    /**
     * 选择时间
     */
    private void showselectTime() {
        TimePickerDialog.Builder builderTime = new TimePickerDialog.Builder() {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                TimePickerDialog dialog = (TimePickerDialog) fragment.getDialog();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                time = dialog.getFormattedTime(format);
                addText(gastationOrderTime, " " + time);
                Toast.makeText(getActivity(), "Date is " + time, Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };

        builderTime.positiveAction("确认")
                .negativeAction("取消");
        DialogFragment fragment = DialogFragment.newInstance(builderTime);
        fragment.show(getFragmentManager(), null);
    }

    /**
     * 无起点导航
     */
    private void goNaiv(Intent intent) {

        intent.putExtra(Constant.NO_START_NAVI, true);
        intent.putExtra(Constant.endLatitude, lat);
        intent.putExtra(Constant.endLongitude, lot);

        getActivity().startActivity(intent);
    }

    /**
     * View添加内容
     * @param view  TextView
     * @param text  添加的内容
     */
    private void addText(TextView view, String text) {
        view.setText(view.getText() + text);
    }

    /**
     * 给LiearLayout 动态添加ItemView
     */
    private void addItemView(final Map<String, String> oilTypeBean) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //设置加油站有的所有的油类型
        for (final String key : oilTypeBean.keySet()) {
            final TextView tv1 = new TextView(getActivity());
            tv1.setLayoutParams(lp);//设置布局参数
            tv1.setTag(key);
            tv1.setText(key);
            tv1.setPadding(15,10,15,10);

            tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    oil = new OilBean();
                    oil.setOilType(key);
                    oil.setPrice(oilTypeBean.get(key));
                    LogUtils.d("油价类型:" + key + "油价:" + oil.getPrice());
                    showselectPrice(oilTypeBean.get(key));
                    tv1.setBackgroundColor(getActivity()
                            .getResources()
                            .getColor(R.color.color_gui));
                }
            });
            selectOilType.addView(tv1);
        }
    }

    /**
     * 显示价格
     *
     * @param price
     */
    private void showselectPrice(String price) {
        oilNumberInput.setText("1");
        priceInput.setText(price);
    }

    /**
     * 装载订单
     * @param gasStation
     */
    private boolean initOrder(GasStationBean gasStation) {
        Boolean result = true;

        orderBean.setStationName(gasStation.getName());
        orderBean.setAgreementTime(gastationOrderTime.getText().toString());
        orderBean.setNumber(oilNumber);
        orderBean.setAddress(gasStation.getAddress());
        orderBean.setBrandName(gasStation.getBrandname());
        orderBean.setAmounts((int)price);

        /**检查用户是否登录*/
        if (null != Constant.userBean && null != Constant.userBean.getId() ) {
            orderBean.setUserId(Constant.userBean.getId());
            orderBean.setUserName(Constant.userBean.getUsername());
        } else {
            ToastUtil.asLong("用户没登录不能下单");
            result = false;
        }

        /**检查用户是否有车*/
        if (null != Constant.carBean && null != Constant.carBean.getPlateNumber()) {
            orderBean.setPlateNumber(Constant.carBean.getPlateNumber());
        } else {
            ToastUtil.asLong("用户没有车辆，请去添加车辆");
            result = false;
        }

        if (null != oil && null != oil.getPrice() && null != oil.getOilType()) {
            orderBean.setUnits("1");
            orderBean.setPrice(Double.parseDouble(oil.getPrice()));
            orderBean.setType(oil.getOilType());
        }  else {
            result = false;
        }
        return result;
    }

}
