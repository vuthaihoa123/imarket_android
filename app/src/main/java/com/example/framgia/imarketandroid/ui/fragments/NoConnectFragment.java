package com.example.framgia.imarketandroid.ui.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.ui.activity.HomeStoreActivity;

/**
 * Created by phongtran on 23/09/2016.
 */
public class NoConnectFragment extends Fragment {
    private Context mContext;
    private View mView;
    private  OnClickToLoadConnect mLoadConnect;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return mView = inflater.inflate(R.layout.fragment_no_connect_network, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public NoConnectFragment(Context context) {
        mContext = context;
        if (mContext instanceof OnClickToLoadConnect) {
            mLoadConnect = (OnClickToLoadConnect) mContext;
        }
    }

    private void initView() {
        mView.findViewById(R.id.image_no_connect_network).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mLoadConnect != null) {
                        mLoadConnect.onClickLoadConnect();
                    }
                }
            });

    }
    public interface OnClickToLoadConnect {
        void onClickLoadConnect();
    }
}
