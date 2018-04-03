package com.innovellent.curight.fragment;


import android.app.ProgressDialog;
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
import com.innovellent.curight.adapter.TRACK_SPINNER_ADAPTER;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.AddBPRecordsDialog;
import com.innovellent.curight.model.BP;
import com.innovellent.curight.model.MyProfile_Response;
import com.innovellent.curight.model.PROFILE;
import com.innovellent.curight.model.PROFILE_FEED;
import com.innovellent.curight.model.ParameterBP;
import com.innovellent.curight.model.PostBodyProfile;
import com.innovellent.curight.model.ServerResponse;
import com.innovellent.curight.utility.Config;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
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

import static com.innovellent.curight.utility.Constants.CURIGHT_TAG;


public class BPFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = ".Retro_MainActivity";
    ImageView iv_home_icon,iv_remainder_icon,iv_article_icon,iv_track_icon,iv_profile_icon;
    TextView tv_home_txt,tv_remainder_txt,tv_article_txt,tv_track_txt,tv_profile_txt;
    TextView tvBP, tvList, tvTrends;
    ImageView ivBmi;
    ImageView ivAdd;
    LinearLayout llStatus;
    Button btnStatus;
    CardView cvCard;
    RecyclerView recyclerView;
    BPAdapter mAdapter;
    GraphView lineGraph;
    TextView tv_locationtxt,tv_locationsymbl,tvTitle;
    LineGraphSeries lineGraphSeries;
    List<Object> arrayList = new ArrayList<>();
    ArrayList<BP> bp_arraylist = new ArrayList<BP>();
    AddBPRecordsDialog addRecordsDialog;
    RelativeLayout rlGraph;
    int i;
    String USER_ID,res_dataforbp;
    ArrayList<PROFILE> spinnerList=new ArrayList<PROFILE>();
    TRACK_SPINNER_ADAPTER customSpinnerAdapter3;
    JSONArray jsonArray_parent,jsonarray_child;
    int uid,position;
    Spinner spUser;
    double dates;
    RelativeLayout rl_location;
    private TextView systolicDiastolic, txt_pulse;
    private Long userId;
