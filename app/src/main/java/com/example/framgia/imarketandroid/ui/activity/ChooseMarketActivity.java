package com.example.framgia.imarketandroid.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.FakeContainer;
import com.example.framgia.imarketandroid.data.listener.OnRecyclerItemInteractListener;
import com.example.framgia.imarketandroid.data.model.CartItem;
import com.example.framgia.imarketandroid.data.model.CommerceCanter;
import com.example.framgia.imarketandroid.data.model.DrawerItem;
import com.example.framgia.imarketandroid.data.model.Session;
import com.example.framgia.imarketandroid.data.model.UserModel;
import com.example.framgia.imarketandroid.ui.adapter.HistoryTimeAdapter;
import com.example.framgia.imarketandroid.ui.adapter.RecyclerDrawerAdapter;
import com.example.framgia.imarketandroid.ui.adapter.RecyclerMarketAdapter;
import com.example.framgia.imarketandroid.ui.widget.LinearItemDecoration;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.DialogShareUtil;
import com.example.framgia.imarketandroid.util.SharedPreferencesUtil;
import com.example.framgia.imarketandroid.util.findpath.InternetUtil;
import com.example.framgia.imarketandroid.util.findpath.LoadDataUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yue on 20/07/2016.
 */
public class ChooseMarketActivity extends AppCompatActivity implements
    NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
    SearchView.OnQueryTextListener, OnRecyclerItemInteractListener {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerMarket;
    private RecyclerMarketAdapter mAdapter;
    public static List<CommerceCanter> sMarkets = new ArrayList<>();
    private CursorAdapter mSearchSuggestionAdapter;
    private TextView mTextEmail;
    private ImageView mImageAvatar;
    private List<DrawerItem> mDrawerItems = new ArrayList<>();
    private RecyclerView mRecyclerDrawer;
    private RecyclerDrawerAdapter mRecyclerDrawerAdapter;
    private View mStrokeLine1, mStrokeLine2, mStrokeLine3;
    private View mLinearMenu;
    private TextView mTextSignIn, mTextSignOut, mTextProfile;
    private TextView mTextUsername;
    private CircleImageView mCircleImageView;
    private List<CartItem> mCartItems = new ArrayList<>();
    private List<String> mHeaderNames = new ArrayList<>();
    private HistoryTimeAdapter mHistoryTimeAdapter;
    private RelativeLayout mReFavorite, mReBound, mReFollow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_market);
        findViews();
        setListeners();
        supportActionBar();
        setSearchSuggestionAdapter();
        initRececlerView();
        InternetUtil.isInternetConnected(ChooseMarketActivity.this);
    }

    private void initRececlerView() {
        // recyclerview navigation drawer
        mRecyclerDrawer.setLayoutManager(new LinearLayoutManager(this));
        getRecycleFavorite();
        mRecyclerDrawer.addItemDecoration(new LinearItemDecoration(this));
        mRecyclerDrawerAdapter.setOnClick(this);
        setListeners();
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mRecyclerMarket.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerMarket.addItemDecoration(new LinearItemDecoration(this));
        LoadDataUtils.init(this);
        LoadDataUtils.loadCommerce();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mAdapter = new RecyclerMarketAdapter(this , sMarkets);
        mRecyclerMarket.setAdapter(mAdapter);
        mAdapter.setOnRecyclerItemInteractListener(this);
    }

    private void supportActionBar() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setSearchSuggestionAdapter() {
        final String[] columns = new String[]{Constants.MARKET_SUGGESTION};
        final int[] displayViews = new int[]{android.R.id.text1};
        mSearchSuggestionAdapter = new SimpleCursorAdapter(this,
            android.R.layout.simple_list_item_1,
            null,
            columns,
            displayViews,
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        getInfo();
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
        if (InternetUtil.isInternetConnected(ChooseMarketActivity.this)) {
            switch (view.getId()) {
                case R.id.button_favorite:
                    getRecycleFavorite();
                    getVisible(mStrokeLine1, mStrokeLine2, mStrokeLine3, mLinearMenu);
                    DialogShareUtil.getSmallBang(ChooseMarketActivity.this, mReFavorite);
                    break;
                case R.id.button_bought:
                    DialogShareUtil.getSmallBang(ChooseMarketActivity.this, mReBound);
                    getVisible(mStrokeLine2, mStrokeLine1, mStrokeLine3, mLinearMenu);
                    mHeaderNames = FakeContainer.getListHeader();
                    mCartItems = FakeContainer.getListCartItem();
                    if (mHistoryTimeAdapter == null) {
                        mHistoryTimeAdapter =
                            new HistoryTimeAdapter(mHeaderNames, mCartItems, this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                        mRecyclerDrawer.setLayoutManager(linearLayoutManager);
                        mRecyclerDrawer.addItemDecoration(new LinearItemDecoration(this));
                        mRecyclerDrawer.setAdapter(mHistoryTimeAdapter);
                    } else {
                        mRecyclerDrawer.setAdapter(mHistoryTimeAdapter);
                    }
                    break;
                case R.id.button_follow:
                    DialogShareUtil.getSmallBang(ChooseMarketActivity.this, mReFollow);
                    getVisible(mStrokeLine3, mStrokeLine2, mStrokeLine1, mLinearMenu);
                    break;
                case R.id.button_more:
                    if (mLinearMenu.getVisibility() == View.GONE) {
                        mLinearMenu.setVisibility(View.VISIBLE);
                    } else {
                        mLinearMenu.setVisibility(View.GONE);
                    }
                    break;
                case R.id.button_sign_in:
                    mLinearMenu.setVisibility(View.GONE);
                    startActivity(new Intent(this, LoginActivity.class));
                    getInfo();
                    break;
                case R.id.button_sign_out:
                    SharedPreferencesUtil.getInstance().init(this, Constants.PREFS_NAME);
                    Session session = (Session) SharedPreferencesUtil
                        .getInstance()
                        .getValue(Constants.SESSION, Session.class);
                    if (session != null) {
                        actionSignout();
                    } else {
                        DialogShareUtil
                            .toastDialogMessage(getString(R.string.signout_fails_message),
                                ChooseMarketActivity.this);
                    }
                    mLinearMenu.setVisibility(View.GONE);
                    break;
                case R.id.button_profile:
                    SharedPreferencesUtil.getInstance().init(this, Constants.PREFS_NAME);
                    Session session2 = (Session) SharedPreferencesUtil
                        .getInstance()
                        .getValue(Constants.SESSION, Session.class);
                    if (session2 != null)
                        startActivity(new Intent(this, UpdateProfileActivity.class));
                    else {
                        mLinearMenu.setVisibility(View.GONE);
                        startActivity(new Intent(this, LoginActivity.class));
                        getInfo();
                    }
                    break;
                case R.id.image_avatar:
                    mLinearMenu.setVisibility(View.GONE);
                    break;
            }
        }
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

    private void findViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerMarket = (RecyclerView) findViewById(R.id.recycler_market);
        mRecyclerDrawer = (RecyclerView) findViewById(R.id.recycler_navigation_drawer);
        mStrokeLine1 = findViewById(R.id.nav_drawer_stroke_1);
        mStrokeLine2 = findViewById(R.id.nav_drawer_stroke_2);
        mStrokeLine3 = findViewById(R.id.nav_drawer_stroke_3);
        mLinearMenu = findViewById(R.id.linear_menu);
        mTextProfile = (TextView) findViewById(R.id.button_profile);
        mTextSignIn = (TextView) findViewById(R.id.button_sign_in);
        mTextSignOut = (TextView) findViewById(R.id.button_sign_out);
        mTextUsername = (TextView) findViewById(R.id.text_user_name);
        mTextEmail = (TextView) findViewById(R.id.text_email);
        mCircleImageView = (CircleImageView) findViewById(R.id.image_avatar);
        mReFavorite = (RelativeLayout) findViewById(R.id.button_favorite);
        mReFavorite.setOnClickListener(this);
        mReBound = (RelativeLayout) findViewById(R.id.button_bought);
        mReBound.setOnClickListener(this);
        mReFollow = (RelativeLayout) findViewById(R.id.button_follow);
        mReFollow.setOnClickListener(this);
    }

    private void setListeners() {
        findViewById(R.id.button_favorite).setOnClickListener(this);
        findViewById(R.id.button_bought).setOnClickListener(this);
        findViewById(R.id.button_follow).setOnClickListener(this);
        findViewById(R.id.button_more).setOnClickListener(this);
        mTextProfile.setOnClickListener(this);
        mTextSignIn.setOnClickListener(this);
        mTextSignOut.setOnClickListener(this);
    }

    private void populateSuggestionAdapter(String query) {
        final MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID,
            Constants.MARKET_SUGGESTION});
        int length = FakeContainer.SUGGESTIONS.length;
        for (int i = 0; i < length; i++) {
            if (FakeContainer.SUGGESTIONS[i].toLowerCase().startsWith(query.toLowerCase()))
                c.addRow(new Object[]{i, FakeContainer.SUGGESTIONS[i]});
            mSearchSuggestionAdapter.changeCursor(c);
        }
    }

    private void getInfo() {
        SharedPreferencesUtil.getInstance().init(this, Constants.PREFS_NAME);
        UserModel userModel = (UserModel) SharedPreferencesUtil
            .getInstance()
            .getValue(Constants.SESSION, UserModel.class);
        if (userModel != null && userModel.getSession() != null) {
            if (userModel.getSession().getFullname() != null) {
                mTextUsername.setText(userModel.getSession().getFullname().toString());
            }
            if (userModel.getSession().getUsername() != null) {
                mTextEmail.setText(userModel.getSession().getUsername().toString());
            }
            if (userModel.getSession().getUrlImage() != null) {
                String url =
                    Constants.HEAD_URL + userModel.getSession().getUrlImage();
                Glide.with(this).load(url).into(mCircleImageView);
            } else {
                mCircleImageView.setImageResource(R.drawable.ic_framgia);
            }
        } else {
            mTextUsername.setText(R.string.login_hint_username);
            mTextEmail.setText(R.string.email);
            mCircleImageView.setImageResource(R.drawable.ic_framgia);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLinearMenu.setVisibility(View.GONE);
        getInfo();
    }

    private void actionSignout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.noti);
        builder.setMessage(R.string.confirm_signout);
        builder
            .setPositiveButton(R.string.ok_dialog_success, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferencesUtil.getInstance().clearSharedPreference();
                    dialog.dismiss();
                    Toast.makeText(ChooseMarketActivity.this,
                        getString(R.string.signout_done_message), Toast.LENGTH_SHORT).show();
                    getInfo();
                }
            });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
        SharedPreferencesUtil.getInstance().clearSharedPreference();
    }

    public void getVisible(View view1, View view2, View view3, View view4) {
        view1.setVisibility(View.VISIBLE);
        view2.setVisibility(View.GONE);
        view3.setVisibility(View.GONE);
        view4.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(View v, int position) {
        switch (v.getId()) {
            case R.id.image_btn_delete:
                mDrawerItems.remove(position);
                mRecyclerDrawerAdapter.notifyDataSetChanged();
                break;
            case R.id.linear_navigation_drawer:
                startActivity(new Intent(this, DetailsProductActivity.class));
                break;
            case R.id.item_recycler_market:
                mLinearMenu.setVisibility(View.GONE);
                startActivity(new Intent(this, FloorActivity.class));
                break;
        }
    }

    private void getRecycleFavorite() {
        mDrawerItems = FakeContainer.initDrawerItems();
        mRecyclerDrawerAdapter = new RecyclerDrawerAdapter(this, mDrawerItems);
        mRecyclerDrawer.setAdapter(mRecyclerDrawerAdapter);
    }

}