package com.xiaweizi.transitiondemo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.ViewGroup;

/**
 * <pre>
 *     author : xiaweizi
 *     e-mail : 1012126908@qq.com
 *     time   : 2020/03/02
 *     desc   :
 * </pre>
 */
public class ChangeCustomTransition extends Transition {

    private static String PROPNAME_CUSTOM_RATIO = "xiaweizi:ChangeCustom:ratio";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        if (transitionValues == null) return;
        TransitionView view = (TransitionView) transitionValues.view;
        transitionValues.values.put(PROPNAME_CUSTOM_RATIO, view.getRatio());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        if (transitionValues == null) return;
        TransitionView view = (TransitionView) transitionValues.view;
        transitionValues.values.put(PROPNAME_CUSTOM_RATIO, view.getRatio());
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {
        final TransitionView endView = (TransitionView) endValues.view;
        final float startRatio = (float) startValues.values.get(PROPNAME_CUSTOM_RATIO);
        final float endRatio = (float) endValues.values.get(PROPNAME_CUSTOM_RATIO);
        ObjectAnimator animator = ObjectAnimator.ofFloat(endView, "ratio", startRatio, endRatio);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                Log.i("xiaweizi::", "onAnimationUpdate: " + animatedValue);
            }
        });
        return animator;
    }
}
