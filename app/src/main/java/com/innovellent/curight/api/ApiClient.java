package com.innovellent.curight.api;

import com.innovellent.curight.utility.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    private static ApiInterface apiInterface = null;

    public static ApiInterface getClient() {
        if (apiInterface == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(new Config().SERVER_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiInterface = retrofit.create(ApiInterface.class);
        }
        return apiInterface;
    }
}
