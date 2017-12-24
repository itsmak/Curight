package com.innovellent.curight.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;


import com.innovellent.curight.LoginActivity;
import com.innovellent.curight.R;
import com.innovellent.curight.adapter.SummaryAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.ServerResponseTestBooking;
import com.innovellent.curight.model.TestBookingCreate;
import com.innovellent.curight.model.TestBookingDetail;
import com.innovellent.curight.model.TestBookingId;
import com.innovellent.curight.model.TestDetail;
import com.innovellent.curight.utility.Config;
import com.innovellent.curight.utility.Util;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SummaryDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView recycler_view;
    SummaryAdapter mAdapter;
    ImageView ivBack;
    TextView startDate,endDate,tvCenterName,tvLoc;
    Button btnProcceed,btnAddTest;
    private int mYear, mMonth, mDay;
    CheckBox cbHomePickup;
    ArrayList<String> arrayList = new ArrayList<String>();
    //ArrayList<String> newArrayList = new ArrayList<String>();
    ArrayList<String> amountList =new ArrayList<String>();
    Toolbar toolbar;
    String dc_name,loc,test_names;
    String[] tests_arr;
    long dc_id;
    String sel_test_ids,test_amnt_str;
    String[] test_amnts;
    TextView tvTotal;
    boolean updateFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_details);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            dc_id = bundle.getLong("dc_id");
            dc_name = bundle.getString("dc_name");


            loc = bundle.getString("location");
            test_names = bundle.getString("test_names");
            Log.d("test_names***",  test_names);
            sel_test_ids = bundle.getString("sel_test_ids");
            test_amnt_str =  bundle.getString("test_amounts");
        }
        init();
        iniClick();
        getData();

        Log.d("arraylistsize***",  ""+arrayList.size());

    }
    public void init(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        startDate =(TextView)findViewById(R.id.startDate);
        endDate = (TextView) findViewById(R.id.endDate);
        cbHomePickup = (CheckBox) findViewById(R.id.cbHomePickUp);
        tvCenterName = (TextView) findViewById(R.id.tvClinicName);
        tvLoc = (TextView) findViewById(R.id.tvAddress);
        tvTotal = (TextView) findViewById(R.id.totalAmount);
        tvCenterName.setText(dc_name);
        tvLoc.setText(loc);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        btnProcceed=(Button)findViewById(R.id.btnProcceed);
        btnAddTest=(Button)findViewById(R.id.btnAddTest);
       // ivBack=(ImageView)findViewById(R.id.ivBack);
        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);
    }
    public void iniClick(){
        btnProcceed.setOnClickListener(this);
        btnAddTest.setOnClickListener(this);
        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        cbHomePickup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!compoundButton.isChecked()) {
                    startDate.setClickable(false);
                    endDate.setClickable(false);
                } else {
                    startDate.setOnClickListener(SummaryDetailsActivity.this);
                    endDate.setOnClickListener(SummaryDetailsActivity.this);
                }
            }
        });
        if (!("".equals(test_names))) {
            tests_arr = test_names.split("\\^");
        }
        for (int i=0;i<tests_arr.length;i++) {
            arrayList.add(tests_arr[i]);
        }
        if (!("".equals(test_amnt_str))) {
            test_amnts = test_amnt_str.split(",");
        }
        long total_amount = 0L;
        for (String amount : test_amnts) {
            amountList.add(amount);
            total_amount = total_amount + Long.parseLong(amount);
        }
        Log.e("SUMMARY","total amnt :: "+total_amount);
        tvTotal.setText(total_amount+"");
        SharedPreferences sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sharedPreferences.edit();
        et.putLong("total_amount", total_amount);
        et.putString("location",loc);
        et.apply();
        et.commit();

    }

    public void getData(){
            mAdapter = new SummaryAdapter(SummaryDetailsActivity.this, arrayList, amountList);
            recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            recycler_view.setAdapter(mAdapter);
        //mAdapter.swap(arrayList,amountList);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnProcceed:

                SharedPreferences sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                String x_access_token = sharedPreferences.getString("access_token","");
                if (Util.isEmpty(x_access_token)) {
                    //perform login
                    Intent i1 = new Intent(SummaryDetailsActivity.this, LoginActivity.class);
                    startActivity(i1);
                } else {
                    //do Payment as its logged in
                    doPayment(x_access_token);
                }

                break;
            case R.id.btnAddTest:
                Intent i2 = new Intent(SummaryDetailsActivity.this, AddTestActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("dc_id",dc_id);
                if(sel_test_ids.endsWith(","))
                {
                    sel_test_ids = sel_test_ids.substring(0,sel_test_ids.length()-1);
                }
                bundle.putString("sel_test_ids",sel_test_ids);
                bundle.putString("dc_name",dc_name);
                bundle.putString("loc",loc);
                i2.putExtras(bundle);
                startActivity(i2);
                break;
            case R.id.startDate:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(SummaryDetailsActivity.this,
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
                                startDate.setText(dayOfMonth + "-" + (monthYear) + "-" + year);


                            }
                        }, mYear, mMonth, mDay);


                datePickerDialog.show();
                break;

            case R.id.endDate:
                final Calendar c1 = Calendar.getInstance();
                mYear = c1.get(Calendar.YEAR);
                mMonth = c1.get(Calendar.MONTH);
                mDay = c1.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog1 = new DatePickerDialog(SummaryDetailsActivity.this,
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
                                endDate.setText(dayOfMonth + "-" + (monthYear) + "-" + year);


                            }
                        }, mYear, mMonth, mDay);


                datePickerDialog1.show();
                break;

        }
    }

    public void doPayment(String access_token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        TestBookingCreate testBookingCreate = new TestBookingCreate("Amit Verma","123456","123123123111","","","","","",dc_id+"",sel_test_ids,"2500","","","","123123123","","","","N");

        Call<TestBookingId> call = apiInterface.proceedToPay(access_token,testBookingCreate);

        Log.e("SUMMARY","request URL ::  "+call.request().url());

        call.enqueue(new Callback<TestBookingId>() {
            @Override
            public void onResponse(Call<TestBookingId> call, Response<TestBookingId> response) {

                if (response.isSuccessful()) {
                    Log.e("Summary","Server Response ::  "+response.body().getTestbookingid());
                    TestBookingId bookingDetail = response.body();
                    Intent intent = new Intent(SummaryDetailsActivity.this, PaymentDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("test_booking_id", bookingDetail.getTestbookingid());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<TestBookingId> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(SummaryDetailsActivity.this,AddTestActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
        super.onBackPressed();
    }

}
