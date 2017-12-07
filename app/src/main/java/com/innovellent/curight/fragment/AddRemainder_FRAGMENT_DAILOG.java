package com.innovellent.curight.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import com.innovellent.curight.R;
import java.util.Calendar;

import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.Toast;



/**
 * Created by Mak on 12/5/2017.
 */
public class AddRemainder_FRAGMENT_DAILOG extends Dialog{
    public AddRemainder_FRAGMENT_DAILOG.AddRemainder_FRAGMENT_DAILOGClickListener listener;
    Context context;
    EditText addDueOn_edttxt,addTakeOn_edtxt,addGivenBy_edtxt,addDetails_edttxt,addReminder1_txtvw,addReminder2_txtvw;
    public AddRemainder_FRAGMENT_DAILOG(Context context, AddRemainder_FRAGMENT_DAILOG.AddRemainder_FRAGMENT_DAILOGClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;

    }

    public interface AddRemainder_FRAGMENT_DAILOGClickListener {

        //String due_on,String Taken_On,String Given_By,String Details,String remainder1,String remainder2
        void onSubmit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dailog_add_remainder);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        addDueOn_edttxt = (EditText)findViewById(R.id.addDueOn_edttxt);
        addTakeOn_edtxt = (EditText)findViewById(R.id.addTakeOn_edtxt);
        addGivenBy_edtxt = (EditText)findViewById(R.id.addGivenBy_edtxt);
        addDetails_edttxt = (EditText)findViewById(R.id.addDetails_edttxt);
        addReminder1_txtvw = (EditText)findViewById(R.id.addReminder1_txtvw);
        addReminder2_txtvw = (EditText)findViewById(R.id.addReminder2_txtvw);

        findViewById(R.id.add_remainder_cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSubmit();
            }
        });
        findViewById(R.id. add_remainder_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSubmit();
            }
        });

        addDueOn_edttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("clicked","dailog");
            }


        });

    }
//    public void showDatePickerDialog() {
//        DialogFragment newFragment = new DatePickerFragment();
//        newFragment.show(getFragmentManager(), "datePicker");
//    }
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            final Calendar c = Calendar.getInstance();
//            int year = c.get(Calendar.YEAR);
//            int month = c.get(Calendar.MONTH);
//            int day = c.get(Calendar.DAY_OF_MONTH);
//
//            // Create a new instance of DatePickerDialog and return it
//            return new DatePickerDialog(getActivity(), (OnDateSetListener) getActivity(), year, month, day);
//        }
//
//    }
//
//    @Override
//    public void onDateSet(DatePicker view, int year, int monthOfYear,
//                          int dayOfMonth) {
//        // TODO Auto-generated method stub
//
//    }

}
