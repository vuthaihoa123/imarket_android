package com.example.framgia.imarketandroid.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.Market;
import com.example.framgia.imarketandroid.data.listener.OnRecyclerItemInteractListener;

import java.util.List;

/**
 * Created by yue on 20/07/2016.
 */
public class RecyclerMarketAdapter extends RecyclerView.Adapter<RecyclerMarketAdapter.ItemHolder> {
    private List<Market> mMarkets;
    private OnRecyclerItemInteractListener mListener;

    public RecyclerMarketAdapter(List<Market> markets) {
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
        Market market = mMarkets.get(position);
        holder.textName.setText(market.getName());
        holder.textAddress.setText(market.getAddress());
        holder.imageMarket.setImageResource(R.drawable.logo_big_c);
        holder.itemRecyclerMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMarkets != null ? mMarkets.size() : 0;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imageMarket;
        public TextView textName;
        public TextView textAddress;
        public View itemRecyclerMarket;

        public ItemHolder(View itemView) {
            super(itemView);
            imageMarket = (ImageView) itemView.findViewById(R.id.image_market);
            textName = (TextView) itemView.findViewById(R.id.text_name);
            textAddress = (TextView) itemView.findViewById(R.id.text_address);
            itemRecyclerMarket = itemView.findViewById(R.id.item_recycler_market);
        }
    }
}
