package com.example.sidkathuria14.healthcare.api;

import com.example.sidkathuria14.healthcare.models.stores;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sidkathuria14 on 27/1/18.
 */

public interface StoresApi {
    @GET("")
    Call<ArrayList<stores>> getStores();

}
