package com.innovellent.curight.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.innovellent.curight.R;
import com.innovellent.curight.model.Vaccine;
import com.innovellent.curight.model.VaccineReminderYearDialog;

import java.util.ArrayList;

/**
 * Created by sagar on 9/13/2017.
 */

public class VaccineAdapter2 extends RecyclerView.Adapter<VaccineAdapter2.MyViewHolder> {

    private ArrayList<Vaccine> arrayList = new ArrayList<>();
    private Context mContext;
    private String state;
    VaccineReminderYearDialog vaccineReminderYearDialog;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvVaccine;
        TextView tvDate;
        ImageView ivTaken;
        LinearLayout llVaccince;
        MyViewHolder(View view) {
            super(view);
            tvVaccine = (TextView) view.findViewById(R.id.tvVaccine);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
            llVaccince=(LinearLayout)view.findViewById(R.id.llVaccince);
            ivTaken=(ImageView)view.findViewById(R.id.ivTaken);
        }
    }

    public VaccineAdapter2(Context context,ArrayList<Vaccine> arrayList) {
        mContext = context;
        this.arrayList = arrayList;

    }

    @Override
    public VaccineAdapter2.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_vaccine, parent, false);
        return new VaccineAdapter2.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VaccineAdapter2.MyViewHolder holder, final int position) {

        holder.tvVaccine.setText(arrayList.get(position).getVaccinename());
        holder.tvDate.setText(arrayList.get(position).getDate());
        if(position%2==0){
            holder.llVaccince.setBackgroundColor(Color.parseColor("#D9DADD"));

        }else{
        }
        holder.llVaccince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                addVaccine(arrayList.get(position).getDate(),arrayList.get(position).getDate(),arrayList.get(position).getDate(),
//                        arrayList.get(position).getDate(),arrayList.get(position).getDate(),1,1,1,"");
            }
        });
        holder.ivTaken.setImageResource(R.mipmap.ic_cancel);



    }
//    private void addVaccine(String a,String b,String c,String d,String e,int f,int g,int h,String i) {
//        vaccineReminderYearDialog = new VaccineReminderYearDialog(mContext,a,b,c,d,f,g,h,i, new VaccineReminderYearDialog.VaccineReminderYearDialogClickListener(){
//
//
//            @Override
//            public void onSubmit() {
//                vaccineReminderYearDialog.dismiss();
//            }
//        });
//
//        vaccineReminderYearDialog.show();
//
//
//    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}


