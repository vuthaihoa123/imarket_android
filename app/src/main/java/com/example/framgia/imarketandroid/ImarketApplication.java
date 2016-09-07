package com.example.framgia.imarketandroid;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;

import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.NotificationUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by toannguyen201194 on 24/08/2016.
 */
public class ImarketApplication extends Application {
    public int notificationCount;

    @Override
    public void onCreate() {
        super.onCreate();
        printHashKey();
        notificationCount = 0;
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
            .name(Realm.DEFAULT_REALM_NAME)
            .schemaVersion(0)
            .deleteRealmIfMigrationNeeded()
            .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        //  NotificationUtil.setAlarm(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public void incrementCount() {
        notificationCount++;
    }

    public int getNotificationCount() {
        return notificationCount;
    }

    public void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                getPackageName(),
                PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance(Constants.MESSAGEDIGEST);
                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }
}
