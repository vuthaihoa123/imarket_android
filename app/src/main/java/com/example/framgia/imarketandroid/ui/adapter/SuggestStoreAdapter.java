package com.example.framgia.imarketandroid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.MessageSuggestStore;

import java.util.List;

/**
 * Created by phongtran on 26/08/2016.
 */
public class SuggestStoreAdapter extends RecyclerView.Adapter<SuggestStoreAdapter.SuggestStoreViewHolder>{
    private Context mContext;
    private List<MessageSuggestStore> mListOldMessage;

    public SuggestStoreAdapter(Context context, List<MessageSuggestStore> listOldMessage) {
        this.mContext = context;
        this.mListOldMessage = listOldMessage;
    }

    @Override
    public SuggestStoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_message_rate,parent,false);
        return new SuggestStoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SuggestStoreViewHolder holder, int position) {
        holder.mMessageSuggestStore = mListOldMessage.get(position);
        holder.mImageViewAvatarItemMessageRate
                .setImageResource(holder.mMessageSuggestStore.getmImageViewAva());
        holder.mTextViewContentMessage.setText(holder.mMessageSuggestStore.getmTextViewContent());
        holder.mTextViewNameUser.setText(holder.mMessageSuggestStore.getmNameUser());
        holder.mImageViewStar1.setImageResource(holder.mMessageSuggestStore.getmImageViewStar1());
        holder.mImageViewStar2.setImageResource(holder.mMessageSuggestStore.getmImageViewStar2());
        holder.mImageViewStar3.setImageResource(holder.mMessageSuggestStore.getmImageViewStar3());
        holder.mImageViewStar4.setImageResource(holder.mMessageSuggestStore.getmImageViewStar4());
        holder.mImageViewStar5.setImageResource(holder.mMessageSuggestStore.getmImageViewStar5());
    }

    @Override
    public int getItemCount() {
        return mListOldMessage == null ? 0 : mListOldMessage.size();
    }

    public class SuggestStoreViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageViewAvatarItemMessageRate;
        private TextView mTextViewContentMessage;
        private TextView mTextViewNameUser;
        private ImageView mImageViewStar1;
        private ImageView mImageViewStar2;
        private ImageView mImageViewStar3;
        private ImageView mImageViewStar4;
        private ImageView mImageViewStar5;
        private MessageSuggestStore mMessageSuggestStore;
        private final View mView;

        public SuggestStoreViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mImageViewAvatarItemMessageRate = (ImageView)mView
                    .findViewById(R.id.image_avatar_item_message_rate);
            mTextViewContentMessage = (TextView) mView
                    .findViewById(R.id.text_content_item_message_rate);
            mTextViewNameUser = (TextView) mView.findViewById(R.id.text_name_user);
            mImageViewStar1 = (ImageView)mView.findViewById(R.id.image_start_item_1);
            mImageViewStar2 = (ImageView)mView.findViewById(R.id.image_start_item_2);
            mImageViewStar3 = (ImageView)mView.findViewById(R.id.image_start_item_3);
            mImageViewStar4 = (ImageView)mView.findViewById(R.id.image_start_item_4);
            mImageViewStar5 = (ImageView)mView.findViewById(R.id.image_start_item_5);
        }
    }
}
