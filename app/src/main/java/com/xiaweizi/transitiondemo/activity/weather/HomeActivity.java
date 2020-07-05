package com.xiaweizi.transitiondemo.activity.weather;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.xiaweizi.transitiondemo.R;
import com.xiaweizi.transitiondemo.TransitionUtils;
import com.xiaweizi.transitiondemo.adapter.ColorAdapter;
import com.xiaweizi.transitiondemo.bean.ColorBean;
import com.xiaweizi.transitiondemo.callback.HomeTransitionCallback;
import com.xiaweizi.transitiondemo.fragment.DetailFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeActivity extends AppCompatActivity implements HomeTransitionCallback {

    public static int mCurrentIndex;
    private static final String VALUE_PROGRESS = "progress";
    private ViewPager2 mViewPager;
    private View mContent;
    private ColorAdapter mAdapter;
    private DetailFragment mFragment;
    private float mProgress;
    private ObjectAnimator mAnimator;
    private View mTargetView;
    private Rect mRect;
    private ColorBean mTargetBean;
    private boolean mInDetailMode = false;
    private float mRadius;
    private float mWindowRadius;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContent = findViewById(R.id.fl_content);
        mToolbar = findViewById(R.id.toolbar);
        mViewPager = findViewById(R.id.view_pager);
        mAdapter = new ColorAdapter(this, R.layout.layout_color_item_home);
        mViewPager.setAdapter(mAdapter);
        initData();
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToDetail();
            }
        });
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mCurrentIndex = position;
            }
        });
        initAnimator();
        mRadius = getResources().getDimensionPixelSize(R.dimen.radius);
        mWindowRadius = TransitionUtils.getWindowCornerRadius(this);
        mRect = new Rect();
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(final float progress) {
        this.mProgress = progress;
        mContent.setClipToOutline(true);
        mContent.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                if (mTargetView != null) {
                    mTargetView.getGlobalVisibleRect(mRect);
                    int left = (int) (progress * mRect.left);
                    int right = (int) (mContent.getWidth() - progress * mRect.left);
                    int top = (int) (progress * mRect.top);
                    int bottom = (int) (mContent.getHeight() - progress * (mContent.getHeight() - mRect.bottom));
                    float startRadius, endRadius;
                    if (mWindowRadius > mRadius) {
                        startRadius = mRadius;
                        endRadius = mWindowRadius;
                    } else {
                        startRadius = mWindowRadius;
                        endRadius = mRadius;
                    }
                    float radius = startRadius + (endRadius - startRadius) * progress;
                    outline.setRoundRect(left, top, right, bottom, radius);
                }
            }
        });
        mToolbar.setAlpha(1 - progress);
        if (progress >= 0.9f) {
            float alpha = (1 - progress) / 0.1f * 0.3f + 0.7f;
            mContent.setAlpha(alpha);
        } else {
            mContent.setAlpha(1f);
        }
        View recyclerChild = mViewPager.getChildAt(0);
        if (recyclerChild instanceof RecyclerView) {
            View child = ((RecyclerView) recyclerChild).getLayoutManager().findViewByPosition(mCurrentIndex);
            if (child != null) {
                ColorBean currentBean = mAdapter.getData().get(mCurrentIndex);
                child.setBackgroundColor(TransitionUtils.getColor(progress, currentBean.getColor(), mTargetBean.getColor()));
            }
        }
    }

    private void initAnimator() {
        mAnimator = ObjectAnimator.ofFloat(this, VALUE_PROGRESS, 0f, 1f);
        mAnimator.setDuration(300);
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mInDetailMode) {
                    mContent.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                if (!mInDetailMode) {
                    mContent.setVisibility(View.VISIBLE);
                }
            }
        });
        mAnimator.setInterpolator(new LinearInterpolator());
    }

    private void jumpToDetail() {
        if (mFragment == null) {
            mFragment = new DetailFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.view_detail, mFragment);
            fragmentTransaction.commit();
        } else {
            mFragment.onDetailEnter();
        }
    }

    private void initData() {
        List<ColorBean> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ColorBean bean = new ColorBean(getRandomColor(), Integer.toString(i), "desc");
            data.add(bean);
        }
        mAdapter.setData(data);
    }

    private int getRandomColor() {
        Random random = new Random();
        int index = random.nextInt(8);
        return TransitionUtils.getColor(index);
    }

    @Override
    public void onHomeEnter(View targetView, ColorBean bean) {
        if (targetView != null) {
            mTargetView = targetView;
        }
        if (bean != null) {
            mTargetBean = bean;
        }
        mViewPager.setCurrentItem(HomeActivity.mCurrentIndex, false);
        mInDetailMode = false;
        mAnimator.setFloatValues(getProgress(), 0f);
        mAnimator.start();
    }

    @Override
    public void onHomeExit(View targetView, ColorBean bean) {
        mInDetailMode = true;
        mTargetView = targetView;
        mTargetBean = bean;
        mAnimator.setFloatValues(getProgress(), 1f);
        mAnimator.start();
    }


    @Override
    public void onBackPressed() {
        if (mInDetailMode) {
            mFragment.onDetailExit(null, null);
        } else {
            super.onBackPressed();
        }
    }
}
