package com.innovellent.curight.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.innovellent.curight.R;
import com.innovellent.curight.model.DrawerItem;

import java.util.List;

public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem> {

    Context context;
    List<DrawerItem> drawerItemList;
    int layoutResID;

    public CustomDrawerAdapter(Context context, int layoutResourceID, List<DrawerItem> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DrawerItemHolder drawerHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new DrawerItemHolder();
            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.ItemName = (TextView) view.findViewById(R.id.drawer_itemName);
            drawerHolder.ivIcon = (ImageView) view.findViewById(R.id.ivIcon);

            view.setTag(drawerHolder);

        } else {
            drawerHolder = (DrawerItemHolder) view.getTag();
        }

        DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);
        drawerHolder.ItemName.setText(dItem.getItemName());
        if(position==0){
            drawerHolder.ivIcon.setImageResource(R.mipmap.ic_about6);
        }else if(position==1){
            drawerHolder.ivIcon.setImageResource(R.mipmap.ic_contactus6);
        }else if(position==2){
            drawerHolder.ivIcon.setImageResource(R.mipmap.ic_feedback6);
        }else if(position==3){
            drawerHolder.ivIcon.setImageResource(R.mipmap.ic_rate5);
        }else if(position==4){
            drawerHolder.ivIcon.setImageResource(R.mipmap.ic_refer5);
        }else if(position==5){
            drawerHolder.ivIcon.setImageResource(R.mipmap.ic_logout6);
        }
        return view;
    }

    private static class DrawerItemHolder {
        TextView ItemName;
        ImageView ivIcon;
        SwitchCompat switchCompatnotifty;
    }
}
