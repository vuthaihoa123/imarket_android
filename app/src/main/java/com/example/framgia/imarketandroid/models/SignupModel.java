package com.example.framgia.imarketandroid.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by toannguyen201194 on 01/08/2016.
 */
public class SignupModel {
    @SerializedName("user")
    private Session mSession;
    @SerializedName("errors")
    private Errors mErrors;
    public SignupModel(Session session) {
        mSession = session;
    }

    public Session getSession() {
        return mSession;
    }

    public void setSession(Session session) {
        mSession = session;
    }

    public Errors getErrors() {
        return mErrors;
    }

    public void setErrors(Errors errors) {
        mErrors = errors;
    }
}
