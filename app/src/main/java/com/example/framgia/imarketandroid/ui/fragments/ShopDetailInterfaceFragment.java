package com.example.framgia.imarketandroid.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.FakeContainer;
import com.example.framgia.imarketandroid.data.model.AlbumShop;
import com.example.framgia.imarketandroid.data.model.Showcase;
import com.example.framgia.imarketandroid.ui.activity.DetailsProductActivity;
import com.example.framgia.imarketandroid.ui.activity.HomeStoreActivity;
import com.example.framgia.imarketandroid.ui.adapter.ShopDetailInterfaceAdapter;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.DialogShareUtil;
import com.example.framgia.imarketandroid.util.ShowcaseGuideUtil;
import com.facebook.CallbackManager;

import java.util.ArrayList;
import java.util.List;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

/**
 * Created by phongtran on 07/09/2016.
 */
public class ShopDetailInterfaceFragment extends Fragment implements View.OnClickListener,
    ShopDetailInterfaceAdapter.OnClickAlbumListener {
    private Button mButtonRecruitmentIntroduction, mButtonInfoShop, mButtonAlbum;
    private boolean mFlagRecruitmentIntroduction = false,
        mFlagInfoShop = false, mFlagAlbum = true;
    private LinearLayout mLinearLayoutReIn, mLinearLayoutInfo;
    private RecyclerView mRecyclerViewAlbumShop;
    private Spinner mSpinnerBranchShop;
    private ShopDetailInterfaceAdapter mShopDetailInterfaceAdapter;
    private List<AlbumShop> mAlbumShopList = new ArrayList<>();
    private View mRootView;
    private Button mButtonShare;
    private CallbackManager mCallback;

    public ShopDetailInterfaceFragment(CallbackManager callbackManager) {
        mCallback = callbackManager;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout
            .fragment_shop_details_interface, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        getListenner();
        initRecycleAlbum();
    }

    private void initRecycleAlbum() {
        mAlbumShopList.addAll(FakeContainer.fakeDataAlbum());
        mShopDetailInterfaceAdapter = new ShopDetailInterfaceAdapter(getActivity(), mAlbumShopList);
        mRecyclerViewAlbumShop
            .setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager
                .HORIZONTAL, false));
        mRecyclerViewAlbumShop.setAdapter(mShopDetailInterfaceAdapter);
    }

    private void getListenner() {
        mButtonInfoShop.setOnClickListener(this);
        mButtonRecruitmentIntroduction.setOnClickListener(this);
        mButtonAlbum.setOnClickListener(this);
        mButtonShare.setOnClickListener(this);
    }

    private void initView() {
        mButtonInfoShop =
            (Button) mRootView.findViewById(R.id.button_recruitment_introduction_shop);
        mButtonRecruitmentIntroduction = (Button) mRootView.findViewById(R.id.button_info_shop);
        mButtonAlbum = (Button) mRootView.findViewById(R.id.button_album_shop);
        mLinearLayoutReIn = (LinearLayout) mRootView.findViewById(R.id
            .linear_recruitment_introduction_shop);
        mLinearLayoutReIn.setVisibility(View.GONE);
        mLinearLayoutInfo = (LinearLayout) mRootView.findViewById(R.id.linear_info_shop);
        mLinearLayoutInfo.setVisibility(View.GONE);
        mRecyclerViewAlbumShop = (RecyclerView) mRootView.findViewById(R.id.recycle_album_shop);
        mRecyclerViewAlbumShop.setVisibility(View.VISIBLE);
        mSpinnerBranchShop = (Spinner) mRootView.findViewById(R.id.spinner_branch_shop);
        ArrayAdapter<String> adapter = new ArrayAdapter<>
            (
                getActivity(),
                android.R.layout.simple_spinner_item,
                FakeContainer.arr
            );
        adapter.setDropDownViewResource
            (android.R.layout.simple_list_item_single_choice);
        mSpinnerBranchShop.setAdapter(adapter);
        mButtonShare = (Button) mRootView.findViewById(R.id.button_share_info_shop_on_fb);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_recruitment_introduction_shop:
                if (mFlagRecruitmentIntroduction) {
                    mFlagRecruitmentIntroduction = false;
                    mLinearLayoutReIn.setVisibility(View.GONE);
                } else {
                    mFlagRecruitmentIntroduction = true;
                    mLinearLayoutReIn.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.button_info_shop:
                if (mFlagInfoShop) {
                    mFlagInfoShop = false;
                    mLinearLayoutInfo.setVisibility(View.GONE);
                } else {
                    mFlagInfoShop = true;
                    mLinearLayoutInfo.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.button_share_info_shop_on_fb:
                DialogShareUtil.dialogShare(getActivity(), R.drawable.ic_iphone5s, mCallback);
                break;
        }
    }

    @Override
    public void OnClickAlbumShop(AlbumShop albumShop, int position) {
//        startActivity(new Intent(getActivity(), DetailsProductActivity.class));
    }

    public void initGuideShopDetail() {
        ShowcaseGuideUtil.singleShowcase(getActivity(), Constants.SHOWCASE_ID_DETAILS_SHOP,
            new Showcase(mButtonShare, getString(R.string.sequence_share_fb)));
    }
}
