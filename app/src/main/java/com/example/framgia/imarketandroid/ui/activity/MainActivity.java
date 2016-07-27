package com.example.framgia.imarketandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.CategoryList;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.HttpRequest;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, HttpRequest.OnLoadDataListener {

    private LoginButton mLoginButton;
    private CallbackManager mCallbackManager;
    private ProfilePictureView mProfilePictureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        HttpRequest.getInstance().init();
        HttpRequest.getInstance().setOnLoadDataListener(this);
        HttpRequest.getInstance().loadCategories();
    }

    private void findView() {
        findViewById(R.id.button_change_activity).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_change_activity:
                startActivity(new Intent(this, ChooseMarketActivity.class));
        }
    }

    @Override
    public void onLoadDataSuccess(Object object) {
    }

    @Override
    public void onLoadDataFailure(String message) {
    }
}
