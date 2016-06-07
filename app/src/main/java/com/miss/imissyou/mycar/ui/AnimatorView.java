package com.miss.imissyou.mycar.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

import io.codetail.animation.ViewAnimationUtils;


/**
 * Created by Imissyou on 2016/4/27.
 */
public class AnimatorView {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void toggleInformationView(final View view) {

        int cx = (view.getRight() + view.getLeft()) /2;
        int cy = (view.getTop() + view.getBottom()) /2;
        float radius = Math.max(view.getWidth() , view.getHeight()) / 2.0f;
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
            ViewAnimationUtils
                    .createCircularReveal(view, cx, cy, 0, radius)
                    .setDuration(1000)
                    .start();
        } else {
            Animator reveal = ViewAnimationUtils.createCircularReveal(view, cx, cy, radius, 0);
            reveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(View.GONE);
                }
            });
            reveal.setDuration(1000)
                    .start();
        }
    }

    /**
     * 展现视图
     * @param view
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void showView(final View view){
        int cx = (view.getRight() + view.getLeft()) /2;
        int cy = (view.getTop() + view.getBottom()) /2;
        float radius = Math.max(view.getWidth() , view.getHeight()) / 2.0f;
        view.setVisibility(View.VISIBLE);
        ViewAnimationUtils
                .createCircularReveal(view, cx, cy, 0, radius)
                .setDuration(1000)
                .start();
    }

    /**
     * 不显示视图
     * @param view
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void disShowView(final View view) {
        int cx = (view.getRight() + view.getLeft()) /2;
        int cy = (view.getTop() + view.getBottom()) /2;
        float radius = Math.max(view.getWidth() , view.getHeight()) / 2.0f;
        Animator reveal = ViewAnimationUtils.createCircularReveal(view, cx, cy, radius, 0);
        reveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
            }
        });
        reveal.setDuration(1000)
                .start();
    }
}
