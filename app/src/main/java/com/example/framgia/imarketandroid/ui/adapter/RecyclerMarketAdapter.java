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
import com.example.framgia.imarketandroid.data.model.CommerceCanter;
import com.example.framgia.imarketandroid.data.model.Market;
import com.example.framgia.imarketandroid.util.Constants;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yue on 20/07/2016.
 */
public class RecyclerMarketAdapter extends RecyclerView.Adapter<RecyclerMarketAdapter.ItemHolder> {
    private Context mContext;
    private List<CommerceCanter> mMarkets;
    private OnRecyclerItemInteractListener mListener;

    public RecyclerMarketAdapter(Context context, List<CommerceCanter> markets) {
        mContext = context;
        mMarkets = markets;
    }

    public void setOnRecyclerItemInteractListener(OnRecyclerItemInteractListener listener) {
        mListener = listener;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.
            from(parent.getContext()).inflate(R.layout.item_recycler_market, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, final int position) {
        final CommerceCanter market = mMarkets.get(position);
        holder.mTextName.setText(market.getName());
        holder.mTextAddress.setText(market.getAddress());
        if (market.getDistance() != 0.0) {
            holder.mTextDistance.setText((int) market.getDistance() + Constants.METTERS);
        } else {
            holder.mTextDistance.setText(R.string.distance);
        }
        holder.mImage.setImageResource(R.drawable.logo_big_c);
        String url = Constants.HEAD_URL + market.getImage();
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
            .into(holder.mImage);
        holder.itemRecyclerMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(view, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMarkets != null ? mMarkets.size() : 0;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public CircleImageView mImage;
        public TextView mTextName;
        public TextView mTextAddress;
        public TextView mTextDistance;
        public View itemRecyclerMarket;

        public ItemHolder(View itemView) {
            super(itemView);
            mImage = (CircleImageView) itemView.findViewById(R.id.image_market);
            mTextName = (TextView) itemView.findViewById(R.id.text_name);
            mTextAddress = (TextView) itemView.findViewById(R.id.text_address);
            mTextDistance = (TextView) itemView.findViewById(R.id.text_distance);
            itemRecyclerMarket = itemView.findViewById(R.id.item_recycler_market);
        }
    }
}
