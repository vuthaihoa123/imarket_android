package com.example.framgia.imarketandroid.ui.activity;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.models.CategoryProduct;
import com.example.framgia.imarketandroid.ui.adapter.CategoryStallAdapter;
import com.example.framgia.imarketandroid.ui.widget.GridItemDecoration;
import com.example.framgia.imarketandroid.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class CategoryStallActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private final int SCROLL_POSITION = 0;
    private RecyclerView mRecyclerView;
    private CategoryStallAdapter mCategoryStallAdapter;
    private List<CategoryProduct> mCategoryProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_stall);
        findView();
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    public void findView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_category);
    }

    public void init() {
        mCategoryProducts = new ArrayList<>();
        mCategoryProducts.add(new CategoryProduct(1, "Pants", "123"));
        mCategoryProducts.add(new CategoryProduct(2, "Watch", "123"));
        mCategoryProducts.add(new CategoryProduct(3, "Hat", "123"));
        mCategoryProducts.add(new CategoryProduct(4, "Shirt", "123"));
        mCategoryStallAdapter = new CategoryStallAdapter(mCategoryProducts);
        mRecyclerView.setAdapter(mCategoryStallAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, Constants.NUMBER_OF_COLUMN));
        mRecyclerView.addItemDecoration(new GridItemDecoration(this));
    }

    private List<CategoryProduct> filter(List<CategoryProduct> categoryProducts, String query) {
        query = query.toLowerCase();
        List<CategoryProduct> filterList = new ArrayList<>();
        int size = categoryProducts.size();
        for (int i = 0; i < size; i++) {
            CategoryProduct categoryProduct = categoryProducts.get(i);
            String categoryName = categoryProduct.getCategoryName().toLowerCase();
            if (categoryName.contains(query)) {
                filterList.add(categoryProduct);
            }
        }
        return filterList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<CategoryProduct> list = filter(mCategoryProducts, query);
        mCategoryStallAdapter.animateTo(list);
        mRecyclerView.scrollToPosition(SCROLL_POSITION);
        return true;
    }
}
