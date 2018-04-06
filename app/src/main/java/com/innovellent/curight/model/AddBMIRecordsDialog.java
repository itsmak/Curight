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


/**
 * Created by sagar on 9/30/2017.
 */

public class AddBMIRecordsDialog extends Dialog {

    public AddBMIRecordsDialog.AddBMIRecordsDialogClickListener listener;
    public ImageView ivCancel;
    Context context;
    private String date, time, height, weight;
    private RelativeLayout dateButton, timeButton;
    private EditText etDate, etTime, etHeight, etWeight;
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;

    public AddBMIRecordsDialog(Context context, AddBMIRecordsDialog.AddBMIRecordsDialogClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_bmi_records);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dateButton = (RelativeLayout) findViewById(R.id.date_layout);
        timeButton = (RelativeLayout) findViewById(R.id.time_layout);

        etDate = (EditText) findViewById(R.id.tv_date);
        etTime = (EditText) findViewById(R.id.tv_time);
        etHeight = (EditText) findViewById(R.id.height);
        etWeight = (EditText) findViewById(R.id.weight);

        final Calendar calendar = Calendar.getInstance();
        etDate.setText(getContext().getString(R.string.date_formatted, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE)));

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
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        etDate.setOnClickListener(new View.OnClickListener() {
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
                if (!(height = etHeight.getText().toString().trim()).equals(""))
                    if (!(weight = etWeight.getText().toString().trim()).equals(""))
                        listener.onSubmit(date, time, height, weight);
                      //  listener.onSubmit("", "", "", "");
                    else
                        Toast.makeText(context, "Please enter valid weight", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "Please enter valid height", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Please enter valid time", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Please enter valid date", Toast.LENGTH_SHORT).show();
    }

    public interface AddBMIRecordsDialogClickListener {

        void onSubmit(String date, String time, String height, String weight);

        void onCancel();

    }
}

