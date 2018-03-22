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

public class AddBPRecordsDialog extends Dialog {

    public AddRecordsDialogClickListener listener;
    public ImageView ivCancel;
    private Context context;
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;
    private EditText etDate, etTime, etSystolic, etDiastolic, etPulse;
    private RelativeLayout dateButton, timeButton;
    private String date, time, systolic, diastolic, pulse;

    public AddBPRecordsDialog(Context context, AddRecordsDialogClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_blood_pressure);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dateButton = (RelativeLayout) findViewById(R.id.date_layout);
        timeButton = (RelativeLayout) findViewById(R.id.time_layout);

        etDate = (EditText) findViewById(R.id.tv_date);
        etTime = (EditText) findViewById(R.id.tv_time);
        etSystolic = (EditText) findViewById(R.id.systolic);
        etDiastolic = (EditText) findViewById(R.id.diastolic);
        etPulse = (EditText) findViewById(R.id.pulse);

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
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            public void onClick(View view) {
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
                if (!(systolic = etSystolic.getText().toString().trim()).equals(""))
                    if (!(diastolic = etDiastolic.getText().toString().trim()).equals(""))
                        if (!(pulse = etPulse.getText().toString().trim()).equals(""))
                            listener.onSubmit(date, time, systolic, diastolic, pulse);
                        else
                            Toast.makeText(context, "Please enter valid pulse", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context, "Please enter valid diastolic", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "Please enter valid systolic", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Please enter valid time", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Please enter valid date", Toast.LENGTH_SHORT).show();
    }

    public interface AddRecordsDialogClickListener {

        void onSubmit(String systolic, String diastolic, String pulse, String date, String time);

        void onCancel();

    }
}
