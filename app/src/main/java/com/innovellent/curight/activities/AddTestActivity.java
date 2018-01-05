package com.innovellent.curight.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.Add_Diagnostictest_Adapter;
import com.innovellent.curight.adapter.DiagnosticCenterAdapter;
import com.innovellent.curight.adapter.DiagnosticTestAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.Center;
import com.innovellent.curight.model.DiagnosticCentre;
import com.innovellent.curight.model.Running;
import com.innovellent.curight.model.ServerResponseDiagCenter;
import com.innovellent.curight.model.ServerResponseTest;
import com.innovellent.curight.model.TEST_DETAILS;
import com.innovellent.curight.model.Test;
import com.innovellent.curight.model.TestDetail;
import com.innovellent.curight.model.Test_List;
import com.innovellent.curight.utility.Config;
import com.innovellent.curight.utility.Constants;
import com.innovellent.curight.utility.Util;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AddTestActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "CuRight";
    RecyclerView recycler_view;
    Add_Diagnostictest_Adapter mAdapter;
    Button btnSubmit;
    ImageView ivBack;
    EditText etSearch_addtest;
    ArrayList<String> arrayList=new ArrayList<String>();
    Toolbar toolbar;
    TextView tvClinicName,location;
    String sel_test_ids ="",loc="";
    ArrayList<Test> tests = new ArrayList<Test>();
    ServerResponseDiagCenter diagCenterByTest;
    ArrayList<TestDetail> testObjs = new ArrayList<TestDetail>();
    ArrayList<TEST_DETAILS> t_arraylist = new ArrayList<TEST_DETAILS>();
    TestDetail testDetail;
    Center center;
    Test t;
    ArrayList<String> testArrayList = new ArrayList<String>();
    ArrayList<Test_List> testlist = new ArrayList<Test_List>();
    private long dc_id;
    private String dc_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);
        Bundle bundle = getIntent().getExtras();

