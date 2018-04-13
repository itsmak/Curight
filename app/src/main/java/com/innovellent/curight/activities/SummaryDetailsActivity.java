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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.DoubleDateAndTimePickerDialog;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.innovellent.curight.LoginActivity;
import com.innovellent.curight.R;
import com.innovellent.curight.adapter.SummaryAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.ServerResponseTestBooking;
import com.innovellent.curight.model.SummaryDetails;
import com.innovellent.curight.model.TestBookingCreate;
import com.innovellent.curight.model.TestBookingDetail;
import com.innovellent.curight.model.TestBookingId;
import com.innovellent.curight.model.TestDetail;
import com.innovellent.curight.utility.Config;
import com.innovellent.curight.utility.Util;
import com.pixplicity.easyprefs.library.Prefs;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SummaryDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "CuRight";
    RecyclerView recycler_view;
    SummaryAdapter mAdapter;
    ImageView ivBack,ivCalendar1,ivCalendar;
    RelativeLayout rl_startDate,rl_enddate;
    TextView tvCenterName,tvLoc;
    EditText startDate,endDate;
    Button btnProcceed,btnAddTest;
    CheckBox cbHomePickup;
    //ArrayList<String> newArrayList = new ArrayList<String>();
    ArrayList<String> arrayList = new ArrayList<String>();
    ArrayList<String> amountList =new ArrayList<String>();
    ArrayList<String> homepickupList =new ArrayList<String>();
    ArrayList<SummaryDetails> summarylist = new ArrayList<SummaryDetails>();

    Toolbar toolbar;
    String dc_name,loc,test_names;
    String[] tests_arr;
    String[] picup_arr;

    long dc_id;
    String sel_test_ids,test_amnt_str,test_homepickup;
    String[] test_amnts;
    TextView tvTotal;
    SimpleDateFormat simpleDateFormat;
    SimpleDateFormat simpleTimeFormat;
    SimpleDateFormat simpleDateOnlyFormat;
    SingleDateAndTimePickerDialog.Builder singleBuilder;
    DoubleDateAndTimePickerDialog.Builder doubleBuilder;
    boolean updateFlag = false;
    private int mYear, mMonth, mDay;

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
            sel_test_ids = bundle.getString("sel_test_ids");
            test_amnt_str =  bundle.getString("test_amounts");
            test_homepickup =  bundle.getString("test_homepickup");

            Log.d(TAG,"summery_test_id***"+String.valueOf(dc_id));
            Log.d(TAG,"summery_test_dcname***"+dc_name);
            Log.d(TAG,"summery_test_location***"+loc);
            Log.d(TAG,"summery_test_names***"+test_names);
            Log.d(TAG,"summery_seltestid***"+sel_test_ids);
            Log.d(TAG,"summery_testamnt***"+test_amnt_str);
            Log.d(TAG,"summery_testpickup***"+test_homepickup);
        }
        init();
        this.simpleDateFormat = new SimpleDateFormat("EEE d MMM HH:mm", Locale.getDefault());
        this.simpleTimeFormat = new SimpleDateFormat("d MMM hh:mm aa", Locale.getDefault());
        this.simpleDateOnlyFormat = new SimpleDateFormat("EEE d MMM", Locale.getDefault());

        iniClick();
        Log.d("arraylistsize***",  ""+arrayList.size());
        Log.d("amountlistsize***",  ""+amountList.size());
        Log.d("homepickuplistsize***",  ""+homepickupList.size());
        for(int i=0;i<arrayList.size();i++)
        {
            summarylist.add(new SummaryDetails(arrayList.get(i),amountList.get(i),homepickupList.get(i)));
        }
        getData();
        String state =Prefs.getString("state","");
        if(state.equalsIgnoreCase("checked"))
        {
            String date1=Prefs.getString("date1","");
            String date2=Prefs.getString("date2","");
            startDate.setText(date1);
            endDate.setText(date2);
        }

    }
    public void init(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        ivCalendar1 = (ImageView) findViewById(R.id.ivCalendar1);
        ivCalendar = (ImageView) findViewById(R.id.ivCalendar);
        startDate =(EditText)findViewById(R.id.startDate);
        endDate = (EditText) findViewById(R.id.endDate);
        rl_enddate = (RelativeLayout) findViewById(R.id.rl_enddate);
        rl_startDate = (RelativeLayout) findViewById(R.id.rl_startDate);
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

        ivCalendar1.setOnClickListener(this);
        ivCalendar.setOnClickListener(this);
        rl_enddate.setOnClickListener(this);
        cbHomePickup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!compoundButton.isChecked()) {
                    startDate.setClickable(false);
                    //endDate.setClickable(false);
                    startDate.setText("");
                    mAdapter = new SummaryAdapter(SummaryDetailsActivity.this, summarylist,false);
                    recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    recycler_view.setAdapter(mAdapter);
                } else {
                    mAdapter = new SummaryAdapter(SummaryDetailsActivity.this, summarylist,true);
                    recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    recycler_view.setAdapter(mAdapter);
                    startDate.setOnClickListener(SummaryDetailsActivity.this);
                    endDate.setOnClickListener(SummaryDetailsActivity.this);
                }
            }
        });
        if (test_homepickup!=null)
        {
            if (!("".equals(test_homepickup))) {
                picup_arr = test_homepickup.split(",");
            }
            if(picup_arr!=null)
            {
                for (int i=0;i<picup_arr.length;i++) {
                    homepickupList.add(picup_arr[i]);
                }
            }
        }


        if (!("".equals(test_names))) {
            tests_arr = test_names.split(",");
        }
        if(tests_arr!=null)
        {
            for (int i=0;i<tests_arr.length;i++) {
                arrayList.add(tests_arr[i]);
            }
        }
        if (!("".equals(test_amnt_str))) {
            test_amnts = test_amnt_str.split(",");
        }
        long total_amount = 0L;
        if(test_amnts!=null)
        {
            for (String amount : test_amnts) {
                amountList.add(amount);
                total_amount = total_amount + Long.parseLong(amount);
            }
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
           // mAdapter = new SummaryAdapter(SummaryDetailsActivity.this, arrayList, amountList);
            mAdapter = new SummaryAdapter(SummaryDetailsActivity.this, summarylist,false);
            recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            recycler_view.setAdapter(mAdapter);
        //mAdapter.swap(arrayList,amountList);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.btnProcceed:

//                SharedPreferences sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
//                String x_access_token = sharedPreferences.getString("access_token","");
//                if (Util.isEmpty(x_access_token)) {
//                    //perform login
//                    Intent i1 = new Intent(SummaryDetailsActivity.this, LoginActivity.class);
//                    startActivity(i1);
//                } else {
//                    //do Payment as its logged in
//                    doPayment(x_access_token);
//                }
                if((startDate.getText().toString().trim().equals(""))&&(endDate.getText().toString().trim().equals("")))
                {
                    Toast.makeText(getApplicationContext(),"Please Select your visit time",Toast.LENGTH_SHORT).show();
                }else {

                    Long uid = Prefs.getLong("user_id",0);
                    Log.d(TAG,"shared_summary_id"+uid);
                    if (uid==0) {
                        Intent i = new Intent(SummaryDetailsActivity.this, Summary_login.class);
                        startActivity(i);
                    }else {
                        Intent i = new Intent(SummaryDetailsActivity.this, PaymentDetailsActivity.class);
                        startActivity(i);
                    }

                }
                break;
            case R.id.btnAddTest:
                Intent i2 = new Intent(SummaryDetailsActivity.this, AddTestActivity.class);
                Bundle bundle = new Bundle();
                Log.d(TAG,"dc_id ::"+dc_id);
                bundle.putLong("dc_id",dc_id);
                if(sel_test_ids.endsWith(","))
                {
                    sel_test_ids = sel_test_ids.substring(0,sel_test_ids.length()-1);
                }
                bundle.putString("sel_test_ids",sel_test_ids);
                Log.d(TAG,"dc_id seltest::"+sel_test_ids);
                bundle.putString("dc_name",dc_name);
                bundle.putString("loc",loc);
                i2.putExtras(bundle);
                if(cbHomePickup.isChecked())
                {
                    Prefs.putString("state","checked");
                    Prefs.putString("date1",startDate.getText().toString().trim());
                    Prefs.putString("date1",endDate.getText().toString().trim());

                }else {
                    Prefs.putString("state","");
                }

                startActivity(i2);
                break;
            case R.id.ivCalendar:
                if(cbHomePickup.isChecked())
                {
                    Calendar calendar = Calendar.getInstance();

//                    calendar.set(Calendar.DAY_OF_MONTH, 30);
//                    calendar.set(Calendar.MONTH, 0);
//                    calendar.set(Calendar.YEAR, 1991);
//                    calendar.set(Calendar.HOUR_OF_DAY, 11);
//                    calendar.set(Calendar.MINUTE, 13);

                    final Date defaultDate = calendar.getTime();
                    singleBuilder = new SingleDateAndTimePickerDialog.Builder(this)

                            .bottomSheet()
                            .curved()

                            .defaultDate(defaultDate)

                            .mustBeOnFuture()
                            //.titleTextColor(Color.GREEN)
                            //.backgroundColor(Color.BLACK)
                            //.mainColor(Color.GREEN)
                            .minutesStep(15)
                            .displayMinutes(true)
                            .displayHours(true)
                            .displayDays(true)
                            .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                                @Override
                                public void onDisplayed(SingleDateAndTimePicker picker) {

                                }
                            })

                            .title("   ")
                            .listener(new SingleDateAndTimePickerDialog.Listener() {
                                @Override
                                public void onDateSelected(Date date) {
                                    startDate.setText(simpleTimeFormat.format(date));
                                }
                            });
                    singleBuilder.display();
                }else {
                    Toast.makeText(SummaryDetailsActivity.this,"Select HomepickUp First",Toast.LENGTH_SHORT).show();
                }

                break;
           case R.id.startDate:
                if(cbHomePickup.isChecked())
                {
                    Calendar calendar = Calendar.getInstance();

//                    calendar.set(Calendar.DAY_OF_MONTH, 30);
//                    calendar.set(Calendar.MONTH, 0);
//                    calendar.set(Calendar.YEAR, 1991);
//                    calendar.set(Calendar.HOUR_OF_DAY, 11);
//                    calendar.set(Calendar.MINUTE, 13);

                    final Date defaultDate = calendar.getTime();
                    singleBuilder = new SingleDateAndTimePickerDialog.Builder(this)

                            .bottomSheet()
                            .curved()

                            .defaultDate(defaultDate)

                            .mustBeOnFuture()
                            //.titleTextColor(Color.GREEN)
                            //.backgroundColor(Color.BLACK)
                            //.mainColor(Color.GREEN)
                            .minutesStep(15)
                            .displayMinutes(true)
                            .displayHours(true)
                            .displayDays(true)
                            .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                                @Override
                                public void onDisplayed(SingleDateAndTimePicker picker) {

                                }
                            })

                            .title("  ")
                            .listener(new SingleDateAndTimePickerDialog.Listener() {
                                @Override
                                public void onDateSelected(Date date) {
                                    startDate.setText(simpleTimeFormat.format(date));
                                }
                            });
                    singleBuilder.display();
                }else {
                    Toast.makeText(SummaryDetailsActivity.this,"Select HomepickUp First",Toast.LENGTH_SHORT).show();
                }




                break;
            case R.id.rl_startDate:

               if(cbHomePickup.isChecked())
               {
                   Calendar calendar = Calendar.getInstance();

//                   calendar.set(Calendar.DAY_OF_MONTH, 30);
//                   calendar.set(Calendar.MONTH, 0);
//                   calendar.set(Calendar.YEAR, 1991);
//                   calendar.set(Calendar.HOUR_OF_DAY, 11);
//                   calendar.set(Calendar.MINUTE, 13);

                   final Date defaultDate = calendar.getTime();
                   singleBuilder = new SingleDateAndTimePickerDialog.Builder(this)

                           .bottomSheet()
                           .curved()

                           .defaultDate(defaultDate)

                           .mustBeOnFuture()
                           //.titleTextColor(Color.GREEN)
                           //.backgroundColor(Color.BLACK)
                           //.mainColor(Color.GREEN)
                           .minutesStep(15)
                           .displayMinutes(true)
                           .displayHours(true)
                           .displayDays(true)
                           .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                               @Override
                               public void onDisplayed(SingleDateAndTimePicker picker) {

                               }
                           })

                           .title("  ")
                           .listener(new SingleDateAndTimePickerDialog.Listener() {
                               @Override
                               public void onDateSelected(Date date) {
                                   startDate.setText(simpleTimeFormat.format(date));
                               }
                           });
                   singleBuilder.display();
               }else {
                    Toast.makeText(SummaryDetailsActivity.this,"Select HomepickUp First",Toast.LENGTH_SHORT).show();
               }




                break;
            case R.id.endDate:
