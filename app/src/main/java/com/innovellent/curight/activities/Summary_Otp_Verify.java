package com.innovellent.curight.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.innovellent.curight.R;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.AccessToken;
import com.innovellent.curight.model.Auth;
import com.innovellent.curight.model.ServerResponseAuth;
import com.innovellent.curight.utility.Config;
import com.innovellent.curight.utility.Util;
import com.pixplicity.easyprefs.library.Prefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by Mak on 12/27/2017.
 */
public class Summary_Otp_Verify extends Activity {

    //private String access_token_str = "";
    long otp;
    private Button btContinue,etContinue1;
    private EditText etOtp;
    private String user_id,email,name;
    private Long uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_verify_layout);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            otp = bundle.getLong("otp");
            user_id = bundle.getString("user_id");
            uid = bundle.getLong("uid");
            email = bundle.getString("email");
            name = bundle.getString("name");
        }
        if (!isFinishing()) {
            Util.showAlertDialog(Summary_Otp_Verify.this,"Please enter "+otp+ " as OTP!","Message");
        }
        etOtp = (EditText) findViewById(R.id.etOtp);
        btContinue=(Button)findViewById(R.id.etContinue);
        etContinue1=(Button) findViewById(R.id.etContinue1);

        btContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {

                    if (Long.parseLong(etOtp.getText().toString()) == otp) {
                        performAuth();
                    } else {
                        Toast.makeText(Summary_Otp_Verify.this, "Invalid OTP entered!", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        etContinue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs.putString("destination", "");
                Summary_Otp_Verify.super.onBackPressed();
            }
        });

    }

    public void performAuth() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface =
                retrofit.create(ApiInterface.class);

        Auth auth = new Auth(user_id,"123","M");

        Call<ServerResponseAuth> call = apiInterface.performAuth(auth);

        Log.e("AUTH","Request URL :: "+call.request().url());

        call.enqueue(new Callback<ServerResponseAuth>() {
            @Override
            public void onResponse(Call<ServerResponseAuth> call, Response<ServerResponseAuth> response) {
                if(response.body()!=null) {
                    Log.e("TAG", "Response :: " + response.isSuccessful() + " message :: " + response.message());
                    AccessToken tokenObj = (AccessToken) response.body().getResults();
                    Log.e("TAG", "Response :message :: " + response.body().getCode());
                    Log.e("TAG", "Response :token :: " + response.body().getCode());
                    String access_token = tokenObj.getX_access_token();
                    Log.e("TAG", "Response :token :: " + access_token);


                    if (!Util.isEmpty(access_token)) {
                        SharedPreferences sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor et = sharedPreferences.edit();
//                      et.putString("access_token", access_token);
//                      et.apply();
//                      et.commit();
                        Prefs.putString("access_token", access_token);
                        //access_token_str = access_token;
                        Log.e("TAG", "Shared value:" + otp+user_id+uid+email+name);
                        Prefs.putLong("user_id", uid);
                        Prefs.putLong("spinner_id", uid);
                        Prefs.putString("name", name);
                        Prefs.putString("email", email);
                        String data = Prefs.getString("destination","");
                        Log.d("Shared","Shared "+data);
                        Intent intent = new Intent(Summary_Otp_Verify.this, PaymentDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("user_id", user_id);
                            bundle.putLong("uid", uid);
                            bundle.putString("name", name);
                            bundle.putString("email", email);
                            intent.putExtras(bundle);
                            startActivity(intent);
                                            }


                } else {
                    Toast.makeText(Summary_Otp_Verify.this, "Invalid Auth token!", Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<ServerResponseAuth> call, Throwable t) {
                t.getMessage();
                String s = t.getMessage();
                if (!isFinishing()) {
                    Util.showAlertDialog(Summary_Otp_Verify.this, s, "Message");
                }
            }
        });
    }


    public boolean validateInput() {
        if (Util.isEmpty(etOtp.getText().toString()) || (etOtp.getText().toString().trim().length() == 0)) {
            if (!isFinishing())
                Util.showAlertDialog(Summary_Otp_Verify.this, "Please enter OTP sent to your mobile number!", "Error");
            etOtp.requestFocus();
            return false;
        }

        return true;
    }

}
