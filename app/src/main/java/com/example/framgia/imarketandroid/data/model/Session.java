package com.example.framgia.imarketandroid.data.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by toannguyen201194 on 29/07/2016.
 */
public class Session extends RealmObject {
    @SerializedName("id")
    private int mId;
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

    public String getUrlImage() {
        return mUrlImage;
    }

    public void setUrlImage(String mUrlImage) {
        this.mUrlImage = mUrlImage;
    }

    public String getBrithday() {
        return mBrithday;
    }

    public void setBrithday(String mBrithday) {
        this.mBrithday = mBrithday;
    }

    public String getNumberPhone() {
        return mNumberPhone;
    }

    public void setNumberPhone(String mNumberPhone) {
        this.mNumberPhone = mNumberPhone;
    }

    public String getAdress() {
        return mAdress;
    }

    public void setAdress(String mAdress) {
        this.mAdress = mAdress;
    }

    public String getPasswordConfirm() {
        return mPasswordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        mPasswordConfirm = passwordConfirm;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
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
