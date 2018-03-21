package com.innovellent.curight.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.innovellent.curight.R;
import com.innovellent.curight.adapter.BMIAdapter;
import com.innovellent.curight.adapter.BloodPressureAdapter;
import com.innovellent.curight.adapter.PROFILE_SPINNER_ADAPTER;
import com.innovellent.curight.adapter.WHRAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.AddWHRDialog;
import com.innovellent.curight.model.BMIDayWise;
import com.innovellent.curight.model.BMIRecord;
import com.innovellent.curight.model.BMIReport;
import com.innovellent.curight.model.BloodPressure;
import com.innovellent.curight.model.DeleteParameterPojo;
import com.innovellent.curight.model.JSON_FEED;
import com.innovellent.curight.model.MyProfile_Response;
import com.innovellent.curight.model.PROFILE;
import com.innovellent.curight.model.PROFILE_FEED;
import com.innovellent.curight.model.ParameterPojo;
import com.innovellent.curight.model.PhotosCenterByDC;
import com.innovellent.curight.model.PostBodyProfile;
import com.innovellent.curight.model.ServerResponse;
import com.innovellent.curight.model.ServerResponsePhotosByDC;
import com.innovellent.curight.model.ServerResponseWHRGet;
import com.innovellent.curight.model.VaccineList;
import com.innovellent.curight.model.WHR;
import com.innovellent.curight.model.WHRGetCenter;
import com.innovellent.curight.model.WHR_LIST;
import com.innovellent.curight.model.WHR_LIST_DATE;
import com.innovellent.curight.model.WhrList;
import com.innovellent.curight.utility.Config;
import com.innovellent.curight.utility.Constants;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
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

