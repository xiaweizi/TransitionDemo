package com.xiaweizi.transitiondemo.stub.touch;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.xiaweizi.transitiondemo.R;


public class TouchTestActivity extends AppCompatActivity {

    private TextView mTouchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_test);
        mTouchView = findViewById(R.id.tv_center_touch);
        NestedScrollView scrollView = findViewById(R.id.scroll_view);
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                mTouchView.setTranslationY(-scrollY);
            }
        });
        mTouchView.post(new Runnable() {
            @Override
            public void run() {
                initTouchListener();
            }
        });
    }

    private void initTouchListener() {
        if (mTouchView.getParent() != null && mTouchView.getParent() instanceof TouchTestFrameLayout) {
            final TouchDealHelper touchHelper = ((TouchTestFrameLayout) mTouchView.getParent()).getTouchHelper();
            if (touchHelper != null) {
                touchHelper.setTouchEventListener(mTouchView, new TouchDealHelper.OnTouchEventListener() {
                    @Override
                    public void onClick() {
                        Toast.makeText(TouchTestActivity.this, "我被点击了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onTouchEvent(int eventType) {
                        if (eventType == MotionEvent.ACTION_DOWN) {
                            mTouchView.setText("DOWN");
                        } else if (eventType == MotionEvent.ACTION_UP) {
                            mTouchView.setText("UP");
                        } else if (eventType == MotionEvent.ACTION_MOVE) {
                            mTouchView.setText("MOVE");
                        } else if (eventType == MotionEvent.ACTION_CANCEL) {
                            mTouchView.setText("CANCEL");
                        }
                    }
                });
            }
        }
    }
}
