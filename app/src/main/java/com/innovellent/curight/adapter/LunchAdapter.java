package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.Lunch;
import com.innovellent.curight.model.Running;

import java.util.ArrayList;

/**
 * Created by sagar on 9/8/2017.
 */

public class LunchAdapter extends RecyclerView.Adapter<LunchAdapter.MyViewHolder> {

    private ArrayList<Lunch> arrayList = new ArrayList<>();
    private Context mContext;
    private String state;
    private int rowlayout;


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTestName,tvHour,tvDistance;
        LinearLayout llLayout;

        MyViewHolder(View view) {
            super(view);
            tvTestName = (TextView) view.findViewById(R.id.tvTestName);
            tvHour = (TextView) view.findViewById(R.id.tvHour);
            tvDistance=(TextView) view.findViewById(R.id.tvDistance);
            llLayout=(LinearLayout)view.findViewById(R.id.llLayout);

        }
    }

    public LunchAdapter(Context context, int rowlayout, ArrayList<Lunch> arrayList) {
        mContext = context;
        this.arrayList = arrayList;
        this.rowlayout=rowlayout;

    }

    @Override
    public LunchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowlayout, parent, false);
        return new LunchAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LunchAdapter.MyViewHolder holder, final int position) {

        holder.tvTestName.setText(arrayList.get(position).getFoodname());
        holder.tvHour.setText(arrayList.get(position).getServing_qty());
        holder.tvDistance.setText(arrayList.get(position).getServing_unit());
        if((position%2)==0){

        }else{
            holder.llLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gray));

        }



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}

