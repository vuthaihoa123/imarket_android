package com.example.framgia.imarketandroid.data.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by toannguyen201194 on 29/07/2016.
 */
public class Session extends RealmObject {
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
    @SerializedName("auth_token")
    private String mAuthToken;
    @SerializedName("url_image")
    private String mUrlImage;
    @SerializedName("birthday")
    private String mBrithday;
    @SerializedName("number_phone")
    private String mNumberPhone;
    @SerializedName("adress")
    private String mAdress;

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

    public String getmUrlImage() {
        return mUrlImage;
    }

    public void setmUrlImage(String mUrlImage) {
        this.mUrlImage = mUrlImage;
    }

    public String getmBrithday() {
        return mBrithday;
    }

    public void setmBrithday(String mBrithday) {
        this.mBrithday = mBrithday;
    }

    public String getmNumberPhone() {
        return mNumberPhone;
    }

    public void setmNumberPhone(String mNumberPhone) {
        this.mNumberPhone = mNumberPhone;
    }

    public String getmAdress() {
        return mAdress;
    }

    public void setmAdress(String mAdress) {
        this.mAdress = mAdress;
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

    public String getmAuthToken() {
        return mAuthToken;
    }

    public void setmAuthToken(String mAuthToken) {
        this.mAuthToken = mAuthToken;
    }
}
