package com.example.framgia.imarketandroid.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.ItemProduct;
import com.example.framgia.imarketandroid.ui.adapter.ListProductsAdapter;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.findpath.LoadDataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hoavt on 19/07/2016.
 */
public class ListProductsActivity extends AppCompatActivity implements SearchView
        .OnQueryTextListener, LoadDataUtils.OnListProductListener {
    private RecyclerView mRvListProducts;
    private static String NAMECATEGORY = "Apple";
    public ListProductsAdapter sAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public List<ItemProduct> mItemProducts = new ArrayList<>();
    private Toolbar mToolbar;
    private LoadDataUtils mDataUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_layout);
        initViews();
        handleIntent();
    }

    private void handleIntent() {
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            int id = bundle.getInt(Constants.CATEGORY_INTENT);
            loadDatafromServer(id);
        }
    }

    private void loadDatafromServer(int id) {
        LoadDataUtils loadDataUtils = new LoadDataUtils();
        loadDataUtils.init(this);
        loadDataUtils.setOnListProductListener(this);
        loadDataUtils.getProductInCategory(this, id);
    }

    private void initViews() {
        mRvListProducts = (RecyclerView) findViewById(R.id.rv_list_products);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRvListProducts.setHasFixedSize(true);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        // use a gridview layout manager
        mLayoutManager = new GridLayoutManager(this, Constants.COLS_LIST_PRODUCT);
        mRvListProducts.setLayoutManager(mLayoutManager);
        sAdapter = new ListProductsAdapter(this, mItemProducts);
        mRvListProducts.setAdapter(sAdapter);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(NAMECATEGORY);
        mDataUtils = new LoadDataUtils();
//        mDataUtils.init(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_searchview, menu);
        // Associate searchable configuration with the SearchView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager searchManager =
                    (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView =
                    (SearchView) menu.findItem(R.id.search).getActionView();
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getComponentName()));
            searchView.setOnQueryTextListener(this);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<ItemProduct> list = filter(mItemProducts, newText);
        sAdapter.animateTo(list);
        return true;
    }

    private List<ItemProduct> filter(List<ItemProduct> itemProducts, String query) {
        query = query.toLowerCase();
        List<ItemProduct> filterList = new ArrayList<>();
        int size = itemProducts.size();
        for (int i = 0; i < size; i++) {
            ItemProduct categoryProduct = itemProducts.get(i);
            String categoryName = categoryProduct.getNameProduct().toLowerCase();
            if (categoryName.contains(query)) {
                filterList.add(categoryProduct);
            }
        }
        return filterList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDataUtils.mReceiver != null) {
            try {
                unregisterReceiver(mDataUtils.mReceiver);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onListReceiver(List<ItemProduct> list) {
        sAdapter.clearList();
        sAdapter.addAll(list);
        sAdapter.notifyDataSetChanged();
    }
}
