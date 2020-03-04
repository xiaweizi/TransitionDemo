package com.xiaweizi.transitiondemo.activity;

import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.xiaweizi.transitiondemo.R;

public class EndOneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_one);
        initListener();
    }

    private void initListener() {
        findViewById(R.id.tv_end_one1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Slide slide = new Slide();
                slide.setDuration(1000);
                getWindow().setReturnTransition(slide);
                finishAfterTransition();
            }
        });
        findViewById(R.id.tv_end_one2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fade fade = new Fade();
                fade.setDuration(1000);
                getWindow().setReturnTransition(fade);
                finishAfterTransition();
            }
        });
        findViewById(R.id.tv_end_one3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Explode explode = new Explode();
                explode.setDuration(1000);
                getWindow().setReturnTransition(explode);
                finishAfterTransition();
            }
        });
        findViewById(R.id.tv_end_one4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Slide slide = new Slide();
                slide.setDuration(1000);
                Fade fade = new Fade();
                fade.setDuration(1000);
                TransitionSet transitionSet  = new TransitionSet();
                transitionSet.addTransition(slide).addTransition(fade);
                getWindow().setReturnTransition(transitionSet);
                finishAfterTransition();
            }
        });
    }
}
