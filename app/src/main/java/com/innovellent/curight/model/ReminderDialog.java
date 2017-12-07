package com.innovellent.curight.model;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovellent.curight.R;


/**
 * Created by sagar on 10/2/2017.
 */

public class ReminderDialog extends Dialog {

    public ReminderDialog.ReminderDialogClickListener listener;
    Context context;
    public ImageView ivCancel;
    EditText etComments;
    TextView tv_AboutDetails;
    String about;

    public ReminderDialog(Context context, ReminderDialog.ReminderDialogClickListener listener) {
        super(context);
        this.context = context;
        this.about = about;
        this.listener = listener;

    }

    public interface ReminderDialogClickListener {

        void onSubmit(String type);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_reminder);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
     //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
       //         WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        findViewById(R.id.tvVaccine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSubmit("vaccine");
            }
        });
        findViewById(R.id.tvMedicine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSubmit("medicine");
            }
        });
    }
}



