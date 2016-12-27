package com.example.framgia.imarketandroid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.Comment;
import com.example.framgia.imarketandroid.ui.views.CustomStarView;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by phongtran on 26/08/2016.
 */
public class CommentStoreAdapter extends RecyclerView.Adapter<CommentStoreAdapter.SuggestStoreViewHolder> {
    private Context mContext;
    private boolean mIsSuggestFrag;
    private List<Comment> mListOldMessage;
    private OnPreviewCommentListener mListener;

    public CommentStoreAdapter(Context context, List<Comment> listOldMessage) {
        this.mContext = context;
        this.mListOldMessage = listOldMessage;
    }

    public CommentStoreAdapter(Context context, List<Comment> listOldMessage, boolean isSuggestStoreFrag) {
        this.mContext = context;
        this.mListOldMessage = listOldMessage;
        this.mIsSuggestFrag = isSuggestStoreFrag;
    }

    @Override
    public SuggestStoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(mContext).inflate(R.layout.item_message_rate, parent, false);
        return new SuggestStoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SuggestStoreViewHolder holder, int position) {
        Comment messageSuggestStore = mListOldMessage.get(position);
        holder.mImageViewAvatarItemMessageRate
                .setImageResource(messageSuggestStore.getImageViewAvatar());
        holder.mTextViewContentMessage.setText(messageSuggestStore.getTextViewContent());
        SystemUtil.makeTextViewResizable(mContext, holder.mTextViewContentMessage,
                Constants.MAX_LINE_SPAN_TEXT, mContext.getString(R.string.view_more), true);
        holder.mTextViewTitleMessage.setText(messageSuggestStore.getTextViewTitle());
        holder.mTextViewNameUser.setText(messageSuggestStore.getNameUser());
        holder.mTextViewCurDate.setText(SystemUtil.formatTimeNow(mContext,
                (System.currentTimeMillis() - messageSuggestStore.getTimeNow()) / Constants.SECOND));
        int totalFullStar = messageSuggestStore.getTotalStar();
        for (int i = 0; i < totalFullStar; i++) {
            holder.mStarList.get(i).setChecked(true);
            holder.mStarList.get(i).setSelectedStar();
        }
        for (int i = totalFullStar; i < Constants.NUMBER_OF_STARS; i++) {
            holder.mStarList.get(i).setChecked(false);
            holder.mStarList.get(i).setSelectedStar();
        }
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
        private List<CustomStarView> mStarList = new ArrayList<>();
        private LinearLayout mLayoutStar;

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
            mLayoutStar = (LinearLayout) mView.findViewById(R.id.layout_stars);
            addStarList();
        }

        private void addStarList() {
            mStarList = new ArrayList<>();
            for (int i = 0; i < Constants.NUMBER_OF_STARS; i++) {
                CustomStarView customStarView = new CustomStarView(mContext, i);
                mStarList.add(customStarView);
                mStarList.get(i).getView().requestLayout();
                mLayoutStar.addView(customStarView.getView());
                mLayoutStar.invalidate();
            }
        }
    }
}
