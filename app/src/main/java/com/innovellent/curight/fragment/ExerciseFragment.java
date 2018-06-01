package com.innovellent.curight.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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


import com.innovellent.curight.R;
import com.innovellent.curight.activities.Exercise.Add_CalBurned_Exersize;
import com.innovellent.curight.adapter.BicyclingAdapter;
import com.innovellent.curight.adapter.CustomSpinnerAdapter2;
import com.innovellent.curight.adapter.FrisbeeAdapter;
import com.innovellent.curight.adapter.GolfAdapter;
import com.innovellent.curight.adapter.ProfileSpinnerAdapter;
import com.innovellent.curight.adapter.RacquetballAdapter;
import com.innovellent.curight.adapter.RowingAdapter;
import com.innovellent.curight.adapter.RunningAdapter;
import com.innovellent.curight.adapter.SoccerAdapter;
import com.innovellent.curight.adapter.SoftballAdapter;
import com.innovellent.curight.adapter.SwimmingAdapter;
import com.innovellent.curight.adapter.TennisAdapter;
import com.innovellent.curight.adapter.TrackAdapter;
import com.innovellent.curight.adapter.TrailBikingAdapter;
import com.innovellent.curight.adapter.VolleyballAdapter;
import com.innovellent.curight.adapter.WalkingAdapter;
import com.innovellent.curight.adapter.WeightliftingAdapter;
import com.innovellent.curight.adapter.WrestlingAdapter;
import com.innovellent.curight.adapter.YogaAdapter;
import com.innovellent.curight.api.ApiClient;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.Bicycling;
import com.innovellent.curight.model.Exercise;
import com.innovellent.curight.model.FamilyProfile;
import com.innovellent.curight.model.Frisbee;
import com.innovellent.curight.model.Golf;
import com.innovellent.curight.model.Racquetball;
import com.innovellent.curight.model.Rowing;
import com.innovellent.curight.model.Running;
import com.innovellent.curight.model.ServerResponse;
import com.innovellent.curight.model.ServerResponseExercise;
import com.innovellent.curight.model.Soccer;
import com.innovellent.curight.model.Softball;
import com.innovellent.curight.model.Swimming;
import com.innovellent.curight.model.Tennis;
import com.innovellent.curight.model.TrailBiking;
import com.innovellent.curight.model.UserIdStr;
import com.innovellent.curight.model.Volleyball;
import com.innovellent.curight.model.Walking;
import com.innovellent.curight.model.Weightlifting;
import com.innovellent.curight.model.Wrestling;
import com.innovellent.curight.model.Yoga;
import com.innovellent.curight.utility.Config;
import com.innovellent.curight.utility.SharedPrefService;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.innovellent.curight.utility.Constants.ACCESS_TOKEN;
import static com.innovellent.curight.utility.Constants.BICYCLING;
import static com.innovellent.curight.utility.Constants.FRISBEE;
import static com.innovellent.curight.utility.Constants.GOLF;
import static com.innovellent.curight.utility.Constants.RACQUETBALL;
import static com.innovellent.curight.utility.Constants.ROWING;
import static com.innovellent.curight.utility.Constants.RUNNING;
import static com.innovellent.curight.utility.Constants.SOCCER;
import static com.innovellent.curight.utility.Constants.SOFTBALL;
import static com.innovellent.curight.utility.Constants.SWIMMING;
import static com.innovellent.curight.utility.Constants.TENNIS;
import static com.innovellent.curight.utility.Constants.TITLE;
import static com.innovellent.curight.utility.Constants.TRAILBIKING;
import static com.innovellent.curight.utility.Constants.VOLLEYBALL;
import static com.innovellent.curight.utility.Constants.WALKING;
import static com.innovellent.curight.utility.Constants.WEIGHTLIFTING;
import static com.innovellent.curight.utility.Constants.WRESTLING;
import static com.innovellent.curight.utility.Constants.YOGA;


