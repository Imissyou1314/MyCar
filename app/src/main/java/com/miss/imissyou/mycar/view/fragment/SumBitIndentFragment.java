package com.miss.imissyou.mycar.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.GasStationBean;
import com.miss.imissyou.mycar.bean.OilBean;
import com.miss.imissyou.mycar.bean.OrderBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.SumbitIndentPresenter;
import com.miss.imissyou.mycar.presenter.impl.SumbitIndentPresenterImpl;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.DialogUtils;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.view.SumbitIndentView;

import java.util.ArrayList;
import java.util.List;

/**
 * 加油订单提交的Fragment
 * Created by Imissyou on 2016/4/24.
 */
public class SumBitIndentFragment extends BaseFragment implements SumbitIndentView {

    public static final String TAG = "SUMBITINDENTFRAGMENT";

    private EditText priceInput;            //总价格输入
    private EditText oilNumberInput;        //总油量输入
    private ImageView stationImageView;     //加油站图片
    private Spinner oilTypeSelect;          //加油类别
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_sumbit_indent,
                inflater, container, savedInstanceState);
    }

    @Override protected void initView(View view) {
        oilTypePrice = (TextView) view.findViewById(R.id.sumbitIndent_oil_Price);
        oilTypeSelect = (Spinner) view.findViewById(R.id.sumbitIndent_selectOilType);
        stationImageView = (ImageView) view.findViewById(R.id.sumbitIndent_gasStation_ImageView);
        oilNumberInput = (EditText) view.findViewById(R.id.sumbitIndent_iloNumber_editInput);
        priceInput = (EditText) view.findViewById(R.id.sumbitIndent_price_editInput);
        sumBitOrder = (Button) view.findViewById(R.id.sumbitIndent_sumbit_btn);
    }

    @Override protected void initData() {
        mSumbitIndentPresenter = new SumbitIndentPresenterImpl(this);
        GasStationBean gasStation = GsonUtils.Instance()
                .fromJson(getArguments().getString("gasStation"), GasStationBean.class);
        if (gasStation.getPrice().size() > 0) {
            oilBeans = new ArrayList<OilBean>();
            oilType = new ArrayList<String>();
            //设置加油站有的所有的油类型
            for (String key : gasStation.getGastprice().keySet()) {
                OilBean tempBean = new OilBean();
                oilType.add(key);
                tempBean.setOilType(key);
                tempBean.setPrice(gasStation.getGastprice().get(key));
                oilBeans.add(tempBean);
             }

            oilTypeSelect.setAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_item, oilType));
        }
    }

    @Override protected void addViewsListener() {
        /**
         * 监控油量输入
         */
        priceInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (priceInput.getText().toString().equals("")) {
                        if(!oilNumberInput.getText().equals("")) {
                            oilNumber = Double.parseDouble(oilNumberInput.getText().toString());
                            priceInput.setEnabled(false);
                            price = getPrice(oilNumber);
                            priceInput.setText(price + "");
                        }
                    }
                }
            }
        });

        /**
         * 监控油量输入
         */
        oilNumberInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (oilNumberInput.getText().toString().equals("")) {
                        if(!priceInput.getText().equals("")) {
                            price = Integer.getInteger(priceInput.getText().toString());
                            oilNumberInput.setEnabled(false);
                            oilNumber = getoilNumber(price);
                            oilNumberInput.setText(oilNumber + "");
                        }
                    }
                }
            }
        });

        /**
         * 提交订单
         */
        sumBitOrder.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override public void onFocusChange(View v, boolean hasFocus) {
                //TODO提交订单
                sumBitOrder.setFocusable(false);
                //TODO提交数据类型
//                orderBean.setCarId(carId);
                orderBean.setUserId(Constant.userBean.getId());
                orderBean.setAddress(address);
//                orderBean.setStationName();
                mSumbitIndentPresenter.loadServiceData(orderBean);
            }
        });

        oilTypeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view,
                                                 int position, long id) {
                oil = oilBeans.get(position);
                oilTypePrice.setText(oil.getPrice() + "$/升");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                new DialogUtils(getActivity())
                        .errorMessage("警告","你没有选择油性")
                        .show();
            }
        });

    }

    /**
     * 根据价格获取油量
     * @param price
     * @return
     */
    private double getoilNumber(double price) {
        return price/Double.parseDouble(oil.getPrice());
    }

    /**
     * 根据输入油升数获取价格
     * @param oilNumber
     * @return
     */
    private double getPrice(double oilNumber) {
        return (double)(oilNumber * Double.parseDouble(oil.getPrice()));
    }

    @Override public void showResultError(int errorNo, String errorMag) {
        String titleMage ="";
        if (errorNo == 0) {
            titleMage = "程序出错";
        } else {
            titleMage = "错误操作";
        }
        new DialogUtils(getActivity())
                .errorMessage(titleMage, errorMag)
                .show();
    }

    @Override public void showResultSuccess(ResultBean resultBean) {

    }

    @Override public void showProgress() {

    }

    @Override public void hideProgress() {

    }

    @Override public void onDestroy() {
        super.onDestroy();
    }

}
