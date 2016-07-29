package com.example.framgia.imarketandroid.ui.activity;

import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.models.Market;
import com.example.framgia.imarketandroid.ui.adapter.RecyclerMarketAdapter;
import com.example.framgia.imarketandroid.ui.widget.LinearItemDecoration;
import com.example.framgia.imarketandroid.util.OnRecyclerItemInteractListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yue on 20/07/2016.
 */
public class ChooseMarketActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
        SearchView.OnQueryTextListener,OnRecyclerItemInteractListener {

    private static final String MARKET_SUGGESTION = "marketName";
    private static final String[] SUGGESTIONS = new String[]{
            "Belgium", "France", "Italy", "Germany", "Spain", "Viet Nam"
    };
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerMarket;
    private RecyclerMarketAdapter mAdapter;
    private List<Market> mMarkets = new ArrayList<>();
    private CursorAdapter mSearchSuggestionAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_market);
        findViews();
        setListeners();
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mRecyclerMarket.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerMarket.addItemDecoration(new LinearItemDecoration(this));
        mMarkets.add(new Market("M0001", "Big C", "Trần Duy Hưng, Trung Hoà, Cầu Giấy, Hà Nội"));
        mMarkets.add(new Market("M0001", "Big C", "Trần Duy Hưng, Trung Hoà, Cầu Giấy, Hà Nội"));
        mMarkets.add(new Market("M0001", "Big C", "Trần Duy Hưng, Trung Hoà, Cầu Giấy, Hà Nội"));
        mMarkets.add(new Market("M0001", "Big C", "Trần Duy Hưng, Trung Hoà, Cầu Giấy, Hà Nội"));
        mAdapter = new RecyclerMarketAdapter(mMarkets);
        mRecyclerMarket.setAdapter(mAdapter);
        final String[] columns = new String[]{MARKET_SUGGESTION};
        final int[] displayViews = new int[]{android.R.id.text1};
        mSearchSuggestionAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null,
                columns,
                displayViews,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mAdapter.setOnRecyclerItemInteractListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_choose_market, menu);
        SearchView searchView = (SearchView)
                MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setSuggestionsAdapter(mSearchSuggestionAdapter);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        populateSuggestionAdapter(newText);
        return false;
    }

    @Override
    public void onItemClick(int position) {
        startActivity(new Intent(this,CategoryStallActivity.class));
    }

    private void findViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerMarket = (RecyclerView) findViewById(R.id.recycler_market);
    }

    private void setListeners() {
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
    }

    private void populateSuggestionAdapter(String query) {
        final MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID, MARKET_SUGGESTION});
        int length = SUGGESTIONS.length;
        for (int i = 0; i < length; i++) {
            if (SUGGESTIONS[i].toLowerCase().startsWith(query.toLowerCase()))
                c.addRow(new Object[]{i, SUGGESTIONS[i]});
        }
        mSearchSuggestionAdapter.changeCursor(c);
    }
}