//        Prefs.putString("addtest_id","");
//        Prefs.putString("addtest_name","");
//        Prefs.putString("addtest_amount","");

        if (bundle!=null) {
            dc_id = bundle.getLong("dc_id");
            dc_name = bundle.getString("dc_name");
            sel_test_ids = bundle.getString("sel_test_ids");
            loc = bundle.getString("loc");

            Log.d(TAG,"addtest_id***"+String.valueOf(dc_id));
            Log.d(TAG,"addtest_dcname***"+dc_name);
            Log.d(TAG,"addtest_ids***"+sel_test_ids);
            Log.d(TAG,"addtest_locn***"+loc);

        }
        init();
        iniClick();
        getData();
        try {
            etSearch_addtest.setClickable(true);
            etSearch_addtest.clearFocus();
            etSearch_addtest.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    filter(s.toString());
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence query, int start, int before, int count) {
                    query = etSearch_addtest.getText().toString().toString().toLowerCase();

                    final ArrayList<String> filteredList = new ArrayList<>();

                    for (int i = 0; i < arrayList.size(); i++) {

                        final String text = arrayList.get(i).toString().toLowerCase();
                        if (text.contains(query)) {

                            filteredList.add(arrayList.get(i));
                        }
                    }


                   //mAdapter=new DiagnosticTestAdapter(AddTestActivity.this,filteredList);
                    recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    recycler_view.setAdapter(mAdapter);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void init(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
       // ivBack=(ImageView)findViewById(R.id.ivBack);
        etSearch_addtest=(EditText)findViewById(R.id.etSearch_addtest);
        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);
        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        tvClinicName = (TextView) findViewById(R.id.tvClinicName);
        location = (TextView) findViewById(R.id.tvAddress);
        tvClinicName.setText(dc_name);
        location.setText(loc);

    }
    public void iniClick(){
        btnSubmit.setOnClickListener(this);
    //    ivBack.setOnClickListener(this);
        /*arrayList.add("CKMB");
        arrayList.add("Los Angeles,CA");
        arrayList.add("Clonazeoam");
        arrayList.add("Clostridium difficile Toxin");
        arrayList.add("Clotting Profile");
        arrayList.add("Clozapine");
        arrayList.add("CMV PCR Quantitative");
        arrayList.add("CMV Serology");
        arrayList.add("Coeliac Disease HLA Genotyping");
        arrayList.add("Cold Agglutinis Test");
        arrayList.add("Collagen Binding AssY");*/
    }


    public void getData(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Log.e("ADD_TEST","DC_ID  ::"+dc_id);

        final DiagnosticCentre centre = new DiagnosticCentre(Integer.parseInt(dc_id+""),sel_test_ids);

        Call<ServerResponseDiagCenter> call = apiInterface.getDcTest(centre);

        Log.e("AddTest", " Request url ::  "+call.request().url().toString());

        call.enqueue(new Callback<ServerResponseDiagCenter>() {
            @Override
            public void onResponse(Call<ServerResponseDiagCenter> call, Response<ServerResponseDiagCenter> response) {
              if(response.body()!=null) {
                  diagCenterByTest = (ServerResponseDiagCenter) response.body();
                  String code = diagCenterByTest.getCode();
                  if ("200".equals(code)) {
                      center = diagCenterByTest.getResults().get(0);
                      for (int j = 0; j < center.getTestDetail().size(); j++) {
                          testDetail = center.getTestDetail().get(j);
                          //Log.e("TAG","CENTERR ::  "+diagCenterByTest.getResults().get(j));
                          Log.e("TAG", "TEST DET ::  " + center.getTestDetail().get(j).getTestid());
                          testObjs.add(testDetail);
                          try {
                              t_arraylist.add(new TEST_DETAILS(testDetail.getDiagnostictestId(),testDetail.getTestid(),testDetail.getTestName(),testDetail.getAmount(),testDetail.getHomePickupFlag(),testDetail.getLabPickupFlag(),testDetail.getTestchoosen()));
                              String test_name = testDetail.getTestName();
                              Long testId = testDetail.getTestid();
                              testArrayList.add(test_name);
                              t = new Test(test_name, testId, dc_id, testDetail.getTestchoosen(), "", 0L);
                              tests.add(t);
                          } catch (Exception e) {
                              e.printStackTrace();
                          }
                      }
                      if (testArrayList.size() != 0) {
                          mAdapter = new Add_Diagnostictest_Adapter(AddTestActivity.this,t_arraylist);
                      //  mAdapter = new DiagnosticTestAdapter(AddTestActivity.this, testArrayList);
                        recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        recycler_view.setAdapter(mAdapter);
                      }
                  }
              }else {
                  Toast.makeText(getApplicationContext(),"No data",Toast.LENGTH_SHORT).show();
              }
            }

            @Override
            public void onFailure(Call<ServerResponseDiagCenter> call, Throwable t) {
                t.getMessage();
                String s = t.getMessage();
                Log.e("TAG","error :: "+s);
            }
        });

        /*mAdapter=new DiagnosticTestAdapter(AddTestActivity.this,arrayList,testObjects);
        recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(mAdapter);*/

    }

    /*public void getTests(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<ServerResponseTest> call = apiInterface.getTest();

        Log.e("TAG",call.request().url()+"");

        //final ArrayList<String> testAL = new ArrayList<String>();

        call.enqueue(new Callback<ServerResponseTest>() {
            @Override
            public void onResponse(Call<ServerResponseTest> call, Response<ServerResponseTest> response) {
                if (response.isSuccessful()) {
                    ServerResponseTest tests = (ServerResponseTest) response.body();
                    String code = tests.getCode();
                    if ("200".equals(code)) {
                        for (int i = 0; i < tests.getResults().size(); i++) {
                            Test jsonObject = tests.getResults().get(i);
                            Log.e("TAG","EXECUTED!!");
                            testObjects.add(jsonObject);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponseTest> call, Throwable t) {
                t.getMessage();
                String s = t.getMessage();
                Log.e("TAG","error :: "+s);
            }
        });
    }*/
    private void filter(String text){

        ArrayList<TEST_DETAILS> filteredlist = new ArrayList<>();
        for(TEST_DETAILS item : t_arraylist){
            if(item.getTestName().toLowerCase().contains(text.toLowerCase()))
            {
                filteredlist.add(item);
            }
        }
        mAdapter.filterlist(filteredlist);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {

            case R.id.btnSubmit:
                Intent i2=new Intent(AddTestActivity.this,SummaryDetailsActivity.class);
                Bundle bundle = new Bundle();



                String seltestids="",seltestnames="",seltestamounts="";

                for(int i=0;i<t_arraylist.size();i++)
                {
                    if(t_arraylist.get(i).isChecked()){
                        seltestids=seltestids+t_arraylist.get(i).getTestid()+",";
                        seltestnames=seltestnames+t_arraylist.get(i).getTestName()+",";
                        seltestamounts=seltestamounts+t_arraylist.get(i).getAmount()+",";
                    }
                }
                if(seltestids.length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Please Select atleast one Test",Toast.LENGTH_SHORT).show();
                }else {
                    Log.e(TAG,"Add test size::  "+tests.size());
                    Log.e(TAG,"Add test dcid::  "+dc_id);
                    Log.e(TAG,"Add test dcname::  "+dc_name);
                    Log.e(TAG,"Add test location::  "+loc);

                    if (seltestids.endsWith(",")) {
                        seltestids = seltestids.substring(0,seltestids.length()-1);
                    }
                    Log.e(TAG,"Add test seltestid::  "+seltestids);
                    if (seltestnames.endsWith(",")) {
                        seltestnames = seltestnames.substring(0,seltestnames.length()-1);
                    }
                    Log.e(TAG,"Add test Seltestnames::  "+seltestnames);
                    if (seltestamounts.endsWith(",")) {
                        seltestamounts = seltestamounts.substring(0,seltestamounts.length()-1);
                    }
                    Log.e(TAG,"Add test seltestamounts::  "+seltestamounts);
                    bundle.putLong("dc_id",dc_id);
                    bundle.putString("dc_name",dc_name);
                    bundle.putString("location",loc);
                    bundle.putString("sel_test_ids",seltestids);
                    bundle.putString("test_names",seltestnames);
                    bundle.putString("test_amounts",seltestamounts);
                    i2.putExtras(bundle);
                    startActivity(i2);
                    finish();
                }

                break;


        }
    }

