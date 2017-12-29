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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.BMIAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.AddBMIRecordsDialog;
import com.innovellent.curight.model.BMIDayWise;
import com.innovellent.curight.model.BMIRecord;
import com.innovellent.curight.model.BMIReport;
import com.innovellent.curight.model.BloodPressure;
import com.innovellent.curight.model.ServerResponse;
import com.innovellent.curight.utility.Config;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

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

public class BMIFragment extends Fragment implements View.OnClickListener {
    TextView tvBMI, tvList, tvTrends;
    ImageView ivBmi, ivAdd;
    RecyclerView recyclerView;
    BMIAdapter mAdapter;
    CardView cvCard;
    AddBMIRecordsDialog addBMIRecordsDialog;
    ArrayList<BloodPressure> arrayList = new ArrayList<>();
    Button btnStatus;
    GraphView lineGraph;
    int i;
    RelativeLayout rlGraph;
    private String accessToken;
    private long userId;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bmi, container, false);
        initReferences(rootView);
        initOnClick();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);

        accessToken = sharedPreferences.getString("access_token", "");
        userId = sharedPreferences.getLong("user_id", 7L);

        showProgressDialog("Loading");
        getBMIRecords();

        return rootView;

    }

    private void showAddBMIRecordDialog() {
        addBMIRecordsDialog = new AddBMIRecordsDialog(getActivity(), new AddBMIRecordsDialog.AddBMIRecordsDialogClickListener() {


            @Override
            public void onSubmit(String date, String time, String height, String weight) {
                addBMIRecordsDialog.dismiss();
                showProgressDialog("Adding");
                addBMIRecord(date, time, height, weight);
            }

            @Override
            public void onCancel() {
                addBMIRecordsDialog.dismiss();
            }
        });

        addBMIRecordsDialog.show();
    }

    public void setGreen() {
        ivBmi.setBackgroundResource(R.mipmap.ic_bmibodygreen);
        //  llStatus.setBackgroundColor(Color.parseColor("#C0F3AD"));
        btnStatus.setBackgroundColor(Color.parseColor("#6ADEB6"));
        // btnStatus.setTextColor(Color.parseColor("#72C852"));
        btnStatus.setText("Low Risk");
    }

    public void setYellow() {
        ivBmi.setBackgroundResource(R.mipmap.ic_bmibodyyellow);
        // llStatus.setBackgroundColor(Color.parseColor("#FFDC75"));
        btnStatus.setBackgroundColor(Color.parseColor("#FFC300"));
        //btnStatus.setTextColor(Color.parseColor("#EBC75B"));
        btnStatus.setText("Medium Risk");
    }

    public void setBlue() {
        ivBmi.setBackgroundResource(R.mipmap.ic_bmibodyblue);
        //  llStatus.setBackgroundColor(Color.parseColor("#CEDEFF"));
        btnStatus.setBackgroundColor(Color.parseColor("#0075b2"));
        //  btnStatus.setTextColor(Color.parseColor("#8EA6DC"));
        btnStatus.setText("Normal");
    }

    public void setRed() {
        ivBmi.setBackgroundResource(R.mipmap.ic_body);
        // llStatus.setBackgroundColor(Color.parseColor("#CEDEFF"));
        btnStatus.setBackgroundColor(Color.parseColor("#FE5757"));
        // btnStatus.setTextColor(Color.parseColor("#8EA6DC"));
        btnStatus.setText("High Risk");
    }

    public void initReferences(View rootView) {
        tvBMI = (TextView) rootView.findViewById(R.id.tv_bmi);
        ivBmi = (ImageView) rootView.findViewById(R.id.ivBmi);
        cvCard = (CardView) rootView.findViewById(R.id.cvCard);
        btnStatus = (Button) rootView.findViewById(R.id.btnStatus);
        tvTrends = (TextView) rootView.findViewById(R.id.tvTrends);
        tvList = (TextView) rootView.findViewById(R.id.tvList);
        ivAdd = (ImageView) rootView.findViewById(R.id.ivAdd);
        rlGraph = (RelativeLayout) rootView.findViewById(R.id.rlGraph);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        lineGraph = (GraphView) rootView.findViewById(R.id.graphLine);
    }

    public void initOnClick() {
        ivAdd.setOnClickListener(this);
        tvTrends.setOnClickListener(this);
        tvList.setOnClickListener(this);
        rlGraph.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        tvTrends.setTextColor(Color.parseColor("#FFFFFF"));
        tvList.setTextColor(Color.parseColor("#9DA1A0"));
    }

    private void getBMIRecords() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("userid", userId);

            Call<ServerResponse<BMIReport>> call = apiInterface.getBMIRecords(accessToken, paramObject.toString());
            call.enqueue(new Callback<ServerResponse<BMIReport>>() {
                @Override
                public void onResponse(Call<ServerResponse<BMIReport>> call, Response<ServerResponse<BMIReport>> response) {
                    if (getActivity() != null) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            ServerResponse<BMIReport> serverResponse = response.body();
                            BMIReport report = serverResponse.getResults();

                            tvBMI.setText(String.valueOf(report.getBmi()));
                            if (report.getBmiFlag() != null)
                                switch (report.getBmiFlag()) {
                                    case "U":
                                        setBlue();
                                        break;
                                    case "N":
                                        setGreen();
                                        break;
                                    case "OV":
                                        setYellow();
                                        break;
                                    case "OB":
                                        setRed();
                                        break;
                                    default:
                                        setYellow();
                                        break;
                                }

                            List<BMIDayWise> bmiDayWises = report.getBmiList();
                            if (!bmiDayWises.isEmpty()) {
                                List<Object> objects = new ArrayList<>();
                                List<DataPoint> points = new ArrayList<>();
                                int i = 0;
                                for (BMIDayWise dayWise : bmiDayWises) {
                                    objects.add(dayWise.getDate());
                                    List<BMIRecord> records = dayWise.getRecords();
                                    for (int j = 0, size = records.size(); j < size; i++, j++) {
                                        BMIRecord record = records.get(j);
                                        objects.add(record);
                                        points.add(new DataPoint(i, record.getBmi()));
                                    }
                                    objects.addAll(dayWise.getRecords());
                                }

                                DataPoint[] pointArray = new DataPoint[points.size()];
                                lineGraph.removeAllSeries();
                                lineGraph.addSeries(new LineGraphSeries<>(points.toArray(pointArray)));
                                lineGraph.getViewport().setMinX(0);
                                lineGraph.getViewport().setMinY(0);
                                lineGraph.getViewport().setScrollable(false);

                                mAdapter = new BMIAdapter(getActivity(), objects, new BMIAdapter.OnBMIListener() {
                                    @Override
                                    public void onDelete(BMIRecord record) {
                                        showProgressDialog("Deleting");
                                        deleteBMIRecord(record);
                                    }
                                });
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                recyclerView.setAdapter(mAdapter);
                            }
                        }else {
                            Toast.makeText(getActivity(), "No Record Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse<BMIReport>> call, Throwable t) {
                    if (getActivity() != null) progressDialog.dismiss();
                    t.getMessage();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteBMIRecord(BMIRecord record) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("bmiid", record.getBmiid());

            Call<ServerResponse<String>> call = apiInterface.deleteBMIRecord(accessToken, paramObject.toString());
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
                                getBMIRecords();
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

    private void showProgressDialog(String title) {
        progressDialog = ProgressDialog.show(getContext(), title, "please wait", true, false);
        progressDialog.show();
    }

    private void addBMIRecord(String date, String time, String height, String weight) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("userid", userId);
            paramObject.put("weight", weight);
            paramObject.put("weight", height);
            paramObject.put("date", date);
            paramObject.put("time", time);

            Call<ServerResponse<String>> call = apiInterface.addBMIRecord(accessToken, paramObject.toString());
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
                                getBMIRecords();
                            } else
                                Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_SHORT).show();
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
                showAddBMIRecordDialog();
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

        }
    }
}


