package com.innovellent.curight.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.DiagnosticCenterAdapter;
import com.innovellent.curight.adapter.SortSpinnerAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.Center;
import com.innovellent.curight.model.DiagnosticCentre;
import com.innovellent.curight.model.ServerResponseDiagCenter;
import com.innovellent.curight.model.SortBy_Item;
import com.innovellent.curight.model.TestDetail;
import com.innovellent.curight.utility.Config;
import com.pixplicity.easyprefs.library.Prefs;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DiagnosticCentersActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "CuRight";
    RecyclerView recycler_view,recycler_view_sortby;
    DiagnosticCenterAdapter mAdapter;
    ImageView ivBack;
    Toolbar toolbar;
    TextView niceSpinner;
    RelativeLayout rl_spinner;
    ServerResponseDiagCenter diagCenterByTest;
    TestDetail testDetail;
    Center center;
    SortSpinnerAdapter sortadapter;
    char newtest_id [];
    ArrayList<Center> centerObjs = new ArrayList<Center>();
    String list[]={"Low to High","High to Low","Rating"};
    int position;
    ArrayList<SortBy_Item> sortitem = new ArrayList<>();
    private String test_ids="";
    private String dianosticcentre_id;
    private int integer_diagnostic_id;
    private String test_names="";
    private String my_test_id = "",newtext,finaltext_id;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic_centers);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            dianosticcentre_id = bundle.getString("testid");
            test_ids = bundle.getString("test_id");
            test_names = bundle.getString("test_names");
        }
        my_test_id=Prefs.getString("test_id","");
        if(my_test_id.length()==1)
        {
            finaltext_id=my_test_id;
        }else{
            int j=0;
            newtest_id = new char[my_test_id.length()+my_test_id.length()];
            for(int i=0;i<my_test_id.length();i++)
            {
                    newtest_id[j] = my_test_id.charAt(i);
                    j=j+1;
                    newtest_id[j]=',';
                    j=j+1;
            }
            newtext = new String(newtest_id);
            if(newtext!=null)
            {
                if(newtext.length()>=1)
                {
                    finaltext_id= newtext.substring(0,newtest_id.length-1);
                }

            }
        }
        Log.d(TAG,"test_Id_old:"+my_test_id);
        Log.d(TAG,"test_Id_new"+finaltext_id);
        Log.d(TAG,"Diagnostic_Id"+dianosticcentre_id);

        testcount();
        init();
        if(dianosticcentre_id==null)
        {
            integer_diagnostic_id = 0;
            finaltext_id = test_ids;
        }else {
            integer_diagnostic_id = Integer.parseInt(dianosticcentre_id);
            finaltext_id ="0";
        }
        niceSpinner = (TextView) findViewById(R.id.spinner_sortby);
        getData(integer_diagnostic_id,finaltext_id,"None");
//        final List<String> sortvalue = new ArrayList<String>();
//        sortvalue.add("None");
//        sortvalue.add("LowToHigh");
//        sortvalue.add("HighToLow");
//        sortvalue.add("popularity");
//
//        // Creating adapter for spinner
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sortvalue);
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
 //       niceSpinner.setAdapter(dataAdapter);
        //niceSpinner.setAdapter();
       // getData(integer_diagnostic_id,finaltext_id,"none");
 //       niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
 //           @Override
 //           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

 //               getData(integer_diagnostic_id,finaltext_id,sortvalue.get(position));
 //           }

//            @Override
 //           public void onNothingSelected(AdapterView<?> parent) {

  //          }
 //       });
        rl_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(DiagnosticCentersActivity.this);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.add_dailog_sortby);
                recycler_view_sortby = (RecyclerView) dialog.findViewById(R.id.recycler_view_sortby);
                dialog.setCancelable(true);
                sortitem.clear();
