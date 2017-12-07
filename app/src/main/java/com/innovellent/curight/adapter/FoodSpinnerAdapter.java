package com.innovellent.curight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.innovellent.curight.R;

import java.util.ArrayList;


public class FoodSpinnerAdapter extends BaseAdapter {
    Context context;
    private ArrayList<String> foodSpinner;
    LayoutInflater inflter;
    public FoodSpinnerAdapter(Context context, ArrayList<String> foodSpinner) {
        this.context = context;
        this.foodSpinner = foodSpinner;
        inflter = (LayoutInflater.from(context));

    }

    /*@Override
    public boolean isEnabled(int position){
        if(position == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }*/

    @Override
    public int getCount() {
        return foodSpinner.size();
    }

    @Override
    public Object getItem(int i) {
        return foodSpinner.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflter.inflate(R.layout.custom_spinner_item1,null);
        TextView names = (TextView) view.findViewById(R.id.textview);
        names.setText(foodSpinner.get(i));
        return view;
    }
}
