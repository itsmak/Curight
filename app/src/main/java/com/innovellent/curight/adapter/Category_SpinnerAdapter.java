package com.innovellent.curight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.Category_List;

import java.util.ArrayList;

/**
 * Created by Mak on 1/3/2018.
 */

public class Category_SpinnerAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflter;

    private ArrayList<Category_List> categoryspinner;

    public Category_SpinnerAdapter(Context context, ArrayList<Category_List> categoryspinner) {
        this.context = context;
        this.categoryspinner = categoryspinner;
        inflter = (LayoutInflater.from(context));
    }


    @Override
    public int getCount() {
        return categoryspinner.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryspinner.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflter.inflate(R.layout.category_spinner, null);
        TextView names = (TextView) view.findViewById(R.id.tv_categoryname);
        names.setText(categoryspinner.get(i).getFoodName());
        return view;
    }
}
