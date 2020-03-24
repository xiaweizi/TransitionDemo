package com.xiaweizi.transitiondemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.xiaweizi.transitiondemo.R;

public class TransitionHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_home);
        initListener();
    }

    private void initListener() {
        findViewById(R.id.bt_home_transition1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransitionHomeActivity.this, TransitionOneActivity.class));
            }
        });
        findViewById(R.id.bt_home_transition2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransitionHomeActivity.this, TransitionTwoActivity.class));
            }
        });
        findViewById(R.id.bt_home_transition3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransitionHomeActivity.this, StartOneActivity.class));
            }
        });
        findViewById(R.id.bt_home_transition4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransitionHomeActivity.this, StartTwoActivity.class));
            }
        });
        findViewById(R.id.bt_home_transition5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransitionHomeActivity.this, RecyclerViewActivity.class));
            }
        });
    }

}
