package com.example.framgia.imarketandroid.ui.activity;

import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.io.InputStream;

public class LoginGooglePlusActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 0;
    // Logcat tag
    private static final String TAG = "MainActivity";
    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private int mStartIndex=0;
    private int mSubLetter=2;
    private ConnectionResult mConnectionResult;
    private SignInButton mBtnSignIn;
    private Button mBtnSignOut, mBtnRevokeAccess;
    private ImageView mImgProfilePic;
    private TextView mTxtName, mTxtEmail;
    private LinearLayout mllProfileLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_google_plus);
        mBtnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        mBtnSignOut = (Button) findViewById(R.id.btn_sign_out);
        mBtnRevokeAccess = (Button) findViewById(R.id.btn_revoke_access);
        mImgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
        mTxtName = (TextView) findViewById(R.id.txtName);
        mTxtEmail = (TextView) findViewById(R.id.txtEmail);
        mllProfileLayout = (LinearLayout) findViewById(R.id.llProfile);

        // Button click listeners
        mBtnSignIn.setOnClickListener(this);
        mBtnSignOut.setOnClickListener(this);
        mBtnRevokeAccess.setOnClickListener(this);

        // Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
    }

    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                // Signin button clicked
                signInWithGplus();
                break;
            case R.id.btn_sign_out:
                // Signout button clicked
                signOutFromGplus();
                break;
            case R.id.btn_revoke_access:
                // Revoke access button clicked
                revokeGplusAccess();
                break;
        }
    }

    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            updateUI(false);
        }
    }

    private void revokeGplusAccess() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            mGoogleApiClient.connect();
                            updateUI(false);
                        }

                    });
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mSignInClicked = false;
        Toast.makeText(this, R.string.connect_successfully, Toast.LENGTH_LONG).show();
        getProfileInformation();
        updateUI(true);
    }

    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String personName = currentPerson.getDisplayName();
                String personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = currentPerson.getUrl();
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

                mTxtName.setText(personName);
                mTxtEmail.setText(email);
                personPhotoUrl = personPhotoUrl.substring(mStartIndex,
                        personPhotoUrl.length() - mSubLetter)
                        + PROFILE_PIC_SIZE;

                new LoadProfileImage(mImgProfilePic).execute(personPhotoUrl);

            } else {
                Toast.makeText(getApplicationContext(),
                        R.string.error_get_profile, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            mBtnSignIn.setVisibility(View.GONE);
            mBtnSignOut.setVisibility(View.VISIBLE);
            mBtnRevokeAccess.setVisibility(View.VISIBLE);
            mllProfileLayout.setVisibility(View.VISIBLE);
        } else {
            mBtnSignIn.setVisibility(View.VISIBLE);
            mBtnSignOut.setVisibility(View.GONE);
            mBtnRevokeAccess.setVisibility(View.GONE);
            mllProfileLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
        updateUI(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {

        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            mConnectionResult = result;

            if (mSignInClicked) {
                resolveSignInError();
            }
        }
    }

    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView mImage;

        public LoadProfileImage(ImageView bmImage) {
            this.mImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            mImage.setImageBitmap(result);
        }
    }
}
