package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import  android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.model.Test;

import java.util.ArrayList;



public class DiagnosticTestAdapter extends RecyclerView.Adapter<DiagnosticTestAdapter.MyViewHolder> {

    private ArrayList<String> arrayList = new ArrayList<>();
    //private ArrayList<String> testIdList = new ArrayList<>();
    private Context mContext;
    private ArrayList<Test> testObjs;
    public static String sel_test_names = "";
    public static String sel_test_ids = "";

    //private onRecyclerViewItemClickListener mItemClickListener;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvCity;
        CheckBox testCheckBox;
        CardView cardView;

        MyViewHolder(View view) {
            super(view);
            tvCity = (TextView) view.findViewById(R.id.tvCity);
            testCheckBox = (CheckBox) view.findViewById(R.id.cbDiagnosticTest);
            cardView = (CardView) view.findViewById(R.id.card_view2);
            //cardView.setOnClickListener(this);
            testCheckBox.setClickable(true);
            //testCheckBox.setOnClickListener(this);

        }

       /* @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.card_view2:
                    if (testCheckBox.isChecked()) {
                        testCheckBox.setChecked(false);
                    } else {
                        testCheckBox.setChecked(true);
                        *//*onBindViewHolder();
                        sel_test_ids = sel_test_ids +testObjs.get(position).getTestid()+",";*//*
                    }
                    break;

                case R.id.tvCity:
                    if (testCheckBox.isChecked()) {
                        testCheckBox.setChecked(false);
                    } else {
                        testCheckBox.setChecked(true);
                        //sel_test_ids = sel_test_ids +testObjs.get(position).getTestid()+",";
                    }
                    break;
            }

             }*/
    }

   public DiagnosticTestAdapter(Context context,ArrayList<String> arrayList,ArrayList<Test> testObjs) {
        mContext = context;
        this.arrayList = arrayList;
       this.testObjs = testObjs;
    }

    private void addItem(String test_name,Test test) {
        this.arrayList.add(test_name);
        this.testObjs.add(test);
        notifyDataSetChanged();
    }


    @Override
    public DiagnosticTestAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_diagnostic_test, parent, false);
        itemView.setClickable(true);
        //itemView.setOnClickListener(this);
        return new DiagnosticTestAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DiagnosticTestAdapter.MyViewHolder holder, final int position) {

        holder.tvCity.setText(arrayList.get(position));
        String testChoosenFlag = testObjs.get(position).getTestcode();
        if ("Y".equals(testChoosenFlag)) {
            holder.testCheckBox.setChecked(true);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.testCheckBox.isChecked()) {
                    holder.testCheckBox.setChecked(false);
                } else {
                    holder.testCheckBox.setChecked(true);
                }
            }
        });

        holder.tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.testCheckBox.isChecked()) {
                    holder.testCheckBox.setChecked(false);
                } else {
                    holder.testCheckBox.setChecked(true);
                    /*sel_test_ids = sel_test_ids + testObjs.get(position).getTestid() + ",";
                    sel_test_names = sel_test_names + testObjs.get(position).getTestname() + "^";*/
                }
            }
        });

        holder.testCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    if (testObjs==null) {

                    } else {
                        sel_test_ids = sel_test_ids + testObjs.get(position).getTestid() + ",";
                        sel_test_names = sel_test_names + testObjs.get(position).getTestname() + "^";
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
