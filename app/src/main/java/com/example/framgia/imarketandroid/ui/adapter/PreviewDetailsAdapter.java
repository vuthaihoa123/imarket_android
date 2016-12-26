package com.example.framgia.imarketandroid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.ui.activity.DetailsProductActivity;
import com.example.framgia.imarketandroid.ui.activity.OrderActivity;

import java.util.ArrayList;

/**
 * Created by vutha on 9/27/2016.
 */
public class PreviewDetailsAdapter extends RecyclerView.Adapter<PreviewDetailsAdapter.ViewHolder> {
    private ArrayList<Integer> mItems = new ArrayList<>();
    private Context mContext;
    private ImageView mIvPreview;

    public PreviewDetailsAdapter(Context context, ArrayList<Integer> myItems) {
        mContext = context;
        mItems = myItems;
    }

    public void setItems(ArrayList<Integer> items) {
        mItems = items;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PreviewDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_preview_product, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PreviewDetailsAdapter.ViewHolder holder, final int position) {
        final int idRes = mItems.get(position);
        ImageView previewProduct = holder.mIvPreviewProduct;
        // add libs:      compile 'com.github.bumptech.glide:glide:3.7.0'     to build.gradle
        Glide.with(mContext)
            .load(idRes)
            .dontAnimate()
            .into(previewProduct);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClickShowPreviewDetail(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvPreviewProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            mIvPreviewProduct = (ImageView) itemView.findViewById(R.id.iv_preview_product);
        }
    }

    public interface OnClickShowPreviewDetail {
        public void onClickShowPreviewDetail(int idRes);
    }

    private OnClickShowPreviewDetail mListener;

    public PreviewDetailsAdapter setOnClickShowPreviewDetail(OnClickShowPreviewDetail listener) {
        mListener = listener;
        return this;
    }
}

