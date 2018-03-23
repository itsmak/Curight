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
import com.innovellent.curight.model.FOOD_ITEM;

import java.util.ArrayList;

/**
 * Created by Mak on 3/22/2018.
 */

public class FoodConsumptionAdapter extends RecyclerView.Adapter<FoodConsumptionAdapter.MyViewHolder>{

    ArrayList<FOOD_ITEM> food_details = new ArrayList<FOOD_ITEM>();
    private Context mContext;
    private int rowlayout;

    public FoodConsumptionAdapter(Context context,int  rowlayout,ArrayList<FOOD_ITEM> food_details) {
        mContext = context;
        this.food_details = food_details;
        this.rowlayout=rowlayout;

    }

    @Override
    public FoodConsumptionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowlayout, parent, false);
        return new FoodConsumptionAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FoodConsumptionAdapter.MyViewHolder holder, final int position) {

        holder.tvTestName.setText(food_details.get(position).getFoodname());
        holder.tvHour.setText(food_details.get(position).getFoodquantity());
        holder.tvDistance.setText(food_details.get(position).getFoodunit());
        holder.tvUnit.setText(food_details.get(position).getFoodcaloryunit());
        if((position%2)==0){

        }else{
            holder.llLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gray));
        }

    }

    @Override
    public int getItemCount() {
        return food_details.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTestName,tvHour,tvDistance,tvUnit;
        LinearLayout llLayout;

        MyViewHolder(View view) {
            super(view);
            tvTestName = (TextView) view.findViewById(R.id.tvTestName);
            tvHour = (TextView) view.findViewById(R.id.tvHour);
            tvDistance=(TextView) view.findViewById(R.id.tvDistance);
            tvUnit=(TextView) view.findViewById(R.id.tvUnit);
            llLayout=(LinearLayout)view.findViewById(R.id.llLayout);
        }
    }

}
