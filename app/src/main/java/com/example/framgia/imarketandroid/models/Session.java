package com.example.framgia.imarketandroid.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by toannguyen201194 on 29/07/2016.
 */
public class Session {
    @SerializedName("id")
    private String mId;
    @SerializedName("email")
    private String mUsername;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("full_name")
    private String mFullname;
    @SerializedName("errors")
    private String mError;

    public Session() {
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public void setFullname(String fullname) {
        mFullname = fullname;
    }

    public void setError(String error) {
        mError = error;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Session(String username, String password) {
        mUsername = username;
        mPassword = password;
    }

    public String getError() {
        return mError;
    }

    public String getFullname() {
        return mFullname;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }
}
