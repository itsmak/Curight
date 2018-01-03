package com.innovellent.curight.model;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by SUNIL on 1/2/2018.
 */

public class AddFCTCountDialog extends Dialog {

    public AddFCTCountDialog.AddFCTDialogClickListener listener;
    Context context;
    RelativeLayout date_layout;
    EditText tv_normalduration,et_gap,et_reminderdays,etNotes;
    EditText tv_date;
    private static final String TAG = ".Retro_MainActivity";
    RadioGroup radio_group;
    RadioButton radio_button_yes,radio_button_no;
    DatePickerDialog datePickerDialog;
    Button btnSave_fct;
    String normalduration,gap,currentperiod,missing,notes,reminder,radiobutton_selected="",Currentperiod,Date,fctid;
    SimpleDateFormat simpledateformat;

    public AddFCTCountDialog(Context context,AddFCTCountDialog.AddFCTDialogClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_addfct);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        date_layout = (RelativeLayout)findViewById(R.id.date_layout);
        btnSave_fct = (Button)findViewById(R.id.btnSave_fct);
        tv_normalduration = (EditText)findViewById(R.id.tv_normalduration);
        et_gap = (EditText)findViewById(R.id.et_gap);
        et_reminderdays = (EditText)findViewById(R.id.et_reminderdays);
        etNotes = (EditText)findViewById(R.id.etNotes);
        tv_date = (EditText)findViewById(R.id.tv_date);
        radio_group = (RadioGroup)findViewById(R.id.radio_group);
        radio_button_yes = (RadioButton)findViewById(R.id.radio_button_yes);
        radio_button_no = (RadioButton)findViewById(R.id.radio_button_no);



        final Calendar calendar = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date = simpledateformat.format(calendar.getTime());
        Log.d("currentdate", Date);

        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tv_date.setText(getContext().getString(R.string.date_formatted, year, month + 1, dayOfMonth));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        date_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });


        findViewById(R.id.btnSave_fct).setOnClickListener(new View.OnClickListener() {
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


    private void validateInputs(){

        if(radio_button_yes.isChecked())
        {
            radio_button_no.setChecked(false);
            radiobutton_selected="Y";
        }else if(radio_button_no.isChecked())
        {
            radio_button_yes.setChecked(false);
            radiobutton_selected="N";
        }

        if(tv_normalduration.getText().toString().equals(""))
        {
            Toast.makeText(context,"Please enter Duration",Toast.LENGTH_SHORT).show();
        }else if(et_gap.getText().toString().trim().equals("")){
            Toast.makeText(context,"Please enter gap",Toast.LENGTH_SHORT).show();
        }else if(et_reminderdays.getText().toString().trim().equals("")){
            Toast.makeText(context,"Please enter Days",Toast.LENGTH_SHORT).show();
        }else if(tv_date.getText().toString().trim().equals(""))
        {
            Toast.makeText(context,"Please enter Date",Toast.LENGTH_SHORT).show();
        }else if(!(radio_button_yes.isChecked())&&!(radio_button_no.isChecked()))
        {
            Toast.makeText(context,"Please Select any one yes or no",Toast.LENGTH_SHORT).show();
        }else {
            Log.d(TAG,"radiovalue:"+radiobutton_selected);
            listener.onSubmit(tv_normalduration.getText().toString().trim(), et_gap.getText().toString().trim(), et_reminderdays.getText().toString().trim(),tv_date.getText().toString().trim(),radiobutton_selected,etNotes.getText().toString().trim(),Date);
            Log.d("dilogcalling", "called");
        }


    }

    public interface AddFCTDialogClickListener {

        void onSubmit(String Normalperiodduration,String Gap,String Reminderdays,String CurrentPeriod,String Miss,String Notes,String Date);

        void onCancel();

    }
}
