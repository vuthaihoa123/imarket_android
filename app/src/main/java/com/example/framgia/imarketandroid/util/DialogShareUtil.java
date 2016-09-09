package com.example.framgia.imarketandroid.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import java.security.interfaces.RSAKey;
import java.util.Arrays;
import java.util.List;

/**
 * Created by phongtran on 08/09/2016.
 */
public class DialogShareUtil {
    private static Activity mActivity;
    private static AlertDialog mAlertDialogShare;
    private static LoginManager mLogin;

    public static void dialogShare(Activity context, final int idImageView,
                                   final CallbackManager callbackManager) {
        mActivity = context;
        LayoutInflater li = LayoutInflater.from(mActivity);
        View promptsView = li.inflate(R.layout.dialog_share, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
        alertDialogBuilder.setView(promptsView);
        final EditText editTextShare = (EditText) promptsView.findViewById(R.id.edittext_share);
        ImageView imgShare = (ImageView) promptsView.findViewById(R.id.image_share);
        Button buttonCancel = (Button) promptsView.findViewById(R.id.button_cancel_share_dialog);
        Button buttonSuccess = (Button) promptsView.findViewById(R.id.button_success_share_dialog);
        imgShare.setImageResource(idImageView);
        alertDialogBuilder
            .setCancelable(false);
        mAlertDialogShare = alertDialogBuilder.create();
        mAlertDialogShare.show();
        mAlertDialogShare.setCanceledOnTouchOutside(true);
        editTextShare.requestFocus();
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlertDialogShare.dismiss();
            }
        });
        buttonSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionShare(idImageView, editTextShare.getText().toString(), callbackManager);
                mAlertDialogShare.dismiss();
            }
        });
    }

    private static void actionShare(final int idImageShare, final String textShare,
                                    CallbackManager callbackManager) {
        FacebookSdk.sdkInitialize(mActivity);
        List<String> permissionNeeds = Arrays.asList(Constants.PERMISSTION_SHARE);
        mLogin = LoginManager.getInstance();
        mLogin.logInWithPublishPermissions(mActivity, permissionNeeds);
        mLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                sharePhotoToFacebook(idImageShare, textShare);
                toastDialogMessage(mActivity.getString(R.string.success), mActivity);
            }

            @Override
            public void onCancel() {
                toastDialogMessage(mActivity.getString(R.string.cancle), mActivity);
            }

            @Override
            public void onError(FacebookException exception) {
                toastDialogMessage(mActivity.getString(R.string.error), mActivity);
            }
        });
    }

    private static void sharePhotoToFacebook(int idImageShare, String textShare) {
        Bitmap image = BitmapFactory.decodeResource(mActivity.getResources(), idImageShare);
        SharePhoto photo = new SharePhoto.Builder()
            .setBitmap(image)
            .setCaption(textShare)
            .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
            .addPhoto(photo)
            .build();
        ShareApi.share(content, null);
    }

    public static void toastDialogMessage(String mess, Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.noti);
        builder.setMessage(mess);
        builder.setPositiveButton(R.string.ok_dialog_success, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