public class WHRFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "CuRight";
//    private static final String BASE_URL = "http://13.59.209.135:8090/diagnosticAPI/webapi/";
    public ImageView ivCancel;
    TextView tvBP,tvList,tvTrends;
    ImageView ivWhr,ivAdd,ivGraph;
    RelativeLayout rlStatus;
    RecyclerView recyclerView;
    WHRAdapter mAdapter;
    ArrayList<String> arrayList=new ArrayList<String>();
    CardView cvCard;
    AddWHRDialog addWHRDialog;
    LinearLayout llStatus;
    Button btnStatus_whr;
    int i;
    RelativeLayout rlGraph;
    GraphView lineGraph;
    ArrayList<WHR_LIST_DATE> arraylist_whr_list_dates = new ArrayList<WHR_LIST_DATE>();
    ArrayList<WHR_LIST> arraylist_whr_list = new ArrayList<WHR_LIST>();
    ArrayList<WhrList> arraylist_whrLists_final = new ArrayList<WhrList>();
    WHR_LIST_DATE daywise;
    WhrList report;
    WHR_LIST record;
    ProgressDialog progressDialog;
    JSONObject jsonObject2,jsonObject1;
    int whrid;
    String about;
    Context context;
    String USER_ID,res_data;
    ArrayList<PROFILE> spinnerList=new ArrayList<PROFILE>();
    ArrayList<WHR> whr_arraylist = new ArrayList<WHR>();
    PROFILE_SPINNER_ADAPTER customSpinnerAdapter3;
    Spinner spUser;
    int uid;
    ServerResponseWHRGet serverResponseWHRGet;
    JSONArray jsonArray_child;
    JSONArray jsonArray_parent;
    private long userId;
    private String date, waist, hip;
    private RelativeLayout dateButton, timeButton;
    private EditText etDate,  etWaist, etHip;
    private Button btnSubmit_whr;
    private DatePickerDialog datePickerDialog;
    public WHRFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_whr, container, false);

       // getwhrlistData(uid);

        initReferences(rootView);
        initOnClick();

        spinnerList.clear();
        getSpinnerData();
        /*initReferences(rootView);
        initOnClick();


        LineGraphSeries<DataPoint> line_series =
                new LineGraphSeries<DataPoint>(new DataPoint[] {
                        new DataPoint(0, 1),
                        new DataPoint(1, 3),
                        new DataPoint(2, 5),
                        new DataPoint(3, 6),
                        new DataPoint(4, 6),

                });
        line_graph.addSeries(line_series);

        // set the bound

        // set manual X bounds
        line_graph.getViewport().setXAxisBoundsManual(true);
        line_graph.getViewport().setMinX(0.5);
        line_graph.getViewport().setMaxX(3.5);

        // set manual Y bounds
        line_graph.getViewport().setYAxisBoundsManual(true);
        line_graph.getViewport().setMinY(0.5);
        line_graph.getViewport().setMaxY(8);

        line_graph.getViewport().setScrollable(false);*/


       /* tvList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlGraph.setVisibility(View.GONE);
                cvCard.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });*/

        return rootView;

    }


    public void setGreen() {
        ivWhr.setBackgroundResource(R.mipmap.ic_bmibodygreen);
        //  llStatus.setBackgroundColor(Color.parseColor("#C0F3AD"));
        btnStatus_whr.setBackgroundColor(Color.parseColor("#6ADEB6"));
        // btnStatus_whr.setTextColor(Color.parseColor("#72C852"));
        btnStatus_whr.setText("Low Risk");
    }

    public void setYellow() {
        ivWhr.setBackgroundResource(R.mipmap.ic_bmibodyyellow);
        // llStatus.setBackgroundColor(Color.parseColor("#FFDC75"));
        btnStatus_whr.setBackgroundColor(Color.parseColor("#FFC300"));
        //btnStatus_whr.setTextColor(Color.parseColor("#EBC75B"));
        btnStatus_whr.setText("Medium Risk");
    }

    public void setBlue() {
        ivWhr.setBackgroundResource(R.mipmap.ic_bmibodyblue);
        //  llStatus.setBackgroundColor(Color.parseColor("#CEDEFF"));
        btnStatus_whr.setBackgroundColor(Color.parseColor("#0075b2"));
        //  btnStatus_whr.setTextColor(Color.parseColor("#8EA6DC"));
        btnStatus_whr.setText("Normal");
    }

    public void setRed() {
        ivWhr.setBackgroundResource(R.mipmap.ic_body);
        // llStatus.setBackgroundColor(Color.parseColor("#CEDEFF"));
        btnStatus_whr.setBackgroundColor(Color.parseColor("#FE5757"));
        // btnStatus_whr.setTextColor(Color.parseColor("#8EA6DC"));
        btnStatus_whr.setText("High Risk");
    }


    private void showProgressDialog(String title) {
        progressDialog = ProgressDialog.show(getContext(), title, "please wait", true, false);
        progressDialog.show();
    }
    public void initReferences(View rootView) {


        llStatus=(LinearLayout)rootView.findViewById(R.id.llStatus);
        tvTrends=(TextView)rootView.findViewById(R.id.tvTrends);
        ivWhr = (ImageView) rootView.findViewById(R.id.ivWhr);
        tvList=(TextView)rootView.findViewById(R.id.tvList);
        tvBP = (TextView)rootView.findViewById(R.id.tvBP);
        ivAdd=(ImageView)rootView.findViewById(R.id.ivAdd);
        btnStatus_whr = (Button)rootView.findViewById(R.id.btnStatus_whr);
        rlGraph=(RelativeLayout) rootView.findViewById(R.id.rlGraph);
        cvCard=(CardView)rootView.findViewById(R.id.cvCard);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        lineGraph = (GraphView)rootView.findViewById(R.id.graphLine);
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
                //recyclerView.setAdapter(mAdapter);

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
//                String pos = (String) adapterView.getItemAtPosition(i);

                    //spinneritemselected(i);
                Prefs.putLong("spinner_id", Long.parseLong(spinnerList.get(i).getUser_id()));
                int uid = (int) Prefs.getLong("spinner_id",0);
                getwhrlistData(uid);
                //mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                int uid = (int) Prefs.getLong("user_id",0);
                Log.d("user_forwhr", ""+uid);
                Log.d(TAG, "MyUSER_ID on spinner" + USER_ID);
                getwhrlistData(uid);
            }
        });

    }



    public void getwhrlistData(int user_id){
        cleardata();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        ParameterPojo parameterPojo = new ParameterPojo(user_id);
      //  Call<MyServer_Response> call = reditapi.getData(postBodyClass);
        Call<ResponseBody> call = apiInterface.getwhrlistdata(parameterPojo);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.body() != null) {
                    Log.d(TAG, "onResponse: Server Response: " + response);

                   // ArrayList<WhrList> result = response.body().getResults();
                   // Log.d(TAG, "onResponse: code: " + result.size());



                    try{
                        res_data = response.body().string();
                        Log.e("res_data", res_data);
                        List<DataPoint> points = new ArrayList<>();
                        List<DataPoint> points2 = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(res_data);
                        String code = jsonObject.getString("Code");
                        Log.d("code==", code);

                        JSONObject jsonObject1 = jsonObject.getJSONObject("Results");
                        String whrflag_mystring = jsonObject1.getString("whrFlag");
                        if (whrflag_mystring != null){
                            switch (whrflag_mystring) {
                                case "OV":
                                    setBlue();
                                    break;
                                case "N":
                                    setGreen();
                                    break;
                                case "OB":
                                    setRed();
                                    break;
                                default:
                                    setYellow();
                                    break;
                            }
                        }
                        String whr = jsonObject1.getString("whr");
                        Log.d("whrflag", whrflag_mystring);
                        Log.d("whr", whr);

                        tvBP.setText(whr);


                        jsonArray_parent = jsonObject1.getJSONArray("whrdtoList");
                        for(int i=0; i<jsonArray_parent.length(); i++) {
                           // whr_arraylist.add(new WHR(jsonArray_parent.getJSONObject(i).getString("date"),0,"", "", "", ""));
                            jsonObject1 = jsonArray_parent.getJSONObject(i);
                            jsonArray_child = jsonObject1.getJSONArray("whrList");

                            for(int j=0; j<jsonArray_child.length(); j++){
                                whr_arraylist.add(new WHR(jsonArray_parent.getJSONObject(i).getString("date"),jsonArray_child.getJSONObject(j).getInt("whrid"),jsonArray_child.getJSONObject(j).getString("whr"),jsonArray_child.getJSONObject(j).getString("graphflag"),jsonArray_child.getJSONObject(j).getString("waistcircumference"),jsonArray_child.getJSONObject(j).getString("hipcircumference")));
                                //points.add(new DataPoint(j, Double.parseDouble(jsonArray_child.getJSONObject(j).getString("whr"))));


                                String graphflag = jsonArray_child.getJSONObject(j).getString("graphflag");

                                if(graphflag.equalsIgnoreCase("Y")){

                                    double waistcircumference = jsonArray_child.getJSONObject(j).getInt("waistcircumference");
                                    double hipcircumference = jsonArray_child.getJSONObject(j).getInt("hipcircumference");


                                    points.add(new DataPoint(i, waistcircumference));
                                    points2.add(new DataPoint(i, hipcircumference));
                                }
                            }
                        }


                        DataPoint[] pointArray = new DataPoint[points.size()];
                        DataPoint[] pointArray2 = new DataPoint[points2.size()];
                        lineGraph.removeAllSeries();
                        lineGraph.addSeries(new LineGraphSeries<>(points.toArray(pointArray)));
                        lineGraph.addSeries(new LineGraphSeries<>(points2.toArray(pointArray2)));
                        // lineGraph = new GraphView(getActivity());
                        lineGraph.getViewport().setMinX(0);
                        lineGraph.getViewport().setMinY(0);
                        lineGraph.getViewport().setScrollable(false);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    /*serverResponseWHRGet = (ServerResponseWHRGet)response.body();

                    Log.d(TAG, "CODE"+ serverResponseWHRGet.getCode());
                    Log.d(TAG, "WHRFLAG"+ serverResponseWHRGet.getResults());

                    int code = serverResponseWHRGet.getCode();

                    if("200".equals(String.valueOf(code))){
                        try {
                            JSONObject jsonObject = new JSONObject("Results");
                            String whrflag = jsonObject.getString("whrFlag");
                            Log.d(TAG, "whrflag" +whrflag);
                            String whr = jsonObject.getString("whr");
                            Log.d(TAG, "whr" +whr);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }*/
                    /*for (int i = 0; i < result.size(); i++) {

                        whr.add(new WHR(result.get(i).getWhrFlag(),"","","","",result.get(i).getWhr(),0,""));
                        ArrayList<WHR_LIST_DATE> vlist = result.get(i).getWhrdtoList();

                        for(int j=0; j<vlist.size(); j++){

                            whr.add(new WHR("",vlist.get(j).getDate(),"","","",0,0,""));

                            ArrayList<WHR_LIST> whrList = vlist.get(j).getWhrList();

                            for(int k=0; k<whrList.size(); k++){

                                whr.add(new WHR("","",whrList.get(k).getGraphflag(),whrList.get(k).getWaistcircumference(),whrList.get(k).getHipcircumference(),0,0, whrList.get(k).getWhr_subdata()));

                            }


                        }

                    }*/
                }else {

                    tvBP.setText("");
                    lineGraph.removeAllSeries();
                    ivWhr.setBackgroundResource(R.mipmap.ic_whrgreen);
                    btnStatus_whr.setText("");
                    btnStatus_whr.setBackgroundResource(R.color.carbs);

                    recyclerView.removeAllViews();
                    Toast.makeText(getActivity(), "No Record Found", Toast.LENGTH_SHORT).show();

                }
                mAdapter=new WHRAdapter(getActivity(),whr_arraylist);
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




    /*private void addWHR() {

        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.dialog_add_whr);

        getActivity().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dateButton = (RelativeLayout) dialog.findViewById(R.id.date_layout_whr);

        etDate = (EditText) dialog.findViewById(R.id.tv_date_whr);
        etWaist = (EditText)dialog. findViewById(R.id.waist_whr);
        etHip = (EditText) dialog.findViewById(R.id.hip_whr);
        btnSubmit_whr =(Button)dialog.findViewById(R.id.btnSubmit_whr);
        ivCancel = (ImageView)dialog.findViewById(R.id.ivCancel);

        final Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                etDate.setText(getContext().getString(R.string.date_formatted, year, month + 1, dayOfMonth));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        btnSubmit_whr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showProgressDialog("Adding");
                validateInputs();


            }
        });

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
*/

    private void showAddWHRDialog() {
        addWHRDialog = new AddWHRDialog(getActivity(), new AddWHRDialog.AddWHRDialogClickListener() {
            @Override
            public void onSubmit(String date, String height, String weight) {
                addWHRDialog.dismiss();
                showProgressDialog("Adding");
                addWHRRecord(date, height, weight);
                /*tvList.setTextColor(Color.parseColor("#9DA1A0"));
                rlGraph.setVisibility(View.GONE);
                cvCard.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(mAdapter);
                recyclerView.setVisibility(View.VISIBLE);*/

            }

            @Override
            public void onCancel() {
                addWHRDialog.dismiss();
            }
        });
        addWHRDialog.show();

    }
    //}


    private void addWHRRecord(String date, String height, String weight){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);


        try{
            final  int uid = (int) Prefs.getLong("spinner_id",0);
            Log.d("user_id_addwhr", ""+uid);
            JSONObject paramObject = new JSONObject();
            paramObject.put("userid", uid);
            paramObject.put("date", date);
            paramObject.put("hips", weight);
            paramObject.put("waist", height);


            Call<ServerResponse<String>> call = apiInterface.addWHRRecord("abc", paramObject.toString());


            call.enqueue(new Callback<ServerResponse<String>>() {
                @Override
                public void onResponse(Call<ServerResponse<String>> call, Response<ServerResponse<String>> response) {
                    if (getActivity() != null) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            ServerResponse<String> serverResponse = response.body();
                            Log.d("addwhrdialog_response", serverResponse.getResults());
                            if (serverResponse.getResults().equals("Success")) {
                                Toast.makeText(getActivity(), "Successfully Added", Toast.LENGTH_SHORT).show();
                                showProgressDialog("Loading");
                                int uid = (int) Prefs.getLong("user_id",0);
                                getwhrlistData(uid);
                                progressDialog.dismiss();
                            }else {
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

        }catch (Exception e){
            e.printStackTrace();
        }

    }





   /* private void validateInputs() {
        if (!(date = etDate.getText().toString().trim()).equals(""))

            if (!(waist = etWaist.getText().toString().trim()).equals(""))
                if (!(hip = etHip.getText().toString().trim()).equals(""))

                    addWHRRecord(date, waist, hip);


                else
                    Toast.makeText(context, "Please enter valid weight", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Please enter valid height", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Please enter valid date", Toast.LENGTH_SHORT).show();

    }*/

   /* void onSubmit(String date,String height,String weight){

        showProgressDialog("Adding");
        addWHRRecord(date, height, weight);

    }*/












    private void cleardata(){
            Log.d("arraylistbefore", ""+whr_arraylist.size());
        mAdapter = new WHRAdapter(getActivity(), whr_arraylist);

        whr_arraylist.clear();
        Log.d("arraylistafter", ""+whr_arraylist.size());
        mAdapter.notifyDataSetChanged();


    }





    @Override
    public void onClick(View v) {

        switch (v.getId())
        {

            case R.id.ivAdd:
                showAddWHRDialog();
                /*tvList.setTextColor(Color.parseColor("#9DA1A0"));
                rlGraph.setVisibility(View.GONE);
                cvCard.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.VISIBLE);*/
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



