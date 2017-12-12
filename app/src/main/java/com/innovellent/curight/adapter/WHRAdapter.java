package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.BloodPressure;
import com.innovellent.curight.model.DoctorList;
import com.innovellent.curight.model.WHR_LIST;
import com.innovellent.curight.model.WHR_LIST_DATE;
import com.innovellent.curight.model.WhrList;

import java.util.ArrayList;

/**
 * Created by SUNIL on 12/11/2017.
 */

public class WHRAdapter extends RecyclerView.Adapter<WHRAdapter.MyViewHolder> {

    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<WHR_LIST> whrlistListArrayList;
    private ArrayList<WHR_LIST_DATE> whr_list_dateArrayList;
    Context mContext;


    String date,whrflag,waistcircumference,hipcircumference,whr;
    public WHRAdapter(Context context,ArrayList<WHR_LIST_DATE> whr_list_dateArrayList, ArrayList<WHR_LIST> whrlistListArrayList) {
        mContext = context;
        this.whrlistListArrayList = whrlistListArrayList;
        this.whr_list_dateArrayList = whr_list_dateArrayList;
    }
    @Override
    public WHRAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_blood_pressure, parent, false);
        return new WHRAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WHRAdapter.MyViewHolder holder, int position) {

        whrflag = whrlistListArrayList.get(position).getWhrFlag();
        date = whr_list_dateArrayList.get(position).getDate();
        waistcircumference = whrlistListArrayList.get(position).getWaistcircumference();
        hipcircumference = whrlistListArrayList.get(position).getHipcircumference();
        whr = whrlistListArrayList.get(position).getWhr();
        //Log.d("whrfinaldata===", whr);


        holder.tvSysDia.setText(waistcircumference+"/"+hipcircumference);
        holder.tvDate.setText(date);
        holder.tvPulse.setText(whr);
    }


    @Override
    public int getItemCount() {
        return whrlistListArrayList.size();
    }


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
}
