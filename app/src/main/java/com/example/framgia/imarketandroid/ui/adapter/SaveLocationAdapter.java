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
import com.example.framgia.imarketandroid.data.listener.OnRecyclerItemInteractListener;
import com.example.framgia.imarketandroid.data.model.CartItem;
import com.example.framgia.imarketandroid.data.model.SavedPointItem;
import com.example.framgia.imarketandroid.util.SystemUtil;

import java.util.ArrayList;

/**
 * Created by framgia on 07/11/2016.
 */
public class SaveLocationAdapter extends RecyclerView.Adapter<SaveLocationAdapter.ViewHolder> {
    private ArrayList<SavedPointItem> mItems = new ArrayList<>();
    private Context mContext;
    private OnRecyclerItemInteractListener mListener;
    private int mPosition;

    // Provide a suitable constructor (depends on the kind of dataset)
    public SaveLocationAdapter(Context context, ArrayList<SavedPointItem> myItems) {
        mContext = context;
        mItems = myItems;
    }

    public void setOnRecyclerItemInteractListener(OnRecyclerItemInteractListener listener) {
        mListener = listener;
    }

    public void setItems(ArrayList<SavedPointItem> items) {
        mItems = items;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SaveLocationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_save_point, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(final SaveLocationAdapter.ViewHolder holder, int position) {
        final SavedPointItem savedPointItem = mItems.get(position);
        holder.mIvPointSaved.setImageResource(savedPointItem.getmAvatar());
        holder.mTvNamePoint.setText(savedPointItem.getmNamePoint());
        holder.mTvNotepoint.setText(savedPointItem.getmNotePoint());
        holder.mIvDelPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mContext.getResources().getString(R.string.remove) + " "
                    + savedPointItem.getmNamePoint(), Toast.LENGTH_SHORT).show();
                savedPointItem.setIsDeleted(true);
                mItems.remove(savedPointItem);
                notifyDataSetChanged();
            }
        });
        holder.mPosition = position;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mIvPointSaved;
        public TextView mTvNamePoint;
        public TextView mTvNotepoint;
        public ImageView mIvDelPoint;
        private OnRecyclerItemInteractListener mListener;
        private int mPosition;

        public ViewHolder(View itemView, OnRecyclerItemInteractListener listener) {
            super(itemView);
            this.mListener = listener;
            mIvPointSaved = (ImageView) itemView.findViewById(R.id.iv_save_point);
            mTvNamePoint = (TextView) itemView.findViewById(R.id.tv_name_point);
            mTvNotepoint = (TextView) itemView.findViewById(R.id.tv_note_point);
            mIvDelPoint = (ImageView) itemView.findViewById(R.id.iv_del_save_point);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, mPosition);
            }
        }
    }

    public ArrayList<SavedPointItem> getItems() {
        return mItems;
    }
}
