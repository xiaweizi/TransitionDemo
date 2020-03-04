package com.xiaweizi.transitiondemo.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Typeface;
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
public class ChangeTextTypefaceTransition extends Transition {

    private static String PROPNAME_TEXT_SIZE = "xiaweizi:changeTextTypeface:typeface";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        if (transitionValues == null) return;
        View view = transitionValues.view;
        int level = 1;
        if (view.getTag() instanceof String) {
            level = Integer.parseInt((String) view.getTag());
        } else {
            level = (int) view.getTag();
        }
        transitionValues.values.put(PROPNAME_TEXT_SIZE, level);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        if (transitionValues == null) return;
        View view = transitionValues.view;
        int level = 1;
        if (view.getTag() instanceof String) {
            level = Integer.parseInt((String) view.getTag());
        } else {
            level = (int) view.getTag();
        }
        transitionValues.values.put(PROPNAME_TEXT_SIZE, level);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {
        final TextView endView = (TextView) endValues.view;
        final int startTextLevel = (int) startValues.values.get(PROPNAME_TEXT_SIZE);
        final int endTextSizeLevel = (int) endValues.values.get(PROPNAME_TEXT_SIZE);
        ObjectAnimator animator = ObjectAnimator.ofInt(endView, new TextTypeFaceProperty(), startTextLevel, endTextSizeLevel);
        animator.setDuration(300);
        return animator;
    }

    private class TextTypeFaceProperty extends Property<TextView, Integer> {

        public TextTypeFaceProperty() {
            super(Integer.class, "typeface");
        }

        @Override
        public void set(TextView object, Integer value) {
            object.setTag(value);
            if (value == 1) {
                object.setTypeface(Typeface.create("sans-serif-thin", Typeface.NORMAL));
            } else if (value == 2) {
                object.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
            } else if (value == 3) {
                object.setTypeface(Typeface.create("sans-serif-regular", Typeface.NORMAL));
            } else if (value == 4) {
                object.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            } else if (value == 5) {
                object.setTypeface(Typeface.create("sans-serif-black", Typeface.NORMAL));
            }
        }

        @Override
        public Integer get(TextView object) {
            return (Integer) object.getTag();
        }
    }
}
