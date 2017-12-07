package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pramod on 16-Nov-2017.
 */

public class AccessToken {
    @SerializedName("x-access-token")
    private String x_access_token;

    public AccessToken(String x_access_token) {
        this.x_access_token = x_access_token;
    }

    public String getX_access_token() {
        return x_access_token;
    }

    public void setX_access_token(String x_access_token) {
        this.x_access_token = x_access_token;
    }
}
