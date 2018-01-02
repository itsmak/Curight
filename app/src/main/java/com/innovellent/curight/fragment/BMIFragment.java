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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.BMIAdapter;
import com.innovellent.curight.adapter.PROFILE_SPINNER_ADAPTER;
import com.innovellent.curight.adapter.WHRAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.AddBMIRecordsDialog;
import com.innovellent.curight.model.BMI;
import com.innovellent.curight.model.BMIDayWise;
import com.innovellent.curight.model.BMIRecord;
import com.innovellent.curight.model.BMIReport;
import com.innovellent.curight.model.BloodPressure;
import com.innovellent.curight.model.MyProfile_Response;
import com.innovellent.curight.model.PROFILE;
import com.innovellent.curight.model.PROFILE_FEED;
import com.innovellent.curight.model.ParameterBMI;
import com.innovellent.curight.model.PostBodyProfile;
import com.innovellent.curight.model.ServerResponse;
import com.innovellent.curight.utility.Config;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.content.ContentValues.TAG;
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
    String USER_ID,res_dataforbmi;
    ArrayList<PROFILE> spinnerList=new ArrayList<PROFILE>();
    PROFILE_SPINNER_ADAPTER customSpinnerAdapter3;
    ArrayList<BMI> bmiArrayList = new ArrayList<BMI>();
    RelativeLayout rlGraph;
    JSONArray jsonArray_parent,jsonarray_child;
    Spinner spUser;
    private String accessToken;
    private long userId;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bmi, container, false);
        initReferences(rootView);
        initOnClick();

        /*SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);

        accessToken = sharedPreferences.getString("access_token", "");
        userId = sharedPreferences.getLong("user_id", 7L);

        showProgressDialog("Loading");*/
        spinnerList.clear();
        getSpinnerData();

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
        spUser = (Spinner) rootView.findViewById(R.id.spUser);
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
                        spinnerList.add(new PROFILE(result.get(i).getUserid(),result.get(i).getId(), firstName, result.get(i).getAge(), result.get(i).getRelationship()));
                    }
                    getData2();
                    USER_ID = result.get(0).getUserid();

                    // GetData(result.get(1).getUserid());
                } else {

                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();

                }

                //remainder_rclrvw.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<MyProfile_Response> call, Throwable t) {

                Log.e("", "onFailure: Somethings went wrong" + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

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
                // USER_ID = spinnerList.get(i).getUser_id();
                Prefs.putLong("spinner_id", Long.parseLong(spinnerList.get(i).getUser_id()));
                int uid = (int) Prefs.getLong("spinner_id",0);
                Log.d(TAG,"Useridchanding" +uid);
                getBMIRecords(uid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                int uid = (int) Prefs.getLong("user_id",0);
                Log.d("user_forwhr", ""+uid);
                Log.d(TAG, "MyUSER_ID on spinner" + USER_ID);
                getBMIRecords(uid);
            }
        });

    }

    private void getBMIRecords(int user_id) {
        cleardata();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        ParameterBMI parameterBMI = new ParameterBMI(user_id);

        final Call<ResponseBody> call = apiInterface.getBMIRecords("abc", parameterBMI);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    try{
                        res_dataforbmi = response.body().string();
                        List<DataPoint> points = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(res_dataforbmi);
                        String code = jsonObject.getString("Code");
                        JSONObject jsonObject1 = jsonObject.getJSONObject("Results");
                        int bmi = jsonObject1.getInt("bmi");
                        String bmiFlag = jsonObject1.getString("bmiFlag");
                        tvBMI.setText(String.valueOf(bmi));

                        if (bmiFlag != null){
                            switch (bmiFlag) {
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

                        }

                        jsonArray_parent = jsonObject1.getJSONArray("bmiList");
                        for(int i=0;i<jsonArray_parent.length();i++){
                            jsonObject1 = jsonArray_parent.getJSONObject(i);
                            jsonarray_child = jsonObject1.getJSONArray("bmiList");

                            for(int j=0; j<jsonarray_child.length(); j++){
                                bmiArrayList.add(new BMI(jsonArray_parent.getJSONObject(i).getString("date"),jsonarray_child.getJSONObject(j).getString("weight"),jsonarray_child.getJSONObject(j).getString("height"),jsonarray_child.getJSONObject(j).getString("bmi"),jsonarray_child.getJSONObject(j).getInt("bmiid")));
                                points.add(new DataPoint(j, Double.parseDouble(jsonarray_child.getJSONObject(j).getString("bmi"))));
                            }
                        }
                        DataPoint[] pointArray = new DataPoint[points.size()];
                        lineGraph.removeAllSeries();
                        lineGraph.addSeries(new LineGraphSeries<>(points.toArray(pointArray)));
                        // lineGraph = new GraphView(getActivity());
                        lineGraph.getViewport().setMinX(0);
                        lineGraph.getViewport().setMinY(0);
                        lineGraph.getViewport().setScrollable(false);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    tvBMI.setText("");
                    lineGraph.removeAllSeries();
                    ivBmi.setBackgroundResource(R.mipmap.ic_bmired);
                    btnStatus.setText("");
                    btnStatus.setBackgroundResource(R.color.bmitext);
                    recyclerView.removeAllViews();
                    Toast.makeText(getActivity(), "No Record Found", Toast.LENGTH_SHORT).show();
                }
                mAdapter=new BMIAdapter(getActivity(),bmiArrayList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: Somethings went wrong" + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cleardata(){

        Log.d("arraylistbefore", ""+bmiArrayList.size());
        mAdapter = new BMIAdapter(getActivity(), bmiArrayList);

        bmiArrayList.clear();
        Log.d("arraylistafter", ""+bmiArrayList.size());
        mAdapter.notifyDataSetChanged();
    }

   /* private void deleteBMIRecord(BMIRecord record) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("bmiid", record.getBmiid());

            Call<ServerResponse<String>> call = apiInterface.deleteBMIRecord("abc", paramObject.toString());
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


    }*/

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
            int uid = (int) Prefs.getLong("user_id",0);

            paramObject.put("userid", uid);
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
                                int uid = (int) Prefs.getLong("user_id",0);
                                getBMIRecords(uid);
                                progressDialog.dismiss();
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


