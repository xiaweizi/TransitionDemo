package com.xiaweizi.transitiondemo;

import android.app.Application;
import android.content.Context;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.xiaweizi.transitiondemo.MyApplication
 *     e-mail : 1012126908@qq.com
 *     time   : 2020/03/03
 *     desc   :
 * </pre>
 */
public class MyApplication extends Application {

    private static Context sContext;

    public static Context getInstance() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}
