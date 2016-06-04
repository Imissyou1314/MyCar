package com.miss.imissyou.mycar.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.miss.imissyou.mycar.R;
import com.miss.imissyou.mycar.ui.sidemenu.interfaces.ScreenShotable;


/**
 * Home fragment
 * ContentFragment
 *
 */
public class ContentFragment extends Fragment implements ScreenShotable {
    public static final String CLOSE = "Close";
    public static final String HOME = "Home";
    public static final String CAR = "Car";
    public static final String ORDER = "Order";
    public static final String BREAK = "Break";
    public static final String SHOP = "Shop";
    public static final String PARTY = "Party";
    public static final String MOVIE = "Movie";
    public static final String USER = "user";
    public static final String MUSIC = "music";
    public static final String MAP = "map";
    public static final String NAVIGATION = "navigation";
    public static final String SETTING = "setting";

    private View containerView;
    protected ImageView mImageView;
    protected static int res;
    private Bitmap bitmap;

    /**
     * 创建ContentFragment
     * @param resId
     * @return
     */
    public static ContentFragment newInstance(int resId) {
        ContentFragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        res = resId;
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        res = getArguments().getInt(Integer.class.getName());
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(res, container, false);
//        mImageView = (ImageView) rootView.findViewById(R.id.image_content);
//        mImageView.setClickable(true);
//        mImageView.setFocusable(true);
//        mImageView.setImageResource(res);
        return rootView;
    }

    @Override public void takeScreenShot() {
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
//                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
//                Canvas canvas = new Canvas(bitmap);
//                containerView.draw(canvas);
//                ContentFragment.this.bitmap = bitmap;
//            }
//        };
//        thread.start();
    }

    @Override public Bitmap getBitmap() {
        return bitmap;
    }
}

