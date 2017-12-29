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
 * Created by sagar on 10/1/2017.
 */

public class AddBloodCountDialog extends Dialog {

    public AddBloodCountDialog.AddBloodCountDialogClickListener listener;
    Context context;
    public ImageView ivCancel;
    EditText etComments;
    TextView tv_AboutDetails;
    String about;

    public AddBloodCountDialog(Context context, AddBloodCountDialog.AddBloodCountDialogClickListener listener) {
        super(context);
        this.context = context;
        this.about = about;
        this.listener = listener;

    }

    public interface AddBloodCountDialogClickListener {

        void onSubmit();
        void onCancel();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_blood_count);
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


