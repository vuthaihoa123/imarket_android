package com.example.framgia.imarketandroid.util;

import com.example.framgia.imarketandroid.data.CategoryList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by yue on 22/07/2016.
 */
public interface IMarketApiEndPoint {

    @GET("stores/1/categories")
    Call<CategoryList> loadCategories();
}
