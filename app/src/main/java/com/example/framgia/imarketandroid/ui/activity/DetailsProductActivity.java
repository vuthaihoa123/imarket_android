package com.example.framgia.imarketandroid.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.FakeContainer;
import com.example.framgia.imarketandroid.data.model.ItemBooking;
import com.example.framgia.imarketandroid.ui.adapter.BookProductAdapter;
import com.example.framgia.imarketandroid.ui.adapter.PreviewProductAdapter;

import java.util.ArrayList;

/**
 * Created by hoavt on 22/07/2016.
 */
public class DetailsProductActivity extends AppCompatActivity {

    private RecyclerView mRvPreviewProducts;
    private RecyclerView mRvBookingProducts;
    private RecyclerView.Adapter mPreviewAdapter;
    private RecyclerView.Adapter mBookingAdapter;
    private RecyclerView.LayoutManager mPreviewLayoutManager;
    private RecyclerView.LayoutManager mBookingLayoutManager;
    private TextView mTvNameProduct;
    private TextView mTvPriceProduct;
    private TextView mTvInfoProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_layout);
        initViews();
    }

    private void initViews() {
        mRvPreviewProducts = (RecyclerView) findViewById(R.id.rv_product_preview);
        mRvBookingProducts = (RecyclerView) findViewById(R.id.rv_product_book_options);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRvPreviewProducts.setHasFixedSize(true);
        mRvBookingProducts.setHasFixedSize(true);

        // use a linear layout manager
        mPreviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvPreviewProducts.setLayoutManager(mPreviewLayoutManager);
        mBookingLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvBookingProducts.setLayoutManager(mBookingLayoutManager);
        mPreviewAdapter = new PreviewProductAdapter(this, FakeContainer.initIdResList(), (ScrollView) findViewById(R.id.sv_info));
        mBookingAdapter = new BookProductAdapter(this, initBookingProducts());
        mRvPreviewProducts.setAdapter(mPreviewAdapter);
        mRvBookingProducts.setAdapter(mBookingAdapter);

        // init textviews
        mTvNameProduct = (TextView) findViewById(R.id.tv_product_name);
        mTvPriceProduct = (TextView) findViewById(R.id.tv_product_price);
        mTvInfoProduct = (TextView) findViewById(R.id.tv_product_info);
        setTexts();
    }

    private ArrayList<ItemBooking> initBookingProducts() {
        ArrayList<ItemBooking> list = new ArrayList<>();
        list.add(new ItemBooking(R.drawable.ic_call_white_24px, getResources().getString(R.string.call)));
        list.add(new ItemBooking(R.drawable.rating_star, getResources().getString(R.string.rate)));
        list.add(new ItemBooking(R.drawable.ic_shopping_cart_white_24px, getResources().getString(R.string.book)));
        list.add(new ItemBooking(R.drawable.ic_favorite_white_24px, getResources().getString(R.string.favorite)));
        return list;
    }

    private void setTexts() {
        mTvNameProduct.setText(FakeContainer.getNameProduct());
        mTvPriceProduct.setText(FakeContainer.getPriceProduct());
        mTvInfoProduct.setText(FakeContainer.getInfoProduct());
    }
}
