package com.innovellent.curight.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;


import com.innovellent.curight.R;
import com.innovellent.curight.adapter.DiagnosticTestAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.BookedTest;
import com.innovellent.curight.model.CreateExercise;
import com.innovellent.curight.model.ServerResponseBookedTest;
import com.innovellent.curight.model.ServerResponseCreateExercise;
import com.innovellent.curight.utility.Config;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sagar on 9/8/2017.
 */

public class WalkingActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recycler_view;
    DiagnosticTestAdapter mAdapter;
    Button btnSubmit;
    String format;
    EditText etSpeed;
    ArrayList<String> arrayList = new ArrayList<String>();
    LinearLayout llTime, llDate;
    TextView tvTextTime, tvTextDate;
    private int mYear, mMonth, mDay;
    private StringBuilder date;
    Toolbar toolbar;
    private TextView tvSlow,tvMedium,tvFast,tvCustom,tvSpeed;
    ImageView ivCustom,ivSlow,ivMedium,ivFast;
    private int mHour, mMinute, mhour1, mhour2, mhour3, minute1, minute2, minute3, mSeconds, seconds1, seconds2, seconds3;
    private EditText atTime;
    private TextView distanceCovered,calsBurned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking);
        init();
        iniClick();

    }

    public void init() {
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        tvTextTime = (TextView) findViewById(R.id.tvTextTime);
        llTime = (LinearLayout) findViewById(R.id.llTime);
        tvTextDate = (TextView) findViewById(R.id.tvTextDate);
        atTime = (EditText) findViewById(R.id.atTime);
        llDate = (LinearLayout) findViewById(R.id.llDate);
        ivSlow=(ImageView)findViewById(R.id.ivSlow);
        ivMedium=(ImageView)findViewById(R.id.ivMedium);
        ivFast=(ImageView)findViewById(R.id.ivFast);
        ivCustom=(ImageView)findViewById(R.id.ivCustom);
        tvSlow=(TextView)findViewById(R.id.tvSlow);
        tvMedium=(TextView)findViewById(R.id.tvMedium);
        tvFast=(TextView)findViewById(R.id.tvFast);
        tvCustom=(TextView)findViewById(R.id.tvCustom);
        tvSpeed=(TextView)findViewById(R.id.tvSpeed);
        etSpeed=(EditText)findViewById(R.id.etSpeed);

        distanceCovered = (EditText) findViewById(R.id.distanceCovered);
        calsBurned = (EditText) findViewById(R.id.calsBurned);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);



    }

    public void iniClick() {
        llTime.setOnClickListener(this);
        llDate.setOnClickListener(this);
        ivSlow.setOnClickListener(this);
        ivMedium.setOnClickListener(this);
        ivFast.setOnClickListener(this);
        ivCustom.setOnClickListener(this);
        tvSlow.setOnClickListener(this);
        tvMedium.setOnClickListener(this);
        tvFast.setOnClickListener(this);
        tvCustom.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        arrayList.add("New York,NY");
        arrayList.add("Los Angeles,CA");
        arrayList.add("Chicago,IL");
        arrayList.add("Houston,TX");
        arrayList.add("NEW Delhi,DL");
        arrayList.add("Mumbai,MH");
        arrayList.add("Bangalore,KA");
    }


    public void selectDate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
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
                        tvTextDate.setText(year + "-" + (monthYear) + "-"  + dayOfMonth);


                    }
                }, mYear, mMonth, mDay);


        datePickerDialog.show();
    }

    private void timePicker() {

        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {


                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        mhour2 = hourOfDay;
                        minute2 = minute;
                        seconds2 = mSeconds;

                        if (hourOfDay == 0) {

                            hourOfDay += 12;

                            format = "AM";
                        } else if (hourOfDay == 12) {

                            format = "PM";

                        } else if (hourOfDay > 12) {

                            hourOfDay -= 12;

                            format = "PM";

                        } else {

                            format = "AM";
                        }

                        if (mhour2 > 7 && mhour2 < 19) {
                            if (hourOfDay <= 9 && minute <= 9) {
                                date = new StringBuilder().append("0").append(hourOfDay).append(" : 0").append(minute).append(" ").append(format);
                            } else if (hourOfDay <= 9) {
                                date = new StringBuilder().append("0").append(hourOfDay).append(" : ").append(minute).append(" ").append(format);
                            } else if (minute <= 9) {
                                date = new StringBuilder().append(hourOfDay).append(" : 0").append(minute).append(" ").append(format);
                            } else {
                                date = new StringBuilder().append(hourOfDay).append(" : ").append(minute).append(" ").append(format);
                            }

                            tvTextTime.setText(date);


                        } else {

                        }

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.llTime:
                timePicker();
                break;
            case R.id.ivSlow:
                setColor();
                tvSpeed.setVisibility(View.VISIBLE);
                tvSpeed.setText("1");
                etSpeed.setVisibility(View.GONE);
                break;
            case R.id.ivMedium:
                setColor();
                tvSpeed.setText("2");
                tvSpeed.setVisibility(View.VISIBLE);
                etSpeed.setVisibility(View.GONE);
                break;
            case R.id.ivFast:
                setColor();
                tvSpeed.setText("3");
                tvSpeed.setVisibility(View.VISIBLE);
                etSpeed.setVisibility(View.GONE);
                break;
            case R.id.ivCustom:
                setColor();
                etSpeed.setVisibility(View.VISIBLE);
                tvSpeed.setVisibility(View.GONE);
                break;
            case R.id.llDate:
                selectDate();
                break;
            case R.id.btnSubmit:
                addExercise();
                break;
        }
    }
    public void setColor() {


        tvSlow.setTextColor(Color.parseColor("#9DA1A0"));
        tvMedium.setTextColor(Color.parseColor("#9DA1A0"));
        tvFast.setTextColor(Color.parseColor("#9DA1A0"));
        tvCustom.setTextColor(Color.parseColor("#9DA1A0"));


    }

    public void addExercise() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        SharedPreferences sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String x_access_token = sharedPreferences.getString("access_token","");
        long user_id = sharedPreferences.getLong("user_id",0L);

        CreateExercise createExercise = new CreateExercise(user_id+"","Walking",tvTextDate.getText().toString(),tvTextTime.getText().toString(),atTime.getText().toString(),tvSpeed.getText().toString(),distanceCovered.getText().toString(),calsBurned.getText().toString());

        Call<ServerResponseCreateExercise> call = apiInterface.createExercise(x_access_token,createExercise);

        Log.e("CR_EXERCISE","Request URL :: "+call.request().url());

        call.enqueue(new Callback<ServerResponseCreateExercise>() {
            @Override
            public void onResponse(Call<ServerResponseCreateExercise> call, Response<ServerResponseCreateExercise> response) {

                if (response.isSuccessful()) {

                    ServerResponseCreateExercise res = response.body();

                    Log.e("CR_EXERCISE","Results ::  "+res.getResults() + "  ## Code ##   "+res.getCode());

                    finish();

                }
            }

            @Override
            public void onFailure(Call<ServerResponseCreateExercise> call, Throwable t) {

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
