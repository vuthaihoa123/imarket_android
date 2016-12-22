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
import com.example.framgia.imarketandroid.data.model.Event;
import com.example.framgia.imarketandroid.data.model.ImageEvent1;
import com.example.framgia.imarketandroid.data.model.ImageEvent2;
import com.example.framgia.imarketandroid.data.model.ItemProduct;
import com.example.framgia.imarketandroid.data.model.NewEvent;
import com.example.framgia.imarketandroid.ui.adapter.SaleOffEventAdapter;
import com.example.framgia.imarketandroid.util.findpath.DateTimeUtil;
import com.facebook.CallbackManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by toannguyen201194 on 07/09/2016.
 */
public class SaleOffEventFragment extends Fragment {
    private View mView;
    private RecyclerView mRecyclerView;
    private SaleOffEventAdapter mSaleOffEventAdapter;
    private List<Event> mEvents = new ArrayList<>();
    private List<Object> mList = new ArrayList<>();
    private CallbackManager mCallback;

    public SaleOffEventFragment(List<Event> events, CallbackManager cb) {
        mCallback = cb;
        mList = FakeContainer.getSampleArrayList();
        if (events != null) {
            mEvents = events;
            for (Event event : mEvents) {
                String time = DateTimeUtil.getTimeEvent(event.getStartTime(), event.getEndTime());
                NewEvent newEvent = new NewEvent(event.getName(), event.getContent(), time);
                mList.add(0, newEvent);
            }
        }
    }

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
        mSaleOffEventAdapter = new SaleOffEventAdapter(mList, getActivity(), mCallback);
        mRecyclerView.setAdapter(mSaleOffEventAdapter);
    }
}