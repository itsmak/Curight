package com.innovellent.curight.activities;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.TRACK_SPINNER_ADAPTER;
import com.innovellent.curight.adapter.YourReportsAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.MyProfile_Response;
import com.innovellent.curight.model.MyReport_Response;
import com.innovellent.curight.model.PROFILE;
import com.innovellent.curight.model.PROFILE_FEED;
import com.innovellent.curight.model.PatientReportsData;
import com.innovellent.curight.model.PatientReportsPojo;
import com.innovellent.curight.model.PostBodyProfile;
import com.innovellent.curight.model.Report_FEED;
import com.innovellent.curight.utility.Config;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SUNIL on 12/22/2017.
 */

public class YourReportsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "CuRight";
    RecyclerView recyclerView_reports;
    Spinner sp_familyforreports;
    EditText et_search;
    ArrayList<Report_FEED> reportlist = new ArrayList<Report_FEED>();
    PatientReportsData patientReportsData;
    YourReportsAdapter _adpater;
    ArrayList<PatientReportsData> patientReportsDataArrayList = new ArrayList<PatientReportsData>();
    String patientreportid,visitdate,diagnosticcentreid,doctorid,reason,reportfilename,reportfiletype,comments,doctorname,doctornumber,diagnsticcentrename,visitday,visitmonth;
    TRACK_SPINNER_ADAPTER customSpinnerAdapter3;
    ArrayList<PROFILE> spinnerList=new ArrayList<PROFILE>();
    String USER_ID;
    ProgressDialog progressDialog;
    EditText editsearch;
    ImageView ivback;
    String phnum;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yourreportsmain);
        recyclerView_reports = (RecyclerView)findViewById(R.id.recycler_viewforreport);
        sp_familyforreports = (Spinner)findViewById(R.id.spUser);
        ivback = (ImageView)findViewById(R.id.ivback_patientreport);
        //et_search = (EditText)findViewById(R.id.search_forreports);
        editsearch = (EditText) findViewById(R.id.search);
        spinnerList.clear();
        getSpinnerData();
        editsearch.setClickable(true);
        editsearch.clearFocus();

        editsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
            Log.d(TAG,"Report list size:"+reportlist.size());
            if(reportlist.size()>0)
            {
                filter(editable.toString());
            }


            }
        });

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void filter(String text){

        ArrayList<Report_FEED> filteredlist = new ArrayList<>();
        for(Report_FEED item : reportlist){
            if(item.getReason().toLowerCase().contains(text.toLowerCase()))
            {
                filteredlist.add(item);
            }
        }
        _adpater.filterlist(filteredlist);
    }

    private void getSpinnerData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface reditapi = retrofit.create(ApiInterface.class);
        int uid = (int) Prefs.getLong("user_id",0);
        Log.d(TAG,"Shared_profile_uid"+uid);
        PostBodyProfile postBodyprofile = new PostBodyProfile(uid, "family");
        Call<MyProfile_Response> call = reditapi.getProfile(postBodyprofile);

        call.enqueue(new Callback<MyProfile_Response>() {
            @Override
            public void onResponse(Call<MyProfile_Response> call, Response<MyProfile_Response> response) {


                if (response.body() != null) {


                    Log.e("", "profileResponse: code: " + response.body().getCode());

                    ArrayList<PROFILE_FEED> result = response.body().getResults();

                    Log.e("", "profileResponse: listsize: " + result.size());
                    for (int i = 0; i < result.size(); i++) {
                        String name = result.get(i).getName();
                        String lastName = "";
                        String firstName= "";
                        if(name.split("\\w+").length>1){

                            //lastName = name.substring(name.lastIndexOf(" ")+1);
                            firstName = name.substring(0, name.lastIndexOf(' '));
                        }
                        else{
                            firstName = name;
                        }
                        USER_ID = result.get(i).getUserid();
                        //spinnerList.add(new PROFILE("","","",""));
                        spinnerList.add(new PROFILE(result.get(i).getUserid(),result.get(i).getId(), firstName, result.get(i).getAge(), result.get(i).getRelationship(),result.get(i).getGender()));
                    }
                    getData2();
                    USER_ID = result.get(0).getUserid();
                    Log.d(TAG, "Myuserid default" + USER_ID);
                    getpatientreport(Integer.parseInt(USER_ID));

                    // GetData(result.get(1).getUserid());
                } else {

                    Toast.makeText(YourReportsActivity.this, response.message(), Toast.LENGTH_SHORT).show();

                }

                //remainder_rclrvw.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<MyProfile_Response> call, Throwable t) {

                Log.e("", "onFailure: Somethings went wrong" + t.getMessage());
                Toast.makeText(YourReportsActivity.this, "Somethings went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }



    public void getData2() {


        customSpinnerAdapter3 = new TRACK_SPINNER_ADAPTER(YourReportsActivity.this, spinnerList);
        sp_familyforreports.setAdapter(customSpinnerAdapter3);
        sp_familyforreports.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Toast.makeText(PasswordRecoveryQuestionsActivity.this, spinner3[i], Toast.LENGTH_SHORT).show();
                //spAge.setText(spinnerList.get(i).getUser_age());
                // Log.e("Userid", spinnerList.get(i).getUser_id());
                //bp.getBloodPressureRecords(spinnerList.get(i).getUser_id());
                Prefs.putLong("spinner_id", Long.parseLong(spinnerList.get(i).getUser_id()));
                int uid = (int) Prefs.getLong("spinner_id",0);
                Log.d(TAG,"Shared_profile_spinnerid"+uid);
                //getpatientreportsdata(uid);
                getpatientreport(uid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//                int uid = (int) Prefs.getLong("user_id",0);
//                Log.d("user_forwhr", ""+uid);
//                Log.d("TAG", "MyUSER_ID on spinner" + USER_ID);
//              //  getpatientreportsdata(uid);
//                getpatientreport(1);
            }
        });

    }
    private void getpatientreport(int user_id)
    {
        progressDialog = ProgressDialog.show(YourReportsActivity.this, "Loading", "please wait", true, false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface reditapi = retrofit.create(ApiInterface.class);
        PatientReportsPojo patientReportsPojo = new PatientReportsPojo(user_id);
        Call<MyReport_Response> call = reditapi.getpatientreport(patientReportsPojo);
        progressDialog.dismiss();
        call.enqueue(new Callback<MyReport_Response>() {
            @Override
            public void onResponse(Call<MyReport_Response> call, Response<MyReport_Response> response) {
                progressDialog.dismiss();

                if(response.body()!=null) {
                    reportlist = response.body().getResults();

                    Log.d(TAG, "reportlist size" + reportlist.size());
                    _adpater = new YourReportsAdapter(YourReportsActivity.this, reportlist, position, new YourReportsAdapter.OnItemClickListener() {
                        @SuppressLint("MissingPermission")
                        @Override
                        public void onItemClick(Report_FEED item, int position) {
                            if(isPermissionGranted()){
                                phnum = item.getDoctornumber();
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + phnum));
                                startActivity(callIntent);
                            }
                        }
                    });
                    recyclerView_reports.setLayoutManager(new LinearLayoutManager(YourReportsActivity.this, LinearLayoutManager.VERTICAL, false));
                    recyclerView_reports.setAdapter(_adpater);
                }
                else
                {
                    int i =10;
                }
                }


            @Override
            public void onFailure(Call<MyReport_Response> call, Throwable t) {

                progressDialog.dismiss();


            }
        });
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

//            private void getpatientreportsdata(int user_id){
//
//       /* progressDialog = new ProgressDialog(YourReportsActivity.this);
//        progressDialog.setMessage("Please Wait...");
//        progressDialog.show();
//        progressDialog.setCanceledOnTouchOutside(false);*/
//      // cleardata();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(new Config().SERVER_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
//        PatientReportsPojo patientReportsPojo = new PatientReportsPojo(user_id);
//
//        final Call<ResponseBody> call = apiInterface.getpatientreports("abc",patientReportsPojo);
//
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if(response.body()!=null){
//                    try{
////                        progressDialog.dismiss();
//
//                        String res_data = response.body().string();
//                        Log.e("res_data", res_data);
//
//                        JSONObject jsonObject = new JSONObject(res_data);
//
//                        String code = jsonObject.getString("Code");
//                        Log.d("code==", code);
//
//                        if(code.equals("200")){
//
//                            JSONArray jsonArray = jsonObject.getJSONArray("Results");
//                            showProgressDialog("Loading");
//                            for(int i=0; i<jsonArray.length(); i++) {
//                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                                patientReportsData = new PatientReportsData();
//                                patientReportsData.setPatientreportid(Integer.parseInt(jsonObject1.getString("patientreportid")));
//                                patientReportsData.setVisitday(jsonObject1.getString("visitday"));
//                                patientReportsData.setVisitmonth(jsonObject1.getString("visitmonth"));
//                                patientReportsData.setDiagnsticcentrename(jsonObject1.getString("diagnsticcentrename"));
//                                patientReportsData.setDoctorname(jsonObject1.getString("doctorname"));
//                                patientReportsData.setDoctornumber(jsonObject1.getString("doctornumber"));
//                                patientReportsData.setReason(jsonObject1.getString("reason"));
//                                patientReportsData.setComments(jsonObject1.getString("comments"));
//                                patientReportsData.setVisitdate(jsonObject1.getString("visitdate"));
//                                patientReportsData.setReportfilename(jsonObject1.getString("reportfilename"));
//
//
//                                patientReportsDataArrayList.add(patientReportsData);
//
//
//                            }
//
//                            progressDialog.dismiss();
//                              //  getSpinnerData();
//
//                        }
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                }else{
//                    Toast.makeText(YourReportsActivity.this, "No Records Found", Toast.LENGTH_SHORT).show();
//                    AlertDialog.Builder ab = new AlertDialog.Builder(YourReportsActivity.this);
//                    ab.setMessage("No Records Found");
//                    ab.create();
//                    ab.show();
//                   // recyclerView_reports.removeAllViews();
//
//                }
//                Log.d(TAG,"Records list size:"+ patientReportsDataArrayList.size());
//                _adpater=new YourReportsAdapter(YourReportsActivity.this,patientReportsDataArrayList);
//                recyclerView_reports.setLayoutManager(new LinearLayoutManager(YourReportsActivity.this, LinearLayoutManager.VERTICAL, false));
//                recyclerView_reports.setAdapter(_adpater);
//                progressDialog.dismiss();
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                progressDialog.dismiss();
//                Toast.makeText(YourReportsActivity.this, "Something went wrong please try again", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


//    private void cleardata(){
//        _adpater=new YourReportsAdapter(YourReportsActivity.this,patientReportsDataArrayList);
//
//        patientReportsDataArrayList.clear();
//        _adpater.notifyDataSetChanged();
//
//    }

    private void showProgressDialog(String title) {
        progressDialog = ProgressDialog.show(YourReportsActivity.this, title, "please wait", true, false);
        progressDialog.show();
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

//    @Override
//    public boolean onQueryTextChange(String newText) {
//        String text = newText;
//        _adpater.filter(text);
//        return false;
//    }
}
