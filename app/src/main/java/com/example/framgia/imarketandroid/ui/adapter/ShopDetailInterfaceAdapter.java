package com.example.framgia.imarketandroid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.AlbumShop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phongtran on 07/09/2016.
 */
public class ShopDetailInterfaceAdapter
    extends RecyclerView.Adapter<ShopDetailInterfaceAdapter.ShopInterfaceViewHolver> {
    private Context mContext;
    private List<AlbumShop> mListAlbum = new ArrayList<>();
    private OnClickAlbumListener mAlbumListener;

    public ShopDetailInterfaceAdapter(Context context,
                                      List<AlbumShop> listAlbum) {
        this.mContext = context;
        this.mListAlbum = listAlbum;
        if (mContext instanceof OnClickAlbumListener) {
            mAlbumListener = (OnClickAlbumListener) mContext;
        } else {
            throw new RuntimeException(
                mContext.toString() + context.getString(R.string.must_album));
        }
    }

    @Override
    public ShopInterfaceViewHolver onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_album_shop, parent, false);
        return new ShopInterfaceViewHolver(view);
    }

    @Override
    public void onBindViewHolder(ShopInterfaceViewHolver holder,
                                 int position) {
        holder.mAlbumShop = mListAlbum.get(position);
        holder.mImageViewAlbum.setImageResource(holder.mAlbumShop.getImageId());
        holder.mTextViewNameAlbum.setText(holder.mAlbumShop.getNameAlbum());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO some thing when click album item
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListAlbum == null ? 0 : mListAlbum.size();
    }

    public class ShopInterfaceViewHolver extends RecyclerView.ViewHolder {
        private final View mView;
        private ImageView mImageViewAlbum;
        private TextView mTextViewNameAlbum;
        private AlbumShop mAlbumShop;

        public ShopInterfaceViewHolver(View itemView) {
            super(itemView);
            mView = itemView;
            mImageViewAlbum = (ImageView) itemView.findViewById(R.id.image_item_album_shop);
            mTextViewNameAlbum = (TextView) itemView.findViewById(R.id.text_name_item_album_shop);
        }
    }

    public interface OnClickAlbumListener {
        void OnClickAlbumShop(AlbumShop albumShop, int position);
    }
}