//                if(cbHomePickup.isChecked())
//                {
                    Calendar calendar = Calendar.getInstance();

//                    calendar.set(Calendar.DAY_OF_MONTH, 30);
//                    calendar.set(Calendar.MONTH, 0);
//                    calendar.set(Calendar.YEAR, 1991);
//                    calendar.set(Calendar.HOUR_OF_DAY, 11);
//                    calendar.set(Calendar.MINUTE, 13);

                    final Date defaultDate = calendar.getTime();
                    singleBuilder = new SingleDateAndTimePickerDialog.Builder(this)

                            .bottomSheet()
                            .curved()

                            .defaultDate(defaultDate)

                            .mustBeOnFuture()
                            //.titleTextColor(Color.GREEN)
                            //.backgroundColor(Color.BLACK)
                            //.mainColor(Color.GREEN)
                            .minutesStep(15)
                            .displayMinutes(true)
                            .displayHours(true)
                            .displayDays(true)
                            .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                                @Override
                                public void onDisplayed(SingleDateAndTimePicker picker) {

                                }
                            })

                            .title("  ")
                            .listener(new SingleDateAndTimePickerDialog.Listener() {
                                @Override
                                public void onDateSelected(Date date) {
                                    endDate.setText(simpleTimeFormat.format(date));
                                }
                            });
                    singleBuilder.display();
