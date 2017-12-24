package com.innovellent.curight;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.multidex.MultiDex;

import com.innovellent.curight.utility.SharedPrefService;
import com.pixplicity.easyprefs.library.Prefs;

import static com.innovellent.curight.utility.Constants.USER_ID;

public class CurightApplication extends Application {

    private static CurightApplication instance;

    public CurightApplication() {
        instance = this;
    }

    public static CurightApplication getAppContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the Prefs class
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        SharedPrefService sharedPrefService = SharedPrefService.getInstance();
        sharedPrefService.setLong(USER_ID, 1L);

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

}
