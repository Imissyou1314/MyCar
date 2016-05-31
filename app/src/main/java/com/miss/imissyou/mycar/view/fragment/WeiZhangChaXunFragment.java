package com.miss.imissyou.mycar.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cheshouye.api.client.WeizhangClient;
import com.cheshouye.api.client.WeizhangIntentService;
import com.cheshouye.api.client.json.CarInfo;
import com.cheshouye.api.client.json.CityInfoJson;
import com.cheshouye.api.client.json.ProvinceInfoJson;
import com.cheshouye.api.client.json.WeizhangResponseJson;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.ui.sidemenu.interfaces.ScreenShotable;
import com.miss.imissyou.mycar.view.activity.BaseActivity;

import java.util.List;


/**
 * Created by Imissyou on 2016/4/23.
 */
public class WeiZhangChaXunFragment extends BaseFragment implements View.OnClickListener {

    private EditText chePai;
    private EditText cheJia;
    private EditText englie;
    private Spinner chePai_Address;
    private TextView cheAddress;

    private String carpai;      //车牌号
    private String carjia;      //车架号
    private String engine;
    private String RegisterId;
    private int cityId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Intent weizhangIntent = new Intent(getActivity(), WeizhangIntentService.class);
        weizhangIntent.putExtra("appId",1604);
        weizhangIntent.putExtra("appKey","04d3a6bfb70c732f8b5a547f8b37213c");
        getActivity().startService(weizhangIntent);

        return super.onCreateView(R.layout.fragment_weizhangchaxunapi,
                inflater, container, savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        chePai = (EditText) view.findViewById(R.id.weizhangchaxun_brandNumber_input);
        cheJia = (EditText) view.findViewById(R.id.weizhangchaxun_carNumber_input);
        englie = (EditText) view.findViewById(R.id.weizhangchaxun_vinNumber_input);
        cheAddress = (TextView) view.findViewById(R.id.weizhangchaxun_address_Listener);
        chePai_Address = (Spinner) view.findViewById(R.id.weizhangchaxun_bianhao);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void addViewsListener() {
        cheAddress.setOnClickListener(this);
    }



    /**
     * 进行查询
     */
    private void getWeizhaInfo() {
        CarInfo car = new CarInfo();
        car.setChepai_no("粤B705A0");
        car.setChejia_no("053146");
        car.setEngine_no("2748");
        car.setRegister_no("");
        car.setCity_id(152);

        WeizhangResponseJson info = WeizhangClient.getWeizhang(car);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weizhangchaxun_address_Listener:
                showDilaogList();
                break;
            case R.id.weizhangchaxun_submit:
                submit();
                break;
        }
    }
    private void showDilaogList() {
        final List<ProvinceInfoJson> pList = WeizhangClient.getAllProvince();
        List<CityInfoJson> cList = WeizhangClient.getCitys(12);

        final ProvinceInfoJson[] pjson = null;

        new MaterialDialog.Builder(getActivity())
                .title("请选择城市")
                .items(pList)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        pjson[0] = pList.get(which);
                        dialog.dismiss();
                        return true;
                    }
                })
                .positiveText("确定")
                .show();

    }

    private void submit() {
        carjia = checkInput(cheJia);
        carpai = checkInput(chePai);
        engine = checkInput(englie);
    }

    /**
     * 获取输入值
     * @param input
     * @return
     */
    private String checkInput(EditText input) {
       return "".equals(input.getText().toString()) ? "" : input.getText().toString();


    }
}
