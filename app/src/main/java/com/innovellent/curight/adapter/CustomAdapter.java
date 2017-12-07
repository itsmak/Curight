package com.innovellent.curight.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.innovellent.curight.R;


public class CustomAdapter extends PagerAdapter {
  // Context context;

  /*  int[] imageId = {R.drawable.intro_5,R.drawable.into_1, R.drawable.into_2, R.drawable.into_3,R.drawable.intro_4,R.drawable.into_3,R.drawable.intro_5, R.drawable.intro_5};
    public CustomAdapter(Context context){
        this.context = context;

   }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View viewItem = inflater.inflate(R.layout.image_item, container, false);
        try {
            ImageView imageView = (ImageView) viewItem.findViewById(R.id.imageView);
            imageView.setImageResource(imageId[position]);
        }catch (Exception e){

        }
        ((ViewPager)container).addView(viewItem);


        return viewItem;
    }

    @Override
    public int getCount() {
        return imageId.length+1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == ((View)object);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
*/
  Context context;

    int[] imageId = {R.drawable.ic_inst, R.drawable.into_1, R.drawable.into_2,R.drawable.into_3, R.drawable.intro_4,R.drawable.intro_5,R.drawable.into_3};
    public CustomAdapter(Context context){
        this.context = context;


    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        LayoutInflater inflater = ((Activity)context).getLayoutInflater();

        View viewItem = inflater.inflate(R.layout.image_item, container, false);
        try {
            ImageView imageView = (ImageView) viewItem.findViewById(R.id.imageView);
            imageView.setImageResource(imageId[position]);
        }catch (Exception e){

        }
        ((ViewPager)container).addView(viewItem);


        return viewItem;
    }

    @Override
    public int getCount() {
        return imageId.length+1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == ((View)object);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

}
