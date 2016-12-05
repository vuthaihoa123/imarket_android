package com.example.framgia.imarketandroid.ui.fragments;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.FakeContainer;
import com.example.framgia.imarketandroid.data.model.Comment;
import com.example.framgia.imarketandroid.data.model.Showcase;
import com.example.framgia.imarketandroid.ui.adapter.CommentStoreAdapter;
import com.example.framgia.imarketandroid.ui.views.CustomStarView;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.ShowcaseGuideUtil;
import com.example.framgia.imarketandroid.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

import static com.example.framgia.imarketandroid.util.Constants.LIMIT_STAR;

/**
 * Created by phongtran on 26/08/2016.
 */
public class SuggestStoreFragment extends Fragment implements View.OnClickListener, CustomStarView.onItemClickListener {
    private ImageView mImageViewAvaStore;
    private TextView mTextViewNameStore, mTextViewHastagStore;
    private TextView mTextViewProportionVote, mTextViewCountVote;
    private ImageView mImageViewStar1, mImageViewStar2, mImageViewStar3, mImageViewStar4,
            mImageViewStar5;
    private LinearLayoutCompat mButtonPostSuggestStore;
    private LinearLayoutCompat mButtonPostComment;
    private RecyclerView mRecyclerViewOldMessage;
    private List<Comment> mMessageSuggestStoreList = new ArrayList<>();
    private List<Comment> mListRate = new ArrayList<>();
    private CommentStoreAdapter mSuggestStoreAdapter;
    private AlertDialog mAlertDialogPostMessage;
    private TextView mTextViewStar1, mTextViewStar2, mTextViewStar3, mTextViewStar4, mTextViewStar5;
    private View mView;
    private List<CustomStarView> mListStar;
    private EditText mTextTitle, mTextContent;
    private LinearLayout mLayoutStar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return mView = inflater.inflate(R.layout.fragment_suggest_store, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findView();
        fakeDataMessage();
        initRecycle();
        event();
    }

    private void initRecycle() {
        mSuggestStoreAdapter = new CommentStoreAdapter(getActivity(), mMessageSuggestStoreList);
        mRecyclerViewOldMessage.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewOldMessage.setAdapter(mSuggestStoreAdapter);
    }

    private void fakeDataMessage() {
        setTexts();
        Comment msm = new Comment(
                R.drawable.avatar,
                getString(R.string.name),
                getString(R.string.message_rate),
                getString(R.string.name_user),
                SystemUtil.getCurDate()
        );
        for (int i = 0; i < Constants.COMMENT_LIST_SIZE; i++) {
            mMessageSuggestStoreList.add(msm);
        }
    }

    private void findView() {
        mImageViewAvaStore = (ImageView) mView.findViewById(R.id.image_ava_store);
        mTextViewNameStore = (TextView) mView.findViewById(R.id.text_name_store);
        mTextViewHastagStore = (TextView) mView.findViewById(R.id.text_hastag_store);
        mTextViewProportionVote = (TextView) mView.findViewById(R.id.tv_general_rate);
        mTextViewCountVote = (TextView) mView.findViewById(R.id.tv_amount_of_rates);
        mImageViewStar1 = (ImageView) mView.findViewById(R.id.image_start_1);
        mImageViewStar2 = (ImageView) mView.findViewById(R.id.image_start_2);
        mImageViewStar3 = (ImageView) mView.findViewById(R.id.image_start_3);
        mImageViewStar4 = (ImageView) mView.findViewById(R.id.image_start_4);
        mImageViewStar5 = (ImageView) mView.findViewById(R.id.image_start_5);
        mButtonPostSuggestStore = (LinearLayoutCompat) mView.findViewById(R.id.button_post_product);
        mRecyclerViewOldMessage = (RecyclerView) mView.findViewById(R.id.recycleview_message_rate);
    }

    private void setTexts() {
        mTextViewNameStore.setText(FakeContainer.getNameProduct());
        mTextViewProportionVote.setText(FakeContainer.getGeneralRate());
        mTextViewCountVote.setText(FakeContainer.getAmountOfRates());
    }

    private void event() {
        mButtonPostSuggestStore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_post_product:
                initAlertDiaLogPostMessage();
                break;
            case R.id.button_post_message_rate:
                String title = mTextTitle.getText().toString();
                String content = mTextContent.getText().toString();
                Comment newMessage = new Comment(
                        R.drawable.avatar,
                        title,
                        content,
                        getString(R.string.name_user),
                        SystemUtil.getCurDate()
                );
                newMessage.setImageStars(getTotalStar());
                mMessageSuggestStoreList.set(0, newMessage);
                mSuggestStoreAdapter.notifyDataSetChanged();
                mAlertDialogPostMessage.dismiss();
                break;
            default:
                break;
        }
    }

    private void initAlertDiaLogPostMessage() {
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.dialog_post_message_rate,
                (ViewGroup) getActivity().findViewById(R.id.view_group_details));
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptsView);
        mTextViewStar1 = (TextView) promptsView.findViewById(R.id.text_start_1);
        mTextViewStar2 = (TextView) promptsView.findViewById(R.id.text_start_2);
        mTextViewStar3 = (TextView) promptsView.findViewById(R.id.text_start_3);
        mTextViewStar4 = (TextView) promptsView.findViewById(R.id.text_start_4);
        mTextViewStar5 = (TextView) promptsView.findViewById(R.id.text_start_5);
        mButtonPostComment = (LinearLayoutCompat) promptsView.findViewById(R.id.button_post_message_rate);
        mTextTitle = (EditText) promptsView.findViewById(R.id.edit_text_message_rate_title);
        mTextContent = (EditText) promptsView.findViewById(R.id.edit_text_message_rate_comment);
        mLayoutStar = (LinearLayout) promptsView.findViewById(R.id.layout_stars);
        mButtonPostComment.setOnClickListener(this);
        mTextTitle.setOnClickListener(this);
        mTextContent.setOnClickListener(this);
        mAlertDialogPostMessage = alertDialogBuilder.create();
        mAlertDialogPostMessage.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mAlertDialogPostMessage.show();
        addStarList();
    }

    public void initGuideSuggestStore() {
        ShowcaseGuideUtil.singleShowcase(getActivity(), Constants.SHOWCASE_ID_SUGGEST_STORE, new
                Showcase(mButtonPostSuggestStore, getString(R.string.sequence_write_vote)));
    }

    public int getTotalStar() {
        int count = 0;
        for (int i = 0; i < LIMIT_STAR; i++) {
            if (mListStar.get(i).isChecked()) {
                count++;
            }
        }
        return count;
    }

    private void addStarList() {
        mListStar = new ArrayList<>();
        for (int i = 0; i < Constants.LIMIT_STAR; i++) {
            CustomStarView customStarView = new CustomStarView(getActivity(), i);
            mListStar.add(customStarView);
            mListStar.get(i).setOnItemClickListener(this);
            mListStar.get(i).getView().requestLayout();
            mLayoutStar.addView(customStarView.getView());
            mLayoutStar.invalidate();
        }
    }

    @Override
    public void onItemClick(int position) {
        setStars(position);
    }

    public void setStars(int position) {
        for (int i = 0; i <= position; i++) {
            mListStar.get(i).setChecked(true);
            mListStar.get(i).setSelectedStar();
        }
        for (int i = position + 1; i < Constants.LIMIT_STAR; i++) {
            mListStar.get(i).setChecked(false);
            mListStar.get(i).setSelectedStar();
        }
    }
}
