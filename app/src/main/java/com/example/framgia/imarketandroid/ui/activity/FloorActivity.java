package com.example.framgia.imarketandroid.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.models.Floor;
import com.example.framgia.imarketandroid.models.Shop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toannguyen201194 on 19/07/2016.
 */
public class FloorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ArrayAdapter<Shop> mAdapterShop;
    ArrayAdapter<Floor> mAdapterFloor;
    private Spinner mSpinerFloor, mSpinnerProduct;
    private List<Shop> mShopList = new ArrayList<>();
    private List<Floor> mFloorList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_floor);
        hideStatusBar();
        initViews();
        createDataForFloor();
    }

    private void initViews() {
        mSpinerFloor = (Spinner) findViewById(R.id.spinner_choosefloor);
        mSpinnerProduct = (Spinner) findViewById(R.id.spinner_chooseproduct);
        mAdapterFloor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mFloorList);
        mAdapterFloor.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mSpinerFloor.setAdapter(mAdapterFloor);
        mAdapterShop = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mShopList);
        mAdapterShop.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mSpinnerProduct.setAdapter(mAdapterShop);
        mSpinerFloor.setOnItemSelectedListener(this);
    }

    private void createDataForFloor() {
        Floor floor = new Floor(0, "Choose floor");
        Floor floor1 = new Floor(1, "Tầng 1");
        Floor floor2 = new Floor(2, "Tầng 2");
        mFloorList.add(floor);
        mFloorList.add(floor1);
        mFloorList.add(floor2);
        mAdapterFloor.notifyDataSetChanged();
    }

    private void createDataForShop() {
        if (mSpinerFloor.getSelectedItemPosition() > 0) {
            Shop shop1 = new Shop(1, "Quần Áo");
            Shop shop2 = new Shop(2, "Váy");
            mShopList.add(shop1);
            mShopList.add(shop2);
            Floor c = mFloorList.get(mSpinerFloor.getSelectedItemPosition());
            c.setShopList(mShopList);
            loadListShop(c);
        }
    }

    private void hideStatusBar() {
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }
    }

    private void loadListShop(Floor floor) {
        mShopList.clear();
        mShopList.addAll(floor.getShopList());
        mAdapterShop.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i > 0) {
            createDataForShop();
        }
        loadListShop(mFloorList.get(i));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
