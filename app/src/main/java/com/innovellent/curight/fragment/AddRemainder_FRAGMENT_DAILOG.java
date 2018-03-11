package com.innovellent.curight.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.innovellent.curight.R;
import com.innovellent.curight.activities.HomeActivity;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.POST_CREATE_CLASS;
import com.innovellent.curight.model.POST_UPDATE_CLASS;
import com.innovellent.curight.model.VACCINE_UPDATE_RESPONSE;
import com.innovellent.curight.model.Vaccine;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Mak on 12/5/2017.
 */
public class AddRemainder_FRAGMENT_DAILOG extends Dialog {
 //   private static final String BASE_URL = "http://13.59.209.135:8090/diagnosticAPI/webapi/";
    public AddRemainder_FRAGMENT_DAILOG.AddRemainder_FRAGMENT_DAILOGClickListener listener;
    Context context;
 //   int userid;
    String ageinmonth, Duration_age;
    String VACCINE_NAME, DUE_ON, TAKEN_ON, GIVENBY, ADD_DETAILS, REMAINDER1, REMAINDER2;
    TextView tvnamelabel;
    EditText addvaccine_name, addDueOn_edttxt, addTakeOn_edtxt, addGivenBy_edtxt, addDetails_edttxt, addReminder1_txtvw, addReminder2_txtvw;
    private Vaccine item;
    private int mYear, mMonth, mDay;

    public AddRemainder_FRAGMENT_DAILOG(Context context, Vaccine item, AddRemainder_FRAGMENT_DAILOG.AddRemainder_FRAGMENT_DAILOGClickListener listener) {
        super(context);
        this.context = context;
        this.item = item;
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dailog_add_remainder);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        addDueOn_edttxt = (EditText) findViewById(R.id.addDueOn_edttxt);
        addvaccine_name = (EditText) findViewById(R.id.vcname_edttxt);
        addTakeOn_edtxt = (EditText) findViewById(R.id.addTakeOn_edtxt);
        addGivenBy_edtxt = (EditText) findViewById(R.id.addGivenBy_edtxt);
        addDetails_edttxt = (EditText) findViewById(R.id.addDetails_edttxt);
        addReminder1_txtvw = (EditText) findViewById(R.id.addReminder1_txtvw);
        addReminder2_txtvw = (EditText) findViewById(R.id.addReminder2_txtvw);
        tvnamelabel = (TextView) findViewById(R.id.tvnamelabel);
        tvnamelabel.setText("Add Vaccine");

        addTakeOn_edtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate(addTakeOn_edtxt);
            }
        });
        addReminder1_txtvw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate(addReminder1_txtvw);
            }
        });
        addReminder2_txtvw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate(addReminder2_txtvw);
            }
        });

        findViewById(R.id.add_remainder_cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        findViewById(R.id.add_remainder_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VACCINE_NAME = addvaccine_name.getText().toString();
                DUE_ON = addDueOn_edttxt.getText().toString();
                TAKEN_ON = addvaccine_name.getText().toString();
                GIVENBY = addGivenBy_edtxt.getText().toString();
                ADD_DETAILS = addDetails_edttxt.getText().toString();
                REMAINDER1 = addReminder1_txtvw.getText().toString();
                REMAINDER2 = addReminder2_txtvw.getText().toString();

                listener.onSubmit(new Vaccine(Duration_age, VACCINE_NAME, TAKEN_ON, DUE_ON, GIVENBY, ADD_DETAILS, item.getAgeinonth(), item.getUserid(),item.getVaccineactivityid(),item.getVaccinechartid(),true),REMAINDER1, REMAINDER2);
            }
        });

        addDueOn_edttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("clicked", "dailog");
            }
        });

    }

    public void selectDate(final EditText setedttxt) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
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
                        setedttxt.setText(dayOfMonth + "/" + (monthYear) + "/" + year);


                    }
                }, mYear, mMonth, mDay);


        datePickerDialog.show();
    }

//    private void addapical(String VACCINE_NAME, String DUE_ON, String TAKEN_ON, String GIVENBY, String ADD_DETAILS, String REMAINDER1, String REMAINDER2) {
//
////        listener.onSubmit();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        ApiInterface reditapi = retrofit.create(ApiInterface.class);
//        Log.d("dialog", "onclick: userid" + userid);
//        Log.d("dialog", "onclick: ageinmonth" + ageinmonth);
//        Log.d("dialog", "onclick: vaccinechart" + VACCINE_NAME);
//        Log.d("dialog", "onclick: vaccinduedate" + DUE_ON);
//        Log.d("dialog", "onclick: vaccinetakenon" + TAKEN_ON);
//        Log.d("dialog", "onclick: doctorname" + GIVENBY);
//        Log.d("dialog", "onclick: doctorname" + ADD_DETAILS);
//        Log.d("dialog", "onclick: remainder1" + REMAINDER1);
//        Log.d("dialog", "onclick: remainder2" + REMAINDER2);
//        POST_CREATE_CLASS post_create_class = new POST_CREATE_CLASS(userid, ageinmonth, "0", VACCINE_NAME, "", "",
//                TAKEN_ON, ADD_DETAILS, GIVENBY, REMAINDER1, REMAINDER2);
//        Call<VACCINE_UPDATE_RESPONSE> call = reditapi.get_vaccine_create(post_create_class);
//        call.enqueue(new Callback<VACCINE_UPDATE_RESPONSE>() {
//
//            @Override
//            public void onResponse(Call<VACCINE_UPDATE_RESPONSE> call, Response<VACCINE_UPDATE_RESPONSE> response) {
//
//                if (response.isSuccessful()) {
//                    Toast.makeText(context, "Successfully Created!", Toast.LENGTH_SHORT).show();
//
//                    Log.d("Dialog", "remainderResponse: code: " + response.body());
//                    Log.d("Dialog", "remainderReseponse: code: " + response.body().getCode());
//                    Log.d("Dialog", "remainderResponse: result: " + response.body().getResults());
//                    //   Intent fkint = new Intent(context, HomeActivity.class);
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<VACCINE_UPDATE_RESPONSE> call, Throwable t) {
//                Toast.makeText(context, "Somethings went wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    public interface AddRemainder_FRAGMENT_DAILOGClickListener {
        void onSubmit(Vaccine updatedItem, String remainder1, String remainder2);
    }

}
