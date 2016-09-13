package com.example.framgia.imarketandroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.SharedPreferencesUtil;

/**
 * Created by toannguyen201194 on 13/09/2016.
 */
public class IntroduceActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mPagerIntroduce;
    private LinearLayout mLinearDots;
    private int[] mLayout;
    private Button mBtnSkip, mBtnNext;
    private TextView[] mDots;
    ViewPager.OnPageChangeListener viewPagerPageChangeListener =
        new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
                if (position == mLayout.length - 1) {
                    mBtnNext.setText(getString(R.string.start));
                    mBtnSkip.setVisibility(View.GONE);
                } else {
                    mBtnNext.setText(getString(R.string.next));
                    mBtnNext.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferencesUtil.getInstance().init(this, Constants.PREF_WELCOME);
        if (!SharedPreferencesUtil.getInstance().isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_introduce);
        initView();
        init();
    }

    private void initView() {
        mLayout = new int[]{
            R.layout.item_introduce_1,
            R.layout.item_introduce_2,
            R.layout.item_introduce_3,
            R.layout.item_introduce_4
        };
        mPagerIntroduce = (ViewPager) findViewById(R.id.view_pager_introduce);
        mLinearDots = (LinearLayout) findViewById(R.id.layoutDots);
        mBtnSkip = (Button) findViewById(R.id.btn_skip);
        mBtnNext = (Button) findViewById(R.id.btn_next);
        mBtnNext.setOnClickListener(this);
        mBtnSkip.setOnClickListener(this);
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
        mPagerIntroduce.setAdapter(myViewPagerAdapter);
        mPagerIntroduce.addOnPageChangeListener(viewPagerPageChangeListener);
    }

    private void init() {
        addBottomDots(0);
        changeStatusBarColor();
    }

    private void launchHomeScreen() {
        SharedPreferencesUtil.getInstance().setFirstTimeLaunch(false);
        startActivity(new Intent(IntroduceActivity.this, ChooseMarketActivity.class));
        finish();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void addBottomDots(int currentPage) {
        mDots = new TextView[mLayout.length];
        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);
        mLinearDots.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(colorsInactive[currentPage]);
            mLinearDots.addView(mDots[i]);
        }
        if (mDots.length > 0)
            mDots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return mPagerIntroduce.getCurrentItem() + i;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                int current = getItem(+1);
                if (current < mLayout.length) {
                    mPagerIntroduce.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
                break;
            case R.id.btn_skip:
                launchHomeScreen();
                break;
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(mLayout[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return mLayout.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
