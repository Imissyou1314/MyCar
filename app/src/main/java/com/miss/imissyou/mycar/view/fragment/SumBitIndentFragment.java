package com.miss.imissyou.mycar.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.bean.OilBean;
import com.miss.imissyou.mycar.bean.OrderBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.presenter.SumbitIndentPresenter;
import com.miss.imissyou.mycar.presenter.impl.SumbitIndentPresenterImpl;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.view.SumbitIndentView;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 加油订单提交的Fragment
 * Created by Imissyou on 2016/4/24.
 */
public class SumBitIndentFragment extends BaseFragment implements SumbitIndentView {

    private EditText priceInput;            //总价格输入
    private EditText oilNumberInput;        //总油量输入
    private ImageView stationImageView;     //加油站图片
    private Spinner oilTypeSelect;          //加油类别
    private TextView oilTypePrice;          //选择油类型的价格（升/元)
    private Button sumBitOrder;

    private int price;          //输入的价格
    private int oilNumber;      //输入的油升数量
    private OilBean oil;        //油的Bean

    private SumbitIndentPresenter mSumbitIndentPresenter;
    private OrderBean orderBean;
    private double lat;            //经度
    private double lot;            //纬度
    private String address;        //地址
    private List<OilBean> oilBean;

    @Nullable
    @Override
    public View onCreateView(int viewId, LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_sumbit_indent, inflater,
                container, savedInstanceState);
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
        orderBean = new OrderBean();
        lat = getArguments().getDouble("lat");
        lot = getArguments().getDouble("lot");
        address = getArguments().getString("address");
        oilBean = GsonUtils.Instance().fromJson(getArguments().getString("oil"),
                new TypeToken<List<OilBean>>() {}.getType());
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
                            oilNumber = Integer.getInteger(oilNumberInput.getText().toString());
                            priceInput.setEnabled(false);
                            price = getPrice(oilNumber);
                            priceInput.setText(price);
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
                            oilNumberInput.setText(oilNumber);
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

    }

    /**
     * 根据价格获取油量
     * @param price
     * @return
     */
    private int getoilNumber(int price) {
        return price/oil.getPrice();
    }

    /**
     * 根据输入油升数获取价格
     * @param oilNumber
     * @return
     */
    private int getPrice(int oilNumber) {
        return oilNumber * oil.getPrice();
    }

    @Override public void showResultError(int errorNo, String errorMag) {

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
