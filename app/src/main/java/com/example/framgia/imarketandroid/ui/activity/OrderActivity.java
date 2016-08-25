package com.example.framgia.imarketandroid.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.FakeContainer;
import com.example.framgia.imarketandroid.ui.adapter.PreviewProductAdapter;
import com.example.framgia.imarketandroid.ui.adapter.SpinnerArrayAdapter;
import com.example.framgia.imarketandroid.ui.views.CustomSpinner;
import com.example.framgia.imarketandroid.util.SystemUtil;

import java.util.ArrayList;

/**
 * Created by hoavt on 18/08/2016.
 */
public class OrderActivity extends AppCompatActivity {
    private RecyclerView mRvProductOrder;
    private RecyclerView.Adapter mProductOrderAdapter;
    private RecyclerView.LayoutManager mProductOrderLayoutManager;
    private Toolbar mToolbar;
    private CustomSpinner mSpinner;
    private TextView mTvName, mTvPrice, mTvRedundance, mTvTotPrice;
    private ImageView mIvAscendQuan, mIvDescendQuan;
    private TextView mTvQuantity;
    private AppCompatButton mBtBuy;
    private int mQuantity = 0;
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.iv_ascend_quantity_order:
                    mQuantity++;
                    mTvQuantity.setText(""+mQuantity);
                    break;
                case R.id.iv_descend_quantity_order:
                    mQuantity--;
                    if (mQuantity >= 0)
                        mTvQuantity.setText(""+mQuantity);
                    else
                        mQuantity++;
                    break;
                case R.id.bt_buy_product_order:
                    Toast.makeText(OrderActivity.this, getResources().getString(R.string.muangay), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_layout);
        initToolbar();
        initRecyclerViews();
        initSpinner();
        initViews();
        handleEvents();
    }

    private void handleEvents() {
        long giaSp = FakeContainer.GIA_SP;
        long chiphiPhatsinh = FakeContainer.CHI_PHI_PHAT_SINH;
        mTvName.setText(getResources().getString(R.string.sanpham) + FakeContainer.getNameProduct());
        mTvPrice.setText(getResources().getString(R.string.giasanpham) + SystemUtil.formatMoneyStr(giaSp));
        mTvRedundance.setText(getResources().getString(R.string.chiphiphatsinh) + SystemUtil.formatMoneyStr(chiphiPhatsinh));
        mTvTotPrice.setText(getResources().getString(R.string.thanhtien) + SystemUtil.formatMoneyStr(giaSp+chiphiPhatsinh));
        mTvQuantity.setText(""+mQuantity);
        mIvAscendQuan.setOnClickListener(mClickListener);
        mIvDescendQuan.setOnClickListener(mClickListener);
        mBtBuy.setOnClickListener(mClickListener);
    }

    private void initViews() {
        mTvName = (TextView) findViewById(R.id.tv_name_product_order);
        mTvPrice = (TextView) findViewById(R.id.tv_price_product_order);
        mTvRedundance = (TextView) findViewById(R.id.tv_redundance_product_order);
        mTvTotPrice = (TextView) findViewById(R.id.tv_total_price_product_order);
        mTvQuantity = (TextView) findViewById(R.id.tv_quantity_product_order);
        mIvAscendQuan = (ImageView) findViewById(R.id.iv_ascend_quantity_order);
        mIvDescendQuan = (ImageView) findViewById(R.id.iv_descend_quantity_order);
        mBtBuy = (AppCompatButton) findViewById(R.id.bt_buy_product_order);
    }

    private void initSpinner() {
        String[] data = {getResources().getString(R.string.phuongthucnhanhang),
                getResources().getString(R.string.tructiep),
                getResources().getString(R.string.chuyenhang)};

        ArrayAdapter adapter = new SpinnerArrayAdapter(this, R.layout.spinner_item_selected, data, 0);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        mSpinner = (CustomSpinner) findViewById(R.id.spinner_receive_product);
        mSpinner.setAdapter(adapter);
        mSpinner.setSpinnerEventsListener(new CustomSpinner.OnSpinnerEventsListener() {
            public void onSpinnerOpened() {
                mSpinner.setSelected(true);
            }

            public void onSpinnerClosed() {
                mSpinner.setSelected(false);
            }
        });
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initRecyclerViews() {
        mRvProductOrder = (RecyclerView) findViewById(R.id.rv_preview_product_order);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRvProductOrder.setHasFixedSize(true);

        // use a linear layout manager
        mProductOrderLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvProductOrder.setLayoutManager(mProductOrderLayoutManager);
        mProductOrderAdapter = new PreviewProductAdapter(this, FakeContainer.initIdResList(),
                (ScrollView) findViewById(R.id.sv_order));
        mRvProductOrder.setAdapter(mProductOrderAdapter);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // mSpin is our custom Spinner
        if (mSpinner.hasBeenOpened() && hasFocus) {
            mSpinner.performClosedEvent();
        }
    }
}
