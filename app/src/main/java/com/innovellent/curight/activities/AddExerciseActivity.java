package com.innovellent.curight.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
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
import com.innovellent.curight.adapter.DiagnosticTestAdapter;
import com.innovellent.curight.api.ApiClient;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.ServerResponseCreateExercise;
import com.innovellent.curight.utility.SharedPrefService;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.innovellent.curight.utility.Constants.ACCESS_TOKEN;
import static com.innovellent.curight.utility.Constants.FAST_SPEED;
import static com.innovellent.curight.utility.Constants.MEDIUM_SPEED;
import static com.innovellent.curight.utility.Constants.SLOW_SPEED;
import static com.innovellent.curight.utility.Constants.TITLE;
import static com.innovellent.curight.utility.Constants.USER_ID;

public class AddExerciseActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recycler_view;
    DiagnosticTestAdapter mAdapter;
    Button btnSubmit;
    String format;
    EditText etSpeed;
    ArrayList<String> arrayList = new ArrayList<String>();
    LinearLayout llTime, llDate;
    TextView tvTextTime, tvTextDate;
    Toolbar toolbar;
    ImageView ivCustom, ivSlow, ivMedium, ivFast;
    private int mYear, mMonth, mDay;
    private StringBuilder date;
    private TextView tvSlow, tvMedium, tvFast, tvCustom, tvSpeed;
    private int mHour, mMinute, mhour1, mhour2, mhour3, minute1, minute2, minute3, mSeconds, seconds1, seconds2, seconds3;
    private EditText atTime;
    private TextView distanceCovered, calsBurned;
    private TextView tvTitle;
    private String title;
    private int speed = 5;
    private SharedPrefService sharedPrefService;
    private long userId;
    private String accessToken;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        sharedPrefService = SharedPrefService.getInstance();
        userId = sharedPrefService.getLong(USER_ID);
        accessToken = sharedPrefService.getString(ACCESS_TOKEN);

        init();
        setupToolbar();
        iniClick();
    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) findViewById(R.id.title);
        tvTextTime = (TextView) findViewById(R.id.tvTextTime);
        llTime = (LinearLayout) findViewById(R.id.llTime);
        tvTextDate = (TextView) findViewById(R.id.tvTextDate);
        atTime = (EditText) findViewById(R.id.atTime);
        llDate = (LinearLayout) findViewById(R.id.llDate);
        ivSlow = (ImageView) findViewById(R.id.ivSlow);
        ivMedium = (ImageView) findViewById(R.id.ivMedium);
        ivFast = (ImageView) findViewById(R.id.ivFast);
        ivCustom = (ImageView) findViewById(R.id.ivCustom);
        tvSlow = (TextView) findViewById(R.id.tvSlow);
        tvMedium = (TextView) findViewById(R.id.tvMedium);
        tvFast = (TextView) findViewById(R.id.tvFast);
        tvCustom = (TextView) findViewById(R.id.tvCustom);
        tvSpeed = (TextView) findViewById(R.id.tvSpeed);
        etSpeed = (EditText) findViewById(R.id.etSpeed);
        etSpeed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                speed = Integer.parseInt(etSpeed.getText().toString());
            }
        });

        distanceCovered = (EditText) findViewById(R.id.distanceCovered);
        calsBurned = (EditText) findViewById(R.id.calsBurned);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        Calendar calendar = Calendar.getInstance();
        tvTextTime.setText(new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(calendar.getTime()));
        tvTextDate.setText(new SimpleDateFormat(getString(R.string.date_format), Locale.ENGLISH).format(calendar.getTime()));
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar;
        if ((actionBar = getSupportActionBar()) != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        title = getIntent().getStringExtra(TITLE);
        if (title == null) title = "Curight";
        tvTitle.setText(title);
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.llTime:
                timePicker();
                break;
            case R.id.ivSlow:
                setColor();
                tvSpeed.setVisibility(View.VISIBLE);
                tvSpeed.setText(getString(R.string.km_h_formatted, speed = SLOW_SPEED));
                etSpeed.setVisibility(View.GONE);
                break;
            case R.id.ivMedium:
                setColor();
                tvSpeed.setText(getString(R.string.km_h_formatted,speed =  MEDIUM_SPEED));
                tvSpeed.setVisibility(View.VISIBLE);
                etSpeed.setVisibility(View.GONE);
                break;
            case R.id.ivFast:
                setColor();
                tvSpeed.setText(getString(R.string.km_h_formatted,speed =  FAST_SPEED));
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

    public void setColor() {
        tvSlow.setTextColor(Color.parseColor("#9DA1A0"));
        tvMedium.setTextColor(Color.parseColor("#9DA1A0"));
        tvFast.setTextColor(Color.parseColor("#9DA1A0"));
        tvCustom.setTextColor(Color.parseColor("#9DA1A0"));
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
                        tvTextDate.setText(year + "-" + (monthYear) + "-" + dayOfMonth);


                    }
                }, mYear, mMonth, mDay);


        datePickerDialog.show();
    }

    public void addExercise() {

        progressDialog = ProgressDialog.show(AddExerciseActivity.this, "Adding", "please wait", true, false);
        progressDialog.show();

        ApiInterface client = ApiClient.getClient();

        try {
            final JSONObject paramObject = new JSONObject();
            paramObject.put(USER_ID, userId);
            paramObject.put("exercisetype", title);
            paramObject.put("exercisedate", tvTextDate.getText().toString());
            paramObject.put("exercisetime", tvTextTime.getText().toString());
            paramObject.put("exerciseat", tvTextTime.getText().toString());
            paramObject.put("speed", speed);
            paramObject.put("distancecovered", distanceCovered.getText().toString());
            paramObject.put("caloriesburnt", calsBurned.getText().toString());

            Call<ServerResponseCreateExercise> call = client.createExercise(accessToken, paramObject.toString());

            call.enqueue(new Callback<ServerResponseCreateExercise>() {
                @Override
                public void onResponse(Call<ServerResponseCreateExercise> call, Response<ServerResponseCreateExercise> response) {

                    if (getBaseContext() != null) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            ServerResponseCreateExercise res = response.body();
                            finish();
                        } else
                            Toast.makeText(AddExerciseActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ServerResponseCreateExercise> call, Throwable t) {
                    if (getBaseContext() != null) progressDialog.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
