package com.miss.imissyou.mycar.presenter.impl;

import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.model.HomeModel;
import com.miss.imissyou.mycar.model.impl.HomeModelImpl;
import com.miss.imissyou.mycar.presenter.HomePresenter;
import com.miss.imissyou.mycar.view.HomeView;
import com.miss.imissyou.mycar.view.fragment.HomeFragment;

/**
 * Created by Imissyou on 2016/5/14.
 */
public class HomePresenterImpl implements HomePresenter {

    private HomeView mHomeView;
    private HomeModel mHomeModel;

    public HomePresenterImpl(HomeView homeFragment) {
        attachView(homeFragment);
        mHomeModel = new HomeModelImpl(this);
    }

    @Override public void onFailure(int errorNo, String strMsg) {

    }

    @Override public void onSuccess(BaseBean resultBean) {

    }

    @Override public void loadServiceData(BaseBean useBean) {

    }

    @Override public void attachView(HomeView view) {

    }

    @Override public void detchView() {

    }
}