//    LineGraphSeries<DataPoint> series;
//    LineGraphSeries<DataPoint> series2;
    private String accessToken;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bp, container, false);
        initReferences(rootView);
        initOnClick();

        spinnerList.clear();
        getSpinnerData();

       /* SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", "");
        userId = sharedPreferences.getLong("user_id", 2L);
*/
       // showProgressDialog("Loading");
       // getBloodPressureRecords(USER_ID);
        rl_location.setVisibility(View.GONE);
        iv_home_icon.setImageResource(R.drawable.home_grey);
        iv_remainder_icon.setImageResource(R.drawable.reminder_grey);
        iv_article_icon.setImageResource(R.drawable.article_grey);
        iv_track_icon.setImageResource(R.drawable.track_blue);
        iv_profile_icon.setImageResource(R.drawable.profile_grey);

        tv_home_txt.setTextColor(Color.parseColor("#54666E"));
        tv_remainder_txt.setTextColor(Color.parseColor("#54666E"));
        tv_article_txt.setTextColor(Color.parseColor("#54666E"));
        tv_track_txt.setTextColor(Color.parseColor("#0B63F8"));
        tv_profile_txt.setTextColor(Color.parseColor("#54666E"));

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

        rl_location = (RelativeLayout) getActivity().findViewById(R.id.rl_location);
        iv_home_icon = (ImageView) getActivity().findViewById(R.id.iv_home_icon);
        iv_remainder_icon = (ImageView) getActivity().findViewById(R.id.iv_remainder_icon);
        iv_article_icon = (ImageView) getActivity().findViewById(R.id.iv_article_icon);
        iv_track_icon = (ImageView) getActivity().findViewById(R.id.iv_track_icon);
        iv_profile_icon = (ImageView) getActivity().findViewById(R.id.iv_profile_icon);
        tv_home_txt = (TextView) getActivity().findViewById(R.id.tv_home_txt);
        tv_remainder_txt = (TextView) getActivity().findViewById(R.id.tv_remainder_txt);
        tv_article_txt = (TextView) getActivity().findViewById(R.id.tv_article_txt);
        tv_track_txt = (TextView) getActivity().findViewById(R.id.tv_track_txt);
        tv_profile_txt = (TextView) getActivity().findViewById(R.id.tv_profile_txt);

        systolicDiastolic = (TextView) rootView.findViewById(R.id.systolic_diastolic);
        txt_pulse = (TextView) rootView.findViewById(R.id.pulse);
        //tvBP = (TextView) rootView.findViewById(R.id.tvBP);
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
        tv_locationtxt = (TextView) getActivity().findViewById(R.id.tv_locationtxt);
        tv_locationsymbl = (TextView) getActivity().findViewById(R.id.tv_locationsymbl);
        tvTitle = (TextView) getActivity().findViewById(R.id.tvTitle);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("Track");
        tv_locationtxt.setVisibility(View.GONE);
        tv_locationsymbl.setVisibility(View.GONE);
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

        customSpinnerAdapter3 = new TRACK_SPINNER_ADAPTER(getActivity(), spinnerList);
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
               getBloodPressureRecords(uid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                int uid = (int) Prefs.getLong("user_id",0);
                Log.d("user_forwhr", ""+uid);
                Log.d(TAG, "MyUSER_ID on spinner" + USER_ID);
                getBloodPressureRecords(uid);
            }
        });

    }


    public void getBloodPressureRecords(int user_id) {
            cleardata();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        ParameterBP parameterBP = new ParameterBP(user_id);

        Call<ResponseBody> call = apiInterface.getBloodPressureRecords(parameterBP);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    try{

                        res_dataforbp = response.body().string();
                        List<DataPoint> points = new ArrayList<>();
                        List<DataPoint> points2 = new ArrayList<>();
                       // graph.addSeries(series);
                        JSONObject jsonObject = new JSONObject(res_dataforbp);
                        String code = jsonObject.getString("Code");

                        JSONObject jsonObject1 = jsonObject.getJSONObject("Results");
                        int pulse = jsonObject1.getInt("pulse");
                        String systolic = jsonObject1.getString("systolic");
                        String diastolic = jsonObject1.getString("diastolic");

                        systolicDiastolic.setText(systolic+"/"+diastolic);
                        txt_pulse.setText(String.valueOf(pulse));
                        String bpFlag = jsonObject1.getString("bpFlag");
                        if (bpFlag != null){
                            switch (bpFlag) {
                                case "normal":
                                    setBlue();
                                    break;
                                case "low":
                                    setGreen();
                                    break;
                                case "prehigh":
                                    setYellow();
                                    break;
                                case "high":
                                    setRed();
                                    break;
                                default:
                                    setYellow();
                                    break;
                            }
                        }

                        jsonArray_parent = jsonObject1.getJSONArray("bpList");
                        Log.d(TAG,"Date array size::"+jsonArray_parent.length());
                        String datearray[] = new String[jsonArray_parent.length()];
                      //  series = new LineGraphSeries<>();
                        for(int i=0; i<jsonArray_parent.length(); i++){

                            //bp_arraylist.add(new BP(0,0,0,0,"",jsonArray_parent.getJSONObject(i).getString("date"),""));
                            jsonObject1 = jsonArray_parent.getJSONObject(i);
                            Log.d(TAG,"Date array element::"+jsonObject1.getString("date"));
                            jsonarray_child =jsonObject1.getJSONArray("bpList");


                            for(int j=0; j<jsonarray_child.length(); j++){
                                bp_arraylist.add(new BP(jsonArray_parent.getJSONObject(i).getString("date"),jsonarray_child.getJSONObject(j).getInt("bpid"),jsonarray_child.getJSONObject(j).getString("time"),jsonarray_child.getJSONObject(j).getString("graphflag"),jsonarray_child.getJSONObject(j).getInt("pulse"),jsonarray_child.getJSONObject(j).getInt("systolic"),jsonarray_child.getJSONObject(j).getInt("diastolic"),""));

//                                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
//                                        new DataPoint(0, 1)

//                                });

                                String graphflag = jsonarray_child.getJSONObject(j).getString("graphflag");

                                if(graphflag.equalsIgnoreCase("Y")){

                                    double systolic1 = jsonarray_child.getJSONObject(j).getInt("systolic");
                                    double diastolic1 = jsonarray_child.getJSONObject(j).getInt("diastolic");
                                    datearray[i] =jsonObject1.getString("date");

                                    points.add(new DataPoint(i, systolic1));
                                    points2.add(new DataPoint(i, diastolic1));

                                }

                            }
                        }

                        DataPoint[] pointArray = new DataPoint[points.size()];
                        DataPoint[] pointArray2 = new DataPoint[points2.size()];
                        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points.toArray(pointArray));
                        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(points2.toArray(pointArray2));
                        lineGraph.removeAllSeries();

                        series.setColor(Color.GREEN);
                        series.setThickness(5);


                        series2.setColor(Color.BLUE);
                        series2.setThickness(5);
                        series.setTitle("Systolic");
                        series2.setTitle("Diastolic");
                        lineGraph.getLegendRenderer().setVisible(true);
                        lineGraph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                        lineGraph.addSeries(series);
                        lineGraph.addSeries(series2);
//                        lineGraph.addSeries(new LineGraphSeries<>(points.toArray(pointArray)));
//                        lineGraph.addSeries(new LineGraphSeries<>(points2.toArray(pointArray2)));
                        StaticLabelsFormatter staticlebel = new StaticLabelsFormatter(lineGraph);
                       // staticlebel.setHorizontalLabels(new String[]{"18/12/12","18/07/12","18/10/43","18/12/11"});
                        staticlebel.setHorizontalLabels(datearray);
                        //staticlebel.setVerticalLabels(new String[] {"0","50","100","150"});
                        lineGraph.getGridLabelRenderer().setLabelFormatter(staticlebel);
                        // lineGraph = new GraphView(getActivity());

                        GridLabelRenderer gridLabel = lineGraph.getGridLabelRenderer();
                        // gridLabel.setHorizontalAxisTitle("systolic");
                        gridLabel.setVerticalAxisTitle("systolic/diastolic");

                        // set date label formatter
                        //lineGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
                        //lineGraph.getGridLabelRenderer().setNumHorizontalLabels(3);
                        lineGraph.getViewport().setMinX(0);
                        lineGraph.getViewport().setMinY(0);
                        lineGraph.getViewport().setXAxisBoundsManual(true);
                       // lineGraph.getGridLabelRenderer().setHumanRounding(false);
                        lineGraph.getViewport().setScrollable(true);
                        lineGraph.getViewport().setScalable(true);

                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }else{
                    systolicDiastolic.setText("");
                    txt_pulse.setText("");
                    lineGraph.removeAllSeries();
                    ivBmi.setBackgroundResource(R.mipmap.ic_bpyellow);
                    btnStatus.setText("");
                    btnStatus.setBackgroundResource(R.color.protein);
                    recyclerView.removeAllViews();
                    Toast.makeText(getActivity(), "No Record Found", Toast.LENGTH_SHORT).show();
                }
                mAdapter=new BPAdapter(getActivity(),bp_arraylist);
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
            int uid = (int) Prefs.getLong("spinner_id",0);
            paramObject.put("userid", uid);
            paramObject.put("time", time);
            paramObject.put("date", date);
            paramObject.put("pulse", pulse);
            paramObject.put("systolic", systolic);
            paramObject.put("diastolic", diastolic);

            Call<ServerResponse<String>> call = apiInterface.addBloodPressureRecord("abc", paramObject.toString());
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
                                int uid = (int) Prefs.getLong("spinner_id",0);
                                getBloodPressureRecords(uid);
                                progressDialog.dismiss();
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


    private void cleardata(){

        Log.d("arraylistbefore", ""+bp_arraylist.size());
        mAdapter = new BPAdapter(getActivity(), bp_arraylist);

        bp_arraylist.clear();
        Log.d("arraylistafter", ""+bp_arraylist.size());
        mAdapter.notifyDataSetChanged();
    }

    private void showProgressDialog(String title) {
        progressDialog = ProgressDialog.show(getContext(), title, "please wait", true, false);
        progressDialog.show();
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


