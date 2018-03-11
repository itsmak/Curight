package com.innovellent.curight;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

/**
 * Created by Mak on 3/2/2018.
 */

public class GPS_Services extends Service {

    private LocationListener listner;
    private LocationManager locationmanager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        listner = new LocationListener(){

            @Override
            public void onLocationChanged(Location location) {
                Intent i = new Intent("Location_update");
                i.putExtra("latitude",location.getLatitude());
                i.putExtra("longitude",location.getLatitude());
                sendBroadcast(i);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent in = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
            }
        };

        locationmanager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,0,listner);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationmanager != null)
        {
            locationmanager.removeUpdates(listner);
        }
    }
}
