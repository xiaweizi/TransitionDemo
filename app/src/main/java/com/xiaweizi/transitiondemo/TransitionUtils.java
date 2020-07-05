package com.xiaweizi.transitiondemo;

import android.content.Context;
import android.content.res.Resources;
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
                    R.color.color5, R.color.color6, R.color.color7, R.color.color8, R.color.color9};
    private static final int DEFAULT_APP_TRANSITION_ROUND_CORNER_RADIUS = 84;

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

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    public static float getWindowCornerRadius(Context context) {
        if (context == null) {
            return DEFAULT_APP_TRANSITION_ROUND_CORNER_RADIUS;
        }
        float radius = 0;
        try {
            float radiusTop = 0;
            float radiusBottom = 0;
            int resourceId = context.getResources().getIdentifier("rounded_corner_radius_top", "dimen", "android");
            if (resourceId > 0) {
                radiusTop = context.getResources().getDimensionPixelSize(resourceId);
            }
            resourceId = context.getResources().getIdentifier("rounded_corner_radius_bottom", "dimen", "android");
            if (resourceId > 0) {
                radiusBottom = context.getResources().getDimensionPixelSize(resourceId);
            }
            radius = radiusTop > radiusBottom ? radiusBottom : radiusTop;
        } catch (Throwable e) {
        }

        if (radius <= 0) {
            radius = DEFAULT_APP_TRANSITION_ROUND_CORNER_RADIUS;
        }
        return radius;
    }

    public static int getColor(float fraction, int startValue, int endValue) {
        float startA = ((startValue >> 24) & 0xff) / 255.0f;
        float startR = ((startValue >> 16) & 0xff) / 255.0f;
        float startG = ((startValue >> 8) & 0xff) / 255.0f;
        float startB = (startValue & 0xff) / 255.0f;

        float endA = ((endValue >> 24) & 0xff) / 255.0f;
        float endR = ((endValue >> 16) & 0xff) / 255.0f;
        float endG = ((endValue >> 8) & 0xff) / 255.0f;
        float endB = (endValue & 0xff) / 255.0f;

        // convert from sRGB to linear
        startR = (float) Math.pow(startR, 2.2);
        startG = (float) Math.pow(startG, 2.2);
        startB = (float) Math.pow(startB, 2.2);

        endR = (float) Math.pow(endR, 2.2);
        endG = (float) Math.pow(endG, 2.2);
        endB = (float) Math.pow(endB, 2.2);

        // compute the interpolated color in linear space
        float a = startA + fraction * (endA - startA);
        float r = startR + fraction * (endR - startR);
        float g = startG + fraction * (endG - startG);
        float b = startB + fraction * (endB - startB);

        // convert back to sRGB in the [0..255] range
        a = a * 255.0f;
        r = (float) Math.pow(r, 1.0 / 2.2) * 255.0f;
        g = (float) Math.pow(g, 1.0 / 2.2) * 255.0f;
        b = (float) Math.pow(b, 1.0 / 2.2) * 255.0f;

        return Math.round(a) << 24 | Math.round(r) << 16 | Math.round(g) << 8 | Math.round(b);
    }
}
