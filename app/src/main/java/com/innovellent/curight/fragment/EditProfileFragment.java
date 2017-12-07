package com.innovellent.curight.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;


import com.innovellent.curight.R;
import com.innovellent.curight.adapter.BloodGroupSpinner;
import com.innovellent.curight.adapter.CitySpinner;
import com.innovellent.curight.adapter.InterestSpinner;
import com.innovellent.curight.adapter.MartialSpinner;
import com.innovellent.curight.adapter.PinSpinner;
import com.innovellent.curight.adapter.SpinnerAdapter;
import com.innovellent.curight.adapter.StateSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by sagar on 9/12/2017.
 */

public class EditProfileFragment extends Fragment implements View.OnClickListener{

       Spinner spGender,spMartialStatus,spIntetest,spBloodGroup,spCity,spState,spPin,spDesignation;
        ArrayList<String>  stateList=new ArrayList<>();
    ArrayList<String>  genderList=new ArrayList<>();
    ArrayList<String>  bloodList=new ArrayList<>();
    ArrayList<String>  cityList=new ArrayList<>();
    ArrayList<String>  martialList=new ArrayList<>();
    ArrayList<String>  pinList=new ArrayList<>();
    ArrayList<String>  interestList=new ArrayList<>();
    TextView tvDateOfBirth,tvGender,tvMartialStatus,tvInterest,tvBloodGroup,tvCity,tvState,tvPin;
    private int mYear, mMonth, mDay;
    boolean deletedDesignation=true,deletedmartial=true,deletedblood=true,deletedcity=true,deletedstate=true,deletedpin=true,deletedinterest=true;
    SpinnerAdapter mAdapter;
    MartialSpinner mAdapter1;
    BloodGroupSpinner mAdapter2;
    CitySpinner mAdapter3;
    StateSpinner mAdapter4;
    PinSpinner mAdapter5;
    InterestSpinner mAdapter6;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_edit_profile,container,false);
        init(rootView);
        initClick();
        return rootView;
    }
    public void init(View rootView){
        spMartialStatus=(Spinner)rootView.findViewById(R.id.spMartialStatus);
        spGender=(Spinner)rootView.findViewById(R.id.spGender);
        //spDesignation = (Spinner)rootView.findViewById(R.id.spDesignation);
        spBloodGroup=(Spinner)rootView.findViewById(R.id.spBloodGroup);
        spCity=(Spinner)rootView.findViewById(R.id.spCity);
        spState=(Spinner)rootView.findViewById(R.id.spState);
        spPin=(Spinner)rootView.findViewById(R.id.spPin);
        spIntetest=(Spinner)rootView.findViewById(R.id.spIntetest);
        tvGender=(TextView)rootView.findViewById(R.id.tvGender);
        tvMartialStatus=(TextView)rootView.findViewById(R.id.tvMartialStatus);
        tvBloodGroup=(TextView)rootView.findViewById(R.id.tvBloodGroup);
        tvDateOfBirth=(TextView)rootView.findViewById(R.id.tvDateOfBirth);
        tvCity=(TextView)rootView.findViewById(R.id.tvCity);
        tvState=(TextView)rootView.findViewById(R.id.tvState);
        tvPin=(TextView)rootView.findViewById(R.id.tvPin);
        tvInterest=(TextView)rootView.findViewById(R.id.tvInterest);
        genderList.add("Gender");
        genderList.add("Male");
        genderList.add("Female");
        martialList.add("Martial Status");
        martialList.add("Married");
        martialList.add("Unmarried");
        martialList.add("Divorce");
        bloodList.add("Blood Group");
        bloodList.add("A");
        bloodList.add("B");
        bloodList.add("A+");
        cityList.add("City");
        cityList.add("Bangalore");
        cityList.add("Pune");
        cityList.add("Mumbai");
        cityList.add("Delhi");
        stateList.add("State");
        stateList.add("Karnataka");
        stateList.add("Maharastra");
        stateList.add("Kerala");
        pinList.add("Pin");
        pinList.add("53234");
        pinList.add("34332");
        pinList.add("43334");
        pinList.add("553234");
        interestList.add("None Selected");
        interestList.add("abc");
        interestList.add("wyz");
        interestList.add("bcd");

    }

    public void initClick(){
        tvDateOfBirth.setOnClickListener(this);
        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, martialList);
        //dataAdapter.setDropDownViewResource(R.layout.home_fragment);
        //spMartialStatus.setAdapter(dataAdapter);
        mAdapter = new SpinnerAdapter(genderList,getActivity());
        spGender.setAdapter(mAdapter);
        spGender.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(genderList.size()>2) {
                    if (deletedDesignation) {
                        genderList.remove(0);
                        mAdapter.notifyDataSetChanged();
                        tvGender.setVisibility(View.VISIBLE);
                        deletedDesignation = false;
                    }
                }
                return false;
            }
        });

        mAdapter1 = new MartialSpinner(martialList,getActivity());
        spMartialStatus.setAdapter(mAdapter1);
        spMartialStatus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(martialList.size()>2) {
                    if (deletedmartial) {
                        martialList.remove(0);
                        mAdapter1.notifyDataSetChanged();
                        tvMartialStatus.setVisibility(View.VISIBLE);
                        deletedmartial = false;
                    }
                }
                return false;
            }
        });
        mAdapter2 = new BloodGroupSpinner(bloodList,getActivity());
        spBloodGroup.setAdapter(mAdapter2);
        spBloodGroup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(bloodList.size()>2) {
                    if (deletedblood) {
                        bloodList.remove(0);
                        mAdapter2.notifyDataSetChanged();
                        tvBloodGroup.setVisibility(View.VISIBLE);
                        deletedblood = false;
                    }
                }
                return false;
            }
        });
        mAdapter3 = new CitySpinner(cityList,getActivity());
        spCity.setAdapter(mAdapter3);
        spCity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(cityList.size()>2) {
                    if (deletedcity) {
                        cityList.remove(0);
                        mAdapter3.notifyDataSetChanged();
                        tvCity.setVisibility(View.VISIBLE);
                        deletedcity = false;
                    }
                }
                return false;
            }
        });
        mAdapter4 = new StateSpinner(stateList,getActivity());
        spState.setAdapter(mAdapter4);
        spState.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(stateList.size()>2) {
                    if (deletedstate) {
                        stateList.remove(0);
                        mAdapter4.notifyDataSetChanged();
                        tvState.setVisibility(View.VISIBLE);
                        deletedstate = false;
                    }
                }
                return false;
            }
        });
        mAdapter5 = new PinSpinner(pinList,getActivity());
        spPin.setAdapter(mAdapter5);
        spPin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(pinList.size()>2) {
                    if (deletedpin) {
                        pinList.remove(0);
                        mAdapter5.notifyDataSetChanged();
                        tvPin.setVisibility(View.VISIBLE);
                        deletedpin = false;
                    }
                }
                return false;
            }
        });
        mAdapter6 = new InterestSpinner(interestList,getActivity());
        spIntetest.setAdapter(mAdapter6);
        spIntetest.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(interestList.size()>2) {
                    if (deletedinterest) {
                        interestList.remove(0);
                        mAdapter6.notifyDataSetChanged();
                        tvInterest.setVisibility(View.VISIBLE);
                        deletedinterest = false;
                    }
                }
                return false;
            }
        });
     //   ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, genderList);
     //  dataAdapter.setDropDownViewResource(R.layout.spinner_item);
      // spGender.setAdapter(dataAdapter1);

      /*  ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, bloodList);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spBloodGroup.setAdapter(dataAdapter2);

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, cityList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCity.setAdapter(dataAdapter3);

        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stateList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spState.setAdapter(dataAdapter4);

        ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, pinList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPin.setAdapter(dataAdapter5);
        */
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvDateOfBirth:
                selectDate();
                break;


        }

    }
    public void selectDate(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {


                        GregorianCalendar GregorianCalendar = new GregorianCalendar(year, monthOfYear, dayOfMonth - 1);

                        int dayOfWeek = GregorianCalendar.get(GregorianCalendar.DAY_OF_WEEK);

                        String day = "", monthYear = "";
                        int month = monthOfYear + 1;
                        if (dayOfMonth >= 1 && dayOfMonth <= 9) {
                            day = "0" + dayOfMonth;
                        } else {
                            day = dayOfMonth + "";
                        }
                        if (month >= 1 && month <= 9) {
                            monthYear = "0" + month;
                        }else{
                            monthYear =  month+"";
                        }

                        String date = day + "-" + monthYear + "-" + year;
                        tvDateOfBirth.setText(dayOfMonth + "-" + (monthYear) + "-" + year);


                    }
                }, mYear, mMonth, mDay);


        datePickerDialog.show();
    }
}
