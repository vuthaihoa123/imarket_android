package com.example.framgia.imarketandroid.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.framgia.imarketandroid.BuildConfig;
import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.ui.fragments.SignInFragment;
import com.example.framgia.imarketandroid.ui.fragments.SignUpFragment;
import com.example.framgia.imarketandroid.util.Constants;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toannguyen201194 on 22/07/2016.
 */
public class LoginActivity extends AppCompatActivity {
    private TabLayout mTabLayoutLogin;
    private ViewPager mViewPagerLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        AppEventsLogger.activateApp(getApplicationContext());
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {
        mTabLayoutLogin = (TabLayout) findViewById(R.id.tab_login);
        mViewPagerLogin = (ViewPager) findViewById(R.id.view_pager_login);
        setupViewPager(mViewPagerLogin);
        mTabLayoutLogin.setupWithViewPager(mViewPagerLogin);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SignInFragment(), Constants.LOGIN);
        adapter.addFragment(new SignUpFragment(), Constants.SIGNUP);
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
