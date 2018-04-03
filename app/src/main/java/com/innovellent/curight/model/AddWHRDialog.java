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
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;

import java.util.Calendar;

/**
 * Created by sagar on 10/1/2017.
 */

public class AddWHRDialog extends Dialog {

    public AddWHRDialog.AddWHRDialogClickListener listener;
    public ImageView ivCancel;
    Context context;
    EditText etComments;
    TextView tv_AboutDetails;
    String about;
    private String date, waist, hip;
    private RelativeLayout dateButton, timeButton;
    private EditText etDate,  etWaist, etHip;
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;

    public AddWHRDialog(Context context, AddWHRDialog.AddWHRDialogClickListener listener) {
        super(context);
        this.context = context;
        this.about = about;
        this.listener = listener;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_whr);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dateButton = (RelativeLayout) findViewById(R.id.date_layout_whr);

        etDate = (EditText) findViewById(R.id.tv_date_whr);
        etWaist = (EditText) findViewById(R.id.waist_whr);
        etHip = (EditText) findViewById(R.id.hip_whr);

        final Calendar calendar = Calendar.getInstance();


        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                etDate.setText(getContext().getString(R.string.date_formatted, year, month + 1, dayOfMonth));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
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

        findViewById(R.id.btnSubmit_whr).setOnClickListener(new View.OnClickListener() {
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

                if (!(waist = etWaist.getText().toString().trim()).equals(""))
                    if (!(hip = etHip.getText().toString().trim()).equals(""))
                        listener.onSubmit(date, waist, hip);

                    else
                        Toast.makeText(context, "Please enter valid weight", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "Please enter valid height", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Please enter valid time", Toast.LENGTH_SHORT).show();

    }


    public interface AddWHRDialogClickListener {

        void onSubmit(String date, String height, String weight);

        void onCancel();

    }

}



