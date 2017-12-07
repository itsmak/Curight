package com.innovellent.curight.model;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.NumberPicker;

import com.innovellent.curight.R;


/**
 * Created by sagar on 9/13/2017.
 */

public class VaccineAddReminderDialog extends Dialog {

    public VaccineAddReminderDialog.VaccineAddReminderDialogClickListener listener;
    Context context;
    NumberPicker npDays;
    public VaccineAddReminderDialog(Context context, VaccineAddReminderDialog.VaccineAddReminderDialogClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;

    }

    public interface VaccineAddReminderDialogClickListener {

        void onSubmit();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_vaccine_add_reminder);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSubmit();
            }
        });

        findViewById(R.id.btnADD).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSubmit();
            }
        });

    }
}

