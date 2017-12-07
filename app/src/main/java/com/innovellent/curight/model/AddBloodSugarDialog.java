package com.innovellent.curight.model;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.innovellent.curight.R;

/**
 * Created by sagar on 9/30/2017.
 */

public class AddBloodSugarDialog extends Dialog {

    public AddBloodSugarDialog.AddBloodSugarDialogClickListener listener;
    Context context;
    String about;

    public AddBloodSugarDialog(Context context, AddBloodSugarDialog.AddBloodSugarDialogClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;

    }

    public interface AddBloodSugarDialogClickListener {

        void onSubmit();
        void onCancel();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_blood_sugar);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSubmit();
            }
        });
        findViewById(R.id.ivCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCancel();
            }
        });
    }
}


