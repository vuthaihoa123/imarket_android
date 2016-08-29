package com.example.framgia.imarketandroid.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.framgia.imarketandroid.data.model.Session;

/**
 * Created by toannguyen201194 on 29/07/2016.
 */
public class SharedPreferencesUtil {
    private static SharedPreferencesUtil sInstance;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mSettings;

    private SharedPreferencesUtil() {
    }

    public static SharedPreferencesUtil getInstance() {
        if (sInstance == null) {
            synchronized (HttpRequest.class) {
                if (sInstance == null) {
                    sInstance = new SharedPreferencesUtil();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        mSettings = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        mEditor = mSettings.edit();
    }

    public void save(Session session) {
        mEditor.putString(Constants.ID, session.getId());
        mEditor.putString(Constants.FULLNAME, session.getFullname());
        mEditor.putString(Constants.USERNAME, session.getUsername());
        mEditor.apply();
    }

    public Session getValue() {
        Session session = new Session();
        session.setId(mSettings.getString(Constants.ID, null));
        session.setFullname(mSettings.getString(Constants.FULLNAME, null));
        session.setUsername(mSettings.getString(Constants.USERNAME, null));
        return session;
    }

    public void clearSharedPreference(Context context) {
        mEditor = mSettings.edit();
        mEditor.clear();
        mEditor.apply();
    }
}
