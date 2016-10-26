package com.example.framgia.imarketandroid.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.FakeContainer;
import com.example.framgia.imarketandroid.data.listener.OnRecyclerItemInteractListener;
import com.example.framgia.imarketandroid.data.model.Store;
import com.example.framgia.imarketandroid.data.model.StoreType;
import com.example.framgia.imarketandroid.data.model.Stores;
import com.example.framgia.imarketandroid.data.model.UserModel;
import com.example.framgia.imarketandroid.ui.adapter.ChooseStoreTypeAdapter;
import com.example.framgia.imarketandroid.ui.widget.LinearItemDecoration;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.DialogShareUtil;
import com.example.framgia.imarketandroid.util.Flog;
import com.example.framgia.imarketandroid.util.HttpRequest;
import com.example.framgia.imarketandroid.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

public class ChooseStoreTypeActivity extends AppCompatActivity implements OnRecyclerItemInteractListener {
    public static int sAvatar;
    private ChooseStoreTypeAdapter mAdapter;
    private List<StoreType> mListStore = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_store_type);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_store_type);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.addItemDecoration(new LinearItemDecoration(this));
        mListStore = FakeContainer.initStore();
        mAdapter = new ChooseStoreTypeAdapter(this, mListStore);
        mAdapter.setOnRecyclerItemInteractListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View view,int position) {
        sAvatar = position;
        finish();
    }

}
