package com.innovellent.curight.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.innovellent.curight.R;
import com.innovellent.curight.activities.WalkingActivity;
import com.innovellent.curight.adapter.BicyclingAdapter;
import com.innovellent.curight.adapter.CustomSpinnerAdapter2;
import com.innovellent.curight.adapter.RunningAdapter;
import com.innovellent.curight.adapter.SwimmingAdapter;
import com.innovellent.curight.adapter.TrackAdapter;
import com.innovellent.curight.adapter.WalkingAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.Bicycling;
import com.innovellent.curight.model.Exercise;
import com.innovellent.curight.model.Running;
import com.innovellent.curight.model.ServerResponseExercise;
import com.innovellent.curight.model.Swimming;
import com.innovellent.curight.model.UserIdStr;
import com.innovellent.curight.model.Walking;
import com.innovellent.curight.utility.Config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ExerciseFragment extends Fragment  implements View.OnClickListener {
    RecyclerView recycler_view,recycler_view1,recycler_view2,recycler_view3;
    ArrayList<String> arrayList=new ArrayList<String>();
    TrackAdapter mAdapter;
    ImageView ivAddWalking,ivAddRunning,ivAddBicycling,ivAddSwimming;
    Spinner spQuestion1;
    ArrayList<Walking> arraywalkingList=new ArrayList<>();
    ArrayList<Bicycling> arraybicycleList=new ArrayList<>();
    ArrayList<Swimming> arrayswimList=new ArrayList<>();
    ArrayList<Running> arrayrunList=new ArrayList<>();
    SwimmingAdapter swimmingAdapter;
    WalkingAdapter walkingAdapter;
    RunningAdapter runningAdapter;
    RelativeLayout rlDate;
    BicyclingAdapter bicyclingAdapter;
    String[]spinner1={"John","Jobby","Suresh","Mahesh"};
    TextView tvDate,tvTitle;
    TextView tvBurned;
    ImageView ivback,ivback1;
    private int mYear, mMonth, mDay;
    final Calendar calendar = Calendar.getInstance();
    private int  mHour, mMinute,mhour1,mhour2,mhour3,minute1,minute2,minute3,mSeconds,seconds1,seconds2,seconds3;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_exercise1,container,false);
        init(rootView);
        iniClick();
        getData();
        CustomSpinnerAdapter2 customSpinnerAdapter3=new CustomSpinnerAdapter2(getActivity(),spinner1);
        spQuestion1.setAdapter(customSpinnerAdapter3);
        spQuestion1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return rootView;
    }
    public void init(View rootview){
        ivAddWalking=(ImageView)rootview.findViewById(R.id.ivAddWalking);
        ivAddRunning=(ImageView)rootview.findViewById(R.id.ivAddRunning);
        ivAddBicycling=(ImageView)rootview.findViewById(R.id.ivAddBicycling);
        ivAddSwimming=(ImageView)rootview.findViewById(R.id.ivAddSwimming);
        ivback=(ImageView)getActivity().findViewById(R.id.ivback);
        tvTitle=(TextView)getActivity().findViewById(R.id.tvTitle);
        tvBurned=(TextView)rootview.findViewById(R.id.tvBurned);
        ivback1=(ImageView)getActivity().findViewById(R.id.ivback1);
        rlDate=(RelativeLayout)rootview.findViewById(R.id.date_layout);
        recycler_view=(RecyclerView)rootview.findViewById(R.id.recycler_view);
        recycler_view1=(RecyclerView)rootview.findViewById(R.id.recycler_view1);
        recycler_view2=(RecyclerView)rootview.findViewById(R.id.recycler_view2);
        recycler_view3=(RecyclerView)rootview.findViewById(R.id.recycler_view3);
        spQuestion1= (Spinner)rootview.findViewById(R.id.spQuestion1);
    }
    public void iniClick(){

        //tvTitle.setText("Exercise");
        ivback.setVisibility(View.GONE);
        ivback1.setVisibility(View.VISIBLE);
        ivback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new TrackDataFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rlMainFragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        arrayList.add("BMI");
        arrayList.add("WHR");
        arrayList.add("BF");
        arrayList.add("LIPID");
        arrayList.add("BP");
        arrayList.add("BLOOD SUGAR");
        arrayList.add("HOSPITAL");
        arrayList.add("FEMALE CYCLE TRACK");
        arrayList.add("THYROID");
        arrayList.add("BLOOD CELL");
        arrayList.add("BLLOD COUNT");
        arrayList.add("VITAMIN PANEL");
        arrayList.add("WHR");
        /*arraybicycleList.add(new Bicycling("fast","3","2"));
        arraybicycleList.add(new Bicycling("slow","2","4"));
        arraybicycleList.add(new Bicycling("fast","3","2"));
        arraybicycleList.add(new Bicycling("medium","5","1"));
        arrayrunList.add(new Running("slow","8","3"));
        arrayrunList.add(new Running("medium","6","21"));

        arraywalkingList.add(new Walking("slow","7","2"));
        arraywalkingList.add(new Walking("medium","1","6"));
        arraywalkingList.add(new Walking("medium","9","2"));
        arraywalkingList.add(new Walking("medium","9","2"));
        arraywalkingList.add(new Walking("medium","9","2"));
        arrayswimList.add(new Swimming("slow","5","7"));
        arrayswimList.add(new Swimming("medium","6","6"));
        arrayswimList.add(new Swimming("medium","6","6"));
        arrayswimList.add(new Swimming("medium","2","6"));*/
        ivAddWalking.setOnClickListener(this);
        ivAddRunning.setOnClickListener(this);
        ivAddBicycling.setOnClickListener(this);
        ivAddSwimming.setOnClickListener(this);
        rlDate.setOnClickListener(this);
    }

    public void getData(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        long user_id = sharedPreferences.getLong("user_id",0L);

        Log.e("EXERCISE","User Id ::  "+user_id);

        UserIdStr userId = new UserIdStr();
        userId.setUserid(user_id+"");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(calendar.getTime());
        // formattedDate have current date/time
        Toast.makeText(getActivity(), formattedDate, Toast.LENGTH_SHORT).show();
        userId.setDate(formattedDate);

        String access_token = sharedPreferences.getString("access_token","");

        Call<ServerResponseExercise> call = apiInterface.getExercise(access_token,userId);

        Log.e("EXERCISE","Request URL ::  "+call.request().url());

        call.enqueue(new Callback<ServerResponseExercise>() {
            @Override
            public void onResponse(Call<ServerResponseExercise> call, Response<ServerResponseExercise> response) {

                if (response.isSuccessful()) {
                    Log.e("Exercise","Cals burnt ::  "+response.body().getResults().getTotalcaloriesburnt());

                    Exercise exercise = response.body().getResults();

                    tvBurned.setText(exercise.getTotalcaloriesburnt()+"");

                    ArrayList<Walking> walkingArrayList = exercise.getWalking();
                    ArrayList<Running> runningArrayList = exercise.getRunning();
                    ArrayList<Bicycling> bicyclingArrayList = exercise.getBicycling();
                    ArrayList<Swimming> swimmingArrayList = exercise.getSwimming();


                    walkingAdapter=new WalkingAdapter(getActivity(),R.layout.list_row_exercise,walkingArrayList);
                    recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycler_view.setAdapter(walkingAdapter);

                    runningAdapter=new RunningAdapter(getActivity(),R.layout.list_row_exercise,runningArrayList);
                    recycler_view1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycler_view1.setAdapter(runningAdapter);

                    bicyclingAdapter=new BicyclingAdapter(getActivity(),R.layout.list_row_exercise,bicyclingArrayList);
                    recycler_view2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycler_view2.setAdapter(bicyclingAdapter);

                    swimmingAdapter=new SwimmingAdapter(getActivity(),R.layout.list_row_exercise,swimmingArrayList);
                    recycler_view3.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycler_view3.setAdapter(swimmingAdapter);
                }
            }

            @Override
            public void onFailure(Call<ServerResponseExercise> call, Throwable t) {

            }
        });


        /*walkingAdapter=new WalkingAdapter(getActivity(),R.layout.list_row_exercise,arraywalkingList);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(walkingAdapter);

        lunchAdapter=new RunningAdapter(getActivity(),R.layout.list_row_exercise,arrayrunList);
        recycler_view1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycler_view1.setAdapter(lunchAdapter);

        bicyclingAdapter=new BicyclingAdapter(getActivity(),R.layout.list_row_exercise,arraybicycleList);
        recycler_view2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycler_view2.setAdapter(bicyclingAdapter);

        swimmingAdapter=new SwimmingAdapter(getActivity(),R.layout.list_row_exercise,arrayswimList);
        recycler_view3.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycler_view3.setAdapter(swimmingAdapter);*/


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

                        String date = year + "-" + monthYear + "-" + dayOfMonth;
                        tvDate.setText(date);
                    }
                }, mYear, mMonth, mDay);


        datePickerDialog.show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.date_layout:
                selectDate();
                break;
            case R.id.ivAddWalking:
                Intent i1=new Intent(getActivity(), WalkingActivity.class);
                startActivity(i1);
                break;
            case R.id.ivAddRunning:
                Intent i2=new Intent(getActivity(), WalkingActivity.class);
                startActivity(i2);
                break;
            case R.id.ivAddBicycling:
                Intent i3=new Intent(getActivity(), WalkingActivity.class);
                startActivity(i3);
                break;
            case R.id.ivAddSwimming:
                Intent i=new Intent(getActivity(), WalkingActivity.class);
                startActivity(i);
                break;
        }
    }
}
