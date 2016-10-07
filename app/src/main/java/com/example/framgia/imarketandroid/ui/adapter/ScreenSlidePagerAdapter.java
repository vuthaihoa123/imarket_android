package com.example.framgia.imarketandroid.ui.adapter;

/**
 * Created by vutha on 10/4/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.framgia.imarketandroid.ui.fragments.ScreenSlidePageFragment;
import com.example.framgia.imarketandroid.util.Constants;

import java.util.ArrayList;

/**
 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
 * sequence.
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private int mNumOfPages = 0;
    private ArrayList<Integer> mArrIdRes;

    public ScreenSlidePagerAdapter(FragmentManager fm, ArrayList<Integer> arrayList) {
        super(fm);
        mArrIdRes = arrayList;
        mNumOfPages = arrayList.size();
    }

    @Override
    public Fragment getItem(int position) {
        int idRes = mArrIdRes.get(position);
        ScreenSlidePageFragment slidePageFrag = new ScreenSlidePageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.EXTRA_ID_RES_PREVIEW_DETAILS, idRes);
        slidePageFrag.setArguments(bundle);
        return slidePageFrag;
    }

    @Override
    public int getCount() {
        return mNumOfPages;
    }
}

