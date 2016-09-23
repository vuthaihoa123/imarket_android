package com.example.framgia.imarketandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.util.Constants;

/**
 * Created by vutha on 9/27/2016.
 */
public class ReadmoreActivity extends AppCompatActivity {

    private ImageView mIvExit;
    private TextView mTvName, mTvPrice, mTvInfor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readmore_layout);
        initViews();
        setTexts();
    }

    private void setTexts() {
        Intent returnedIntent = getIntent();
        if (returnedIntent != null) {
            String nameProduct = returnedIntent.getStringExtra(Constants.EXTRA_NAME_PRODUCT);
            mTvName.setText(nameProduct);
            String priceProduct = returnedIntent.getStringExtra(Constants.EXTRA_PRICE_PRODUCT);
            mTvPrice.setText(priceProduct);
            String inforProduct = returnedIntent.getStringExtra(Constants.EXTRA_INFOR_PRODUCT);
            mTvInfor.setText(inforProduct);
        }
    }

    private void initViews() {
        mIvExit = (ImageView) findViewById(R.id.iv_exit);
        mIvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTvName = (TextView) findViewById(R.id.tv_product_name);
        mTvPrice = (TextView) findViewById(R.id.tv_product_price);
        mTvInfor = (TextView) findViewById(R.id.tv_product_info);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
