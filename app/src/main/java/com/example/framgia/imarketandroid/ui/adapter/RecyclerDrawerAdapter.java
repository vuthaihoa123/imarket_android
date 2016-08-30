package com.example.framgia.imarketandroid.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.DrawerItem;
import com.example.framgia.imarketandroid.util.Constants;
import java.util.List;

/**
 * Created by yue on 31/07/2016.
 */
public class RecyclerDrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DrawerItem> mNavigationDrawerItems;

    public RecyclerDrawerAdapter(List<DrawerItem> items) {
        mNavigationDrawerItems = items;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder itemHolder = (ItemHolder) holder;
            itemHolder.imageIcon.setImageResource(R.drawable.shirt);
            itemHolder.textTitle.setText(mNavigationDrawerItems.get(position).getTitle());
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

        public ImageView imageIcon;
        public TextView textTitle;

        public ItemHolder(View itemView) {
            super(itemView);
            imageIcon = (ImageView) itemView.findViewById(R.id.image_icon);
            textTitle = (TextView) itemView.findViewById(R.id.text_title);
        }
    }

    class TailHolder extends RecyclerView.ViewHolder {

        public TailHolder(View itemView) {
            super(itemView);
        }
    }
}
