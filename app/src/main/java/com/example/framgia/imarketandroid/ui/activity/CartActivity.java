package com.example.framgia.imarketandroid.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.models.CartItem;
import com.example.framgia.imarketandroid.models.ItemBooking;
import com.example.framgia.imarketandroid.ui.adapter.CartProductAdapter;
import com.example.framgia.imarketandroid.util.Flog;
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

    private void initViews() {
        mRvProductCart = (RecyclerView) findViewById(R.id.rv_cart);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRvProductCart.setHasFixedSize(true);

        // use a linear layout manager
        mProductCartLayoutManager = new LinearLayoutManager(this);
        mRvProductCart.setLayoutManager(mProductCartLayoutManager);
        mProductCartAdapter = new CartProductAdapter(this, initCartProductList());
        mRvProductCart.setAdapter(mProductCartAdapter);

        mTotalPrice = (TextView) findViewById(R.id.tv_total_price);
        mTotalPrice.setText(getResources().getString(R.string.total) + " " + SystemUtil.formatMoneyStr(calTotalPrice())
                + getResources().getString(R.string.unitVietMoney));
        mBtAccept = (AppCompatButton) findViewById(R.id.bt_accept);
        mBtAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CartActivity.this,
                        "Click accept button", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private long calTotalPrice() {
        long totPrice = 0;
        ArrayList<CartItem> list = ((CartProductAdapter)mProductCartAdapter).getItems();
        Flog.i("size: "+list.size());
        for (int i = 0; i < list.size(); i++) {
            totPrice += list.get(i).getQuantity() * list.get(i).getPriceProduct();
        }
        return totPrice;
    }

    private ArrayList<CartItem> initCartProductList() {
        ArrayList<CartItem> list = new ArrayList<>();
        list.add(new CartItem(R.drawable.ic_iphone6s, "Iphone 6S", 16000000, 1, false));
        list.add(new CartItem(R.drawable.ic_iphone5s, "Iphone 5S", 14000000, 1, false));
        list.add(new CartItem(R.drawable.ic_htc_one, "HTC One", 12000000, 1, false));
        list.add(new CartItem(R.drawable.ic_sky_a850, "Sky A850", 4000000, 1, false));
        list.add(new CartItem(R.drawable.ic_lg_optimus, "LG Optimus", 9000000, 1, false));
        list.add(new CartItem(R.drawable.ic_window_phone, "Window Phone", 6000000, 1, false));
        list.add(new CartItem(R.drawable.ic_blackberry, "Blackberry", 12000000, 1, false));
        list.add(new CartItem(R.drawable.ic_nokia_n8, "Nokia", 5000000, 1, false));
        return list;
    }
}
