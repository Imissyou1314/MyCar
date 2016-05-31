package com.miss.imissyou.mycar.ui;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lidroid.xutils.util.LogUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * 滑动删除
 * Created by Imissyou on 2016/5/12.
 */
public class MissSwipDismissListView extends ListView {

    /**
     * 认为是用户滑动的最小距离
     */
    private int mSlop;
    /**
     * 滑动的最小速度
     */
    private int mMinFlingVelocity;
    /**
     * 滑动的最大速度
     */
    private int mMaxFlingVelocity;
    /**
     * 执行动画的时间
     */
    private long mAnimationTime = 150;
    /**
     * 用来标记用户是否正在滑动中
     */
    private boolean mSwiping;
    /**
     * 滑动速度检测类
     */
    private VelocityTracker mVelocityTracker;
    /**
     * 手指按下的position
     */
    private int mDownPosition;
    /**
     * 按下的item对应的View
     */
    private View mDownView;
    private float mDownX;
    private float mDownY;
    /**
     * item的宽度
     */
    private int mViewWidth;
    /**
     * 当ListView的Item滑出界面回调的接口
     */
    private OnDismissCallback onDismissCallback;

    /**
     * 设置动画时间
     * @param mAnimationTime  动画时间
     */
    public void setmAnimationTime(long mAnimationTime) {
        if (mAnimationTime > 0)
            this.mAnimationTime = mAnimationTime;
    }

    /**
     * 设置滑动删除的接口
     * @param onDismissCallback
     */
    public void setOnDismissCallback(OnDismissCallback onDismissCallback) {
        this.onDismissCallback = onDismissCallback;
    }

    public MissSwipDismissListView(Context context) {
       this(context, null);
    }

