package com.xiaweizi.transitiondemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaweizi.transitiondemo.R;
import com.xiaweizi.transitiondemo.TransitionUtils;
import com.xiaweizi.transitiondemo.adapter.ColorAdapter;
import com.xiaweizi.transitiondemo.bean.ColorBean;
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
public class DetailFragment extends Fragment {

    private View mRoot;
    private RecyclerView mRecyclerView;
    private ColorAdapter mAdapter;

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
        mAdapter = new ColorAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
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
    }

    private int getRandomColor() {
        Random random = new Random();
        int index = random.nextInt(8);
        return TransitionUtils.getColor(index);
    }
}
