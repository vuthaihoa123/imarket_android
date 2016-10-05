package com.example.framgia.imarketandroid.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.framgia.imarketandroid.BuildConfig;
import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.Session;
import com.example.framgia.imarketandroid.data.model.UserModel;
import com.example.framgia.imarketandroid.ui.adapter.ViewPagerAdapter;
import com.example.framgia.imarketandroid.ui.fragments.SignInFragment;
import com.example.framgia.imarketandroid.ui.fragments.SignUpFragment;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.HttpRequest;
import com.example.framgia.imarketandroid.util.SharedPreferencesUtil;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by toannguyen201194 on 22/07/2016.
 */
public class LoginActivity extends AppCompatActivity {
    private TabLayout mTabLayoutLogin;
    private ViewPager mViewPagerLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferencesUtil.getInstance().init(this, Constants.PREFS_NAME);
        FacebookSdk.sdkInitialize(this);
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        UserModel session = (UserModel) SharedPreferencesUtil.getInstance().getValue
            (Constants.SESSION,
                UserModel.class);
        if (session != null && session.getSession() != null) {
            startActivity(new Intent(this, UpdateProfileActivity.class));
            finish();
        }
        AppEventsLogger.activateApp(getApplicationContext());
        setContentView(R.layout.activity_login);
        initViews();
        HttpRequest.getInstance().init();
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
}
