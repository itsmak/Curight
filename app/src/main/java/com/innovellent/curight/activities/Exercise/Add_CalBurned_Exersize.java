package com.innovellent.curight.activities.Exercise;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.activities.DiagnosticTestListActivity;
import com.innovellent.curight.activities.HomeActivity;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.AddExerciseResponse;
import com.innovellent.curight.model.MyCalorieResponse;
import com.innovellent.curight.model.PostBodyAddExersize;
import com.innovellent.curight.model.PostBodyCalorie;
import com.innovellent.curight.model.Result_CAl;
import com.innovellent.curight.utility.Config;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static com.innovellent.curight.utility.Constants.BICYCLING;
import static com.innovellent.curight.utility.Constants.FRISBEE;
import static com.innovellent.curight.utility.Constants.GOLF;
import static com.innovellent.curight.utility.Constants.RACQUETBALL;
import static com.innovellent.curight.utility.Constants.ROWING;
import static com.innovellent.curight.utility.Constants.RUNNING;
import static com.innovellent.curight.utility.Constants.SOCCER;
import static com.innovellent.curight.utility.Constants.SOFTBALL;
import static com.innovellent.curight.utility.Constants.SWIMMING;
import static com.innovellent.curight.utility.Constants.TENNIS;
import static com.innovellent.curight.utility.Constants.TITLE;
import static com.innovellent.curight.utility.Constants.TRAILBIKING;
import static com.innovellent.curight.utility.Constants.VOLLEYBALL;
import static com.innovellent.curight.utility.Constants.WALKING;
import static com.innovellent.curight.utility.Constants.WEIGHTLIFTING;
import static com.innovellent.curight.utility.Constants.WRESTLING;
import static com.innovellent.curight.utility.Constants.YOGA;

/**
 * Created by Mak on 3/23/2018.
 */
public class Add_CalBurned_Exersize extends Activity implements View.OnClickListener {

    private static final String TAG = ".Curight";
    private static String activitytype;
    TextView tv_title,tvBurned,tvSlow,tvMedium,tvFast,tvCustom,calsBurned;
    LinearLayout llTime, llDate;
    Toolbar toolbar;
    EditText tvTextDate,tvTextTime,distanceCovered,atTime,tvSpeed;
    ImageView ivCustom,ivSlow,ivMedium,ivFast,ivback1_exersize;
    Button btnSubmit;
    String format;
    String finaldate;
    LinearLayout llspeedselecttext,llspeedselecticon;
    private String title;
    private ProgressDialog progressDialog;
    private StringBuilder date;
    private int mYear, mMonth, mDay;
    private int mHour, mMinute, mhour1, mhour2, mhour3, minute1, minute2, minute3, mSeconds, seconds1, seconds2, seconds3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking);
        init();
        setupToolbar();
        iniClick();
        final Calendar c = Calendar.getInstance();
        int monthnumbr = c.get(Calendar.MONTH)+1;
        int daynumber = c.get(Calendar.DATE);
        String month,day;
        if (monthnumbr >= 1 && monthnumbr <= 9) {
            month = "0" + monthnumbr;
        } else {
            month = monthnumbr + "";
        }
        if (daynumber >= 1 && daynumber <= 9) {
            day = "0" + daynumber;
        } else {
            day = daynumber + "";
        }
        finaldate = c.get(Calendar.YEAR) +"-"+month+"-" +day;
        tvTextDate.setText(finaldate);
        tvSpeed.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if(atTime.getText().toString().equals(""))
                {
                 //   Toast.makeText(Add_CalBurned_Exersize.this, "Please enter Time", Toast.LENGTH_SHORT).show();

//                   // atTime.requestFocus();

                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(tvSpeed.getWindowToken(), 0);

                }else {
                    double time_double = Double.parseDouble(atTime.getText().toString());
                     if(tvSpeed.getText().toString().equals(""))
                    {

                    }else {
                        double time_speed = Double.parseDouble(tvSpeed.getText().toString());
                        double distance = (time_speed)*(time_double/60);
                         DecimalFormat dff=new DecimalFormat(".##");
                        distanceCovered.setText(String.valueOf(dff.format(distance)));
//                        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                        mgr.hideSoftInputFromWindow(tvSpeed.getWindowToken(), 0);
                    }

                }


                return false;
            }
        });
        distanceCovered.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if(atTime.getText().toString().equals(""))
                {
//                    Toast.makeText(Add_CalBurned_Exersize.this, "Please enter Time", Toast.LENGTH_SHORT).show();
//                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    mgr.hideSoftInputFromWindow(distanceCovered.getWindowToken(), 0);
                    //  distanceCovered.setText("");
                }else {
                    if(distanceCovered.getText().toString().equals(""))
                    {

                    }else {
                        double time_double = Double.parseDouble(atTime.getText().toString());
                        double distance_double = Double.parseDouble(distanceCovered.getText().toString());
                        double speed_double = (distance_double/time_double)*60;
                        DecimalFormat dff=new DecimalFormat(".##");
                        tvSpeed.setText(String.valueOf(dff.format(speed_double)));
//                        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                        mgr.hideSoftInputFromWindow(tvSpeed.getWindowToken(), 0);
                    }

                }
                return false;
            }
        });

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
                    if(tvSpeed.getText().toString().equals(""))
                    {

                    }else {
                        double time_double = Double.parseDouble(editable.toString());
                        double time_speed = Double.parseDouble(tvSpeed.getText().toString());
                        double distance = (time_speed)*(time_double/60);
                        DecimalFormat dff=new DecimalFormat(".##");
                        distanceCovered.setText(String.valueOf(dff.format(distance)));
                    }
                    int uid = (int) Prefs.getLong("user_id",0);
                    Log.d(TAG,"Shared_profile_uid"+uid);
