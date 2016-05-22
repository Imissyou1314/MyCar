package com.miss.imissyou.mycar.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.GasStationBean;
import com.miss.imissyou.mycar.bean.OilBean;
import com.miss.imissyou.mycar.bean.OrderBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.SumbitIndentPresenter;
import com.miss.imissyou.mycar.presenter.impl.SumbitIndentPresenterImpl;
import com.miss.imissyou.mycar.ui.spinner.NiceSpinner;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.DialogUtils;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.view.SumbitIndentView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 加油订单提交的Fragment
 * Created by Imissyou on 2016/4/24.
 */
public class SumBitIndentFragment extends BaseFragment implements SumbitIndentView {

    public static final String TAG = "SUMBITINDENTFRAGMENT";

    private EditText priceInput;            //总价格输入
    private EditText oilNumberInput;        //总油量输入
    private ImageView stationImageView;     //加油站图片
    private NiceSpinner oilTypeSelect;          //加油类别
    private TextView oilTypePrice;          //选择油类型的价格（升/元)
    private Button sumBitOrder;

    private double price;          //输入的价格
    private double oilNumber;      //输入的油升数量
    private OilBean oil;        //油的Bean

    private SumbitIndentPresenter mSumbitIndentPresenter;
    private OrderBean orderBean;
    private double lat;            //经度
    private double lot;            //纬度
    private String address;        //地址
    private List<OilBean> oilBeans;
    private List<String> oilType;
    private GasStationBean gasStation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_sumbit_indent,
                inflater, container, savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        oilTypePrice = (TextView) view.findViewById(R.id.sumbitIndent_oil_Price);
        oilTypeSelect = (NiceSpinner) view.findViewById(R.id.sumbitIndent_selectOilType);
        stationImageView = (ImageView) view.findViewById(R.id.sumbitIndent_gasStation_ImageView);
        oilNumberInput = (EditText) view.findViewById(R.id.sumbitIndent_iloNumber_editInput);
        oilNumberInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        priceInput = (EditText) view.findViewById(R.id.sumbitIndent_price_editInput);
        priceInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        sumBitOrder = (Button) view.findViewById(R.id.sumbitIndent_sumbit_btn);
    }

    @Override
    protected void initData() {
        mSumbitIndentPresenter = new SumbitIndentPresenterImpl(this);

        LogUtils.d(getArguments().getString("gasStation"));
        gasStation = GsonUtils.Instance()
                .fromJson(getArguments().getString("gasStation"), GasStationBean.class);
        //获取到的价格不为空
        if (null != gasStation.getPrice() && null != gasStation.getGastprice()) {
            oilBeans = new ArrayList<OilBean>();
            oilType = new ArrayList<String>();
            Map<String, String> oilTypeBean;

            /**
             * 默认的是该加油站提供的信息
             */
            if (null != gasStation.getGastprice()) {
                oilTypeBean = gasStation.getGastprice();
            } else {
                oilTypeBean = gasStation.getPrice();
            }
            //设置加油站有的所有的油类型
            for (String key : oilTypeBean.keySet()) {
                OilBean tempBean = new OilBean();
                oilType.add(key);
                tempBean.setOilType(key);
                tempBean.setPrice(oilTypeBean.get(key));
                oilBeans.add(tempBean);
            }

            oilTypeSelect.attachDataSource(oilType);
        } else {
            showResultError(0, "加油站的价格为空");
        }
    }

    @Override
    protected void addViewsListener() {
        /**
         * 监控油量输入
         */
        priceInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override public void onFocusChange(View v, boolean hasFocus) {
                LogUtils.d("价格输入焦点该变");
                if (hasFocus) {
                    LogUtils.d("价格输入获取焦点");
                } else {
                    setOilText();
                    LogUtils.d("价格输入失去焦点");
                }

            }
        });

        /**
         * 监控油量输入
         */
        oilNumberInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override public void onFocusChange(View v, boolean hasFocus) {
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
        sumBitOrder.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (null == Constant.userBean.getId()) {
                    showResultError(0, "用户没登录,请去登录");
                } else if (null == Constant.carBean.getId()) {
                    showResultError(0, "请添加车辆");
                } else {
                    orderBean.setUserId(Constant.userBean.getId());
                    orderBean.setCarId(Constant.carBean.getId());

                    orderBean.setStationName(gasStation.getName());
                    orderBean.setAddress(gasStation.getAddress());
                    orderBean.setBrandName(gasStation.getBrandname());

                    if (null == oil) {
                        showResultError(0, "请选择油类型先");
                    } else {
                        orderBean.setType(oil.getOilType());
                        orderBean.setPrice(Float.parseFloat(oil.getPrice()));
                        orderBean.setUnits(oil.getPrice());
                        if (price != 0 && oilNumber != 0) {
                            orderBean.setAmounts((int) price);
                            orderBean.setNumber((int) oilNumber);
                            //进行加油订单的提交
                            mSumbitIndentPresenter.loadServiceData(orderBean);
                        } else {
                            showResultError(0, "请输入加油数量");
                        }
                    }
                }
            }
        });

        /**
         * 选择油的类型
         */
        oilTypeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                oil = oilBeans.get(position);
                oilTypePrice.setText(oil.getPrice() + "$/升");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                new DialogUtils(getActivity())
                        .errorMessage("警告", "你没有选择油性")
                        .show();
            }
        });

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
        if (oilNumber <= 0 || oil == null )
            return 0;
        return (double) (oilNumber * Double.parseDouble(oil.getPrice()));
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

}
