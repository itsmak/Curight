package com.innovellent.curight.activities;

import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.DiagnosticTestAdapter;
import com.innovellent.curight.adapter.Selected_Test_Adapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.SelectedTest;
import com.innovellent.curight.model.ServerResponseTest;
import com.innovellent.curight.model.Test;
import com.innovellent.curight.model.Test_List;
import com.innovellent.curight.model.TestingCenter;
import com.innovellent.curight.utility.Config;
import com.innovellent.curight.utility.Constants;
import com.innovellent.curight.utility.Util;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DiagnosticTestListActivity extends AppCompatActivity{
    private static final String TAG = "CuRight";
    RecyclerView recycler_view,rv_selectedtest;
    DiagnosticTestAdapter mAdapter;
    Selected_Test_Adapter sAdapter;
    ArrayList<SelectedTest> selectedlist;
    Button btnSubmit;
    EditText etSearch;
    TextView tv_AllTest,tv_selectedTest;
    Toolbar toolbar;
    int testid;
    int position;
    ArrayList<Test_List> testlist = new ArrayList<Test_List>();
    ArrayList<String> arrayList=new ArrayList<String>();
    ArrayList<String> testArrayList = new ArrayList<String>();
    String sel_test_ids = "";
    String sel_test_names = "";
    ServerResponseTest tests;
    Test jsonObject;
    ArrayList<Test> testObjs = new ArrayList<Test>();
    private ProgressDialog progressDialog;

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
                int code = tests.getCode();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic_test_list);
        Prefs.putString("test_id","");
        Prefs.putInt("test_length",0);
        Bundle b = new Bundle();
        b = getIntent().getExtras();
        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);
        tv_AllTest=(TextView) findViewById(R.id.tv_AllTest);
        tv_selectedTest=(TextView) findViewById(R.id.tv_selectedTest);
        Log.d(TAG,"test bundle :"+b);
        if(b==null)
        {
             testid = 0;//Integer.parseInt(b.getString("testid"));
            recycler_view.setVisibility(View.GONE);
            tv_AllTest.setVisibility(View.INVISIBLE);
            Log.d(TAG,"test bundle :"+testid);
        }else {
            testid = Integer.parseInt(b.getString("testid"));
            recycler_view.setVisibility(View.VISIBLE);
            tv_AllTest.setVisibility(View.VISIBLE);
        }
        //String name = b.getString("name");
        getalltest(testid);
        getData();
        init();
        iniClick();

        etSearch.setClickable(true);
        etSearch.clearFocus();

            etSearch.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable editable) {
                   filter(editable.toString());
                   if(editable.toString().length()>0)
                   {
                       recycler_view.setVisibility(View.VISIBLE);
                   }else {
                       recycler_view.setVisibility(View.GONE);
                   }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence query, int start, int before, int count) {
                }
            });

        tv_AllTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getalltest(0);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        Prefs.putString("test_id","");
       // Prefs.putInt("test_length",0);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Prefs.putString("test_id","");
       // Prefs.putInt("test_length",0);
    }

    private void filter(String text){

        ArrayList<Test_List> filteredlist = new ArrayList<>();
        for(Test_List item : testlist){
            if(item.getTestname().toLowerCase().contains(text.toLowerCase()))
            {
                filteredlist.add(item);
            }
        }
        mAdapter.filterlist(filteredlist);
    }

    public void init(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        etSearch=(EditText)findViewById(R.id.etSearch_diagnostic);

        rv_selectedtest=(RecyclerView) findViewById(R.id.rv_selectedtest);
        btnSubmit=(Button)findViewById(R.id.btnSubmit);
    }

    public void iniClick(){
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                sel_test_ids = DiagnosticTestAdapter.sel_test_ids;
//                if(sel_test_ids.endsWith(","))
//                {
//                    sel_test_ids = sel_test_ids.substring(0,sel_test_ids.length()-1);
//                }
//                Log.d(TAG, "String ID"+sel_test_ids);
//                bundle.putString("test_id",sel_test_ids);
//                bundle.putString("test_names",sel_test_names);
//                i.putExtras(bundle);
//                String mystring = Prefs.getString("test_id","");
//                Log.d(TAG,"selected ids text:"+mystring);
                if(selectedlist!=null)
                {
                    if (selectedlist.size()==0)
                    {
                        Toast.makeText(getApplicationContext(),"Please! Select atleast One test",Toast.LENGTH_SHORT).show();
                    }else {
                        String selected_test_id="",selected_test_names="";
                        for(int j=0;j<selectedlist.size();j++)
                        {
                            selected_test_id = selected_test_id + selectedlist.get(j).getTestid()+",";
                            selected_test_names = selected_test_names + selectedlist.get(j).getTestname()+",";
                        }
                        if(selected_test_id.endsWith(","))
                        {
                            selected_test_id = selected_test_id.substring(0,selected_test_id.length()-1);
                        }
                        if(selected_test_names.endsWith(","))
                        {
                            selected_test_names = selected_test_names.substring(0,selected_test_names.length()-1);
                        }
                        Intent i=new Intent(DiagnosticTestListActivity.this,DiagnosticCentersActivity.class);
                        Bundle bundle = new Bundle();
                        Log.d(TAG,"selected ids::"+selected_test_id);
                        Log.d(TAG, "selected names::"+selected_test_names);
                        bundle.putString("test_id",selected_test_id);
                        bundle.putString("test_names",selected_test_names);
                        Prefs.putInt("test_length",selectedlist.size());
                        i.putExtras(bundle);
                         startActivity(i);
                        finish();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Please! Select atleast One test",Toast.LENGTH_SHORT).show();
                }

                //
            }
        });
    }

    public void getalltest(int test_id)
    {
        clearData();
        progressDialog = ProgressDialog.show(DiagnosticTestListActivity.this, "Loading", "please wait", true, false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        TestingCenter testcentre = new TestingCenter(test_id);
        Call<ServerResponseTest> call = apiInterface.getTestByTestID(testcentre);
        call.enqueue(new Callback<ServerResponseTest>() {
            @Override
            public void onResponse(Call<ServerResponseTest> call, Response<ServerResponseTest> response) {

                if (response.body() != null) {
                    progressDialog.dismiss();
                    Log.d(TAG, "gettestcentre " + response.body().getCode());
                    ArrayList<Test> result = response.body().getResults();
                    Log.e(TAG, "gettest:  listsize: " + result.size());
                    for (int i = 0; i < result.size(); i++){
                        Log.e(TAG, "gettest:  testname " + result.get(i).getTestname());
                        testlist.add(new Test_List(result.get(i).getTestid(),result.get(i).getTestcode(),result.get(i).getTestname(),result.get(i).getDescription(),result.get(i).getModifiedby(),false));
                    }
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(DiagnosticTestListActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
                mAdapter = new DiagnosticTestAdapter(DiagnosticTestListActivity.this, testlist, position, new DiagnosticTestAdapter.OnTestClickListener() {
                    @Override
                    public void testselected(final Test_List item_m, int position) {
                        if (item_m.isChecked())
                        {
                            item_m.setChecked(false);
                        }else {
                            item_m.setChecked(true);

                        }
                        recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        recycler_view.setAdapter(mAdapter);

                        selectedlist = new ArrayList<SelectedTest>();
                        for(int i=0;i<testlist.size();i++)
                        {
                            if (testlist.get(i).isChecked())
                            {
                                selectedlist.add(new SelectedTest(testlist.get(i).getTestid(),testlist.get(i).getTestname()));
                            }
                        }
                        if(selectedlist.size()>0)
                        {
                            tv_selectedTest.setVisibility(View.VISIBLE);
                        }else {
                            tv_selectedTest.setVisibility(View.GONE);
                        }
                        sAdapter = new Selected_Test_Adapter(DiagnosticTestListActivity.this, selectedlist, position, new Selected_Test_Adapter.OnTestClickListener() {
                            @Override
                            public void closeclicked(SelectedTest item_s, int position) {
                                Long selected_id = item_s.getTestid();
                               // Toast.makeText(DiagnosticTestListActivity.this, "close clicked", Toast.LENGTH_SHORT).show();
                                for(int i=0;i<testlist.size();i++)
                                {
                                    if(selected_id==testlist.get(i).getTestid())
                                    {
                                        testlist.get(i).setChecked(false);

                                    }
                                }
                                Update_main_List();
                                Update_selected_List();

                            }
                        });
                        rv_selectedtest.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                        rv_selectedtest.setAdapter(sAdapter);
                    }
                });
                recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                recycler_view.setAdapter(mAdapter);

            }
            @Override
            public void onFailure(Call<ServerResponseTest> call, Throwable t) {
                progressDialog.dismiss();
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

           // ArrayList<Test> result = response.body().getResults();
        if (response.body() != null)
        {
            tests =(ServerResponseTest) response.body();
            int code = tests.getCode();
            if ("200".equals(code)) {
                for (int i = 0; i < tests.getResults().size(); i++) {
                    jsonObject = tests.getResults().get(i);
                    //testlist.add(new Test_List(result.get(i).getTestid(),result.get(i).getTestcode(),result.get(i).getTestname(),result.get(i).getDescription(),result.get(i).getModifiedby()));
                    testObjs.add(jsonObject);
                    try {
                        String test_name = jsonObject.getTestname();
                        testArrayList.add(test_name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
//                if (testArrayList.size()!=0) {
//                    mAdapter = new DiagnosticTestAdapter(DiagnosticTestListActivity.this, testlist, testObjs);
//                    recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
//                    recycler_view.setAdapter(mAdapter);
//                }
            }
        }else {
            Toast.makeText(DiagnosticTestListActivity.this, response.message(), Toast.LENGTH_SHORT).show();
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
    public void Update_main_List()
    {
        mAdapter = new DiagnosticTestAdapter(DiagnosticTestListActivity.this, testlist, position, new DiagnosticTestAdapter.OnTestClickListener() {
            @Override
            public void testselected(Test_List item_m, int position) {
                if (item_m.isChecked())
                {
                    item_m.setChecked(false);
                }else {
                    item_m.setChecked(true);

                }
                recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                recycler_view.setAdapter(mAdapter);
                Update_selected_List();
                //Update_selected_List();
            }
        });

        mAdapter.notifyDataSetChanged();
        recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
       recycler_view.setAdapter(mAdapter);

    }
    public void Update_selected_List()
    {
        selectedlist = new ArrayList<SelectedTest>();
        for(int i=0;i<testlist.size();i++)
        {
            if (testlist.get(i).isChecked())
            {
                selectedlist.add(new SelectedTest(testlist.get(i).getTestid(),testlist.get(i).getTestname()));
            }
        }
        if(selectedlist.size()>0)
        {
            tv_selectedTest.setVisibility(View.VISIBLE);
        }else {
            tv_selectedTest.setVisibility(View.GONE);
        }
        sAdapter = new Selected_Test_Adapter(DiagnosticTestListActivity.this, selectedlist, position, new Selected_Test_Adapter.OnTestClickListener() {
            @Override
            public void closeclicked(SelectedTest item_s, int position) {
                Long selected_id = item_s.getTestid();

                for(int i=0;i<testlist.size();i++)
                {
                    if(selected_id==testlist.get(i).getTestid())
                    {
                        testlist.get(i).setChecked(false);
                    }
                }
                Update_main_List();
                Update_selected_List();
            }
        });
        sAdapter.notifyDataSetChanged();
        rv_selectedtest.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_selectedtest.setAdapter(sAdapter);

    }
    public void clearData() {
        mAdapter = new DiagnosticTestAdapter(DiagnosticTestListActivity.this, testlist, position,new DiagnosticTestAdapter.OnTestClickListener() {
            @Override
            public void testselected(Test_List item_m, int position) {
                testlist.clear(); //clear list
                mAdapter.notifyDataSetChanged(); //let your adapter know about the changes and reload view.
            }
        });
    }


}