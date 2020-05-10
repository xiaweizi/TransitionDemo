package com.xiaweizi.transitiondemo.stub.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.xiaweizi.transitiondemo.stub.touch.TouchTestFrameLayout
 *     e-mail : 1012126908@qq.com
 *     time   : 2020/05/10
 *     desc   :
 * </pre>
 */
public class TouchTestFrameLayout extends FrameLayout {

    private TouchDealHelper mHelper;

    public TouchTestFrameLayout(Context context) {
        this(context, null);
    }

    public TouchTestFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchTestFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs, defStyle);
    }

    private void initView(Context context, AttributeSet attrs, int defStyle) {
        mHelper = new TouchDealHelper(context);
    }

    public TouchDealHelper getTouchHelper() {
        return mHelper;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mHelper.onDispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
}
