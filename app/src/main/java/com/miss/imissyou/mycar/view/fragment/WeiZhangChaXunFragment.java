package com.miss.imissyou.mycar.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cheshouye.api.client.WeizhangClient;
import com.cheshouye.api.client.WeizhangIntentService;
import com.cheshouye.api.client.json.AllConfigJson;
import com.cheshouye.api.client.json.CarInfo;
import com.cheshouye.api.client.json.CityInfoJson;
import com.cheshouye.api.client.json.InputConfigJson;
import com.cheshouye.api.client.json.ProvinceInfoJson;
import com.cheshouye.api.client.json.WeizhangResponseJson;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.util.GsonUtils;

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

    private Button sumbit;
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
    public boolean onBackPressed() {
        return false;
    }

    @Override
    protected void initView(View view) {
        chePai = (EditText) view.findViewById(R.id.weizhangchaxun_brandNumber_input);
        cheJia = (EditText) view.findViewById(R.id.weizhangchaxun_carNumber_input);
        englie = (EditText) view.findViewById(R.id.weizhangchaxun_vinNumber_input);
        cheAddress = (TextView) view.findViewById(R.id.weizhangchaxun_address_Listener);
        chePai_Address = (Spinner) view.findViewById(R.id.weizhangchaxun_bianhao);

        sumbit = (Button) view.findViewById(R.id.weizhangchaxun_submit);
    }

    @Override
    protected void initData() {

        /**
         * 参数：无
         * 返回值：省份配置对象的List集合
         */
        List<ProvinceInfoJson> pList = WeizhangClient.getAllProvince();
        //示例二，获取广东省的所有城市列表

        /**
         * 参数：     省份ID【广东省Id:12】
         * 返回值：广东省下面所有城市配置对象的List集合
         */
        List<CityInfoJson> cList = WeizhangClient.getCitys(12);

        /**
         * 参数：    城市ID【深圳市：152】
         * 返回值：深圳市违章汽车配置信息如【车架号，发动机号，等 具体参考附录】
         */
        //示例三，获取深圳查询违章的输入格式,具体参考附录
        InputConfigJson inputJson = WeizhangClient.getInputConfig(152);


        //附录方法:
        /**
         * 参数：     省份ID【广东省Id:12】
         * 返回值：广东省信息
         */
        //工具一: 根据省份id获取省份信息
        ProvinceInfoJson pInfo = WeizhangClient.getProvince(12);

        //工具二: 根据城市id获取城市配置
        /**
         * 参数：    城市ID【深圳市：152】
         * 返回值：深圳市信息
         */
        CityInfoJson city = WeizhangClient.getCity(152);

        //工具三: 获取整体配置对象
        /**
         * 参数：    无
         * 返回值：所有省份城市配置信息
         */
        AllConfigJson allConfig = WeizhangClient.getAll();


        //示例一，获取[粤B12345T]的违章信息。
        CarInfo car = new CarInfo();
        car.setChepai_no("粤B705A0");
        car.setChejia_no("053146");
        car.setEngine_no("2748");
        car.setRegister_no("");
        car.setCity_id(152);
        WeizhangResponseJson weizhangInfo = WeizhangClient.getWeizhang(car);
    }

    @Override
    protected void addViewsListener() {
        sumbit.setOnClickListener(this);
        cheAddress.setOnClickListener(this);
    }



    /**
     * 进行查询
     */
    private void getWeizhaInfo() {
        CarInfo car = new CarInfo();
//        car.setChepai_no("粤B705A0");
//        car.setChejia_no("053146");
//        car.setEngine_no("2748");
//        car.setRegister_no("");
//        car.setCity_id(152);
        car.setChepai_no("泸GC3193");
        car.setEngine_no("C190C8008");

        List<ProvinceInfoJson> provinceInfoJsons = WeizhangClient.getAllProvince();
        LogUtils.w("查询"  + GsonUtils.Instance().toJson(provinceInfoJsons.toArray()));
        WeizhangResponseJson weizhangInfo = WeizhangClient.getWeizhang(car);
        LogUtils.w("获取到的数据：" + weizhangInfo.toJson());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weizhangchaxun_address_Listener:
                showDilaogList();
                break;
            case R.id.weizhangchaxun_submit:
               runnable.run();
//                submit();
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

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("miss","请求结果:" + val);
        }
    };

    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            // TODO: http request.
            getWeizhaInfo();
//            Message msg = new Message();
//            Bundle data = new Bundle();
//            data.putString("value","请求结果");
//            msg.setData(data);
//            handler.sendMessage(msg);
        }
    };

    /**
     * 获取输入值
     * @param input
     * @return
     */
    private String checkInput(EditText input) {
       return "".equals(input.getText().toString()) ? "" : input.getText().toString();


    }
}