//                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    mgr.hideSoftInputFromWindow(atTime.getWindowToken(), 0);
                    getcaloriesapi(title,uid,editable.toString());
//                    tvSpeed.setText("");
//                    distanceCovered.setText("");
                }else {
                    tvBurned.setText("");
                }
            }
        });
        llDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                switch(motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        selectDate();
                        break;
                    default:
                        return false;

                }
                return false;
            }
        });
        tvTextDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                switch(motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        selectDate();
                        break;
                    default:
                        return false;

                }
                return false;
            }
        });
        llTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                switch(motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        timePicker();
                        break;
                    default:
                        return false;

                }
                return false;
            }
        });
        tvTextTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                switch(motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        timePicker();
                        break;
                    default:
                        return false;

                }
                return false;
            }
        });
    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        tv_title = (TextView) findViewById(R.id.title);
        tvTextTime = (EditText) findViewById(R.id.tvTextTime);
        tvBurned = (TextView) findViewById(R.id.tvBurned);
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
 //       etSpeed=(EditText)findViewById(R.id.etSpeed);
        ivback1_exersize = (ImageView) findViewById(R.id.ivback1_exersize);
        llspeedselecttext = (LinearLayout) findViewById(R.id.llspeedselecttext);
        llspeedselecticon = (LinearLayout) findViewById(R.id.llspeedselecticon);
        distanceCovered = (EditText) findViewById(R.id.distanceCovered);
        calsBurned = (EditText) findViewById(R.id.calsBurned);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
    }

    private void setupToolbar() {

        title = getIntent().getStringExtra(TITLE);
        if (title != null) {
            switch (title) {
                case WALKING:
                    title = WALKING;
                    break;
                case RUNNING:
                    title = RUNNING;
                    break;
                case BICYCLING:
                    title = BICYCLING;
                    break;
                case SWIMMING:
                    title = SWIMMING;
                    llspeedselecttext.setVisibility(View.GONE);
                    llspeedselecticon.setVisibility(View.GONE);
                    tvSpeed.setVisibility(View.GONE);
                    distanceCovered.setVisibility(View.INVISIBLE);
                    tvSpeed.setText("0");
                    distanceCovered.setText("0");
                    break;
                case GOLF:
                    title = GOLF;
                    llspeedselecttext.setVisibility(View.GONE);
                    llspeedselecticon.setVisibility(View.GONE);
                    tvSpeed.setVisibility(View.GONE);
                    distanceCovered.setVisibility(View.INVISIBLE);
                    tvSpeed.setText("0");
                    distanceCovered.setText("0");
                    break;
                case FRISBEE:
                    title = FRISBEE;
                    llspeedselecttext.setVisibility(View.GONE);
                    llspeedselecticon.setVisibility(View.GONE);
                    tvSpeed.setVisibility(View.GONE);
                    distanceCovered.setVisibility(View.INVISIBLE);
                    tvSpeed.setText("0");
                    distanceCovered.setText("0");
                    break;
                case RACQUETBALL:
                    title = RACQUETBALL;
                    llspeedselecttext.setVisibility(View.GONE);
                    llspeedselecticon.setVisibility(View.GONE);
                    tvSpeed.setVisibility(View.GONE);
                    distanceCovered.setVisibility(View.INVISIBLE);
                    tvSpeed.setText("0");
                    distanceCovered.setText("0");
                    break;
                case ROWING:
                    llspeedselecttext.setVisibility(View.GONE);
                    llspeedselecticon.setVisibility(View.GONE);
                    tvSpeed.setVisibility(View.GONE);
                    distanceCovered.setVisibility(View.INVISIBLE);
                    tvSpeed.setText("0");
                    distanceCovered.setText("0");
                    title = ROWING;
                    break;
                case SOCCER:
                    llspeedselecttext.setVisibility(View.GONE);
                    llspeedselecticon.setVisibility(View.GONE);
                    tvSpeed.setVisibility(View.GONE);
                    distanceCovered.setVisibility(View.INVISIBLE);
                    tvSpeed.setText("0");
                    distanceCovered.setText("0");
                    title = SOCCER;
                    break;
                case SOFTBALL:
                    llspeedselecttext.setVisibility(View.GONE);
                    llspeedselecticon.setVisibility(View.GONE);
                    tvSpeed.setVisibility(View.GONE);
                    distanceCovered.setVisibility(View.INVISIBLE);
                    tvSpeed.setText("0");
                    distanceCovered.setText("0");
                    title = SOFTBALL;
                    break;
                case TENNIS:
                    llspeedselecttext.setVisibility(View.GONE);
                    llspeedselecticon.setVisibility(View.GONE);
                    tvSpeed.setVisibility(View.GONE);
                    distanceCovered.setVisibility(View.INVISIBLE);
                    tvSpeed.setText("0");
                    distanceCovered.setText("0");
                    title = TENNIS;
                    break;
                case TRAILBIKING:
                    llspeedselecttext.setVisibility(View.GONE);
                    llspeedselecticon.setVisibility(View.GONE);
                    tvSpeed.setVisibility(View.GONE);
                    distanceCovered.setVisibility(View.INVISIBLE);
                    tvSpeed.setText("0");
                    distanceCovered.setText("0");
                    title = TRAILBIKING;
                    break;

                case VOLLEYBALL:
                    llspeedselecttext.setVisibility(View.GONE);
                    llspeedselecticon.setVisibility(View.GONE);
                    tvSpeed.setVisibility(View.GONE);
                    distanceCovered.setVisibility(View.INVISIBLE);
                    tvSpeed.setText("0");
                    distanceCovered.setText("0");
                    title = VOLLEYBALL;
                    break;

                case WEIGHTLIFTING:
                    llspeedselecttext.setVisibility(View.GONE);
                    llspeedselecticon.setVisibility(View.GONE);
                    tvSpeed.setVisibility(View.GONE);
                    distanceCovered.setVisibility(View.INVISIBLE);
                    tvSpeed.setText("0");
                    distanceCovered.setText("0");
                    title = WEIGHTLIFTING;
                    break;

                case WRESTLING:
                    llspeedselecttext.setVisibility(View.GONE);
                    llspeedselecticon.setVisibility(View.GONE);
                    tvSpeed.setVisibility(View.GONE);
                    distanceCovered.setVisibility(View.INVISIBLE);
                    tvSpeed.setText("0");
                    distanceCovered.setText("0");
                    title = WRESTLING;
                    break;

                case YOGA:
                    llspeedselecttext.setVisibility(View.GONE);
                    llspeedselecticon.setVisibility(View.GONE);
                    tvSpeed.setVisibility(View.GONE);
                    distanceCovered.setVisibility(View.INVISIBLE);
                    tvSpeed.setText("0");
                    distanceCovered.setText("0");
                    title = YOGA;
                    break;

                default:
                    title = "Curight";
                    break;
            }
            tv_title.setText(title);
        }
    }

    private void getcaloriesapi(String activity, int userid, String duration) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface reditapi = retrofit.create(ApiInterface.class);
        Log.e(TAG, "CalorieResponse: activity: " + activity);
        Log.e(TAG, "CalorieResponse: userid: " + userid);
        Log.e(TAG, "CalorieResponse: duration: " + duration);
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

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
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


    }
    public void setColor() {


        tvSlow.setTextColor(Color.parseColor("#9DA1A0"));
        tvMedium.setTextColor(Color.parseColor("#9DA1A0"));
        tvFast.setTextColor(Color.parseColor("#9DA1A0"));
        tvCustom.setTextColor(Color.parseColor("#9DA1A0"));


    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.llTime:
//                timePicker();
//                break;
            case R.id.ivSlow:
                setColor();
                tvSpeed.setVisibility(View.VISIBLE);
                if(title.equalsIgnoreCase("walking"))
                {activitytype ="walkingslow";}
                tvSpeed.setText("1");
                if(atTime.getText().toString().equals(""))
                {
//                    Toast.makeText(Add_CalBurned_Exersize.this, "Please enter Time first", Toast.LENGTH_SHORT).show();
//                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    mgr.hideSoftInputFromWindow(tvSpeed.getWindowToken(), 0);
                }else {
                    double time_double = Double.parseDouble(atTime.getText().toString());

                        double time_speed = 1;
                        double distance = (time_speed)*(time_double/60);
                        DecimalFormat dff=new DecimalFormat(".##");
                        distanceCovered.setText(String.valueOf(dff.format(distance)));
                }
             //   etSpeed.setVisibility(View.GONE);
                break;

            case R.id.ivMedium:
                setColor();
                tvSpeed.setText("2");
                if(title.equalsIgnoreCase("walking"))
                {activitytype ="walkingmedium";}
                tvSpeed.setVisibility(View.VISIBLE);
               // etSpeed.setVisibility(View.GONE);
                if(atTime.getText().toString().equals(""))
                {
//                    Toast.makeText(Add_CalBurned_Exersize.this, "Please enter Time first", Toast.LENGTH_SHORT).show();
//                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    mgr.hideSoftInputFromWindow(tvSpeed.getWindowToken(), 0);
                }else {
                    double time_double = Double.parseDouble(atTime.getText().toString());

                    double time_speed = 2;
                    double distance = (time_speed)*(time_double/60);
                    DecimalFormat dff=new DecimalFormat(".##");
                    distanceCovered.setText(String.valueOf(dff.format(distance)));
                }
                break;
            case R.id.ivFast:
                setColor();
                tvSpeed.setText("3");
                if(title.equalsIgnoreCase("walking"))
                {activitytype ="walkingfast";}
                tvSpeed.setVisibility(View.VISIBLE);
                if(atTime.getText().toString().equals(""))
                {
//                    Toast.makeText(Add_CalBurned_Exersize.this, "Please enter Time first", Toast.LENGTH_SHORT).show();
//                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    mgr.hideSoftInputFromWindow(tvSpeed.getWindowToken(), 0);
                }else {
                    double time_double = Double.parseDouble(atTime.getText().toString());

                    double time_speed = 3;
                    double distance = (time_speed)*(time_double/60);
                    DecimalFormat dff=new DecimalFormat(".##");
                    distanceCovered.setText(String.valueOf(dff.format(distance)));
                }
                break;
            case R.id.ivCustom:
                setColor();
             //   etSpeed.setVisibility(View.VISIBLE);
                tvSpeed.setVisibility(View.VISIBLE);
                break;
//            case R.id.llDate:
//                selectDate();
//                break;
//            case R.id.tvTextDate:
//                selectDate();
//                break;
//            case R.id.tvTextTime:
//                timePicker();
//                break;
            case R.id.btnSubmit:
//                addExercise();
                addnewExercise(title);
                break;
            case R.id.ivback1_exersize:
//                addExercise();
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
            Toast.makeText(Add_CalBurned_Exersize.this,"Please enter Date",Toast.LENGTH_SHORT).show();
        }else if(tvTextTime.getText().toString().trim().equals("")){
            Toast.makeText(Add_CalBurned_Exersize.this,"Please enter Time",Toast.LENGTH_SHORT).show();
        }
