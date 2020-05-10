package com.xiaweizi.transitiondemo.stub.touch;

import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.xiaweizi.transitiondemo.stub.touch.TouchDealHelper
 *     e-mail : 1012126908@qq.com
 *     time   : 2020/05/10
 *     desc   :
 * </pre>
 */
public class TouchDealHelper {

    private float mDownX, mDownY;
    private OnTouchEventListener mListener;
    private Rect mRect;
    private boolean mHasCancel, mHasViewClick;
    private float mTouchSlop;
    private View mChildView;

    public TouchDealHelper(Context context) {
        if (context != null) {
            ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
            mTouchSlop = viewConfiguration.getScaledTouchSlop();
        }
        mRect = new Rect();
    }

    public void setTouchEventListener(View view, OnTouchEventListener listener) {
        this.mListener = listener;
        this.mChildView = view;
    }

    public void onDispatchTouchEvent(MotionEvent ev) {
        if (mChildView == null) {
            return;
        }
        int actionMasked = ev.getActionMasked();
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getRawX();
                mDownY = ev.getRawY();
                mChildView.getGlobalVisibleRect(mRect);
                if (mRect.contains(((int) mDownX), (int) mDownY)) {
                    mHasViewClick = true;
                    mHasCancel = false;
                    if (mListener != null) {
                        mListener.onTouchEvent(MotionEvent.ACTION_DOWN);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mHasViewClick) {
                    float deltaX = Math.abs(ev.getRawX() - mDownX);
                    float deltaY = Math.abs(ev.getRawY() - mDownY);
                    if (deltaX <= mTouchSlop && deltaY <= mTouchSlop) {
                        if (mListener != null) {
                            mListener.onTouchEvent(MotionEvent.ACTION_UP);
                            mListener.onClick();
                        }
                    }
                }
                mHasViewClick = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                if (mHasViewClick && mListener != null) {
                    mListener.onTouchEvent(MotionEvent.ACTION_CANCEL);
                }
                mHasViewClick = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mHasCancel) {
                    break;
                }
                if (mHasViewClick) {
                    float deltaX = Math.abs(ev.getRawX() - mDownX);
                    float deltaY = Math.abs(ev.getRawY() - mDownY);
                    if (deltaX > mTouchSlop || deltaY > mTouchSlop) {
                        mHasCancel = true;
                        if (mListener != null) {
                            mListener.onTouchEvent(MotionEvent.ACTION_CANCEL);
                        }
                    } else {
                        if (mListener != null) {
                            mListener.onTouchEvent(MotionEvent.ACTION_MOVE);
                        }
                    }
                }
                break;
        }
    }

    public interface OnTouchEventListener {
        /**
         * 处理点击事件
         */
        void onClick();

        /**
         * 处理点击效果
         */
        void onTouchEvent(int eventType);
    }
}
