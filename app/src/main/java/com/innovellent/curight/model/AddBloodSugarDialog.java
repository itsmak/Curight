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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.innovellent.curight.R;

import java.util.Calendar;

/**
 * Created by sagar on 9/30/2017.
 */

public class AddBloodSugarDialog extends Dialog {

    public AddBloodSugarDialog.AddBloodSugarDialogClickListener listener;
    Context context;
    String date,time,aftermeal,beforemeal;
    RelativeLayout layout_datebloodsugar,timelayout_bloodsugar;
    EditText etBeforemeal,etAftermeal;
    TextView tv_date,tv_time;
   // private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;

    public AddBloodSugarDialog(Context context, AddBloodSugarDialog.AddBloodSugarDialogClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_blood_sugar);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        layout_datebloodsugar = (RelativeLayout)findViewById(R.id.layout_datebloodsugar);
       // timelayout_bloodsugar = (RelativeLayout)findViewById(R.id.timelayout_bloodsugar);
        etBeforemeal = (EditText)findViewById(R.id.etBeforemeal);
        etAftermeal = (EditText)findViewById(R.id.etAftermeal);
        tv_date = (TextView)findViewById(R.id.tv_date);
       // tv_time = (TextView)findViewById(R.id.tv_time);

        final Calendar calendar = Calendar.getInstance();

        /*timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                tv_time.setText(getContext().getString(R.string.time_formatted, hourOfDay, minute));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);*/


        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tv_date.setText(getContext().getString(R.string.date_formatted, year, month + 1, dayOfMonth));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        layout_datebloodsugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        /*timelayout_bloodsugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });*/
        findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInputs();;
            }
        });
        findViewById(R.id.ivCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCancel();
            }
        });
    }

    private void validateInputs(){

        if (!(date = tv_date.getText().toString().trim()).equals(""))
                if(!(beforemeal = etBeforemeal.getText().toString().trim()).equals(""))
                    if(!(aftermeal = etAftermeal.getText().toString().trim()).equals(""))
                        listener.onSubmit(beforemeal, aftermeal,date);
                        else
                    Toast.makeText(context, "Please enter valid date", Toast.LENGTH_SHORT).show();

        else
            Toast.makeText(context, "Please enter beforemeal", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Please enter aftermeal", Toast.LENGTH_SHORT).show();
    }

    public interface AddBloodSugarDialogClickListener {

        void onSubmit(String Aftermeal,String Beforemeal,String Date);
        void onCancel();

    }


}


