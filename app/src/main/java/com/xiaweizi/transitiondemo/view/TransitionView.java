package com.xiaweizi.transitiondemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.xiaweizi.transitiondemo.R;
import com.xiaweizi.transitiondemo.TransitionUtils;

/**
 * <pre>
 *     author : xiaweizi
 *     e-mail : 1012126908@qq.com
 *     time   : 2020/03/02
 *     desc   :
 * </pre>
 */
public class TransitionView extends View {
    private float mRatio = 1f;
    private Paint mTextPaint;
    private int mStartColor;
    private int mEndColor;
    private Rect mRect;

    public TransitionView(Context context) {
        this(context, null);
    }

    public TransitionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransitionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs, defStyle);
    }

    private void initView(Context context, AttributeSet attrs, int defStyle) {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size));
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mStartColor = Color.WHITE;
        mEndColor = Color.parseColor("#EC407A");
        mRect = new Rect();
    }

    public void setRatio(float ratio) {
        this.mRatio = ratio;
        invalidate();
    }

    public float getRatio() {
        return mRatio;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制左边
        canvas.save();
        mRect.set(0, 0, (int) (getWidth() * mRatio), getHeight());
        canvas.clipRect(mRect);
        mTextPaint.setColor(mStartColor);
        TransitionUtils.drawTextCenter(canvas, "文本三", getWidth() / 2, getHeight() / 2, mTextPaint);
        canvas.restore();

        // 绘制右边
        canvas.save();
        mRect.set((int) (getWidth() * mRatio), 0, getWidth(), getHeight());
        canvas.clipRect(mRect);
        mTextPaint.setColor(mEndColor);
        TransitionUtils.drawTextCenter(canvas, "三本文", getWidth() / 2, getHeight() / 2, mTextPaint);
        canvas.restore();
    }
}
