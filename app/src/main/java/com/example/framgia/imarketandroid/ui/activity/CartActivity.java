package com.example.framgia.imarketandroid.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.FakeContainer;
import com.example.framgia.imarketandroid.data.model.CartItem;
import com.example.framgia.imarketandroid.ui.adapter.CartProductAdapter;
import com.example.framgia.imarketandroid.util.SystemUtil;

import java.util.ArrayList;

/**
 * Created by hoavt on 18/08/2016.
 */
public class CartActivity extends AppCompatActivity {
    private RecyclerView mRvProductCart;
    private RecyclerView.Adapter mProductCartAdapter;
    private RecyclerView.LayoutManager mProductCartLayoutManager;
    private Toolbar mToolbar;
    private TextView mTotalPrice;
    private AppCompatButton mBtAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_layout);
        initToolbar();
        initViews();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        mRvProductCart = (RecyclerView) findViewById(R.id.rv_cart);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRvProductCart.setHasFixedSize(true);
        // use a linear layout manager
        mProductCartLayoutManager = new LinearLayoutManager(this);
        mRvProductCart.setLayoutManager(mProductCartLayoutManager);
        mProductCartAdapter = new CartProductAdapter(this, FakeContainer.initCartProductList());
        mRvProductCart.setAdapter(mProductCartAdapter);
        mTotalPrice = (TextView) findViewById(R.id.tv_total_price);
        mTotalPrice.setText(
            getResources().getString(R.string.total) + SystemUtil.formatMoneyStr(calTotalPrice()));
        mBtAccept = (AppCompatButton) findViewById(R.id.bt_accept);
        mBtAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:
            }
        });
    }

    private long calTotalPrice() {
        long totPrice = 0;
        ArrayList<CartItem> list = ((CartProductAdapter) mProductCartAdapter).getItems();
        for (int i = 0; i < list.size(); i++) {
            totPrice += list.get(i).getQuantity() * list.get(i).getPriceProduct();
        }
        return totPrice;
    }
}