    public MissSwipDismissListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MissSwipDismissListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        ViewConfiguration vc = ViewConfiguration.get(context);
        mSlop = vc.getScaledTouchSlop();
        //获取最小的滑动速度
        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity() * 8;
        //获取最大的滑动速度
        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
    }

    @Override public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :
                handleActionDown(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                return handleActionMove(ev);
            case MotionEvent.ACTION_UP :
                handleActionUp(ev);
                break;
        }

        return super.onTouchEvent(ev);
    }

    /**
     * 手指抬起的事件处理
     * @param ev
     */
    private void handleActionUp(MotionEvent ev) {
        if (mVelocityTracker == null || mDownView == null || !mSwiping) {
            return;
        }

        float deltaX = ev.getX() - mDownX;

        //通过滑动的距离计算出X,Y方向的速度
        mVelocityTracker.computeCurrentVelocity(1000);
        float velocityX = Math.abs(mVelocityTracker.getXVelocity());
        float velocityY = Math.abs(mVelocityTracker.getYVelocity());

        //item 是否滑出屏幕
        boolean dismiss = false;
        //是否右边删除
        boolean dismissRight = false;

        //当拖动item的距离大于item的一半，item滑出屏幕
        if (Math.abs(deltaX) > mViewWidth / 2) {
            dismiss = true;
            dismissRight = deltaX > 0;

            //手指在屏幕滑动的速度在某个范围内，也使得item滑出屏幕
        } else if (mMinFlingVelocity <= velocityX
                && velocityX <= mMaxFlingVelocity && velocityY < velocityX) {
            dismiss = true;
            dismissRight = mVelocityTracker.getXVelocity() > 0;
        }

        if (dismiss) {
            ViewPropertyAnimator.animate(mDownView)
                    .translationX(dismissRight ? mViewWidth : -mViewWidth)//X轴方向的移动距离
                    .alpha(0)
                    .setDuration(mAnimationTime)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            //Item滑出界面之后执行删除
                            performDismiss(mDownView, mDownPosition);
                        }
                    });
        } else {
            //将item滑动至开始位置
            ViewPropertyAnimator.animate(mDownView)
                    .translationX(0)
                    .alpha(1)
                    .setDuration(mAnimationTime).setListener(null);
        }

        //移除速度检测
        if(mVelocityTracker != null){
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }

        mSwiping = false;
    }

    /**
     * 处理手指滑动的方法
     *
     * @param ev
     * @return
     */
    private boolean handleActionMove(MotionEvent ev) {
        if (mVelocityTracker == null || mDownView == null) {
            return super.onTouchEvent(ev);
        }

        // 获取X方向滑动的距离
        float deltaX = ev.getX() - mDownX;
        float deltaY = ev.getY() - mDownY;

        // X方向滑动的距离大于mSlop并且Y方向滑动的距离小于mSlop，表示可以滑动
        if (Math.abs(deltaX) > mSlop && Math.abs(deltaY) < mSlop) {
            mSwiping = true;

            //当手指滑动item,取消item的点击事件，不然我们滑动Item也伴随着item点击事件的发生
            MotionEvent cancelEvent = MotionEvent.obtain(ev);
            cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                    (ev.getActionIndex()<< MotionEvent.ACTION_POINTER_INDEX_SHIFT));
            onTouchEvent(cancelEvent);
        }

        if (mSwiping) {
            // 跟谁手指移动item
            ViewHelper.setTranslationX(mDownView, deltaX);
            // 透明度渐变
            ViewHelper.setAlpha(mDownView, Math.max(0f, Math.min(1f, 1f - 2f * Math.abs(deltaX)/ mViewWidth)));

            // 手指滑动的时候,返回true，表示SwipeDismissListView自己处理onTouchEvent,其他的就交给父类来处理
            return true;
        }

        return super.onTouchEvent(ev);

    }

    /**
     * 按下事件处理
     *
     * @param ev
     * @return
     */
    private void handleActionDown(MotionEvent ev) {
        mDownX = ev.getX();
        mDownY = ev.getY();

        mDownPosition = pointToPosition((int) mDownX, (int) mDownY);
        if (mDownPosition == AdapterView.INVALID_POSITION)
            return;

        mDownView = getChildAt(mDownPosition - getFirstVisiblePosition());

        if (null != mDownView) {
            mViewWidth = mDownView.getWidth();
        }

        //加入速度检测
        mVelocityTracker = VelocityTracker.obtain();
        mVelocityTracker.addMovement(ev);
    }


    /**
     * 在此方法中执行item删除之后，其他的item向上或者向下滚动的动画，并且将position回调到方法onDismiss()中
     * @param dismissView
     * @param dismissPosition
     */
    private void performDismiss(final View dismissView, final int dismissPosition) {

        //获取item的参数
        final ViewGroup.LayoutParams lp = dismissView.getLayoutParams();
        //item 的高度
        final int originalHeight = dismissView.getHeight();

        ValueAnimator animator = ValueAnimator.ofFloat(originalHeight, 0)
                .setDuration(mAnimationTime);
        animator.start();

        animator.addListener(new AnimatorListenerAdapter() {

            @Override public void onAnimationEnd(Animator animation) {
                if (onDismissCallback != null) {
                    onDismissCallback.onDismiss(dismissPosition);
                }
                //这段代码很重要，因为我们并没有将item从ListView中移除，而是将item的高度设置为0
                //所以我们在动画执行完毕之后将item设置回来
                ViewHelper.setAlpha(dismissView, 1f);
                ViewHelper.setTranslationX(dismissView, 0);
                ViewGroup.LayoutParams lp = dismissView.getLayoutParams();
                lp.height = originalHeight;
                dismissView.setLayoutParams(lp);
            }
        });

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //这段代码的效果是ListView删除某item之后，其他的item向上滑动的效果
                String number = valueAnimator.getAnimatedValue() + "";
                number = number.substring(0, number.indexOf("."));
                LogUtils.d(number);
                lp.height = Integer.parseInt(number);

                dismissView.setLayoutParams(lp);
            }
        });
    }

    /**
     * 滑动删除的回调接口
     * Created by Imissyou on 2016/5/12.
     */
    public interface OnDismissCallback {
        void onDismiss(int dismissPosition);
    }
}
