package com.innovellent.curight.fragment;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.ProfileSpinnerAdapter;
import com.innovellent.curight.adapter.TrackAdapter;
import com.innovellent.curight.api.ApiClient;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.FamilyProfile;
import com.innovellent.curight.model.Goal;
import com.innovellent.curight.model.ServerResponse;
import com.innovellent.curight.utility.SharedPrefService;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.innovellent.curight.utility.Constants.ACCESS_TOKEN;
import static com.innovellent.curight.utility.Constants.CURIGHT_TAG;
import static com.innovellent.curight.utility.Constants.GOAL;
import static com.innovellent.curight.utility.Constants.GOAL_ID;
import static com.innovellent.curight.utility.Constants.USER_ID;

public class TrackDataFragment extends Fragment implements View.OnClickListener, NumberPicker.OnValueChangeListener {
    private static final String TAG = "CuRight";
    RecyclerView recycler_view;
    ArrayList<String> arrayList = new ArrayList<String>();
    TrackAdapter mAdapter;
    Spinner spUser;
    TextView tvSave;
    NumberPicker numberpicker;
    ImageView ivAdd, ivback, ivback1;
    LinearLayout llCalariesBurned, llCaloriesConsumed;
    int goal_val = 0;
    TextView tv_locationtxt,tv_locationsymbl;
    int goal_change_val = 0;
    int goal_id;
    ImageView iv_search;
    TextView tvMyGoal, tvTitle, tvGoalTop, tvConsumedNumber, tvBurnedNumber;
    ProgressBar circularProgressbar_starttest;
    private SharedPrefService sharedPrefService;
    private long userId;
    private String accessToken;
    private List<FamilyProfile> familyProfiles;
    private int progressBarCounter = 2;
    private ProgressDialog progressDialog;
    private Goal goal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_track_data, container, false);

        progressDialog = ProgressDialog.show(getContext(), "Loading", "please wait", true, false);
        progressDialog.show();

        init(rootView);
        iniClick();

        sharedPrefService = SharedPrefService.getInstance();
        userId = sharedPrefService.getLong(USER_ID);
        accessToken = sharedPrefService.getString(ACCESS_TOKEN);

        int uid = (int) Prefs.getLong("user_id",0);
        getFamilyProfiles(uid);

        getGoal(uid);

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
                            spUser.setAdapter(spinnerAdapter);
                            spUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    Prefs.putLong("spinner_id",Long.parseLong(familyProfiles.get(i).getUserId()));
                                    int usid = (int) Prefs.getLong("spinner_id",0);
                                    getGoal(usid);
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
                    if (getActivity() != null) closeProgressDialog();
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

    public void init(View rootView) {
        tvTitle = (TextView) getActivity().findViewById(R.id.tvTitle);
        iv_search = (ImageView) getActivity().findViewById(R.id.select_loc);
        ivback = (ImageView) getActivity().findViewById(R.id.ivback);
        ivback1 = (ImageView) getActivity().findViewById(R.id.ivback1);
        circularProgressbar_starttest = (ProgressBar) rootView.findViewById(R.id.circularProgressbar_starttest);
        tvMyGoal = (TextView) rootView.findViewById(R.id.tvMyGoal);
        tvGoalTop = (TextView) rootView.findViewById(R.id.tvConsumedNumber);
        tvConsumedNumber = (TextView) rootView.findViewById(R.id.tvBurnedNumber);
        tvBurnedNumber = (TextView) rootView.findViewById(R.id.tvNetNumber);
        ivAdd = (ImageView) getActivity().findViewById(R.id.ivAdd);
        spUser = (Spinner) rootView.findViewById(R.id.spUser);
        llCalariesBurned = (LinearLayout) rootView.findViewById(R.id.llCalariesBurned);
        llCaloriesConsumed = (LinearLayout) rootView.findViewById(R.id.llCaloriesConsumed);
        tv_locationtxt = (TextView) getActivity().findViewById(R.id.tv_locationtxt);
        tv_locationsymbl = (TextView) getActivity().findViewById(R.id.tv_locationsymbl);
        tv_locationtxt.setVisibility(View.GONE);
        tv_locationsymbl.setVisibility(View.GONE);
        iv_search.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("Food & Fitness");
       // ivback.setVisibility(View.GONE);

    }

    public void iniClick() {
       //tvTitle.setText("Food & Fitness");
        ivback.setVisibility(View.GONE);
        ivback1.setVisibility(View.VISIBLE);
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
        tvMyGoal.setOnClickListener(this);
        llCalariesBurned.setOnClickListener(this);
        llCaloriesConsumed.setOnClickListener(this);
        ivAdd.setVisibility(View.INVISIBLE);
    }

    public void showBottomSheet() {
        final BottomSheetDialog sheetDialog = new BottomSheetDialog(getActivity());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View bottomSheetView = inflater.inflate(R.layout.bottom_sheet, null);
        sheetDialog.setContentView(bottomSheetView);
        sheetDialog.show();

        tvSave = (TextView) sheetDialog.findViewById(R.id.tvSave);
        numberpicker = (NumberPicker) sheetDialog.findViewById(R.id.numberPicker1);
        numberpicker.setMinValue(0);
        numberpicker.setMaxValue(100);
        numberpicker.setValue(goal_val);
        numberpicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                goal_change_val = newVal;

            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGoal();
                //tvMyGoal.setText("My Goal is: "+goal_change_val);
                sheetDialog.dismiss();
            }
        });

    }

    public void setGoal() {
        ApiInterface client = ApiClient.getClient();

        progressDialog = ProgressDialog.show(getContext(), "Setting Goal", "please wait", false);
        progressDialog.show();

        try {
            JSONObject paramObject = new JSONObject();
            int uid = (int) Prefs.getLong("user_id",0);
            paramObject.put("userid", uid);
            paramObject.put("goal", goal_change_val);
            Log.d(TAG,"goal input:"+paramObject.toString());
            Call<ServerResponse<String>> call = client.setGoal(accessToken, paramObject.toString());

            call.enqueue(new Callback<ServerResponse<String>>() {
                @Override
                public void onResponse(Call<ServerResponse<String>> call, Response<ServerResponse<String>> response) {

                    if (getActivity() != null) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            if ("Success".equals(response.body().getResults())) {
                                Toast.makeText(getActivity(), "Goal set to " + goal_change_val, Toast.LENGTH_SHORT).show();
                                tvMyGoal.setText(getString(R.string.my_goal_formatted, goal_change_val));
                                tvGoalTop.setText(String.valueOf(goal_change_val));
                                //tvConsumedNumber.setText();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Can't update goal to " + goal_change_val, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse<String>> call, Throwable t) {
                    if (getActivity() != null) progressDialog.dismiss();
                    Log.e(CURIGHT_TAG, "error :: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
        }
    }

    public void getGoal(int uid) {
        ApiInterface client = ApiClient.getClient();

        try {
            Log.d(TAG,"get Goal userid:"+uid);
            JSONObject paramObject = new JSONObject();
            paramObject.put(USER_ID, uid);

            Call<ServerResponse<Goal>> call = client.getGoal(accessToken, paramObject.toString());

            call.enqueue(new Callback<ServerResponse<Goal>>() {
                @Override
                public void onResponse(Call<ServerResponse<Goal>> call, Response<ServerResponse<Goal>> response) {
                    if (getActivity() != null) {
                        closeProgressDialog();
                        if (response.isSuccessful()) {
                            ServerResponse<Goal> serverResponse = response.body();
                            if ((goal = serverResponse.getResults()) != null) {
                                Log.d(TAG,"get Goal goal:"+goal.getGoal());
                                Log.d(TAG,"get Goal burn:"+goal.getBurn());
                                Log.d(TAG,"get Goal consumption:"+goal.getConsumption());
                                Log.d(TAG,"get Goal goalpercentage:"+goal.getGoalpercentage());
                                tvGoalTop.setText(String.valueOf(goal.getGoal()));
                                tvMyGoal.setText(getString(R.string.my_goal_formatted, goal.getGoal()));
                                tvBurnedNumber.setText(String.valueOf(goal.getBurn()));
                                tvConsumedNumber.setText(String.valueOf(goal.getConsumption()));
                                circularProgressbar_starttest.setProgress(75);
                                ObjectAnimator animation = ObjectAnimator.ofInt (circularProgressbar_starttest, "progress", 0, 75); // see this max value coming back here, we animate towards that value
                                animation.setDuration (2000); //in milliseconds
                                animation.setInterpolator (new DecelerateInterpolator());
                                animation.start ();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse<Goal>> call, Throwable t) {
                    closeProgressDialog();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            closeProgressDialog();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvMyGoal:
                showBottomSheet();
                break;
            case R.id.llCaloriesConsumed:
                Fragment fragment = new FoodFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rlMainFragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.llCalariesBurned:
                Fragment fragment1 = new ExerciseFragment();
                FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                fragmentTransaction1.replace(R.id.rlMainFragment, fragment1);
                fragmentTransaction1.addToBackStack(null);
                fragmentTransaction1.commit();
                break;
        }
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {

    }
}
