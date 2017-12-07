package com.innovellent.curight.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.activities.AddTestActivity;
import com.innovellent.curight.activities.SummaryDetailsActivity;
import com.innovellent.curight.model.Center;
import com.innovellent.curight.model.TestDetail;

import java.util.ArrayList;

/**
 * Created by sagar on 9/5/2017.
 */

public class DiagnosticCenterAdapter extends RecyclerView.Adapter<DiagnosticCenterAdapter.MyViewHolder> {

    private ArrayList<Center> arrayList = new ArrayList<Center>();
    private Context mContext;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvCenterName;
        Button btnBookTest;
        RadioButton rbSpec;
        TextView startTime;
        RadioButton rbTag;
        TextView endTime;
        TextView location;
        TextView testCount;
        Button btnOverview;
        Button btnPhotos;

        MyViewHolder(View view) {
            super(view);
            tvCenterName = (TextView) view.findViewById(R.id.tvCenterName);
            btnBookTest=(Button)view.findViewById(R.id.btnBookTest);
            rbSpec = (RadioButton) view.findViewById(R.id.rbSpec);
            startTime = (TextView) view.findViewById(R.id.tvDateTime);
            rbTag = (RadioButton) view.findViewById(R.id.rbFullTime);
            endTime = (TextView) view.findViewById(R.id.tvFullTimeDateTime);
            location = (TextView) view.findViewById(R.id.tvLocation);
            testCount = (TextView) view.findViewById(R.id.tvTestCount);
            btnOverview = (Button) view.findViewById(R.id.btnOverview);
            btnPhotos = (Button) view.findViewById(R.id.btnPhotos);

        }
    }

    public DiagnosticCenterAdapter(Context context,ArrayList<Center> arrayList) {
        mContext = context;
        this.arrayList = arrayList;

    }

    @Override
    public DiagnosticCenterAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_diagnostic_center, parent, false);
        return new DiagnosticCenterAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DiagnosticCenterAdapter.MyViewHolder holder, final int position) {

        holder.tvCenterName.setText(arrayList.get(position).getDiagnosticcentrename());
        holder.rbSpec.setText(arrayList.get(position).getSpecializationname());
        holder.startTime.setText(arrayList.get(position).getNormalworkingschedule());
        holder.rbTag.setText(arrayList.get(position).getTagline());
        holder.endTime.setText(arrayList.get(position).getWeekendworkingschedule());
        holder.location.setText(arrayList.get(position).getAddress()+","+arrayList.get(position).getCity());

        int tests_count = 0;
        ArrayList<TestDetail> _test = arrayList.get(holder.getAdapterPosition()).getTestDetail();
        for (int j=0;j<_test.size();j++) {
            if ("Y".equals(_test.get(j).getTestchoosen())) {
                tests_count++;
            }
        }

        holder.testCount.setText(tests_count+" Out of "+arrayList.get(position).getCount()+" tests available");

        holder.btnOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("OverView","center :: "+arrayList.get(position).getDiagnosticcentreid());
            }
        });

        holder.btnPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.btnBookTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(mContext,SummaryDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("dc_id",arrayList.get(position).getDiagnosticcentreid());
                bundle.putString("dc_name",arrayList.get(position).getDiagnosticcentrename());
                bundle.putString("location",arrayList.get(position).getAddress());

                Log.e("TAG","Size is ::  "+arrayList.get(position).getTestDetail().size());


                ArrayList<TestDetail> testObj = arrayList.get(position).getTestDetail();
                String test_amounts = "";
                for (int j=0;j<testObj.size();j++) {
                    if ("Y".equals(testObj.get(j).getTestchoosen())) {
                        test_amounts = test_amounts + testObj.get(j).getAmount() + ",";
                    }
                }
                //Log.e("AMOUNTS","Val :: "+test_amounts);

                String test_amnt_str = test_amounts;
                if (test_amnt_str.endsWith(",")) {
                    test_amnt_str = test_amnt_str.substring(0,test_amnt_str.length()-1);
                }

                //Log.e("AMOUNTS","amnt_str :: "+test_amnt_str);

                String sel_test_names = DiagnosticTestAdapter.sel_test_names;
                if (sel_test_names.endsWith("^")) {
                    sel_test_names = sel_test_names.substring(0,sel_test_names.length()-1);
                }
                String sel_test_ids = DiagnosticTestAdapter.sel_test_ids;
                if (sel_test_ids.endsWith("^")) {
                    sel_test_ids = sel_test_ids.substring(0,sel_test_ids.length()-1);
                }
                bundle.putString("sel_test_ids",sel_test_ids);
                bundle.putString("test_names",sel_test_names);
                bundle.putString("test_amounts",test_amnt_str);
                i.putExtras(bundle);
                mContext.startActivity(i);
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}

