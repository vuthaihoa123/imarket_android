package com.example.framgia.imarketandroid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.CartItem;
import com.example.framgia.imarketandroid.util.SystemUtil;

import java.util.ArrayList;

/**
 * Created by hoavt on 22/07/2016.
 */
public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.ViewHolder> {
    private ArrayList<CartItem> mItems = new ArrayList<>();
    private Context mContext;

    // Provide a suitable constructor (depends on the kind of dataset)
    public CartProductAdapter(Context context, ArrayList<CartItem> myItems) {
        mContext = context;
        mItems = myItems;
    }

    public void setItems(ArrayList<CartItem> items) {
        mItems = items;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CartProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_cart_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final CartProductAdapter.ViewHolder holder, int position) {
        final CartItem cartItem = mItems.get(position);
        holder.mIvPreviewCart.setImageResource(cartItem.getIdRes());
        holder.mTvNameCart.setText(cartItem.getNameProduct());
        holder.mTvPriceCart.setText(SystemUtil.formatMoneyStr(cartItem.getPriceProduct()));
        holder.mIvAscendQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                holder.mTvQuantityCart.setText(cartItem.getQuantity() + "");
            }
        });
        holder.mIvDescendQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartItem.getQuantity() <= 1)
                    return;
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                holder.mTvQuantityCart.setText(cartItem.getQuantity() + "");
            }
        });
        holder.mTvQuantityCart.setText(cartItem.getQuantity() + "");
        holder.mIvDelProductCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mContext.getResources().getString(R.string.remove) + " "
                    + cartItem.getNameProduct(), Toast.LENGTH_SHORT).show();
                cartItem.setIsDeleted(true);
                mItems.remove(cartItem);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvPreviewCart;
        public TextView mTvNameCart;
        public TextView mTvPriceCart;
        public ImageView mIvAscendQuantity;
        public ImageView mIvDescendQuantity;
        public TextView mTvQuantityCart;
        public ImageView mIvDelProductCart;

        public ViewHolder(View itemView) {
            super(itemView);
            mIvPreviewCart = (ImageView) itemView.findViewById(R.id.iv_preview_product_cart);
            mTvNameCart = (TextView) itemView.findViewById(R.id.tv_name_product_cart);
            mTvPriceCart = (TextView) itemView.findViewById(R.id.tv_price_product_cart);
            mIvAscendQuantity = (ImageView) itemView.findViewById(R.id.iv_ascend_quantity_product);
            mIvDescendQuantity =
                (ImageView) itemView.findViewById(R.id.iv_descend_quantity_product);
            mTvQuantityCart = (TextView) itemView.findViewById(R.id.tv_product_quantity_cart);
            mIvDelProductCart = (ImageView) itemView.findViewById(R.id.iv_del_product_cart);
        }
    }

    public ArrayList<CartItem> getItems() {
        return mItems;
    }
}
