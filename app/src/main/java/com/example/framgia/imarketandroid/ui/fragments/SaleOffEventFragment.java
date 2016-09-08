package com.example.framgia.imarketandroid.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.FakeContainer;
import com.example.framgia.imarketandroid.data.model.CategorySaleOff;
import com.example.framgia.imarketandroid.data.model.ImageEvent1;
import com.example.framgia.imarketandroid.data.model.ImageEvent2;
import com.example.framgia.imarketandroid.data.model.ItemProduct;
import com.example.framgia.imarketandroid.ui.adapter.SaleOffEventAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toannguyen201194 on 07/09/2016.
 */
public class SaleOffEventFragment extends Fragment {
    private View mView;
    private RecyclerView mRecyclerView;
    private SaleOffEventAdapter mSaleOffEventAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return mView = inflater.inflate(R.layout.fragment_saleoff_event, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycle_saleoff_main);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSaleOffEventAdapter = new SaleOffEventAdapter(FakeContainer.getSampleArrayList(), getContext());
        mRecyclerView.setAdapter(mSaleOffEventAdapter);
    }


}
