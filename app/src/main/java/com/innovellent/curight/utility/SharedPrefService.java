package com.innovellent.curight.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.innovellent.curight.CurightApplication;

public class SharedPrefService {

    private static SharedPrefService instance = new SharedPrefService();
    private static SharedPreferences preferences = CurightApplication.getAppContext().getSharedPreferences("com.innovellent.curight", Context.MODE_PRIVATE);

    private SharedPrefService() {
    }

    public static SharedPrefService getInstance() {
        return instance;
    }

    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public String getString(String key) {
        return preferences.getString(key, null);
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public void setInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public void setString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    public void setBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    public void setLong(String key, long value) {
        preferences.edit().putLong(key, value).apply();
    }

    public long getLong(String key) {
        return preferences.getLong(key, 0);
    }
}
