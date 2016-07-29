package com.example.framgia.imarketandroid.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.models.ItemBooking;
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
        mPreviewAdapter = new PreviewProductAdapter(this, initIdResList(), (ScrollView) findViewById(R.id.sv_info));
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
        list.add(new ItemBooking(R.drawable.ic_schedule_white_24px, getResources().getString(R.string.schedule)));
        list.add(new ItemBooking(R.drawable.ic_shopping_cart_white_24px, getResources().getString(R.string.book)));
        list.add(new ItemBooking(R.drawable.ic_favorite_white_24px, getResources().getString(R.string.favorite)));
        return list;
    }

    private void setTexts() {
        // Fake datas:
        mTvNameProduct.setText("Điện thoại HTC One M8 Eye");
        mTvPriceProduct.setText("5.990.000đ");
        mTvInfoProduct.setText(
                "    Màn hình:Super LCD 3, 5\", Full HD\n" +
                        "    Hệ điều hành:Android 5.0 (Lollipop)\n" +
                        "    Camera sau:13 MP\n" +
                        "    Camera trước:5 MP\n" +
                        "    CPU:Qualcomm Snapdragon 801 4 nhân 32-bit, 2.3 GHz\n" +
                        "    RAM:2 GB\n" +
                        "    Bộ nhớ trong:16 GB\n" +
                        "    Hỗ trợ thẻ nhớ:MicroSD, 128 GB\n" +
                        "    Thẻ SIM:1 Sim, Nano SIM\n" +
                        "    Kết nối:WiFi, 3G, 4G LTE Cat 4\n" +
                        "    Dung lượng pin:2600 mAh\n" +
                        "    Thiết kế:Nguyên khối\n" +
                        "    Chức năng đặc biệt:HTC BoomSound");
    }

    private ArrayList<Integer> initIdResList() {
        ArrayList<Integer> list = new ArrayList<>();
        int numFakePreviews = 7;
        for (int i = 0; i < numFakePreviews; i++) {
            list.add(R.drawable.ic_htc_preview_01 + i);
        }
        return list;
    }
}
