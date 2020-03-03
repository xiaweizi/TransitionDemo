package com.xiaweizi.transitiondemo.transition;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
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
public class ChangeBackgroundColorTransition extends Transition {

    private static String PROPNAME_COLOR = "xiaweizi:ChangeBackgroundColor:color";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        if (transitionValues == null) return;
        View view = transitionValues.view;
        transitionValues.values.put(PROPNAME_COLOR, view.getBackground());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        if (transitionValues == null) return;
        View view = transitionValues.view;
        transitionValues.values.put(PROPNAME_COLOR, view.getBackground());
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {
        final View endView = endValues.view;
        ColorDrawable startColorDrawable = (ColorDrawable) startValues.values.get(PROPNAME_COLOR);
        ColorDrawable endColorDrawable = (ColorDrawable) endValues.values.get(PROPNAME_COLOR);
        if (startColorDrawable == null || endColorDrawable == null) return super.createAnimator(sceneRoot, startValues, endValues);
        final int startColor = startColorDrawable.getColor();
        final int endColor = endColorDrawable.getColor();

        ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                endView.setBackgroundColor(animatedValue);
            }
        });
        return animator;
    }
}
