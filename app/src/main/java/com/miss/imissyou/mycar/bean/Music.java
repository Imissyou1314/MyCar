package com.miss.imissyou.mycar.bean;

import android.graphics.Bitmap;

/**
 * Created by Imissyou on 2016/3/11.
 *
 * 音乐实体类
 */
public class Music extends BaseBean{

    /**音乐存放的地址*/
    private String musicPath;
    /**音乐名*/
    private String musicName;
    /**音乐艺术家*/
    private String musicArtist;
    /**音乐存放的标题*/
    private String musicTitle;
    /**音乐ID*/
    private Long musicID;
    /**音乐播放时长*/
    private Long musicTime;
    /**音乐文件大小*/
    private Long musicSize;
    /**音乐图片*/
    private Bitmap musicImage;
    /**唱片图片*/
    private String album;
    /**唱片图片ID*/
    private Long album_id;

    public Long getMusicID() {
        return musicID;
    }

    public void setMusicID(Long musicID) {
        this.musicID = musicID;
    }



    public String getMusicTitle() {
        return musicTitle;
    }

    public void setMusicTitle(String musicTitle) {
        this.musicTitle = musicTitle;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }

    public String getMusicArtist() {
        return musicArtist;
    }

    public void setMusicArtist(String musicArtist) {
        this.musicArtist = musicArtist;
    }

    public Long getMusicSize() {
        return musicSize;
    }

    public void setMusicSize(Long musicSize) {
        this.musicSize = musicSize;
    }

    public Long getMusicTime() {
        return musicTime;
    }

    public void setMusicTime(Long musicTime) {
        this.musicTime = musicTime;
    }

    public Bitmap getMusicImage() {
        return musicImage;
    }

    public void setMusicImage(Bitmap musicImage) {
        this.musicImage = musicImage;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Long getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(Long album_id) {
        this.album_id = album_id;
    }
}
