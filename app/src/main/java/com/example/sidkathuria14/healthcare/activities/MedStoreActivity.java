package com.example.sidkathuria14.healthcare.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sidkathuria14.healthcare.R;
import com.example.sidkathuria14.healthcare.adapter.MedStoreAdapter;
import com.example.sidkathuria14.healthcare.api.StoresApi;
import com.example.sidkathuria14.healthcare.models.shops;
import com.example.sidkathuria14.healthcare.models.stores;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
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
    private  static final String TAG = "medstoreactivity";
    LocationManager locMan;LocationListener locLis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_media_store);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.medStoreMap);
        mapFragment.getMapAsync(this);

        rvMedStores=findViewById(R.id.rvMedStores);
        rvMedStores.setLayoutManager(new LinearLayoutManager(
                this,LinearLayoutManager.VERTICAL,false
        ));

        medStoreAdapter= new MedStoreAdapter(this,new ArrayList<shops>());
        rvMedStores.setAdapter(medStoreAdapter);


        String medicineName = getIntent().getStringExtra("medicine_name");


        locMan = (LocationManager) getSystemService(LOCATION_SERVICE);
        locLis = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                String lat,lng;
                Log.d(TAG, "lat: " + location.getLatitude());
                Log.d(TAG, "lng: " + location.getLongitude());
                Log.d(TAG, "prov: " + location.getProvider());
                Log.d(TAG, "accuracy: " + location.getAccuracy());
                Log.d(TAG, "alt: " + location.getAltitude());
                Log.d(TAG, "speed: " + location.getSpeed());
                lat = String.valueOf(location.getLatitude());
                lng = String.valueOf(location.getLongitude());
//                latlng = "("+lat + "," + lng +")";
//                medStoreMap.addMarker(new MarkerOptions().position(new LatLng
//                        (location.getLatitude(),location.getLongitude())));

medStoreMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(new
        LatLng(location.getLatitude(),location.getLongitude()),10.0f)));

//                mapAddress = " http://maps.google.com/maps?q=loc:" + lat + "," + lng;


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) &&

                (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(this,
                    new String[] {
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, 234);
        } else {
            startLocationTracking();
        }


    }

    @SuppressWarnings("MissingPermission")
    void startLocationTracking () {

        locMan.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                10000,
                10,
                locLis
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (permissions[0].equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationTracking();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        medStoreMap=googleMap;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //User has previously accepted this permission
            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                medStoreMap.setMyLocationEnabled(true);
            }
        } else {
            //Not in api-23, no need to prompt
            medStoreMap.setMyLocationEnabled(true);
        }
        medStoreMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.retro));
//medStoreMap.moveCamera(CameraUpdateFactory.newLatLng().zoomBy(10.0f));
        retrofitInit();

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        medStoreMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        medStoreMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

public void retrofitInit(){
    retrofit = new Retrofit.Builder()
            .baseUrl("https://fierce-forest-33378.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    StoresApi storesApi = retrofit.create(StoresApi.class);

    Callback<stores> callback = new Callback<stores>() {
        @Override
        public void onResponse(Call<stores> call, Response<stores> response) {
            if(response.body()!=null){
                addMedStoreMarkers();
                medStoreAdapter.updateList(response.body().getShops());
            }
            Log.d(TAG, "onResponse:" +  response.body().getShops().get(0).getName()  );
            for(int i=0;i<response.body().getShops().size();i++){
                Log.d(TAG, "onResponse: " + Integer.parseInt(String.valueOf(i)));
                medStoreMap.addMarker(new MarkerOptions().position(new LatLng(
                        response.body().getShops().get(i).getLat(),
                        response.body().getShops().get(i).getLon()
                )));
            }
        }

        @Override
        public void onFailure(Call<stores> call, Throwable t) {
            Log.d(TAG, "onFailure: ");
        }
    };

    storesApi.getStores().enqueue(callback);

}

    private void addMedStoreMarkers(){
        // to  add markers of medical stores on map
    }
}
