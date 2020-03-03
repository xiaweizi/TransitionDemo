package com.xiaweizi.transitiondemo;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;

/**
 * <pre>
 *     author : xiaweizi
 *     e-mail : 1012126908@qq.com
 *     time   : 2020/03/02
 *     desc   :
 * </pre>
 */
public class ChangeBackgroundAlphaTransition extends Transition {

    private static String PROPNAME_BACKGROUND = "xiaweizi:changeBackgroundAlpha:background";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        if (transitionValues == null) return;
        View view = transitionValues.view;
        transitionValues.values.put(PROPNAME_BACKGROUND, view.getBackground());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        if (transitionValues == null) return;
        View view = transitionValues.view;
        transitionValues.values.put(PROPNAME_BACKGROUND, view.getBackground());
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {
        final View endView = endValues.view;
        final ColorDrawable startColor = (ColorDrawable) startValues.values.get(PROPNAME_BACKGROUND);
        final ColorDrawable endColor = (ColorDrawable) endValues.values.get(PROPNAME_BACKGROUND);
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1f);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                if (animatedValue <= 0.5f) {
                    endView.setBackground(startColor);
                    float ratio = (0.5f - animatedValue) / 0.5f;
                    endView.getBackground().setAlpha((int) (255 * ratio));
                } else {
                    endView.setBackground(endColor);
                    float ratio = (animatedValue - 0.5f) / 0.5f;
                    endView.getBackground().setAlpha((int) (255 * ratio));
                }
            }
        });
        return animator;
    }
}
