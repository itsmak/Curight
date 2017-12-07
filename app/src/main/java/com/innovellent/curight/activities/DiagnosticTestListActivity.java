package com.innovellent.curight.activities;

import android.content.Intent;
import android.os.Bundle;
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


import com.google.gson.JsonObject;
import com.innovellent.curight.R;
import com.innovellent.curight.adapter.DiagnosticTestAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.ServerResponseTest;
import com.innovellent.curight.model.Test;
import com.innovellent.curight.utility.Config;
import com.innovellent.curight.utility.Constants;
import com.innovellent.curight.utility.Util;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DiagnosticTestListActivity extends AppCompatActivity{
    RecyclerView recycler_view;
    DiagnosticTestAdapter mAdapter;
    Button btnSubmit;
    EditText etSearch;
    Toolbar toolbar;
    ArrayList<String> arrayList=new ArrayList<String>();
    ArrayList<String> testArrayList = new ArrayList<String>();

    String sel_test_ids = "";
    String sel_test_names = "";

    ServerResponseTest tests;

    Test jsonObject;
    ArrayList<Test> testObjs = new ArrayList<Test>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic_test_list);
        init();
        iniClick();
        getData();
        etSearch.setClickable(true);
        etSearch.clearFocus();
        try {
            etSearch.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence query, int start, int before, int count) {
                    query = etSearch.getText().toString().toString().toLowerCase();

                    final ArrayList<String> filteredList = new ArrayList<>();

                    for (int i = 0; i < arrayList.size(); i++) {

                        final String text = arrayList.get(i).toString().toLowerCase();
                        if (text.contains(query)) {

                            filteredList.add(arrayList.get(i));
                        }
                    }


                    //mAdapter=new DiagnosticTestAdapter(DiagnosticTestListActivity.this,filteredList);
                    recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    recycler_view.setAdapter(mAdapter);
                }
            });

        }catch (Exception e){
            System.out.println("Exception :::  "+e);
        }
    }
    public void init(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        etSearch=(EditText)findViewById(R.id.etSearch);
        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);
        btnSubmit=(Button)findViewById(R.id.btnSubmit);
    }
    public void iniClick(){
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DiagnosticTestListActivity.this,DiagnosticCentersActivity.class);
                Bundle bundle = new Bundle();
                sel_test_ids = DiagnosticTestAdapter.sel_test_ids;
                if(sel_test_ids.endsWith(","))
                {
                    sel_test_ids = sel_test_ids.substring(0,sel_test_ids.length()-1);
                }
                bundle.putString("test_id",sel_test_ids);
                bundle.putString("test_names",sel_test_names);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }

    public static ArrayList<Test> getTestObjs(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<ServerResponseTest> call = apiInterface.getTest();

        Log.e("TAG",call.request().url()+"");

        final ArrayList<Test> testObjects = new ArrayList<Test>();

        final ArrayList<String> testAL = new ArrayList<String>();

        call.enqueue(new Callback<ServerResponseTest>() {
            @Override
            public void onResponse(Call<ServerResponseTest> call, Response<ServerResponseTest> response) {
                ServerResponseTest tests =(ServerResponseTest) response.body();
                String code = tests.getCode();
                if ("200".equals(code)) {
                    for (int i = 0; i < tests.getResults().size(); i++) {
                        Test jsonObject = tests.getResults().get(i);
                        testObjects.add(jsonObject);
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
        return testObjects;
    }

    public void getData(){

    Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    Call<ServerResponseTest> call = apiInterface.getTest();

    Log.e("TAG",call.request().url()+"");

    call.enqueue(new Callback<ServerResponseTest>() {
        @Override
        public void onResponse(Call<ServerResponseTest> call, Response<ServerResponseTest> response) {
            tests =(ServerResponseTest) response.body();
            String code = tests.getCode();
            if ("200".equals(code)) {
                for (int i = 0; i < tests.getResults().size(); i++) {
                    jsonObject = tests.getResults().get(i);
                    testObjs.add(jsonObject);
                    try {
                        String test_name = jsonObject.getTestname();
                        testArrayList.add(test_name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (testArrayList.size()!=0) {
                    mAdapter = new DiagnosticTestAdapter(DiagnosticTestListActivity.this, testArrayList, testObjs);
                    recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    recycler_view.setAdapter(mAdapter);
                }
            }
        }

        @Override
        public void onFailure(Call<ServerResponseTest> call, Throwable t) {
            t.getMessage();
            String message = t.getMessage();
            Log.e("TAG","error :: "+message);
            if (!isFinishing()) {
                if (Constants.SERVER_DOWN.equals(message)) {
                        Util.showAlertDialog(DiagnosticTestListActivity.this, "Server is Down! Please try  again later!", "ERROR");
                        return;
                    } else {
                        Util.showAlertDialog(DiagnosticTestListActivity.this, message, "ERROR");
                        return;
                    }
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}