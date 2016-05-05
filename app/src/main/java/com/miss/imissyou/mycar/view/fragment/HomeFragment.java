package com.miss.imissyou.mycar.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miss.imissyou.mycar.R;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Imissyou on 2016/3/26.
 */
public class HomeFragment extends Fragment implements ScreenShotable {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       super.onCreateView(inflater, container, savedInstanceState);
        addViewListener();
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    private void addViewListener() {

    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}
