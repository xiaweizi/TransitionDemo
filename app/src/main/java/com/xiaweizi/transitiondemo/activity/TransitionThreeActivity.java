package com.xiaweizi.transitiondemo.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.transition.Scene;
import androidx.transition.TransitionManager;

import com.xiaweizi.transitiondemo.R;
import com.xiaweizi.transitiondemo.transition.SceneTransition;

public class TransitionThreeActivity extends AppCompatActivity {

    private ConstraintLayout mRoot;
    private Scene mScene1;
    private Scene mScene2;
    private SceneTransition mTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_three);
        mRoot = findViewById(R.id.view_one1);
        mScene1 = Scene.getSceneForLayout(mRoot, R.layout.layout_scene1, this);
        mScene2 = Scene.getSceneForLayout(mRoot, R.layout.layout_scene2, this);
        mTransition = new SceneTransition();
    }

    public void onOneClick(View view) {
        TransitionManager.go(mScene1, mTransition);
    }

    public void onTwoClick(View view) {
        TransitionManager.go(mScene2, mTransition);
    }
}