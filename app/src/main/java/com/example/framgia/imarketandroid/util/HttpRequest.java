package com.example.framgia.imarketandroid.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.example.framgia.imarketandroid.ImarketApplication;
import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.CategoryList;
import com.example.framgia.imarketandroid.data.model.CommerceList;
import com.example.framgia.imarketandroid.data.model.EventList;
import com.example.framgia.imarketandroid.data.model.ListFloor;
import com.example.framgia.imarketandroid.data.model.ProductList;
import com.example.framgia.imarketandroid.data.model.Session;
import com.example.framgia.imarketandroid.data.model.StoreTypeList;
import com.example.framgia.imarketandroid.data.model.Stores;
import com.example.framgia.imarketandroid.data.model.UserModel;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.IllegalFormatException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yue on 22/07/2016.
 */
public class HttpRequest {
    private static final String BASE_URL = Constants.BASE_URL;
    private static HttpRequest sInstance;
    private static OkHttpClient mClient;
    private Retrofit mRetrofit;
    private IMarketApiEndPoint mApi;
    private OnLoadDataListener mListener;
    private ProgressDialog mProgressDialog;
    private static Context mActivity;

    private HttpRequest() {
    }

    public static HttpRequest getInstance(Context activity) {
        mActivity = activity;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();
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
            addConverterFactory(GsonConverterFactory.create()).client(mClient).build();
    }

    public void initProgressDialog(Context context) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(context.getString(R.string.progressdialog));
        mProgressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    public void initAuthToken(final String auth) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).
            addConverterFactory(GsonConverterFactory.create()).client(mClient).build();
        if (auth != null) {
            httpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Authorization", auth)
                        .method(original.method(), original.body());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }
        mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).
            addConverterFactory(GsonConverterFactory.create()).client(httpClientBuilder.build())
            .build();
    }

    public void loadCategories(int storeId) {
        mApi = mRetrofit.create(IMarketApiEndPoint.class);
        Call<CategoryList> call = mApi.loadCategories(storeId);
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

    public void login(Session session) {
        mApi = mRetrofit.create(IMarketApiEndPoint.class);
        Call<UserModel> call = mApi.login(session);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (mListener != null) {
                    mListener.onLoadDataSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                if (mListener != null) {
                    mListener.onLoadDataFailure(t.getMessage());
                }
            }
        });
    }

    public void register(UserModel user) {
        mApi = mRetrofit.create(IMarketApiEndPoint.class);
        Call<UserModel> callRegister = mApi.register(user);
        callRegister.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful() && mListener != null) {
                    mListener.onLoadDataSuccess(response.body());
                } else {
                    UserModel signupModel = null;
                    try {
                        signupModel =
                            new Gson().fromJson(response.errorBody().string(), UserModel.class);
                        mListener.onLoadDataSuccess(signupModel);
                    } catch (JsonSyntaxException exception) {
                        mListener.onLoadDataFailure(mActivity.getString(R.string.duplicate_email));
                        Flog.toastLong(mActivity, R.string.duplicate_email);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                if (mListener != null) {
                    mListener.onLoadDataFailure(t.getMessage());
                }
            }
        });
    }

    public void updateUser(int idUser, UserModel userUpdate) {
        mApi = mRetrofit.create(IMarketApiEndPoint.class);
        Call<UserModel> callUpdateUser = mApi.updateUser(idUser, userUpdate);
        callUpdateUser.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful() && mListener != null) {
                    mListener.onLoadDataSuccess(response.body());
                } else {
                    UserModel userUpdate = null;
                    try {
                        userUpdate =
                            new Gson().fromJson(response.errorBody().string(), UserModel.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mListener.onLoadDataSuccess(userUpdate);
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                if (mListener != null) {
                    mListener.onLoadDataFailure(t.getMessage());
                }
            }
        });
    }

    public void requestEventNocation() {
        mApi = mRetrofit.create(IMarketApiEndPoint.class);
        Call<Session> callEventNotification = mApi.eventNotification();
        callEventNotification.enqueue(new Callback<Session>() {
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

    public void loadListFloor(int commerceId) {
        mApi = mRetrofit.create(IMarketApiEndPoint.class);
        Call<ListFloor> request = mApi.getListFloorByCommerceId(commerceId);
        request.enqueue(new Callback<ListFloor>() {
            @Override
            public void onResponse(Call<ListFloor> call, Response<ListFloor> response) {
                if (mListener != null) {
                    mListener.onLoadDataSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<ListFloor> call, Throwable t) {
                if (mListener != null) {
                    mListener.onLoadDataFailure(t.getMessage());
                }
            }
        });
    }

    public void loadListStoreType(int commerceId) {
        mApi = mRetrofit.create(IMarketApiEndPoint.class);
        Call<StoreTypeList> request = mApi.getListStoreTypeByCommerceId(commerceId);
        request.enqueue(new Callback<StoreTypeList>() {
            @Override
            public void onResponse(Call<StoreTypeList> call, Response<StoreTypeList> response) {
                if (mListener != null) {
                    mListener.onLoadDataSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<StoreTypeList> call, Throwable t) {
                if (mListener != null) {
                    mListener.onLoadDataFailure(t.getMessage());
                }
            }
        });
    }

    public void loadListCommerce() {
        mApi = mRetrofit.create(IMarketApiEndPoint.class);
        Call<CommerceList> request = mApi.getListCommerceCenter();
        request.enqueue(new Callback<CommerceList>() {
            @Override
            public void onResponse(Call<CommerceList> call, Response<CommerceList> response) {
                if (mListener != null) {
                    mListener.onLoadDataSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<CommerceList> call, Throwable t) {
                if (mListener != null) {
                    mListener.onLoadDataFailure(t.getMessage());
                }
            }
        });
    }

    public void getStore(int idFloor, int storeTypeId) {
        mApi = mRetrofit.create(IMarketApiEndPoint.class);
        Call<Stores> repuestServer = mApi.getStoreByStoreType(idFloor, storeTypeId);
        repuestServer.enqueue(new Callback<Stores>() {
            @Override
            public void onResponse(Call<Stores> call, Response<Stores> response) {
                if (mListener != null) {
                    mListener.onLoadDataSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<Stores> call, Throwable t) {
                if (mListener != null) {
                    mListener.onLoadDataFailure(t.getMessage());
                }
            }
        });
    }

    public void getProduct(int idCategory) {
        mApi = mRetrofit.create(IMarketApiEndPoint.class);
        Call<ProductList> repuestServer = mApi.getProductInCategory(idCategory);
        repuestServer.enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                if (mListener != null) {
                    mListener.onLoadDataSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
                if (mListener != null) {
                    mListener.onLoadDataFailure(t.getMessage());
                }
            }
        });
    }

    public void getEvents(int idStore) {
        mApi = mRetrofit.create(IMarketApiEndPoint.class);
        Call<EventList> requesServer = mApi.getEventInStore(idStore);
        requesServer.enqueue(new Callback<EventList>() {
            @Override
            public void onResponse(Call<EventList> call, Response<EventList> response) {
                if (mListener != null) {
                    mListener.onLoadDataSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<EventList> call, Throwable t) {
                if (mListener != null) {
                    mListener.onLoadDataFailure(t.getMessage());
                }
            }
        });
    }
}
