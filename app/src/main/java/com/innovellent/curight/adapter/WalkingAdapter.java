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
import com.innovellent.curight.model.Walking;

import java.util.ArrayList;

/**
 * Created by sagar on 9/8/2017.
 */

public class WalkingAdapter  extends RecyclerView.Adapter<WalkingAdapter.MyViewHolder> {

    private ArrayList<Walking> arrayList = new ArrayList<>();
    private Context mContext;
    private String state;
    private int rowLayout;


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTestName,tvHour,tvDistance,tvUnit;
        LinearLayout llLayout;


        MyViewHolder(View view) {
            super(view);
            tvTestName = (TextView) view.findViewById(R.id.tvTestName);
            tvDistance=(TextView) view.findViewById(R.id.tvDistance);
            tvUnit=(TextView) view.findViewById(R.id.tvUnit);
            tvHour=(TextView)view.findViewById(R.id.tvHour);
            llLayout=(LinearLayout)view.findViewById(R.id.llLayout);
        }
    }

    public WalkingAdapter(Context context,int rowLayout,ArrayList<Walking> arrayList) {
        mContext = context;
        this.arrayList = arrayList;
        this.rowLayout=rowLayout;

    }

    @Override
    public WalkingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final WalkingAdapter.MyViewHolder holder, final int position) {

        holder.tvTestName.setText(arrayList.get(position).getExercisetype()+"");
        holder.tvHour.setText(arrayList.get(position).getExercisetime()+"");
        holder.tvDistance.setText(arrayList.get(position).getDistancecovered()+"");
        if((position%2)==0){
            holder.tvUnit.setText("53.2");
        }else{
            holder.llLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gray));
            holder.tvUnit.setText("23.2");
        }



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}

