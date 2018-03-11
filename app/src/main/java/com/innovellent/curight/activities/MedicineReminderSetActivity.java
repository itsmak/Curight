package com.innovellent.curight.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.innovellent.curight.LoginActivity;
import com.innovellent.curight.R;
import com.innovellent.curight.adapter.CustomSpinnerAdapter1;
import com.innovellent.curight.adapter.CustomSpinnerAdapter2;
import com.innovellent.curight.adapter.CustomSpinnerAdapter3;
import com.innovellent.curight.adapter.DiagnosticTestAdapter;
import com.innovellent.curight.adapter.MedicineAdpater;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.Post_MedReminderAdd;
import com.innovellent.curight.model.Registration_Response;
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


public class MedicineReminderSetActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CuRight";
    RecyclerView recycler_view;
    MedicineAdpater mAdapter;
    Button btnSubmitmed;
    EditText etStrength,etDos,etNotes;
    TextView tvSelectMedicine, tvTimeMorning,tvDate;
    RadioButton rbDuration, rbDurationLifetime;
    Spinner spDays, spPeriod, spHow;
    ImageView ivBack, ivMorning, ivNoon, ivEvening, ivNight;
    boolean ismorningselected, isnoonselected, iseveningsselected, isnightselected;
    RelativeLayout rlMorning, rlNoon, rlEvening, rlNight;
    LinearLayout llMedicineName;
    Toolbar toolbar;
