package com.example.sidkathuria14.healthcare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sidkathuria14.healthcare.api.StoresApi;
import com.example.sidkathuria14.healthcare.models.stores;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MedStoreActivity extends AppCompatActivity {
Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_store);

        retrofit = new Retrofit.Builder()
                .baseUrl()
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StoresApi storesApi = retrofit.create(StoresApi.class);

        Callback<stores> callback = new Callback<stores>() {
            @Override
            public void onResponse(Call<stores> call, Response<stores> response) {

            }

            @Override
            public void onFailure(Call<stores> call, Throwable t) {

            }
        };

        storesApi.getStores().enqueue(callback);

    }
}
