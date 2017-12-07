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
 * Created by sagar on 9/19/2017.
 */

public class ChangeReminderPreferenceDialog extends Dialog {

    public ChangeReminderPreferenceDialog.ChangeReminderPreferenceDialogClickListener listener;
    Context context;
    public ImageView ivCancel;
    EditText etComments;
    TextView tv_AboutDetails;
    String about;

    public ChangeReminderPreferenceDialog(Context context, ChangeReminderPreferenceDialog.ChangeReminderPreferenceDialogClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;

    }

    public interface ChangeReminderPreferenceDialogClickListener {

        void onSubmit(String change);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_change_reminder_preference);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSubmit("close");
            }
        });
        findViewById(R.id.btnTake).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSubmit("take");
            }
        });
        findViewById(R.id.btnSkip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSubmit("skip");
            }
        });



    }
}
