package com.miss.imissyou.mycar;


import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
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
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.CarInfoBean;
import com.miss.imissyou.mycar.bean.Music;
import com.miss.imissyou.mycar.bean.ResultBean;
import com.miss.imissyou.mycar.bean.UserBean;
import com.miss.imissyou.mycar.broadcastReceiver.JpushReceiver;
import com.miss.imissyou.mycar.service.impl.MusicPlayService;
import com.miss.imissyou.mycar.ui.MissDialog;
import com.miss.imissyou.mycar.ui.sidemenu.interfaces.Resourceble;
import com.miss.imissyou.mycar.ui.sidemenu.interfaces.ScreenShotable;
import com.miss.imissyou.mycar.ui.sidemenu.model.SlideMenuItem;
import com.miss.imissyou.mycar.ui.sidemenu.util.ViewAnimator;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.FindSongs;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.miss.imissyou.mycar.util.RxVolleyUtils;
import com.miss.imissyou.mycar.util.SPUtils;
import com.miss.imissyou.mycar.util.StringUtil;
import com.miss.imissyou.mycar.util.ToastUtil;
import com.miss.imissyou.mycar.util.zxing.camera.ServiceUtils;
import com.miss.imissyou.mycar.view.BackHandledInterface;
import com.miss.imissyou.mycar.view.activity.LoginActivity;
import com.miss.imissyou.mycar.view.activity.MessageActivity;
import com.miss.imissyou.mycar.view.activity.SettingActivity;
import com.miss.imissyou.mycar.view.activity.WeiZhangChaXunActivity;
import com.miss.imissyou.mycar.view.fragment.BaseFragment;
import com.miss.imissyou.mycar.view.fragment.CarInfoFragment;
import com.miss.imissyou.mycar.view.fragment.FirstAddCarFragment;
import com.miss.imissyou.mycar.view.fragment.CarListFragment;
import com.miss.imissyou.mycar.view.fragment.ContentFragment;
import com.miss.imissyou.mycar.view.fragment.MusicFragment;
import com.miss.imissyou.mycar.view.fragment.NaviViewFragment;
import com.miss.imissyou.mycar.view.fragment.OrderFragment;
import com.miss.imissyou.mycar.view.fragment.RouteSelectFragment;
import com.miss.imissyou.mycar.view.fragment.StationMapViewFragment;
import com.miss.imissyou.mycar.view.fragment.UserInfoFragment;
import com.miss.imissyou.mycar.view.fragment.WZCXFragment;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

