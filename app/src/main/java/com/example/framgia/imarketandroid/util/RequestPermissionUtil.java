package com.example.framgia.imarketandroid.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.example.framgia.imarketandroid.R;

/**
 * Created by toannguyen201194 on 20/09/2016.
 */
public class RequestPermissionUtil {
    public static boolean checkIfAlreadyhavePermission(Activity activity, String
        permission) {
        int result = ContextCompat.checkSelfPermission(activity, permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestForSpecificPermision(final Activity activity,
                                                   final String[] permisson, View view, String p1) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, p1)) {
            Snackbar.make(view, R.string.toastPermisson,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityCompat
                            .requestPermissions(activity, permisson, Constants.REQUEST_PERMISSION);
                    }
                }).show();
        }
    }
}
