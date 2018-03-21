package com.innovellent.curight.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import com.innovellent.curight.adapter.BloodPressureAdapter;
import com.innovellent.curight.adapter.BloodSugarAdapter;
import com.innovellent.curight.adapter.CholesterolAdapter;
import com.innovellent.curight.adapter.PROFILE_SPINNER_ADAPTER;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.AddBloodSugarDialog;
import com.innovellent.curight.model.BloodPressure;
import com.innovellent.curight.model.BloodSugar;
import com.innovellent.curight.model.BloodsugarPojo;
import com.innovellent.curight.model.MyProfile_Response;
import com.innovellent.curight.model.PROFILE;
import com.innovellent.curight.model.PROFILE_FEED;
import com.innovellent.curight.model.PostBodyProfile;
import com.innovellent.curight.model.ServerResponse;
import com.innovellent.curight.model.ServerResponseBloodSugar;
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
 * Created by sagar on 8/31/2017.
 */

public class BloodSugarFragment extends Fragment implements View.OnClickListener{

    LinearLayout llStatus;
    Button btnStatus;

    TextView tvBP,tvList,tvTrends;
    ImageView ivBmi,ivAdd,ivBloosSugar;
    RelativeLayout rlStatus;
    CardView cvCard;
    RecyclerView recyclerView;
    GraphView line_graph;
    BloodSugarAdapter mAdapter;
    AddBloodSugarDialog addBloodSugarDialog;
    ArrayList<BloodSugar> bloodSugarArrayList = new ArrayList<BloodSugar>();
    ArrayList<BloodPressure> arrayList=new ArrayList<BloodPressure>();
    int i;
    int uid;
    ProgressDialog progressDialog;
     RelativeLayout rlGraph;
    String USER_ID;
    TextView txt_beforemeal,txt_aftermeal;
    ArrayList<PROFILE> spinnerList=new ArrayList<PROFILE>();
    PROFILE_SPINNER_ADAPTER customSpinnerAdapter3;
    JSONArray jsonarray_parent,jsonarray_child;
    Spinner spUser;
    public BloodSugarFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_blood_sugar, container, false);
        initReferences(rootView);
        initOnClick();

        spinnerList.clear();
        getSpinnerData();
       // setBlue();
        //getData();
       /* i = 0;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(i != 105) {

                    if((i<50)&&(i>25)){
                        setGreen();
                    }else if((i<75)&&(i>50)){
                        setYellow();
                    }else if(i>75){
                        setRed();
                    }
                    i=i+5;


                    handler.postDelayed(this, 1000);
                } else {
                    handler.removeCallbacks(this);

                }
            }
        }, 1000);



        LineGraphSeries<DataPoint> line_series =
                new LineGraphSeries<DataPoint>(new DataPoint[] {
                        new DataPoint(0, 0),
                        new DataPoint(0, 0),
                        new DataPoint(0, 0),
                        new DataPoint(0, 0),
                        new DataPoint(0, 0),

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

        line_graph.getViewport().setScrollable(false);
*/

        return rootView;

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



    public void setGreen(){
        ivBloosSugar.setBackgroundResource(R.mipmap.ic_fastgreen);
        //  llStatus.setBackgroundColor(Color.parseColor("#C0F3AD"));
        btnStatus.setBackgroundColor(Color.parseColor("#6ADEB6"));
        // btnStatus.setTextColor(Color.parseColor("#72C852"));
        btnStatus.setText("Low Risk");
    }

    public void setYellow(){
        ivBloosSugar.setBackgroundResource(R.mipmap.ic_fastyellow);
        // llStatus.setBackgroundColor(Color.parseColor("#FFDC75"));
        btnStatus.setBackgroundColor(Color.parseColor("#FFC300"));
        //btnStatus.setTextColor(Color.parseColor("#EBC75B"));
        btnStatus.setText("Medium Risk");
    }
    public void setBlue(){
        ivBloosSugar.setBackgroundResource(R.mipmap.ic_fastblue);
        //  llStatus.setBackgroundColor(Color.parseColor("#CEDEFF"));
        btnStatus.setBackgroundColor(Color.parseColor("#0075b2"));
        //  btnStatus.setTextColor(Color.parseColor("#8EA6DC"));
        btnStatus.setText("Normal");
    }
    public void setRed(){
        ivBloosSugar.setBackgroundResource(R.mipmap.ic_fastred);
        // llStatus.setBackgroundColor(Color.parseColor("#CEDEFF"));
        btnStatus.setBackgroundColor(Color.parseColor("#FE5757"));
        // btnStatus.setTextColor(Color.parseColor("#8EA6DC"));
        btnStatus.setText("High Risk");
    }


    public void initReferences(View rootView) {
        tvBP=(TextView)rootView.findViewById(R.id.tvBP);
        ivBloosSugar=(ImageView)rootView.findViewById(R.id.ivbloodsugar);
        llStatus=(LinearLayout)rootView.findViewById(R.id.llStatus);
        btnStatus=(Button)rootView.findViewById(R.id.btnStatus);
        tvTrends=(TextView)rootView.findViewById(R.id.tvTrends);
        tvList=(TextView)rootView.findViewById(R.id.tvList);
        txt_aftermeal = (TextView)rootView.findViewById(R.id.txt_aftermeal);
        txt_beforemeal = (TextView)rootView.findViewById(R.id.txt_beforemeal);
        ivAdd=(ImageView)rootView.findViewById(R.id.ivAdd);
        rlGraph=(RelativeLayout) rootView.findViewById(R.id.rlGraph);
        cvCard=(CardView)rootView.findViewById(R.id.cvCard);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view);
        line_graph = (GraphView)rootView.findViewById(R.id.graphLine);
        spUser = (Spinner) rootView.findViewById(R.id.spUser);

    }




    private void AddBPRecords() {
        addBloodSugarDialog = new AddBloodSugarDialog(getActivity(), new AddBloodSugarDialog.AddBloodSugarDialogClickListener(){

            @Override
            public void onSubmit(String Aftermeal,String Beforemeal,String Date) {
                addBloodSugarDialog.dismiss();
                showProgressDialog("Adding");
                addbloodsugardata(Aftermeal,Beforemeal,Date);

            }

            @Override
            public void onCancel() {
                addBloodSugarDialog.dismiss();
            }
        });

        addBloodSugarDialog.show();


    }


    private void addbloodsugardata(String Aftermeal,String Beforemeal,String Date){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        try{
            JSONObject paramObject = new JSONObject();
            int uid = (int) Prefs.getLong("spinner_id",0);
            Log.d(TAG,"patient id ::"+uid);
            paramObject.put("userid", uid);
            paramObject.put("aftermeal", Aftermeal);
            paramObject.put("beforemeal", Beforemeal);
            paramObject.put("date", Date);

            Log.d("bloodsugar_valueuid", ""+uid);
            Log.d("aftermeal", Aftermeal);
            Log.d("Beforemeal", Beforemeal);
            Log.d("Date", Date);

            Call<ServerResponseBloodSugar<String>> call = apiInterface.addbloodsugarrecord("abc", paramObject.toString());

            call.enqueue(new Callback<ServerResponseBloodSugar<String>>() {
                @Override
                public void onResponse(Call<ServerResponseBloodSugar<String>> call, Response<ServerResponseBloodSugar<String>> response) {
                    if (getActivity() != null) {
                        progressDialog.dismiss();
                        if(response.isSuccessful()){
                            ServerResponseBloodSugar<String> serverResponseBloodSugar = response.body();
                            if (serverResponseBloodSugar.getResults().equals("Success")) {
                                Log.d("blooadsugaradd_response", serverResponseBloodSugar.getResults());
                                Toast.makeText(getActivity(), "Successfully Added", Toast.LENGTH_SHORT).show();
                                showProgressDialog("Loading");
                                int uid = (int) Prefs.getLong("spinner_id",0);
                                getBloodSugarData(uid);
                                progressDialog.dismiss();
                            }else
                                Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServerResponseBloodSugar<String>> call, Throwable t) {
                    Log.d(CURIGHT_TAG, t.getMessage());
                    if (getActivity() != null) progressDialog.dismiss();
                }
            });
        }catch (Exception e){
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
                int uid = (int) Prefs.getLong("spinner_id",0);
                Log.d(TAG, "Myuserid on select" + uid);

                getBloodSugarData(uid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                int uid = (int) Prefs.getLong("user_id",0);
                Log.d("user_forbloodsugar", ""+uid);
                Log.d(TAG, "MyUSER_ID on spinner" + USER_ID);
                getBloodSugarData(uid);
            }
        });

    }
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {

            case R.id.ivAdd:
                AddBPRecords();
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

    public void getBloodSugarData(int user_id){
        cleardata();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        BloodsugarPojo bloodsugarPojo = new BloodsugarPojo(user_id);

        Call<ResponseBody> call = apiInterface.getbloodsugardata("abc", bloodsugarPojo);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.body() != null){

                    try{
                        String res_data = response.body().string();
                        List<DataPoint> points = new ArrayList<>();
                        List<DataPoint> points2 = new ArrayList<>();
                        Log.e("res_dataforbloodsugar", res_data);

                        JSONObject jsonObject = new JSONObject(res_data);
                        String code = jsonObject.getString("Code");

                        JSONObject jsonObject1 = jsonObject.getJSONObject("Results");
                        String bloodsugar_flag = jsonObject1.getString("bsFlag");
                        if (bloodsugar_flag != null){
                            switch (bloodsugar_flag) {
                                case "low":
                                    setYellow();
                                    break;
                                case "high":
                                    setRed();
                                    break;
                                case "normal":
                                    setGreen();
                                    break;
                                default:
                                    setYellow();
                                    break;
                            }

                        }

                        int Beforemeal = jsonObject1.getInt("beforemeal");
                        int Aftermeal = jsonObject1.getInt("aftermeal");

                        txt_beforemeal.setText(String.valueOf(Beforemeal));
                        txt_aftermeal.setText(String.valueOf(Aftermeal));

                        jsonarray_parent = jsonObject1.getJSONArray("bsList");
                        for(int i=0; i<jsonarray_parent.length(); i++){

                            jsonObject1 = jsonarray_parent.getJSONObject(i);

                            jsonarray_child = jsonObject1.getJSONArray("bsList");

                            for(int j=0;j<jsonarray_child.length();j++){
                                bloodSugarArrayList.add(new BloodSugar(jsonarray_child.getJSONObject(j).getInt("aftermeal"),jsonarray_child.getJSONObject(j).getInt("beforemeal"),jsonarray_child.getJSONObject(j).getInt("bsid"),jsonarray_child.getJSONObject(j).getString("bsflag"),jsonarray_parent.getJSONObject(i).getString("date"),jsonarray_child.getJSONObject(j).getString("graphflag")));

                                String graphflag = jsonarray_child.getJSONObject(j).getString("graphflag");

                                if(graphflag.equalsIgnoreCase("Y")){

                                    double beforemeal = jsonarray_child.getJSONObject(j).getInt("beforemeal");
                                    double aftermeal = jsonarray_child.getJSONObject(j).getInt("aftermeal");


                                    points.add(new DataPoint(i, beforemeal));
                                    points2.add(new DataPoint(i, aftermeal));
                                }
                                //points.add(new DataPoint(Double.parseDouble(String.valueOf(jsonarray_child.getJSONObject(j).getInt("aftermeal"))),Double.parseDouble(String.valueOf(jsonarray_child.getJSONObject(j).getInt("beforemeal")))));
                                //Log.d("bloodsugargrapghvalue",""+Double.parseDouble(String.valueOf(jsonarray_child.getJSONObject(j).getInt("aftermeal"))) +""+Double.parseDouble(String.valueOf(jsonarray_child.getJSONObject(j).getInt("beforemeal"))));



                            }
                        }
                        DataPoint[] pointArray = new DataPoint[points.size()];
                        DataPoint[] pointArray2 = new DataPoint[points2.size()];
                        line_graph.removeAllSeries();
                        line_graph.addSeries(new LineGraphSeries<>(points.toArray(pointArray)));
                        line_graph.addSeries(new LineGraphSeries<>(points2.toArray(pointArray2)));
                        // lineGraph = new GraphView(getActivity());
                        line_graph.getViewport().setMinX(0);
                        line_graph.getViewport().setMinY(0);
                        line_graph.getViewport().setScrollable(false);


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    txt_beforemeal.setText("");
                    txt_aftermeal.setText("");
                    ivBloosSugar.setBackgroundResource(R.mipmap.ic_statscopered);
                    btnStatus.setText("");
                    line_graph.removeAllSeries();
                    btnStatus.setBackgroundResource(R.color.pink);
                    recyclerView.removeAllViews();
                    Toast.makeText(getActivity(), "No Record Found", Toast.LENGTH_SHORT).show();
                }

                mAdapter=new BloodSugarAdapter(getActivity(),bloodSugarArrayList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(mAdapter);
                cvCard.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: Somethings went wrong" + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void cleardata(){

        Log.d("arraylistbefore", ""+bloodSugarArrayList.size());
        mAdapter = new BloodSugarAdapter(getActivity(), bloodSugarArrayList);

        bloodSugarArrayList.clear();
        Log.d("arraylistafter", ""+bloodSugarArrayList.size());
        mAdapter.notifyDataSetChanged();
    }

    private void showProgressDialog(String title) {
        progressDialog = ProgressDialog.show(getContext(), title, "please wait", true, false);
        progressDialog.show();
    }
}



