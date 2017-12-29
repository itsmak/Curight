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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.CholesterolAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.AddCholesterolRecordsDialog;
import com.innovellent.curight.model.BloodPressure;
import com.innovellent.curight.model.CholesterolDayWise;
import com.innovellent.curight.model.CholesterolRecord;
import com.innovellent.curight.model.CholesterolReport;
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

/**
 * Created by sagar on 10/1/2017.
 */

public class CholesterolFragment extends Fragment implements View.OnClickListener {

    TextView tvList;
    TextView tvTrends;
    ImageView ivAdd;
    RecyclerView recyclerView;
    CholesterolAdapter mAdapter;
    ArrayList<BloodPressure> arrayList = new ArrayList<>();
    CardView cvCard;
    AddCholesterolRecordsDialog addCholesterolRecordsDialog;
    LinearLayout llStatus;
    RelativeLayout rlGraph;
    int i;
    GraphView line_graph;
    private String accessToken;
    private long userId;
    private TextView tvLDL, tvHDL;
    private ImageView ivBmi;
    private Button btnStatus;
    private ProgressDialog progressDialog;
    private GraphView lineGraph;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cholestrol, container, false);
        initReferences(rootView);
        initOnClick();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);

        accessToken = sharedPreferences.getString("access_token", "");
        userId = sharedPreferences.getLong("user_id", 1L);

        showProgressDialog("Loading");
        getCholesterolRecords();

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
        llStatus = (LinearLayout) rootView.findViewById(R.id.llStatus);
        tvTrends = (TextView) rootView.findViewById(R.id.tvTrends);
        tvList = (TextView) rootView.findViewById(R.id.tvList);
        ivAdd = (ImageView) rootView.findViewById(R.id.ivAdd);
        rlGraph = (RelativeLayout) rootView.findViewById(R.id.rlGraph);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        cvCard = (CardView) rootView.findViewById(R.id.cvCard1);
        lineGraph = (GraphView) rootView.findViewById(R.id.graphLine);

        ivBmi = (ImageView) rootView.findViewById(R.id.ivBmi);
        btnStatus = (Button) rootView.findViewById(R.id.btnStatus);
        tvLDL = (TextView) rootView.findViewById(R.id.ldl);
        tvHDL = (TextView) rootView.findViewById(R.id.hdl);
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

    private void showAddCholesterolRecordDialog() {
        addCholesterolRecordsDialog = new AddCholesterolRecordsDialog(getActivity(), new AddCholesterolRecordsDialog.AddCholesterolRecordsDialogClickListener() {

            @Override
            public void onSubmit(String date, String time, String ldl, String hdl, String triglycerides) {
                addCholesterolRecordsDialog.dismiss();
                showProgressDialog("Adding");
                addCholesterolRecord(date, time, ldl, hdl, triglycerides);
            }

            @Override
            public void onCancel() {
                addCholesterolRecordsDialog.dismiss();
            }
        });

        addCholesterolRecordsDialog.show();
    }

    private void getCholesterolRecords() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("userid", userId);

            Call<ServerResponse<CholesterolReport>> call = apiInterface.getCholesterolRecords(accessToken, paramObject.toString());
            call.enqueue(new Callback<ServerResponse<CholesterolReport>>() {
                @Override
                public void onResponse(Call<ServerResponse<CholesterolReport>> call, Response<ServerResponse<CholesterolReport>> response) {
                    if (getActivity() != null) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            ServerResponse<CholesterolReport> serverResponse = response.body();
                            CholesterolReport report = serverResponse.getResults();

                            tvLDL.setText(String.valueOf(report.getLdl()));
                            tvHDL.setText(String.valueOf(report.getHdl()));
                            if (report.getCholesterolFlag() != null)
                                switch (report.getCholesterolFlag()) {
                                    case "Good":
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

                            List<CholesterolDayWise> cholesterolDayWises = report.getDayWises();
                            if (!cholesterolDayWises.isEmpty()) {
                                List<Object> objects = new ArrayList<>();
                                List<DataPoint> points = new ArrayList<>();
                                int i = 0;
                                for (CholesterolDayWise dayWise : cholesterolDayWises) {
                                    objects.add(dayWise.getDate());
                                    List<CholesterolRecord> records = dayWise.getRecords();
                                    for (int j = 0, size = records.size(); j < size; i++, j++) {
                                        CholesterolRecord record = records.get(j);
                                        objects.add(record);
                                        points.add(new DataPoint(i, record.getTriglycerides()));
                                    }
                                    objects.addAll(dayWise.getRecords());
                                }

                                DataPoint[] pointArray = new DataPoint[points.size()];
                                lineGraph.removeAllSeries();
                                lineGraph.addSeries(new LineGraphSeries<>(points.toArray(pointArray)));
                                lineGraph.getViewport().setMinX(0);
                                lineGraph.getViewport().setMinY(0);
                                lineGraph.getViewport().setScrollable(false);

                                mAdapter = new CholesterolAdapter(getActivity(), objects, new CholesterolAdapter.OnCholesterolListener() {
                                    @Override
                                    public void onDelete(CholesterolRecord record) {
                                        showProgressDialog("Deleting");
                                        deleteCholesterolRecord(record);
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
                public void onFailure(Call<ServerResponse<CholesterolReport>> call, Throwable t) {
                    if (getActivity() != null) progressDialog.dismiss();
                    t.getMessage();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteCholesterolRecord(CholesterolRecord record) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("cholestrolid", record.getCholesterolId());

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
                                getCholesterolRecords();
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

    private void addCholesterolRecord(String date, String time, String ldl, String hdl, String triglycerides) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("userid", userId);
            paramObject.put("hdl", hdl);
            paramObject.put("ldl", ldl);
            paramObject.put("triglycerides", triglycerides);
            paramObject.put("date", date);
            paramObject.put("time", time);

            Call<ServerResponse<String>> call = apiInterface.addCholesterolRecord(accessToken, paramObject.toString());
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
                                getCholesterolRecords();
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
                showAddCholesterolRecordDialog();
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


