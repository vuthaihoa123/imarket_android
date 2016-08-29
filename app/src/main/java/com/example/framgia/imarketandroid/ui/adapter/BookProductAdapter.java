package com.example.framgia.imarketandroid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.ItemBooking;

import java.util.ArrayList;

/**
 * Created by hoavt on 22/07/2016.
 */
public class BookProductAdapter extends RecyclerView.Adapter<BookProductAdapter.ViewHolder> {

    private ArrayList<ItemBooking> mItems = new ArrayList<>();
    private Context mContext;

    // Provide a suitable constructor (depends on the kind of dataset)
    public BookProductAdapter(Context context, ArrayList<ItemBooking> myItems) {
        mContext = context;
        mItems = myItems;
    }

    public void setItems(ArrayList<ItemBooking> items) {
        mItems = items;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BookProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book_product, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(BookProductAdapter.ViewHolder holder, int position) {
        ItemBooking itemBooking = mItems.get(position);
        ImageView ivBookingIcon = holder.mIvBookingIcon;
        TextView tvBookingDescribe = holder.mTvBookingDes;
        ivBookingIcon.setImageResource(itemBooking.getIdResIcon());
        tvBookingDescribe.setText(itemBooking.getDescribe());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvBookingIcon;
        public TextView mTvBookingDes;

        public ViewHolder(View itemView) {
            super(itemView);
            mIvBookingIcon = (ImageView) itemView.findViewById(R.id.iv_booking_icon);
            mTvBookingDes = (TextView) itemView.findViewById(R.id.tv_booking_describe);
        }
    }
}

