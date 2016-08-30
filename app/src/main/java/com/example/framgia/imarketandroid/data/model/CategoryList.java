package com.example.framgia.imarketandroid.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yue on 25/07/2016.
 */
public class CategoryList {

    @SerializedName("categories")
    private List<Category> mCategories=new ArrayList<>();

    public List<Category> getList(){
        return mCategories;
    }
}
