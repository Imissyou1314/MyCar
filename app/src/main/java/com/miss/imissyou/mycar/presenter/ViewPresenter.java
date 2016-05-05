package com.miss.imissyou.mycar.presenter;

/**
 *  这个接口用于绑定View
 * Created by Imissyou on 2016/4/2.
 */
public interface ViewPresenter<V> {

    /**
     * 装载View
     * @param view
     */
    void attachView(V view);

    /**
     * 清空View
     */
    void detchView();
}
