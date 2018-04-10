package com.innovellent.curight.activities;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.innovellent.curight.GPS_Services;
import com.innovellent.curight.R;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.google.firebase.crash.FirebaseCrash.log;

/**
 * Created by Mak on 1/2/2018.
 */

public class SearchLocations extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "Curight";
    private static final int PERMISSION_REQUEST_CODE = 200;
    TextView tvLocation,tv_recent1,tv_recent2,tv_recent3,tv_recent4,tv_recent5;
    EditText et_searchloctn;
    ImageView iv_location_back;
    RelativeLayout rl_location;
    double latitude, longitude;
    String street1, street2, street3;
    double final_latitude;
    double final_longitude;
    private GoogleApiClient mGoogleApiClient;

    //   BroadcastReceiver broadcastreceiver;
//    int permissionCheck = ContextCompat.checkSelfPermission(SearchLocations.this, Manifest.permission.ACCESS_FINE_LOCATION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_search);
        tvLocation = (TextView) findViewById(R.id.tvLocation);

        tv_recent1 = (TextView) findViewById(R.id.tv_recent1);
        tv_recent2 = (TextView) findViewById(R.id.tv_recent2);
        tv_recent3 = (TextView) findViewById(R.id.tv_recent3);
        tv_recent4 = (TextView) findViewById(R.id.tv_recent4);
        tv_recent5 = (TextView) findViewById(R.id.tv_recent5);

        et_searchloctn = (EditText) findViewById(R.id.et_searchloctn);
        iv_location_back = (ImageView) findViewById(R.id.iv_location_back);
        rl_location = (RelativeLayout) findViewById(R.id.rl_location);
        requestPermission();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(SearchLocations.this)
                .addApi(Places.PLACE_DETECTION_API)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        String recent1 = Prefs.getString("recent1","");
        String recent2 = Prefs.getString("recent2","");
        String recent3 = Prefs.getString("recent3","");
        String recent4 = Prefs.getString("recent4","");
        String recent5 = Prefs.getString("recent5","");
        Log.d(TAG,"recent r1:"+recent1);
        Log.d(TAG,"recent r2:"+recent2);
        Log.d(TAG,"recent r3:"+recent3);
        Log.d(TAG,"recent r4:"+recent4);
        Log.d(TAG,"recent r5:"+recent5);
        tv_recent1.setText(Prefs.getString("recent1",""));
        tv_recent2.setText(Prefs.getString("recent2",""));
        tv_recent3.setText(Prefs.getString("recent3",""));
        tv_recent4.setText(Prefs.getString("recent4",""));
        tv_recent5.setText(Prefs.getString("recent5",""));

        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d(TAG,"location cord lat:"+latitude);
//                Log.d(TAG,"location cord long:"+longitude);
//                Log.d(TAG,"location cord 1:"+street1);
//                Log.d(TAG,"location cord 2:"+street2);
//                Log.d(TAG,"location cord 3:"+street3);
//                if(street1==null)
//                {
//                    if(street2==null){
//                        if(street3==null){
//                            Toast.makeText(SearchLocations.this,"Cant find Location!! Please Search",Toast.LENGTH_SHORT).show();
//                        }else {
//                            Prefs.putString("location",street3);
//                            tvLocation.setText(street3);
//                        }
//                    }else {
//                        Prefs.putString("location",street2);
//                        tvLocation.setText(street2);
//                    }
//
//                }else {
//                    Prefs.putString("location",street1);
//                    tvLocation.setText(street1);
//                }
            }
        });
        iv_location_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent stp = new Intent(getApplicationContext(),GPS_Services.class);
//                stopService(stp);
                Intent mhome = new Intent(SearchLocations.this, HomeActivity.class);
                startActivity(mhome);
                finish();
            }
        });
        rl_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "final latitude : " + final_latitude);
                Log.d(TAG, "final latitude : " + final_longitude);
                if(final_latitude==0.0)
                {
                  Toast.makeText(SearchLocations.this,"Please Switch on GPS and try again",Toast.LENGTH_SHORT).show();
                    Intent mhome = new Intent(SearchLocations.this, HomeActivity.class);
                    startActivity(mhome);
                    finish();
                }else {
                    Geocoder geoCoder = new Geocoder(SearchLocations.this);
                    List<android.location.Address> matches = null;
                    try {
                        matches = geoCoder.getFromLocation(final_latitude, final_longitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    android.location.Address bestMatch = (matches.isEmpty() ? null : matches.get(0));

                    Log.d(TAG, "final street4 : " + bestMatch.getSubLocality());
                    Log.d(TAG, "final street5 : " + bestMatch.getAdminArea());
                    Log.d(TAG, "final street5 : " + bestMatch.getPremises());
                    Log.d(TAG, "final street5 : " + bestMatch.getAdminArea());
                    Prefs.putString("location", bestMatch.getSubLocality());

                    tvLocation.setText(bestMatch.getSubLocality());
                    Intent mhome = new Intent(SearchLocations.this, HomeActivity.class);
                    startActivity(mhome);
                    finish();
                }

            }
        });
