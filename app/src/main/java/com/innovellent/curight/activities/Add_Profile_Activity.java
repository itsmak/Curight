package com.innovellent.curight.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.innovellent.curight.LoginActivity;
import com.innovellent.curight.R;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.PostAddProfile;
import com.innovellent.curight.model.Registration_Response;
import com.innovellent.curight.utility.Config;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Calendar;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mak on 12/19/2017.
 */

public class Add_Profile_Activity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "CuRight";
//    private static final String BASE_URL = "http://13.59.209.135:8090/diagnosticAPI/webapi/";
    EditText etFirstName,etLastName,etEmail,etMobileNo,etAddress,etdateOfBirth,etpincd;
    ImageView ivBack;
    Spinner spGender,sprelationship,spBloodGroup,spCity,spState;
    Button btn_save;
    private int mYear, mMonth, mDay;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        init();
        awesomeValidation.addValidation(this, R.id.etFirstName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.etLastName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.etEmail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.etMobileNo, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
        iniClick();

    }
    public void init()
    {
        etFirstName = (EditText)findViewById(R.id.etFirstName);
        etLastName = (EditText)findViewById(R.id.etLastName);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etMobileNo = (EditText)findViewById(R.id.etMobileNo);
        etAddress = (EditText)findViewById(R.id.etAddress);
        etdateOfBirth = (EditText) findViewById(R.id.etdateOfBirth);
        spGender = (Spinner) findViewById(R.id.spGender);
        sprelationship = (Spinner) findViewById(R.id.sprelationship);
        spBloodGroup = (Spinner) findViewById(R.id.spBloodGroup);
        spCity = (Spinner) findViewById(R.id.spCity);
        spState = (Spinner) findViewById(R.id.spState);
        etpincd = (EditText) findViewById(R.id.etpincd);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        btn_save = (Button) findViewById(R.id.btn_save);
    }

    public void iniClick() {
        ivBack.setOnClickListener(this);
        etdateOfBirth.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.ivBack:
//                Intent i = new Intent(Add_Profile_Activity.this, HomeActivity.class);
//                i.putExtra("flag", "profile");
//                Prefs.putString("destination","Profile");
//                startActivity(i);
                super.onBackPressed();
                break;

            case R.id.etdateOfBirth:
                selectDate(etdateOfBirth);
                break;
            case R.id.btn_save:
                SubmitProfile();
                break;

        }
    }

    private void SubmitProfile() {

        if (awesomeValidation.validate()) {
            if(spGender.getSelectedItem().toString().trim().equals(""))
            {
                TextView errorText = (TextView)spGender.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);//just to highlight that this is an error
                errorText.setText("Select Gender");//changes the selected item text to this
            }else if(sprelationship.getSelectedItem().toString().trim().equals(""))
            {
                TextView errorText = (TextView)sprelationship.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);//just to highlight that this is an error
                errorText.setText("Select Relation");//changes the selected item text to this
            }
            else if(spBloodGroup.getSelectedItem().toString().trim().equals(""))
            {
                TextView errorText = (TextView)spBloodGroup.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);//just to highlight that this is an error
                errorText.setText("Select BloodGroup");//changes the selected item text to this
            }
            else if(etdateOfBirth.getText().toString().trim().equals(""))
            {
                etdateOfBirth.setError("Enter your Date of birth");
                etdateOfBirth.requestFocus();
            }else if(spCity.getSelectedItem().toString().trim().equals(""))
            {
                TextView errorText = (TextView)spCity.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);//just to highlight that this is an error
                errorText.setText("Select City");//changes the selected item text to this
            }else if(etAddress.getText().toString().trim().equals(""))
            {
                etAddress.setError("Enter your Date of birth");
                etAddress.requestFocus();
            }else if(spState.getSelectedItem().toString().trim().equals(""))
            {
                TextView errorText = (TextView)spState.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);//just to highlight that this is an error
                errorText.setText("Select State");//changes the selected item text to this
            }else if(etpincd.getText().toString().trim().equals(""))
            {
                etpincd.setError("Enter your Date of birth");
                etpincd.requestFocus();
            }
            else {
                String uid = String.valueOf(Prefs.getLong("user_id",0));
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(new Config().SERVER_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiInterface apiInterface = retrofit.create(ApiInterface.class);
                PostAddProfile postaddprofile = new PostAddProfile(etFirstName.getText().toString().trim()+etLastName.getText().toString().trim(),uid,
                etdateOfBirth.getText().toString().trim(),sprelationship.getSelectedItem().toString().trim(),spGender.getSelectedItem().toString().trim(),
                spBloodGroup.getSelectedItem().toString().trim(),etMobileNo.getText().toString().trim(),etEmail.getText().toString().trim(),
                spCity.getSelectedItem().toString().trim(),etAddress.getText().toString().trim());
                Call<Registration_Response> call = apiInterface.createprofile(postaddprofile);

                call.enqueue(new Callback<Registration_Response>(){
                    @Override
                    public void onResponse(Call<Registration_Response> call, Response<Registration_Response> response) {
                        if (response.body() != null) {
                            Log.e(TAG, "registrationResponse: code: " + response.body().getCode());
                            Log.e(TAG, "registrationResponse: result: " + response.body().getResults());
                            if(response.body().getCode()==200){
                                Toast.makeText(getApplicationContext(),"Profile Sussessfully Created",Toast.LENGTH_SHORT).show();
                                Intent homeint = new Intent(getApplicationContext(), HomeActivity.class);
                                Prefs.putString("destination","Profile");
                                startActivity(homeint);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(),"Profile already added",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Registration_Response> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Somethings went wrong", Toast.LENGTH_SHORT).show();
                    }
                });



            }

        }

    }

    public void selectDate(final EditText setedttxt) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(Add_Profile_Activity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {


                        GregorianCalendar GregorianCalendar = new GregorianCalendar(year, monthOfYear, dayOfMonth - 1);

                        int dayOfWeek = GregorianCalendar.get(GregorianCalendar.DAY_OF_WEEK);

                        String day = "", monthYear = "";
                        int month = monthOfYear + 1;
                        if (dayOfMonth >= 1 && dayOfMonth <= 9) {
                            day = "0" + dayOfMonth;
                        } else {
                            day = dayOfMonth + "";
                        }
                        if (month >= 1 && month <= 9) {
                            monthYear = "0" + month;
                        } else {
                            monthYear = month + "";
                        }

                        String date = day + "-" + monthYear + "-" + year;
                        setedttxt.setText(year + "/" + (monthYear) + "/" + dayOfMonth);


                    }
                }, mYear, mMonth, mDay);


        datePickerDialog.show();
    }

}
