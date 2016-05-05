package com.miss.imissyou.mycar.util;


import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Audio;

import com.lidroid.xutils.db.sqlite.CursorUtils;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.Music;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 扫描系统音乐文件
 * Created by Imissyou on 2016/4/23.
 */
public class ScannerMusic {

    private static ScannerMusic mScannerMusic;
    private static List<Music> mMusic = new ArrayList<Music>();

    private static ContentResolver mContentResolver;
    private Uri url = Audio.Media.EXTERNAL_CONTENT_URI;

    //projection：选择的列; where：过滤条件; sortOrder：排序。
    private String[] projection = {
            Audio.Media._ID,
            Audio.Media.DISPLAY_NAME,
            Audio.Media.DATA,
            Audio.Media.ALBUM,
            Audio.Media.ARTIST,
            Audio.Media.DURATION,
            Audio.Media.SIZE
    };

    private String where = "mime_type in ('audio/mpeg','audio/x-ms-wma') " +
            "and bucket_display_name <> 'audio' and is_music > 0 ";

    private String sortOrder  = Audio.Media.DATA;

    /**
     * 单例模式
     * @param pComtentResolver
     * @return
     */
    public static ScannerMusic intance(ContentResolver pComtentResolver) {
        if (mScannerMusic == null) {
            mContentResolver = pComtentResolver;
            mScannerMusic = new ScannerMusic();
        }
        return mScannerMusic;
    }

    private ScannerMusic() {
        Cursor cursor = mContentResolver.query(url, projection, where, null, sortOrder);

        if (cursor == null) {
            LogUtils.d("音乐文件为空");
        } else if (!cursor.moveToNext()) {
            int displayNameCol = cursor.getColumnIndex(Audio.Media.DISPLAY_NAME);
            int albumCol = cursor.getColumnIndex(Audio.Media.ALBUM);
            int idCol = cursor.getColumnIndex(Audio.Media._ID);
            int durationCol = cursor.getColumnIndex(Audio.Media.DURATION);
            int sizeCol = cursor.getColumnIndex(Audio.Media.SIZE);
            int artistCol = cursor.getColumnIndex(Audio.Media.ARTIST);
            int urlCol = cursor.getColumnIndex(Audio.Media.DATA);
            do{
                String title = cursor.getString(displayNameCol);
                String album = cursor.getString(albumCol);
                long id = cursor.getLong(idCol);
                int duration = cursor.getInt(durationCol);
                long size = cursor.getLong(sizeCol);
                String artist = cursor.getString(artistCol);
                String url = cursor.getString(urlCol);

                Music musicInfo = new Music();
                musicInfo.setMusicTitle(title);
                musicInfo.setMusicID(id);
                musicInfo.setMusicTime((long) duration);
                musicInfo.setMusicSize(size);
                musicInfo.setMusicArtist(artist);
                musicInfo.setMusicPath(url);
                musicInfo.setAlbum(album);
                mMusic.add(musicInfo);

            }while(cursor.moveToNext());
        }
    }

    public List<Music> getMusics() {
        return mMusic;
    }

}
