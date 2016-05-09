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
import com.miss.imissyou.mycar.util.StringUtil;



import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
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
    private File[] musics;
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
                holder.setText(R.id.textview_music_name,music.getMusicName());
                holder.setText(R.id.tv_music_auther,music.getMusicArtist());
//                holder.setImage(R.id.menu_item_image,music.getMusicImage());
            }
        });
    }


    /**
     * 获取本地音乐列表
     * @return
     */
    private void getLocalMusicList() {

        String[] proj = { MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.TITLE, };
        Cursor cursor = getActivity().getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        if (cursor == null || cursor.getCount() == 0) {
            LogUtils.d("扫描不到音乐文件");
            return;
        }

        for (int i = 0; i < cursor.getCount() ; i ++ ) {
            Music tmpMusic = new Music();
            cursor.moveToNext();

            long id = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media._ID));   //音乐id

            String title = cursor.getString((cursor
                    .getColumnIndex(MediaStore.Audio.Media.TITLE)));//音乐标题

            String artist = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ARTIST));//艺术家

            long duration = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DURATION));//时长

            long size = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.SIZE));  //文件大小

            String url = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DATA));  //文件路径

            String album = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ALBUM)); //唱片图片

            long album_id = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)); //唱片图片ID

            int isMusic = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));//是否为音乐

            if (isMusic != 0 && duration/(1000 * 60) >= 1) {        //只把1分钟以上的音乐添加到集合当中

                tmpMusic.setMusicID(id);
                tmpMusic.setMusicTitle(title);
                tmpMusic.setMusicArtist(artist);
                tmpMusic.setMusicTime(duration);
                tmpMusic.setMusicName(title);
                tmpMusic.setMusicPath(url);
                tmpMusic.setAlbum(album);
                tmpMusic.setAlbum_id(album_id);
                mMusics.add(tmpMusic);
            }
        }
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
        final File music = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        musics = music.listFiles();

        /**
         * 打印信息
         */
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        String artName = null;
        Bitmap userImage = null;

        if (musics == null) {
            LogUtils.d("sd卡无歌曲");
        } else {
            for (File item : musics) {
                Music tempMusic = new Music();
                tempMusic.setMusicPath(item.getAbsolutePath());
                tempMusic.setMusicName(item.getName());
                mmr.setDataSource(item.getAbsolutePath());
                /**
                 * 获取歌手名
                 */
                if (null != mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)) {
                    artName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                } else {
                    artName = "<未知>";
                }
                tempMusic.setMusicArtist(artName);

                userImage = android.graphics.BitmapFactory.decodeByteArray(
                        mmr.getEmbeddedPicture(), 0, mmr.getEmbeddedPicture().length);
                tempMusic.setMusicImage(userImage);
                mMusics.add(tempMusic);
                LogUtils.d("歌曲位置::-->" + item);
            }
        }

        /**
         * 通过扫描全局获取音乐文件
         */
        if (mMusics.size() == 0) {
            getLocalMusicList();
            LogUtils.d("歌曲数量:::" + mMusics.size());
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
                if (mPosition != (musics.length - 1)) {
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
        Intent intent = new Intent(getActivity().getApplicationContext() ,MusicPlayService.class);
        intent.putExtra("musicPath",musics[mPosition].getAbsolutePath());
        intent.putExtra("musicName",musics[mPosition].getName());
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
