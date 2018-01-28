package com.example.sidkathuria14.healthcare;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.sidkathuria14.healthcare.activities.MapsActivity;
import com.example.sidkathuria14.healthcare.activities.MedSchedulesViewActivity;

import java.security.Permission;

public class MainActivity extends AppCompatActivity {
    LinearLayout medRem,medNearMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        medNearMe=findViewById(R.id.medNearMe);
        medRem=findViewById(R.id.medRem);

        PermissionManager.askForPermission(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET},
                new PermissionManager.OnPermissionResultListener() {
                    @Override
                    public void onGranted(String permission) {

                    }

                    @Override
                    public void onDenied(String permission) {

                    }
                }
        );

        medRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MedSchedulesViewActivity.class));
            }
        });

        medNearMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MapsActivity.class));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
}
