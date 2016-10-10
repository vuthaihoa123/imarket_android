package com.example.framgia.imarketandroid.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by phongtran on 07/10/2016.
 */
public class Error {
    @SerializedName("email")
    private String mEmail;
    @SerializedName("phone_number")
    private String mPhoneNumber;
    @SerializedName("password_confirmation")
    private String mConfirmPassword;

    public Error() {
    }

    public Error(String email, String phoneNumber, String confirmPassword) {
        this.mEmail = email;
        this.mPhoneNumber = phoneNumber;
        this.mConfirmPassword = confirmPassword;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.mPhoneNumber = phoneNumber;
    }

    public String getConfirmPassword() {
        return mConfirmPassword;
    }

    public void setmConfirmPassword(String confirmPassword) {
        this.mConfirmPassword = confirmPassword;
    }
}
