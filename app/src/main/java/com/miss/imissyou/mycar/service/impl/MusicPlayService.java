package com.miss.imissyou.mycar.service.impl;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.util.Constant;

import java.io.IOException;

/**
 * 主动播放音乐的后台服务
 *
 * Created by Imissyou on 2016/3/11.
 */
public class MusicPlayService extends Service{

    /**声明媒体播放器*/
    private MediaPlayer musicPlay;
    /**声明播放进度*/
    private boolean flag;           //标志位
    private String musicName;       //音乐名

    private String musicPath;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d("进入音乐播放");
        int type = intent.getIntExtra("type", Constant.MUSIC_CLICK_START);
        switch (type) {
            case Constant.MUSIC_CLICK_START :
                startMusic(intent);
                break;
            case Constant.MUSIC_SEEK:
                seekToProgress(intent);
                break;
            case Constant.MUSIC_BUTTON_PAUSE :
                pauseMusic(intent);
                break;
            case Constant.MUSIC_BUTTON_START:
                startMusic(intent);
                break;
            case Constant.MUSIC_NEXT :
                startMusic(intent);
                break;
            case Constant.MUSIC_PREVIOUS :
                startMusic(intent);
                break;
            default:
                startMusic(intent);
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 设置播放进度播放
     * @param intent
     */
    private void seekToProgress(Intent intent) {
        int progress = intent.getIntExtra("progress",0);
        musicPlay.seekTo(progress);
    }

    /**
     * 点击暂停  再次点击播放
     * @param intent
     */
    private void pauseMusic(Intent intent) {
        LogUtils.d("停止音乐播放");
        flag = intent.getBooleanExtra("flag",true);

        if (!flag) {
            //涨停音乐播放    线程关闭
            musicPlay.pause();
        } else {
            int keepOn = musicPlay.getCurrentPosition();
            musicPlay.seekTo(keepOn);
            musicPlay.start();
            //开线程
            MusicSeekBar seekBar = new MusicSeekBar();
            seekBar.start();
        }

    }

    /**
     * 开始播放音乐
     * @param intent
     */
    private void startMusic(Intent intent) {
        LogUtils.d("开始音乐播放");
        musicPath = intent.getStringExtra("musicPath");      //获取音乐播放地址
        musicName = intent.getStringExtra("musicName");

        LogUtils.d("播放地址:" + musicPath + "播放名:" + musicName);
        
        if (null == musicPlay) {
            musicPlay = new MediaPlayer();
        }
        musicPlay.reset();
        try {
            musicPlay.setAudioStreamType(AudioManager.STREAM_MUSIC);
            musicPlay.setDataSource(musicPath);
            musicPlay.prepare();
            musicPlay.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    int allTime = musicPlay.getDuration();      //获取音乐时长
                    //发送广播时间参数送过去
                    Intent intent = new Intent(Constant.MUSIC_TIME);
                    intent.putExtra("type",0);
                    intent.putExtra("time",allTime);
                    intent.putExtra("name",musicName);
                    sendBroadcast(intent);

                    //服务中更新音乐进度条
                    MusicSeekBar seekBar = new MusicSeekBar();
                    seekBar.start();
                }
            });
        } catch (IOException e) {
            LogUtils.e("MusicPlayService 音乐播放：：：：出错", e);
            return ;
        }
    }

    /**
     * 更新音乐播放的进程
     */
    class MusicSeekBar extends Thread{

        @Override public void run() {
            super.run();
            while (musicPlay.isPlaying()) {
                int now = musicPlay.getCurrentPosition();
                Intent intent = new Intent(Constant.MUSIC_TIME);
                intent.putExtra("type",1);
                intent.putExtra("time",now);
                sendBroadcast(intent);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    LogUtils.e("MusicPlayService 线程报错" , e);
                }
            }
        }
    }
}
