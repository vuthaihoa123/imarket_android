package com.example.framgia.imarketandroid.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.MessageSuggestStore;
import com.example.framgia.imarketandroid.ui.adapter.SuggestStoreAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by phongtran on 26/08/2016.
 */
public class SuggestStoreActivity extends Activity implements View.OnClickListener {
    private ImageView mImageViewAvaStore;
    private TextView mTextViewNameStore, mTextViewHastagStore;
    private Button mButtonFollow, mButtonMessage, mButtonShare;
    private TextView mTextViewProportionVote, mTextViewCountVote;
    private ImageView mImageViewStar1, mImageViewStar2, mImageViewStar3, mImageViewStar4,
        mImageViewStar5;
    private Button mButtonPostSuggestStore;
    private RecyclerView mRecyclerViewOldMessage;
    private List<MessageSuggestStore> mMessageSuggestStoreList = new ArrayList<>();
    private SuggestStoreAdapter mSuggestStoreAdapter;
    private AlertDialog mAlertDialogPostMessage;
    private EditText mEditTextContentMess;
    private Button mButtonBack, mButtonPost;
    private Button mButtonStar1, mButtonStar2, mButtonStar3, mButtonStar4, mButtonStar5;
    private TextView mTextViewStar1, mTextViewStar2, mTextViewStar3, mTextViewStar4, mTextViewStar5;
    private MessageSuggestStore mMessage = new MessageSuggestStore();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_store);
        initView();
        event();
        fakeDataMessage();
        initRecycle();
    }

    private void initRecycle() {
        mSuggestStoreAdapter = new SuggestStoreAdapter(this, mMessageSuggestStoreList);
        mRecyclerViewOldMessage.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerViewOldMessage.setAdapter(mSuggestStoreAdapter);
    }

    private void fakeDataMessage() {
        MessageSuggestStore msm = new MessageSuggestStore(
            R.drawable.avatar,
            getString(R.string.message_rate),
            getString(R.string.name_user),
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

    private void initView() {
        mImageViewAvaStore = (ImageView) findViewById(R.id.image_ava_store);
        mTextViewNameStore = (TextView) findViewById(R.id.text_name_store);
        mTextViewHastagStore = (TextView) findViewById(R.id.text_hastag_store);
        mButtonFollow = (Button) findViewById(R.id.button_follow_suggetstore);
        mButtonMessage = (Button) findViewById(R.id.button_message_suggetstore);
        mButtonShare = (Button) findViewById(R.id.button_share_suggetstore);
        mTextViewProportionVote = (TextView) findViewById(R.id.text_proportion_rate);
        mTextViewCountVote = (TextView) findViewById(R.id.text_count_rate);
        mImageViewStar1 = (ImageView) findViewById(R.id.image_start_1);
        mImageViewStar2 = (ImageView) findViewById(R.id.image_start_2);
        mImageViewStar3 = (ImageView) findViewById(R.id.image_start_3);
        mImageViewStar4 = (ImageView) findViewById(R.id.image_start_4);
        mImageViewStar5 = (ImageView) findViewById(R.id.image_start_5);
        mButtonPostSuggestStore = (Button) findViewById(R.id.button_post_store);
        mRecyclerViewOldMessage = (RecyclerView) findViewById(R.id.recycleview_message_rate);
    }

    private void event() {
        mButtonFollow.setOnClickListener(this);
        mButtonMessage.setOnClickListener(this);
        mButtonShare.setOnClickListener(this);
        mButtonPostSuggestStore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_follow_suggetstore:
                Toast.makeText(SuggestStoreActivity.this, getString(R.string.follow),
                    Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_message_suggetstore:
                Toast.makeText(SuggestStoreActivity.this, getString(R.string.mesage),
                    Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_share_suggetstore:
                Toast.makeText(SuggestStoreActivity.this, getString(R.string.share),
                    Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_post_store:
                initAlertDiaLogPostMessage();
                break;
            default:
                break;
        }
    }

    private void initAlertDiaLogPostMessage() {
        mMessage.setmImageViewStar1(R.drawable.ic_star_empty);
        mMessage.setmImageViewStar2(R.drawable.ic_star_empty);
        mMessage.setmImageViewStar3(R.drawable.ic_star_empty);
        mMessage.setmImageViewStar4(R.drawable.ic_star_empty);
        mMessage.setmImageViewStar5(R.drawable.ic_star_empty);
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_post_message_rate, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        mEditTextContentMess = (EditText) promptsView.findViewById(R.id.edittext_message_rate);
        mButtonBack = (Button) promptsView.findViewById(R.id.button_back_message_rate);
        mButtonPost = (Button) promptsView.findViewById(R.id.button_post_message_rate);
        mButtonStar1 = (Button) promptsView.findViewById(R.id.button_start_vote_1);
        mButtonStar2 = (Button) promptsView.findViewById(R.id.button_start_vote_2);
        mButtonStar3 = (Button) promptsView.findViewById(R.id.button_start_vote_3);
        mButtonStar4 = (Button) promptsView.findViewById(R.id.button_start_vote_4);
        mButtonStar5 = (Button) promptsView.findViewById(R.id.button_start_vote_5);
        mTextViewStar1 = (TextView) promptsView.findViewById(R.id.text_start_1);
        mTextViewStar2 = (TextView) promptsView.findViewById(R.id.text_start_2);
        mTextViewStar3 = (TextView) promptsView.findViewById(R.id.text_start_3);
        mTextViewStar4 = (TextView) promptsView.findViewById(R.id.text_start_4);
        mTextViewStar5 = (TextView) promptsView.findViewById(R.id.text_start_5);
        alertDialogBuilder
            .setCancelable(false);
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialogPostMessage.dismiss();
            }
        });
        mButtonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessage.setmImageViewAva(R.drawable.avatar);
                mMessage.setmNameUser(getString(R.string.name_user));
                mMessage.setmTextViewContent(mEditTextContentMess.getText().toString());
                mMessageSuggestStoreList.add(mMessage);
                Collections.reverse(mMessageSuggestStoreList);
                mSuggestStoreAdapter.notifyDataSetChanged();
                mAlertDialogPostMessage.dismiss();
            }
        });
        mButtonStar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonStar1.setBackgroundResource(R.drawable.ic_star_full);
                mButtonStar2.setBackgroundResource(R.drawable.ic_star_empty);
                mButtonStar3.setBackgroundResource(R.drawable.ic_star_empty);
                mButtonStar4.setBackgroundResource(R.drawable.ic_star_empty);
                mButtonStar5.setBackgroundResource(R.drawable.ic_star_empty);
                mMessage.setmImageViewStar1(R.drawable.ic_star_full);
                mMessage.setmImageViewStar2(R.drawable.ic_star_empty);
                mMessage.setmImageViewStar3(R.drawable.ic_star_empty);
                mMessage.setmImageViewStar4(R.drawable.ic_star_empty);
                mMessage.setmImageViewStar5(R.drawable.ic_star_empty);
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
                mMessage.setmImageViewStar1(R.drawable.ic_star_full);
                mMessage.setmImageViewStar2(R.drawable.ic_star_full);
                mMessage.setmImageViewStar3(R.drawable.ic_star_empty);
                mMessage.setmImageViewStar4(R.drawable.ic_star_empty);
                mMessage.setmImageViewStar5(R.drawable.ic_star_empty);
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
                mMessage.setmImageViewStar1(R.drawable.ic_star_full);
                mMessage.setmImageViewStar2(R.drawable.ic_star_full);
                mMessage.setmImageViewStar3(R.drawable.ic_star_full);
                mMessage.setmImageViewStar4(R.drawable.ic_star_empty);
                mMessage.setmImageViewStar5(R.drawable.ic_star_empty);
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
                mMessage.setmImageViewStar1(R.drawable.ic_star_full);
                mMessage.setmImageViewStar2(R.drawable.ic_star_full);
                mMessage.setmImageViewStar3(R.drawable.ic_star_full);
                mMessage.setmImageViewStar4(R.drawable.ic_star_full);
                mMessage.setmImageViewStar5(R.drawable.ic_star_empty);
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
                mMessage.setmImageViewStar1(R.drawable.ic_star_full);
                mMessage.setmImageViewStar2(R.drawable.ic_star_full);
                mMessage.setmImageViewStar3(R.drawable.ic_star_full);
                mMessage.setmImageViewStar4(R.drawable.ic_star_full);
                mMessage.setmImageViewStar5(R.drawable.ic_star_full);
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
                mMessage.setmImageViewStar1(R.drawable.ic_star_full);
                mMessage.setmImageViewStar2(R.drawable.ic_star_empty);
                mMessage.setmImageViewStar3(R.drawable.ic_star_empty);
                mMessage.setmImageViewStar4(R.drawable.ic_star_empty);
                mMessage.setmImageViewStar5(R.drawable.ic_star_empty);
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
                mMessage.setmImageViewStar1(R.drawable.ic_star_full);
                mMessage.setmImageViewStar2(R.drawable.ic_star_full);
                mMessage.setmImageViewStar3(R.drawable.ic_star_empty);
                mMessage.setmImageViewStar4(R.drawable.ic_star_empty);
                mMessage.setmImageViewStar5(R.drawable.ic_star_empty);
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
                mMessage.setmImageViewStar1(R.drawable.ic_star_full);
                mMessage.setmImageViewStar2(R.drawable.ic_star_full);
                mMessage.setmImageViewStar3(R.drawable.ic_star_full);
                mMessage.setmImageViewStar4(R.drawable.ic_star_empty);
                mMessage.setmImageViewStar5(R.drawable.ic_star_empty);
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
                mMessage.setmImageViewStar1(R.drawable.ic_star_full);
                mMessage.setmImageViewStar2(R.drawable.ic_star_full);
                mMessage.setmImageViewStar3(R.drawable.ic_star_full);
                mMessage.setmImageViewStar4(R.drawable.ic_star_full);
                mMessage.setmImageViewStar5(R.drawable.ic_star_empty);
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
                mMessage.setmImageViewStar1(R.drawable.ic_star_full);
                mMessage.setmImageViewStar2(R.drawable.ic_star_full);
                mMessage.setmImageViewStar3(R.drawable.ic_star_full);
                mMessage.setmImageViewStar4(R.drawable.ic_star_full);
                mMessage.setmImageViewStar5(R.drawable.ic_star_full);
            }
        });
        mAlertDialogPostMessage = alertDialogBuilder.create();
        mAlertDialogPostMessage.show();
    }
}
