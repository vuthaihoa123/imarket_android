package com.example.framgia.imarketandroid.util;

import com.example.framgia.imarketandroid.data.CategoryList;
import com.example.framgia.imarketandroid.models.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yue on 22/07/2016.
 */
public class HttpRequest {
    private static final String BASE_URL = "https://imarket-api.herokuapp.com/api/";
    private static final String LOGIN_URL = "https://imarket-api.herokuapp.com";
    private static HttpRequest sInstance;
    private Retrofit mRetrofit;
    private IMarketApiEndPoint mApi;
    private OnLoadDataListener mListener;

    private HttpRequest() {
    }

    public static HttpRequest getInstance() {
        if (sInstance == null) {
            synchronized (HttpRequest.class) {
                if (sInstance == null) {
                    sInstance = new HttpRequest();
                }
            }
        }
        return sInstance;
    }

    public void setOnLoadDataListener(OnLoadDataListener listener) {
        mListener = listener;
    }

    public void init() {
        mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).
            addConverterFactory(GsonConverterFactory.create()).build();
    }

    public void initLogin() {
        mRetrofit = new Retrofit.Builder().baseUrl(LOGIN_URL).
            addConverterFactory(GsonConverterFactory.create()).build();
    }

    public void loadCategories() {
        mApi = mRetrofit.create(IMarketApiEndPoint.class);
        Call<CategoryList> call = mApi.loadCategories();
        call.enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
                if (mListener != null) {
                    mListener.onLoadDataSuccess(response.body().getList());
                }
            }

            @Override
            public void onFailure(Call<CategoryList> call, Throwable t) {
                if (mListener != null) {
                    mListener.onLoadDataFailure(t.getMessage());
                }
            }
        });
    }

    public void login(Session user) {
        mApi = mRetrofit.create(IMarketApiEndPoint.class);
        Call<Session> call = mApi.login(user);
        call.enqueue(new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                if (mListener != null) {
                    mListener.onLoadDataSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<Session> call, Throwable t) {
                if (mListener != null) {
                    mListener.onLoadDataFailure(t.getMessage());
                }
            }
        });
    }

    public interface OnLoadDataListener {
        void onLoadDataSuccess(Object object);
        void onLoadDataFailure(String message);
    }
}
