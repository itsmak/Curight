package com.innovellent.curight.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.innovellent.curight.R;
import com.innovellent.curight.activities.MedicineReminderSetActivity;
import com.innovellent.curight.activities.ProfileActivity;
import com.innovellent.curight.adapter.MedicineReminderAdapter;
import com.innovellent.curight.adapter.PROFILE_SPINNER_ADAPTER;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.ChangeReminderPreferenceDialog;
import com.innovellent.curight.model.MEDCN_FEED;
import com.innovellent.curight.model.MED_REMAINDER_RESPONSE;
import com.innovellent.curight.model.Medicine;
import com.innovellent.curight.model.MyProfile_Response;
import com.innovellent.curight.model.POST_MED_CLASS;
import com.innovellent.curight.model.PROFILE;
import com.innovellent.curight.model.PROFILE_FEED;
import com.innovellent.curight.model.PostBodyProfile;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MedicineReminderFragment extends Fragment implements View.OnClickListener{

    RecyclerView recyclerView;
    HorizontalCalendar horizontalCalendar;
    ChangeReminderPreferenceDialog reminderPreferenceDialog;
    ArrayList<Medicine> arrayList=new ArrayList<Medicine>();
    Spinner spItem;
    ImageView ivReminder;
    String[]spinner1={"John","Jobby","Suresh","Mahesh"};
    private static final String TAG = ".Retro_MainActivity";
    private static final String BASE_URL ="http://13.59.209.135:8090/diagnosticAPI/webapi/";
    ArrayList<PROFILE> spinnerList=new ArrayList<PROFILE>();
    PROFILE_SPINNER_ADAPTER customSpinnerAdapter3;
    static String USER_ID,M_YEAR,M_MONTH,M_DAY,FINAL_DATE;
    int counter= 0;
    MedicineReminderAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_medicine_reminder,container,false);

        init(rootView);

        initRegister();

        Log.d(TAG,"calender: userid"+USER_ID);

        addCalendar(rootView);
        Log.d(TAG,"final date:"+FINAL_DATE);
        getSpinnerData();
        return rootView;
    }


    public String addCalendar(View rootView){
      Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

     horizontalCalendar = new HorizontalCalendar.Builder(rootView, R.id.calendarView)
     .startDate(startDate.getTime()).endDate(endDate.getTime()).datesNumberOnScreen(5).dayNameFormat("EEE")
     .dayNumberFormat("dd").monthFormat("MMM").textSize(14f, 24f, 14f).showDayName(true)
     .showMonthName(true).textColor(Color.LTGRAY, Color.WHITE).selectedDateBackground(Color.TRANSPARENT).build();
     Log.d(TAG,"calender:");
     horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {

         @Override
         public void onDateSelected(Date date, int position) {

           //  Toast.makeText(getContext(), DateFormat.getDateInstance().format(date) + " is selected!", Toast.LENGTH_SHORT).show();
             counter++;
             Log.d(TAG,"calender: year"+date.getYear());
             Log.d(TAG,"calender: month"+date.getMonth());
             Log.d(TAG,"calender: day"+date.getDate());
            datecalculation(String.valueOf(date.getYear()),String.valueOf(date.getMonth()),String.valueOf(date.getDate()));
             Log.d(TAG,"final date: selected"+FINAL_DATE);

         }
     });
    return FINAL_DATE;
    }

    private void datecalculation(String year, String month, String date) {
        if(year.equals("117")){
            M_YEAR="2017";
        }else if(M_YEAR.equals("118")){
            M_YEAR="2018";
        }
        if(month.equals("0")){
            M_MONTH="01";
        }else if(month.equals("1")){
            M_MONTH="02";
        }else if(month.equals("2")){
            M_MONTH="03";
        }else if(month.equals("3")){
            M_MONTH="04";
        }else if(month.equals("4")){
            M_MONTH="05";
        }else if(month.equals("5")){
            M_MONTH="06";
        }else if(month.equals("6")){
            M_MONTH="07";
        }else if(month.equals("7")){
            M_MONTH="08";
        }else if(month.equals("8")){
            M_MONTH="09";
        }else if(month.equals("9")){
            M_MONTH="10";
        }else if(month.equals("9")){
            M_MONTH="10";
        }else if(month.equals("10")){
            M_MONTH="11";
        }
        else if(month.equals("11")){
            M_MONTH="12";
        }
        M_DAY =date;
        FINAL_DATE = M_YEAR+"-"+M_MONTH+"-"+M_DAY;
        Log.d(TAG,"calender: finaldate"+FINAL_DATE);

    }

    public void init(View rootView){
        spItem=(Spinner)rootView.findViewById(R.id.spYear);
        ivReminder=(ImageView)rootView.findViewById(R.id.ivReminder);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view);

    }
    public void initRegister(){
       ivReminder.setOnClickListener(this);

    }
    private void getSpinnerData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface reditapi = retrofit.create(ApiInterface.class);

        PostBodyProfile postBodyprofile = new PostBodyProfile(1,"family");
        Call<MyProfile_Response> call = reditapi.getProfile(postBodyprofile);

        call.enqueue(new Callback<MyProfile_Response>() {
            @Override
            public void onResponse(Call<MyProfile_Response> call, Response<MyProfile_Response> response) {


                if(response.body()!=null) {


                    Log.e(TAG, "profileResponse: code: " + response.body().getCode());
                    ArrayList<PROFILE_FEED> result = response.body().getResults();
                    Log.e(TAG, "profileResponse: listsize: " +result.size());
                    for(int i=0;i<result.size();i++)
                    {

                        USER_ID = result.get(i).getUserid();
                        //spinnerList.add(new PROFILE("","","",""));
                        spinnerList.add(new PROFILE(result.get(i).getUserid(),result.get(i).getName(),result.get(i).getAge(),result.get(i).getRelationship()));
                    }
                    getData2();
                    USER_ID = result.get(0).getUserid();
                  //  FINAL_DATE= finaldate;
                    Log.d(TAG,"inloop: userid"+USER_ID);
                    Log.d(TAG,"inloop: finaldate"+FINAL_DATE);
                    //Get_MED_Data(USER_ID,FINAL_DATE);
                    //api call
                }else{

                    Toast.makeText(getActivity(), response.message(),Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<MyProfile_Response> call, Throwable t) {

                Log.e(TAG,"onFailure: Somethings went wrong"+t.getMessage());
                Toast.makeText(getActivity(),"Somethings went wrong",Toast.LENGTH_SHORT).show();

            }
        });
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
    public void getData2() {

        customSpinnerAdapter3 = new PROFILE_SPINNER_ADAPTER(getActivity(), spinnerList);
        spItem.setAdapter(customSpinnerAdapter3);
        spItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                USER_ID = spinnerList.get(i).getUser_id();
                Log.d(TAG, "inspiner userid" + USER_ID);
                Log.d(TAG, "inspiner date" + FINAL_DATE);
                Get_MED_Data(USER_ID,FINAL_DATE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void Get_MED_Data(String user_id,String finaldate) {
        Log.d(TAG,"getmedcalled");
        clearData();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Toast.makeText(getActivity(),"I am getting called",Toast.LENGTH_SHORT).show();
        ApiInterface reditapi = retrofit.create(ApiInterface.class);
        Log.d(TAG, "onResponse: userid" + user_id);
        Log.d(TAG, "onResponse: finaldate" + finaldate);
        POST_MED_CLASS post_med_class = new POST_MED_CLASS(Integer.parseInt(user_id),finaldate);

        Call<MED_REMAINDER_RESPONSE> call = reditapi.get_med_remainder(post_med_class);
        call.enqueue(new Callback<MED_REMAINDER_RESPONSE>() {
            @Override
            public void onResponse(Call<MED_REMAINDER_RESPONSE> call, Response<MED_REMAINDER_RESPONSE> response) {

                if(response.body()!=null) {

                    Log.d(TAG, "medResponse: Server Response: " + response);
                    Log.d(TAG, "medResponse: Server Response: " + response.body());
                    Log.d(TAG, "medResponse: code " + response.body().getCode());
                    Log.d(TAG, "medResponse: Result: " + response.body().getResults());
                    ArrayList<MEDCN_FEED> result = response.body().getResults();
                    Log.d(TAG, "medResponse: code: " +result.size());
                    for(int i=0;i<result.size();i++)
                    {
                        Log.d(TAG, "medResponse: medcn name: " + result.get(i).getMedicinename());
                        arrayList.add(new Medicine(result.get(i).getMedicinename(),result.get(i).getStrength(),result.get(i).getMorningtime(),
                        result.get(i).getMorningmedstatus(),result.get(i).getNoontime(),result.get(i).getNoonmedstatus(),result.get(i).getEveningtime(),
                        result.get(i).getEveninmedstatus(),result.get(i).getNighttime(),result.get(i).getNightmedstatus()));
                    }
                }
                mAdapter=new MedicineReminderAdapter (getActivity(),arrayList);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(Call<MED_REMAINDER_RESPONSE> call, Throwable t) {

                Log.e(TAG,"onFailure: Somethings went wrong"+t.getMessage());
                Toast.makeText(getActivity(),"Somethings went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void clearData() {
        mAdapter = new MedicineReminderAdapter(getActivity(),arrayList);
        arrayList.clear(); //clear list
        //mAdapter.notifyDataSetChanged(); //let your adapter know about the changes and reload view.

    }
}