//                }else {
//                    Toast.makeText(SummaryDetailsActivity.this,"Select HomepickUp First",Toast.LENGTH_SHORT).show();
//                }
                break;

            case R.id.ivCalendar1:
//                if(cbHomePickup.isChecked())
//                {
                    Calendar calendar1 = Calendar.getInstance();

//                    calendar1.set(Calendar.DAY_OF_MONTH, 30);
//                    calendar1.set(Calendar.MONTH, 0);
//                    calendar1.set(Calendar.YEAR, 1991);
//                    calendar1.set(Calendar.HOUR_OF_DAY, 11);
//                    calendar1.set(Calendar.MINUTE, 13);

                    final Date defaultDate1 = calendar1.getTime();
                    singleBuilder = new SingleDateAndTimePickerDialog.Builder(this)

                            .bottomSheet()
                            .curved()

                            .defaultDate(defaultDate1)

                            .mustBeOnFuture()
                            //.titleTextColor(Color.GREEN)
                            //.backgroundColor(Color.BLACK)
                            //.mainColor(Color.GREEN)
                            .minutesStep(15)
                            .displayMinutes(true)
                            .displayHours(true)
                            .displayDays(true)
                            .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                                @Override
                                public void onDisplayed(SingleDateAndTimePicker picker) {

                                }
                            })

                            .title("  ")
                            .listener(new SingleDateAndTimePickerDialog.Listener() {
                                @Override
                                public void onDateSelected(Date date) {
                                    endDate.setText(simpleTimeFormat.format(date));
                                }
                            });
                    singleBuilder.display();
