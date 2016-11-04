package com.example.framgia.imarketandroid.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.CartItem;
import com.example.framgia.imarketandroid.ui.activity.DetailsProductActivity;

import java.util.List;

/**
 * Created by VULAN on 8/29/2016.
 */
public class HistoryOrderAdapter extends RecyclerView.Adapter<HistoryOrderAdapter.ItemHolder> {

    private List<CartItem> mCartItems;
    private Context mContext;
    private onRemoveItemListener mOnRemoveItemListener;

    public HistoryOrderAdapter(List<CartItem> mCartItems, Context context) {
        this.mCartItems = mCartItems;
        mContext = context;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_history_order, parent, false);
        return new ItemHolder(view);
    }

    public void setOnRemoveItemListener(onRemoveItemListener mOnRemoveItemListener) {
        this.mOnRemoveItemListener = mOnRemoveItemListener;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, final int position) {
        final CartItem cartItem = mCartItems.get(position);
        holder.textPrice.setText(cartItem.getPriceProduct() + " " + mContext.getResources().getString(R.string.vnd_uppercase));
        holder.textName.setText(cartItem.getNameProduct());
        holder.textQuantity.setText(mContext.getResources().getString(R.string.quantity_second) + cartItem.getQuantity());
        holder.layoutRemoving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(position);
                if (getItemCount() == 0) {
                    if (mOnRemoveItemListener != null) {
                        mOnRemoveItemListener.onRemoveButtonClick(getItemCount());
                    }
                }
            }
        });
        holder.layoutDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, DetailsProductActivity.class));
            }
        });
    }

    private void removeItem(int position) {
        mCartItems.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCartItems.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        public TextView textName;
        public TextView textQuantity;
        public TextView textPrice;
        public LinearLayout layoutDetail;
        public ViewGroup layoutRemoving;

        public ItemHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.text_name);
            textPrice = (TextView) itemView.findViewById(R.id.text_price);
            textQuantity = (TextView) itemView.findViewById(R.id.text_quantity);
            layoutRemoving = (LinearLayoutCompat) itemView.findViewById(R.id.layout_remove);
            layoutDetail = (LinearLayout) itemView.findViewById(R.id.layout_detail);
        }
    }

    public interface onRemoveItemListener {
        void onRemoveButtonClick(int total);
    }
}
