package com.example.framgia.imarketandroid;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.framgia.imarketandroid.util.NotificationUtil;

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
}
