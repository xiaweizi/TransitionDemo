package com.xiaweizi.transitiondemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.xiaweizi.transitiondemo.R;
import com.xiaweizi.transitiondemo.stub.touch.TouchTestActivity;

public class StubHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stub_home);
        findViewById(R.id.bt_stub1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StubHomeActivity.this, TouchTestActivity.class));
            }
        });
    }
}
