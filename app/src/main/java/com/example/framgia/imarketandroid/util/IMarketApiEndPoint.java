package com.example.framgia.imarketandroid.util;

import com.example.framgia.imarketandroid.data.model.CategoryList;
import com.example.framgia.imarketandroid.data.model.Session;
import com.example.framgia.imarketandroid.data.model.SignupModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by yue on 22/07/2016.
 */
public interface IMarketApiEndPoint {
    @GET("stores/1/categories")
    Call<CategoryList> loadCategories();
    // TODO: 19/08/2016 change notification if have api
    @GET("users/32")
    Call<Session> eventNotification();
    @POST("sessions")
    Call<Session> login(@Body Session session);
    @POST("users")
    Call<SignupModel> register(@Body SignupModel user);
}