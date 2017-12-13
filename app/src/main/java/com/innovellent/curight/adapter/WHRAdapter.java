package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.BMIRecord;
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
    ArrayList<WHR_LIST_DATE> arraylist_whr_list_dates = new ArrayList<WHR_LIST_DATE>();
    ArrayList<WHR_LIST> arraylist_whr_list = new ArrayList<WHR_LIST>();
    private WHRAdapter.OnWHRListener listener;
    WHR_LIST_DATE whr_list_date;
    WHR_LIST whr_list;
    Context mContext;


    String date,whrflag,waistcircumference,hipcircumference,whr;
    public WHRAdapter(Context context,ArrayList<WHR_LIST_DATE> arraylist_whr_list_dates, ArrayList<WHR_LIST> arraylist_whr_list,OnWHRListener listener) {
        mContext = context;
        this.arraylist_whr_list_dates = arraylist_whr_list_dates;
        this.arraylist_whr_list = arraylist_whr_list;
        this.listener = listener;
    }
    @Override
    public WHRAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_blood_pressure, parent, false);
        return new WHRAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WHRAdapter.MyViewHolder holder, final int position) {

        /*whrflag = whrlistListArrayList.get(position).getWhrFlag();
        date = whr_list_dateArrayList.get(position).getDate();
        waistcircumference = whrlistListArrayList.get(position).getWaistcircumference();
        hipcircumference = whrlistListArrayList.get(position).getHipcircumference();
        whr = whrlistListArrayList.get(position).getWhr();*/
        //Log.d("whrfinaldata===", whr);

        whr_list_date = arraylist_whr_list_dates.get(position);
        whr_list = arraylist_whr_list.get(position);
        holder.tvSysDia.setText("W/H:"+" "+arraylist_whr_list.get(position).getWaistcircumference()+"/"+arraylist_whr_list.get(position).getHipcircumference());
        holder.tvDate.setText(arraylist_whr_list_dates.get(position).getDate());
        holder.tvPulse.setText(String.valueOf(arraylist_whr_list.get(position).getWhr()));

        holder.delete_whr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDelete(arraylist_whr_list.get(position).getWhrid());


            }
        });
    }



    @Override
    public int getItemCount() {
        return arraylist_whr_list_dates.size();
    }

    public interface OnWHRListener {
        void onDelete(int whrid);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvSysDia;
        TextView tvPulse;
        TextView tvDate;
        ImageView delete_whr;
        MyViewHolder(View view) {
            super(view);
            tvSysDia = (TextView) view.findViewById(R.id.tvSysDia);
            tvPulse = (TextView) view.findViewById(R.id.tvPulse);
            tvDate=(TextView)view.findViewById(R.id.tv_date);
           // delete_whr = (ImageView)view.findViewById(R.id.delete_whr);

        }
    }
}
