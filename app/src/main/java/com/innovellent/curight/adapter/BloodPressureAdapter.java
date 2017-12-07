package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.BloodPressure;


import java.util.ArrayList;

public class BloodPressureAdapter extends RecyclerView.Adapter<BloodPressureAdapter.MyViewHolder> {

    private ArrayList<BloodPressure> arrayList = new ArrayList<>();
    private Context mContext;
    private String state;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvSysDia;
        TextView tvPulse;
        TextView tvDate;

        MyViewHolder(View view) {
            super(view);
            tvSysDia = (TextView) view.findViewById(R.id.tvSysDia);
            tvPulse = (TextView) view.findViewById(R.id.tvPulse);
            tvDate=(TextView)view.findViewById(R.id.tv_date);

        }
    }

    public BloodPressureAdapter(Context context,ArrayList<BloodPressure> arrayList) {
        mContext = context;
        this.arrayList = arrayList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_blood_pressure, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tvSysDia.setText(arrayList.get(position).getSystolic());
        holder.tvPulse.setText(arrayList.get(position).getPulse());
        if(position%5==0){
            holder.tvDate.setVisibility(View.VISIBLE);
        }else{
            holder.tvDate.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
