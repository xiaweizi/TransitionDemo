package com.xiaweizi.transitiondemo.callback;

import android.view.View;

import com.xiaweizi.transitiondemo.bean.ColorBean;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.xiaweizi.transitiondemo.callback.HomeTransitionCallback
 *     e-mail : 1012126908@qq.com
 *     time   : 2020/07/05
 *     desc   :
 * </pre>
 */
public interface HomeTransitionCallback {
    void onHomeEnter(View targetView, ColorBean bean);
    void onHomeExit(View targetView, ColorBean bean);
}
