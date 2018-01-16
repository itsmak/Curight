package com.innovellent.curight.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.innovellent.curight.R;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.google.firebase.crash.FirebaseCrash.log;

/**
 * Created by Mak on 1/2/2018.
 */

public class SearchLocations extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    TextView tvLocation;
    EditText et_searchloctn;
   GoogleApiClient mGoogleApiClient;
//    int permissionCheck = ContextCompat.checkSelfPermission(SearchLocations.this, Manifest.permission.ACCESS_FINE_LOCATION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_search);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        et_searchloctn = (EditText) findViewById(R.id.et_searchloctn);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(SearchLocations.this)
                .addApi(Places.PLACE_DETECTION_API)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Prefs.putString("location", "Kudlu gate");
                Intent mhome = new Intent(SearchLocations.this,HomeActivity.class);
                startActivity(mhome);

            }
        });
        et_searchloctn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent =
                            new PlaceAutocomplete
                                    .IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());
                Prefs.putString("location", String.valueOf(place.getName()));
                Intent home = new Intent(SearchLocations.this,HomeActivity.class);
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
//        if( mGoogleApiClient != null )
//            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
//        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
//            mGoogleApiClient.disconnect();
//        }
        super.onStop();
    }

        @Override
        protected void onDestroy() {
            super.onDestroy();
        }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}


