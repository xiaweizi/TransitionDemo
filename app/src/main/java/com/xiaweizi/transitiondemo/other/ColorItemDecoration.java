package com.xiaweizi.transitiondemo.other;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.xiaweizi.transitiondemo.other.ColorItemDecoration
 *     e-mail : 1012126908@qq.com
 *     time   : 2020/03/24
 *     desc   :
 * </pre>
 */
public class ColorItemDecoration extends RecyclerView.ItemDecoration {
    private int mLeftMargin;
    private int mTopMargin;

    public ColorItemDecoration() {
        mLeftMargin = 60;
        mTopMargin = 60;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.top = mTopMargin;
        outRect.left = mLeftMargin;
        int position = parent.getChildAdapterPosition(view);
        if (position % 2 == 1) {
            outRect.right = mLeftMargin;
            outRect.left = mLeftMargin / 2;
        } else {
            outRect.right = mLeftMargin / 2;
            outRect.left = mLeftMargin;
        }
        int itemCount = parent.getAdapter().getItemCount();
        if (position == itemCount - 1 || position == itemCount - 2) {
            outRect.bottom = mTopMargin;
        }
    }
}
