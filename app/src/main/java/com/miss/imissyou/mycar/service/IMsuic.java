package com.miss.imissyou.mycar.service;

import android.content.Intent;

/**
 * 音乐播放器的接口
 * Created by Imissyou on 2016/4/14.
 */
public interface IMsuic {

    public void moveon();       //继续
    public void pause();        //暂停
    public void stop();         //停止
    public void nextSong();     //下一曲
    public void lastSong();     //上一曲
}
