package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.BloodCount;

import java.util.ArrayList;

/**
 * Created by sagar on 10/16/2017.
 */

public class FemaleCycleAdapter extends RecyclerView.Adapter<FemaleCycleAdapter.MyViewHolder> {

    private ArrayList<BloodCount> arrayList = new ArrayList<>();
    private Context mContext;
    private String state;

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvAntiCPP,tvCRP,tvESR,tvHaemoglobin,tvHbA1c,tvINR,tvPlatelet,tvProlactin,tvRBC,tvRF,tvWBC;

        MyViewHolder(View view) {
            super(view);
         //   tvAntiCPP=(TextView) view.findViewById(R.id.tvAntiCPP);

        }
    }

    public FemaleCycleAdapter(Context context,ArrayList<BloodCount> arrayList) {
        mContext = context;
        this.arrayList = arrayList;

    }

    @Override
    public FemaleCycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_female_cycle, parent, false);
        return new FemaleCycleAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FemaleCycleAdapter.MyViewHolder holder, final int position) {
       // holder.tvAntiCPP.setText(arrayList.get(position).getAntiCPP());


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