public class MainActivity extends ActionBarActivity
        implements ViewAnimator.ViewAnimatorListener, BackHandledInterface {
    /**
     * 布局
     */
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ContentFragment contentFragment;
    private ViewAnimator viewAnimator;
    private LinearLayout linearLayout;
    private MissDialog.Builder builder;

    private Intent intent;

    /**
     * 导航栏的Item fragment
     */
    private CarListFragment carListFragement;
    private MusicFragment musicFragment;
    private WZCXFragment weiZhanChaXunFragment;
    private UserInfoFragment userInfoFragment;
    private OrderFragment orderFragment;
    private NaviViewFragment naviViewFragment;
    private CarInfoFragment carInfoFragment;
    private FirstAddCarFragment firstAddCarFragment;
    private StationMapViewFragment stationMapViewFrament;
    private RouteSelectFragment routeSelectFragment;

    /**
     * 双击退出程序
     */
    private boolean isQuit;
    private Timer timer = new Timer();
    private boolean resultTag = false;
    private BaseFragment mBaseFragment;

    /**
     * 添加开机自动播放音乐
     */
    private List<Music> mMusics;            //音乐列表
    private int mPosition = 0;              //当前播放音乐列
    private MyBroadCastService myBroad;     //音乐播放广播
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**极光推送*/
        JPushInterface.setDebugMode(true);
        JPushInterface.init(getApplication());
        JpushReceiver jpushReceiver = new JpushReceiver();

        if (Constant.userBean.getId() == null) {
            builder = new MissDialog.Builder(this);
            doLogin();
        } else {
            LogUtils.d("用户Id" + Constant.userBean.getId());
            setAlias(Constant.userBean.getId());
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setNavigationBarTintResource(R.color.color_activty_title);
        tintManager.setStatusBarTintResource(R.color.color_activty_title);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);

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
        setUpView();
        viewAnimator = new ViewAnimator<>(this, list, contentFragment, drawerLayout, this);
        /**加载页面数据*/

        startMusic();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * 检查用户是否拥有车辆
     *
     * @param id 用户Id
     * @return boolean
     *         true 用户有车辆
     *         false 用户没有车辆
     */
    private boolean checkUserHasCar(Long id) {

        String url = Constant.SERVER_URL + "car/currentCar=" + id;
        HttpCallback callback = new HttpCallback() {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                LogUtils.d("====> 获取当前车辆失败");
                resultTag = false;
            }

            @Override
            public void onSuccess(String t) {
                LogUtils.d("收到的数据===>" + t);
                ResultBean resultBean = GsonUtils.getResultBean(t);
                CarInfoBean carInfoBean = GsonUtils.getParam(resultBean, "car", CarInfoBean.class);
                Constant.carBean = carInfoBean;
                resultTag = true;
            }
        };
        RxVolleyUtils.getInstance().get(url,null,callback);
        return resultTag;
    }

    /**
     * 登录页面
     */
    private void doLogin() {
        SPUtils.init(this);
        String password = SPUtils.getSp_user().getString(Constant.UserPassID, "");
        String account = SPUtils.getSp_user().getString(Constant.UserAccountID, "");
        String carJson = SPUtils.getSp_user().getString(Constant.UserPassID + Constant.UserAccountID,"");
        if(!"".equals(carJson)) {
            Constant.carBean = GsonUtils.Instance().fromJson(carJson,CarInfoBean.class);
        }

        if (password.equals("") || account.equals("")) {
            builder.setTitle("欢迎使用")
                    .setMessage("新用户请去登录")
                    .setSingleButton(true)
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            toDoLogin();
                        }
                    });
            builder.create().show();
        } else {

            HttpParams params = new HttpParams();
            params.put("password", password);
            params.put("loginid", account);
            String url = Constant.SERVER_URL + "users/doLogin";
            LogUtils.d("登录: " + url);
            RxVolleyUtils.getInstance().post(url, params, new HttpCallback() {
                @Override
                public void onFailure(int errorNo, String strMsg) {
                    if (errorNo == Constant.NETWORK_STATE)
                        strMsg = Constant.NOTNETWORK;
                    builder.setTitle("登录出错")
                            .setMessage(strMsg + errorNo)
                            .setSingleButton(true)
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }

                @Override
                public void onSuccess(Map<String, String> headers, byte[] t) {
                    //设置COOKIE
                    Constant.COOKIE = headers.get("Set-Cookie");
                    ResultBean resultBean = GsonUtils.Instance().fromJson(StringUtil.bytesToString(t), ResultBean.class);
                    LogUtils.d("header===>" + GsonUtils.Instance().toJson(headers));
                    LogUtils.d("收到的数据::" + StringUtil.bytesToString(t));
                    LogUtils.d("Cookie===>" + headers.get("Set-Cookie"));
                    if (resultBean.isServiceResult()) {
                        Constant.userBean = GsonUtils.getParam(resultBean, "user", UserBean.class);
                        setAlias(Constant.userBean.getId());
                        //TODO加载页面
                        startMainFragment();
                        // TODO: 2016-06-07 获取当前车辆
                        if (checkUserHasCar(Constant.userBean.getId())) {
                            LogUtils.d("当前用户没有车辆");
                        }

                    } else {
                        if (resultBean.getResultInfo().equals(Constant.FileCOOKIE)){
                            RxVolleyUtils.getInstance().restartLogin();
                        } else {
                            builder.setTitle("登录出错")
                                    .setMessage(resultBean.getResultInfo())
                                    .setSingleButton(true)
                                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            toDoLogin();
                                        }
                                    });
                            builder.create().show();
                        }
                    }
                }
            });
        }
    }

    /**
     * JPushInterface绑定别名
     *
     * @param id  用户ID
     */
    private void setAlias(Long id) {
        LogUtils.d("设置别名:");
        JPushInterface.setAlias(this, id + "", new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                LogUtils.w("别名：" + i);
            }
        });
    }

    /**
     * 转到登录页面
     */
    private void toDoLogin() {
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivity(intent);
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
    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(ContentFragment.CLOSE, R.mipmap.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem1 = new SlideMenuItem(ContentFragment.HOME, R.mipmap.ic_home_icon);
        list.add(menuItem1);
        SlideMenuItem menuItem2 = new SlideMenuItem(ContentFragment.CAR, R.mipmap.ic_car_icon);
        list.add(menuItem2);
        SlideMenuItem menuItem5 = new SlideMenuItem(ContentFragment.OIL, R.mipmap.ic_gasstation_oil_icon);
        list.add(menuItem5);
        SlideMenuItem menuItem8 = new SlideMenuItem(ContentFragment.PARK, R.mipmap.ic_park_icon);
        list.add(menuItem8);
        SlideMenuItem menuItem10 = new SlideMenuItem(ContentFragment.FIX, R.mipmap.ic_car_fix_icon);
        list.add(menuItem10);
        SlideMenuItem menuItem3 = new SlideMenuItem(ContentFragment.ORDER, R.mipmap.ic_order_icon);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(ContentFragment.BREAK, R.mipmap.ic_break_icon);
        list.add(menuItem4);
        SlideMenuItem menuItem9 = new SlideMenuItem(ContentFragment.MUSIC, R.mipmap.ic_music_icon);
        list.add(menuItem9);
        SlideMenuItem menuItem11 = new SlideMenuItem(ContentFragment.NAVIGATION, R.mipmap.ic_guide_icon);
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

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
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

        contentFragment = ContentFragment.newInstance(R.mipmap.content_music);
        carListFragement = new CarListFragment();
        musicFragment = new MusicFragment();
        weiZhanChaXunFragment = new WZCXFragment();
        userInfoFragment = new UserInfoFragment();
        orderFragment = new OrderFragment();
        naviViewFragment = new NaviViewFragment();
        stationMapViewFrament = new StationMapViewFragment();
        carInfoFragment = new CarInfoFragment();
        firstAddCarFragment = new FirstAddCarFragment();
        routeSelectFragment = new RouteSelectFragment();
        //编写自己的布
        startMainFragment();
    }

    /**
     * 启动默认主页
     */
    private void startMainFragment() {
        if ((null != Constant.carBean && null != Constant.carBean.getId()) &&
                (null != Constant.userBean && null != Constant.userBean.getId())) {
            if (!carInfoFragment.isAdded()) {
                Bundle bundle = new Bundle();
                LogUtils.w("<======================启动车辆信息页面======================>");
                bundle.putLong(Constant.USER_ID, Constant.userBean.getId());
                bundle.putLong(Constant.CAR_ID, Constant.carBean.getId());
                carInfoFragment.setArguments(bundle);
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, carInfoFragment, Constant.CarInfoFragment)
                    .commit();
        } else {
            LogUtils.w("<========================启动第一次添加车辆页面=====================>");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, firstAddCarFragment)
                    .commit();
        }
    }

    /**
     * 左边菜单项的选择和处理
     *
     * @param slideMenuItem
     * @param screenShotable
     * @param position
     * @return
     */
    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem,
                                   ScreenShotable screenShotable, int position) {
        //TODO 更新Fragment
        switch (slideMenuItem.getName()) {
            case ContentFragment.CLOSE:
                //关掉菜单项
                return screenShotable;
            case ContentFragment.CAR:
                //关掉菜单项
                return replaceFragment(carListFragement, position, Constant.CarListFragment);
            case ContentFragment.OIL:
                //加油菜单项
                stationMapViewFrament.setType(Constant.MAP_GASSTATION);
                return replaceFragment(stationMapViewFrament, position, Constant.StationMapViewFragment);
            case ContentFragment.PARK:
                stationMapViewFrament.setType(Constant.MAP_PARK);
                return replaceFragment(stationMapViewFrament, position, Constant.StationMapViewFragment);
            case ContentFragment.ORDER:
                return replaceFragment(orderFragment, position, Constant.OrderFragment);
            case ContentFragment.BREAK:
                startActivity(new Intent(MainActivity.this, WeiZhangChaXunActivity.class));
                return screenShotable;
            case ContentFragment.HOME:
                startMainFragment();
                return screenShotable;
            case ContentFragment.FIX:
                stationMapViewFrament.setType(Constant.MAP_MAINTAIN);
                return replaceFragment(stationMapViewFrament, position, Constant.StationMapViewFragment);
            case ContentFragment.MUSIC:
                return replaceFragment(musicFragment, position, Constant.MusicFragment);
            case ContentFragment.NAVIGATION:

                //TODO
                return replaceFragment(routeSelectFragment, position, Constant.NaviViewFragment);
            default:
                return screenShotable;
        }
    }

    /**
     * ViewAnimator接口的实现类
     */
    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();
    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }

    /**
     * 更换Fragment
     * @param screenShotable
     * @param topPosition  点击的第几个
     * @param Tag          标签
     * @return
     */
    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition, String Tag) {
        //更改页面布局

        View view = findViewById(R.id.content_overlay);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());

        SupportAnimator animator = ViewAnimationUtils
                .createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, (Fragment) screenShotable, Tag)
                .commit();

        animator.start();
        return screenShotable;
    }

    /**
     * 右边的菜单项
     *
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        //设置OptionsMenu 菜单的选项
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * 处理菜单项的OptionsMenu选择
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
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
            case R.id.action_userinfo:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, userInfoFragment)
                        .commit();
                return true;
            //TODO 去掉关于页面
//            case R.id.action_about:
//                intent.setClass(this, AboutActivity.class);
//                startActivity(intent);
//                return true;
            case R.id.action_meaasge:
                intent.setClass(this, MessageActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        JPushInterface.onPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        JPushInterface.onResume(this);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(myBroad);
        } catch (Exception e) {
            LogUtils.d("该广播没有注册");
        }
        stopMusic();
        super.onDestroy();
    }

    /**
     * 按放回键退出程序
     */
    @Override
    public void onBackPressed() {
        if (!isQuit) {
            isQuit = true;
            Toast.makeText(getBaseContext(),
                    "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
            TimerTask task = null;
            task = new TimerTask() {
                @Override
                public void run() {
                    isQuit = false;
                }
            };
            timer.schedule(task, 2000);
        } else {
            finish();
            System.exit(0);
            Process.killProcess(Process.myPid());
        }
    }

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {
        this.mBaseFragment = selectedFragment;
    }

    /**
     * 开机启动音乐
     */
    private void startMusic() {
        SPUtils.init(this);
        Boolean musicState = SPUtils.getSp_set().getBoolean(Constant.MESSAGEMUSIC, true);            //默认开机自启
        if (musicState) {
            FindSongs songs = new FindSongs();

            if (null != getContentResolver()) {
                mMusics = songs.getSongInfo(getContentResolver());

                LogUtils.d("获取到的音乐数量" + mMusics.size());
                ToastUtil.asLong("获取到的音乐数量" + mMusics.size());
            } else {
                LogUtils.d("获取getActivity().getContentResolver()失败");
                ToastUtil.asLong("获取getActivity().getContentResolver()失败");
            }

            //注册广播
            myBroad = new MyBroadCastService();
            IntentFilter fiter = new IntentFilter();
            fiter.addAction(Constant.MUSIC_TIME);
            registerReceiver(myBroad, fiter);
            palyMusic(Constant.MUSIC_NEXT, mPosition);
        } else {
            LogUtils.d("不进行播放");
        }
    }

    /**
     * 退出关掉音乐
     */
    private void stopMusic() {
        palyMusic(Constant.MUSIC_BUTTON_PAUSE, mPosition);
    }

    /**
     *  播放音乐
     * @param type      播出类型
     * @param mPosition 播放条数
     */
    private void palyMusic(int type, int mPosition) {
//        LogUtils.w("当前播放音乐:" + mPosition);
//        LogUtils.w("总音乐数量:" + mMusics.size());

        Music music = mMusics.get(mPosition);
        //ToDO 添加音乐播放服务监听
        if (ServiceUtils.Instance().isServiceRunning(this.getApplicationContext(),
                MusicPlayService.class.getName())) {
            LogUtils.d("音乐已经播放了");
            return;
        } else {

            Intent intent = new Intent(getApplicationContext(), MusicPlayService.class);
            intent.putExtra("musicPath", music.getMusicPath());
            intent.putExtra("musicName", music.getMusicName());
            intent.putExtra("type", type);
            startService(intent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.miss.imissyou.mycar/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.miss.imissyou.mycar/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    /**
     * 广播在活动中设置UI参数
     */
    class MyBroadCastService extends BroadcastReceiver {
        int EndTime = 0;

        @Override
        public void onReceive(Context context, Intent intent) {
            int type = intent.getIntExtra("type", 0);
            LogUtils.w("音乐播放的广播:" + type + "结束时间:" + EndTime);
            switch (type) {
                case 0:             //开始播放
                    int endTime = intent.getIntExtra("time", 0);
                    EndTime = endTime;
                    break;
                case 1:            //实时更新时间
                    int playTime = intent.getIntExtra("time", 0);
                    LogUtils.w("当前播放时间:" + playTime);
                    if (playTime >= EndTime - 1000) {
                        LogUtils.w("自动播放下一首");
                        if (mPosition == (mMusics.size() - 1)) {
                            mPosition = 0;
                        } else {
                            mPosition++;
                        }
                        palyMusic(Constant.MUSIC_NEXT, mPosition);
                    } else {

                        String timeStr = StringUtil.timeToString(playTime, "mm:ss");
                        LogUtils.d("播放时间" + timeStr);
                    }
                    break;
                default:
                    LogUtils.d("MusicFragment 并无该项操作");
                    break;
            }
        }
    }
}