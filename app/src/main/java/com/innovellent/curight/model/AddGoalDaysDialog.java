package com.innovellent.curight.model;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.NumberPicker;

import com.innovellent.curight.R;


public class AddGoalDaysDialog extends Dialog {

    public AddGoalDaysDialog.AddGoalDaysDialogClickListener listener;
    Context context;
   NumberPicker npDays;
    public AddGoalDaysDialog(Context context, AddGoalDaysDialog.AddGoalDaysDialogClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;

    }

    public interface AddGoalDaysDialogClickListener {

        void onSubmit();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_days_goal);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       // NumberPicker np = (NumberPicker) findViewById(R.id.npDays);
       // np.setMinValue(0);
        //np.setMinValue(365);


    }
}

