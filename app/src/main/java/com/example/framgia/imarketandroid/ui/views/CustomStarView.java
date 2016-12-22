package com.example.framgia.imarketandroid.ui.views;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.framgia.imarketandroid.R;

/**
 * Created by VULAN on 10/8/2016.
 */
public class CustomStarView implements View.OnClickListener {
    private int mPosition;
    private ImageView mImageStar;
    private View mView;
    private Context mContext;
    private boolean mIsChecked;
    private onItemClickListener mOnItemClickListener;

    public CustomStarView(Context context, int position) {
        this.mContext = context;
        this.mPosition = position;
        mView = View.inflate(this.mContext, R.layout.item_star, null);
        findView(mView);
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public View getView() {
        return this.mView;
    }

    public void findView(View view) {
        mImageStar = (ImageView) view.findViewById(R.id.image_star);
        if (mPosition == 0) {
            mImageStar.setImageResource(R.drawable.ic_star_full);
            setChecked(true);
        }
        mImageStar.setOnClickListener(this);
    }

    public void setSelectedStar() {
        if (mIsChecked) {
            mImageStar.setImageResource(R.drawable.ic_star_full);
        } else {
            mImageStar.setImageResource(R.drawable.ic_star_empty);
        }
    }

    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean isChecked) {
        this.mIsChecked = isChecked;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(mPosition);
        }
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }
}