public class ExerciseFragment extends Fragment  implements View.OnClickListener {
    final Calendar calendar = Calendar.getInstance();
    RecyclerView recycler_view_walking,recycler_view_running,recycler_view_bycycling,recycler_view_swimming,recycler_view_golf,
    recycler_view_frisbee,recycler_view_racquetball,recycler_view_rowing,recycler_view_soccer,recycler_view_softball,recycler_view_tennis,
    recycler_view_trailbiking,recycler_view_volleyball,recycler_view_weightlifting,recycler_view_wrestling,recycler_view_yoga;
    LinearLayout tvWorkout_walking,tvWorkout_running,tvWorkout_bycycling,tvWorkout_swimming,tvWorkout_golf,tvWorkout_frisbee,tvWorkout_Racquetball,
    tvWorkout_rowing, tvWorkout_soccer,tvWorkout_softball,tvWorkout_tennis,tvWorkout_trailbkng,tvWorkout_volleyball,tvWorkout_weightlifting,tvWorkout_wrestling,tvWorkout_yoga;
    ArrayList<String> arrayList=new ArrayList<String>();
    TrackAdapter mAdapter;
    RelativeLayout rl_location;
    ImageView ivAddWalking,ivAddRunning,ivAddBicycling,ivAddSwimming,ivAddgolf,ivAddfrisbee,ivAddracquetball,ivAddrowing,ivAddsoccer,ivAddsoftball,ivAddtennis,ivAddtrailbking,ivAddvolleyball,ivAddweightlifting,ivAddwrestling,ivAddyoga;
    Spinner spQuestion1;
    ArrayList<Walking> arraywalkingList=new ArrayList<>();
    ArrayList<Bicycling> arraybicycleList=new ArrayList<>();
    ArrayList<Swimming> arrayswimList=new ArrayList<>();
    ArrayList<Running> arrayrunList=new ArrayList<>();
    SwimmingAdapter swimmingAdapter;
    WalkingAdapter walkingAdapter;
    RunningAdapter runningAdapter;
    GolfAdapter golfAdapter;
    FrisbeeAdapter frisbeeAdapter;
    RacquetballAdapter racquetballAdapter;
    RowingAdapter rowingAdapter;
    SoccerAdapter soccerAdapter;
    SoftballAdapter softballAdapter;
    TennisAdapter tennisAdapter;
    TrailBikingAdapter trailBikingAdapter;
    VolleyballAdapter volleyballAdapter;
    WeightliftingAdapter weightliftingAdapter;
    WrestlingAdapter wrestlingAdapter;
    YogaAdapter yogaAdapter;
    RelativeLayout rlDate;
    BicyclingAdapter bicyclingAdapter;
    String[]spinner1={"John","Jobby","Suresh","Mahesh"};
    TextView tvDate,tvTitle;
    TextView tvBurned;
    ImageView ivback,ivback1;
    String finaldate;
    private String accessToken;
    private SharedPrefService sharedPrefService;
    private ProgressDialog progressDialog;
    private int progressBarCounter = 2;
    private List<FamilyProfile> familyProfiles;
    private int mYear, mMonth, mDay;
    private int  mHour, mMinute,mhour1,mhour2,mhour3,minute1,minute2,minute3,mSeconds,seconds1,seconds2,seconds3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_exercise1,container,false);
        init(rootView);
        iniClick();
        progressDialog = ProgressDialog.show(getContext(), "Loading", "please wait", true, false);
        progressDialog.show();

        sharedPrefService = SharedPrefService.getInstance();
        accessToken = sharedPrefService.getString(ACCESS_TOKEN);
        int uid = (int) Prefs.getLong("user_id",0);
        String month,day;
        final Calendar c = Calendar.getInstance();
        int monthnumbr = c.get(Calendar.MONTH)+1;
        int daynumber = c.get(Calendar.DATE);

        if (monthnumbr >= 1 && monthnumbr <= 9) {
            month = "0" + monthnumbr;
        } else {
            month = monthnumbr + "";
        }
        if (daynumber >= 1 && daynumber <= 9) {
            day = "0" + daynumber;
        } else {
            day = daynumber + "";
        }

        finaldate = c.get(Calendar.YEAR) +"-"+month+"-" +day;
        tvDate.setText(finaldate);
        getFamilyProfiles(uid);


