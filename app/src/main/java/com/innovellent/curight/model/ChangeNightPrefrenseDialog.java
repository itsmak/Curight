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
 * Created by Mak on 12/14/2017.
 */

public class ChangeNightPrefrenseDialog extends Dialog {
    private Medicine item;
    public ChangeNightPrefrenseDialog.ChangeNightPrefrenseDialogClickListener listener;
    Context context;
    public ImageView ivCancel;
    EditText etComments;
    TextView tv_AboutDetails;
    String about;

    public ChangeNightPrefrenseDialog(Context context, Medicine item, ChangeNightPrefrenseDialog.ChangeNightPrefrenseDialogClickListener listener) {
        super(context);
        this.context = context;
        this.item = item;
        this.listener = listener;
    }

    public interface ChangeNightPrefrenseDialogClickListener {
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
                listener.onTake(new Medicine(item.getMedreminderparentid(), item.getMedreminderchildid(), item.getDate(), item.getMedicinename(), item.getMedicinemeasure(), item.getMorningtime(), item.getMorningmedstatus(), item.getNoontime(), "T",
                        item.getEveningtime(), item.getEveninmedstatus(), item.getNighttime(), item.getNightmedstatus()));
            }
        });
        findViewById(R.id.btnSkip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSkip(new Medicine(item.getMedreminderparentid(), item.getMedreminderchildid(), item.getDate(), item.getMedicinename(), item.getMedicinemeasure(), item.getMorningtime(), item.getMorningmedstatus(), item.getNoontime(), "S",
                        item.getEveningtime(), item.getEveninmedstatus(), item.getNighttime(), item.getNightmedstatus()));
            }
        });
    }
}
