package com.innovellent.curight;

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

import com.innovellent.curight.activities.DiagnosticCentersActivity;
import com.innovellent.curight.activities.DiagnosticTestListActivity;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.AccessToken;
import com.innovellent.curight.model.Login;
import com.innovellent.curight.model.ServerResponseLogin;
import com.innovellent.curight.model.User;
import com.innovellent.curight.utility.Config;
import com.innovellent.curight.utility.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends Activity {
    private Button btContinue;
    private EditText etUsername;
    String user_name;
    private User _user;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor et;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        etUsername = (EditText) findViewById(R.id.etMobile);
        btContinue=(Button)findViewById(R.id.etContinue);


         sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
         et = sharedPreferences.edit();

        btContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(new Config().SERVER_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();


                    ApiInterface apiInterface = retrofit.create(ApiInterface.class);


                    Login login = new Login(user_name,"123","M");

                    Call<ServerResponseLogin> call = apiInterface.performLogin(login);

                    Log.e("LOGIN","Request URL :: "+call.request().url());

                    call.enqueue(new Callback<ServerResponseLogin>() {
                        @Override
                        public void onResponse(Call<ServerResponseLogin> call, Response<ServerResponseLogin> response) {
                            ServerResponseLogin responseLogin =(ServerResponseLogin) response.body();
                            String code = responseLogin.getCode();
                            if ("200".equals(code)) {
                                _user = responseLogin.getResults().get(0);

                                Log.e("LOGIN","User  :: "+_user.getName()+" Mobile :: "+_user.getMobile()+" OTP :: "+_user.getOtp());


                               // if (_user!=null && _user.getUserid()<=0L) {
                                /*SharedPreferences sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                                SharedPreferences.Editor et = sharedPreferences.edit();*/
                                et.putBoolean("Islogin", false);
                                et.putLong("user_id", _user.getUserid());
                                et.putString("mobile",_user.getMobile());
                                et.putString("user_name",_user.getName());
                                et.putString("email",_user.getEmail());
                                et.apply();
                                et.commit();

                                Intent intent = new Intent(LoginActivity.this, OtpVerifyActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("user_id", _user.getMobile());
                                bundle.putLong("uid",_user.getUserid());
                                bundle.putString("name",_user.getName());
                                bundle.putString("email",_user.getEmail());
                                bundle.putLong("otp", _user.getOtp());
                                intent.putExtras(bundle);
                                startActivity(intent);

                            } /*else {
                                //Toast.makeText(LoginActivity.this,"Login",Toast.LENGTH_LONG).show();
                            }*/
                        }

                        @Override
                        public void onFailure(Call<ServerResponseLogin> call, Throwable t) {
                            t.getMessage();
                            String s = t.getMessage();
                            //Util.showAlertDialog(LoginActivity.this,s,"Message");
                        }
                    });
                }
            }
        });
    }


    public boolean validateInput() {
        user_name = etUsername.getText().toString();

        if (Util.isEmpty(user_name) || (user_name.trim().length() == 0)) {
            if (!isFinishing())
                Util.showAlertDialog(LoginActivity.this, "Please enter username", "Message");
            etUsername.requestFocus();
            return false;
        }
        return true;
    }
}
