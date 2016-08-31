package com.example.framgia.imarketandroid.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.framgia.imarketandroid.data.model.Session;
import com.google.gson.Gson;

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
    }
    public void save(String nameObject, Object object) {
        SharedPreferences.Editor prefsEditor = mSettings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(object); // object - instance of MyObject
        prefsEditor.putString(nameObject, json);
        prefsEditor.apply();
    }
    public Object getValue(String nameObject,Class aClass) {
        Gson gson = new Gson();
        String json = mSettings.getString(nameObject,"");
        Object object=gson.fromJson(json,aClass);
        return object;
    }
    public void clearSharedPreference(Context context) {
        mEditor = mSettings.edit();
        mEditor.clear();
        mEditor.apply();
    }
}
