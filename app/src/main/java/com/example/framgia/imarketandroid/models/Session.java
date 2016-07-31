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
    @SerializedName("password_confirmation")
    private String mPasswordConfirm;

    public Session() {
    }

    public Session(String username, String password) {
        mUsername = username;
        mPassword = password;
    }

    public Session(String fullname, String username, String password, String passwordConfirm) {
        mFullname = fullname;
        mUsername = username;
        mPassword = password;
        mPasswordConfirm = passwordConfirm;
    }

    public String getPasswordConfirm() {
        return mPasswordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        mPasswordConfirm = passwordConfirm;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getFullname() {
        return mFullname;
    }

    public void setFullname(String fullname) {
        mFullname = fullname;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
