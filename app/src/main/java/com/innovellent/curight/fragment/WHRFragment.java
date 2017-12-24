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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.innovellent.curight.R;
import com.innovellent.curight.adapter.BMIAdapter;
import com.innovellent.curight.adapter.BloodPressureAdapter;
import com.innovellent.curight.adapter.WHRAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.AddWHRDialog;
import com.innovellent.curight.model.BMIDayWise;
import com.innovellent.curight.model.BMIRecord;
import com.innovellent.curight.model.BMIReport;
import com.innovellent.curight.model.BloodPressure;
import com.innovellent.curight.model.DeleteParameterPojo;
import com.innovellent.curight.model.ParameterPojo;
import com.innovellent.curight.model.PhotosCenterByDC;
import com.innovellent.curight.model.ServerResponse;
import com.innovellent.curight.model.ServerResponsePhotosByDC;
import com.innovellent.curight.model.ServerResponseWHRGet;
import com.innovellent.curight.model.WHRGetCenter;
import com.innovellent.curight.model.WHR_LIST;
import com.innovellent.curight.model.WHR_LIST_DATE;
import com.innovellent.curight.model.WhrList;
import com.innovellent.curight.utility.Config;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

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

import static com.innovellent.curight.utility.Constants.CURIGHT_TAG;

/**
 * Created by sagar on 10/1/2017.
 */

public class WHRFragment extends Fragment implements View.OnClickListener{

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
    ServerResponseWHRGet serverResponseWHRGet;
    ArrayList<WHR_LIST_DATE> arraylist_whr_list_dates = new ArrayList<WHR_LIST_DATE>();
    ArrayList<WHR_LIST> arraylist_whr_list = new ArrayList<WHR_LIST>();
    ArrayList<WhrList> arraylist_whrLists_final = new ArrayList<WhrList>();
    WHR_LIST_DATE daywise;
    WhrList report;
    WHR_LIST record;
    ProgressDialog progressDialog;
    private long userId;
    JSONObject jsonObject2;
    int whrid;
    String about;
    private String date, waist, hip;
    private RelativeLayout dateButton, timeButton;
    private EditText etDate,  etWaist, etHip;
    private Button btnSubmit_whr;
    Context context;
    public ImageView ivCancel;
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

        getwhrlistData();

        initReferences(rootView);
        initOnClick();
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
        lineGraph = (GraphView)rootView.findViewById(R.id.graphLine);
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





    private void addWHR() {

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



       /* addWHRDialog = new AddWHRDialog(getActivity(), new AddWHRDialog.AddWHRDialogClickListener() {
            @Override
            public void onSubmit(String date, String height, String weight) {
                addWHRDialog.dismiss();
                showProgressDialog("Adding");
                addWHRRecord(date, height, weight);
                tvList.setTextColor(Color.parseColor("#9DA1A0"));
                rlGraph.setVisibility(View.GONE);
                cvCard.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(mAdapter);
                recyclerView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancel() {
                addWHRDialog.dismiss();
            }
        });
        addWHRDialog.show();*/


    }


    private void validateInputs() {
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

    }

   /* void onSubmit(String date,String height,String weight){

        showProgressDialog("Adding");
        addWHRRecord(date, height, weight);

    }*/





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


    private void addWHRRecord(String date, String height, String weight){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);


