package com.innovellent.curight.fragment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovellent.curight.R;


/**
 * Created by mancha on 8/16/2017.
 */

class CustomList extends ArrayAdapter<String> {

    private Activity context;
    private String[] web;
    private Integer[] imageId;

    public CustomList(Activity context, String[] web, Integer[] imageId) {
        super(context, R.layout.account_menu_layout, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;


    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.account_menu_layout, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.tvCity);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.list_image);
        txtTitle.setText(web[position]);
        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
