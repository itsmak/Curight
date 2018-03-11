package com.innovellent.curight.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;
import com.innovellent.curight.R;
import com.innovellent.curight.activities.DiagnosticTestListActivity;
import com.innovellent.curight.activities.MedicineReminderSetActivity;
import com.innovellent.curight.activities.ProfileActivity;
import com.innovellent.curight.adapter.MedicineReminderAdapter;
import com.innovellent.curight.adapter.PROFILE_SPINNER_ADAPTER;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.ChangeEvngPrefrenseDialog;
import com.innovellent.curight.model.ChangeMorningPreferenceDialog;
import com.innovellent.curight.model.ChangeNightPrefrenseDialog;
import com.innovellent.curight.model.ChangeNoonPreferenceDialog;
import com.innovellent.curight.model.MEDCN_FEED;
import com.innovellent.curight.model.MED_REMAINDER_RESPONSE;
import com.innovellent.curight.model.Medicine;
import com.innovellent.curight.model.MyProfile_Response;
import com.innovellent.curight.model.POST_MED_CLASS;
import com.innovellent.curight.model.POST_TIME_UPDATE_CLASS;
import com.innovellent.curight.model.PROFILE;
import com.innovellent.curight.model.PROFILE_FEED;
import com.innovellent.curight.model.PostBodyClass;
import com.innovellent.curight.model.PostBodyProfile;
import com.innovellent.curight.model.VACCINE_UPDATE_RESPONSE;
import com.innovellent.curight.utility.Config;
import com.pixplicity.easyprefs.library.Prefs;

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

    private static final String TAG = ".Curight";
 //   private static final String BASE_URL ="http://13.59.209.135:8090/diagnosticAPI/webapi/";
    static String USER_ID,M_YEAR,M_MONTH,M_DAY,FINAL_DATE;
    RecyclerView recyclerView;
    HorizontalCalendar horizontalCalendar;
    ChangeMorningPreferenceDialog morningPreferenceDialog;
    ChangeNoonPreferenceDialog noonPreferenceDialog;
    ChangeEvngPrefrenseDialog eveningPreferenceDialog;
    ChangeNightPrefrenseDialog nightPreferenceDialog;
    ArrayList<Medicine> arrayList=new ArrayList<Medicine>();
    Spinner spItem;
    TextView tv_locationtxt,tv_locationsymbl,tvTitle;
    ImageView ivReminder;
    String[]spinner1={"John","Jobby","Suresh","Mahesh"};
    ArrayList<PROFILE> spinnerList=new ArrayList<PROFILE>();
    PROFILE_SPINNER_ADAPTER customSpinnerAdapter3;
    int counter= 0;
    int position;
    Context context;
    MedicineReminderAdapter mAdapter;
    private ProgressDialog progressDialog;
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        context = getActivity();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_medicine_reminder,container,false);
        //getActivity().setupViewPagerReminder();
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

          if (year.equals("117")) {
              M_YEAR = "2017";
          } else if (year.equals("118")) {
              M_YEAR = "2018";
          }
          if (month.equals("0")) {
              M_MONTH = "01";
          } else if (month.equals("1")) {
              M_MONTH = "02";
          } else if (month.equals("2")) {
              M_MONTH = "03";
          } else if (month.equals("3")) {
              M_MONTH = "04";
          } else if (month.equals("4")) {
              M_MONTH = "05";
          } else if (month.equals("5")) {
              M_MONTH = "06";
          } else if (month.equals("6")) {
              M_MONTH = "07";
          } else if (month.equals("7")) {
              M_MONTH = "08";
          } else if (month.equals("8")) {
              M_MONTH = "09";
          } else if (month.equals("9")) {
              M_MONTH = "10";
          } else if (month.equals("9")) {
              M_MONTH = "10";
          } else if (month.equals("10")) {
              M_MONTH = "11";
          } else if (month.equals("11")) {
              M_MONTH = "12";
          }
          M_DAY = date;
          FINAL_DATE = M_YEAR + "-" + M_MONTH + "-" + M_DAY;
          Log.d(TAG, "calender: finaldate" + FINAL_DATE);

    }

    public void init(View rootView){
        spItem=(Spinner)rootView.findViewById(R.id.spYear);
        ivReminder=(ImageView)rootView.findViewById(R.id.ivReminder);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view);
        tv_locationtxt = (TextView) getActivity().findViewById(R.id.tv_locationtxt);
        tv_locationsymbl = (TextView) getActivity().findViewById(R.id.tv_locationsymbl);
        tvTitle = (TextView) getActivity().findViewById(R.id.tvTitle);
        tvTitle.setText("Remainder");
        tv_locationtxt.setVisibility(View.GONE);
        tv_locationsymbl.setVisibility(View.GONE);
    }
    public void initRegister(){
       ivReminder.setOnClickListener(this);

    }
    private void getSpinnerData(){
        progressDialog = ProgressDialog.show(context, "Loading", "please wait", true, false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        int uid = (int) Prefs.getLong("user_id",0);
        Log.d(TAG,"Shared_profile_uid"+uid);
        ApiInterface reditapi = retrofit.create(ApiInterface.class);

        PostBodyProfile postBodyprofile = new PostBodyProfile(uid,"family");
        Call<MyProfile_Response> call = reditapi.getProfile(postBodyprofile);
        progressDialog.dismiss();
        call.enqueue(new Callback<MyProfile_Response>() {
            @Override
            public void onResponse(Call<MyProfile_Response> call, Response<MyProfile_Response> response) {
                progressDialog.dismiss();

                if(response.body()!=null) {
                    Log.e(TAG, "profileResponse: code: " + response.body().getCode());
                    ArrayList<PROFILE_FEED> result = response.body().getResults();
                    Log.e(TAG, "profileResponse: listsize: " +result.size());
                    for(int i=0;i<result.size();i++)
                    {
                        String name = result.get(i).getName();
                        String lastName = "";
                        String firstName= "";
                        if(name.split("\\w+").length>1){

                            //lastName = name.substring(name.lastIndexOf(" ")+1);
                            firstName = name.substring(0, name.lastIndexOf(' '));
                        }
                        else{
                            firstName = name;
                        }
                        USER_ID = result.get(i).getUserid();
                        //spinnerList.add(new PROFILE("","","",""));
                        spinnerList.add(new PROFILE(result.get(i).getUserid(),result.get(i).getId(),firstName,result.get(i).getAge(),result.get(i).getRelationship()));
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
                progressDialog.dismiss();
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
                Prefs.putLong("spinner_id", Long.parseLong(spinnerList.get(i).getUser_id()));
                int uid = (int) Prefs.getLong("spinner_id",0);
                Log.d(TAG,"Shared_profile_spinnerid"+uid);
                Log.d(TAG, "inspiner date" + FINAL_DATE);
                Get_MED_Data(uid,FINAL_DATE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void Get_MED_Data(int user_id,String finaldate) {
        Log.d(TAG,"getmedcalled");
        progressDialog = ProgressDialog.show(context, "Loading", "please wait", true, false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        clearData();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Toast.makeText(getActivity(),"I am getting called",Toast.LENGTH_SHORT).show();
        ApiInterface reditapi = retrofit.create(ApiInterface.class);
        Log.d(TAG, "onResponse: userid" + user_id);
        Log.d(TAG, "onResponse: finaldate" + finaldate);
        POST_MED_CLASS post_med_class = new POST_MED_CLASS(user_id,finaldate);

        Call<MED_REMAINDER_RESPONSE> call = reditapi.get_med_remainder(post_med_class);
        progressDialog.dismiss();
        call.enqueue(new Callback<MED_REMAINDER_RESPONSE>() {
            @Override
            public void onResponse(Call<MED_REMAINDER_RESPONSE> call, Response<MED_REMAINDER_RESPONSE> response) {
                progressDialog.dismiss();
                if(response.body()!=null) {

                    Log.d(TAG, "medResponse: Server Response: " + response);
                    Log.d(TAG, "medResponse: Server Response: " + response.body());
                    Log.d(TAG, "medResponse: code " + response.body().getCode());
                    if(response.body().getCode()==403)
                    {
                        Toast.makeText(getActivity(),"No data for the User",Toast.LENGTH_SHORT).show();
                    }
                    Log.d(TAG, "medResponse: Result: " + response.body().getResults());
                    ArrayList<MEDCN_FEED> result = response.body().getResults();
                    Log.d(TAG, "medResponse: code: " +result.size());
                    for(int i=0;i<result.size();i++)
                    {
                        Log.d(TAG, "medResponse: medcn name: " + result.get(i).getMedicinename());
                        arrayList.add(new Medicine(result.get(i).getMedreminderparentid(),result.get(i).getMedreminderchildid(),result.get(i).getMedicinetakendate(),result.get(i).getMedicinename(),result.get(i).getStrength(),result.get(i).getMorningtime(),
                        result.get(i).getMorningmedstatus(),result.get(i).getNoontime(),result.get(i).getNoonmedstatus(),result.get(i).getEveningtime(),
                        result.get(i).getEveninmedstatus(),result.get(i).getNighttime(),result.get(i).getNightmedstatus()));
                    }
                }
              //  ChangeNoonPreferenceDialog noonPreferenceDialog;
              //  ChangeEvngPrefrenseDialog eveningPreferenceDialog;
              //  ChangeNightPrefrenseDialog nightPreferenceDialog;
                mAdapter=new MedicineReminderAdapter (getActivity(),arrayList,position, new MedicineReminderAdapter.OnTimeClickListener() {
                    @Override
                    public void onMorningClick(Medicine item_m, int position) {
                        morningPreferenceDialog = new ChangeMorningPreferenceDialog(getContext(), item_m, new ChangeMorningPreferenceDialog.ChangeReminderPreferenceDialogClickListener() {
                            @Override
                            public void onTake(Medicine updateditems) {
                                morningPreferenceDialog.dismiss();
                                timeapical(updateditems);
                            }

                            @Override
                            public void onSkip(Medicine updateditems) {
                                morningPreferenceDialog.dismiss();
                                timeapical(updateditems);
                            }
                        });
                        morningPreferenceDialog.show();

                    }

                    @Override
                    public void onNoonClick(Medicine item_n, int position) {

                        noonPreferenceDialog = new ChangeNoonPreferenceDialog(getContext(), item_n, new ChangeNoonPreferenceDialog.ChangeNoonPreferenceDialogClickListener() {
                            @Override
                            public void onTake(Medicine updateditems) {
                                noonPreferenceDialog.dismiss();
                                timeapical(updateditems);
                            }

                            @Override
                            public void onSkip(Medicine updateditems) {
                                noonPreferenceDialog.dismiss();
                                timeapical(updateditems);
                            }
                        });
                        noonPreferenceDialog.show();

                    }

                    @Override
                    public void onEveningClick(Medicine item_e, int position) {

                        eveningPreferenceDialog = new ChangeEvngPrefrenseDialog(getContext(), item_e, new ChangeEvngPrefrenseDialog.ChangeEvngPrefrenseDialogClickListener() {
                            @Override
                            public void onTake(Medicine updateditems) {
                                eveningPreferenceDialog.dismiss();
                                timeapical(updateditems);
                            }

                            @Override
                            public void onSkip(Medicine updateditems) {
                                eveningPreferenceDialog.dismiss();
                                timeapical(updateditems);
                            }
                        });
                        eveningPreferenceDialog.show();

                    }

                    @Override
                    public void onNightClick(Medicine item_ngt, int position) {

                        nightPreferenceDialog = new ChangeNightPrefrenseDialog(getContext(), item_ngt, new ChangeNightPrefrenseDialog.ChangeNightPrefrenseDialogClickListener() {
                            @Override
                            public void onTake(Medicine updateditems) {
                                nightPreferenceDialog.dismiss();
                                timeapical(updateditems);
                            }

                            @Override
                            public void onSkip(Medicine updateditems) {
                                nightPreferenceDialog.dismiss();
                                timeapical(updateditems);
                            }
                        });
                        nightPreferenceDialog.show();
                    }
                });
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(Call<MED_REMAINDER_RESPONSE> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG,"onFailure: Somethings went wrong"+t.getMessage());
                Toast.makeText(getActivity(),"Somethings went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void timeapical(final Medicine updateditems) {
        progressDialog = ProgressDialog.show(context, "Loading", "please wait", true, false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface reditapi = retrofit.create(ApiInterface.class);
        Log.d("Dialog", "timecall: parentid " +updateditems.getMedreminderparentid());
        Log.d("Dialog", "timecall: childid" +updateditems.getMedreminderchildid());
        Log.d("Dialog", "timecall: date " +updateditems.getDate());
        Log.d("Dialog", "timecall: childid" +updateditems.getMorningmedstatus());
        Log.d("Dialog", "timecall: date " +updateditems.getNoonmedstatus());
        Log.d("Dialog", "timecall: date " +updateditems.getEveninmedstatus());
        Log.d("Dialog", "timecall: date " +updateditems.getNightmedstatus());

        POST_TIME_UPDATE_CLASS postTimeUpdateClass = new POST_TIME_UPDATE_CLASS(updateditems.getMedreminderparentid(),updateditems.getMedreminderchildid(),updateditems.getDate(),updateditems.getMorningmedstatus(),updateditems.getNoonmedstatus(),
                updateditems.getEveninmedstatus(),updateditems.getNightmedstatus());
        Call<VACCINE_UPDATE_RESPONSE> call = reditapi.get_med_update(postTimeUpdateClass);
        progressDialog.dismiss();
        call.enqueue(new Callback<VACCINE_UPDATE_RESPONSE>() {
            @Override
            public void onResponse(Call<VACCINE_UPDATE_RESPONSE> call, Response<VACCINE_UPDATE_RESPONSE> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    Toast.makeText(getActivity(),"Successfully Updated!",Toast.LENGTH_SHORT).show();

                    Log.d("Dialog", "remainderResponse: code: " +response.body());
                    Log.d("Dialog", "remainderReseponse: code: " +response.body().getCode());
                    Log.d("Dialog", "remainderResponse: result: " +response.body().getResults());
                    int uid = (int) Prefs.getLong("spinner_id",0);
                    Log.d(TAG,"Shared_profile_spinnerid"+uid);
                    Log.d(TAG, "inspiner date" + FINAL_DATE);
                    Get_MED_Data(uid,updateditems.getDate());
                }
            }

            @Override
            public void onFailure(Call<VACCINE_UPDATE_RESPONSE> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),"Somethings went wrong",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void clearData() {
        mAdapter = new MedicineReminderAdapter(getActivity(),arrayList,position, new MedicineReminderAdapter.OnTimeClickListener() {
            @Override
            public void onMorningClick(Medicine item_m, int position) {

            }

            @Override
            public void onNoonClick(Medicine item_n, int position) {

            }

            @Override
            public void onEveningClick(Medicine item_e, int position) {

            }

            @Override
            public void onNightClick(Medicine item_ngt, int position) {

            }
        });
        arrayList.clear(); //clear list
        //mAdapter.notifyDataSetChanged(); //let your adapter know about the changes and reload view.

    }
}
