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
        this(context.getResources().getDimensionPixelSize(R.dimen.recycler_item_default_space));
    }

    public LinearItemDecoration(int space) {
        mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        if (position == 0) {
            outRect.top = mSpace;
        } else {
            outRect.top = 0;
        }
        outRect.bottom = mSpace;
        outRect.left = mSpace;
        outRect.right = mSpace;
    }
}
