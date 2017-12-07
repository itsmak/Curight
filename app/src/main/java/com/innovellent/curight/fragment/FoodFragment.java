package com.innovellent.curight.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
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

import com.innovellent.curight.R;
import com.innovellent.curight.activities.AddFoodConsumptionActivity;
import com.innovellent.curight.adapter.BicyclingAdapter;
import com.innovellent.curight.adapter.LunchAdapter;
import com.innovellent.curight.adapter.ProfileSpinnerAdapter;
import com.innovellent.curight.adapter.SwimmingAdapter;
import com.innovellent.curight.adapter.TrackAdapter;
import com.innovellent.curight.adapter.WalkingAdapter;
import com.innovellent.curight.api.ApiClient;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.Bicycling;
import com.innovellent.curight.model.FamilyProfile;
import com.innovellent.curight.model.Food;
import com.innovellent.curight.model.Lunch;
import com.innovellent.curight.model.ServerResponse;
import com.innovellent.curight.model.ServerResponseFood;
import com.innovellent.curight.model.Swimming;
import com.innovellent.curight.model.Walking;
import com.innovellent.curight.utility.SharedPrefService;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.innovellent.curight.utility.Constants.ACCESS_TOKEN;
import static com.innovellent.curight.utility.Constants.BREAKFAST;
import static com.innovellent.curight.utility.Constants.DATE;
import static com.innovellent.curight.utility.Constants.DINNER;
import static com.innovellent.curight.utility.Constants.LUNCH;
import static com.innovellent.curight.utility.Constants.SNACKS;
import static com.innovellent.curight.utility.Constants.TITLE;
import static com.innovellent.curight.utility.Constants.USER_ID;


