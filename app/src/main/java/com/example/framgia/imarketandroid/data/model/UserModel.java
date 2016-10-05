package com.example.framgia.imarketandroid.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by toannguyen201194 on 01/08/2016.
 */
public class UserModel {
    @SerializedName("user")
    private Session mSession;
    @SerializedName("errors")
    private String  mErrors;
    public UserModel(Session session) {
        mSession = session;
    }

    public Session getSession() {
        return mSession;
    }

    public void setSession(Session session) {
        mSession = session;
    }

    public String getmErrors() {
        return mErrors;
    }

    public void setmErrors(String mErrors) {
        this.mErrors = mErrors;
    }
}
