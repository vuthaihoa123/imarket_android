package com.example.framgia.imarketandroid.ui.activity;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.FakeContainer;
import com.example.framgia.imarketandroid.data.model.CartItem;
import com.example.framgia.imarketandroid.ui.adapter.HistoryTimeAdapter;
import com.example.framgia.imarketandroid.ui.widget.LinearItemDecoration;
import com.example.framgia.imarketandroid.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class HistoryOrderActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView mRecyclerView;
    private List<CartItem> mCartItems;
    private List<String> mHeaderNames;
    private HistoryTimeAdapter mHistoryTimeAdapter;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);
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

    private void init() {
        mHeaderNames = new ArrayList<>();
        mCartItems = new ArrayList<>();
        mHeaderNames = FakeContainer.getListHeader();
        mCartItems = FakeContainer.getListCartItem();
        mHistoryTimeAdapter = new HistoryTimeAdapter(mHeaderNames, mCartItems, this);
        mRecyclerView.setAdapter(mHistoryTimeAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.addItemDecoration(new LinearItemDecoration(this));
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mHistoryTimeAdapter.notifyDataSetChanged();
    }

    private void findView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_history_order);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<String> list = getItems(mHeaderNames, newText);
        mHistoryTimeAdapter.animateTo(list);
        mRecyclerView.scrollToPosition(Constants.SCROLL_POSITION);
        return true;
    }

    private List<String> getItems(List<String> headers, String query) {
        List<String> filterList = new ArrayList<>();
        query = query.toLowerCase();
        for (String header : headers) {
            String lowerName = header.toLowerCase();
            if (lowerName.contains(query)) {
                filterList.add(header);
            }
        }
        return filterList;
    }
}
