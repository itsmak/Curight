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

public class ChangeMorningPreferenceDialog extends Dialog {

    private Medicine item;
    public ChangeMorningPreferenceDialog.ChangeReminderPreferenceDialogClickListener listener;
    Context context;
    public ImageView ivCancel;
    EditText etComments;
    TextView tv_AboutDetails;
    String about;

    public ChangeMorningPreferenceDialog(Context context, Medicine item, ChangeMorningPreferenceDialog.ChangeReminderPreferenceDialogClickListener listener) {
        super(context);
        this.context = context;
        this.item = item;
        this.listener = listener;
    }

    public interface ChangeReminderPreferenceDialogClickListener {
        void onTake(Medicine updateditems);
        void onSkip(Medicine updateditems);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_change_reminder_preference);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        findViewById(R.id.btnTake).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTake(new Medicine(item.getMedreminderparentid(),item.getMedreminderchildid(),item.getDate(),item.getMedicinename(),item.getMedicinemeasure(),item.getMorningtime(),"T",item.getNoontime(),item.getNoonmedstatus(),
                        item.getEveningtime(),item.getEveninmedstatus(),item.getNighttime(),item.getNightmedstatus()));
            }
        });
        findViewById(R.id.btnSkip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSkip(new Medicine(item.getMedreminderparentid(),item.getMedreminderchildid(),item.getDate(),item.getMedicinename(),item.getMedicinemeasure(),item.getMorningtime(),"S",item.getNoontime(),item.getNoonmedstatus(),
                        item.getEveningtime(),item.getEveninmedstatus(),item.getNighttime(),item.getNightmedstatus()));
            }
        });
    }
}
