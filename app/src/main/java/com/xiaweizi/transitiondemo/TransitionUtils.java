package com.xiaweizi.transitiondemo;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.xiaweizi.transition.TransitionUtils
 *     e-mail : 1012126908@qq.com
 *     time   : 2020/03/01
 *     desc   :
 * </pre>
 */
public class TransitionUtils {

    private static final int[] COLORS =
            new int[]{R.color.color1, R.color.color2, R.color.color3, R.color.color4,
                    R.color.color5, R.color.color6, R.color.color7, R.color.color8};

    public static void toggleViewVisible(View... views) {
        for (View view : views) {
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.INVISIBLE);
            } else {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    public static void drawTextCenter(Canvas canvas, String text, float x, float y, Paint paint) {
        if (!TextUtils.isEmpty(text) && canvas != null && paint != null) {
            canvas.drawText(text, x, y - ((paint.descent() + paint.ascent()) / 2), paint);
        }
    }

    public static int getColor(int index) {
        int length = COLORS.length;
        if (index >= 0 && index < length) {
            return MyApplication.getInstance().getResources().getColor(COLORS[index], null);
        }
        return MyApplication.getInstance().getResources().getColor(COLORS[0], null);
    }
}
