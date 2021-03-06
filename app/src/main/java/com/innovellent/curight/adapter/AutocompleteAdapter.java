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
 * Created by Mak on 1/18/2018.
 */

public class AutocompleteAdapter extends RecyclerView.Adapter<AutocompleteAdapter.MyViewHolder>{

    private final OnFoodClickListener listener;
   // ArrayList<Category_List> foodlist = new ArrayList<>();
    ArrayList<Category_Feed> result = new ArrayList<>();
    int position;
//    ArrayList<FoodUnit_Feed> foodUnits = new ArrayList<>();
    private Context mContext;

    public AutocompleteAdapter(Context context, ArrayList<Category_Feed> result,int position,OnFoodClickListener listener) {
        mContext = context;
        this.result = result;
//        this.foodUnits = foodUnits;
        this.position = position;
        this.listener = listener;
    }

    @Override
    public AutocompleteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_food_customadapter, parent, false);
        itemView.setClickable(true);
        //itemView.setOnClickListener(this);
        return new AutocompleteAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AutocompleteAdapter.MyViewHolder holder, final int position) {

        holder.tv_foodname.setText(result.get(position).getFoodName());
        holder.cl_fooditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onfoodnameselected(result.get(position),position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public void filterlist(ArrayList<Category_Feed> filteredlist) {
        result=filteredlist;
        notifyDataSetChanged();
    }

    public interface OnFoodClickListener {
        void onfoodnameselected(Category_Feed item_m,int position);
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
