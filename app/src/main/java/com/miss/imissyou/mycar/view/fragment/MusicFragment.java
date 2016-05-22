package com.miss.imissyou.mycar.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.service.impl.MusicPlayService;
import com.miss.imissyou.mycar.bean.Music;
import com.miss.imissyou.mycar.ui.adapterutils.CommonAdapter;
import com.miss.imissyou.mycar.ui.adapterutils.ViewHolder;
import com.miss.imissyou.mycar.ui.sidemenu.interfaces.ScreenShotable;
import com.miss.imissyou.mycar.util.Constant;
import com.miss.imissyou.mycar.util.FindSongs;
import com.miss.imissyou.mycar.util.StringUtil;
import com.miss.imissyou.mycar.util.ToastUtil;


import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 音乐列表播放界面
 * Created by Imissyou on 2016/3/22.
 */
public class MusicFragment extends Fragment implements ScreenShotable {

    private CheckBox mBtnBeforeMusic;
    private CheckBox mBtnPauseMusic;
    private CheckBox mBtnNextMusic;
    private SeekBar mSeekBar;
    private ListView mListView;
    private TextView mTextViewAllTime;
    private TextView mTextViewCurrentTime;
    private TextView mTextViewMusicName;
    private MyBroadCastService myBroad;
    private int mPosition;
    private boolean flag;

    private View upView;
    /**
     * 我的音乐
     */
    private List<Music> mMusics = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_list, container, false);

        initView(view);
        intitData();
        addViewListener();
        showView();
        return view;
    }

    /**
     * 展现数据
     */
    private void showView() {
        /**
         * 设置Item
         */
        mListView.setAdapter(new CommonAdapter<Music>(getActivity(), mMusics, R.layout.item_music) {
            @Override public void convert(ViewHolder holder, Music music) {
                holder.setText(R.id.tv_music_auther,music.getMusicArtist());
                if (music.getMusicName() == null || music.getMusicName().equals("")) {
                    holder.setText(R.id.textview_music_name,music.getMusicName());
                } else {
                    holder.setText(R.id.textview_music_name, music.getMusicTitle());
                }
            }
        });
    }

    /**
     * findViewByID
     * @param view
     */
    private void initView(View view) {
        this.upView = view;

        mBtnBeforeMusic = (CheckBox) view.findViewById(R.id.btn_brfore_music);
        mBtnNextMusic = (CheckBox) view.findViewById(R.id.btn_next_music);
        mBtnPauseMusic = (CheckBox) view.findViewById(R.id.btn_pause_music);

        mListView = (ListView) view.findViewById(R.id.listview);
        mSeekBar = (SeekBar) view.findViewById(R.id.seekbar);
        mTextViewAllTime = (TextView) view.findViewById(R.id.textview_all_time);
        mTextViewMusicName = (TextView) view.findViewById(R.id.textview_music_name);
        mTextViewCurrentTime = (TextView) view.findViewById(R.id.textview_current_time);
    }

    /**
     * 加载sdcard下的Music文件
     */
    private void intitData() {
        FindSongs songs = new FindSongs();
        //TODO 获取本地音乐
        if (null != getActivity().getContentResolver()) {
            mMusics = songs.getSongInfo(getActivity().getContentResolver());
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
        getActivity().registerReceiver(myBroad,fiter);
    }


    /**
     * 添加页面触发事件
     */
    public void addViewListener() {
        mBtnNextMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //不是最后一首音乐
                if (mPosition != (mMusics.size() - 1)) {
                    mPosition ++;
                }
                /**
                 * 播放下一首哥
                 */
                palyMusic(Constant.MUSIC_NEXT, mPosition);

            }
        });

        mBtnBeforeMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mPosition != 0)
                    mPosition--;
                /**
                 * 播放上一首歌
                 */
                palyMusic(Constant.MUSIC_PREVIOUS, mPosition);
            }
        });

        mBtnPauseMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent intent = new Intent(getActivity().getApplicationContext(),MusicPlayService.class);
                intent.putExtra("flag",flag);
                intent.putExtra("type",Constant.MUSIC_BUTTON_PAUSE);
                getActivity().startService(intent);
                flag = !flag;
            }
        });

        /**
         * 点击Item 播放
         */
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                palyMusic(Constant.MUSIC_CLICK_START, position);
                mPosition = position;
                view.setBackgroundResource(R.color.color_progress_yello);
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {

            }

            /**
             * 通过滑动进度条设置播放
             * @param seekBar
             */
            @Override public void onStopTrackingTouch(SeekBar seekBar) {
                Intent intent = new Intent(getActivity().getApplicationContext(),MusicPlayService.class);
                intent.putExtra("type",Constant.MUSIC_SEEK);
                intent.putExtra("progress",seekBar.getProgress());
                getActivity().startService(intent);
            }
        });
    }

    private void palyMusic(int type, int mPosition) {
        Music music = mMusics.get(mPosition);
        Intent intent = new Intent(getActivity().getApplicationContext() ,MusicPlayService.class);
        intent.putExtra("musicPath",music.getMusicPath());
        intent.putExtra("musicName",music.getMusicName());
        intent.putExtra("type",type);
        getActivity().startService(intent);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
       try {
           getActivity().unregisterReceiver(myBroad);
       } catch (Exception e) {
           LogUtils.d("该广播没有注册");
       }
    }

    /**
     * 广播在活动中设置UI参数
     */
    class MyBroadCastService extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int type = intent.getIntExtra("type", 0);
            switch (type) {
                case 0:
                    int time = intent.getIntExtra("time", 0);
                    mSeekBar.setMax(time);
                    String strTime = StringUtil.timeToString(time,"mm:ss");
                    mTextViewAllTime.setText(strTime);
                    String name = intent.getStringExtra("name");
                    mTextViewMusicName.setText(name);
                    break;
                case 1:
                    int playTime = intent.getIntExtra("time", 0);
                    mSeekBar.setProgress(playTime);
                    String timeStr = StringUtil.timeToString(playTime,"mm:ss");
                    mTextViewCurrentTime.setText(timeStr);
                    String musicName = intent.getStringExtra("name");
                    mTextViewMusicName.setText(musicName);
                    break;
                default:
                    LogUtils.d("MusicFragment 并无该项操作");
                    break;

            }
        }
    }

    @Override public void takeScreenShot() {

    }

    @Override public Bitmap getBitmap() {
        return null;
    }

}
