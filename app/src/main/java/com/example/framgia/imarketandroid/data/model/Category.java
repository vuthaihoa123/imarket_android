package com.example.framgia.imarketandroid.data.model;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yue on 22/07/2016.
 */
public class Category {

    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;

    public Category() {
    }

    public Category(String id, String name) {
        mId = id;
        mName = name;
    }
}
