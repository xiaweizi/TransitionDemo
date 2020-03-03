package com.xiaweizi.transitiondemo;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * <pre>
 *     author : xiaweizi
 *     e-mail : 1012126908@qq.com
 *     time   : 2020/03/02
 *     desc   :
 * </pre>
 */
public class ChangeTextTransition extends Transition {

    private static String PROPNAME_TEXT = "xiaweizi:changeText:text";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        if (transitionValues == null) return;
        View view = transitionValues.view;
        transitionValues.values.put(PROPNAME_TEXT, ((TextView) view).getText());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        if (transitionValues == null) return;
        View view = transitionValues.view;
        transitionValues.values.put(PROPNAME_TEXT, ((TextView) view).getText());
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {
        final TextView endView = (TextView) endValues.view;
        final CharSequence startText = (CharSequence) startValues.values.get(PROPNAME_TEXT);
        final CharSequence endText = (CharSequence) endValues.values.get(PROPNAME_TEXT);
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1f);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                if (animatedValue <= 0.5f) {
                    endView.setText(startText);
                    float ratio = (0.5f - animatedValue) / 0.5f;
                    endView.setAlpha(ratio);
                } else {
                    endView.setText(endText);
                    float ratio = (animatedValue - 0.5f) / 0.5f;
                    endView.setAlpha(ratio);
                }
            }
        });
        return animator;
    }
}
