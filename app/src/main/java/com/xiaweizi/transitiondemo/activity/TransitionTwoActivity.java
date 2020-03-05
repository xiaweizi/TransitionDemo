package com.xiaweizi.transitiondemo.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.xiaweizi.transitiondemo.R;
import com.xiaweizi.transitiondemo.TransitionUtils;
import com.xiaweizi.transitiondemo.transition.ChangeBackgroundAlphaTransition;
import com.xiaweizi.transitiondemo.transition.ChangeBackgroundColorTransition;
import com.xiaweizi.transitiondemo.transition.ChangeCustomTransition;
import com.xiaweizi.transitiondemo.transition.ChangeImageResourceTransition;
import com.xiaweizi.transitiondemo.transition.ChangeTextTransition;
import com.xiaweizi.transitiondemo.view.TransitionView;

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
        mContent.setTag(R.id.type_face_level, 1);
        initListener();
    }

    private void initListener() {
        findViewById(R.id.bt_two_transition1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeTextTransition changeTextTransition = new ChangeTextTransition();
                changeTextTransition.addTarget(mContent);
                TransitionManager.beginDelayedTransition(mTransition1, changeTextTransition);
                if (TextUtils.equals(mContent.getText(), "Hello")) {
                    mContent.setText("World");
                } else {
                    mContent.setText("Hello");
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

        findViewById(R.id.bt_two_transition6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeTextTransition changeTextColorTransition = new ChangeTextTransition();
                changeTextColorTransition.addTarget(mContent);
                TransitionManager.beginDelayedTransition(mTransition1, changeTextColorTransition);
                if (mContent.getCurrentTextColor() == TransitionUtils.getColor(8)) {
                    mContent.setTextColor(TransitionUtils.getColor(0));
                } else {
                    mContent.setTextColor(TransitionUtils.getColor(8));
                }
            }
        });

        findViewById(R.id.bt_two_transition7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeTextTransition changeTextSizeTransition = new ChangeTextTransition();
                changeTextSizeTransition.addTarget(mContent);
                TransitionManager.beginDelayedTransition(mTransition1, changeTextSizeTransition);
                int textSize = getResources().getDimensionPixelSize(R.dimen.text_size);
                if (mContent.getTextSize() == textSize) {
                    mContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize / 3);
                } else {
                    mContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }
            }
        });

        findViewById(R.id.bt_two_transition8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeTextTransition changeTextTypefaceTransition = new ChangeTextTransition();
                changeTextTypefaceTransition.addTarget(mContent);
                TransitionManager.beginDelayedTransition(mTransition1, changeTextTypefaceTransition);
                int level = 1;
                Object tag = mContent.getTag(R.id.type_face_level);
                if (tag instanceof Integer) {
                    level = (int) tag;
                }
                if (level == 1) {
                    mContent.setTag(R.id.type_face_level, 5);
                } else {
                    mContent.setTag(R.id.type_face_level, 1);
                }
            }
        });
    }

}
