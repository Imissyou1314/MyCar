package com.miss.imissyou.mycar.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.ui.TitleFragment;
import com.miss.imissyou.mycar.util.FindViewById;

/**
 * 作者
 *
 * Created by Imissyou on 2016/3/23.
 */
public class AuthorActivity extends BaseActivity {
    @FindViewById(id = R.id.author_title)
    private TitleFragment titleView;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_author);
    }

    @Override
    public void addListeners() {
        titleView.setTitleText("关于作者");
    }
}
