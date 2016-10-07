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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by phongtran on 26/08/2016.
 */
public class CommentStoreAdapter extends RecyclerView.Adapter<CommentStoreAdapter.SuggestStoreViewHolder> {
    private Context mContext;
    private List<MessageSuggestStore> mListOldMessage;
    private OnPreviewCommentListener mListener;

    public CommentStoreAdapter(Context context, List<MessageSuggestStore> listOldMessage) {
        this.mContext = context;
        this.mListOldMessage = listOldMessage;
    }

    @Override
    public SuggestStoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_message_rate, parent, false);
        return new SuggestStoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SuggestStoreViewHolder holder, int position) {
        MessageSuggestStore messageSuggestStore = mListOldMessage.get(position);
        holder.mImageViewAvatarItemMessageRate.setImageResource(messageSuggestStore.getImageViewAvatar());
        holder.mTextViewContentMessage.setText(messageSuggestStore.getTextViewContent());
        holder.mTextViewTitleMessage.setText(messageSuggestStore.getTextViewTitle());
        holder.mTextViewNameUser.setText(messageSuggestStore.getNameUser());
        holder.mTextViewCurDate.setText(messageSuggestStore.getCurDate());
        holder.mImageViewStar1.setImageResource(messageSuggestStore.getImageViewStar1());
        holder.mImageViewStar2.setImageResource(messageSuggestStore.getImageViewStar2());
        holder.mImageViewStar3.setImageResource(messageSuggestStore.getImageViewStar3());
        holder.mImageViewStar4.setImageResource(messageSuggestStore.getImageViewStar4());
        holder.mImageViewStar5.setImageResource(messageSuggestStore.getImageViewStar5());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onPreviewCommentClicked();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListOldMessage == null ? 0 : mListOldMessage.size();
    }

    public CommentStoreAdapter setListener(OnPreviewCommentListener listener) {
        this.mListener = listener;
        return this;
    }

    public interface OnPreviewCommentListener {
        public void onPreviewCommentClicked();
    }

    public class SuggestStoreViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private CircleImageView mImageViewAvatarItemMessageRate;
        private TextView mTextViewCurDate;
        private TextView mTextViewContentMessage;
        private TextView mTextViewNameUser;
        private TextView mTextViewTitleMessage;
        private ImageView mImageViewStar1;
        private ImageView mImageViewStar2;
        private ImageView mImageViewStar3;
        private ImageView mImageViewStar4;
        private ImageView mImageViewStar5;

        public SuggestStoreViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mImageViewAvatarItemMessageRate = (CircleImageView) mView
                    .findViewById(R.id.image_avatar_item_message_rate);
            mTextViewContentMessage = (TextView) mView
                    .findViewById(R.id.text_content_item_message_rate);
            mTextViewTitleMessage = (TextView) mView.findViewById(R.id.tv_title_item_message_rate);
            mTextViewNameUser = (TextView) mView.findViewById(R.id.text_name_user);
            mTextViewCurDate = (TextView) mView.findViewById(R.id.tv_rated_date);
            mImageViewStar1 = (ImageView) mView.findViewById(R.id.image_start_item_1);
            mImageViewStar2 = (ImageView) mView.findViewById(R.id.image_start_item_2);
            mImageViewStar3 = (ImageView) mView.findViewById(R.id.image_start_item_3);
            mImageViewStar4 = (ImageView) mView.findViewById(R.id.image_start_item_4);
            mImageViewStar5 = (ImageView) mView.findViewById(R.id.image_start_item_5);
        }
    }
}
