package com.xiaweizi.transitiondemo;

import android.graphics.Rect;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeScroll;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class TransitionOneActivity extends AppCompatActivity {

    private ConstraintLayout mRoot;
    private View[] views;
    private View view1, view2, view3, view4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSharedElementsUseOverlay(true);
        setContentView(R.layout.activity_transition_one);
        mRoot = findViewById(R.id.view_one1);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
        view4 = findViewById(R.id.view4);
        views = new View[]{view1, view2, view3, view4};
        initListener();
    }

    private void initListener() {
        findViewById(R.id.bt_one_transition1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(mRoot);
                TransitionUtils.toggleViewVisible(view1);
            }
        });
        findViewById(R.id.bt_one_transition2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Slide slide = new Slide();
                slide.setInterpolator(new AccelerateDecelerateInterpolator());
                TransitionManager.beginDelayedTransition(mRoot, slide);
                TransitionUtils.toggleViewVisible(view2);
            }
        });

        findViewById(R.id.bt_one_transition3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fade fade = new Fade();
                TransitionManager.beginDelayedTransition(mRoot, fade);
                TransitionUtils.toggleViewVisible(view3);
            }
        });

        findViewById(R.id.bt_one_transition4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Explode explode = new Explode();
                TransitionManager.beginDelayedTransition(mRoot, explode);
                TransitionUtils.toggleViewVisible(view4);
            }
        });
        findViewById(R.id.bt_one_transition5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fade fade = new Fade();
                Slide slide = new Slide();
                TransitionSet set = new TransitionSet();
                set.addTransition(fade).addTransition(slide).setOrdering(TransitionSet.ORDERING_TOGETHER);
                TransitionManager.beginDelayedTransition(mRoot, set);
                TransitionUtils.toggleViewVisible(views);
            }
        });
        findViewById(R.id.bt_one_transition6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(mRoot, new ChangeBounds());
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view3.getLayoutParams();
//                int height = view1.getHeight();
//                int width = view1.getWidth();
//                layoutParams.height = (height == width) ? width / 2 : width;
//                view1.setLayoutParams(layoutParams);
                if (layoutParams.leftMargin == 400) {
                    layoutParams.leftMargin = 50;
                } else {
                    layoutParams.leftMargin = 400;
                }
                view3.setLayoutParams(layoutParams);
            }
        });

        findViewById(R.id.bt_one_transition7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(mRoot, new ChangeClipBounds());
                int width = view2.getWidth();
                int height = view2.getHeight();
                int gap = 140;
                Rect rect = new Rect(0, gap, width, height - gap);
                if (rect.equals(view2.getClipBounds())) {
                    view2.setClipBounds(null);
                } else {
                    view2.setClipBounds(rect);
                }
            }
        });

        findViewById(R.id.bt_one_transition8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(mRoot, new ChangeScroll());
                if (view1.getScrollX() == -100 && view1.getScrollY() == -100) {
                    view1.scrollTo(0, 0);
                } else {
                    view1.scrollTo(-100, -100);
                }
            }
        });
        findViewById(R.id.bt_one_transition9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(mRoot, new ChangeTransform());
                if (view1.getTranslationX() == 100 && view1.getTranslationY() == 100) {
                    view1.setTranslationX(0);
                    view1.setTranslationY(0);
                } else {
                    view1.setTranslationX(100);
                    view1.setTranslationY(100);
                }

//                if (view2.getRotation() == 30f) {
//                    view2.setRotation(0);
//                } else {
//                    view2.setRotation(30);
//                }

                if (view2.getRotationX() == 30f) {
                    view2.setRotationX(0);
                } else {
                    view2.setRotationX(30);
                }

                if (view3.getRotationY() == 30f) {
                    view3.setRotationY(0);
                } else {
                    view3.setRotationY(30);
                }

//                if (view3.getTranslationZ() == 100) {
//                    view3.setTranslationZ(0);
//                } else {
//                    view3.setTranslationZ(100);
//                }

//                if (view3.getRotationX() == 30f && view3.getRotationY() == 30f) {
//                    view3.setRotationX(1f);
//                    view3.setRotationY(1f);
//                } else {
//                    view3.setRotationX(30f);
//                    view3.setRotationY(30f);
//                }
                if (view4.getScaleX() == 0.5f && view4.getScaleY() == 0.5f) {
                    view4.setScaleX(1f);
                    view4.setScaleY(1f);
                } else {
                    view4.setScaleX(0.5f);
                    view4.setScaleY(0.5f);
                }
            }
        });
    }
}
