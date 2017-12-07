package com.innovellent.curight.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;


import com.innovellent.curight.R;
import com.innovellent.curight.adapter.CustomSpinnerAdapter1;
import com.innovellent.curight.adapter.CustomSpinnerAdapter2;
import com.innovellent.curight.adapter.CustomSpinnerAdapter3;
import com.innovellent.curight.adapter.DiagnosticTestAdapter;
import com.innovellent.curight.adapter.MedicineAdpater;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class MedicineReminderSetActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recycler_view;
    MedicineAdpater mAdapter;
    Button btnSubmit;
    TextView tvSelectMedicine, tvTimeMorning,tvDate;
    RadioButton rbDuration, rbDurationLifetime;
    Spinner spDays, spPeriod, spHow;
    private int mYear, mMonth, mDay;
    ImageView ivBack, ivMorning, ivNoon, ivEvening, ivNight;
    boolean ismorningselected, isnoonselected, iseveningsselected, isnightselected;
    RelativeLayout rlMorning, rlNoon, rlEvening, rlNight;
    LinearLayout llMedicineName;
    private StringBuilder date;
    Toolbar toolbar;
    DiagnosticTestAdapter mAdapter1;
    private int mHour, mMinute, mhour1, mhour2, mhour3, minute1, minute2, minute3, mSeconds, seconds1, seconds2, seconds3;
    TextView tvTime, tvMorninglabel, tvTimeNoon, tvNoonlabel, tvTimeEvening, tvEveninglabel, tvTimetNight, tvNightlabel;
    ArrayList<String> arrayList = new ArrayList<String>();
    String[] spinner1 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    String[] spinner2 = {"days", "Daily", "Weekly", "Monthly", "Yealy"};
    String[] spinner3 = {"How", "Injection", "External Use", "Oral"};
    String format;
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
    private void timePicker(final String type) {

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
                           if(type.equals("mor")) {
                               tvTimeMorning.setText(date);
                           }else if(type.equals("eve")) {
                                tvTimeEvening.setText(date);
                            }else if(type.equals("noon")) {
                               tvTimeNoon.setText(date);
                           }else if(type.equals("night")) {
                               tvTimetNight.setText(date);
                           }


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
                timePicker("mor");
                break;
            case R.id.tvTimeEvening:
                timePicker("eve");
                break;
            case R.id.tvTimeNoon:
                timePicker("noon");
                break;
            case R.id.tvTimetNight:
                timePicker("night");
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
                            tvDate.setText(dayOfMonth + "-" + (monthYear) + "-" + year);


                        }
                    }, mYear, mMonth, mDay);


            datePickerDialog.show();
                break;
            case R.id.rlMorning:
                if (ismorningselected) {
                    ismorningselected=false;
                    tvTimeMorning.setVisibility(View.INVISIBLE);
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
                    tvTimeNoon.setVisibility(View.INVISIBLE);
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
                    tvTimeEvening.setVisibility(View.INVISIBLE);
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
                    tvTimetNight.setVisibility(View.INVISIBLE);
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
                startActivity(i2);
                break;



        }
    }


}
