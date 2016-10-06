package com.example.framgia.imarketandroid.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by toannguyen201194 on 29/07/2016.
 */
public class SharedPreferencesUtil {
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
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

    public void init(Context context, String name) {
        mSettings = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        mEditor = mSettings.edit();
    }

    public void save(String nameObject, Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object); // object - instance of MyObject
        mEditor.putString(nameObject, json);
        mEditor.apply();
    }

    public Object getValue(String nameObject, Class aClass) {
        Gson gson = new Gson();
        String json = mSettings.getString(nameObject, "");
        Object object = gson.fromJson(json, aClass);
        return object;
    }

    public void clearSharedPreference() {
        mEditor = mSettings.edit();
        mEditor.clear();
        mEditor.apply();
    }

    public boolean isFirstTimeLaunch() {
        return mSettings.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
    public void setFirstTimeLaunch(boolean isFirstTime) {
        mEditor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        mEditor.commit();
    }
    public void saveString(String key, String value) {
        mEditor.putString(key,value);
        mEditor.apply();
    }
    public String getString(String key) {
        return mSettings.getString(key,Constants.NULL_DATA);
    }
}
