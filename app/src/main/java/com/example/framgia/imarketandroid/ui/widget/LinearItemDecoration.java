package com.example.framgia.imarketandroid.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.framgia.imarketandroid.R;

/**
 * Created by yue on 21/07/2016.
 */
public class LinearItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;

    public LinearItemDecoration(Context context) {
        this(context.getResources().getDimensionPixelSize(R.dimen.common_size_7));
    }

    public LinearItemDecoration(int space) {
        mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        if (position == 0 && outRect.top == 0) {
            outRect.top = mSpace;
        }
        outRect.bottom = mSpace;
        outRect.right = mSpace;
        outRect.left = mSpace;
    }
}
