package com.xiaweizi.transitiondemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaweizi.transitiondemo.R;
import com.xiaweizi.transitiondemo.bean.ColorBean;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.xiaweizi.transitiondemo.adapter.ColorAdapter
 *     e-mail : 1012126908@qq.com
 *     time   : 2020/03/24
 *     desc   :
 * </pre>
 */
public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {
    private Context mContext;
    private List<ColorBean> mData;

    public ColorAdapter(Context context) {
        this.mContext = context;
        this.mData = new ArrayList<>();
    }

    private @LayoutRes
    int getItemLayout() {
        return R.layout.layout_color_item;
    }

    public void setData(List<ColorBean> data) {
        if (data != null) {
            this.mData = data;
            notifyDataSetChanged();
        }
    }

    public List<ColorBean> getData() {
        return mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(getItemLayout(), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private View mRoot;
        private TextView mTvContent;

        ViewHolder(View itemView) {
            super(itemView);
            mRoot = itemView.findViewById(R.id.cl_color_item);
            mTvContent = itemView.findViewById(R.id.tv_color_content);
        }

        void bind(ColorBean bean, int position) {
            mTvContent.setText(bean.getContent());
            mRoot.setBackgroundColor(bean.getColor());
        }
    }
}