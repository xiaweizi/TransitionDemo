package com.xiaweizi.transitiondemo.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.xiaweizi.transitiondemo.R;

public class StartOneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_one);
        initListener();

    }

    private void initListener() {
        findViewById(R.id.tv_start_one1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setAllowEnterTransitionOverlap(false);
                Slide slide = new Slide();
                slide.setDuration(1000);
                getWindow().setExitTransition(slide);
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(StartOneActivity.this);
                startActivity(new Intent(StartOneActivity.this, EndOneActivity.class), activityOptions.toBundle());
            }
        });

        findViewById(R.id.tv_start_one2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setAllowEnterTransitionOverlap(false);
                Fade fade = new Fade();
                fade.setDuration(1000);
                getWindow().setExitTransition(fade);
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(StartOneActivity.this);
                startActivity(new Intent(StartOneActivity.this, EndOneActivity.class), activityOptions.toBundle());
            }
        });

        findViewById(R.id.tv_start_one3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setAllowEnterTransitionOverlap(false);
                Explode explode = new Explode();
                explode.setDuration(1000);
                getWindow().setExitTransition(explode);
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(StartOneActivity.this);
                startActivity(new Intent(StartOneActivity.this, EndOneActivity.class), activityOptions.toBundle());
            }
        });

        findViewById(R.id.tv_start_one4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setAllowEnterTransitionOverlap(false);
                Explode explode = new Explode();
                explode.setDuration(1000);
                Fade fade = new Fade();
                fade.setDuration(1000);
                TransitionSet transitionSet = new TransitionSet();
                transitionSet.addTransition(explode).addTransition(fade).setOrdering(TransitionSet.ORDERING_TOGETHER);
                getWindow().setExitTransition(transitionSet);
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(StartOneActivity.this);
                startActivity(new Intent(StartOneActivity.this, EndOneActivity.class), activityOptions.toBundle());
            }
        });
    }

}
