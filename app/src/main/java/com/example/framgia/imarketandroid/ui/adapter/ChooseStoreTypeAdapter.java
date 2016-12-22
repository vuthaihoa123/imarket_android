package com.example.framgia.imarketandroid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.listener.OnRecyclerItemInteractListener;
import com.example.framgia.imarketandroid.data.model.StoreType;
import com.example.framgia.imarketandroid.util.Constants;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by framgia on 06/09/2016.
 */
public class ChooseStoreTypeAdapter
    extends RecyclerView.Adapter<ChooseStoreTypeAdapter.ItemHolderStore> {
    private Context mContext;
    private List<StoreType> mListStore;
    private OnRecyclerItemInteractListener mListener;

    public ChooseStoreTypeAdapter(Context context, List<StoreType> listStore) {
        mListStore = listStore;
        mContext = context;
    }

    public void setOnRecyclerItemInteractListener(OnRecyclerItemInteractListener listener) {
        mListener = listener;
    }

    @Override
    public ItemHolderStore onCreateViewHolder(ViewGroup parent,
                                              int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout
            .item_choose_store_type, parent, false);
        return new ItemHolderStore(view);
    }

    @Override
    public void onBindViewHolder(ItemHolderStore holder, final int position) {
        StoreType store = mListStore.get(position);
        holder.avatar.setImageResource(R.drawable.store);
        String url = Constants.HEAD_URL + store.getAvatar();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(mContext).clearDiskCache();
            }
        }).start();
        Glide.get(mContext).clearMemory();
        Glide.with(mContext).load(url)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(false)
            .centerCrop()
            .into(holder.avatar);
        holder.textName.setText(store.getName());
        holder.itemRecyclerMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onItemClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListStore == null ? 0 : mListStore.size();
    }

    public class ItemHolderStore extends RecyclerView.ViewHolder {
        public ImageView avatar;
        public TextView textName;
        public View itemRecyclerMarket;

        public ItemHolderStore(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.iv_avatar_store);
            textName = (TextView) itemView.findViewById(R.id.tv_name_store);
            itemRecyclerMarket = itemView.findViewById(R.id.item_recycler_market);
        }
    }
}