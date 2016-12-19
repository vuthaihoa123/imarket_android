package com.example.framgia.imarketandroid.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.CategorySaleOff;
import com.example.framgia.imarketandroid.data.model.ImageEvent1;
import com.example.framgia.imarketandroid.data.model.ImageEvent2;
import com.example.framgia.imarketandroid.data.model.ItemProduct;
import com.example.framgia.imarketandroid.data.model.NewEvent;
import com.example.framgia.imarketandroid.util.DialogShareUtil;
import com.facebook.CallbackManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toannguyen201194 on 07/09/2016.
 */
public class SaleOffEventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TYPE_BANNER = 1;
    private final int TYPE_BANNERTWO = 2;
    private final int TYPE_CATEGORY = 5;
    private final int TYPE_NEW_EVENT = 6;
    private List<Object> mItems;
    private Activity mContext;
    private CallbackManager mCallback;
    public SaleOffEventAdapter(List<Object> items, Activity context, CallbackManager cb) {
        mItems = items;
        mContext = context;
        mCallback = cb;
    }

    //sau khi da xet duoc loai ta khoai tao viewholder theo loai ( ham nay chay thu 2)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_BANNER:
                view = inflater.inflate(R.layout.item_banner_saleoff_event, parent, false);
                viewHolder = new HolderOneBannerImage(view);
                break;
            case TYPE_BANNERTWO:
                view = inflater.inflate(R.layout.item_two_banner_saleoff_event, parent, false);
                viewHolder = new HolderTwoBanner(view);
                break;
            case TYPE_CATEGORY:
                view = inflater.inflate(R.layout.item_saleoff_event, parent, false);
                viewHolder = new HolderCategorySaleOff(view);
                break;
            case TYPE_NEW_EVENT:
                view = inflater.inflate(R.layout.item_event_other, parent, false);
                viewHolder = new HolderNewSaleOff(view);
                break;
        }
        return viewHolder;
    }
    //sau khi da khoi tao duoc tao chuyen du lieu va potision (cai nay cuoi cung)

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_BANNER:
                HolderOneBannerImage viewHolder1 = (HolderOneBannerImage) holder;
                configureViewHolder1(viewHolder1, position);
                break;
            case TYPE_BANNERTWO:
                HolderTwoBanner holderTwoBanner = (HolderTwoBanner) holder;
                configureViewHolder2(holderTwoBanner, position);
                break;
            case TYPE_CATEGORY:
                HolderCategorySaleOff holdeTwoBanner = (HolderCategorySaleOff) holder;
                configureViewHolder3(holdeTwoBanner, position);
                break;
            case TYPE_NEW_EVENT:
                HolderNewSaleOff newSaleOff = (HolderNewSaleOff) holder;
                configureViewHolder4(newSaleOff, position);
                break;
        }
        holder.getItemViewType();
    }


    // thao tác cho từng item
    private void configureViewHolder1(HolderOneBannerImage vh1, int position) {
        ImageEvent1 imageEvent1 = (ImageEvent1) mItems.get(position);
        if (imageEvent1 != null) {
            vh1.imageBanner1.setImageResource(imageEvent1.getBanner1());
        }
    }

    private void configureViewHolder2(HolderTwoBanner vh2, int position) {
        ImageEvent2 imageEvent2 = (ImageEvent2) mItems.get(position);
        if (imageEvent2 != null) {
            vh2.imageBanner2.setImageResource(imageEvent2.getBanner2());
            vh2.imageBanner3.setImageResource(imageEvent2.getBanner3());
        }
    }

    private void configureViewHolder3(HolderCategorySaleOff vh3, int position) {
        CategorySaleOff categorySaleOff = (CategorySaleOff) mItems.get(position);
        if (categorySaleOff != null) {
            List<ItemProduct> itemProductList = ((CategorySaleOff) mItems.get(position))
                .getProductList();
            vh3.mTextTitleCategory.setText(categorySaleOff.getNameSaleOff());
            vh3.mRecyclerView.setHasFixedSize(true);
            SaleOffListProductsAdapter saleOffListProductsAdapter = new SaleOffListProductsAdapter(mContext,
                (ArrayList<ItemProduct>) itemProductList);
            vh3.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL, false));
            vh3.mRecyclerView.setAdapter(saleOffListProductsAdapter);
        }
    }

    private void configureViewHolder4(HolderNewSaleOff vh4, int position) {
        NewEvent newEvent = (NewEvent) mItems.get(position);
        if (newEvent == null) return;
        vh4.mTextNameEvent.setText(newEvent.getNameEvent());
        vh4.mTextContentEvent.setText(newEvent.getContentEvent());
        vh4.mTextTimeEvent.setText(newEvent.getTimeEvent());
        vh4.mTextShareEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogShareUtil.dialogShare(mContext, R.drawable.ic_events, mCallback);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    // tra ve cho minh mot loai dựa vào loại ta sẽ xét được viewholder (cái này chạy đầu tiên)
    @Override
    public int getItemViewType(int position) {
        if (mItems.get(position) instanceof ImageEvent1) {
            return TYPE_BANNER;
        } else if (mItems.get(position) instanceof ImageEvent2) {
            return TYPE_BANNERTWO;
        } else if (mItems.get(position) instanceof NewEvent) {
            return TYPE_NEW_EVENT;
        }
        return TYPE_CATEGORY;
    }

    public class HolderTwoBanner extends RecyclerView.ViewHolder {
        private ImageView imageBanner2, imageBanner3;

        public HolderTwoBanner(View v) {
            super(v);
            imageBanner2 = (ImageView) v.findViewById(R.id.image_banner2);
            imageBanner3 = (ImageView) v.findViewById(R.id.image_banner3);
        }
    }

    public class HolderOneBannerImage extends RecyclerView.ViewHolder {
        private ImageView imageBanner1;

        public HolderOneBannerImage(View v) {
            super(v);
            imageBanner1 = (ImageView) v.findViewById(R.id.image_banner);
        }
    }

    public class HolderCategorySaleOff extends RecyclerView.ViewHolder {
        private TextView mTextTitleCategory;
        private RecyclerView mRecyclerView;

        public HolderCategorySaleOff(View v) {
            super(v);
            mRecyclerView = (RecyclerView) v.findViewById(R.id.recycle_saleoff_item);
            mTextTitleCategory = (TextView) v.findViewById(R.id.text_title_category);
        }
    }

    public class HolderNewSaleOff extends RecyclerView.ViewHolder {
        private TextView mTextNameEvent;
        private TextView mTextContentEvent;
        private TextView mTextTimeEvent;
        private TextView mTextShareEvent;

        public HolderNewSaleOff(View v) {
            super(v);
            mTextNameEvent = (TextView) v.findViewById(R.id.text_name_event);
            mTextContentEvent = (TextView) v.findViewById(R.id.text_content_event);
            mTextTimeEvent = (TextView) v.findViewById(R.id.text_time_event);
            mTextShareEvent = (TextView) v.findViewById(R.id.text_share_event);
        }
    }
}
