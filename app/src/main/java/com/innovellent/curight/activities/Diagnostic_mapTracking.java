package com.innovellent.curight.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.innovellent.curight.R;
import com.innovellent.curight.utility.GeocodingLocation;

import java.util.ArrayList;

/**
 * Created by Mak on 3/13/2018.
 */

public class Diagnostic_mapTracking extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = ".iken";
    ProgressDialog progressDialog;
    String lat_latitude, lan_longitude;
    double final_latitude;
    ImageView iv_dcentre_back;
    double final_longitude;
    RelativeLayout rl_direction_text;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnostic_centre_mapactivity);
        iv_dcentre_back = (ImageView)findViewById(R.id.iv_dcentre_back);
        rl_direction_text = (RelativeLayout) findViewById(R.id.rl_direction_text);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.diagnostic_gmap);
        mapFragment.getMapAsync(this);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(Diagnostic_mapTracking.this)
                .addApi(Places.PLACE_DETECTION_API)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        // callBusTrackingApi();

        iv_dcentre_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Diagnostic_mapTracking.super.onBackPressed();
            }
        });
        rl_direction_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double latitude = 12.456754;
                double longitude = -77.54436;
                String label = "Diagnostic Centre";
                String uriBegin = "geo:" + latitude + "," + longitude;
                String query = latitude + "," + longitude + "(" + label + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uri = Uri.parse(uriString);
                Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                startActivity(mapIntent);
            }
        });
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "Location services connected.");
        @SuppressLint("MissingPermission") Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            // Blank for a moment...
        } else {
            handleNewLocation(location);
        }
    }
    @SuppressLint("MissingPermission")
    private void handleNewLocation(Location location) {
        Log.d(TAG, "my new lat :" + String.valueOf(location.getLatitude()));
        Log.d(TAG, "my new long :" + String.valueOf(location.getLongitude()));
        final_latitude = location.getLatitude();
        final_longitude = location.getLongitude();
        GeocodingLocation locationAddress = new GeocodingLocation();
        locationAddress.getAddressFromLocation("BTM Layout",
                getApplicationContext(), new GeocoderHandler());
        LatLng mylocation = new LatLng(final_latitude, final_longitude);
        LatLng buslocation = new LatLng(12.456754, 77.54436);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.addPolyline(new PolylineOptions().add(mylocation,buslocation).width(5).color(Color.BLUE).geodesic(true));
        mMap.addMarker(new MarkerOptions().position(buslocation).title("Diagnostic Centre").icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(buslocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        //    Toast.makeText(SearchLocations.this,"lat : "+String.valueOf(location.getLatitude())+" and longitude ::"+String.valueOf(location.getLongitude()),Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            String locationlattitude="";
            String locationLongitude = "";
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");

                    Log.d(TAG,"resultant lat::"+bundle.toString());
//                    locationlattitude = bundle.getString("lattitude");
//                    locationLongitude = bundle.getString("longitude");
                    break;
                default:
                    locationAddress = null;
            }
            Log.d(TAG,"resultant address::"+locationAddress);

            Log.d(TAG,"resultant long::"+locationLongitude);
         //   latLongTV.setText(locationAddress);
        }
    }

}
