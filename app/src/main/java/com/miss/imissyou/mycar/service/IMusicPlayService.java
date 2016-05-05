package com.miss.imissyou.mycar.service;

/**
 * 音乐播放器
 *
 * Created by Imissyou on 2016/3/11.
 */
public interface IMusicPlayService {

    /**
     * 启动音乐播放器
     */
    public void onStartMusic();

    /**
     * 停止音乐播放器
     */
    public void onStopMusic();

    /**
     * 暂停音乐播放器
     */
    public void onPasueMusic();
}
