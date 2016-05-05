package com.miss.imissyou.mycar.util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.miss.imissyou.mycar.bean.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Imissyou on 2016/4/23.
 */
public class FindSongs {


    public List<Music> getSongInfo(ContentResolver contentResolver) {
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        List<Music> mMusics = new ArrayList<Music>();
        for (int i = 0; i < cursor.getCount(); i++) {


            cursor.moveToNext();
            if (cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC)) != 0) {


            Music music = new Music();
            music.setMusicTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            music.setMusicArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            music.setMusicTime(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
            music.setMusicSize(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)));
            music.setMusicPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
//            music.setMusicImage(cursor.getExtras());
                mMusics.add(music);
            }

        }
        return mMusics;
    }
}
