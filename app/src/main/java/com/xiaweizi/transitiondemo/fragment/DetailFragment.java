package com.xiaweizi.transitiondemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaweizi.transitiondemo.R;
import com.xiaweizi.transitiondemo.TransitionUtils;
import com.xiaweizi.transitiondemo.activity.weather.HomeActivity;
import com.xiaweizi.transitiondemo.adapter.ColorAdapter;
import com.xiaweizi.transitiondemo.bean.ColorBean;
import com.xiaweizi.transitiondemo.callback.DetailTransitionCallback;
import com.xiaweizi.transitiondemo.other.ColorItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.xiaweizi.transitiondemo.fragment.DetailFragment
 *     e-mail : 1012126908@qq.com
 *     time   : 2020/07/05
 *     desc   :
 * </pre>
 */
public class DetailFragment extends Fragment implements DetailTransitionCallback {

    private View mRoot;
    private RecyclerView mRecyclerView;
    private ColorAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_detail, null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = mRoot.findViewById(R.id.recycler_view);
        View statusView = mRoot.findViewById(R.id.view_status);
        ViewGroup.LayoutParams layoutParams = statusView.getLayoutParams();
        layoutParams.height = TransitionUtils.getStatusBarHeight(mRoot.getContext());
        statusView.setLayoutParams(layoutParams);
        mAdapter = new ColorAdapter(getActivity(), R.layout.layout_color_item);
        Toolbar toolbar = mRoot.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDetailExit(null, null);
            }
        });
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.mCurrentIndex = mRecyclerView.getChildAdapterPosition(v);
                onDetailExit(v, ((ColorAdapter.ViewHolder) mRecyclerView.getChildViewHolder(v)).getBean());
            }
        });
        ColorItemTouchHelperCallback helperCallback = new ColorItemTouchHelperCallback();
        ItemTouchHelper helper = new ItemTouchHelper(helperCallback);
        helper.attachToRecyclerView(mRecyclerView);
        initData();
    }

    private void initData() {
        List<ColorBean> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ColorBean bean = new ColorBean(getRandomColor(), Integer.toString(i), "desc");
            data.add(bean);
        }
        mAdapter.setData(data);
        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                onDetailEnter();
                mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private int getRandomColor() {
        Random random = new Random();
        int index = random.nextInt(8);
        return TransitionUtils.getColor(index);
    }

    @Override
    public void onDetailEnter() {
        int firstPosition = mLayoutManager.findFirstVisibleItemPosition();
        int lastPosition = mLayoutManager.findLastVisibleItemPosition();
        if (HomeActivity.mCurrentIndex <= firstPosition || HomeActivity.mCurrentIndex >= lastPosition) {
            mRecyclerView.scrollToPosition(HomeActivity.mCurrentIndex);
        }
        View viewByPosition = mLayoutManager.findViewByPosition(HomeActivity.mCurrentIndex);
        if (viewByPosition != null) {
            if (getActivity() instanceof HomeActivity) {
                ((HomeActivity) getActivity()).onHomeExit(viewByPosition, ((ColorAdapter.ViewHolder) mRecyclerView.findViewHolderForLayoutPosition(HomeActivity.mCurrentIndex)).getBean());
            }
            for (int i = firstPosition; i <= lastPosition; i++) {
                View child = mLayoutManager.findViewByPosition(i);
                if (child != null) {
                    child.setAlpha(0f);
                    child.animate().alpha(1f).setDuration(300).setStartDelay(getItemDelay(i)).start();
                }
            }
        }
    }

    private int getItemDelay(int position) {
        return (int) (Math.pow(Math.abs(position - HomeActivity.mCurrentIndex) * 1.0f / 16, 2) * 1000) + 50;
    }

    @Override
    public void onDetailExit(View targetView, ColorBean bean) {
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).onHomeEnter(targetView, bean);
        }
    }
}
