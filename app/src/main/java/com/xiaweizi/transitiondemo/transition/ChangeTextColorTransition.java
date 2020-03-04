package com.xiaweizi.transitiondemo.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Property;
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
public class ChangeTextColorTransition extends Transition {

    private static String PROPNAME_TEXT_COLOR = "xiaweizi:changeTextColor:color";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        if (transitionValues == null) return;
        View view = transitionValues.view;
        transitionValues.values.put(PROPNAME_TEXT_COLOR, ((TextView) view).getCurrentTextColor());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        if (transitionValues == null) return;
        View view = transitionValues.view;
        transitionValues.values.put(PROPNAME_TEXT_COLOR, ((TextView) view).getCurrentTextColor());
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {
        final TextView endView = (TextView) endValues.view;
        final int startTextColor = (int) startValues.values.get(PROPNAME_TEXT_COLOR);
        final int endTextColor = (int) endValues.values.get(PROPNAME_TEXT_COLOR);
        ObjectAnimator animator = ObjectAnimator.ofArgb(endView, new TextColorProperty(), startTextColor, endTextColor);
        animator.setDuration(300);
        return animator;
    }

    private class TextColorProperty extends Property<TextView, Integer> {

        public TextColorProperty() {
            super(Integer.class, "textColor");
        }

        @Override
        public void set(TextView object, Integer value) {
            object.setTextColor(value);
        }

        @Override
        public Integer get(TextView object) {
            return object.getCurrentTextColor();
        }
    }
}
