package com.example.framgia.imarketandroid.ui.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framgia.imarketandroid.BuildConfig;
import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.Session;
import com.example.framgia.imarketandroid.data.model.UserModel;
import com.example.framgia.imarketandroid.ui.activity.DetailsProductActivity;
import com.example.framgia.imarketandroid.ui.activity.UpdateProfileActivity;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.HttpRequest;
import com.example.framgia.imarketandroid.util.SharedPreferencesUtil;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by toannguyen201194 on 22/07/2016.
 */
public class SignInFragment extends android.support.v4.app.Fragment implements
    View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient
    .OnConnectionFailedListener {
    private static final String SHOW = "show";
    private static final int RC_SIGN_IN = 0;
    private static final int PROFILE_PIC_SIZE = 400;
    private CallbackManager mCallbackManager;
    private View mView;
    private Button mButtonLogin;
    private FrameLayout mFramLoginFacebook, mFramLoginGmail;
    private TextView mTextForgetPass;
    private EditText mEditUsername, mEditPassword;
    private AccessToken mAccessToken;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private int mStartIndex = 0;
    private int mSubLetter = 2;
    private ProgressDialog mProgressDialog;
    private ConnectionResult mConnectionResult;
    private boolean flagPass = false;

    public SignInFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_signin, container, false);
        FacebookSdk.sdkInitialize(getContext());
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        AppEventsLogger.activateApp(getContext());
        mCallbackManager = CallbackManager.Factory.create();
        createBuilderGoogleApi();
        SharedPreferencesUtil.getInstance().init(getActivity(), Constants.PREFS_NAME);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findView();
        initView();
    }

    private void findView() {
        mTextForgetPass = (TextView) mView.findViewById(R.id.text_view_forget_password);
        mButtonLogin = (Button) mView.findViewById(R.id.button_login);
        mFramLoginFacebook = (FrameLayout) mView.findViewById(R.id.frame_button_facebook);
        mFramLoginGmail = (FrameLayout) mView.findViewById(R.id.frame_button_gmail);
        mEditUsername = (EditText) mView.findViewById(R.id.edit_text_username);
        mEditPassword = (EditText) mView.findViewById(R.id.edit_text_password);
    }

    public void createBuilderGoogleApi() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this).addApi(Plus.API)
            .addScope(Plus.SCOPE_PLUS_LOGIN).build();
    }

    private void initView() {
        mTextForgetPass.setOnClickListener(this);
        mFramLoginGmail.setOnClickListener(this);
        mFramLoginFacebook.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);
        mView.findViewById(R.id.button_see_pass).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view_forget_password:
                showFragmentLogin();
                break;
            case R.id.button_login:
                eventClickButtonLogin();
                break;
            case R.id.frame_button_facebook:
                checkReadPermission();
                eventClickButtonLoginFacabook();
                break;
            case R.id.frame_button_gmail:
                requestPermissions();
                break;
            case R.id.button_see_pass:
                setTextPass();
                break;
        }
    }

    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(getActivity(), RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public void showFragmentLogin() {
        ForgetFragment forgetFragment = ForgetFragment.newInstace();
        forgetFragment.show(getFragmentManager(), SHOW);
    }

    public void eventClickButtonLogin() {
        if (mEditUsername.getText().toString().isEmpty() || mEditPassword.getText().toString()
            .trim().isEmpty()) {
            Toast.makeText(getContext(), R.string.notification_user_pass, Toast.LENGTH_SHORT)
                .show();
        } else {
            final Session session = new Session(mEditUsername.getText().toString(), mEditPassword
                .getText().toString());
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.progressdialog));
            mProgressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.show();
            HttpRequest.getInstance().login(session);
            HttpRequest.getInstance().setOnLoadDataListener(new HttpRequest.OnLoadDataListener() {
                @Override
                public void onLoadDataSuccess(Object object) {
                    object.toString();
                    UserModel userModel = (UserModel) object;
                    Session session1 = userModel.getSession();
                    mProgressDialog.dismiss();
                    if (userModel == null) {
                        Toast.makeText(getContext(), R.string.msg_login_invaild,
                            Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferencesUtil.getInstance().save(Constants.SESSION, userModel);
                        getActivity().onBackPressed();
                    }
                }

                @Override
                public void onLoadDataFailure(String message) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getActivity(), "Login fails !", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void eventClickButtonLoginFacabook() {
        LoginManager.getInstance().registerCallback(mCallbackManager,
            new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    mAccessToken = loginResult.getAccessToken();
                    GraphRequest graphRequest =
                        GraphRequest.newMeRequest(mAccessToken,
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    try {
                                        AccessToken accessToken =
                                            AccessToken.getCurrentAccessToken();
                                        String name =
                                            object.getString(Constants.LAST_NAME) + object.getString
                                                (Constants.FIRST_NAME);
                                        String mail = object.getString(Constants.EMAIL);
                                        startActivity(new Intent(getActivity(),
                                            DetailsProductActivity.class));
                                        // TODO: 26/07/2016  post data token at here
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString(Constants.FIELD,
                        "" + Constants.FIRST_NAME + "," + Constants.LAST_NAME + "," +
                            Constants.EMAIL + "");
                    graphRequest.setParameters(parameters);
                    graphRequest.executeAsync();
                }

                @Override
                public void onCancel() {
                }

                @Override
                public void onError(FacebookException error) {
                }
            });
    }

    private void checkReadPermission() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(Constants
            .PUBLIC_PROFILE, Constants.EMAIL));
    }

    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != Activity.RESULT_OK) {
                mSignInClicked = false;
            }
            mIntentInProgress = false;
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mSignInClicked = false;
        getProfileInformation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
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
                personPhotoUrl = personPhotoUrl.substring(mStartIndex,
                    personPhotoUrl.length() - mSubLetter)
                    + PROFILE_PIC_SIZE;
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), (Activity) getContext(),
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

    public void requestPermissions() {
        if (ActivityCompat
            .checkSelfPermission(getActivity(), Manifest.permission.GET_ACCOUNTS)
            != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat
                .shouldShowRequestPermissionRationale(getActivity(), Manifest.permission
                    .GET_ACCOUNTS)) {
                Snackbar.make(mView, getString(R.string.permission),
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.retry), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.GET_ACCOUNTS},
                                Constants.MY_PERMISSIONS_REQUEST);
                        }
                    }).show();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission
                    .GET_ACCOUNTS}, Constants.MY_PERMISSIONS_REQUEST);
            }
        } else {
            signInWithGplus();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.MY_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                signInWithGplus();
            } else {
                Snackbar.make(mView, getString(R.string.no_allow_print),
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.Settings), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intentSetting =
                                new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts(Constants.PACKAGE, getActivity().getPackageName(),
                                        null));
                            intentSetting.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intentSetting);
                        }
                    }).show();
            }
        }
    }

    private void setTextPass() {
        if (mEditPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            mEditPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            mEditPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
    }

    private void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
        Toast.makeText(getActivity(), R.string.timeout_dialog, Toast.LENGTH_SHORT)
            .show();
    }
}