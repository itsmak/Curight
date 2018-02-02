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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.innovellent.curight.R;
import com.innovellent.curight.activities.Exercise.BycyclingActivity;
import com.innovellent.curight.activities.Exercise.FrisbeeActivity;
import com.innovellent.curight.activities.Exercise.GolfActivity;
import com.innovellent.curight.activities.Exercise.RacquetballActivity;
import com.innovellent.curight.activities.Exercise.RowingActivity;
import com.innovellent.curight.activities.Exercise.RunningActivity;
import com.innovellent.curight.activities.Exercise.SoccerActivity;
import com.innovellent.curight.activities.Exercise.SoftballActivity;
import com.innovellent.curight.activities.Exercise.SwimmingActivity;
import com.innovellent.curight.activities.Exercise.TennisActivity;
import com.innovellent.curight.activities.Exercise.TrailBkngActivity;
import com.innovellent.curight.activities.Exercise.VolleyBallActivity;
import com.innovellent.curight.activities.Exercise.WalkingActivity;
import com.innovellent.curight.activities.Exercise.WeightliftingActivity;
import com.innovellent.curight.activities.Exercise.WreslingActivity;
import com.innovellent.curight.activities.Exercise.YogaActivity;
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
    final Calendar calendar = Calendar.getInstance();
    RecyclerView recycler_view_walking,recycler_view_running,recycler_view_bycycling,recycler_view_swimming,recycler_view_golf,
    recycler_view_frisbee,recycler_view_racquetball,recycler_view_rowing,recycler_view_soccer,recycler_view_softball,recycler_view_tennis,
    recycler_view_trailbiking,recycler_view_volleyball,recycler_view_weightlifting,recycler_view_wrestling,recycler_view_yoga;
    LinearLayout tvWorkout_walking,tvWorkout_running,tvWorkout_bycycling,tvWorkout_swimming,tvWorkout_golf,tvWorkout_frisbee,tvWorkout_Racquetball,
    tvWorkout_rowing, tvWorkout_soccer,tvWorkout_softball,tvWorkout_tennis,tvWorkout_trailbkng,tvWorkout_volleyball,tvWorkout_weightlifting,tvWorkout_wrestling,tvWorkout_yoga;
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
        spQuestion1= (Spinner)rootview.findViewById(R.id.spQuestion1);

        tvWorkout_walking = (LinearLayout) rootview.findViewById(R.id.tvWorkout_walking);
        tvWorkout_running  = (LinearLayout) rootview.findViewById(R.id.tvWorkout_running);
        tvWorkout_bycycling = (LinearLayout) rootview.findViewById(R.id.tvWorkout_bycycling);
        tvWorkout_swimming = (LinearLayout) rootview.findViewById(R.id.tvWorkout_swimming);
        tvWorkout_golf = (LinearLayout) rootview.findViewById(R.id.tvWorkout_golf);
        tvWorkout_frisbee = (LinearLayout) rootview.findViewById(R.id.tvWorkout_frisbee);
        tvWorkout_Racquetball = (LinearLayout) rootview.findViewById(R.id.tvWorkout_Racquetball);
        tvWorkout_rowing = (LinearLayout) rootview.findViewById(R.id.tvWorkout_rowing);
        tvWorkout_soccer = (LinearLayout) rootview.findViewById(R.id.tvWorkout_soccer);
        tvWorkout_softball = (LinearLayout) rootview.findViewById(R.id.tvWorkout_softball);
        tvWorkout_tennis = (LinearLayout) rootview.findViewById(R.id.tvWorkout_tennis);
        tvWorkout_trailbkng = (LinearLayout) rootview.findViewById(R.id.tvWorkout_trailbkng);
        tvWorkout_volleyball = (LinearLayout) rootview.findViewById(R.id.tvWorkout_volleyball);
        tvWorkout_weightlifting = (LinearLayout) rootview.findViewById(R.id.tvWorkout_weightlifting);
        tvWorkout_wrestling = (LinearLayout) rootview.findViewById(R.id.tvWorkout_wrestling);
        tvWorkout_yoga = (LinearLayout) rootview.findViewById(R.id.tvWorkout_yoga);

        recycler_view_walking=(RecyclerView)rootview.findViewById(R.id.recycler_view_walking);
        recycler_view_running=(RecyclerView)rootview.findViewById(R.id.recycler_view_running);
        recycler_view_bycycling=(RecyclerView)rootview.findViewById(R.id.recycler_view_bycycling);
        recycler_view_swimming=(RecyclerView)rootview.findViewById(R.id.recycler_view_swimming);
        recycler_view_golf=(RecyclerView)rootview.findViewById(R.id.recycler_view_golf);
        recycler_view_frisbee=(RecyclerView)rootview.findViewById(R.id.recycler_view_frisbee);
        recycler_view_racquetball=(RecyclerView)rootview.findViewById(R.id.recycler_view_racquetball);
        recycler_view_rowing=(RecyclerView)rootview.findViewById(R.id.recycler_view_rowing);
        recycler_view_soccer=(RecyclerView)rootview.findViewById(R.id.recycler_view_soccer);
        recycler_view_softball=(RecyclerView)rootview.findViewById(R.id.recycler_view_softball);
        recycler_view_tennis =(RecyclerView)rootview.findViewById(R.id.recycler_view_tennis);
        recycler_view_trailbiking=(RecyclerView)rootview.findViewById(R.id.recycler_view_trailbiking);
        recycler_view_volleyball=(RecyclerView)rootview.findViewById(R.id.recycler_view_volleyball);
        recycler_view_weightlifting=(RecyclerView)rootview.findViewById(R.id.recycler_view_weightlifting);
        recycler_view_wrestling=(RecyclerView)rootview.findViewById(R.id.recycler_view_wrestling);
        recycler_view_yoga=(RecyclerView)rootview.findViewById(R.id.recycler_view_yoga);

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

        tvWorkout_walking.setOnClickListener(this);
        tvWorkout_running.setOnClickListener(this);
        tvWorkout_bycycling.setOnClickListener(this);
        tvWorkout_swimming.setOnClickListener(this);
        tvWorkout_golf.setOnClickListener(this);
        tvWorkout_frisbee.setOnClickListener(this);
        tvWorkout_Racquetball.setOnClickListener(this);
        tvWorkout_rowing.setOnClickListener(this);
        tvWorkout_soccer.setOnClickListener(this);
        tvWorkout_softball.setOnClickListener(this);
        tvWorkout_tennis.setOnClickListener(this);
        tvWorkout_trailbkng.setOnClickListener(this);
        tvWorkout_volleyball.setOnClickListener(this);
        tvWorkout_weightlifting.setOnClickListener(this);
        tvWorkout_wrestling.setOnClickListener(this);
        tvWorkout_yoga.setOnClickListener(this);

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
 //       Toast.makeText(getActivity(), formattedDate, Toast.LENGTH_SHORT).show();
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
                    recycler_view_walking.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycler_view_walking.setAdapter(walkingAdapter);

                    runningAdapter=new RunningAdapter(getActivity(),R.layout.list_row_exercise,runningArrayList);
                    recycler_view_running.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycler_view_running.setAdapter(runningAdapter);

                    bicyclingAdapter=new BicyclingAdapter(getActivity(),R.layout.list_row_exercise,bicyclingArrayList);
                    recycler_view_bycycling.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycler_view_bycycling.setAdapter(bicyclingAdapter);

                    swimmingAdapter=new SwimmingAdapter(getActivity(),R.layout.list_row_exercise,swimmingArrayList);
                    recycler_view_swimming.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycler_view_swimming.setAdapter(swimmingAdapter);
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
//                Intent i1=new Intent(getActivity(), WalkingActivity.class);
//                startActivity(i1);
                break;
            case R.id.ivAddRunning:
