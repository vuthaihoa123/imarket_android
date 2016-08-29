package com.example.framgia.imarketandroid.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toannguyen201194 on 01/08/2016.
 */
public class Errors {

    @SerializedName("email")
    private List<String> mEmail = new ArrayList<>();

    public List<String> getEmail() {
        return mEmail;
    }

}
