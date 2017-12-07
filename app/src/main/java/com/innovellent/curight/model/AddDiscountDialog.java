package com.innovellent.curight.model;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.innovellent.curight.R;
import com.innovellent.curight.activities.PaymentDetailsActivity;
import com.innovellent.curight.adapter.CustomSpinnerAdapter2;
import com.innovellent.curight.adapter.PaymentDetailsAdapter;

/**
 * Created by sagar on 10/12/2017.
 */

public class AddDiscountDialog extends Dialog {

    public AddDiscountDialog.AddDiscountDialogClickListener listener;
    Context context;
    Spinner spCode;
    EditText etCode;
    String strcode;
    NumberPicker npDays;
    String[]spinner1={"1dh","2hn","4be3","5v3","53q"};
    public AddDiscountDialog(Context context, AddDiscountDialog.AddDiscountDialogClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;

    }

    public interface AddDiscountDialogClickListener {

        void onSubmit(String code);
        void onCancel();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_discount_code);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        spCode=(Spinner)findViewById(R.id.spCode);
        etCode=(EditText)findViewById(R.id.etCode);
        CustomSpinnerAdapter2 customSpinnerAdapter3 = new CustomSpinnerAdapter2(context, spinner1);
        spCode.setAdapter(customSpinnerAdapter3);
        spCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               strcode = spCode.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        findViewById(R.id.ivCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCancel();

            }
        });

        findViewById(R.id.btnADD).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etCode.getText().toString().trim().length() > 0){
                   listener.onSubmit(etCode.getText().toString());
                }else{
                    listener.onSubmit(strcode);
                }

            }
        });

    }
}

