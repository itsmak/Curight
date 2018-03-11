package com.innovellent.curight.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.innovellent.curight.R;
import com.innovellent.curight.activities.Add_Profile_Activity;
import com.innovellent.curight.activities.HomeActivity;
import com.innovellent.curight.adapter.BloodGroupSpinner;
import com.innovellent.curight.adapter.CitySpinner;
import com.innovellent.curight.adapter.InterestSpinner;
import com.innovellent.curight.adapter.MartialSpinner;
import com.innovellent.curight.adapter.PinSpinner;
import com.innovellent.curight.adapter.SpinnerAdapter;
import com.innovellent.curight.adapter.StateSpinner;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.PROFILE_RESPONSE_FEED;
import com.innovellent.curight.model.PostBodyProfile;
import com.innovellent.curight.model.Post_Family_Update;
import com.innovellent.curight.model.Post_Profile_Update;
import com.innovellent.curight.model.Profile_Response;
import com.innovellent.curight.model.Registration_Response;
import com.innovellent.curight.utility.Config;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sagar on 9/12/2017.
 */

public class EditProfileFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "CuRight";
 //   private static final String BASE_URL = "http://13.59.209.135:8090/diagnosticAPI/webapi/";
    Spinner spGender,sprelationship,spBloodGroup,spCity,spState;
    EditText etFullName,etEmail,etMobileNo,etAddress,etdateOfBirth,etpincd;
    TextView tvDateOfBirth,tvGender,tvMartialStatus,tvInterest,tvBloodGroup,tvCity,tvState,tvPin;
    Button btn_submit;
    SpinnerAdapter mAdapter;
    private int mYear, mMonth, mDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_edit_profile,container,false);

        init(rootView);

        int uid = (int) Prefs.getLong("spinner_id",0);
        getProfileData(uid);
        String relation=Prefs.getString("relationship","");
        Log.d(TAG,"relationship:"+relation);
        if(relation.equalsIgnoreCase("Self"))
        {
            sprelationship.setEnabled(false);
        }else {
            sprelationship.setEnabled(true);
        }
        initClick();
        return rootView;
    }
    public void init(View rootView){
        etFullName = (EditText)rootView.findViewById(R.id.etFullName);
        etEmail = (EditText)rootView.findViewById(R.id.etEmail);
        etMobileNo = (EditText)rootView.findViewById(R.id.etMobileNo);
        etAddress = (EditText)rootView.findViewById(R.id.etAddress);
        etdateOfBirth = (EditText) rootView.findViewById(R.id.etdateOfBirth);
        etpincd = (EditText) rootView.findViewById(R.id.etpincd);
        spGender = (Spinner) rootView.findViewById(R.id.spGender);
        sprelationship = (Spinner) rootView.findViewById(R.id.sprelationship);
        spBloodGroup = (Spinner) rootView.findViewById(R.id.spBloodGroup);
        spState = (Spinner) rootView.findViewById(R.id.spState);
        spCity = (Spinner) rootView.findViewById(R.id.spCity);
        btn_submit = (Button) rootView.findViewById(R.id.btn_submit);

    }

    public void initClick(){
        etdateOfBirth.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.etdateOfBirth:
                selectDate(etdateOfBirth);
                break;
            case R.id.btn_submit:
                UpdateProfile();
                break;
        }

    }
    public void selectDate(final EditText setedttxt) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
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

    private void getProfileData(int userid) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface reditapi = retrofit.create(ApiInterface.class);

        PostBodyProfile postBodyprofile = new PostBodyProfile(userid, "individual");
        Call<Profile_Response> call = reditapi.getProfileIndividual(postBodyprofile);

        call.enqueue(new Callback<Profile_Response>(){

            @Override
            public void onResponse(Call<Profile_Response> call, Response<Profile_Response> response) {

                if (response.body() != null) {
                    Log.e(TAG, "profileResponse: code: " + response.body().getCode());
                    ArrayList<PROFILE_RESPONSE_FEED> result = response.body().getResults();
                    Log.e(TAG, "profileResponse: listsize: " + result.size());
                    for (int i = 0; i < result.size(); i++)
                    {
                        TextView gender_txt,relation_txt,bloodgrp_txt,state_txt,city_txt;
                        Log.e(TAG, "profileResponse: listsize: " + result.get(i).getDob());
                        etFullName.setText(result.get(i).getName());
                        etEmail.setText(result.get(i).getEmail());
                        etMobileNo.setText(result.get(i).getMobile());
                        etAddress.setText(result.get(i).getAddress());
                        etdateOfBirth.setText(result.get(i).getDob());
                        etpincd.setText(String.valueOf(result.get(i).getPin()));
                        gender_txt = (TextView)spGender.getSelectedView();
                        relation_txt = (TextView)sprelationship.getSelectedView();
                        bloodgrp_txt = (TextView)spBloodGroup.getSelectedView();
                        state_txt = (TextView)spState.getSelectedView();
                        city_txt = (TextView)spCity.getSelectedView();
                        gender_txt.setText(result.get(i).getGender());
                        relation_txt.setText(result.get(i).getRelationship());
                        bloodgrp_txt.setText(result.get(i).getBloodgroup());
                        state_txt.setText(result.get(i).getState());
                        city_txt.setText(result.get(i).getCity());

                    }
                }

            }
            @Override
            public void onFailure(Call<Profile_Response> call, Throwable t) {

                Log.e(TAG, "onFailure: Somethings went wrong" + t.getMessage());
                Toast.makeText(getActivity(), "Somethings went wrong", Toast.LENGTH_SHORT).show();

            }

        });
    }

    private void UpdateProfile() {
        String RELATIONSHIP;
            if(etFullName.getText().toString().trim().equals(""))
            {
                etFullName.setError("Enter a Name");
                etdateOfBirth.requestFocus();
            }else if(etEmail.getText().toString().trim().equals(""))
            {
                etEmail.setError("Email Required");
                etEmail.requestFocus();
            }else if(spGender.getSelectedItem().toString().trim().equals(""))
            {
                TextView errorText = (TextView)spGender.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);//just to highlight that this is an error
                errorText.setText("Select Gender");//changes the selected item text to this
            }else if(spBloodGroup.getSelectedItem().toString().trim().equals(""))
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
            }else if(etpincd.getText().toString().length()<6)
            {
                etpincd.setError("Enter Valid Pincode");
                etpincd.requestFocus();
            }
            else{
                //Toast.makeText(getActivity(),"Validation Successfull",Toast.LENGTH_SHORT).show();

                String relationship=Prefs.getString("relationship","");
                Log.d(TAG,"relationship:"+relationship);
                    if(relationship.equalsIgnoreCase("Self"))
                    {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(new Config().SERVER_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
//                        Log.d(TAG,"update: name"+etFullName.getText().toString());
//                        Log.d(TAG,"update: gender"+spGender.getSelectedItem().toString());
//                        Log.d(TAG,"update: email"+etEmail.getText().toString());
//                        Log.d(TAG,"update: bloodgroup"+spBloodGroup.getSelectedItem().toString());
//                        Log.d(TAG,"update: dob"+etdateOfBirth.getText().toString());
//                        Log.d(TAG,"update: pin"+Integer.parseInt(etpincd.getText().toString()));
//                        Log.d(TAG,"update: address"+etAddress.getText().toString());
//                        Log.d(TAG,"update: city"+spCity.getSelectedItem().toString());
//                        Log.d(TAG,"update: state"+etAddress.getText().toString());
                        int uid = (int) Prefs.getLong("user_id",0);
                        Log.d(TAG,"update: user_id"+uid);
                        Post_Profile_Update postprofileupdate = new Post_Profile_Update(uid,etFullName.getText().toString(),spGender.getSelectedItem().toString(),etEmail.getText().toString(),spBloodGroup.getSelectedItem().toString(),etdateOfBirth.getText().toString(),Integer.parseInt(etpincd.getText().toString()),etAddress.getText().toString(),spCity.getSelectedItem().toString(),etpincd.getText().toString());
                        Call<Registration_Response> call = apiInterface.updatepatientprofile(postprofileupdate);
                        call.enqueue(new Callback<Registration_Response>(){
                            @Override
                            public void onResponse(Call<Registration_Response> call, Response<Registration_Response> response) {
                                Log.e(TAG, "update:Response:" + response.body().getCode());
                                if (response.body() != null) {
                                   // Log.e(TAG, "update:Response: code: " + response.body().getCode());
                                   // Log.e(TAG, "update:Response: result: " + response.body().getResults());
                                    if(response.body().getCode()==200){
                                        Toast.makeText(getActivity(),"Patient Profile Sussessfully Updated",Toast.LENGTH_SHORT).show();
                                        Intent homeint = new Intent(getActivity(), HomeActivity.class);
                                        Prefs.putString("destination","Profile");
                                        startActivity(homeint);


                                    }else {
                                        Toast.makeText(getActivity(),"You are already Registered",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            @Override
                            public void onFailure(Call<Registration_Response> call, Throwable t) {
                               // Toast.makeText(getActivity(), "Somethings went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }else {
                        if(sprelationship.getSelectedItem().toString().equals(""))
                        {
                            TextView errorText = (TextView)sprelationship.getSelectedView();
                            errorText.setError("");
                            errorText.setTextColor(Color.RED);//just to highlight that this is an error
                            errorText.setText("Select City");
                        }
                        else
                        {
                            int uid = (int) Prefs.getLong("user_id",0);
                            Log.d(TAG,"update: parentId"+uid);
                            int family_id = Prefs.getInt("family_id",0);
                            Log.d(TAG,"update: FamilyId"+family_id);
                            Log.d(TAG,"update: name"+etFullName.getText().toString());
                            Log.d(TAG,"update: gender"+spGender.getSelectedItem().toString());
                            Log.d(TAG,"update: email"+etEmail.getText().toString());
                            Log.d(TAG,"update: relationship"+sprelationship.getSelectedItem().toString());
                            Log.d(TAG,"update: bloodgroup"+spBloodGroup.getSelectedItem().toString());
                            Log.d(TAG,"update: dob"+etdateOfBirth.getText().toString());
                            Log.d(TAG,"update: pin"+Integer.parseInt(etpincd.getText().toString()));
                            Log.d(TAG,"update: address"+etAddress.getText().toString());
                            Log.d(TAG,"update: city"+spCity.getSelectedItem().toString());
                            Log.d(TAG,"update: state"+spState.getSelectedItem().toString());

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(new Config().SERVER_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
                            Post_Family_Update postfamilyupdate = new Post_Family_Update(uid,family_id,etFullName.getText().toString(),spGender.getSelectedItem().toString(),
                   etEmail.getText().toString(),spBloodGroup.getSelectedItem().toString(),etdateOfBirth.getText().toString(),Integer.parseInt(etpincd.getText().toString()),
                   etAddress.getText().toString(),spCity.getSelectedItem().toString(),spState.getSelectedItem().toString(),"",sprelationship.getSelectedItem().toString());

                            Call<Registration_Response> call = apiInterface.updatefamilyprofile(postfamilyupdate);
                            call.enqueue(new Callback<Registration_Response>(){
                                @Override
                                public void onResponse(Call<Registration_Response> call, Response<Registration_Response> response) {
                                    Log.e(TAG, "update:Response:" + response.body().getCode());
                                    if (response.body() != null) {
                                        Log.e(TAG, "update:Response: code: " + response.body().getCode());
                                        Log.e(TAG, "update:Response: result: " + response.body().getResults());
                                        if(response.body().getCode()==200){
                                            Toast.makeText(getActivity(),"Patient Profile Sussessfully Updated",Toast.LENGTH_SHORT).show();
                                            Intent homeint = new Intent(getActivity(), HomeActivity.class);
                                            Prefs.putString("destination","Profile");
                                            startActivity(homeint);

                                        }else {
                                            Toast.makeText(getActivity(),"You are already Registered",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<Registration_Response> call, Throwable t) {
                                    // Toast.makeText(getActivity(), "Somethings went wrong", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    }
                }


        }

    }