public class FoodFragment extends Fragment implements View.OnClickListener {
    RecyclerView recycler_view, recycler_view1, recycler_view2, recycler_view3;
    ArrayList<String> arrayList = new ArrayList<String>();
    TrackAdapter mAdapter;
    Spinner spQuestion1;
    RelativeLayout rlDate;
    ArrayList<Walking> arraywalkingList = new ArrayList<>();
    ArrayList<Bicycling> arraybicycleList = new ArrayList<>();
    ArrayList<Swimming> arrayswimList = new ArrayList<>();
    ArrayList<Lunch> breakfasts, lunches, snacks, dinners;
    LunchAdapter breakfastAdapter, lunchAdaoter, snacksAdapter, dinnerAdapter;
    ImageView ivBreakfast, ivLunch, ivSnacks, ivDinner, ivback, ivback1;
    TextView tvDate, tvTitle;
    String[] spinner1 = {"John", "Jobby", "Suresh", "Mahesh"};
    private int mYear, mMonth, mDay;
    private SharedPrefService sharedPrefService;
    private long userId;
    private String accessToken;
    private ProgressDialog progressDialog;
    private int progressBarCounter = 2;
    private List<FamilyProfile> familyProfiles;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_food, container, false);

        progressDialog = ProgressDialog.show(getContext(), "Loading", "please wait", true, false);
        progressDialog.show();

        init(rootView);
        iniClick();

        sharedPrefService = SharedPrefService.getInstance();
        userId = sharedPrefService.getLong(USER_ID);
        accessToken = sharedPrefService.getString(ACCESS_TOKEN);

        getFamilyProfiles(userId);

        return rootView;
    }

    public void init(View rootview) {
        rlDate = (RelativeLayout) rootview.findViewById(R.id.date_layout);
        tvTitle = (TextView) getActivity().findViewById(R.id.tvTitle);
        ivback = (ImageView) getActivity().findViewById(R.id.ivback);
        ivback1 = (ImageView) getActivity().findViewById(R.id.ivback1);
        recycler_view = (RecyclerView) rootview.findViewById(R.id.recycler_view);
        recycler_view1 = (RecyclerView) rootview.findViewById(R.id.recycler_view1);
        recycler_view2 = (RecyclerView) rootview.findViewById(R.id.recycler_view2);
        recycler_view3 = (RecyclerView) rootview.findViewById(R.id.recycler_view3);
        spQuestion1 = (Spinner) rootview.findViewById(R.id.spQuestion1);
        tvDate = (TextView) rootview.findViewById(R.id.tv_date);
        ivBreakfast = (ImageView) rootview.findViewById(R.id.ivBreakfast);
        ivLunch = (ImageView) rootview.findViewById(R.id.ivLunch);
        ivSnacks = (ImageView) rootview.findViewById(R.id.ivSnacks);
        ivDinner = (ImageView) rootview.findViewById(R.id.ivDinner);
    }

    public void iniClick() {

        tvTitle.setText("Food");
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
        ivBreakfast.setOnClickListener(this);
        ivLunch.setOnClickListener(this);
        ivSnacks.setOnClickListener(this);
        ivDinner.setOnClickListener(this);

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
        /*arraybicycleList.add(new Bicycling("milk", "2", "4"));
       *//* arraybicycleList.add(new Bicycling("chapathi", "3", "2"));
        arraybicycleList.add(new Bicycling("curry", "5", "1"));*//*
        arrayrunList.add(new Running("juice", "8", "3"));
        arrayrunList.add(new Running("rice", "6", "21"));
        arrayrunList.add(new Running("chapati", "5", "6"));
        arraywalkingList.add(new Walking("chapathi", "7", "2"));
        arraywalkingList.add(new Walking("milk", "1", "6"));
        arraywalkingList.add(new Walking("rice", "9", "2"));
        arrayswimList.add(new Swimming("juice", "5", "7"));
        arrayswimList.add(new Swimming("milk", "6", "6"));
        arrayswimList.add(new Swimming("chapthi", "2", "6"));*/
        ivBreakfast.setOnClickListener(this);
        rlDate.setOnClickListener(this);
    }

    public void getFoodConsumptions() {

        ApiInterface client = ApiClient.getClient();

        Calendar calendar = Calendar.getInstance();

        try {

            JSONObject paramObject = new JSONObject();
            paramObject.put(USER_ID, userId);
            paramObject.put(DATE, new SimpleDateFormat(getString(R.string.date_format), Locale.ENGLISH).format(calendar.getTime()));

            Call<ServerResponseFood> call = client.getFood(accessToken, paramObject.toString());

            call.enqueue(new Callback<ServerResponseFood>() {
                @Override
                public void onResponse(Call<ServerResponseFood> call, Response<ServerResponseFood> response) {

                    if (getActivity() != null) {
                        closeProgressDialog();
                        if (response.isSuccessful()) {
                            Log.e("FOOD", "Response ::  " + response.code() + "   message ::  " + response.message());

                            Food _food = response.body().getResults();

                            breakfasts = _food.getBreakFast();
                            lunches = _food.getLunch();
                            snacks = _food.getSnacks();
                            dinners = _food.getDinner();

                            breakfastAdapter = new LunchAdapter(getActivity(), R.layout.list_row_exercise, breakfasts);
                            recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            recycler_view.setAdapter(breakfastAdapter);

                            lunchAdaoter = new LunchAdapter(getActivity(), R.layout.list_row_exercise, lunches);
                            recycler_view1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            recycler_view1.setAdapter(lunchAdaoter);

                            snacksAdapter = new LunchAdapter(getActivity(), R.layout.list_row_exercise, snacks);
                            recycler_view2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            recycler_view2.setAdapter(snacksAdapter);

                            dinnerAdapter = new LunchAdapter(getActivity(), R.layout.list_row_exercise, dinners);
                            recycler_view3.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            recycler_view3.setAdapter(dinnerAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServerResponseFood> call, Throwable t) {
                    if (getActivity() != null) closeProgressDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            closeProgressDialog();
        }
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

                        String date = year + "-" + monthYear + "-" + dayOfMonth;
                        tvDate.setText(year + "-" + monthYear + "-" + dayOfMonth);

                        getFoodConsumptions();

                    }
                }, mYear, mMonth, mDay);


        datePickerDialog.show();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ivAdd:
                selectDate();
                break;
            case R.id.date_layout:
                selectDate();
                break;
            case R.id.ivBreakfast:
                intent = new Intent(getActivity(), AddFoodConsumptionActivity.class);
                intent.putExtra(TITLE, BREAKFAST);
                startActivity(intent);
                break;
            case R.id.ivLunch:
                intent = new Intent(getActivity(), AddFoodConsumptionActivity.class);
                intent.putExtra(TITLE, LUNCH);
                startActivity(intent);
                break;
            case R.id.ivDinner:
                intent = new Intent(getActivity(), AddFoodConsumptionActivity.class);
                intent.putExtra(TITLE, DINNER);
                startActivity(intent);
                break;
            case R.id.ivSnacks:
                intent = new Intent(getActivity(), AddFoodConsumptionActivity.class);
                intent.putExtra(TITLE, SNACKS);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getFoodConsumptions();
    }
}

