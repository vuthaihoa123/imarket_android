package com.example.framgia.imarketandroid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.CartItem;
import com.example.framgia.imarketandroid.ui.widget.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VULAN on 8/30/2016.
 */
public class HistoryTimeAdapter extends RecyclerView.Adapter<HistoryTimeAdapter.ItemHolder> {

    private List<String> mNameHeaders;
    private List<CartItem> mCartItems;
    private HistoryOrderAdapter mHistoryOrderAdapter;
    private Context mContext;

    public HistoryTimeAdapter(List<String> mNameHeaders, List<CartItem> cartItems, Context context) {
        this.mNameHeaders = new ArrayList<>(mNameHeaders);
        this.mCartItems = cartItems;
        this.mContext = context;
    }

    @Override

    public HistoryTimeAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_history_time, parent, false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryTimeAdapter.ItemHolder holder, final int position) {
        String nameHeader = mNameHeaders.get(position);
        List<CartItem> cartItemsChilds = new ArrayList<>();
        int size = mCartItems.size();
        for (int i = 0; i < size; i++) {
            if (nameHeader.equals(mCartItems.get(i).getDate())) {
                cartItemsChilds.add(mCartItems.get(i));
            }
        }
        holder.dateText.setText(nameHeader);
        mHistoryOrderAdapter = new HistoryOrderAdapter(cartItemsChilds, mContext);
        mHistoryOrderAdapter.setOnRemoveItemListener(new HistoryOrderAdapter.onRemoveItemListener() {
            @Override
            public void onRemoveButtonClick(int total) {
                if (total == 0) {
                    removeItem(position);
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        holder.recyclerView.addItemDecoration(new LinearItemDecoration(mContext));
        holder.recyclerView.setAdapter(mHistoryOrderAdapter);
    }

    @Override
    public int getItemCount() {
        return mNameHeaders.size();
    }

    private void removeItem(int position) {
        mNameHeaders.remove(position);
        notifyDataSetChanged();
    }

    private void addItem(int position, String model) {
        mNameHeaders.add(position, model);
        notifyItemInserted(position);
    }

    private void moveItem(int fromPosition, int toPosition) {
        String category = mNameHeaders.remove(fromPosition);
        mNameHeaders.add(toPosition, category);
        notifyItemMoved(fromPosition, toPosition);
    }

    private void applyAndAnimateRemovals(List<String> categoryList) {
        int size = mNameHeaders.size();
        for (int i = size - 1; i >= 0; i--) {
            String item = mNameHeaders.get(i);
            if (!categoryList.contains(item)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAddition(List<String> categoryList) {
        for (int i = 0, count = categoryList.size(); i < count; i++) {
            String categoryProduct = categoryList.get(i);
            if (!mNameHeaders.contains(categoryProduct)) {
                addItem(i, categoryProduct);
            }
        }
    }

    private void applyAndAnimateMoveItems(List<String> categoryList) {
        int size = categoryList.size();
        for (int toPosition = size - 1; toPosition > 0; toPosition--) {
            String item = categoryList.get(toPosition);
            int fromPosition = mNameHeaders.indexOf(item);
            if (fromPosition != 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public void animateTo(List<String> list) {
        applyAndAnimateAddition(list);
        applyAndAnimateMoveItems(list);
        applyAndAnimateRemovals(list);
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView dateText;
        public LinearLayout layoutHeader;
        public RecyclerView recyclerView;
        public ImageView imageArrow;

        public ItemHolder(View itemView) {
            super(itemView);
            dateText = (TextView) itemView.findViewById(R.id.text_header);
            layoutHeader = (LinearLayout) itemView.findViewById(R.id.layout_header);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_history_time);
            imageArrow = (ImageView) itemView.findViewById(R.id.image_arrow);
            layoutHeader.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (recyclerView.getVisibility() == View.GONE) {
                recyclerView.setVisibility(View.VISIBLE);
                imageArrow.setImageResource(R.drawable.ic_arrow_up);
            } else {
                recyclerView.setVisibility(View.GONE);
                imageArrow.setImageResource(R.drawable.ic_arrow_down);
            }
        }
    }
}
