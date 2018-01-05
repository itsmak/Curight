package com.innovellent.curight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.FoodUnit;
import com.innovellent.curight.model.Food_Units;

import java.util.ArrayList;


public class FoodUnitSpinnerAdapter extends BaseAdapter {
    Context context;
    private LayoutInflater inflater;
    private ArrayList<Food_Units> foodUnits;

    public FoodUnitSpinnerAdapter(Context context, ArrayList<Food_Units> foodUnits) {
        this.context = context;
        this.foodUnits = foodUnits;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return foodUnits != null ? foodUnits.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return foodUnits.get(i);
    }

    @Override
    public long getItemId(int i) {
        return foodUnits.get(i).getFoodid();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.spinner_string_row, null);
        ((TextView) view).setText(foodUnits.get(i).getUnit());
        return view;
    }
}
