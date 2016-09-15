package com.example.framgia.imarketandroid.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.FakeContainer;
import com.example.framgia.imarketandroid.data.model.ItemBooking;
import com.example.framgia.imarketandroid.data.model.MessageSuggestStore;
import com.example.framgia.imarketandroid.ui.adapter.BookProductAdapter;
import com.example.framgia.imarketandroid.ui.adapter.PreviewProductAdapter;
import com.example.framgia.imarketandroid.ui.adapter.SuggestStoreAdapter;
import com.example.framgia.imarketandroid.util.Constants;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

/**
 * Created by hoavt on 22/07/2016.
 */
public class DetailsProductActivity extends AppCompatActivity
        implements BookProductAdapter.OnClickItemBarListenner {
    private RecyclerView mRvPreviewProducts;
    private RecyclerView mRvBookingProducts;
    private RecyclerView.Adapter mPreviewAdapter;
    private RecyclerView.Adapter mBookingAdapter;
    private RecyclerView.LayoutManager mPreviewLayoutManager;
    private RecyclerView.LayoutManager mBookingLayoutManager;
    private TextView mTvNameProduct;
    private TextView mTvPriceProduct;
    private TextView mTvInfoProduct;
    private List<MessageSuggestStore> mListRate = new ArrayList<>();
    private SuggestStoreAdapter mSuggestStoreAdapter;
    private RecyclerView mRecyclerRateMessage;
    private MessageSuggestStore mMessage = new MessageSuggestStore();
    private EditText mEditTextContentMess;
    private Button mButtonBack, mButtonPost;
    private Button mButtonStar1, mButtonStar2, mButtonStar3, mButtonStar4, mButtonStar5;
    private TextView mTextViewStar1, mTextViewStar2, mTextViewStar3, mTextViewStar4, mTextViewStar5;
    private AlertDialog mAlertDialogPostMessage;
    private Button mButtonPostProductMess;
    private CallbackManager mCallbackManager;
    private ShareDialog mShareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        FacebookSdk.setIsDebugEnabled(true);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        AppEventsLogger.activateApp(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        mShareDialog = new ShareDialog(this);
        setContentView(R.layout.activity_product_details_layout);
        initViews();
        fakeMessage();
        initRecycle();
        initGuide();
    }

    private void initViews() {
        mRvPreviewProducts = (RecyclerView) findViewById(R.id.rv_product_preview);
        mRvBookingProducts = (RecyclerView) findViewById(R.id.rv_product_book_options);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRvPreviewProducts.setHasFixedSize(true);
        mRvBookingProducts.setHasFixedSize(true);
        // use a linear layout manager
        mPreviewLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvPreviewProducts.setLayoutManager(mPreviewLayoutManager);
        mBookingLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvBookingProducts.setLayoutManager(mBookingLayoutManager);
        mPreviewAdapter = new PreviewProductAdapter(this, FakeContainer.initIdResList(),
                (ScrollView) findViewById(R.id.sv_info));
        mBookingAdapter = new BookProductAdapter(this, initBookingProducts());
        mRvPreviewProducts.setAdapter(mPreviewAdapter);
        mRvBookingProducts.setAdapter(mBookingAdapter);
        // init textviews
        mTvNameProduct = (TextView) findViewById(R.id.tv_product_name);
        mTvPriceProduct = (TextView) findViewById(R.id.tv_product_price);
        mTvInfoProduct = (TextView) findViewById(R.id.tv_product_info);
        setTexts();
        mButtonPostProductMess = (Button) findViewById(R.id.button_post_product);
        mButtonPostProductMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initAlertDiaLogPostMessage();
            }
        });
    }

    private ArrayList<ItemBooking> initBookingProducts() {
        ArrayList<ItemBooking> list = new ArrayList<>();
        list.add(new ItemBooking(R.drawable.ic_call_white_24px, getString(R.string.call)));
        list.add(new ItemBooking(R.drawable.ic_schedule_white_24px, getString(R.string.schedule)));
        list.add(new ItemBooking(R.drawable.ic_shopping_cart_white_24px, getString(R.string.book)));
        list.add(new ItemBooking(R.drawable.ic_favorite_white_24px, getString(R.string.favorite)));
        list.add(new ItemBooking(R.drawable.ic_white_star_full, getString(R.string.vote_product)));
        return list;
    }

    private void setTexts() {
        mTvNameProduct.setText(FakeContainer.getNameProduct());
        mTvPriceProduct.setText(FakeContainer.getPriceProduct());
        mTvInfoProduct.setText(FakeContainer.getInfoProduct());
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
        TextView textNameProduct = (TextView) promptsView.findViewById(R.id.text_question);
        textNameProduct.setText(mTvNameProduct.getText().toString());
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
                mListRate.add(mMessage);
                Collections.reverse(mListRate);
                mSuggestStoreAdapter.notifyDataSetChanged();
                shareFacebook((mEditTextContentMess.getText().toString()));
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

    private void fakeMessage() {
        MessageSuggestStore msm = new MessageSuggestStore(
                R.drawable.avatar,
                getString(R.string.rate_product_message),
                getString(R.string.name_user),
                R.drawable.ic_star_full,
                R.drawable.ic_star_full,
                R.drawable.ic_star_full,
                R.drawable.ic_star_half,
                R.drawable.ic_star_empty);
        mListRate.add(msm);
        mListRate.add(msm);
        mListRate.add(msm);
        mListRate.add(msm);
    }

    private void initRecycle() {
        mSuggestStoreAdapter = new SuggestStoreAdapter(this, mListRate);
        mRecyclerRateMessage = (RecyclerView) findViewById(R.id.recycleview_message_rate_product);
        mRecyclerRateMessage.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerRateMessage.setAdapter(mSuggestStoreAdapter);
    }

    @Override
    public void onClickItemBar(String textNameItem, int position) {
        switch (position) {
            case Constants.CALLING_POSITION:
                break;
            case Constants.LIKE_POSITION:
                break;
            case Constants.ORDER_POSITION:
                break;
            case Constants.RATE_POSITION:
                initAlertDiaLogPostMessage();
                break;
            case Constants.SCHEDULING_POSITION:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void shareFacebook(String content) {
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(FakeContainer.URL_TEST))
                    .setContentTitle(content)
                    .build();
            mShareDialog.show(linkContent);
        }
    }

    private void initGuide() {
        new MaterialShowcaseView.Builder(this)
                .setTarget(mButtonPostProductMess)
                .setDismissText(Constants.GOT_IT)
                .setContentText(getString(R.string.sequence_write_vote))
                .setDelay(Constants.TIME_DELAY_GUIDE)
                .singleUse(Constants.SHOWCASE_ID_DETAILS_PRODUCT)
                .show();
    }
}
