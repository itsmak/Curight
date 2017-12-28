package com.innovellent.curight.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.PROFILE_SPINNER_ADAPTER;
import com.innovellent.curight.adapter.YourReportsAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.MyProfile_Response;
import com.innovellent.curight.model.PROFILE;
import com.innovellent.curight.model.PROFILE_FEED;
import com.innovellent.curight.model.PatientReportsData;
import com.innovellent.curight.model.PatientReportsPojo;
import com.innovellent.curight.model.PostBodyProfile;
import com.innovellent.curight.utility.Config;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SUNIL on 12/22/2017.
 */

public class YourReportsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView recyclerView_reports;
    Spinner sp_familyforreports;
    EditText et_search;
    PatientReportsData patientReportsData;
    YourReportsAdapter _adpater;
    ArrayList<PatientReportsData> patientReportsDataArrayList = new ArrayList<PatientReportsData>();
    String patientreportid,visitdate,diagnosticcentreid,doctorid,reason,reportfilename,reportfiletype,comments,doctorname,doctornumber,diagnsticcentrename,visitday,visitmonth;
    PROFILE_SPINNER_ADAPTER customSpinnerAdapter3;
    ArrayList<PROFILE> spinnerList=new ArrayList<PROFILE>();
    String USER_ID;
    ProgressDialog progressDialog;
    SearchView editsearch;
    ImageView ivback;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yourreportsmain);

        recyclerView_reports = (RecyclerView)findViewById(R.id.recycler_viewforreport);
        sp_familyforreports = (Spinner)findViewById(R.id.spUser);
        ivback = (ImageView)findViewById(R.id.ivback_patientreport);
        //et_search = (EditText)findViewById(R.id.search_forreports);
        editsearch = (SearchView) findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);

        getpatientreportsdata();


        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }


    public void getData2() {


        customSpinnerAdapter3 = new PROFILE_SPINNER_ADAPTER(YourReportsActivity.this, spinnerList);
        sp_familyforreports.setAdapter(customSpinnerAdapter3);
        sp_familyforreports.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Toast.makeText(PasswordRecoveryQuestionsActivity.this, spinner3[i], Toast.LENGTH_SHORT).show();
                //spAge.setText(spinnerList.get(i).getUser_age());
               // Log.e("Userid", spinnerList.get(i).getUser_id());
                //bp.getBloodPressureRecords(spinnerList.get(i).getUser_id());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    private void getSpinnerData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface reditapi = retrofit.create(ApiInterface.class);
        int uid = (int) Prefs.getLong("user_id",0);
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

                        USER_ID = result.get(i).getUserid();
                        //spinnerList.add(new PROFILE("","","",""));
                        spinnerList.add(new PROFILE(result.get(i).getUserid(),result.get(i).getId(), result.get(i).getName(), result.get(i).getAge(), result.get(i).getRelationship()));
                    }
                    getData2();
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

    private void getpatientreportsdata(){

        progressDialog = new ProgressDialog(YourReportsActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        int uid = (int) Prefs.getLong("user_id",0);
        PatientReportsPojo patientReportsPojo = new PatientReportsPojo(uid);

        final Call<ResponseBody> call = apiInterface.getpatientreports("abc", patientReportsPojo);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    try{
                        String res_data = response.body().string();
                        Log.e("res_data", res_data);


                        JSONObject jsonObject = new JSONObject(res_data);

                        String code = jsonObject.getString("Code");
                        Log.d("code==", code);

                        if(code.equals("200")){

                            JSONArray jsonArray = jsonObject.getJSONArray("Results");
                            for(int i=0; i<jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                patientReportsData = new PatientReportsData();
                                patientReportsData.setPatientreportid(Integer.parseInt(jsonObject1.getString("patientreportid")));
                                patientReportsData.setVisitday(jsonObject1.getString("visitday"));
                                patientReportsData.setVisitmonth(jsonObject1.getString("visitmonth"));
                                patientReportsData.setDiagnsticcentrename(jsonObject1.getString("diagnsticcentrename"));
                                patientReportsData.setDoctorname(jsonObject1.getString("doctorname"));
                                patientReportsData.setDoctornumber(jsonObject1.getString("doctornumber"));
                                patientReportsData.setReason(jsonObject1.getString("reason"));
                                patientReportsData.setComments(jsonObject1.getString("comments"));
                                patientReportsData.setVisitdate(jsonObject1.getString("visitdate"));
                                patientReportsData.setReportfilename(jsonObject1.getString("reportfilename"));


                                patientReportsDataArrayList.add(patientReportsData);


                            }
                                getSpinnerData();
                            _adpater=new YourReportsAdapter(YourReportsActivity.this,patientReportsDataArrayList);
                            recyclerView_reports.setLayoutManager(new LinearLayoutManager(YourReportsActivity.this, LinearLayoutManager.VERTICAL, false));
                            recyclerView_reports.setAdapter(_adpater);



                        }
                        progressDialog.dismiss();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(YourReportsActivity.this, "Something went wrong please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        _adpater.filter(text);
        return false;
    }
}
