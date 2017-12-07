package com.innovellent.curight.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.activities.HomeActivity;
import com.innovellent.curight.activities.TrackActivity;
import com.innovellent.curight.activities.VaccineActivity;
import com.innovellent.curight.model.ReminderDialog;



public class ReminderFragment extends DialogFragment{
    ReminderDialog reminderDialog;
    TextView tvMedicine,tvVaccine;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_reminder,container,false);
        init(rootView);
        initClick();
        return rootView;
    }
    public  void init(View rootview){
        tvMedicine=(TextView)rootview.findViewById(R.id.tvMedicine);
        tvVaccine=(TextView)rootview.findViewById(R.id.tvVaccine);

    }
    public void initClick(){
        tvMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               ((HomeActivity)getActivity()).setupViewPagerReminder();
            }
        });
        tvVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(), VaccineActivity.class);
                startActivity(i);
            }
        });
    }
    private void reminderSet() {
        reminderDialog = new ReminderDialog(getActivity(), new ReminderDialog.ReminderDialogClickListener(){


            @Override
            public void onSubmit(String type) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("display", "N");
                editor.apply();

                if(type.equals("vaccine")){
                    reminderDialog.dismiss();
                     Intent i=new Intent(getActivity(), VaccineActivity.class);
                    startActivity(i);

                }else{
                    reminderDialog.dismiss();
                  // ((HomeActivity)getActivity()).setupViewPagerReminder();
                }


            }
        });

        reminderDialog.show();


    }

}
