package com.example.framgia.imarketandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.Category;
import com.example.framgia.imarketandroid.ui.adapter.ViewPagerAdapter;
import com.example.framgia.imarketandroid.ui.fragments.CategoryStallFragment;
import com.example.framgia.imarketandroid.ui.fragments.SaleOffEventFragment;
import com.example.framgia.imarketandroid.ui.fragments.ShopDetailInterfaceFragment;
import com.example.framgia.imarketandroid.ui.fragments.SuggestStoreFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toannguyen201194 on 06/09/2016.
 */
public class HomeStoreActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final String DANHMUC = "Danh Mục";
    private static final String THONGTINKHUYENMAI = "Chương Trình Khuyến Mãi";
    private static final String DANHGIA = "Đánh Giá & Góp Ý";
    private static final String THONGTINCUAHANG = "Thông Tin Của Hàng";
    private static final String NAMESTORE ="Apple Store";
    private TabLayout mTabLayout;
    private ViewPager mViewPagerStore;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_store);
        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tab_home_store);
        mViewPagerStore = (ViewPager) findViewById(R.id.view_home_store);
        mTabLayout.setupWithViewPager(mViewPagerStore);
        setupViewPager(mViewPagerStore);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(NAMESTORE);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CategoryStallFragment(), DANHMUC);
        adapter.addFragment(new SaleOffEventFragment(), THONGTINKHUYENMAI);
        adapter.addFragment(new SuggestStoreFragment(), DANHGIA);
        adapter.addFragment(new ShopDetailInterfaceFragment(), THONGTINCUAHANG);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.item_cart:
                startActivity(new Intent(HomeStoreActivity.this,CartActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        List<Category> list = filter(CategoryStallFragment.mCategoryProducts, query);
        CategoryStallFragment.mCategoryStallAdapter.animateTo(list);
        return true;
    }

    private List<Category> filter(List<Category> categoryProducts, String query) {
        query = query.toLowerCase();
        List<Category> filterList = new ArrayList<>();
        int size = categoryProducts.size();
        for (int i = 0; i < size; i++) {
            Category categoryProduct = categoryProducts.get(i);
            String categoryName = categoryProduct.getName().toLowerCase();
            if (categoryName.contains(query)) {
                filterList.add(categoryProduct);
            }
        }
        return filterList;
    }
}
