package com.hillarie.havatest.rides.detail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.hillarie.havatest.R;
import com.hillarie.havatest.common.CustomerWindowInfo;
import com.hillarie.havatest.common.PicassoImage;
import com.hillarie.havatest.common.directionhelpers.FetchURL;
import com.hillarie.havatest.common.directionhelpers.TaskLoadedCallback;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hillarie Kalya on 07/14/2021.
 * Copyright (c) 2021 All rights reserved.
 */
public class RideDetailActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    GoogleMap mMap;
    MarkerOptions placePickup, placeDestination;
    Marker mCurrentLocation, mDestination;

    Polyline currentPolyline;


    int duration ,cost;

    Double pickup_lat,pickup_lng,dropoff_lat,dropoff_lng,distance;
    String status,request_date,pickup_location,dropoff_location,pickup_date,dropoff_date,type,driver_name,driver_pic,car_make,car_model,car_number,car_pic,duration_unit,distance_unit,cost_unit;

CircleImageView ivAsset,ivDriver;
    TextView tvDriverName, tvPickup, tvDropOff,tvPickUpDate,tvCost,tvDuration,tvPlate,tvDistance;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);

        Intent ii = getIntent();
        request_date = ii.getStringExtra("request_date");
        pickup_location = ii.getStringExtra("pickup_location");
        dropoff_location = ii.getStringExtra("dropoff_location");
        pickup_date = ii.getStringExtra("pickup_date");
        dropoff_date = ii.getStringExtra("dropoff_date");
        type = ii.getStringExtra("type");
        driver_name = ii.getStringExtra("driver_name");
        driver_pic = ii.getStringExtra("driver_pic");
        car_make = ii.getStringExtra("car_make");
        car_model = ii.getStringExtra("car_model");
        car_number = ii.getStringExtra("car_number");
        car_pic = ii.getStringExtra("car_pic");
        duration_unit = ii.getStringExtra("duration_unit");
        distance_unit = ii.getStringExtra("distance_unit");
        cost_unit = ii.getStringExtra("cost_unit");

        pickup_lat = ii.getDoubleExtra("pickup_lat",0.00);
        pickup_lng = ii.getDoubleExtra("pickup_lng",0.00);
        dropoff_lat = ii.getDoubleExtra("dropoff_lat",0.00);
        dropoff_lng = ii.getDoubleExtra("dropoff_lng",0.00);
        distance= ii.getDoubleExtra("distance",0.00);
        duration = ii.getIntExtra("duration",0);
        cost = ii.getIntExtra("cost",0);


        tvDriverName= findViewById(R.id.tv_driver);
        tvPickup= findViewById(R.id.tv_pickup);
        tvDropOff= findViewById(R.id.tv_dropOff);
        tvPickUpDate= findViewById(R.id.tv_timestamp);
        tvCost= findViewById(R.id.tv_cost);
        tvDuration= findViewById(R.id.tv_duration);
        tvPlate= findViewById(R.id.tv_assetName);
        tvDistance= findViewById(R.id.tv_distance);
        ivAsset= findViewById(R.id.iv_asset);
        ivDriver= findViewById(R.id.iv_driver);


        PicassoImage.downloadImage(this, driver_pic, ivDriver);
        PicassoImage.downloadImage(this, car_pic, ivAsset);
        tvDriverName.setText(driver_name);
        tvPickup.setText(pickup_location);
        tvDropOff.setText(dropoff_location);
        tvPickUpDate.setText(pickup_date);
        tvCost.setText(cost+" "+cost_unit);
        tvDuration.setText(duration+" "+duration_unit);
        tvPlate.setText(car_number);
        tvDistance.setText(distance+" "+distance_unit);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(this);

      // mapView();


    }

    private void mapView() {

       mMap.clear();
        placePickup = new MarkerOptions()
                .title(pickup_location)
                .snippet(pickup_date)
                .position(new LatLng(pickup_lat, pickup_lng))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_start));

        placeDestination = new MarkerOptions()
                .title(dropoff_location)
                .snippet(dropoff_date)
                .position(new LatLng(dropoff_lat, dropoff_lng))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_end));



        new FetchURL(RideDetailActivity.this).execute(getUrl(placePickup.getPosition(), placeDestination.getPosition(), "driving"), "driving");
  //need key to be enabled in order to get pollyline here for pickup n destination
     //   mCurrentLocation = mMap.addMarker(placePickup);
     //   mDestination = mMap.addMarker(placeDestination);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(placePickup.getPosition(), 15.0f));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


        Log.d("mylog", "Added Markers");

        try {
            boolean isSuccess = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(this, R.raw.app_map)
            );
            if (!isSuccess)
                Log.d("Error", "Unable to load the map");

        } catch (Resources.NotFoundException E) {
            E.printStackTrace();
        }

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(false);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.setInfoWindowAdapter(new CustomerWindowInfo(this));


    }


    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_map_key);
        Log.d("TRANS",url);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
            currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);

    }


}
