package com.miss.imissyou.mycar.presenter.impl;

import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.OrderBean;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.model.SumbitIndentModel;
import com.miss.imissyou.mycar.model.impl.SumbitIndentModelImpl;
import com.miss.imissyou.mycar.presenter.SumbitIndentPresenter;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.view.SumbitIndentView;
import com.miss.imissyou.mycar.view.fragment.BaseFragment;

/**
 * Created by Imissyou on 2016/4/25.
 */
public class SumbitIndentPresenterImpl implements SumbitIndentPresenter {

    private SumbitIndentView mSumbitIndentView;
    private SumbitIndentModel mSumbitIndentModel;

    public SumbitIndentPresenterImpl(BaseFragment sumBitIndentFragment) {
        attachView(sumBitIndentFragment);
        mSumbitIndentModel = new SumbitIndentModelImpl(this);
    }



    @Override public void onFailure(int errorNo, String strMsg) {
        if (errorNo == Constant.NETWORK_STATE)
            strMsg = "网络异常";
        mSumbitIndentView.showResultError(errorNo, strMsg);
    }

    @Override public void onSuccess(BaseBean resultBean) {
        mSumbitIndentView.showResultSuccess((ResultBean) resultBean);
    }

    @Override public void loadServiceData(BaseBean useBean) {

    }

    @Override public void attachView(BaseFragment view) {
        mSumbitIndentView = (SumbitIndentView) view;
        mSumbitIndentView.showProgress();
    }

    @Override public void detchView() {
        mSumbitIndentView = null;
    }

    @Override public void submitOrderToService(OrderBean orderBean) {
        if (orderBean != null || orderBean.getUserId() != null) {
            mSumbitIndentModel.sentIndentToService(orderBean );
        }
    }
}
