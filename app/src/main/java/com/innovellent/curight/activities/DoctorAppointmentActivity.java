package com.innovellent.curight.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.DoctorAppointmentAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.DoctorList;
import com.innovellent.curight.model.Post_Body_DoctorList;
import com.innovellent.curight.model.ServerResponseDoctorAppointment;
import com.innovellent.curight.model.Test_List;
import com.innovellent.curight.utility.Config;
import com.innovellent.curight.utility.Constants;
import com.innovellent.curight.utility.DividerItemDecoration;
import com.innovellent.curight.utility.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SUNIL on 12/4/2017.
 */

public class DoctorAppointmentActivity extends AppCompatActivity {

    private static final String TAG = "CuRight";
    RecyclerView recycler_view_doctorappointment;
    DoctorAppointmentAdapter doctorAppointmentAdapter;
    ImageView ivback1;
    int testid;
    String phnum;
    EditText etSearch_doctor;
    ServerResponseDoctorAppointment serverResponseDoctorAppointment;
    ArrayList<String> doctorArrayList = new ArrayList<String>();
    ArrayList<DoctorList> arrayList_doctorlist = new ArrayList<DoctorList>();
    DoctorList doctorList;
    int position;
    TextView doctorname_doctorbydc,specialization_doctorbydc,tvemail_doctorbydc,tvtime_doctorbydc,tvmobile_doctorbydc,tvaddress_doctorbydc;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorappointment);

        recycler_view_doctorappointment = (RecyclerView)findViewById(R.id.recycler_view_doctorappointment);
        ivback1 = (ImageView) findViewById(R.id.ivback1);
        etSearch_doctor = (EditText) findViewById(R.id.etSearch_doctor);
        ivback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Bundle b = new Bundle();
        b = getIntent().getExtras();

        Log.d(TAG,"test bundle :"+b);
        if(b==null)
        {
            testid = 0;//Integer.parseInt(b.getString("testid"));
            Log.d(TAG,"test bundle :"+testid);
        }else {
            testid = Integer.parseInt(b.getString("testid"));
        }
        getAllDoctors(testid);
        etSearch_doctor.setClickable(true);
        etSearch_doctor.clearFocus();
        etSearch_doctor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()==1)
                {
                    testid = 0;
                    getAllDoctors(testid);
                }
                filter(editable.toString());
            }
        });

       /* recycler_view_doctorappointment.addOnItemTouchListener(new RecyclerItemClickListener(DoctorAppointmentActivity.this, recycler_view_doctorappointment, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(DoctorAppointmentActivity.this, "item clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));*/


    }
    private void filter(String text) {
        ArrayList<DoctorList> filteredlist = new ArrayList<>();
        for (DoctorList item : arrayList_doctorlist) {
            if (item.getDoctorname().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
            if (item.getSpecialization().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        doctorAppointmentAdapter.filterlist(filteredlist);
    }

    private void getAllDoctors(int doctorid){
        progressDialog = ProgressDialog.show(DoctorAppointmentActivity.this, "Loading", "please wait", true, false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        cleardoctorlist();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Post_Body_DoctorList doctorlist = new Post_Body_DoctorList(doctorid);
        Call<ServerResponseDoctorAppointment> call = apiInterface.getAllDoctorbyid(doctorlist);

        call.enqueue(new Callback<ServerResponseDoctorAppointment>() {
            @Override
            public void onResponse(Call<ServerResponseDoctorAppointment> call, Response<ServerResponseDoctorAppointment> response) {
                serverResponseDoctorAppointment =(ServerResponseDoctorAppointment) response.body();
                String code = serverResponseDoctorAppointment.getCode();
                progressDialog.dismiss();
                if("200".equals(code)){

                    for (int i=0; i<serverResponseDoctorAppointment.getResults().size(); i++){
                        doctorList = serverResponseDoctorAppointment.getResults().get(i);
                            Log.d("doctoralldata===", ""+doctorList.getDoctorname());
                        arrayList_doctorlist.add(doctorList);
                        try{
                            String doctor_name = doctorList.getDoctorname();
                            Log.d("doctorname", doctor_name);
                            doctorArrayList.add(doctor_name);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    if (doctorArrayList.size()!=0) {
                        doctorAppointmentAdapter = new DoctorAppointmentAdapter(DoctorAppointmentActivity.this, doctorArrayList, arrayList_doctorlist, position, new DoctorAppointmentAdapter.OnItemClickListener() {
                            @SuppressLint("MissingPermission")
                            @Override
                            public void onPhoneClick(DoctorList item, int position) {
                                if(isPermissionGranted()){
                                    phnum = item.getMobile();
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:" + phnum));
                                    startActivity(callIntent);
                                }
                            }

                            @Override
                            public void onLayoutClick(DoctorList item, int position) {
                                Dialog dialog = new Dialog(DoctorAppointmentActivity.this);
                                dialog.setCancelable(true);

                                dialog.setContentView(R.layout.doctor_details_dailog);

                                doctorname_doctorbydc = (TextView) dialog.findViewById(R.id.doctorname_doctorbydc);
                                specialization_doctorbydc = (TextView) dialog.findViewById(R.id.specialization_doctorbydc);
                                tvemail_doctorbydc = (TextView) dialog.findViewById(R.id.tvemail_doctorbydc);
                                tvtime_doctorbydc = (TextView) dialog.findViewById(R.id.tvtime_doctorbydc);
                                tvmobile_doctorbydc = (TextView) dialog.findViewById(R.id.tvmobile_doctorbydc);
                                tvaddress_doctorbydc = (TextView) dialog.findViewById(R.id.tvaddress_doctorbydc);

                                doctorname_doctorbydc.setText(String.valueOf(item.getDoctorname()));
                                specialization_doctorbydc.setText(String.valueOf(item.getSpecialization()));
                                tvemail_doctorbydc.setText(String.valueOf(item.getEmail()));
                                tvtime_doctorbydc.setText(String.valueOf(item.getWeekendworkingschedule()));
                                tvmobile_doctorbydc.setText(String.valueOf(item.getMobile()));
                                tvaddress_doctorbydc.setText(String.valueOf(item.getAddresscentre()));

                                Window dialogWindow = dialog.getWindow();
                                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                                dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

                                dialogWindow.setAttributes(lp);
                                dialog.show();
                            }
                        });
                        recycler_view_doctorappointment.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler_view_doctorappointment.getContext());
                        recycler_view_doctorappointment.addItemDecoration(dividerItemDecoration);
                        recycler_view_doctorappointment.setAdapter(doctorAppointmentAdapter);
                    }
                }

            }

            @Override
            public void onFailure(Call<ServerResponseDoctorAppointment> call, Throwable t) {
                t.getMessage();
                String message = t.getMessage();
                progressDialog.dismiss();
                Log.e("TAG","error :: "+message);
                if (!isFinishing()) {
                    if (Constants.SERVER_DOWN.equals(message)) {
                        Util.showAlertDialog(DoctorAppointmentActivity.this, "Server is Down! Please try  again later!", "ERROR");
                        return;
                    } else {
                        Util.showAlertDialog(DoctorAppointmentActivity.this, message, "ERROR");
                        return;
                    }
                }
            }
        });
    }

    private void cleardoctorlist() {
        arrayList_doctorlist.clear();
        doctorArrayList.clear();
        doctorAppointmentAdapter = new DoctorAppointmentAdapter(DoctorAppointmentActivity.this, doctorArrayList, arrayList_doctorlist, position, new DoctorAppointmentAdapter.OnItemClickListener() {
            @Override
            public void onPhoneClick(DoctorList item, int position) {

            }

            @Override
            public void onLayoutClick(DoctorList item, int position) {

            }
        });

        doctorAppointmentAdapter.notifyDataSetChanged();
    }

    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + phnum));
                    startActivity(callIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


}
