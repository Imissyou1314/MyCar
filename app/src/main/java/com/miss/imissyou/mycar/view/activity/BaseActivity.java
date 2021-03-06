package com.miss.imissyou.mycar.view.activity;

import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.github.ikidou.fragmentBackHandler.BackHandlerHelper;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.util.FindViewById;
import com.miss.imissyou.mycar.view.BackHandledInterface;
import com.miss.imissyou.mycar.view.fragment.BaseFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * 所有的Activity都继承的基类
 * Created by Imissyou on 2016/3/23.
 */
public abstract class BaseActivity extends FragmentActivity {

    private static String TAG = "BaseActivity";
    private Class<?> clazz;

    private BaseFragment mBaseFragment;
    private boolean hadIntercept;

    private long lastBackPress;

    /**
     * 调用onFinish 返回上一个页面
     */
    public void onFinish(View v){
        this.finish();
    }

    protected void onCreate(Bundle savedInstanceState,int layoutResID) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "layoutResID:" + layoutResID);
        setContentView(layoutResID);
        clazz = this.getClass();

        byIdContentView();      //finviewbyid注解
        byIdViews();

        initData();
        addListeners();         //控件监听事件接口
    }

    private void byIdContentView() {
        // TODO 自动生成的方法存根
        //获取注解
        FindViewById view=clazz.getAnnotation(FindViewById.class);
        //获取注解的值
        if(view!=null){
            int id=view.id();
            //获取类的ContentView方法
            try {
                Method method=clazz.getMethod("ContentView", int.class);
                try {
                    method.setAccessible(true);
                    method.invoke(this, id);
                } catch (IllegalAccessException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        }
    }

    private void byIdViews() {
        // TODO 自动生成的方法存根
        //获取类所有的属性对象
        Field[]fields=clazz.getDeclaredFields();
        if(fields!=null){
            for(Field field:fields){
                //判断是否加了注解
                if (!field.isAnnotationPresent(FindViewById.class))
                    continue;
                //获取当前属性的注解
                FindViewById viewById=field.getAnnotation(FindViewById.class);
                //获取注解的值
                int id=viewById.id();
                //获取类的findviewbyid方法
                try {
                    Method method=clazz.getMethod("findViewById", int.class);
                    try {
                        Object obj=method.invoke(this, id);
                        field.setAccessible(true);
                        //将设置好的方法放入属性中
                        field.set(this, obj);
                    } catch (IllegalAccessException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                } catch (NoSuchMethodException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        /**打印退出该方法的信息*/
        LogUtils.d("---------------------------->>>>>onDestroy");
        super.onDestroy();
    }

    /**
     * 添加初始化数据
     */
    protected abstract void initData();

    /**
     * 添加监听事件
     */
    public abstract void  addListeners();

    @Override public void onBackPressed() {
        //如果Fragment没有消费OnBackPressed事件，交由Activity处理
        if (!BackHandlerHelper.handleBackPress(this))
//            if (System.currentTimeMillis() - lastBackPress < 2000) {
                super.onBackPressed();
//            } else {
//                lastBackPress = System.currentTimeMillis();
//                Toast.makeText(BaseActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
//            }
    }
}
