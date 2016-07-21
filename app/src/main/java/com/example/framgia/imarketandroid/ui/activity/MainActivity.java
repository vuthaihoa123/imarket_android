package com.example.framgia.imarketandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.util.Constants;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LoginButton mLoginButton;
    private CallbackManager mCallbackManager;
    private ProfilePictureView mProfilePictureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);
        mCallbackManager = CallbackManager.Factory.create();
        findView();
    }

    private void findView() {
        mLoginButton = (LoginButton) findViewById(R.id.login_button);
        mProfilePictureView = (ProfilePictureView) findViewById(R.id.profile_picture);
        mLoginButton.setOnClickListener(this);
        findViewById(R.id.button_change_activity).setOnClickListener(this);
    }

    public void registerCallback() {
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        if (response != null) {
                            mProfilePictureView.setProfileId(Profile.getCurrentProfile().getId());
                            mProfilePictureView.setVisibility(View.VISIBLE);
                        }
                    }
                });
                Bundle params = new Bundle();
                params.putString(Constants.FIELD, Constants.PARAMS);
                request.setParameters(params);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void checkReadPermission() {
        mLoginButton.setReadPermissions(Arrays.asList(Constants.PUBLIC_PROFILE, Constants.EMAIL));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                if (AccessToken.getCurrentAccessToken() == null) {
                    checkReadPermission();
                    registerCallback();
                } else {
                    mProfilePictureView.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.button_change_activity:
                startActivity(new Intent(this, ChooseMarketActivity.class));
        }
    }
}