        try{

            JSONObject paramObject = new JSONObject();
            paramObject.put("userid", 7);
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
                                getwhrlistData();
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



    private void getwhrlistData(){
        /*arrayList.add(new BloodPressure("197/23","92"));
        arrayList.add(new BloodPressure("127/53","42"));
        arrayList.add(new BloodPressure("177/63","81"));
        arrayList.add(new BloodPressure("183/12","55"));
        arrayList.add(new BloodPressure("162/33","64"));
        arrayList.add(new BloodPressure("132/33","53"));
        arrayList.add(new BloodPressure("163/33","63"));
        arrayList.add(new BloodPressure("152/63","91"));
        arrayList.add(new BloodPressure("122/23","32"));
        arrayList.add(new BloodPressure("162/73","61"));*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        ParameterPojo parameterPojo = new ParameterPojo(7);
        final Call<ResponseBody> call = apiInterface.getwhrlistdata("abc", parameterPojo);


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {

                        String res_data = response.body().string();
                        Log.e("res_data", res_data);
                        List<DataPoint> points = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(res_data);

                        String code = jsonObject.getString("Code");
                        Log.d("code==", code);

                        if("200".equals(code)) {

                            JSONObject jsonObject1 = jsonObject.getJSONObject("Results");
                            String whrflag_mystring = jsonObject1.getString("whrFlag");
                            Log.d("whrflag", whrflag_mystring);

                            tvBP.setText(jsonObject1.getString("whr"));
                            if (jsonObject1.getString("whrFlag") != null) {
                                switch (jsonObject1.getString("whrFlag")) {
                                    case "U":
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

                            JSONArray jsonArray_parent = jsonObject1.getJSONArray("whrdtoList");

                            for (int i = 0; i < jsonArray_parent.length(); i++) {


                                // String date = jsonObject1.getString("date");
                                // Log.d("date", date);

                                daywise = new WHR_LIST_DATE();
                                jsonObject1 = jsonArray_parent.getJSONObject(i);

                                daywise.setDate(jsonObject1.getString("date"));

                                arraylist_whr_list_dates.add(daywise);


                                JSONArray jsonArray_child = jsonObject1.getJSONArray("whrList");
                                for (int j = 0; j < jsonArray_child.length(); j++) {

                                    //jsonArray_child = jsonObject1.getJSONArray("whrList");
                                    record = new WHR_LIST();
                                    jsonObject2 = jsonArray_child.getJSONObject(j);

                                    // String waist = jsonObject2.getString("waistcircumference");
                                    // Log.d("waist", waist);
                                    whrid = Integer.parseInt(jsonObject2.getString("whrid"));
                                    record.setWhr(jsonObject2.getString("whr"));
                                    record.setGraphflag(jsonObject2.getString("graphflag"));
                                    record.setWaistcircumference(jsonObject2.getString("waistcircumference"));
                                    record.setHipcircumference(jsonObject2.getString("hipcircumference"));
                                    record.setWhrid(Integer.parseInt(jsonObject2.getString("whrid")));

                                    arraylist_whr_list.add(record);
                                    points.add(new DataPoint(i, Double.parseDouble(record.getWhr())));

                                }

                               // arraylist_whr_list.addAll(daywise.getWhrList());

                            }

                            DataPoint[] pointArray = new DataPoint[arraylist_whr_list.size()];
                            lineGraph.removeAllSeries();
                            lineGraph.addSeries(new LineGraphSeries<>(points.toArray(pointArray)));
                            lineGraph = new GraphView(getActivity());
                            lineGraph.getViewport().setMinX(0);
                            lineGraph.getViewport().setMinY(0);
                            lineGraph.getViewport().setScrollable(false);


                           /* mAdapter = new WHRAdapter(getActivity(), arraylist_whr_list_dates, arraylist_whr_list, new WHRAdapter.OnWHRListener() {
                                @Override
                                public void onDelete(int record) {
                                    showProgressDialog("Deleting");
                                    Log.d("whrid", "" + whrid);
                                    DeleteWhrData(whrid);
                                }
                            });*/
                                mAdapter=new WHRAdapter(getActivity(),arraylist_whr_list_dates,arraylist_whr_list);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                recyclerView.setAdapter(mAdapter);


                        }else if("403".equals(code)) {

                            Toast.makeText(getActivity(), "No Record Found", Toast.LENGTH_SHORT).show();
                            /*try{
                                String res_datanotfound = response.body().string();
                                JSONObject jsonObject1 = new JSONObject(res_datanotfound);

                                String code_403 = jsonObject1.getString("Code");
                                String result_403 = jsonObject1.getString("Results");

                                if(code_403.equals("403")){
                                    Toast.makeText(getActivity(), "No Record Found", Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }*/

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


        //final WHRGetCenter centre = new WHRGetCenter(7);

           /* try{
               *//* JSONObject paramObject = new JSONObject();
                paramObject.put("userid", 7);*//*

                ParameterPojo parameterPojo = new ParameterPojo(7);

                Call<ServerResponseWHRGet> call = apiInterface.getwhrlistdata(parameterPojo);

                call.enqueue(new Callback<ServerResponseWHRGet>() {
                    @Override
                    public void onResponse(Call<ServerResponseWHRGet> call, Response<ServerResponseWHRGet> response) {

                        if(response.body()!=null){

                            ServerResponseWHRGet serverResponse = response.body();
                            WhrList whrList = serverResponse.getResults();
                            arraylist_whr_list_dates = whrList.getWhrdtoList();

//                            String whrflag = report.getWhrFlag();
                           // Log.d("whrflag", whrflag);

                            if (whrList.getWhrFlag() != null){
                                switch (whrList.getWhrFlag()) {
                                    case "U":
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

                            for(int i =0; i<arraylist_whr_list_dates.size(); i++){
                                arraylist_whr_list = arraylist_whr_list_dates.get(i).getWhrList();
                                long L = arraylist_whr_list.size();


                                for(int j =0; j<L; j++){
                                    arraylist_whr_list.add(arraylist_whr_list_dates.get(i).getWhrList().get(j));
                                }

                            }



                       *//*     for(int i=0; i<whr_list_dates.size(); i++){

                                whr_listArrayList=whr_list_dates.get(i).getWhrList();
                                long long1 = whr_listArrayList.size();
                              // ArrayList<String> date6 = report.getWhrdtoList().get(i).getDate();
                                String date = report.getWhrdtoList().get(i).getDate();
                                Log.d("date", date);

                                whr_list_dates.add(daywise);


                               // whr_listArrayList = report.getWhrdtoList().get(i).getWhrList();

                                for(int j = 0; j<long1; j++){
                                   // record = new WHR_LIST();
                                    //record = whr_listArrayList.get(j);

                                    whr_listArrayList.add(whr_list_dates.get(i).getWhrList().get(j));


//                                    String waistcircumfernce = record.getWaistcircumference();
                                   // Log.d("waistcircumfernce", waistcircumfernce);

                                    //String hip = record.getHipcircumference();
                                    //Log.d("hip", hip);


                                }


                            }*//*

                            *//*for(int i=0; i<report.getWhrdtoList().size(); i++){

                                report.getWhrdtoList().get(i);
                                String date = report.getWhrdtoList().get(i).getDate();
                                Log.d("date", date);

                                whrLists.add(report);
                                //whr_listArrayList = daywise.getWhrList();
//                                record = whr_listArrayList.get(i);
                                whrLists = daywise.getWhrList();
                                for (int j=0; j<whr_list_dates.size(); j++) {
                                   // record = report.getWhrList().get(j);
//                                    daywise.getWhrList().get(i);
                                    String waistcircumfernce = daywise.getWhrList().get(i).getWaistcircumference();
                                    Log.d("waistcircumfernce", waistcircumfernce);
                                    //whr_list_dates.add(daywise);
                                  //  String waistcircumfernce = record.getWaistcircumference();
                                    //Log.d("waistcircumfernce", waistcircumfernce);

                                    WHR_LIST whr_list = whr_list_dates.get(j);


                                }

                                whr_listArrayList.add(record);
                            }*//*
                            mAdapter=new WHRAdapter(getActivity(),arraylist_whr_list_dates,arraylist_whr_list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            recyclerView.setAdapter(mAdapter);


                        }


                }

                    @Override
                    public void onFailure(Call<ServerResponseWHRGet> call, Throwable t) {

                    }


                });
            }catch (Exception e){
                e.printStackTrace();
            }
*/


    }





    @Override
    public void onClick(View v) {

        switch (v.getId())
        {

            case R.id.ivAdd:
                addWHR();
                tvList.setTextColor(Color.parseColor("#9DA1A0"));
                rlGraph.setVisibility(View.GONE);
                cvCard.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.VISIBLE);
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



