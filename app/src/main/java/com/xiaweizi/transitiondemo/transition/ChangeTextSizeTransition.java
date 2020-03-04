package com.xiaweizi.transitiondemo.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Property;
import android.util.TypedValue;
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
public class ChangeTextSizeTransition extends Transition {

    private static String PROPNAME_TEXT_SIZE = "xiaweizi:changeTextSize:size";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        if (transitionValues == null) return;
        View view = transitionValues.view;
        transitionValues.values.put(PROPNAME_TEXT_SIZE, ((TextView) view).getTextSize());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        if (transitionValues == null) return;
        View view = transitionValues.view;
        transitionValues.values.put(PROPNAME_TEXT_SIZE, ((TextView) view).getTextSize());
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {
        final TextView endView = (TextView) endValues.view;
        final float startTextSize = (float) startValues.values.get(PROPNAME_TEXT_SIZE);
        final float endTextSize = (float) endValues.values.get(PROPNAME_TEXT_SIZE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(endView, new TextSizeProperty(), startTextSize, endTextSize);
        animator.setDuration(300);
        return animator;
    }

    private class TextSizeProperty extends Property<TextView, Float> {

        public TextSizeProperty() {
            super(Float.class, "textSize");
        }

        @Override
        public void set(TextView object, Float value) {
            object.setTextSize(TypedValue.COMPLEX_UNIT_PX, value);
        }

        @Override
        public Float get(TextView object) {
            return object.getTextSize();
        }
    }
}
