package com.innovellent.curight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.FoodItem;
import com.innovellent.curight.model.FoodItem_Feed;

import java.util.ArrayList;
import java.util.List;

public class FoodItemSpinnerAdapter extends BaseAdapter {

    private ArrayList<FoodItem_Feed> items;
    private LayoutInflater inflater;

    public FoodItemSpinnerAdapter(Context context, ArrayList<FoodItem_Feed> items) {
        this.items = items;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.spinner_string_row, null);
        ((TextView) view).setText(String.valueOf(items.get(position).getFoodName()));
        return view;
    }
}
