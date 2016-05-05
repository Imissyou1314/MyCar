package com.miss.imissyou.mycar;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.view.activity.AboutActivity;
import com.miss.imissyou.mycar.view.activity.AddNewCarActivity;
import com.miss.imissyou.mycar.view.activity.AuthorActivity;

import com.miss.imissyou.mycar.view.activity.LoginActivity;
import com.miss.imissyou.mycar.view.activity.SettingActivity;
import com.miss.imissyou.mycar.view.fragment.CarListFragment;
import com.miss.imissyou.mycar.view.fragment.ContentFragment;
import com.miss.imissyou.mycar.view.fragment.HomeFragment;
import com.miss.imissyou.mycar.view.fragment.LocationMapFragment;
import com.miss.imissyou.mycar.view.fragment.MusicFragment;
import com.miss.imissyou.mycar.view.fragment.UserInfoFragment;
import com.miss.imissyou.mycar.view.fragment.WZCXFragment;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

public class MainActivity extends ActionBarActivity
        implements ViewAnimator.ViewAnimatorListener {
    /**
     * 布局
     */
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ContentFragment contentFragment;
    private ViewAnimator viewAnimator;
    private LinearLayout linearLayout;


    private Intent intent;
    private HomeFragment homeFragment;
    private CarListFragment carListFragement;
    private MusicFragment musicFragment;
    private WZCXFragment weiZhanChaXunFragment;
    private UserInfoFragment userInfoFragment;
    private LocationMapFragment locationMapFragment;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
            LogUtils.d("kitkit");
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setNavigationBarTintResource(R.color.color_activty_title);
        tintManager.setStatusBarTintResource(R.color.color_activty_title);
        setContentView(R.layout.activity_main);
        setUpView();
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        /**
         * 设置主页菜单
         */
        setActionBar();
        /**创建菜单项*/
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, contentFragment, drawerLayout, this);
        /**加载页面数据*/
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    /**
     * 创建侧滑菜单项
     */
    //TODO 更换布局
    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(ContentFragment.CLOSE, R.mipmap.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(ContentFragment.BUILDING, R.mipmap.icn_1);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(ContentFragment.BOOK, R.mipmap.icn_2);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(ContentFragment.PAINT, R.mipmap.icn_3);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(ContentFragment.CASE, R.mipmap.icn_4);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(ContentFragment.SHOP, R.mipmap.icn_5);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(ContentFragment.PARTY, R.mipmap.icn_6);
        list.add(menuItem6);
        SlideMenuItem menuItem7 = new SlideMenuItem(ContentFragment.MOVIE, R.mipmap.icn_7);
        list.add(menuItem7);
        SlideMenuItem menuItem8 = new SlideMenuItem(ContentFragment.USER, R.mipmap.user);
        list.add(menuItem8);
        SlideMenuItem menuItem9 = new SlideMenuItem(ContentFragment.MUSIC, R.drawable.music);
        list.add(menuItem9);
        SlideMenuItem menuItem10 = new SlideMenuItem(ContentFragment.MAP, R.drawable.ic_action_name);
        list.add(menuItem10);
        SlideMenuItem menuItem11 = new SlideMenuItem(ContentFragment.NAVIGATION, R.drawable.ic_navigation_image);
        list.add(menuItem11);

    }

    /**
     * 设置TitieBar
     */
    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    /**
     * 初始化布局
     */
    private void setUpView() {
        //TODO
        contentFragment = ContentFragment.newInstance(R.mipmap.content_music);
        homeFragment =new HomeFragment();
        carListFragement = new CarListFragment();
        musicFragment = new MusicFragment();
        weiZhanChaXunFragment = new WZCXFragment();
        userInfoFragment = new UserInfoFragment();
        locationMapFragment = new LocationMapFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, homeFragment)
                .commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        //编写自己的布局
    }





    /**
     * 左边菜单项的选择和处理
     * @param slideMenuItem
     * @param screenShotable
     * @param position
     * @return
     */
    @Override public ScreenShotable onSwitch(Resourceble slideMenuItem,
                                   ScreenShotable screenShotable, int position) {
        //TODO 更新Fragment
        switch (slideMenuItem.getName()) {

            case ContentFragment.CLOSE:
                //关掉菜单项
                return screenShotable;
            case ContentFragment.BOOK:
                //关掉菜单项
                LogUtils.d("position :" + position);
              return replaceFragment(carListFragement,position);
            case ContentFragment.MOVIE:
                //关掉菜单项
                LogUtils.d("position :" + position);
                return replaceFragment(homeFragment,position);
            case ContentFragment.PAINT:
                return replaceFragment(musicFragment,position);
            case ContentFragment.SHOP:
                return  replaceFragment(weiZhanChaXunFragment, position);
//                return replaceFragment(mapFragment,position);
            case ContentFragment.USER:
                return replaceFragment(userInfoFragment, position);
            case ContentFragment.MUSIC:
                return replaceFragment(musicFragment, position);
            case ContentFragment.MAP :
                return replaceFragment(locationMapFragment, position);
            case ContentFragment.NAVIGATION :
                return screenShotable;
            default:
                //更换菜单项
                LogUtils.d("position :" + position);
                return screenShotable;
        }
    }

    /**
     *
     * ViewAnimator接口的实现类
     */
    @Override public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();
    }

    @Override public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }

    /**
     * 更换Fragment
     */
    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition) {
        //更改页面布局

        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());

        SupportAnimator animator = ViewAnimationUtils
                .createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        animator.start();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, (Fragment)screenShotable).commit();
        return screenShotable;
    }

    /**
     * 右边的菜单项
     * @param savedInstanceState
     */
    @Override protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override public boolean onCreateOptionsMenu(final Menu menu) {
        //设置OptionsMenu 菜单的选项
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * 处理菜单项的OptionsMenu选择
     * @param item
     * @return
     */
    @Override public boolean onOptionsItemSelected(final MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        //只有一个设置
        intent = new Intent();
        switch (item.getItemId()) {
            case R.id.action_settings:
                intent.setClass(this, SettingActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_author:
                intent.setClass(this, AuthorActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_about:
                intent.setClass(this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_help:
//                intent.setClass(this, HelpActivity.class);
                intent.setClass(this, AddNewCarActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_login:
                intent.setClass(this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
