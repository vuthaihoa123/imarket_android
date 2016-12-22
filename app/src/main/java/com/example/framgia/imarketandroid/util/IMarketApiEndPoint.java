package com.example.framgia.imarketandroid.util;

import com.example.framgia.imarketandroid.data.model.CategoryList;
import com.example.framgia.imarketandroid.data.model.CommerceList;
import com.example.framgia.imarketandroid.data.model.Event;
import com.example.framgia.imarketandroid.data.model.EventList;
import com.example.framgia.imarketandroid.data.model.Floor;
import com.example.framgia.imarketandroid.data.model.ListFloor;
import com.example.framgia.imarketandroid.data.model.ListItemProduct;
import com.example.framgia.imarketandroid.data.model.ProductList;
import com.example.framgia.imarketandroid.data.model.Session;
import com.example.framgia.imarketandroid.data.model.Store;
import com.example.framgia.imarketandroid.data.model.StoreTypeList;
import com.example.framgia.imarketandroid.data.model.Stores;
import com.example.framgia.imarketandroid.data.model.UserModel;
import com.google.android.gms.analytics.ecommerce.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by yue on 22/07/2016.
 */
public interface IMarketApiEndPoint {
    @GET(Constants.PathAPI.LOAD_CATEGORIES_PATH)
    Call<CategoryList> loadCategories(@Path(value = Constants.ParamAPI.STORE_ID, encoded = true)
                                      int storeId);
    // TODO: 19/08/2016 change notification if have api
    @GET("users/32")
    Call<Session> eventNotification();
    @POST(Constants.PathAPI.LOGIN_PATH)
    Call<UserModel> login(@Body Session session);
    @POST(Constants.PathAPI.REGISTER_PATH)
    Call<UserModel> register(@Body UserModel user);
    @Headers(Constants.PathAPI.CONTENT_TYPE)
    @PATCH(Constants.PathAPI.UPDATE_USERS_PATH)
    Call<UserModel> updateUser(@Path(value = Constants.ParamAPI.USER_ID, encoded = true) int idUser
        , @Body UserModel userModel);
    @GET(Constants.PathAPI.GET_FLOORS_PATH)
    Call<ListFloor> getListFloorByCommerceId(
        @Path(value = Constants.ParamAPI.COMMERCE_CENTER_ID, encoded = true) int commerceCenterId);
    @GET(Constants.PathAPI.GET_COMMERCE_CENTERS_PATH)
    Call<CommerceList> getListCommerceCenter();
    @GET(Constants.PathAPI.GET_STORES_PATH)
    Call<Stores> getStoreByStoreType(@Path(value = Constants.ParamAPI.FLOOR_ID, encoded = true)
                                     int idFloor,
                                     @Query(Constants.ParamAPI.STORE_TYPE_ID) int storeTypeId);
    @GET(Constants.PathAPI.GET_PRODUCTS_PATH)
    Call<ProductList> getProductInCategory(
        @Path(value = Constants.ParamAPI.CATEGORY_ID, encoded = true) int idCategory
    );
    @GET(Constants.PathAPI.GET_EVENTS_PATH)
    Call<EventList> getEventInStore(@Path(value = Constants.ParamAPI.STORE_ID, encoded = true)
                                    int storeId);
    @GET(Constants.PathAPI.GET_STORE_TYPE_PATH)
    Call<StoreTypeList> getListStoreTypeByCommerceId(
        @Path(value = Constants.ParamAPI.COMMERCE_CENTER_ID, encoded = true) int commerceCenterId);
}