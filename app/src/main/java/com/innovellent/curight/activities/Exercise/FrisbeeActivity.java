package com.innovellent.curight.activities.Exercise;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.activities.HomeActivity;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.AddExerciseResponse;
import com.innovellent.curight.model.MyCalorieResponse;
import com.innovellent.curight.model.PostBodyAddExersize;
import com.innovellent.curight.model.PostBodyCalorie;
import com.innovellent.curight.model.Result_CAl;
import com.innovellent.curight.utility.Config;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mak on 1/30/2018.
 */

public class FrisbeeActivity extends Activity implements View.OnClickListener{

    private static final String TAG = ".Curight";
    private static String activitytype;
    Button btnSubmit;
    String format;
    EditText etSpeed,tvTextDate,tvTextTime;
    ArrayList<String> arrayList = new ArrayList<String>();
    LinearLayout llTime, llDate;
    TextView title;
    Toolbar toolbar;
    ImageView ivCustom,ivSlow,ivMedium,ivFast,ivback1_exersize;
    EditText distanceCovered,atTime,tvSpeed;
    LinearLayout llspeedselecttext,llspeedselecticon;
    private int mYear, mMonth, mDay;
    private StringBuilder date;
    private TextView tvSlow,tvMedium,tvFast,tvCustom;
    private int mHour, mMinute, mhour1, mhour2, mhour3, minute1, minute2, minute3, mSeconds, seconds1, seconds2, seconds3;
    private TextView calsBurned,tvBurned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking);
        init();
        iniClick();
        llspeedselecttext = (LinearLayout) findViewById(R.id.llspeedselecttext);
        llspeedselecticon = (LinearLayout) findViewById(R.id.llspeedselecticon);
        llspeedselecttext.setVisibility(View.GONE);
        llspeedselecticon.setVisibility(View.GONE);
        tvSpeed.setVisibility(View.GONE);
        distanceCovered.setVisibility(View.INVISIBLE);
        activitytype ="frisbee";
        title.setText("Frisbee");
        atTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>0)
                {
                    int uid = (int) Prefs.getLong("user_id",0);
                    Log.d(TAG,"Shared_profile_uid"+uid);
                    getcaloriesapi(activitytype,uid,editable.toString());
                }else {
                    tvBurned.setText("");
                }
            }
        });

    }

    private void getcaloriesapi(String activity, int userid, String duration) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface reditapi = retrofit.create(ApiInterface.class);
        PostBodyCalorie postcalorie = new PostBodyCalorie(activity,userid,duration);
        Call<MyCalorieResponse> call = reditapi.getCalorie(postcalorie);
        call.enqueue(new Callback<MyCalorieResponse>() {
            @Override
            public void onResponse(Call<MyCalorieResponse> call, Response<MyCalorieResponse> response) {

                if(response.body()!=null) {
                    Log.e(TAG, "CalorieResponse: code: " + response.body().getCode());
                    Result_CAl resultcal = response.body().getResults();
                    Log.e(TAG, "CalorieResponse: calories: " + resultcal.getCalories());
                    tvBurned.setText(String.valueOf(resultcal.getCalories()));
                }

            }

            @Override
            public void onFailure(Call<MyCalorieResponse> call, Throwable t) {

            }
        });
    }

    public void init() {
        toolbar=(Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        if(getSupportActionBar()!=null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }
        ivback1_exersize = (ImageView) findViewById(R.id.ivback1_exersize);
        tvTextTime = (EditText) findViewById(R.id.tvTextTime);
        tvBurned = (TextView) findViewById(R.id.tvBurned);
        title = (TextView) findViewById(R.id.title);
        llTime = (LinearLayout) findViewById(R.id.llTime);
        tvTextDate = (EditText) findViewById(R.id.tvTextDate);
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
        tvSpeed=(EditText) findViewById(R.id.tvSpeed);
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
        ivback1_exersize.setOnClickListener(this);


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
                            // Toast.makeText(WalkingActivity.this,"date",Toast.LENGTH_SHORT).show();
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
                activitytype ="walkingslow";
                tvSpeed.setText("1");
                etSpeed.setVisibility(View.GONE);
                break;
            case R.id.ivMedium:
                setColor();
                tvSpeed.setText("2");
                activitytype ="walkingmedium";
                tvSpeed.setVisibility(View.VISIBLE);
                etSpeed.setVisibility(View.GONE);
                break;
            case R.id.ivFast:
                setColor();
                tvSpeed.setText("3");
                activitytype ="walkingfast";
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
//                addExercise();
                addnewExercise(activitytype);
                break;
            case R.id.ivback1_exersize:
                Intent hm = new Intent(this,HomeActivity.class);
                hm.putExtra("source", "exersize");
                startActivity(hm);
                break;
        }
    }

    private void addnewExercise(String activitytype) {
        String calorie_burn;
        if(tvTextDate.getText().toString().trim().equals(""))
        {
            Toast.makeText(FrisbeeActivity.this,"Please enter Date",Toast.LENGTH_SHORT).show();
        }else if(tvTextTime.getText().toString().trim().equals("")){
            Toast.makeText(FrisbeeActivity.this,"Please enter Time",Toast.LENGTH_SHORT).show();
        }else if(atTime.getText().toString().trim().equals(""))
        {
            Toast.makeText(FrisbeeActivity.this,"Please enter Time Spend",Toast.LENGTH_SHORT).show();
        }else {

            if(calsBurned.getText().toString().trim().equals(""))
            {
                calorie_burn = tvBurned.getText().toString();
                addapicall(activitytype,calorie_burn);
            }else {
                calorie_burn = calsBurned.getText().toString();
                addapicall(activitytype,calorie_burn);
            }
        }
    }

    private void addapicall(String exercisetype,String calorie_burn) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        int uid = (int) Prefs.getLong("user_id",0);
        ApiInterface reditapi = retrofit.create(ApiInterface.class);
        PostBodyAddExersize addexersize = new PostBodyAddExersize(String.valueOf(uid),exercisetype,tvTextDate.getText().toString(),atTime.getText().toString(),tvTextTime.getText().toString(),tvSpeed.getText().toString(),distanceCovered.getText().toString(),calorie_burn);
        Call<AddExerciseResponse> call = reditapi.createCalorie(addexersize);
        call.enqueue(new Callback<AddExerciseResponse>() {
            @Override
            public void onResponse(Call<AddExerciseResponse> call, Response<AddExerciseResponse> response) {

                if(response.body()!=null) {
                    Log.e(TAG, "ExersizeResponse: code: " + response.body().getCode());

                    if(response.body().getCode()==200)
                    {
                        Toast.makeText(FrisbeeActivity.this,"Successfully Added",Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<AddExerciseResponse> call, Throwable t) {

            }
        });

    }

    public void setColor() {


        tvSlow.setTextColor(Color.parseColor("#9DA1A0"));
        tvMedium.setTextColor(Color.parseColor("#9DA1A0"));
        tvFast.setTextColor(Color.parseColor("#9DA1A0"));
        tvCustom.setTextColor(Color.parseColor("#9DA1A0"));


    }

//    public void addExercise() {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(new Config().SERVER_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
//
//        SharedPreferences sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
//        String x_access_token = sharedPreferences.getString("access_token","");
//        long user_id = sharedPreferences.getLong("user_id",0L);
//
//        CreateExercise createExercise = new CreateExercise(user_id+"","Walking",tvTextDate.getText().toString(),tvTextTime.getText().toString(),atTime.getText().toString(),tvSpeed.getText().toString(),distanceCovered.getText().toString(),calsBurned.getText().toString());
//
//        Call<ServerResponseCreateExercise> call = apiInterface.createExercise(x_access_token,createExercise);
//
//        Log.e("CR_EXERCISE","Request URL :: "+call.request().url());
//
//        call.enqueue(new Callback<ServerResponseCreateExercise>() {
//            @Override
//            public void onResponse(Call<ServerResponseCreateExercise> call, Response<ServerResponseCreateExercise> response) {
//
//                if (response.isSuccessful()) {
//
//                    ServerResponseCreateExercise res = response.body();
//
//                    Log.e("CR_EXERCISE","Results ::  "+res.getResults() + "  ## Code ##   "+res.getCode());
//
//                    finish();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ServerResponseCreateExercise> call, Throwable t) {
//
//            }
//        });
//
//    }
@Override
public void onBackPressed() {
    Intent hm = new Intent(this,HomeActivity.class);
    hm.putExtra("source", "exersize");
    startActivity(hm);

    //    super.onBackPressed();
}

}

