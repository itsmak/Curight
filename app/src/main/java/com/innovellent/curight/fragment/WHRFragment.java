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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.innovellent.curight.R;
import com.innovellent.curight.adapter.BloodPressureAdapter;
import com.innovellent.curight.adapter.WHRAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.AddWHRDialog;
import com.innovellent.curight.model.BMIDayWise;
import com.innovellent.curight.model.BMIRecord;
import com.innovellent.curight.model.BMIReport;
import com.innovellent.curight.model.BloodPressure;
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
    GraphView line_graph;
    ServerResponseWHRGet serverResponseWHRGet;
    ArrayList<WHR_LIST_DATE> whr_list_dates = new ArrayList<WHR_LIST_DATE>();
    ArrayList<WHR_LIST> whr_listArrayList = new ArrayList<WHR_LIST>();
    ArrayList<WhrList> whrLists = new ArrayList<WhrList>();
    WHR_LIST_DATE daywise;
    WhrList report;
    WHR_LIST record;
    ProgressDialog progressDialog;
    private long userId;
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
        ivAdd=(ImageView)rootView.findViewById(R.id.ivAdd);
        btnStatus_whr = (Button)rootView.findViewById(R.id.btnStatus_whr);
        rlGraph=(RelativeLayout) rootView.findViewById(R.id.rlGraph);
        cvCard=(CardView)rootView.findViewById(R.id.cvCard);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view);
        line_graph = (GraphView)rootView.findViewById(R.id.graphLine);
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
        addWHRDialog = new AddWHRDialog(getActivity(), new AddWHRDialog.AddWHRDialogClickListener() {
            @Override
            public void onSubmit(String date, String height, String weight) {
                addWHRDialog.dismiss();
                showProgressDialog("Adding");
                addWHRRecord(date, height, weight);
            }

            @Override
            public void onCancel() {
                addWHRDialog.dismiss();
            }
        });
        addWHRDialog.show();


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
                               // getwhrlistData();
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


        //final WHRGetCenter centre = new WHRGetCenter(7);

            try{
               /* JSONObject paramObject = new JSONObject();
                paramObject.put("userid", 7);*/

                ParameterPojo parameterPojo = new ParameterPojo(7);

                Call<ServerResponseWHRGet> call = apiInterface.getwhrlistdata(parameterPojo);

                call.enqueue(new Callback<ServerResponseWHRGet>() {
                    @Override
                    public void onResponse(Call<ServerResponseWHRGet> call, Response<ServerResponseWHRGet> response) {
                        ServerResponseWHRGet serverResponse = response.body();
                         report = serverResponse.getResults();
                         whr_list_dates = report.getWhrdtoList();
                        if(response.body()!=null){

                            String whrflag = report.getWhrFlag();
                            Log.d("whrflag", whrflag);

                            if (report.getWhrFlag() != null){
                                switch (report.getWhrFlag()) {
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

                            for(int i=0; i<whr_list_dates.size(); i++){

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


                            }

                            /*for(int i=0; i<report.getWhrdtoList().size(); i++){

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
                            }*/
                            mAdapter=new WHRAdapter(getActivity(),whr_list_dates,whr_listArrayList);
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



    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {

            case R.id.ivAdd:
                addWHR();
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



