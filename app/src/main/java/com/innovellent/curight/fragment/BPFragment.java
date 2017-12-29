package com.innovellent.curight.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.activities.HomeActivity;
import com.innovellent.curight.adapter.BPAdapter;
import com.innovellent.curight.adapter.PROFILE_SPINNER_ADAPTER;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.AddBPRecordsDialog;
import com.innovellent.curight.model.BloodPressureDayWise;
import com.innovellent.curight.model.BloodPressureRecord;
import com.innovellent.curight.model.BloodPressureReport;
import com.innovellent.curight.model.MyProfile_Response;
import com.innovellent.curight.model.PROFILE;
import com.innovellent.curight.model.PROFILE_FEED;
import com.innovellent.curight.model.PostBodyProfile;
import com.innovellent.curight.model.ServerResponse;
import com.innovellent.curight.utility.Config;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.innovellent.curight.utility.Constants.CURIGHT_TAG;


public class BPFragment extends Fragment implements View.OnClickListener {

    TextView tvBP, tvList, tvTrends;
    ImageView ivBmi;
    ImageView ivAdd;
    LinearLayout llStatus;
    Button btnStatus;
    CardView cvCard;
    RecyclerView recyclerView;
    BPAdapter mAdapter;
    GraphView lineGraph;
    ArrayList<Object> arrayList = new ArrayList<>();
    AddBPRecordsDialog addRecordsDialog;
    RelativeLayout rlGraph;
    int i;
    String USER_ID;
    ArrayList<PROFILE> spinnerList=new ArrayList<PROFILE>();
    PROFILE_SPINNER_ADAPTER customSpinnerAdapter3;
    private TextView systolicDiastolic, pulse;
    private Long userId;
    private String accessToken;
    private static final String TAG = ".Retro_MainActivity";
    private ProgressDialog progressDialog;
    int uid;
    Spinner spUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bp, container, false);
        initReferences(rootView);
        initOnClick();

        getSpinnerData();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", "");
        userId = sharedPreferences.getLong("user_id", 2L);

        showProgressDialog("Loading");
        getBloodPressureRecords(HomeActivity.USER_ID);

        return rootView;
    }

    public void setGreen() {
        ivBmi.setBackgroundResource(R.mipmap.ic_statscopegreen);
        //  llStatus.setBackgroundColor(Color.parseColor("#C0F3AD"));
        btnStatus.setBackgroundColor(Color.parseColor("#6ADEB6"));
        // btnStatus.setTextColor(Color.parseColor("#72C852"));
        btnStatus.setText("Low Risk");
    }

    public void setYellow() {
        ivBmi.setBackgroundResource(R.mipmap.ic_statscopeyellow);
        // llStatus.setBackgroundColor(Color.parseColor("#FFDC75"));
        btnStatus.setBackgroundColor(Color.parseColor("#FFC300"));
        //btnStatus.setTextColor(Color.parseColor("#EBC75B"));
        btnStatus.setText("Medium Risk");
    }

    public void setBlue() {
        ivBmi.setBackgroundResource(R.mipmap.ic_statscopeblue);
        //  llStatus.setBackgroundColor(Color.parseColor("#CEDEFF"));
        btnStatus.setBackgroundColor(Color.parseColor("#0075b2"));
        //  btnStatus.setTextColor(Color.parseColor("#8EA6DC"));
        btnStatus.setText("Normal");
    }

    public void setRed() {
        ivBmi.setBackgroundResource(R.mipmap.ic_statscopered);
        // llStatus.setBackgroundColor(Color.parseColor("#CEDEFF"));
        btnStatus.setBackgroundColor(Color.parseColor("#FE5757"));
        // btnStatus.setTextColor(Color.parseColor("#8EA6DC"));
        btnStatus.setText("High Risk");
    }

    public void initReferences(View rootView) {
        systolicDiastolic = (TextView) rootView.findViewById(R.id.systolic_diastolic);
        pulse = (TextView) rootView.findViewById(R.id.pulse);
        tvBP = (TextView) rootView.findViewById(R.id.tvBP);
        ivBmi = (ImageView) rootView.findViewById(R.id.ivBmi);
        llStatus = (LinearLayout) rootView.findViewById(R.id.llStatus);
        btnStatus = (Button) rootView.findViewById(R.id.btnStatus);
        tvTrends = (TextView) rootView.findViewById(R.id.tvTrends);
        tvList = (TextView) rootView.findViewById(R.id.tvList);
        ivAdd = (ImageView) rootView.findViewById(R.id.ivAdd);
        rlGraph = (RelativeLayout) rootView.findViewById(R.id.rlGraph);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        cvCard = (CardView) rootView.findViewById(R.id.cvCard);
        lineGraph = (GraphView) rootView.findViewById(R.id.graphLine);
        spUser = (Spinner) rootView.findViewById(R.id.spUser);
    }

    public void initOnClick() {
        ivAdd.setOnClickListener(this);
        tvTrends.setOnClickListener(this);
        tvList.setOnClickListener(this);
        rlGraph.setVisibility(View.VISIBLE);
        cvCard.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        tvTrends.setTextColor(Color.parseColor("#FFFFFF"));
        tvList.setTextColor(Color.parseColor("#9DA1A0"));
    }

    private void showAddBloodPressureDialog() {
        addRecordsDialog = new AddBPRecordsDialog(getActivity(), new AddBPRecordsDialog.AddRecordsDialogClickListener() {

            @Override
            public void onSubmit(String date, String time, String systolic, String diastolic, String pulse) {
                addRecordsDialog.dismiss();
                showProgressDialog("Adding");
                addBloodPressureRecord(date, time, systolic, diastolic, pulse);
            }

            @Override
            public void onCancel() {
                addRecordsDialog.dismiss();
            }
        });

        addRecordsDialog.show();
    }

    private void addBloodPressureRecord(String date, String time, String systolic, String diastolic, String pulse) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("userid", userId);
            paramObject.put("time", time);
            paramObject.put("date", date);
            paramObject.put("pulse", pulse);
            paramObject.put("systolic", systolic);
            paramObject.put("diastolic", diastolic);

            Call<ServerResponse<String>> call = apiInterface.addBloodPressureRecord(accessToken, paramObject.toString());
            call.enqueue(new Callback<ServerResponse<String>>() {
                @Override
                public void onResponse(Call<ServerResponse<String>> call, Response<ServerResponse<String>> response) {
                    if (getActivity() != null) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            ServerResponse<String> serverResponse = response.body();
                            if (serverResponse.getResults().equals("Success")) {
                                Toast.makeText(getActivity(), "Successfully Added", Toast.LENGTH_SHORT).show();
                                showProgressDialog("Loading");
                                getBloodPressureRecords(HomeActivity.USER_ID);
                            } else
                                Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse<String>> call, Throwable t) {
                    Log.d(CURIGHT_TAG, t.getMessage());
                    if (getActivity() != null) progressDialog.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void getSpinnerData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface reditapi = retrofit.create(ApiInterface.class);
        int uid = (int) Prefs.getLong("user_id",0);
        PostBodyProfile postBodyprofile = new PostBodyProfile(uid, "family");
        Call<MyProfile_Response> call = reditapi.getProfile(postBodyprofile);

        call.enqueue(new Callback<MyProfile_Response>() {
            @Override
            public void onResponse(Call<MyProfile_Response> call, Response<MyProfile_Response> response) {


                if (response.body() != null) {


                    Log.e("", "profileResponse: code: " + response.body().getCode());

                    ArrayList<PROFILE_FEED> result = response.body().getResults();

                    Log.e("", "profileResponse: listsize: " + result.size());
                    for (int i = 0; i < result.size(); i++) {

                        USER_ID = result.get(i).getUserid();
                        //spinnerList.add(new PROFILE("","","",""));
                        spinnerList.add(new PROFILE(result.get(i).getUserid(),result.get(i).getId(), result.get(i).getName(), result.get(i).getAge(), result.get(i).getRelationship()));
                    }
                    getData2();
                    // GetData(result.get(1).getUserid());
                } else {

                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();

                }

                //remainder_rclrvw.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<MyProfile_Response> call, Throwable t) {

                Log.e("", "onFailure: Somethings went wrong" + t.getMessage());
                Toast.makeText(getActivity(), "Somethings went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void getData2() {

        customSpinnerAdapter3 = new PROFILE_SPINNER_ADAPTER(getActivity(), spinnerList);
        spUser.setAdapter(customSpinnerAdapter3);
        spUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Toast.makeText(PasswordRecoveryQuestionsActivity.this, spinner3[i], Toast.LENGTH_SHORT).show();
                //spAge.setText(spinnerList.get(i).getUser_age());
                USER_ID = spinnerList.get(i).getUser_id();
                Prefs.putLong("spinner_id", Long.parseLong(spinnerList.get(i).getUser_id()));
                uid = (int) Prefs.getLong("spinner_id",0);
                Log.e("Userid", spinnerList.get(i).getUser_id());
                //bp.getBloodPressureRecords(spinnerList.get(i).getUser_id());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    public void getBloodPressureRecords(String user_id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("userid", user_id);

            Call<ServerResponse<BloodPressureReport>> call = apiInterface.getBloodPressureRecords("bgvvgjhhjv", paramObject.toString());
            call.enqueue(new Callback<ServerResponse<BloodPressureReport>>() {
                @Override
                public void onResponse(Call<ServerResponse<BloodPressureReport>> call, Response<ServerResponse<BloodPressureReport>> response) {
                    if (getActivity() != null) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            ServerResponse<BloodPressureReport> serverResponse = response.body();
                            BloodPressureReport report = serverResponse.getResults();
                            Log.d("BP", report.toString());
                            systolicDiastolic.setText(getActivity().getString(R.string.systolic_diastolic_formatted, report.getSystolic(), report.getDiastolic()));
                            pulse.setText(String.valueOf(report.getPulse()));
                            switch (report.getBpFlag()) {
                                case "normal":
                                    setBlue();
                                    break;
                                case "low risk":
                                    setGreen();
                                    break;
                                case "medium risk":
                                    setYellow();
                                    break;
                                case "high risk":
                                    setRed();
                                    break;
                                default:
                                    setYellow();
                                    break;
                            }

                            List<BloodPressureDayWise> dayWises = report.getBpList();
                            if (!dayWises.isEmpty()) {
                                List<Object> objects = new ArrayList<>();
                                List<DataPoint> points = new ArrayList<>();
                                int i = 0;
                                for (BloodPressureDayWise dayWise : dayWises) {
                                    objects.add(dayWise.getDate());
                                    List<BloodPressureRecord> records = dayWise.getBpList();
                                    for (int j = 0, size = records.size(); j < size; i++, j++) {
                                        BloodPressureRecord record = records.get(j);
                                        objects.add(record);
                                        points.add(new DataPoint(i, record.getPulse()));
                                    }
                                }
                                DataPoint[] pointArray = new DataPoint[points.size()];
                                lineGraph.removeAllSeries();
                                lineGraph.addSeries(new LineGraphSeries<>(points.toArray(pointArray)));
                                lineGraph.getViewport().setMinX(0);
                                lineGraph.getViewport().setMinY(0);
                                lineGraph.getViewport().setScrollable(false);

                                mAdapter = new BPAdapter(getActivity(), objects, new BPAdapter.OnBloodPressureListener() {
                                    @Override
                                    public void onDelete(BloodPressureRecord record) {
                                        showProgressDialog("Deleting");
                                        deleteBloodPressureRecord(record);
                                    }
                                });
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                recyclerView.setAdapter(mAdapter);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse<BloodPressureReport>> call, Throwable t) {
                    if (getActivity() != null) progressDialog.dismiss();
                    Log.d(CURIGHT_TAG, t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showProgressDialog(String title) {
        progressDialog = ProgressDialog.show(getContext(), title, "please wait", true, false);
        progressDialog.show();
    }

    private void deleteBloodPressureRecord(BloodPressureRecord record) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("bpid", record.getBpId());

            Call<ServerResponse<String>> call = apiInterface.deleteBloodPressureRecord(accessToken, paramObject.toString());
            call.enqueue(new Callback<ServerResponse<String>>() {
                @Override
                public void onResponse(Call<ServerResponse<String>> call, Response<ServerResponse<String>> response) {
                    if (getActivity() != null) {

                        progressDialog.dismiss();
                        if (response.isSuccessful()) {

                            ServerResponse<String> serverResponse = response.body();
                            if (serverResponse.getResults().equals("Success")) {
                                Toast.makeText(getActivity(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                showProgressDialog("Loading");
                                getBloodPressureRecords(HomeActivity.USER_ID);
                            } else {
                                Toast.makeText(getActivity(), "please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse<String>> call, Throwable t) {
                    if (getActivity() != null) progressDialog.dismiss();
                    Log.d(CURIGHT_TAG, t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivAdd:
                showAddBloodPressureDialog();
                break;
            case R.id.tvTrends:
                rlGraph.setVisibility(View.VISIBLE);
                cvCard.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                tvTrends.setTextColor(Color.parseColor("#FFFFFF"));
                tvList.setTextColor(Color.parseColor("#9DA1A0"));

                break;
            case R.id.tvList:
                rlGraph.setVisibility(View.GONE);
                cvCard.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                tvTrends.setTextColor(Color.parseColor("#9DA1A0"));
                tvList.setTextColor(Color.parseColor("#FFFFFF"));

                break;

            default:
                break;

        }
    }
}


