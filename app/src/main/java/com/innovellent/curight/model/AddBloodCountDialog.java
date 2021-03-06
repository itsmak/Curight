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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;

import java.util.Calendar;


/**
 * Created by sagar on 10/1/2017.
 */

public class AddBloodCountDialog extends Dialog {

    public AddBloodCountDialog.AddBloodCountDialogClickListener listener;
    public ImageView ivCancel;
    Context context;
    EditText et_anticpp,et_crp,et_esr,et_hoemoglobin,et_hbalc,et_inr,et_platelet,et_prolactin,et_rbc,et_rf,et_wbc,tvSelectDate;
    TextView tv_AboutDetails;
    String about;
    String date,anticpp,crp,esr,hoemoglobin,hbalc,inr,platelet,prolactin,rbc,rf,wbc;
    RelativeLayout linear_datedialog;
    DatePickerDialog datePickerDialog;
    public AddBloodCountDialog(Context context, AddBloodCountDialog.AddBloodCountDialogClickListener listener) {
        super(context);
        this.context = context;
        this.about = about;
        this.listener = listener;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_blood_count);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        tvSelectDate = (EditText) findViewById(R.id.tvSelectDate);

        et_anticpp = (EditText)findViewById(R.id.et_anticpp);
        et_crp = (EditText)findViewById(R.id.et_crp);
        et_esr = (EditText)findViewById(R.id.et_esr);
        et_inr = (EditText)findViewById(R.id.et_inr);
        et_hoemoglobin = (EditText)findViewById(R.id.et_hoemoglobin);
        et_hbalc = (EditText)findViewById(R.id.et_hbalc);
        et_platelet = (EditText)findViewById(R.id.et_platelet);
        et_prolactin = (EditText)findViewById(R.id.et_prolactin);
        et_rbc = (EditText)findViewById(R.id.et_rbc);
        et_rf = (EditText)findViewById(R.id.et_rf);
        et_wbc = (EditText)findViewById(R.id.et_wbc);
        linear_datedialog = (RelativeLayout)findViewById(R.id.linear_datedialog);

        final Calendar calendar = Calendar.getInstance();

        tvSelectDate.setText(getContext().getString(R.string.date_formatted, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE)));

        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tvSelectDate.setText(getContext().getString(R.string.date_formatted, year, month + 1, dayOfMonth));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        linear_datedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        tvSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInputs_new();
            }
        });
        findViewById(R.id.ivCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCancel();
            }
        });
    }


    private void validateInputs_new()
        {
            if(tvSelectDate.getText().toString().trim().equals(""))
            {
                Toast.makeText(context, "Please enter Date", Toast.LENGTH_SHORT).show();
            }else if(et_anticpp.getText().toString().trim().equals(""))
            {
                Toast.makeText(context, "Please enter Anticpp", Toast.LENGTH_SHORT).show();
            }else if(et_crp.getText().toString().trim().equals(""))
            {
            Toast.makeText(context, "Please enter Crp", Toast.LENGTH_SHORT).show();
            }else if(et_esr.getText().toString().trim().equals(""))
            {
            Toast.makeText(context, "Please enter Esr", Toast.LENGTH_SHORT).show();
            }else if(et_hoemoglobin.getText().toString().trim().equals(""))
            {
            Toast.makeText(context, "Please enter Haemoglobin", Toast.LENGTH_SHORT).show();
            }else if(et_hbalc.getText().toString().trim().equals(""))
            {
                Toast.makeText(context, "Please enter HbA1c", Toast.LENGTH_SHORT).show();
            }else if(et_inr.getText().toString().trim().equals(""))
            {
                Toast.makeText(context, "Please enter INR", Toast.LENGTH_SHORT).show();
            }else if(et_platelet.getText().toString().trim().equals(""))
            {
                Toast.makeText(context, "Please enter Platelets", Toast.LENGTH_SHORT).show();
            }else if(et_prolactin.getText().toString().trim().equals(""))
            {
                Toast.makeText(context, "Please enter Prolactin", Toast.LENGTH_SHORT).show();
            }else if(et_rbc.getText().toString().trim().equals(""))
            {
                Toast.makeText(context, "Please enter RBC", Toast.LENGTH_SHORT).show();
            }else if(et_rf.getText().toString().trim().equals(""))
            {
                Toast.makeText(context, "Please enter RF", Toast.LENGTH_SHORT).show();
            }else if(et_wbc.getText().toString().trim().equals(""))
            {
                Toast.makeText(context, "Please enter WBC", Toast.LENGTH_SHORT).show();
            }else {
                listener.onSubmit(et_anticpp.getText().toString().trim(), et_crp.getText().toString().trim(), et_esr.getText().toString().trim(),et_hoemoglobin.getText().toString().trim(),et_hbalc.getText().toString().trim(),et_inr.getText().toString().trim(),et_platelet.getText().toString().trim(),et_prolactin.getText().toString().trim()
                        ,et_rbc.getText().toString().trim(),et_rf.getText().toString().trim(),et_wbc.getText().toString().trim(),tvSelectDate.getText().toString().trim());
            }
        }

//        private void validateInputs() {
//        if (!(anticpp = et_anticpp.getText().toString().trim()).equals(""))
//            if(!(date = tvSelectDate.getText().toString().trim()).equals(""))
//            if (!(crp = et_crp.getText().toString().trim()).equals(""))
//                if (!(esr = et_esr.getText().toString().trim()).equals(""))
//                    if(!(hoemoglobin = et_hoemoglobin.getText().toString().trim()).equals(""))
//                        if(!(hbalc = et_hbalc.getText().toString().trim()).equals(""))
//                            if(!(inr = et_inr.getText().toString().trim()).equals(""))
//                                if(!(platelet = et_platelet.getText().toString().trim()).equals(""))
//                                    if(!(prolactin = et_prolactin.getText().toString().trim()).equals(""))
//                                        if(!(rbc = et_rbc.getText().toString().trim()).equals(""))
//                                            if(!(rf = et_rf.getText().toString().trim()).equals(""))
//                                                if(!(wbc = et_wbc.getText().toString().trim()).equals(""))
//                                                    listener.onSubmit(anticpp, crp, esr,hoemoglobin,hbalc,inr,platelet,prolactin,rbc,rf,wbc,date);
//
//        else
//            Toast.makeText(context, "Please enter anticpp", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(context, "Please select date", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(context, "Please enter crp", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(context, "Please enter esr", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(context, "Please enter hoemoglobin", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(context, "Please enter hbalc", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(context, "Please enter inr", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(context, "Please enter platelet", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(context, "Please enter prolactin", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(context, "Please enter rbc", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(context, "Please enter rf", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(context, "please enter wbc", Toast.LENGTH_SHORT).show();
//
//
//
//
//    }



    public interface AddBloodCountDialogClickListener {

        void onSubmit(String Anticep, String Crp, String Esr,String Haemoglobin,String Hbalc,String Inr,String Platelest,String Prolactin,String Rbc,String Rf, String Wbc,String Date);

        void onCancel();

    }
}