//    public  void getalltest()
//    {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(new Config().SERVER_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
//        Log.e(TAG,"params id::"+dc_id);
//        Log.e(TAG,"params testids::"+sel_test_ids);
//        final DiagnosticCentre centre = new DiagnosticCentre(Integer.parseInt(dc_id+""),sel_test_ids);
//        Call<ServerResponseDiagCenter> call = apiInterface.getDcTest(centre);
//        call.enqueue(new Callback<ServerResponseDiagCenter>() {
//            @Override
//            public void onResponse(Call<ServerResponseDiagCenter> call, Response<ServerResponseDiagCenter> response) {
//
//                if(response.body()!=null) {
//                    diagCenterByTest = (ServerResponseDiagCenter) response.body();
//                    String code = diagCenterByTest.getCode();
//                    if ("200".equals(code)) {
//                        center = diagCenterByTest.getResults().get(0);
//                        for (int j = 0; j < center.getTestDetail().size(); j++) {
//                            testDetail = center.getTestDetail().get(j);
//                            //Log.e("TAG","CENTERR ::  "+diagCenterByTest.getResults().get(j));
//                            Log.e("TAG", "TEST DET ::  " + center.getTestDetail().get(j).getTestid());
//                            testObjs.add(testDetail);
//                            try {
//                                String test_name = testDetail.getTestName();
//                                Long testId = testDetail.getTestid();
//                                testArrayList.add(test_name);
//                                t = new Test(test_name, testId, dc_id, testDetail.getTestchoosen(), "", 0L);
//                                tests.add(t);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        if (testArrayList.size() != 0) {
//                            mAdapter = new Add_Diagnostictest_Adapter(AddTestActivity.this,testObjs);
//                            mAdapter = new DiagnosticTestAdapter(AddTestActivity.this, testArrayList);
//                            recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
//                            recycler_view.setAdapter(mAdapter);
//                        }
//                    }
//                }else {
//                    Toast.makeText(getApplicationContext(),"No data",Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<ServerResponseTest> call, Throwable t) {
//
//                t.getMessage();
//                String message = t.getMessage();
//                Log.e("TAG","error :: "+message);
//                if (!isFinishing()) {
//                    if (Constants.SERVER_DOWN.equals(message)) {
//                        Util.showAlertDialog(AddTestActivity.this, "Server is Down! Please try  again later!", "ERROR");
//                        return;
//                    } else {
//                        Util.showAlertDialog(AddTestActivity.this, message, "ERROR");
//                        return;
//                    }
//                }
//
//
//            }
//        });
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(AddTestActivity.this,DiagnosticCentersActivity.class);
        //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}
