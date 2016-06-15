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
import com.miss.imissyou.mycar.view.BackHandledInterface;


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
    public static final String OIL = "oil";
    public static final String PARK = "park";           //停车场
    public static final String BREAK = "Break";
//    public static final String USER = "user";
    public static final String MUSIC = "music";
//    public static final String MAP = "map";
    public static final String NAVIGATION = "navigation";
    public static final String FIX = "fix";         //维修站

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
        this.containerView = view.findViewById(R.id.container_frame);
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

    }

    @Override public Bitmap getBitmap() {
        return bitmap;
    }

}

