package com.innovellent.curight.model;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.innovellent.curight.R;

/**
 * Created by Mak on 12/14/2017.
 */

public class ChangeEvngPrefrenseDialog extends Dialog {
    private Medicine item;
    public ChangeEvngPrefrenseDialog.ChangeEvngPrefrenseDialogClickListener listener;
    Context context;
    public ImageView ivCancel;
    public ChangeEvngPrefrenseDialog(Context context, Medicine item, ChangeEvngPrefrenseDialog.ChangeEvngPrefrenseDialogClickListener listener) {
        super(context);
        this.context = context;
        this.item = item;
        this.listener = listener;
    }
    public interface ChangeEvngPrefrenseDialogClickListener {
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
                listener.onTake(new Medicine(item.getMedreminderparentid(),item.getMedreminderchildid(),item.getDate(),item.getMedicinename(),item.getMedicinemeasure(),item.getMorningtime(),item.getMorningmedstatus(),item.getNoontime(),item.getNoonmedstatus(),
                        item.getEveningtime(),"T",item.getNighttime(),item.getNightmedstatus()));
            }
        });
        findViewById(R.id.btnSkip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSkip(new Medicine(item.getMedreminderparentid(),item.getMedreminderchildid(),item.getDate(),item.getMedicinename(),item.getMedicinemeasure(),item.getMorningtime(),item.getMorningmedstatus(),"S",item.getNoonmedstatus(),
                        item.getEveningtime(),"S",item.getNighttime(),item.getNightmedstatus()));
            }
        });
    }
}
