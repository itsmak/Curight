package com.innovellent.curight.model;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.api.ApiInterface;

import java.util.Calendar;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mak on 9/13/2017.
 */

public class VaccineReminderYearDialog extends Dialog {

    public VaccineReminderYearDialog.VaccineReminderYearDialogClickListener listener;
    Context context;
    String vaccine_name,ageinmonth,DueDate,doctorname,details;
//    NumberPicker npDays;
    private static final String BASE_URL ="http://13.59.209.135:8090/diagnosticAPI/webapi/";
    private int mYear, mMonth, mDay;
    TextView mdfy_header_txtvw;
    EditText modify_due_edttxt,mofy_tkn_edttxt,mdfy_gvnby_edttxt,mdfy_details_edttxt,mdfy_remainder1_edttxt,mdfy_remainder2_edttxt;
    public VaccineReminderYearDialog(Context context, String vaccine_name,
     String ageinmonth,String DueDate,String doctorname,String details ,VaccineReminderYearDialog.VaccineReminderYearDialogClickListener listener) {
        super(context);
        this.context = context;
        this.vaccine_name = vaccine_name;
        this.ageinmonth = ageinmonth;
        this.DueDate = DueDate;
        this.doctorname = doctorname;
        this.details = details;
        this.listener = listener;

    }

    public interface VaccineReminderYearDialogClickListener {

        void onSubmit();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_vaccine_reminder_year);
        mdfy_header_txtvw = (TextView) findViewById(R.id.mdfy_header_txtvw);
        modify_due_edttxt = (EditText) findViewById(R.id.modify_due_edttxt);
        mofy_tkn_edttxt = (EditText) findViewById(R.id.mofy_tkn_edttxt);
        mdfy_gvnby_edttxt = (EditText) findViewById(R.id.mdfy_gvnby_edttxt);
        mdfy_details_edttxt = (EditText) findViewById(R.id.mdfy_details_edttxt);
        mdfy_remainder1_edttxt = (EditText) findViewById(R.id.mdfy_remainder1_edttxt);
        mdfy_remainder2_edttxt = (EditText) findViewById(R.id.mdfy_remainder2_edttxt);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mdfy_header_txtvw.setText(vaccine_name+" at "+ageinmonth);
        modify_due_edttxt.setText(DueDate);
        mofy_tkn_edttxt.setText(DueDate);
        mdfy_gvnby_edttxt.setText(doctorname);
        mdfy_details_edttxt.setText(doctorname);
        mofy_tkn_edttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate(mofy_tkn_edttxt);
            }
        });
        mdfy_remainder1_edttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate(mdfy_remainder1_edttxt);
            }
        });
        mdfy_remainder2_edttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate(mdfy_remainder2_edttxt);
            }
        });
        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSubmit();

            }
        });
        findViewById(R.id. btnModify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // listener.onSubmit();

                apical();
            }
        });

    }

    private void apical() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//        ApiInterface reditapi = retrofit.create(ApiInterface.class);
//        POST_UPDATE_CLASS post_update_class = new POST_UPDATE_CLASS(details);
//        Call<VACCINE_UPDATE_RESPONSE> call = reditapi.get_vaccine_update(post_update_class);
//        call.enqueue(new Callback<VACCINE_UPDATE_RESPONSE>() {
//
//            @Override
//            public void onResponse(Call<VACCINE_UPDATE_RESPONSE> call, Response<VACCINE_UPDATE_RESPONSE> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<VACCINE_UPDATE_RESPONSE> call, Throwable t) {
//
//            }
//        });

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
}

