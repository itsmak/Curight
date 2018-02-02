package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.Category_Feed;
import com.innovellent.curight.model.FoodUnit_Feed;

import java.util.ArrayList;

/**
 * Created by Mak on 1/29/2018.
 */

public class FoodunitAdapter extends RecyclerView.Adapter<FoodunitAdapter.MyViewHolder>{

    private final OnFoodunitClickListener listener;
    ArrayList<FoodUnit_Feed> foodUnits = new ArrayList<>();
    int position;
    private Context mContext;

    public FoodunitAdapter(Context mContext,ArrayList<FoodUnit_Feed> foodUnits, int position,OnFoodunitClickListener listener) {
        this.listener = listener;
        this.foodUnits = foodUnits;
        this.mContext = mContext;
        this.position = position;
    }

    @Override
    public FoodunitAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_food_customadapter, parent, false);
        itemView.setClickable(true);
        return new FoodunitAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FoodunitAdapter.MyViewHolder holder, final int position) {

        holder.tv_foodname.setText(foodUnits.get(position).getUnit());
        holder.cl_fooditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onfoodunitselect(foodUnits.get(position),position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodUnits.size();
    }
    public interface OnFoodunitClickListener {
        void onfoodunitselect(FoodUnit_Feed item_f, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_foodname;
        ConstraintLayout cl_fooditem;
        MyViewHolder(View view) {
            super(view);
            tv_foodname = (TextView) view.findViewById(R.id.textView_food);
            cl_fooditem = (ConstraintLayout) view.findViewById(R.id.cl_fooditem);
        }
    }
}
