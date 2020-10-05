package com.xiaweizi.transitiondemo.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.util.Property;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.transition.Transition;
import androidx.transition.TransitionValues;

import com.xiaweizi.transitiondemo.R;

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
    private static String PROPNAME_TEXT_COLOR = "xiaweizi:changeTextColor:color";
    private static String PROPNAME_TEXT_SIZE = "xiaweizi:changeTextSize:size";
    private static String PROPNAME_TEXT_LEVEL = "xiaweizi:changeTextTypeface:level";
    public static int TAG_KEY_TYPEFACE_LEVEL = 666;

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues) {
        if (transitionValues == null || !(transitionValues.view instanceof TextView)) return;
        TextView view = (TextView) transitionValues.view;
        transitionValues.values.put(PROPNAME_TEXT, view.getText());
        transitionValues.values.put(PROPNAME_TEXT_COLOR, view.getCurrentTextColor());
        transitionValues.values.put(PROPNAME_TEXT_SIZE, view.getTextSize());
        transitionValues.values.put(PROPNAME_TEXT_LEVEL, view.getTag(R.id.type_face_level));
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        if (!(endValues.view instanceof TextView)) {
            return super.createAnimator(sceneRoot, startValues, endValues);
        }
        TextView endView = (TextView) endValues.view;
        CharSequence startText = (CharSequence) startValues.values.get(PROPNAME_TEXT);
        CharSequence endText = (CharSequence) endValues.values.get(PROPNAME_TEXT);
        int startTextColor = (int) startValues.values.get(PROPNAME_TEXT_COLOR);
        int endTextColor = (int) endValues.values.get(PROPNAME_TEXT_COLOR);
        float startTextSize = (float) startValues.values.get(PROPNAME_TEXT_SIZE);
        float endTextSize = (float) endValues.values.get(PROPNAME_TEXT_SIZE);
        Object startTag = startValues.values.get(PROPNAME_TEXT_LEVEL);
        int startTextLevel = 1;
        if (startTag instanceof Integer) {
            startTextLevel = (int) startTag;
        }
        Object endTag = endValues.values.get(PROPNAME_TEXT_LEVEL);
        int endTextLevel = 1;
        if (startTag instanceof Integer) {
            endTextLevel = (int) endTag;
        }
        if (!TextUtils.equals(startText, endText)) {
            return createTextChangeAnimator(endView, startText, endText);
        }
        if (startTextColor != endTextColor) {
            return createColorChangeAnimator(endView, startTextColor, endTextColor);
        }
        if (startTextSize != endTextSize) {
            return createSizeChangeAnimator(endView, startTextSize, endTextSize);
        }
        if (startTextLevel != endTextLevel) {
            return createTypefaceChangeAnimator(endView, startTextLevel, endTextLevel);
        }
        return super.createAnimator(sceneRoot, startValues, endValues);
    }

    private Animator createTextChangeAnimator(final TextView endView, final CharSequence startText, final CharSequence endText) {
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

    private Animator createColorChangeAnimator(final TextView endView, final int startTextColor, final int endTextColor) {
        ObjectAnimator animator = ObjectAnimator.ofArgb(endView, new TextColorProperty(), startTextColor, endTextColor);
        animator.setDuration(300);
        return animator;
    }

    private Animator createSizeChangeAnimator(final TextView endView, final float startTextSize, final float endTextSize) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(endView, new TextSizeProperty(), startTextSize, endTextSize);
        animator.setDuration(300);
        Log.i("xiaweizi::", "createSizeChangeAnimator: ");
        return animator;
    }

    private Animator createTypefaceChangeAnimator(final TextView endView, final int startTextLevel, final int endTextSizeLevel) {
        ObjectAnimator animator = ObjectAnimator.ofInt(endView, new TextTypeFaceProperty(), startTextLevel, endTextSizeLevel);
        animator.setDuration(300);
        return animator;
    }

    private class TextColorProperty extends Property<TextView, Integer> {

        TextColorProperty() {
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

    private class TextSizeProperty extends Property<TextView, Float> {

        TextSizeProperty() {
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

    private class TextTypeFaceProperty extends Property<TextView, Integer> {

        TextTypeFaceProperty() {
            super(Integer.class, "typeface");
        }

        @Override
        public void set(TextView object, Integer value) {
            object.setTag(R.id.type_face_level, value);
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
            return (Integer) object.getTag(R.id.type_face_level);
        }
    }
}
