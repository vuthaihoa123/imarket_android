package com.example.framgia.imarketandroid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.ui.activity.DetailsProductActivity;

import java.util.ArrayList;

/**
 * Created by hoavt on 22/07/2016.
 */
public class PreviewProductAdapter extends RecyclerView.Adapter<PreviewProductAdapter.ViewHolder> {
    private ArrayList<Integer> mItems = new ArrayList<>();
    private Context mContext;
    private ScrollView mInfoView;
    private ImageView mIvPreview;

    // Provide a suitable constructor (depends on the kind of dataset)
    public PreviewProductAdapter(Context context, ArrayList<Integer> myItems, ScrollView infoView) {
        mContext = context;
        mItems = myItems;
        mInfoView = infoView;
        mIvPreview = (ImageView) ((DetailsProductActivity) mContext).findViewById(R.id.iv_preview);
        showPreview(false);
    }

    public void setItems(ArrayList<Integer> items) {
        mItems = items;
    }

    public void showPreview(boolean show) {
        if (show) {
            mIvPreview.setVisibility(View.VISIBLE);
            mInfoView.setVisibility(View.GONE);
        } else {
            mInfoView.setVisibility(View.VISIBLE);
            mIvPreview.setVisibility(View.GONE);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PreviewProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_preview_product, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PreviewProductAdapter.ViewHolder holder, int position) {
        final int idRes = mItems.get(position);
        ImageView previewProduct = holder.mIvPreviewProduct;
        // add libs:      compile 'com.github.bumptech.glide:glide:3.7.0'     to build.gradle
        Glide.with(mContext)
                .load(idRes)
                .dontAnimate()
                .into(previewProduct);
        previewProduct.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int idAction = event.getAction();
                switch (idAction) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        mIvPreview.setImageResource(idRes);
                        showPreview(true);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        showPreview(false);
                        break;
                    default:
                        showPreview(false);
                        break;
                }
                return true;
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
}
