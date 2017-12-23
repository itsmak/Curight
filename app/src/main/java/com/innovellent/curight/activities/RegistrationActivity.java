package com.innovellent.curight.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.innovellent.curight.LoginActivity;
import com.innovellent.curight.MainActivity;
import com.innovellent.curight.R;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.MyProfile_Response;
import com.innovellent.curight.model.PostBodyRegister;
import com.innovellent.curight.model.Registration_Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sagar on 9/15/2017.
 */

public class RegistrationActivity extends Activity {
    private Button btContinue;
    private EditText etName, etEmail, etMobileNumber,etPassword,etConfirmedPassword;
    private AwesomeValidation awesomeValidation;
    private static final String TAG = ".Retro_MainActivity";
    private static final String BASE_URL = "http://13.59.209.135:8090/diagnosticAPI/webapi/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registraiton);
        btContinue=(Button)findViewById(R.id.etContinue);
        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etMobileNumber = (EditText) findViewById(R.id.etMobileNumber);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmedPassword = (EditText) findViewById(R.id.etConfirmedPassword);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.etName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.etEmail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.etMobileNumber, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);


        btContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitForm();
            }
        });
    }

    private void submitForm() {
        //first validate the form then move ahead
        //if this becomes true that means validation is successfull
        String password = etPassword.getText().toString().trim();
        String confirmpassword = etConfirmedPassword.getText().toString().trim();

        if (awesomeValidation.validate()) {
            if(etPassword.getText().toString().trim().equals(""))
            {
                etPassword.requestFocus();
                etPassword.setError("Enter a valid Password");
            }else if(etConfirmedPassword.getText().toString().trim().equals(""))
            {
                etConfirmedPassword.requestFocus();
                etConfirmedPassword.setError("Re-enter your Password");
            }else if(!password.equals(confirmpassword)){
                etConfirmedPassword.requestFocus();
                etConfirmedPassword.setError("Confirm Password doesn't match Password");
            }else {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiInterface apiInterface = retrofit.create(ApiInterface.class);
                PostBodyRegister postBodyprofile = new PostBodyRegister(etName.getText().toString().trim(), etMobileNumber.getText().toString().trim()
                ,etEmail.getText().toString().trim(),etPassword.getText().toString().trim(),"SELF");

                Call<Registration_Response> call = apiInterface.createUser(postBodyprofile);
                call.enqueue(new Callback<Registration_Response>(){
                    @Override
                    public void onResponse(Call<Registration_Response> call, Response<Registration_Response> response) {
                        if (response.body() != null) {
                            Log.e(TAG, "registrationResponse: code: " + response.body().getCode());
                            Log.e(TAG, "registrationResponse: result: " + response.body().getResults());
                            if(response.body().getCode()==200){
                                Toast.makeText(getApplicationContext(),"You are Sussessfully Registered",Toast.LENGTH_SHORT).show();
                                Intent logint = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(logint);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(),"You are already Registered",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Registration_Response> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Somethings went wrong", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            //process the data further
        }
    }
}

