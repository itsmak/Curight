package com.innovellent.curight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.FamilyProfile;

import java.util.List;

public class ProfileSpinnerAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private List<FamilyProfile> profiles;

    public ProfileSpinnerAdapter(Context context, List<FamilyProfile> profiles) {
        inflater = LayoutInflater.from(context);
        this.profiles = profiles;
    }

    @Override
    public int getCount() {
        return profiles != null ? profiles.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return profiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return profiles.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.spinner_string_row, null);

        String name = profiles.get(position).getName();
        String lastName = "";
        String firstName= "";
        if(name.split("\\w+").length>1){

            //lastName = name.substring(name.lastIndexOf(" ")+1);
            firstName = name.substring(0, name.lastIndexOf(' '));
        }
        else{
            firstName = name;
        }

        ((TextView) view).setText(String.valueOf(firstName));
        return view;
    }
}
