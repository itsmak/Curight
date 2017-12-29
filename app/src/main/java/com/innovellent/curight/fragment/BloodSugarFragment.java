package com.innovellent.curight.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.BloodPressureAdapter;
import com.innovellent.curight.model.AddBloodSugarDialog;
import com.innovellent.curight.model.BloodPressure;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

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
    BloodPressureAdapter mAdapter;
    AddBloodSugarDialog addBloodSugarDialog;
    ArrayList<BloodPressure> arrayList=new ArrayList<BloodPressure>();
    int i;
     RelativeLayout rlGraph;
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
        setBlue();
        getData();
        i = 0;
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
        ivAdd=(ImageView)rootView.findViewById(R.id.ivAdd);
        rlGraph=(RelativeLayout) rootView.findViewById(R.id.rlGraph);
        cvCard=(CardView)rootView.findViewById(R.id.cvCard);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view);
        line_graph = (GraphView)rootView.findViewById(R.id.graphLine);

    }


    public void getData(){
//        arrayList.add(new BloodPressure("197/23","92"));
//        arrayList.add(new BloodPressure("127/53","42"));
//        arrayList.add(new BloodPressure("177/63","81"));
//        arrayList.add(new BloodPressure("183/12","55"));
//        arrayList.add(new BloodPressure("162/33","64"));
//        arrayList.add(new BloodPressure("132/33","53"));
//        arrayList.add(new BloodPressure("163/33","63"));
//        arrayList.add(new BloodPressure("152/63","91"));
//        arrayList.add(new BloodPressure("122/23","32"));
//        arrayList.add(new BloodPressure("162/73","61"));
        mAdapter=new BloodPressureAdapter(getActivity(),arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);
        cvCard.setVisibility(View.VISIBLE);

    }

    private void AddBPRecords() {
        addBloodSugarDialog = new AddBloodSugarDialog(getActivity(), new AddBloodSugarDialog.AddBloodSugarDialogClickListener(){

            @Override
            public void onSubmit() {
                addBloodSugarDialog.dismiss();
            }

            @Override
            public void onCancel() {
                addBloodSugarDialog.dismiss();
            }
        });

        addBloodSugarDialog.show();


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
}



