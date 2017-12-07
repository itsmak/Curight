package com.innovellent.curight.fragment;

import android.graphics.Color;
import android.os.Bundle;
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
import com.innovellent.curight.model.AddWHRDialog;
import com.innovellent.curight.model.BloodPressure;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

/**
 * Created by sagar on 10/1/2017.
 */

public class WHRFragment extends Fragment implements View.OnClickListener{

    TextView tvBP,tvList,tvTrends;
    ImageView ivBmi,ivAdd,ivGraph;
    RelativeLayout rlStatus;
    RecyclerView recyclerView;
    BloodPressureAdapter mAdapter;
    ArrayList<BloodPressure> arrayList=new ArrayList<BloodPressure>();
    CardView cvCard;
    AddWHRDialog addWHRDialog;
    LinearLayout llStatus;
    Button btnStatus;
    int i;
    RelativeLayout rlGraph;
    GraphView line_graph;
    public WHRFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_whr, container, false);
        initReferences(rootView);
        initOnClick();
        getData();

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

        line_graph.getViewport().setScrollable(false);

        return rootView;

    }
    public void initReferences(View rootView) {


        llStatus=(LinearLayout)rootView.findViewById(R.id.llStatus);
        tvTrends=(TextView)rootView.findViewById(R.id.tvTrends);
        tvList=(TextView)rootView.findViewById(R.id.tvList);
        ivAdd=(ImageView)rootView.findViewById(R.id.ivAdd);
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
        addWHRDialog = new AddWHRDialog(getActivity(), new AddWHRDialog.AddWHRDialogClickListener(){

            @Override
            public void onSubmit() {
                addWHRDialog.dismiss();
            }

            @Override
            public void onCancel() {
                addWHRDialog.dismiss();
            }
        });

        addWHRDialog.show();


    }



    public void getData(){
        arrayList.add(new BloodPressure("197/23","92"));
        arrayList.add(new BloodPressure("127/53","42"));
        arrayList.add(new BloodPressure("177/63","81"));
        arrayList.add(new BloodPressure("183/12","55"));
        arrayList.add(new BloodPressure("162/33","64"));
        arrayList.add(new BloodPressure("132/33","53"));
        arrayList.add(new BloodPressure("163/33","63"));
        arrayList.add(new BloodPressure("152/63","91"));
        arrayList.add(new BloodPressure("122/23","32"));
        arrayList.add(new BloodPressure("162/73","61"));
        mAdapter=new BloodPressureAdapter(getActivity(),arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);

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



