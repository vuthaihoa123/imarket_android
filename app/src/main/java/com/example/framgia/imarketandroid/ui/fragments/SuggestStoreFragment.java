package com.example.framgia.imarketandroid.ui.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.MessageSuggestStore;
import com.example.framgia.imarketandroid.data.model.Showcase;
import com.example.framgia.imarketandroid.ui.adapter.CommentStoreAdapter;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.ShowcaseGuideUtil;
import com.example.framgia.imarketandroid.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phongtran on 26/08/2016.
 */
public class SuggestStoreFragment extends Fragment implements View.OnClickListener {
    private ImageView mImageViewAvaStore;
    private TextView mTextViewNameStore, mTextViewHastagStore;
    private TextView mTextViewProportionVote, mTextViewCountVote;
    private ImageView mImageViewStar1, mImageViewStar2, mImageViewStar3, mImageViewStar4,
            mImageViewStar5;
    private Button mButtonPostSuggestStore;
    private RecyclerView mRecyclerViewOldMessage;
    private List<MessageSuggestStore> mMessageSuggestStoreList = new ArrayList<>();
    private CommentStoreAdapter mSuggestStoreAdapter;
    private AlertDialog mAlertDialogPostMessage;
    private Button mButtonStar1, mButtonStar2, mButtonStar3, mButtonStar4, mButtonStar5;
    private TextView mTextViewStar1, mTextViewStar2, mTextViewStar3, mTextViewStar4, mTextViewStar5;
    private MessageSuggestStore mMessage = new MessageSuggestStore();
    private View mView;

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
        MessageSuggestStore msm = new MessageSuggestStore(
                R.drawable.avatar,
                getString(R.string.name),
                getString(R.string.message_rate),
                getString(R.string.name_user),
                SystemUtil.getCurDate(),
                R.drawable.ic_star_full,
                R.drawable.ic_star_full,
                R.drawable.ic_star_full,
                R.drawable.ic_star_half,
                R.drawable.ic_star_empty);
        mMessageSuggestStoreList.add(msm);
        mMessageSuggestStoreList.add(msm);
        mMessageSuggestStoreList.add(msm);
        mMessageSuggestStoreList.add(msm);
    }

    private void findView() {
        mImageViewAvaStore = (ImageView) mView.findViewById(R.id.image_ava_store);
        mTextViewNameStore = (TextView) mView.findViewById(R.id.text_name_store);
        mTextViewHastagStore = (TextView) mView.findViewById(R.id.text_hastag_store);
        mTextViewProportionVote = (TextView) mView.findViewById(R.id.text_proportion_rate);
        mTextViewCountVote = (TextView) mView.findViewById(R.id.text_count_rate);
        mImageViewStar1 = (ImageView) mView.findViewById(R.id.image_start_1);
        mImageViewStar2 = (ImageView) mView.findViewById(R.id.image_start_2);
        mImageViewStar3 = (ImageView) mView.findViewById(R.id.image_start_3);
        mImageViewStar4 = (ImageView) mView.findViewById(R.id.image_start_4);
        mImageViewStar5 = (ImageView) mView.findViewById(R.id.image_start_5);
        mButtonPostSuggestStore = (Button) mView.findViewById(R.id.button_post_store);
        mRecyclerViewOldMessage = (RecyclerView) mView.findViewById(R.id.recycleview_message_rate);
    }

    private void event() {
        mButtonPostSuggestStore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_post_store:
                initAlertDiaLogPostMessage();
                break;
            default:
                break;
        }
    }

    private void initAlertDiaLogPostMessage() {
        mMessage.setImageViewStar1(R.drawable.ic_star_empty);
        mMessage.setImageViewStar2(R.drawable.ic_star_empty);
        mMessage.setImageViewStar3(R.drawable.ic_star_empty);
        mMessage.setImageViewStar4(R.drawable.ic_star_empty);
        mMessage.setImageViewStar5(R.drawable.ic_star_empty);
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

//        mButtonPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mMessage.setImageViewAva(R.drawable.avatar);
//                mMessage.setNameUser(getString(R.string.name_user));
//                mMessageSuggestStoreList.add(mMessage);
//                Collections.reverse(mMessageSuggestStoreList);
//                mSuggestStoreAdapter.notifyDataSetChanged();
//                mAlertDialogPostMessage.dismiss();
//            }
//        });

        mButtonStar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonStar1.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar2.setBackgroundResource(R.drawable.ic_star_empty);
                mButtonStar3.setBackgroundResource(R.drawable.ic_star_empty);
                mButtonStar4.setBackgroundResource(R.drawable.ic_star_empty);
                mButtonStar5.setBackgroundResource(R.drawable.ic_star_empty);
                mMessage.setImageViewStar1(R.drawable.ic_star_full);
                mMessage.setImageViewStar2(R.drawable.ic_star_empty);
                mMessage.setImageViewStar3(R.drawable.ic_star_empty);
                mMessage.setImageViewStar4(R.drawable.ic_star_empty);
                mMessage.setImageViewStar5(R.drawable.ic_star_empty);
            }
        });
        mButtonStar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonStar1.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar2.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar3.setBackgroundResource(R.drawable.ic_star_empty);
                mButtonStar4.setBackgroundResource(R.drawable.ic_star_empty);
                mButtonStar5.setBackgroundResource(R.drawable.ic_star_empty);
                mMessage.setImageViewStar1(R.drawable.ic_star_full);
                mMessage.setImageViewStar2(R.drawable.ic_star_full);
                mMessage.setImageViewStar3(R.drawable.ic_star_empty);
                mMessage.setImageViewStar4(R.drawable.ic_star_empty);
                mMessage.setImageViewStar5(R.drawable.ic_star_empty);
            }
        });
        mButtonStar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonStar1.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar2.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar3.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar4.setBackgroundResource(R.drawable.ic_star_empty);
                mButtonStar5.setBackgroundResource(R.drawable.ic_star_empty);
                mMessage.setImageViewStar1(R.drawable.ic_star_full);
                mMessage.setImageViewStar2(R.drawable.ic_star_full);
                mMessage.setImageViewStar3(R.drawable.ic_star_full);
                mMessage.setImageViewStar4(R.drawable.ic_star_empty);
                mMessage.setImageViewStar5(R.drawable.ic_star_empty);
            }
        });
        mButtonStar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonStar1.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar2.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar3.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar4.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar5.setBackgroundResource(R.drawable.ic_star_empty);
                mMessage.setImageViewStar1(R.drawable.ic_star_full);
                mMessage.setImageViewStar2(R.drawable.ic_star_full);
                mMessage.setImageViewStar3(R.drawable.ic_star_full);
                mMessage.setImageViewStar4(R.drawable.ic_star_full);
                mMessage.setImageViewStar5(R.drawable.ic_star_empty);
            }
        });
        mButtonStar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonStar1.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar2.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar3.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar4.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar5.setBackgroundResource(R.drawable.ic_star_full);
                mMessage.setImageViewStar1(R.drawable.ic_star_full);
                mMessage.setImageViewStar2(R.drawable.ic_star_full);
                mMessage.setImageViewStar3(R.drawable.ic_star_full);
                mMessage.setImageViewStar4(R.drawable.ic_star_full);
                mMessage.setImageViewStar5(R.drawable.ic_star_full);
            }
        });
        mTextViewStar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonStar1.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar2.setBackgroundResource(R.drawable.ic_star_empty);
                mButtonStar3.setBackgroundResource(R.drawable.ic_star_empty);
                mButtonStar4.setBackgroundResource(R.drawable.ic_star_empty);
                mButtonStar5.setBackgroundResource(R.drawable.ic_star_empty);
                mMessage.setImageViewStar1(R.drawable.ic_star_full);
                mMessage.setImageViewStar2(R.drawable.ic_star_empty);
                mMessage.setImageViewStar3(R.drawable.ic_star_empty);
                mMessage.setImageViewStar4(R.drawable.ic_star_empty);
                mMessage.setImageViewStar5(R.drawable.ic_star_empty);
            }
        });
        mTextViewStar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonStar1.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar2.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar3.setBackgroundResource(R.drawable.ic_star_empty);
                mButtonStar4.setBackgroundResource(R.drawable.ic_star_empty);
                mButtonStar5.setBackgroundResource(R.drawable.ic_star_empty);
                mMessage.setImageViewStar1(R.drawable.ic_star_full);
                mMessage.setImageViewStar2(R.drawable.ic_star_full);
                mMessage.setImageViewStar3(R.drawable.ic_star_empty);
                mMessage.setImageViewStar4(R.drawable.ic_star_empty);
                mMessage.setImageViewStar5(R.drawable.ic_star_empty);
            }
        });
        mTextViewStar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonStar1.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar2.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar3.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar4.setBackgroundResource(R.drawable.ic_star_empty);
                mButtonStar5.setBackgroundResource(R.drawable.ic_star_empty);
                mMessage.setImageViewStar1(R.drawable.ic_star_full);
                mMessage.setImageViewStar2(R.drawable.ic_star_full);
                mMessage.setImageViewStar3(R.drawable.ic_star_full);
                mMessage.setImageViewStar4(R.drawable.ic_star_empty);
                mMessage.setImageViewStar5(R.drawable.ic_star_empty);
            }
        });
        mTextViewStar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonStar1.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar2.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar3.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar4.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar5.setBackgroundResource(R.drawable.ic_star_empty);
                mMessage.setImageViewStar1(R.drawable.ic_star_full);
                mMessage.setImageViewStar2(R.drawable.ic_star_full);
                mMessage.setImageViewStar3(R.drawable.ic_star_full);
                mMessage.setImageViewStar4(R.drawable.ic_star_full);
                mMessage.setImageViewStar5(R.drawable.ic_star_empty);
            }
        });
        mTextViewStar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonStar1.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar2.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar3.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar4.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar5.setBackgroundResource(R.drawable.ic_star_full);
                mMessage.setImageViewStar1(R.drawable.ic_star_full);
                mMessage.setImageViewStar2(R.drawable.ic_star_full);
                mMessage.setImageViewStar3(R.drawable.ic_star_full);
                mMessage.setImageViewStar4(R.drawable.ic_star_full);
                mMessage.setImageViewStar5(R.drawable.ic_star_full);
            }
        });
        mAlertDialogPostMessage = alertDialogBuilder.create();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(mAlertDialogPostMessage.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        mAlertDialogPostMessage.setCanceledOnTouchOutside(true);
        mAlertDialogPostMessage.show();
        mAlertDialogPostMessage.getWindow().setAttributes(lp);
    }

    public void initGuideSuggestStore() {
        ShowcaseGuideUtil.singleShowcase(getActivity(), Constants.SHOWCASE_ID_SUGGEST_STORE, new
            Showcase(mButtonPostSuggestStore, getString(R.string.sequence_write_vote)));
    }
}
