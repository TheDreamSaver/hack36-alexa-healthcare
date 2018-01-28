package com.example.sidkathuria14.healthcare.api;

import com.example.sidkathuria14.healthcare.models.stores;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sidkathuria14 on 27/1/18.
 */


//?medicine=Medicine4&latitude=81.012321&longitude=88.305505
public interface StoresApi {
    @GET("nearest?medicine=Desonide&latitude=81.012321&longitude=88.305505")
    Call<stores> getStores(
            @Query("medicine")String medicine,
            @Query("latitude")double latitude,
            @Query("longitude")double longitude
    );

}
