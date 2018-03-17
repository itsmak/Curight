package com.innovellent.curight.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    EditText etFirstName,etLastName,etEmail,etMobileNo,etAddress,etdateOfBirth,etpincd,et_state;
    ImageView ivBack;
    Spinner spGender,sprelationship,spBloodGroup,spCity;
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

        etpincd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(editable.length()==6)
                {
                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(etpincd.getWindowToken(), 0);
                    String substring3 = editable.toString().substring(0,3);
                    int pin_3digit = Integer.parseInt(substring3);
                    Log.d(TAG,"pincd s3"+pin_3digit);
                    if(pin_3digit==688)
                    {
                        et_state.setText("Lakshadweep");
                    }else if(pin_3digit==799)
                    {
                        et_state.setText("Tripura");
                    }else if(pin_3digit==744)
                    {
                        et_state.setText("Andaman & Nicobar");
                    }else{
                        String substring2 = editable.toString().substring(0,2);
                        int pin_2digit = Integer.parseInt(substring2);
                        Log.d(TAG,"pincd s2"+pin_2digit);
                        if(pin_2digit==11)
                        {
                            et_state.setText("Delhi");
                        }else if((pin_2digit==12)||(pin_2digit==13))
                        {
                            et_state.setText("Haryana");
                        }else if((pin_2digit==14)||(pin_2digit==15)||(pin_2digit==16))
                        {
                            et_state.setText("Punjab");
                        }else if(pin_2digit==17)
                        {
                            et_state.setText("Himachal Pradesh");
                        }else if((pin_2digit==18)||(pin_2digit==19))
                        {
                            et_state.setText("Jammu & Kashmir");
                        }else if((pin_2digit>19)&&(pin_2digit<29))
                        {
                            et_state.setText("Uttar Pradesh");
                        }else if((pin_2digit >29)&&(pin_2digit < 35))
                        {
                            et_state.setText("Rajasthan");
                        }else if((pin_2digit>36)&&(pin_2digit<39))
                        {
                            et_state.setText("Gujrat");
                        }else if((pin_2digit > 40)&&(pin_2digit < 44))
                        {
                            et_state.setText("Maharashtra");
                        }else if((pin_2digit > 45)&&(pin_2digit < 49))
                        {
                            et_state.setText("Madhya Pradesh");
                        }else if(pin_2digit==49)
                        {
                            et_state.setText("Chhattisgarh");
                        }else if((pin_2digit > 49)&&(pin_2digit< 54))
                        {
                            et_state.setText("Andhra Pradesh");
                        }else if(pin_2digit==54)
                        {
                            et_state.setText("Telangana");
                        }else if((pin_2digit > 55)&&(pin_2digit < 60))
                        {
                            et_state.setText("Karnataka");
                        }else if((pin_2digit >59)&&(pin_2digit < 65))
                        {
                            et_state.setText("Tamil Nadu");
                        }else if((pin_2digit > 66)&&(pin_2digit < 69))
                        {
                            et_state.setText("Kerala");

                        }else if((pin_2digit > 69)&&(pin_2digit < 75))
                        {
                            et_state.setText("West Bengal");
                        }else if((pin_2digit > 75)&&(pin_2digit < 78))
                        {
                            et_state.setText("Orissa");
                        }else if(pin_2digit==78)
                        {
                            et_state.setText("Assam");
                        }else if((pin_2digit >79)&&(pin_2digit < 86))
                        {
                            et_state.setText("Bihar");
                        }else if((pin_2digit==92))
                        {
                            et_state.setText("Jharkhand");
                        }else if((pin_2digit==79))
                        {
                            et_state.setText("Arunachal Pradesh");
                        }
                    }
                }
            }
        });

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
        et_state = (EditText) findViewById(R.id.et_state);
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
            }else if(et_state.getText().toString().trim().equals(""))
            {
                et_state.setError("Enter your Date of birth");
                et_state.requestFocus();
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
