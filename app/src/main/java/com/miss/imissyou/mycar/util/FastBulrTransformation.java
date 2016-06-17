package com.miss.imissyou.mycar.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by Imissyou on 2016/6/17.
 */
public class FastBulrTransformation extends BitmapTransformation {

    private static final String ID = "com.miss.glide.FastBulrTransformation";

    private static  final  int DEFAULT_RADIUS = 50;     //默认模糊度
    private static final int DEFAULT_WIDTH = 200;      //默认宽度
    private static final int DEFAULT_HEIGT = 200;      //默认高度

    private int mRadius;

    private int width;              //宽度
    private int higth;              //高度

    public FastBulrTransformation(BitmapPool bitmapPool) {
        super(bitmapPool);
    }

    public FastBulrTransformation(Context context, int mRadius) {
        super(context);
        this.mRadius = mRadius;
        width = DEFAULT_WIDTH;
        higth = DEFAULT_HEIGT;
    }
    public FastBulrTransformation(Context context, int mRadius, int width, int higth) {
        super(context);
        this.mRadius = mRadius;
        this.width = width;
        this.higth = higth;
    }

    public int getmRadius() {
        return mRadius;
    }

    public void setmRadius(int mRadius) {
        this.mRadius = mRadius;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap result = pool.get(width,higth, Bitmap.Config.ARGB_8888);
        if (result == null){
            result = Bitmap.createBitmap(width,higth, Bitmap.Config.ARGB_8888);
        }

        result = FastBlur.doBlur(result,mRadius,true);

        // Create a Canvas backed by the result Bitmap.
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setAlpha(128);
        // Draw the original Bitmap onto the result Bitmap with a transformation.
        canvas.drawBitmap(toTransform, 0, 0, paint);

        return result;
    }

    @Override
    public String getId() {
        return ID;
    }
}