//                }else {
//                    Toast.makeText(SummaryDetailsActivity.this,"Select HomepickUp First",Toast.LENGTH_SHORT).show();
//                }
                break;

            case R.id.rl_enddate:

//                if(cbHomePickup.isChecked())
//                {
                    Calendar calendar2 = Calendar.getInstance();

//                    calendar2.set(Calendar.DAY_OF_MONTH, 30);
//                    calendar2.set(Calendar.MONTH, 0);
//                    calendar2.set(Calendar.YEAR, 1991);
//                    calendar2.set(Calendar.HOUR_OF_DAY, 11);
//                    calendar2.set(Calendar.MINUTE, 13);

                    final Date defaultDate2 = calendar2.getTime();
                    singleBuilder = new SingleDateAndTimePickerDialog.Builder(this)

                            .bottomSheet()
                            .curved()

                            .defaultDate(defaultDate2)

                            .mustBeOnFuture()
                            //.titleTextColor(Color.GREEN)
                            //.backgroundColor(Color.BLACK)
                            //.mainColor(Color.GREEN)
                            .minutesStep(15)
                            .displayMinutes(true)
                            .displayHours(true)
                            .displayDays(true)
                            .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                                @Override
                                public void onDisplayed(SingleDateAndTimePicker picker) {

                                }
                            })

                            .title(" ")
                            .listener(new SingleDateAndTimePickerDialog.Listener() {
                                @Override
                                public void onDateSelected(Date date) {
                                    endDate.setText(simpleTimeFormat.format(date));
                                }
                            });
                    singleBuilder.display();
                    //.displayMonth(true)
                    //.displayYears(true)