//        if(!runtime_purmission())
//        {
//            configure_button();
//        }


        et_searchloctn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    AutocompleteFilter typeFilter = new AutocompleteFilter.Builder().
                            setTypeFilter(Place.TYPE_COUNTRY).setCountry("IN").build();
                    Intent intent =
                            new PlaceAutocomplete
                                    .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .setFilter(typeFilter)
                                    .build(SearchLocations.this);

                    startActivityForResult(intent, 1);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });


    }

//    private Boolean runtime_purmission() {
//
//        if(Build.VERSION.SDK_INT >=23 && ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
//        {
//            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
//            return true;
//        }
//            return false;
//    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onBackPressed() {
        Intent mhome = new Intent(SearchLocations.this, HomeActivity.class);
        startActivity(mhome);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //       setUpMapIfNeeded();
        mGoogleApiClient.connect();
//        if(broadcastreceiver==null)
//        {
//            broadcastreceiver = new BroadcastReceiver() {
//                @Override
//                public void onReceive(Context context, Intent intent) {
//                    latitude = (double) intent.getExtras().get("latitude");
//                    longitude = (double) intent.getExtras().get("latitude");
//                    Geocoder geoCoder = new Geocoder(SearchLocations.this);
//                List<android.location.Address> matches = null;
//                try {
//                    matches = geoCoder.getFromLocation(latitude, longitude, 1);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                android.location.Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
//                    street1 = bestMatch.getLocality();
//                    street2 = bestMatch.getThoroughfare();
//                    street3 = bestMatch.getPostalCode();
//                }
//            };
//        }
//        registerReceiver(broadcastreceiver,new IntentFilter("Location_update"));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());
                Prefs.putString("location", String.valueOf(place.getName()));
                //Prefs.putString("recent1",String.valueOf(place.getName()));
                String recent1 = Prefs.getString("recent1","");
                String recent2 = Prefs.getString("recent2","");
                String recent3 = Prefs.getString("recent3","");
                String recent4 = Prefs.getString("recent4","");
                String recent5 = Prefs.getString("recent5","");
                Log.d(TAG,"recent s1:"+recent1);
                Log.d(TAG,"recent s2:"+recent2);
                Log.d(TAG,"recent s3:"+recent3);
                Log.d(TAG,"recent s2:"+recent4);
                Log.d(TAG,"recent s2:"+recent5);
                if (recent1.equalsIgnoreCase(""))
                {
                    Prefs.putString("recent1",String.valueOf(place.getName()));
                    recent1 = Prefs.getString("recent1","");


                }else if(recent2.equalsIgnoreCase("")){
                    Prefs.putString("recent1",String.valueOf(place.getName()));
                    Prefs.putString("recent2",recent1);
                    recent2 = Prefs.getString("recent2","");
                   // tv_recent2.setText(recent2);
                }else if(recent3.equalsIgnoreCase(""))
                {
                    Prefs.putString("recent2",Prefs.getString("recent1",""));
                    Prefs.putString("recent1",String.valueOf(place.getName()));
                    Prefs.putString("recent3",recent2);
                    recent3 = Prefs.getString("recent3","");
                }else if(recent4.equalsIgnoreCase(""))
                {
                    Prefs.putString("recent3",Prefs.getString("recent2",""));
                    Prefs.putString("recent2",Prefs.getString("recent1",""));
                    Prefs.putString("recent1",String.valueOf(place.getName()));

                    Prefs.putString("recent4",recent3);
                    recent4 = Prefs.getString("recent4","");
                }else if(recent5.equalsIgnoreCase(""))
                {
                    Prefs.putString("recent4",Prefs.getString("recent3",""));
                    Prefs.putString("recent3",Prefs.getString("recent2",""));
                    Prefs.putString("recent2",Prefs.getString("recent1",""));
                    Prefs.putString("recent1",String.valueOf(place.getName()));

                    Prefs.putString("recent5",recent4);
                    recent5 = Prefs.getString("recent5","");
                }else {
                    Prefs.putString("recent5",Prefs.getString("recent4",""));
                    Prefs.putString("recent4",Prefs.getString("recent3",""));
                    Prefs.putString("recent3",Prefs.getString("recent2",""));
                    Prefs.putString("recent2",Prefs.getString("recent1",""));

                    Prefs.putString("recent1",String.valueOf(place.getName()));
                }
                Intent home = new Intent(SearchLocations.this, HomeActivity.class);
                startActivity(home);
//                ((TextView) findViewById(R.id.searched_address))
//                        .setText(place.getName()+",\n"+
//                                place.getAddress() +"\n" + place.getPhoneNumber());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "Location services connected.");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }else{
            requestPermission();
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            // Blank for a moment...
        }
        else {
            handleNewLocation(location);
        };
    }
    private void handleNewLocation(Location location) {
        Log.d(TAG, "my new lat :"+String.valueOf(location.getLatitude()));
        Log.d(TAG, "my new long :"+String.valueOf(location.getLongitude()));
        final_latitude = location.getLatitude();
        final_longitude = location.getLongitude();
            //    Toast.makeText(SearchLocations.this,"lat : "+String.valueOf(location.getLatitude())+" and longitude ::"+String.valueOf(location.getLongitude()),Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void requestPermission() {

        ActivityCompat.requestPermissions(SearchLocations.this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){

            case 200:


                break;


        }
    }
}


