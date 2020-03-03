package com.xiaweizi.transitiondemo;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * <pre>
 *     author : xiaweizi
 *     e-mail : 1012126908@qq.com
 *     time   : 2020/03/02
 *     desc   :
 * </pre>
 */
public class ChangeImageResourceTransition extends Transition {

    private static String PROPNAME_IMAGE_RESOURCE = "xiaweizi:ChangeImageResource:image";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        if (transitionValues == null) return;
        ImageView view = (ImageView) transitionValues.view;
        transitionValues.values.put(PROPNAME_IMAGE_RESOURCE, view.getDrawable());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        if (transitionValues == null) return;
        ImageView view = (ImageView) transitionValues.view;
        transitionValues.values.put(PROPNAME_IMAGE_RESOURCE, view.getDrawable());
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {
        final ImageView endView = (ImageView) endValues.view;
        final Drawable startDrawable = (Drawable) startValues.values.get(PROPNAME_IMAGE_RESOURCE);
        final Drawable endDrawable = (Drawable) endValues.values.get(PROPNAME_IMAGE_RESOURCE);
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1f);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                if (animatedValue <= 0.5f) {
                    endView.setImageDrawable(startDrawable);
                    float ratio = (0.5f - animatedValue) / 0.5f;
                    endView.setAlpha(ratio);
                } else {
                    endView.setImageDrawable(endDrawable);
                    float ratio = (animatedValue - 0.5f) / 0.5f;
                    endView.setAlpha(ratio);
                }
            }
        });
        return animator;
    }
}
