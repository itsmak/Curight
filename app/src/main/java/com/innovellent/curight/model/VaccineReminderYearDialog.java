package com.innovellent.curight.model;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
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
    String vaccine_name,ageinmonth,DueDate,doctorname,details,Remainder1,Remainder2,VACCINEDATE,DOCTORNAME,DETAILS;
    int userid,vaccineid,vaccinechartid;
    TextView mdfy_header_txtvw;
    EditText modify_due_edttxt,mofy_tkn_edttxt,mdfy_gvnby_edttxt,mdfy_details_edttxt,mdfy_remainder1_edttxt,mdfy_remainder2_edttxt;
    private Vaccine item;
//    NumberPicker npDays;
 //   private static final String BASE_URL ="http://13.59.209.135:8090/diagnosticAPI/webapi/";
    private int mYear, mMonth, mDay;
    public VaccineReminderYearDialog(Context context, Vaccine item,VaccineReminderYearDialog.VaccineReminderYearDialogClickListener listener) {
        super(context);
        this.context = context;
        this.item = item;
        this.listener = listener;
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

        mdfy_header_txtvw.setText(item.getVaccinename()+" at "+item.getAgeinonth());
        modify_due_edttxt.setText(item.getDuedate());
        mofy_tkn_edttxt.setText(item.getDate());
        mdfy_gvnby_edttxt.setText(item.getDoctorname());
        mdfy_details_edttxt.setText(item.getComments());
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
              dismiss();

            }
        });
        findViewById(R.id. btnModify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // listener.onSubmit();
                VACCINEDATE = mofy_tkn_edttxt.getText().toString().trim();
                DOCTORNAME = mdfy_gvnby_edttxt.getText().toString().trim();
                DETAILS = mdfy_details_edttxt.getText().toString().trim();
                Remainder1 = mdfy_remainder1_edttxt.getText().toString().trim();
                Remainder2=mdfy_remainder2_edttxt.getText().toString().trim();

                listener.onSubmit(new Vaccine(item.getDuration_age(),item.getVaccinename(),VACCINEDATE,item.getDuedate(),DOCTORNAME,DETAILS,item.getAgeinonth(),item.getUserid(),
                        item.getVaccineactivityid(),item.getVaccinechartid(),true),Remainder1,Remainder2);
        //        apical(VACCINEDATE,DOCTORNAME,DETAILS,Remainder1,Remainder2);
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

//    private void apical_modify(String VACCINEDATE,String DOCTORNAME,String DETAILS,String remainder1,String remainder2) {
//        listener.onSubmit();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        ApiInterface reditapi = retrofit.create(ApiInterface.class);
//        Log.d("dialog","onclick: userid"+userid);
//        Log.d("dialog","onclick: vaccineactivityid"+vaccineid);
//        Log.d("dialog","onclick: vaccinechart"+vaccinechartid);
//        Log.d("dialog","onclick: vaccinedate"+VACCINEDATE);
//        Log.d("dialog","onclick: comments"+DETAILS);
//        Log.d("dialog","onclick: doctorname"+DOCTORNAME);
//        Log.d("dialog","onclick: remainder1"+remainder1);
//        Log.d("dialog","onclick: remainder2"+remainder2);
//        POST_UPDATE_CLASS post_update_class = new POST_UPDATE_CLASS(userid,vaccineid,vaccinechartid,VACCINEDATE,DETAILS,DOCTORNAME,
//                mdfy_remainder1_edttxt.getText().toString(),mdfy_remainder2_edttxt.getText().toString());
//        Call<VACCINE_UPDATE_RESPONSE> call = reditapi.get_vaccine_update(post_update_class);
//        call.enqueue(new Callback<VACCINE_UPDATE_RESPONSE>() {
//
//            @Override
//            public void onResponse(Call<VACCINE_UPDATE_RESPONSE> call, Response<VACCINE_UPDATE_RESPONSE> response) {
//
//                if (response.isSuccessful()){
//                    Toast.makeText(context,"Successfully Updated!",Toast.LENGTH_SHORT).show();
//
//                    Log.d("Dialog", "remainderResponse: code: " +response.body());
//                    Log.d("Dialog", "remainderReseponse: code: " +response.body().getCode());
//                    Log.d("Dialog", "remainderResponse: result: " +response.body().getResults());
////                    VaccineFragment vf = new VaccineFragment();
////                    vf.GetData(String.valueOf(userid));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<VACCINE_UPDATE_RESPONSE> call, Throwable t) {
//                Toast.makeText(context,"Somethings went wrong",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    public interface VaccineReminderYearDialogClickListener {
        void onSubmit(Vaccine myupdatedItem, String remainder1, String remainder2);

    }
}

