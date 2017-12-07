package com.innovellent.curight;

import android.app.Application;

import com.innovellent.curight.utility.SharedPrefService;

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

        SharedPrefService sharedPrefService = SharedPrefService.getInstance();
        sharedPrefService.setLong(USER_ID, 1L);
    }

}