//                SortBy_Item movie = new SortBy_Item("None");
//                sortitem.add(movie);
                SortBy_Item movie = new SortBy_Item("Price : Low To High");
                sortitem.add(movie);
                movie = new SortBy_Item("Price : High To Low");
                sortitem.add(movie);
                movie = new SortBy_Item("Popularity");
                sortitem.add(movie);

                sortadapter = new SortSpinnerAdapter(DiagnosticCentersActivity.this, sortitem, position, new SortSpinnerAdapter.OnSpinnerClickListener() {
                    @Override
                    public void onspinneritemselect(SortBy_Item item_f, int position) {
                       String sort_text;
                       if(item_f.getSortBy().equalsIgnoreCase("Price : Low To High"))
                       {
                           sort_text = "LowToHigh";
                       }else if(item_f.getSortBy().equalsIgnoreCase("Price : High To Low"))
                       {
                           sort_text = "HighToLow";
                       }else {
                           sort_text = item_f.getSortBy();
                       }
                        niceSpinner.setText(sort_text);
                        dialog.dismiss();
                        getData(integer_diagnostic_id,finaltext_id,sort_text);
                    }
                });
                recycler_view_sortby.setItemAnimator(new DefaultItemAnimator());
                recycler_view_sortby.addItemDecoration(new android.support.v7.widget.DividerItemDecoration(DiagnosticCentersActivity.this, LinearLayoutManager.VERTICAL));
                recycler_view_sortby.setLayoutManager(new LinearLayoutManager(DiagnosticCentersActivity.this, LinearLayoutManager.VERTICAL, false));
                recycler_view_sortby.setAdapter(sortadapter);
                sortadapter.notifyDataSetChanged();
                Window dialogWindow = dialog.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

                dialogWindow.setAttributes(lp);
                dialog.show();
            }
        });

    }
        public int testcount()
        {
        int test_count = Prefs.getInt("test_length",0);
            Log.d(TAG,"activity_testcount"+test_count);
        return test_count;
        }

    public void init(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);
        rl_spinner = (RelativeLayout) findViewById(R.id.rl_spinner);
    }

    public void getData(int integer_diagnostic_id, String newtest_id,String sortby){

        progressDialog = ProgressDialog.show(DiagnosticCentersActivity.this, "Loading", "please wait", true, false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        clearData();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Log.d(TAG,"summery_param diagnostic"+integer_diagnostic_id);
        Log.d(TAG,"summery_param test"+newtest_id);
        final DiagnosticCentre centre = new DiagnosticCentre(integer_diagnostic_id,newtest_id,sortby);

        Call<ServerResponseDiagCenter> call = apiInterface.getDcTest(centre);

        call.enqueue(new Callback<ServerResponseDiagCenter>() {
            @Override
            public void onResponse(Call<ServerResponseDiagCenter> call, Response<ServerResponseDiagCenter> response) {
                progressDialog.dismiss();
                if(response.body()!=null) {
                  progressDialog.dismiss();
                  diagCenterByTest = (ServerResponseDiagCenter) response.body();
                  String code = diagCenterByTest.getCode();
                  if ("200".equals(code)) {
                      for (int i = 0; i < diagCenterByTest.getResults().size(); i++) {
                          center = diagCenterByTest.getResults().get(i);
                          //Log.e("TAG","SERVER RESPONSE ::  "+diagCenterByTest.getResults().get(i));;
                          centerObjs.add(center);

                      }
                      if (centerObjs.size() != 0) {
                          mAdapter = new DiagnosticCenterAdapter(DiagnosticCentersActivity.this, centerObjs);
                          recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                          recycler_view.setAdapter(mAdapter);
                      }
                  }
              }else {
                  Toast.makeText(getApplicationContext(),"No data",Toast.LENGTH_SHORT).show();
                  progressDialog.dismiss();
              }
            }

            @Override
            public void onFailure(Call<ServerResponseDiagCenter> call, Throwable t) {
                progressDialog.dismiss();
                t.getMessage();
                String s = t.getMessage();
                Log.e("TAG","error :: "+s);
            }
    });
}

    private void clearData() {
        mAdapter = new DiagnosticCenterAdapter(DiagnosticCentersActivity.this, centerObjs);
        centerObjs.clear(); //clear list
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {


        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            //finish();
        {
            Intent i=new Intent(DiagnosticCentersActivity.this,DiagnosticTestListActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(DiagnosticCentersActivity.this,DiagnosticTestListActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();

    }
}
