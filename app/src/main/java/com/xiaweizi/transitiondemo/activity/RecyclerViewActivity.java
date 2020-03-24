package com.xiaweizi.transitiondemo.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaweizi.transitiondemo.R;
import com.xiaweizi.transitiondemo.TransitionUtils;
import com.xiaweizi.transitiondemo.adapter.ColorAdapter;
import com.xiaweizi.transitiondemo.bean.ColorBean;
import com.xiaweizi.transitiondemo.other.ColorItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ColorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new ColorAdapter(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new ColorItemDecoration());
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
