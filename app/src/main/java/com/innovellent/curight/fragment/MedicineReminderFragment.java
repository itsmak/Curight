package com.innovellent.curight.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.innovellent.curight.R;
import com.innovellent.curight.activities.MedicineReminderSetActivity;
import com.innovellent.curight.activities.ProfileActivity;
import com.innovellent.curight.adapter.CustomSpinnerAdapter2;
import com.innovellent.curight.adapter.MedicineReminderAdapter;
import com.innovellent.curight.model.ChangeReminderPreferenceDialog;
import com.innovellent.curight.model.Medicine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;


public class MedicineReminderFragment extends Fragment implements View.OnClickListener{

    RecyclerView recyclerView;
    HorizontalCalendar horizontalCalendar;
    ChangeReminderPreferenceDialog reminderPreferenceDialog;
    ArrayList<Medicine> arrayList=new ArrayList<Medicine>();
    Spinner spItem;
    ImageView ivReminder;
    String[]spinner1={"John","Jobby","Suresh","Mahesh"};
    MedicineReminderAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_medicine_reminder,container,false);
        init(rootView);
        initRegister();
        addData();
        CustomSpinnerAdapter2 customSpinnerAdapter3=new CustomSpinnerAdapter2(getActivity(),spinner1);
        spItem.setAdapter(customSpinnerAdapter3);
        spItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

         addCalendar(rootView);
        return rootView;
    }

    public void addData() {

        arrayList.add(new Medicine("DEDEP 20MG tablet", "20gm"));
        arrayList.add(new Medicine("FLUTIVATE 0.05% W/W  OINTMENT", "100gm"));
        arrayList.add(new Medicine("ENTOFOAM 2GM Inhaler", "3ml"));
        mAdapter = new MedicineReminderAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    public void addCalendar(View rootView){
      Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

     horizontalCalendar = new HorizontalCalendar.Builder(rootView, R.id.calendarView).startDate(startDate.getTime()).endDate(endDate.getTime()).datesNumberOnScreen(5).dayNameFormat("EEE").dayNumberFormat("dd").monthFormat("MMM").textSize(14f, 24f, 14f).showDayName(true).showMonthName(true).textColor(Color.LTGRAY, Color.WHITE).selectedDateBackground(Color.TRANSPARENT).build();
     horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {

         @Override
         public void onDateSelected(Date date, int position) {
           //  Toast.makeText(getContext(), DateFormat.getDateInstance().format(date) + " is selected!", Toast.LENGTH_SHORT).show();
         }
     });

    }

    public void init(View rootView){
        spItem=(Spinner)rootView.findViewById(R.id.spItem);
        ivReminder=(ImageView)rootView.findViewById(R.id.ivReminder);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view);

    }
    public void initRegister(){
       ivReminder.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llprofile:
                Intent i=new Intent(getActivity(), ProfileActivity.class);
                startActivity(i);
               break;
            case R.id.ivReminder:
                Intent i1=new Intent(getActivity(), MedicineReminderSetActivity.class);
                startActivity(i1);
                break;


        }

    }
}