//    private static final String BASE_URL = "http://13.59.209.135:8090/diagnosticAPI/webapi/";
    DiagnosticTestAdapter mAdapter1;
    TextView tvTime, tvMorninglabel, tvTimeNoon, tvNoonlabel, tvTimeEvening, tvEveninglabel, tvTimetNight, tvNightlabel;
    ArrayList<String> arrayList = new ArrayList<String>();
    String[] spinner1 = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    String[] spinner2 = {"days", "Daily", "Weekly", "Monthly", "Yealy"};
    String[] spinner3 = {"How", "Injection", "External Use", "Oral"};
    String format;
    private int mYear, mMonth, mDay;
    private StringBuilder date;
    private int mHour, mMinute, mhour1, mhour2, mhour3, minute1, minute2, minute3, mSeconds, seconds1, seconds2, seconds3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_reminder_set);
        init();
        iniClick();
        getData();
    }

    public void init() {
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
       // ivBack = (ImageView) findViewById(R.id.ivBack);
        spDays = (Spinner) findViewById(R.id.spDays);
        btnSubmitmed = (Button) findViewById(R.id.btnSubmitmed);
        etStrength = (EditText) findViewById(R.id.etStrength);
        etDos = (EditText) findViewById(R.id.etDos);
        etNotes = (EditText) findViewById(R.id.etNotes);
        tvDate=(TextView)findViewById(R.id.tv_date);
        rbDurationLifetime = (RadioButton) findViewById(R.id.rbDurationLifetime);
        rbDuration = (RadioButton) findViewById(R.id.rbDuration);
        spPeriod = (Spinner) findViewById(R.id.spPeriod);
        spHow = (Spinner) findViewById(R.id.spHow);
        rlMorning = (RelativeLayout) findViewById(R.id.rlMorning);
        rlNoon = (RelativeLayout) findViewById(R.id.rlNoon);
        rlEvening = (RelativeLayout) findViewById(R.id.rlEvening);
        rlNight = (RelativeLayout) findViewById(R.id.rlNight);
        tvSelectMedicine = (TextView) findViewById(R.id.tvSelectMedicine);
        tvTimeMorning = (TextView) findViewById(R.id.tvTimeMorning);
        tvMorninglabel = (TextView) findViewById(R.id.tvMorninglabel);
        ivMorning = (ImageView) findViewById(R.id.ivMorning);
        tvTimeNoon = (TextView) findViewById(R.id.tvTimeNoon);
        ivNoon = (ImageView) findViewById(R.id.ivNoon);
        tvNoonlabel = (TextView) findViewById(R.id.tvNoonlabel);
        tvTimeEvening = (TextView) findViewById(R.id.tvTimeEvening);
        ivEvening = (ImageView) findViewById(R.id.ivEvening);
        tvEveninglabel = (TextView) findViewById(R.id.tvEveninglabel);
        tvTimetNight = (TextView) findViewById(R.id.tvTimetNight);
        ivNight = (ImageView) findViewById(R.id.ivNight);
        tvNightlabel = (TextView) findViewById(R.id.tvNightlabel);
        llMedicineName=(LinearLayout)findViewById(R.id.llMedicineName);

    }

    public void iniClick() {
        //ivBack.setOnClickListener(this);
        btnSubmitmed.setOnClickListener(this);
        llMedicineName.setOnClickListener(this);
        rlMorning.setOnClickListener(this);
        rlNight.setOnClickListener(this);
        rlEvening.setOnClickListener(this);
        rlNoon.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        tvTimeEvening.setOnClickListener(this);
        tvTimeNoon.setOnClickListener(this);
        tvTimeMorning.setOnClickListener(this);
        tvTimetNight.setOnClickListener(this);
        ivEvening.setImageResource(R.mipmap.ic_evening_gray);
        ivMorning.setImageResource(R.mipmap.ic_morning_gray);
        ivNoon.setImageResource(R.mipmap.ic_noon_gray);
        ivNight.setImageResource(R.mipmap.ic_night_gray);
        arrayList.add("How");
        arrayList.add("Injection");
        arrayList.add("oral");
        arrayList.add("External use");



        rbDurationLifetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbDuration.setChecked(false);
                spPeriod.setEnabled(false);
                spDays.setEnabled(false);
                spDays.setSelection(0);
                spPeriod.setSelection(0);
            }
        });
        rbDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbDurationLifetime.setChecked(false);
                spPeriod.setEnabled(true);
                spDays.setEnabled(true);

            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String medicinename = sharedPreferences.getString("medicinename", "");
        tvSelectMedicine.setText(medicinename);
        tvTimeMorning.setVisibility(View.INVISIBLE);
        tvTimeNoon.setVisibility(View.INVISIBLE);
        tvTimeEvening.setVisibility(View.INVISIBLE);
        tvTimetNight.setVisibility(View.INVISIBLE);
    }


    public void getData() {
        CustomSpinnerAdapter2 customSpinnerAdapter3 = new CustomSpinnerAdapter2(getApplicationContext(), spinner1);
        spDays.setAdapter(customSpinnerAdapter3);
        spDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        CustomSpinnerAdapter1 customSpinnerAdapter1 = new CustomSpinnerAdapter1(getApplicationContext(), spinner2);
        spPeriod.setAdapter(customSpinnerAdapter1);
        spPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Toast.makeText(PasswordRecoveryQuestionsActivity.this, spinner3[i], Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        CustomSpinnerAdapter3 customSpinnerAdapter34 = new CustomSpinnerAdapter3(getApplicationContext(), spinner3);
        spHow.setAdapter(customSpinnerAdapter34);
        spHow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Toast.makeText(PasswordRecoveryQuestionsActivity.this, spinner3[i], Toast.LENGTH_SHORT).show();

            }

            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public void timePicker(final TextView type) {

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
                            type.setText(date);
//                           if(type.equals("mor")) {
//                               tvTimeMorning.setText(date);
//                           }else if(type.equals("eve")) {
//                                tvTimeEvening.setText(date);
//                            }else if(type.equals("noon")) {
//                               tvTimeNoon.setText(date);
//                           }else if(type.equals("night")) {
//                               tvTimetNight.setText(date);
//                           }


                        } else {

                        }

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tvTimeMorning:
                timePicker(tvTimeMorning);
                break;
            case R.id.tvTimeEvening:
               // timePicker("eve");
                timePicker(tvTimeEvening);
                break;
            case R.id.tvTimeNoon:
                timePicker(tvTimeNoon);
                break;
            case R.id.tvTimetNight:
                timePicker(tvTimetNight);
                break;
            case R.id.tv_date:
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(MedicineReminderSetActivity.this,
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
                            }else{
                                monthYear =  month+"";
                            }
                            String date = day + "-" + monthYear + "-" + year;
                            tvDate.setText(year + "-" + (monthYear) + "-" + dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);


            datePickerDialog.show();
                break;
            case R.id.rlMorning:
                if (ismorningselected) {
                    ismorningselected=false;
                    tvTimeMorning.setVisibility(View.GONE);
                    tvTimeMorning.setText("");
                    tvTimeMorning.setTextColor(Color.parseColor("#9DA1A0"));
                    tvMorninglabel.setTextColor(Color.parseColor("#9DA1A0"));
                    ivMorning.setImageResource(R.mipmap.ic_morning_gray);

                } else {
                    ismorningselected=true;
                    tvTimeMorning.setVisibility(View.VISIBLE);
                    tvTimeMorning.setTextColor(Color.parseColor("#0075b2"));
                    tvMorninglabel.setTextColor(Color.parseColor("#0075b2"));
                    ivMorning.setImageResource(R.mipmap.ic_morning_blue);

                }

                break;

            case R.id.rlNoon:
                if (isnoonselected) {
                    isnoonselected=false;
                    tvTimeNoon.setVisibility(View.GONE);
                    tvTimeNoon.setText("");
                    tvTimeNoon.setTextColor(Color.parseColor("#9DA1A0"));
                    tvNoonlabel.setTextColor(Color.parseColor("#9DA1A0"));
                    ivNoon.setImageResource(R.mipmap.ic_noon_gray);

                } else {
                    isnoonselected=true;
                    tvTimeNoon.setVisibility(View.VISIBLE);
                    tvTimeNoon.setTextColor(Color.parseColor("#0075b2"));
                    tvNoonlabel.setTextColor(Color.parseColor("#0075b2"));
                    ivNoon.setImageResource(R.mipmap.ic_noon_blue);

                }
                break;
            case R.id.rlEvening:
                if (iseveningsselected) {
                    iseveningsselected=false;
                    tvTimeEvening.setVisibility(View.GONE);
                    tvTimeEvening.setText("");
                    tvEveninglabel.setTextColor(Color.parseColor("#9DA1A0"));
                    tvTimeEvening.setTextColor(Color.parseColor("#9DA1A0"));
                    ivEvening.setImageResource(R.mipmap.ic_evening_gray);


                } else {
                    iseveningsselected=true;
                    tvTimeEvening.setVisibility(View.VISIBLE);
                    tvEveninglabel.setTextColor(Color.parseColor("#0075b2"));
                    tvTimeEvening.setTextColor(Color.parseColor("#0075b2"));
                    ivEvening.setImageResource(R.mipmap.ic_evening_blue);

                }
                break;
            case R.id.rlNight:
                if (isnightselected) {
                    isnightselected=false;
                    tvTimetNight.setVisibility(View.GONE);
                    tvTimetNight.setText("");
                    tvTimetNight.setTextColor(Color.parseColor("#9DA1A0"));
                    tvNightlabel.setTextColor(Color.parseColor("#9DA1A0"));
                    ivNight.setImageResource(R.mipmap.ic_night_gray);

                } else {
                    isnightselected=true;
                    tvTimetNight.setVisibility(View.VISIBLE);
                    tvTimetNight.setTextColor(Color.parseColor("#0075b2"));
                    tvNightlabel.setTextColor(Color.parseColor("#0075b2"));
                    ivNight.setImageResource(R.mipmap.ic_night_blue);

                }
                break;

            case R.id.llMedicineName:
                Intent i1 = new Intent(MedicineReminderSetActivity.this, MedicineReminderListActivity.class);
                startActivity(i1);
                break;
            case R.id.ivBack:
                Intent i2 = new Intent(MedicineReminderSetActivity.this, HomeActivity.class);
                i2.putExtra("flag","medicine");
                Prefs.putString("destination","Remainder");
                startActivity(i2);
                break;

            case R.id.btnSubmitmed:
                GetUIValues();
                break;

        }
    }

    public void GetUIValues() {

        int userid,medicineid;
        String medicinename,strength,dose,how = "",startdate,durationday="",durationtype="",lifetime="",morningtime,noontime,eveningtime,nighttime,notes;

        if(tvSelectMedicine.getText().toString().trim().equals(""))
        {
            tvSelectMedicine.setError("Please select a medicine");
            tvSelectMedicine.requestFocus();
        }else if( (tvTimeMorning.getText().toString().trim().equals("")) && (tvTimeNoon.getText().toString().trim().equals("")) &&
                (tvTimeEvening.getText().toString().trim().equals("")) && (tvTimetNight.getText().toString().trim().equals("")))
        {
            showalertdialog("Select atleast one Time");
        }else
        {
            if(spHow.getSelectedItem().toString().trim().equals("How"))
            {
                how = "";
            }else {
                how = spHow.getSelectedItem().toString();
            }
        }
        if(etStrength.getText().toString().trim().equals("")){
            etStrength.setError("Select Strength");
            etStrength.requestFocus();
        }else if (etDos.getText().toString().trim().equals(""))
        {
            etDos.setError("Select a Dose");
            etDos.requestFocus();

        }else if(tvDate.getText().toString().trim().equals("Select Date"))
        {
            tvDate.setError("Select start date");
            tvDate.requestFocus();
        }else
        {
            if(rbDurationLifetime.isChecked())
            {
                lifetime = "Y";
                durationday = "";
                durationtype ="";
//               Toast.makeText(getApplicationContext(),"Successfully validated",Toast.LENGTH_SHORT).show();
//                Log.d(TAG,"submit: medicine"+tvSelectMedicine.getText().toString());
//                Log.d(TAG,"submit: strength"+etStrength.getText().toString());
//                Log.d(TAG,"submit: how"+how);
//                Log.d(TAG,"submit: dose"+etDos.getText().toString());
//                Log.d(TAG,"submit: startdate"+tvDate.getText().toString());
//                Log.d(TAG,"submit: notes"+etNotes.getText().toString());
//                Log.d(TAG,"submit: lifetime"+lifetime);
//                Log.d(TAG,"submit: days"+durationday);
//                Log.d(TAG,"submit: durationtype"+durationtype);
//                Log.d(TAG,"submit: morningtime"+tvTimeMorning.getText().toString());
//                Log.d(TAG,"submit: noontime"+tvTimeNoon.getText().toString());
//                Log.d(TAG,"submit: eveningtime"+tvTimeEvening.getText().toString());
//                Log.d(TAG,"submit: nighttime"+tvTimetNight.getText().toString());
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(new Config().SERVER_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiInterface apiInterface = retrofit.create(ApiInterface.class);
                Post_MedReminderAdd postmedremainderadd = new Post_MedReminderAdd(1,0,tvSelectMedicine.getText().toString(),
                etStrength.getText().toString(),etDos.getText().toString(),how,tvDate.getText().toString(),durationday,durationtype,lifetime,
                tvTimeMorning.getText().toString(),tvTimeNoon.getText().toString(),tvTimeEvening.getText().toString(),tvTimetNight.getText().toString(),etNotes.getText().toString());
                Call<Registration_Response> call = apiInterface.createmedremainder(postmedremainderadd);
                call.enqueue(new Callback<Registration_Response>(){
                    @Override
                    public void onResponse(Call<Registration_Response> call, Response<Registration_Response> response) {
                        if (response.body() != null) {
                            Log.e(TAG, "registrationResponse: code: " + response.body().getCode());
                            Log.e(TAG, "registrationResponse: result: " + response.body().getResults());
                            if(response.body().getCode()==200){
                                Toast.makeText(getApplicationContext(),"Medicine Reminder Sussessfully Added",Toast.LENGTH_SHORT).show();
                                Intent homeint = new Intent(getApplicationContext(), HomeActivity.class);
                                Prefs.putString("destination","Remainder");
                                startActivity(homeint);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(),"You are already Registered",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<Registration_Response> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Somethings went wrong", Toast.LENGTH_SHORT).show();
                    }
                });


            }else
            {
                lifetime = "N";
                durationday = spDays.getSelectedItem().toString();
                if(spPeriod.getSelectedItem().toString().equals("days"))
                {
                    showalertdialog("Select a Duration Type");
                }else {
                    if(spPeriod.getSelectedItem().toString().equals("Daily"))
                    {
                        durationtype="D";
                    }else if(spPeriod.getSelectedItem().toString().equals("Weekly"))
                    {
                        durationtype="W";
                    }else if(spPeriod.getSelectedItem().toString().equals("Monthly"))
                    {
                        durationtype="M";
                    }else if(spPeriod.getSelectedItem().toString().equals("Yealy"))
                    {
                        durationtype="Y";
                    }

//                    Toast.makeText(getApplicationContext(),"Successfully validated",Toast.LENGTH_SHORT).show();
//                    Log.d(TAG,"submit: medicine"+tvSelectMedicine.getText().toString());
//                    Log.d(TAG,"submit: strength"+etStrength.getText().toString());
//                    Log.d(TAG,"submit: how"+how);
//                    Log.d(TAG,"submit: dose"+etDos.getText().toString());
//                    Log.d(TAG,"submit: startdate"+tvDate.getText().toString());
//                    Log.d(TAG,"submit: notes"+etNotes.getText().toString());
//                    Log.d(TAG,"submit: lifetime"+lifetime);
//                    Log.d(TAG,"submit: days"+durationday);
//                    Log.d(TAG,"submit: durationtype"+durationtype);
//                    Log.d(TAG,"submit: morningtime"+tvTimeMorning.getText().toString());
//                    Log.d(TAG,"submit: noontime"+tvTimeNoon.getText().toString());
//                    Log.d(TAG,"submit: eveningtime"+tvTimeEvening.getText().toString());
//                    Log.d(TAG,"submit: nighttime"+tvTimetNight.getText().toString());
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(new Config().SERVER_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ApiInterface apiInterface = retrofit.create(ApiInterface.class);
                    Post_MedReminderAdd postmedremainderadd = new Post_MedReminderAdd(1,0,tvSelectMedicine.getText().toString(),
                            etStrength.getText().toString(),etDos.getText().toString(),how,tvDate.getText().toString(),durationday,durationtype,lifetime,
                            tvTimeMorning.getText().toString(),tvTimeNoon.getText().toString(),tvTimeEvening.getText().toString(),tvTimetNight.getText().toString(),etNotes.getText().toString());
                    Call<Registration_Response> call = apiInterface.createmedremainder(postmedremainderadd);
                    call.enqueue(new Callback<Registration_Response>(){
                        @Override
                        public void onResponse(Call<Registration_Response> call, Response<Registration_Response> response) {
                            if (response.body() != null) {
                                Log.e(TAG, "registrationResponse: code: " + response.body().getCode());
                                Log.e(TAG, "registrationResponse: result: " + response.body().getResults());
                                if(response.body().getCode()==200){
                                    Toast.makeText(getApplicationContext(),"Medicine Reminder Sussessfully Added",Toast.LENGTH_SHORT).show();
                                    Intent homeint = new Intent(getApplicationContext(), HomeActivity.class);
                                    Prefs.putString("destination","Remainder");
                                    startActivity(homeint);
                                    finish();
                                }else {
                                    Toast.makeText(getApplicationContext(),"You are already Registered",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<Registration_Response> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Somethings went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });


                }


            }
        }



    }
    public void showalertdialog(String message)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(MedicineReminderSetActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}
