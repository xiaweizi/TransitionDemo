package com.xiaweizi.transitiondemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xiaweizi.transitiondemo.R;
import com.xiaweizi.transitiondemo.TransitionUtils;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.xiaweizi.transitiondemo.view.CloudDrawable
 *     e-mail : 1012126908@qq.com
 *     time   : 2020/07/03
 *     desc   :
 * </pre>
 */
public class CloudDrawable extends Drawable {

    private Bitmap mBitmap;
    private Paint mPaint;
    private Context mContext;


    public CloudDrawable(Context context) {
        mContext = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.cloud, options);
        int bitmapWidth = options.outWidth;
        int bitmapHeight = options.outHeight;
        Log.i("xiaweizi::", "draw: " + getBounds().toShortString());
        Log.i("xiaweizi::", "bitmap width: " + options.outWidth + ", bitmap height: " + options.outHeight);
        if (getBounds().width() < bitmapWidth || getBounds().height() < bitmapHeight) {
            float ratioHeight = bitmapHeight * 1.0f / getBounds().height();
            float ratioWidth = bitmapWidth * 1.0f / getBounds().width();
            Log.i("xiaweizi::", "draw: " + ratioWidth + ", height: " + ratioHeight);
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = 2;
        options.inScaled = false;
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.cloud, options);
        Log.i("xiaweizi::", "bitmap width: " + options.outWidth + ", bitmap height: " + options.outHeight);
        Log.w("xiaweizi::", "bitmap width: " + mBitmap.getWidth() + ", bitmap height: " + mBitmap.getHeight());
        mPaint.setColor(TransitionUtils.getColor(7));
        canvas.drawRect(getBounds(), mPaint);
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setBounds(@NonNull Rect bounds) {
        super.setBounds(bounds);
        Log.i("xiaweizi::", "setBounds: " + bounds.toShortString());
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
