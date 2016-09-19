package com.example.framgia.imarketandroid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.listener.OnRecyclerItemInteractListener;
import com.example.framgia.imarketandroid.data.model.DrawerItem;
import com.example.framgia.imarketandroid.util.Constants;

import java.util.List;

/**
 * Created by yue on 31/07/2016.
 */
public class RecyclerDrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DrawerItem> mNavigationDrawerItems;
    private Context mContext;
    private OnRecyclerItemInteractListener mOnRecyclerItemInteractListener;
    public RecyclerDrawerAdapter(Context context, List<DrawerItem> items
    ) {
        mContext = context;
        mNavigationDrawerItems = items;
    }

    public void setOnClick(OnRecyclerItemInteractListener onRecyclerItemInteractListener) {
        // truyen mot interface nham bat su kien bat cu khi nào có recyclerview
        mOnRecyclerItemInteractListener = onRecyclerItemInteractListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constants.NORMAL_ITEM)
            return new ItemHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_recycler_navigation_drawer, parent, false));
        else
            return new TailHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_tail_recycler_navigation_drawer, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder itemHolder = (ItemHolder) holder;
            itemHolder.mImageIcon.setImageResource(R.drawable.ic_iphone7);
            itemHolder.mTextTitle.setText(R.string.name_product);
        } else {
            TailHolder tailHolder = (TailHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return mNavigationDrawerItems != null ? mNavigationDrawerItems.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mNavigationDrawerItems
            .get(position).isTail() ? Constants.TAIL_ITEM : Constants.NORMAL_ITEM;
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView mImageIcon, mImageDelete;
        public LinearLayout mlinearDrawer;
        public TextView mTextTitle;

        public ItemHolder(View itemView) {
            super(itemView);
            mlinearDrawer = (LinearLayout) itemView.findViewById(R.id.linear_navigation_drawer);
            mImageIcon = (ImageView) itemView.findViewById(R.id.image_icon);
            mTextTitle = (TextView) itemView.findViewById(R.id.text_title);
            mImageDelete = (ImageView) itemView.findViewById(R.id.image_btn_delete);
            // bat su kien click vao item
            mImageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnRecyclerItemInteractListener != null) {
                        mOnRecyclerItemInteractListener.onItemClick(view, getAdapterPosition());
                    }
                }
            });
            mlinearDrawer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnRecyclerItemInteractListener != null) {
                        mOnRecyclerItemInteractListener.onItemClick(view, getAdapterPosition());
                    }
                }
            });

        }
    }

    class TailHolder extends RecyclerView.ViewHolder {
        public TailHolder(View itemView) {
            super(itemView);
        }
    }

}
