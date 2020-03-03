package com.xiaweizi.transitiondemo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class TransitionTwoActivity extends AppCompatActivity {

    private TextView mContent;
    private ImageView mIv4;
    private TransitionView mTransitionView3;
    private ConstraintLayout mRoot;
    private ConstraintLayout mTransition1;
    private ConstraintLayout mTransition2;
    private ConstraintLayout mTransition3;
    private ConstraintLayout mTransition4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_two);
        mContent = findViewById(R.id.tv_two_transition1);
        mIv4 = findViewById(R.id.iv_two_transition4);
        mRoot = findViewById(R.id.root_transition_two);
        mTransitionView3 = findViewById(R.id.view_two_transition3);
        mTransition1 = findViewById(R.id.cl_two_transition1);
        mTransition2 = findViewById(R.id.cl_two_transition2);
        mTransition3 = findViewById(R.id.cl_two_transition3);
        mTransition4 = findViewById(R.id.cl_two_transition4);
        mTransition2.getBackground().setAlpha(255);
        initListener();
    }

    private void initListener() {
        findViewById(R.id.bt_two_transition1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeTextTransition changeTextTransition = new ChangeTextTransition();
                changeTextTransition.addTarget(mContent);
                TransitionManager.beginDelayedTransition(mTransition1, changeTextTransition);
                if (TextUtils.equals(mContent.getText(), "文本一")) {
                    mContent.setText("文本二");
                } else {
                    mContent.setText("文本一");
                }
            }
        });

        findViewById(R.id.bt_two_transition2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeBackgroundAlphaTransition changeBackgroundAlphaTransition = new ChangeBackgroundAlphaTransition();
                TransitionManager.beginDelayedTransition(mTransition2, changeBackgroundAlphaTransition);
                int color1 = Color.parseColor("#FF7043");
                int color2 = Color.parseColor("#AB47BC");
                if (((ColorDrawable) mTransition2.getBackground()).getColor() == color1) {
                    mTransition2.setBackground(new ColorDrawable(color2));
                } else {
                    mTransition2.setBackground(new ColorDrawable(color1));
                }
            }
        });

        findViewById(R.id.bt_two_transition3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeBackgroundColorTransition changeBackgroundColorTransition = new ChangeBackgroundColorTransition();
                TransitionManager.beginDelayedTransition(mTransition2, changeBackgroundColorTransition);
                int color1 = Color.parseColor("#FF7043");
                int color2 = Color.parseColor("#AB47BC");
                if (((ColorDrawable) mTransition2.getBackground()).getColor() == color1) {
                    mTransition2.setBackground(new ColorDrawable(color2));
                } else {
                    mTransition2.setBackground(new ColorDrawable(color1));
                }
            }
        });

        findViewById(R.id.bt_two_transition4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeImageResourceTransition changeImageResourceTransition = new ChangeImageResourceTransition();
                changeImageResourceTransition.addTarget(mIv4);
                TransitionManager.beginDelayedTransition(mTransition4, changeImageResourceTransition);
                String tag = (String) mIv4.getTag();
                if (TextUtils.equals(tag, "girl")) {
                    mIv4.setImageResource(R.drawable.boy);
                    mIv4.setTag("boy");
                } else {
                    mIv4.setImageResource(R.drawable.girl);
                    mIv4.setTag("girl");
                }
            }
        });

        findViewById(R.id.bt_two_transition5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeCustomTransition changeCustomTransition = new ChangeCustomTransition();
                changeCustomTransition.addTarget(mTransitionView3);
                TransitionManager.beginDelayedTransition(mTransition4, changeCustomTransition);
                if (mTransitionView3.getRatio() == 0f) {
                    mTransitionView3.setRatio(1f);
                } else {
                    mTransitionView3.setRatio(0f);
                }
            }
        });
    }
}
