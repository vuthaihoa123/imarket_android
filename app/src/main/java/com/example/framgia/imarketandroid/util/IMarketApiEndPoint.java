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
    @GET("stores/{storeId}/categories")
    Call<CategoryList> loadCategories(@Path(value = Constants.STORE_ID, encoded = true) int storeId);
    // TODO: 19/08/2016 change notification if have api
    @GET("users/32")
    Call<Session> eventNotification();
    @POST(Constants.LOGIN_PATH)
    Call<UserModel> login(@Body Session session);
    @POST(Constants.REGISTER_PATH)
    Call<UserModel> register(@Body UserModel user);
    @Headers(Constants.CONTENT_TYPE)
    @PATCH(Constants.UPDATE_USERS_PATH)
    Call<UserModel> updateUser(@Path(value = Constants.USER_ID, encoded = true) int idUser
        , @Body UserModel userModel);
    @GET(Constants.GET_FLOORS_PATH)
    Call<ListFloor> getListFloorByCommerceId(
        @Path(value = Constants.COMMERCE_CENTER_ID, encoded = true) int commerceCenterId);
    @GET(Constants.GET_COMMERCE_CENTERS_PATH)
    Call<CommerceList> getListCommerceCenter();
    @GET(Constants.GET_STORES_PATH)
    Call<Stores> getStoreByStoreType(@Path(value = Constants.FLOOR_ID, encoded = true) int idFloor,
                                     @Query(Constants.STORE_TYPE_ID) int storeTypeId);
    @GET(Constants.GET_PRODUCTS_PATH)
    Call<ProductList> getProductInCategory(
        @Path(value = Constants.CATEGORY_ID, encoded = true) int idCategory
    );
    @GET(Constants.GET_EVENTS_PATH)
    Call<EventList> getEventInStore(@Path(value = Constants.STORE_ID, encoded = true) int storeId);
}