//                Intent i2=new Intent(getActivity(), WalkingActivity.class);
//                startActivity(i2);
                break;
            case R.id.ivAddBicycling:
//                Intent i3=new Intent(getActivity(), WalkingActivity.class);
//                startActivity(i3);
                break;
            case R.id.ivAddSwimming:
//                Intent i=new Intent(getActivity(), WalkingActivity.class);
//                startActivity(i);
                break;
            case R.id.tvWorkout_walking:
                Intent in=new Intent(getActivity(), WalkingActivity.class);
                startActivity(in);

                break;
            case R.id.tvWorkout_running:
                Intent inr=new Intent(getActivity(), RunningActivity.class);
                startActivity(inr);
                break;
            case R.id.tvWorkout_bycycling:
                Intent inb=new Intent(getActivity(), BycyclingActivity.class);
                startActivity(inb);
                break;
            case R.id.tvWorkout_swimming:
                Intent ins=new Intent(getActivity(), SwimmingActivity.class);
                startActivity(ins);
                break;
            case R.id.tvWorkout_golf:
                Intent ing=new Intent(getActivity(), GolfActivity.class);
                startActivity(ing);
                break;
            case R.id.tvWorkout_frisbee:
                Intent inf=new Intent(getActivity(), FrisbeeActivity.class);
                startActivity(inf);
                break;
            case R.id.tvWorkout_Racquetball:
                Intent inrc=new Intent(getActivity(), RacquetballActivity.class);
                startActivity(inrc);
                break;
            case R.id.tvWorkout_rowing:
                Intent inro=new Intent(getActivity(), RowingActivity.class);
                startActivity(inro);
                break;
            case R.id.tvWorkout_soccer:
                Intent scr=new Intent(getActivity(), SoccerActivity.class);
                startActivity(scr);
                break;
            case R.id.tvWorkout_softball:
                Intent sftb=new Intent(getActivity(), SoftballActivity.class);
                startActivity(sftb);
                break;
            case R.id.tvWorkout_tennis:
                Intent tns=new Intent(getActivity(), TennisActivity.class);
                startActivity(tns);
                break;
            case R.id.tvWorkout_trailbkng:
                Intent trlbkg=new Intent(getActivity(), TrailBkngActivity.class);
                startActivity(trlbkg);
                break;
            case R.id.tvWorkout_volleyball:
                Intent vba=new Intent(getActivity(), VolleyBallActivity.class);
                startActivity(vba);
                break;
            case R.id.tvWorkout_weightlifting:
                Intent wgt=new Intent(getActivity(), WeightliftingActivity.class);
                startActivity(wgt);
                break;
            case R.id.tvWorkout_wrestling:
                Intent wrs=new Intent(getActivity(), WreslingActivity.class);
                startActivity(wrs);
                break;
            case R.id.tvWorkout_yoga:
                Intent yga=new Intent(getActivity(), YogaActivity.class);
                startActivity(yga);
                break;


        }
    }

}
