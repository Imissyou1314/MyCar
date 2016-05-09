package com.miss.imissyou.mycar.ui.adapterutils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;

import java.util.List;

/**
 * Created by Imissyou on 2016/5/7.
 */
public abstract class GNowListAdapter<T> extends GenericBaseAdapter {

    public GNowListAdapter(Context context, SpeedScrollListener scrollListener, List<T> items, int layoutId) {
        super(context, scrollListener, items, layoutId);
    }



    @Override protected View getAnimatedView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder(mContext,parent,layoutId, position);
        convert(holder, getItem(position));
        v = holder.getmConverView();
        if (v != null && !positionsMapper.get(position) && position > previousPostition) {

            speed = scrollListener.getSpeed();

            animDuration = (((int) speed) == 0) ? ANIM_DEFAULT_SPEED : (long) (1 / speed * 15000);

            if (animDuration > ANIM_DEFAULT_SPEED)
                animDuration = ANIM_DEFAULT_SPEED;

            previousPostition = position;

            v.setTranslationY(height);
            v.setPivotX(width / 2);
            v.setPivotY(height / 2);
            v.setAlpha(0.0F);

            if (position % 2 == 0) {
                v.setTranslationX(-(width / 1.2F));
                v.setRotation(50);
            } else {
                v.setTranslationX((width / 1.2F));
                v.setRotation(-50);
            }

            ViewPropertyAnimator localViewPropertyAnimator =
                    v.animate().rotation(0.0F).translationX(0).translationY(0).setDuration(animDuration).alpha(1.0F)
                            .setInterpolator(interpolator);

            localViewPropertyAnimator.setStartDelay(500).start();

            positionsMapper.put(position, true);
        }
        return v;
    }

    public abstract void convert(ViewHolder holder, Object item);

    @Override protected void defineInterpolator() {
        interpolator = new DecelerateInterpolator();
    }
}
