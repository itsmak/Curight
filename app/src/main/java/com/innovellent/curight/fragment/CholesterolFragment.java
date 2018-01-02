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
import com.innovellent.curight.adapter.BPAdapter;
import com.innovellent.curight.adapter.CholesterolAdapter;
import com.innovellent.curight.adapter.PROFILE_SPINNER_ADAPTER;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.AddCholesterolRecordsDialog;
import com.innovellent.curight.model.BloodPressure;
import com.innovellent.curight.model.Cholesterol;
import com.innovellent.curight.model.CholesterolDayWise;
import com.innovellent.curight.model.CholesterolRecord;
import com.innovellent.curight.model.CholesterolReport;
import com.innovellent.curight.model.MyProfile_Response;
import com.innovellent.curight.model.PROFILE;
import com.innovellent.curight.model.PROFILE_FEED;
import com.innovellent.curight.model.ParameterCholesterol;
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
    ArrayList<Cholesterol> cholesterolArrayList = new ArrayList<Cholesterol>();
    CardView cvCard;
    AddCholesterolRecordsDialog addCholesterolRecordsDialog;
    LinearLayout llStatus;
    RelativeLayout rlGraph;
    int i;
    GraphView line_graph;
    String res_data,USER_ID;
    JSONArray jsonarray_parent,jsonarray_child;
    ArrayList<PROFILE> spinnerList=new ArrayList<PROFILE>();
    PROFILE_SPINNER_ADAPTER customSpinnerAdapter3;
    Spinner spUser;
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

        /*SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);

        accessToken = sharedPreferences.getString("access_token", "");
        userId = sharedPreferences.getLong("user_id", 1L);
*/
        //showProgressDialog("Loading");
        spinnerList.clear();
        getSpinnerData();

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
        spUser = (Spinner) rootView.findViewById(R.id.spUser);
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
                getCholesterolRecords(uid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                int uid = (int) Prefs.getLong("user_id",0);
                Log.d("user_forwhr", ""+uid);
                Log.d(TAG, "MyUSER_ID on spinner" + USER_ID);
                getCholesterolRecords(uid);
            }
        });

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

    private void getCholesterolRecords(int user_id) {
        cleardata();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        ParameterCholesterol parameterCholesterol = new ParameterCholesterol(user_id);

        Call<ResponseBody> call = apiInterface.getCholesterolRecords("abc", parameterCholesterol);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {

                    try{
                        res_data = response.body().string();
                        List<DataPoint> points = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(res_data);
                        String code = jsonObject.getString("Code");

                        JSONObject jsonObject1 = jsonObject.getJSONObject("Results");
                        String whrflag_mystring = jsonObject1.getString("cholestrolFlag");
                        if (whrflag_mystring != null){
                            switch (whrflag_mystring) {
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

                        }

                        int HDL = jsonObject1.getInt("hdl");
                        int LDL = jsonObject1.getInt("ldl");

                        tvLDL.setText(String.valueOf(LDL));
                        tvHDL.setText(String.valueOf(HDL));

                        jsonarray_parent = jsonObject1.getJSONArray("cholestrolbpList");
                        for(int i=0; i<jsonarray_parent.length(); i++){

                            jsonObject1 = jsonarray_parent.getJSONObject(i);

                            jsonarray_child = jsonObject1.getJSONArray("cholestrolList");
                            for(int j=0;j<jsonarray_child.length();j++){
                                cholesterolArrayList.add(new Cholesterol(jsonarray_child.getJSONObject(j).getInt("hdl"),jsonarray_child.getJSONObject(j).getInt("ldl"),jsonarray_child.getJSONObject(j).getInt("cholestrolid"),jsonarray_child.getJSONObject(j).getInt("triglycerides"),jsonarray_parent.getJSONObject(i).getString("date"),jsonarray_child.getJSONObject(j).getString("time"),jsonarray_child.getJSONObject(j).getString("totalCholestrolFlag")));
                                points.add(new DataPoint(j, Double.parseDouble(String.valueOf(jsonarray_child.getJSONObject(j).getInt("triglycerides")))));

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
                    tvHDL.setText("");
                    tvLDL.setText("");
                    lineGraph.removeAllSeries();
                    ivBmi.setBackgroundResource(R.mipmap.ic_cholestrolblue);
                    btnStatus.setText("");
                    btnStatus.setBackgroundResource(R.color.colorPrimary);
                    recyclerView.removeAllViews();
                    Toast.makeText(getActivity(), "No Record Found", Toast.LENGTH_SHORT).show();

                }

                mAdapter=new CholesterolAdapter(getActivity(),cholesterolArrayList);
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

   /* private void deleteCholesterolRecord(CholesterolRecord record) {

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


    }*/


    private void cleardata(){

        Log.d("arraylistbefore", ""+cholesterolArrayList.size());
        mAdapter = new CholesterolAdapter(getActivity(), cholesterolArrayList);

        cholesterolArrayList.clear();
        Log.d("arraylistafter", ""+cholesterolArrayList.size());
        mAdapter.notifyDataSetChanged();
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
            int uid = (int) Prefs.getLong("user_id",0);

            paramObject.put("userid", uid);
            paramObject.put("hdl", hdl);
            paramObject.put("ldl", ldl);
            paramObject.put("triglycerides", triglycerides);
            paramObject.put("date", date);
            paramObject.put("time", time);

            Call<ServerResponse<String>> call = apiInterface.addCholesterolRecord("abc", paramObject.toString());
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
                                getCholesterolRecords(uid);
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


