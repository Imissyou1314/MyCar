package com.miss.imissyou.mycar.ui.DashboardView;

/**
 * HighlightColorAndRange
 * 高亮效果的范围和颜色对象
 * Created by Imissyou
 */
public class HighlightCR {

    private int mStartAngle;
    private int mSweepAngle;
    private int mColor;

    public HighlightCR() {

    }

    public HighlightCR(int startAngle, int sweepAngle, int color) {
        this.mStartAngle = startAngle;
        this.mSweepAngle = sweepAngle;
        this.mColor = color;
    }

    public int getStartAngle() {
        return mStartAngle;
    }

    public void setStartAngle(int startAngle) {
        mStartAngle = startAngle;
    }

    public int getSweepAngle() {
        return mSweepAngle;
    }

    public void setSweepAngle(int sweepAngle) {
        mSweepAngle = sweepAngle;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
    }
}