//                new SingleDateAndTimePickerDialog.Builder(SummaryDetailsActivity.this)
//                        //.bottomSheet()
//                        //.curved()
//                        .minutesStep(15)
//
//                        //.displayHours(false)
//                        //.displayMinutes(false)
//
//                        //.todayText("aujourd'hui")
//
//                        .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
//                            @Override
//                            public void onDisplayed(SingleDateAndTimePicker picker) {
//                                //retrieve the SingleDateAndTimePicker
//                            }
//                        })
//
//                        .title("End Date")
//                        .listener(new SingleDateAndTimePickerDialog.Listener() {
//                            @Override
//                            public void onDateSelected(Date date) {
//                               int date1 = date.getDate();
//                               int date2 = date.getMonth();
//                               int date3 = date.getYear();
//                               int date4 = date.getHours();
//                               int date5 = date.getMinutes();
//                               int date6 = date.getSeconds();
//                               long date7 = date.getTime();
//                               Log.d(TAG,"date e1 ::"+date1);
//                               Log.d(TAG,"date e2 ::"+date2);
//                               Log.d(TAG,"date e3 ::"+date3);
//                               Log.d(TAG,"date e4 ::"+date4);
//                               Log.d(TAG,"date e5 ::"+date5);
//                               Log.d(TAG,"date e6 ::"+date6);
//                               Log.d(TAG,"date e7 ::"+date7);
//
//                                endDate.setText(String.valueOf(date));
//                            }
//                        }).display();
//                }else {
//                    Toast.makeText(SummaryDetailsActivity.this,"Select HomepickUp First",Toast.LENGTH_SHORT).show();
//                }
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
    protected void onPause() {
        super.onPause();
        if (singleBuilder != null)
            singleBuilder.dismiss();
        if (doubleBuilder != null)
            doubleBuilder.dismiss();
    }
    @Override
    public void onBackPressed() {
//        Intent i=new Intent(SummaryDetailsActivity.this,AddTestActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(i);
//        finish();
        super.onBackPressed();
    }

}