//        else if(distanceCovered.getText().toString().trim().equals("")){
//            Toast.makeText(Add_CalBurned_Exersize.this,"Please enter Distance",Toast.LENGTH_SHORT).show();
//        }
        else if(atTime.getText().toString().trim().equals(""))
        {
            Toast.makeText(Add_CalBurned_Exersize.this,"Please enter Time Spend",Toast.LENGTH_SHORT).show();
        }

        else {

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

        progressDialog = ProgressDialog.show(Add_CalBurned_Exersize.this, "Loading", "please wait", true, false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        int s_uid = (int) Prefs.getLong("spinner_id",0);
        int uid;
        if(s_uid==0)
        {
            uid = (int) Prefs.getLong("user_id",0);
        }else {
            uid = s_uid;
        }
      //  int uid = (int) Prefs.getLong("user_id",0);
        ApiInterface reditapi = retrofit.create(ApiInterface.class);
        Log.d(TAG,"add exercize :"+ exercisetype);
        PostBodyAddExersize addexersize = new PostBodyAddExersize(String.valueOf(uid),exercisetype,tvTextDate.getText().toString(),atTime.getText().toString(),tvTextTime.getText().toString(),tvSpeed.getText().toString(),distanceCovered.getText().toString(),calorie_burn);
        Call<AddExerciseResponse> call = reditapi.createCalorie(addexersize);
        call.enqueue(new Callback<AddExerciseResponse>() {
            @Override
            public void onResponse(Call<AddExerciseResponse> call, Response<AddExerciseResponse> response) {
                progressDialog.dismiss();
                if(response.body()!=null) {
                    Log.d(TAG,"add exercize response:"+ response.body());
                    Log.e(TAG, "ExersizeResponse: code: " + response.body().getCode());

                    if(response.body().getCode()==200)
                    {

                        Toast.makeText(Add_CalBurned_Exersize.this,"Successfully Added",Toast.LENGTH_SHORT).show();
                        Intent hm = new Intent(Add_CalBurned_Exersize.this,HomeActivity.class);
                        hm.putExtra("source", "exersize");
                        startActivity(hm);
                    }
                }

            }

            @Override
            public void onFailure(Call<AddExerciseResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent hm = new Intent(this,HomeActivity.class);
        hm.putExtra("source", "exersize");
        startActivity(hm);

        //    super.onBackPressed();
    }
}
