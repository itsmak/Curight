package com.innovellent.curight.model;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.innovellent.curight.R;

import java.util.Calendar;

public class AddCholesterolRecordsDialog extends Dialog {

    private AddCholesterolRecordsDialogClickListener listener;
    private Context context;
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;
    private EditText etDate, etTime, etHdl, etLdl, etTriglycerides;
    private RelativeLayout dateButton, timeButton;
    private String date, time, ldl, hdl, triglycerides;

    public AddCholesterolRecordsDialog(Context context, AddCholesterolRecordsDialogClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_cholestrol);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dateButton = (RelativeLayout) findViewById(R.id.date_layout);
        timeButton = (RelativeLayout) findViewById(R.id.time_layout);

        etDate = (EditText) findViewById(R.id.tv_date);
        etTime = (EditText) findViewById(R.id.tv_time);
        etLdl = (EditText) findViewById(R.id.ldl);
        etHdl = (EditText) findViewById(R.id.hdl);
        etTriglycerides = (EditText) findViewById(R.id.triglycerides);

        final Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                etTime.setText(getContext().getString(R.string.time_formatted, hourOfDay, minute));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                etDate.setText(getContext().getString(R.string.date_formatted, year, month + 1, dayOfMonth));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });
        findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInputs();
            }
        });
        findViewById(R.id.ivCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCancel();
            }
        });
    }

    private void validateInputs() {
        if (!(date = etDate.getText().toString().trim()).equals(""))
            if (!(time = etTime.getText().toString().trim()).equals(""))
                if (!(ldl = etLdl.getText().toString().trim()).equals(""))
                    if (!(hdl = etHdl.getText().toString().trim()).equals(""))
                        if (!(triglycerides = etTriglycerides.getText().toString().trim()).equals(""))
                            listener.onSubmit(date, time, ldl, hdl, triglycerides);
                        else
                            Toast.makeText(context, "Please enter valid triglycerides", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context, "Please enter valid HDL", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "Please enter valid LDL", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Please enter valid time", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Please enter valid date", Toast.LENGTH_SHORT).show();
    }

    public interface AddCholesterolRecordsDialogClickListener {

        void onSubmit(String date, String time, String ldl, String hdl, String triglycerides);

        void onCancel();
    }
}


