package com.example.sidkathuria14.healthcare.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.sidkathuria14.healthcare.R;
import com.example.sidkathuria14.healthcare.adapter.MedStoreAdapter;
import com.example.sidkathuria14.healthcare.api.StoresApi;
import com.example.sidkathuria14.healthcare.models.stores;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MedStoreActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Retrofit retrofit;
    private RecyclerView rvMedStores;
    private GoogleMap medStoreMap;
    private MedStoreAdapter medStoreAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.medStoreMap);
        mapFragment.getMapAsync(this);

        rvMedStores=findViewById(R.id.rvMedStores);
        rvMedStores.setLayoutManager(new LinearLayoutManager(
                this,LinearLayoutManager.VERTICAL,false
        ));

        medStoreAdapter= new MedStoreAdapter(this,new ArrayList<stores>());

        setContentView(R.layout.activity_media_store);

        String medicineName = getIntent().getStringExtra("medicine_name");

        retrofit = new Retrofit.Builder()
                .baseUrl("https://fierce-forest-33378.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StoresApi storesApi = retrofit.create(StoresApi.class);

        Callback<ArrayList<stores>> callback = new Callback<ArrayList<stores>>() {
            @Override
            public void onResponse(Call<ArrayList<stores>> call, Response<ArrayList<stores>> response) {
                    if(response.body()!=null){
                        addMedStoreMarkers();
                        medStoreAdapter.updateList(response.body());
                    }
            }

            @Override
            public void onFailure(Call<ArrayList<stores>> call, Throwable t) {

            }
        };

        storesApi.getStores().enqueue(callback);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        medStoreMap=googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        medStoreMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        medStoreMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    private void addMedStoreMarkers(){
        // to  add markers of medical stores on map
    }
}
