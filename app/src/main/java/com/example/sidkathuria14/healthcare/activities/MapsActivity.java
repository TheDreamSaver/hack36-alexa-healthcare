package com.example.sidkathuria14.healthcare.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sidkathuria14.healthcare.MainActivity;
import com.example.sidkathuria14.healthcare.PermissionManager;
import com.example.sidkathuria14.healthcare.R;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap mMap;
    private double latitude;
    private double longitude;
    EditText etInput;String medicine;
    private LocationListener locationListener;
    private LocationManager locationManager;
    public static final String TAG="mapsactivity";
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Log.d(TAG, "onSuccess: "+location.getLatitude());
                latitude=location.getLatitude();
                longitude=location.getLongitude();
            }
        });

        locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);

        locationListener= new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mMap.clear();
                latitude=location.getLatitude();
                longitude=location.getLongitude();
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(location.getLatitude(),location.getLongitude()));
                markerOptions.title("Current Location");
                mMap.addMarker(markerOptions);
                Log.d("mapsactivity", "onLocationChanged: " + String.valueOf(location.getLatitude()));
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                        CameraPosition.fromLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),10.0f)
                ));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("mapsactivity", "onStatusChanged: ");
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("mapsacitivity", "onProviderEnabled: ");
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("mapsactivity", "onProviderDisabled: ");
            }
        };

        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                10000,
                10,
                locationListener

        );


        etInput = (EditText)findViewById(R.id.etInput);
        medicine = etInput.getText().toString();

        ((Button)findViewById(R.id.btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapsActivity.this,MedStoreActivity.class);
                i.putExtra("medicine_name",medicine);
                i.putExtra("latitude",latitude);
                i.putExtra("longitude",longitude);
                startActivity(i);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            //User has previously accepted this permission
//            if (ActivityCompat.checkSelfPermission(this,
//                    android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                mMap.setMyLocationEnabled(true);
//            }
//        } else {
//            //Not in api-23, no need to prompt
//            mMap.setMyLocationEnabled(true);
//        }

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(location.getLatitude(),location.getLongitude()));
                markerOptions.title("Current Location");
                mMap.addMarker(markerOptions);
                Log.d("mapsactivity", "onLocationChanged: " + String.valueOf(location.getLatitude()));
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                        CameraPosition.fromLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),10.0f)
                ));
            }
        });

        // Add a marker in Sydney and move the camera



    }
}
