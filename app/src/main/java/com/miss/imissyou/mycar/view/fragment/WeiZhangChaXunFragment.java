package com.miss.imissyou.mycar.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cheshouye.api.client.WeizhangIntentService;
import com.cheshouye.api.client.json.CarInfo;
import com.miss.imissyou.mycar.R;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Imissyou on 2016/4/23.
 */
public class WeiZhangChaXunFragment extends Fragment implements ScreenShotable {

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
        getActivity().startActivity(weizhangIntent);


        View  view = inflater.inflate(R.layout.fragment_weizhangchaxunapi,container,false);
        return view;
    }

    private void laodData() {
        CarInfo car = new CarInfo();
        car.setChepai_no(carpai);  //车牌号
    }

    @Override public void takeScreenShot() {

    }

    @Override public Bitmap getBitmap() {
        return null;
    }
}