//        CustomSpinnerAdapter2 customSpinnerAdapter3=new CustomSpinnerAdapter2(getActivity(),spinner1);
//        spQuestion1.setAdapter(customSpinnerAdapter3);
//        spQuestion1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
        return rootView;
    }

    private void getFamilyProfiles(long userId) {

        ApiInterface client = ApiClient.getClient();
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("userid", userId);
            paramObject.put("familyorindividual", "family");

            Call<ServerResponse<List<FamilyProfile>>> call = client.getFamilyProfiles(accessToken, paramObject.toString());
            call.enqueue(new Callback<ServerResponse<List<FamilyProfile>>>() {
                @Override
                public void onResponse(Call<ServerResponse<List<FamilyProfile>>> call, Response<ServerResponse<List<FamilyProfile>>> response) {
                    if (getActivity() != null) {
                        closeProgressDialog();
                        if (response.isSuccessful()) {
                            ServerResponse<List<FamilyProfile>> serverResponse = response.body();
                            familyProfiles = serverResponse.getResults();
                            ProfileSpinnerAdapter spinnerAdapter = new ProfileSpinnerAdapter(getActivity(), familyProfiles);
                            spQuestion1.setAdapter(spinnerAdapter);
                            spQuestion1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                    Prefs.putLong("spinner_id",Long.parseLong(familyProfiles.get(i).getUserId()));
                                    int uid = (int) Prefs.getLong("spinner_id",0);
                                    String date =tvDate.getText().toString();
                                    getData(uid,date);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse<List<FamilyProfile>>> call, Throwable t) {
                    closeProgressDialog();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            closeProgressDialog();
        }
    }
    private void closeProgressDialog() {
        if (--progressBarCounter < 1) progressDialog.dismiss();
    }

    public void init(View rootview){
        ivAddWalking = (ImageView)rootview.findViewById(R.id.ivAddWalking);
        ivAddRunning=(ImageView)rootview.findViewById(R.id.ivAddRunning);
        ivAddBicycling=(ImageView)rootview.findViewById(R.id.ivAddBicycling);
        ivAddSwimming=(ImageView)rootview.findViewById(R.id.ivAddSwimming);
        ivAddgolf = (ImageView)rootview.findViewById(R.id.ivAddgolf);
        ivAddfrisbee = (ImageView)rootview.findViewById(R.id.ivAddfrisbee);
        ivAddracquetball = (ImageView)rootview.findViewById(R.id.ivAddracquetball);
        ivAddrowing = (ImageView)rootview.findViewById(R.id.ivAddrowing);
        ivAddsoccer = (ImageView)rootview.findViewById(R.id.ivAddsoccer);
        ivAddsoftball = (ImageView)rootview.findViewById(R.id.ivAddsoftball);
        ivAddtennis = (ImageView)rootview.findViewById(R.id.ivAddtennis);
        ivAddtrailbking = (ImageView)rootview.findViewById(R.id.ivAddtrailbking);
        ivAddvolleyball = (ImageView)rootview.findViewById(R.id.ivAddvolleyball);
        ivAddweightlifting = (ImageView)rootview.findViewById(R.id.ivAddweightlifting);
        ivAddwrestling = (ImageView)rootview.findViewById(R.id.ivAddwrestling);
        ivAddyoga = (ImageView)rootview.findViewById(R.id.ivAddyoga);

        ivback=(ImageView)getActivity().findViewById(R.id.ivback);
        tvTitle=(TextView)getActivity().findViewById(R.id.tvTitle);
        tvBurned=(TextView)rootview.findViewById(R.id.tvBurned);
        tvDate = (TextView) rootview.findViewById(R.id.tv_date);
        ivback1=(ImageView)getActivity().findViewById(R.id.ivback1);
        rlDate=(RelativeLayout)rootview.findViewById(R.id.date_layout);
        spQuestion1= (Spinner)rootview.findViewById(R.id.spQuestion1);
        rl_location = (RelativeLayout) getActivity().findViewById(R.id.rl_location);

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
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("Exercise");
        rl_location.setVisibility(View.GONE);
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

        ivAddgolf.setOnClickListener(this);
        ivAddfrisbee.setOnClickListener(this);
        ivAddracquetball.setOnClickListener(this);
        ivAddrowing.setOnClickListener(this);
        ivAddsoccer.setOnClickListener(this);
        ivAddsoftball.setOnClickListener(this);
        ivAddtennis.setOnClickListener(this);
        ivAddtrailbking.setOnClickListener(this);
        ivAddvolleyball.setOnClickListener(this);
        ivAddweightlifting.setOnClickListener(this);
        ivAddwrestling.setOnClickListener(this);
        ivAddyoga.setOnClickListener(this);

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
    public void selectDate() {
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
                        } else {
                            monthYear = month + "";
                        }

                        String date = year + "-" + monthYear + "-" + day;
                        tvDate.setText(year + "-" + monthYear + "-" + day);
                        int s_uid = (int) Prefs.getLong("spinner_id",0);
                        if(s_uid==0)
                        {
                            int uid = (int) Prefs.getLong("user_id",0);
                            getData(uid,date);
                        }else {
                            getData(s_uid,date);
                        }


                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
    public void getData(long userid,String selecteddate){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        long user_id = sharedPreferences.getLong("user_id",0L);

        Log.e("EXERCISE","User Id ::  "+user_id);

        UserIdStr userId = new UserIdStr();
        userId.setUserid(userid+"");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(calendar.getTime());
        // formattedDate have current date/time
 //       Toast.makeText(getActivity(), formattedDate, Toast.LENGTH_SHORT).show();
        userId.setDate(selecteddate);

        String access_token = sharedPreferences.getString("access_token","");

        Call<ServerResponseExercise> call = apiInterface.getExercise(access_token,userId);

        Log.e("EXERCISE","Request URL ::  "+call.request().url());

        call.enqueue(new Callback<ServerResponseExercise>() {
            @Override
            public void onResponse(Call<ServerResponseExercise> call, Response<ServerResponseExercise> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Log.e("Exercise","Cals burnt ::  "+response.body().getResults().getTotalcaloriesburnt());

                    Exercise exercise = response.body().getResults();

                    tvBurned.setText(exercise.getTotalcaloriesburnt()+"");

                    ArrayList<Walking> walkingArrayList = exercise.getWalking();
                    ArrayList<Running> runningArrayList = exercise.getRunning();
                    ArrayList<Bicycling> bicyclingArrayList = exercise.getBicycling();
                    ArrayList<Swimming> swimmingArrayList = exercise.getSwimming();
                    ArrayList<Golf> golfArrayList = exercise.getGolf();
                    ArrayList<Frisbee> frisbeesArrayList = exercise.getFrisbee();
                    ArrayList<Racquetball> racquetballArrayList = exercise.getRacquetball();
                    ArrayList<Rowing> rowingsArrayList = exercise.getRowing();
                    ArrayList<Soccer> soccersArrayList = exercise.getSoccer();
                    ArrayList<Softball> softballsArrayList = exercise.getSoftball();
                    ArrayList<Tennis> tennisArrayList = exercise.getTennis();
                    ArrayList<TrailBiking> trailBikingsArrayList = exercise.getTrailBiking();
                    ArrayList<Volleyball> volleyballsArrayList = exercise.getVolleyball();
                    ArrayList<Weightlifting> weightliftingsArrayList = exercise.getWeightlifting();
                    ArrayList<Wrestling> wrestlingsArrayList = exercise.getWrestling();
                    ArrayList<Yoga> yogaArrayList = exercise.getYoga();


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

                    golfAdapter=new GolfAdapter(getActivity(),R.layout.list_row_exercise,golfArrayList);
                    recycler_view_golf.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycler_view_golf.setAdapter(golfAdapter);

                    frisbeeAdapter=new FrisbeeAdapter(getActivity(),R.layout.list_row_exercise,frisbeesArrayList);
                    recycler_view_frisbee.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycler_view_frisbee.setAdapter(frisbeeAdapter);

                    racquetballAdapter=new RacquetballAdapter(getActivity(),R.layout.list_row_exercise,racquetballArrayList);
                    recycler_view_racquetball.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycler_view_racquetball.setAdapter(racquetballAdapter);

                    rowingAdapter=new RowingAdapter(getActivity(),R.layout.list_row_exercise,rowingsArrayList);
                    recycler_view_rowing.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycler_view_rowing.setAdapter(rowingAdapter);

                    soccerAdapter=new SoccerAdapter(getActivity(),R.layout.list_row_exercise,soccersArrayList);
                    recycler_view_soccer.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycler_view_soccer.setAdapter(soccerAdapter);

                    softballAdapter=new SoftballAdapter(getActivity(),R.layout.list_row_exercise,softballsArrayList);
                    recycler_view_softball.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycler_view_softball.setAdapter(softballAdapter);

                    tennisAdapter=new TennisAdapter(getActivity(),R.layout.list_row_exercise,tennisArrayList);
                    recycler_view_tennis.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycler_view_tennis.setAdapter(tennisAdapter);

                    trailBikingAdapter=new TrailBikingAdapter(getActivity(),R.layout.list_row_exercise,trailBikingsArrayList);
                    recycler_view_trailbiking.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycler_view_trailbiking.setAdapter(trailBikingAdapter);

                    volleyballAdapter=new VolleyballAdapter(getActivity(),R.layout.list_row_exercise,volleyballsArrayList);
                    recycler_view_volleyball.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycler_view_volleyball.setAdapter(volleyballAdapter);

                    weightliftingAdapter=new WeightliftingAdapter(getActivity(),R.layout.list_row_exercise,weightliftingsArrayList);
                    recycler_view_weightlifting.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycler_view_weightlifting.setAdapter(weightliftingAdapter);

                    wrestlingAdapter=new WrestlingAdapter(getActivity(),R.layout.list_row_exercise,wrestlingsArrayList);
                    recycler_view_wrestling.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycler_view_wrestling.setAdapter(wrestlingAdapter);

                    yogaAdapter=new YogaAdapter(getActivity(),R.layout.list_row_exercise,yogaArrayList);
                    recycler_view_yoga.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycler_view_yoga.setAdapter(yogaAdapter);


                }
            }

            @Override
            public void onFailure(Call<ServerResponseExercise> call, Throwable t) {
                progressDialog.dismiss();
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

    @Override
    public void onResume() {
        super.onResume();
      //  Log.d(TAG,"final date::"+finaldate);
        int uid = (int) Prefs.getLong("user_id",0);
        getData(uid,finaldate);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.date_layout:
                selectDate();
                break;
            case R.id.ivAddWalking:
                Intent ine=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                ine.putExtra(TITLE, WALKING);
                startActivity(ine);
                break;
            case R.id.ivAddRunning:
                Intent inr =new Intent(getActivity(), Add_CalBurned_Exersize.class);
                inr.putExtra(TITLE, RUNNING);
                startActivity(inr);
                break;
            case R.id.ivAddBicycling:
                Intent inb1=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                inb1.putExtra(TITLE, BICYCLING);
                startActivity(inb1);
                break;
            case R.id.ivAddSwimming:
                Intent ins1=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                ins1.putExtra(TITLE, SWIMMING);
                startActivity(ins1);
                break;
            case R.id.tvWorkout_walking:
                Intent inw =new Intent(getActivity(), Add_CalBurned_Exersize.class);
                inw.putExtra(TITLE, WALKING);
                startActivity(inw);

                break;
            case R.id.tvWorkout_running:
                Intent inr1=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                inr1.putExtra(TITLE, RUNNING);
                startActivity(inr1);
                break;
            case R.id.tvWorkout_bycycling:
                Intent inb=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                inb.putExtra(TITLE, BICYCLING);
                startActivity(inb);
                break;
            case R.id.tvWorkout_swimming:
                Intent ins=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                ins.putExtra(TITLE, SWIMMING);
                startActivity(ins);
                break;
            case R.id.tvWorkout_golf:
                Intent ing=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                ing.putExtra(TITLE, GOLF);
                startActivity(ing);
                break;
            case R.id.ivAddgolf:
                Intent ing1=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                ing1.putExtra(TITLE, GOLF);
                startActivity(ing1);
                break;
            case R.id.ivAddfrisbee:
                Intent infsb1=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                infsb1.putExtra(TITLE, FRISBEE);
                startActivity(infsb1);
                break;
            case R.id.tvWorkout_frisbee:
                Intent infsb=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                infsb.putExtra(TITLE, FRISBEE);
                startActivity(infsb);
                break;
            case R.id.ivAddracquetball:
                Intent inre1=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                inre1.putExtra(TITLE, RACQUETBALL);
                startActivity(inre1);
                break;
            case R.id.tvWorkout_Racquetball:
                Intent inre=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                inre.putExtra(TITLE, RACQUETBALL);
                startActivity(inre);
                break;
            case R.id.tvWorkout_rowing:
                Intent inro=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                inro.putExtra(TITLE, ROWING);
                startActivity(inro);
                break;
            case R.id.ivAddrowing:
                Intent inro1=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                inro1.putExtra(TITLE, ROWING);
                startActivity(inro1);
                break;
            case R.id.tvWorkout_soccer:
                Intent inso=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                inso.putExtra(TITLE, SOCCER);
                startActivity(inso);
                break;
            case R.id.ivAddsoccer:
                Intent inso1=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                inso1.putExtra(TITLE, SOCCER);
                startActivity(inso1);
                break;
            case R.id.tvWorkout_softball:
                Intent insft=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                insft.putExtra(TITLE, SOFTBALL);
                startActivity(insft);
                break;
            case R.id.ivAddsoftball:
                Intent insft1=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                insft1.putExtra(TITLE, SOFTBALL);
                startActivity(insft1);
                break;
            case R.id.tvWorkout_tennis:
                Intent tns=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                tns.putExtra(TITLE, TENNIS);
                startActivity(tns);
                break;
            case R.id.ivAddtennis:
                Intent tns1=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                tns1.putExtra(TITLE, TENNIS);
                startActivity(tns1);
                break;
            case R.id.tvWorkout_trailbkng:
                Intent incl=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                incl.putExtra(TITLE, TRAILBIKING);
                startActivity(incl);
                break;
            case R.id.ivAddtrailbking:
                Intent incl1=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                incl1.putExtra(TITLE, TRAILBIKING);
                startActivity(incl1);
                break;
            case R.id.tvWorkout_volleyball:
                Intent invl=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                invl.putExtra(TITLE, VOLLEYBALL);
                startActivity(invl);
                break;
            case R.id.ivAddvolleyball:
                Intent invl1=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                invl1.putExtra(TITLE, VOLLEYBALL);
                startActivity(invl1);
                break;
            case R.id.tvWorkout_weightlifting:
                Intent inwl=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                inwl.putExtra(TITLE, WEIGHTLIFTING);
                startActivity(inwl);
                break;
            case R.id.ivAddweightlifting:
                Intent inwl1=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                inwl1.putExtra(TITLE, WEIGHTLIFTING);
                startActivity(inwl1);
                break;
            case R.id.ivAddwrestling:
                Intent inwrs = new Intent(getActivity(), Add_CalBurned_Exersize.class);
                inwrs.putExtra(TITLE, WRESTLING);
                startActivity(inwrs);
                break;
            case R.id.tvWorkout_wrestling:
                Intent inwrs1 = new Intent(getActivity(), Add_CalBurned_Exersize.class);
                inwrs1.putExtra(TITLE, WRESTLING);
                startActivity(inwrs1);
                break;
            case R.id.tvWorkout_yoga:
                Intent inyg=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                inyg.putExtra(TITLE, YOGA);
                startActivity(inyg);
                break;
            case R.id.ivAddyoga:
                Intent inyg1=new Intent(getActivity(), Add_CalBurned_Exersize.class);
                inyg1.putExtra(TITLE, YOGA);
                startActivity(inyg1);
                break;
        }
    }

}
