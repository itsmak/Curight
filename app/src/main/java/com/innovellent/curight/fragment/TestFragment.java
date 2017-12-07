package com.innovellent.curight.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.CustomAdapter;


public class TestFragment  extends Fragment {
        ImageView ivImage1,ivImage2,imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9;
        ViewPager viewPager;
        private int   mCurrentPosition;
        private int   mScrollState;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                View rootView=inflater.inflate(R.layout.fragment_home1,container,false);
                viewPager = (ViewPager)rootView.findViewById(R.id.viewPager);
                imageView1=(ImageView)rootView.findViewById(R.id.iv_image1);
                imageView2=(ImageView)rootView.findViewById(R.id.iv_image2);

                // imageView1.setImageResource(R.drawable.circularorange);
                PagerAdapter adapter = new CustomAdapter(getActivity());
                viewPager.setAdapter(adapter);
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                        @Override
                        public void onPageSelected(int position) {
                                mCurrentPosition = position;
                                if(position == 0){
                                        setbackgrounImage();
                                        imageView1.setImageResource(R.drawable.intro_5);
                                        imageView2.setImageResource(R.drawable.apple);

                                }else if(position == 1){
                                        setbackgrounImage();
                                        imageView1.setImageResource(R.drawable.caps);
                                        imageView2.setImageResource(R.drawable.appoint);
                                }else if(position == 2){
                                        setbackgrounImage();
                                        imageView1.setImageResource(R.drawable.apple);
                                        imageView2.setImageResource(R.drawable.feedback);
                                }else if(position == 3){
                                        setbackgrounImage();
                                        imageView1.setImageResource(R.drawable.appoint);
                                        imageView2.setImageResource(R.drawable.intro_5);
                                }else if(position == 4){
                                        setbackgrounImage();
                                        imageView1.setImageResource(R.drawable.intro_5);
                                        imageView2.setImageResource(R.drawable.apple);
                                }else if(position == 5){

                                }


                        }

                        @Override
                        public void onPageScrollStateChanged(final int state) {
                                handleScrollState(state);
                                mScrollState = state;
                        }

                        private void handleScrollState(final int state) {
                                if (state == ViewPager.SCROLL_STATE_IDLE) {
                                        setNextItemIfNeeded();
                                }
                        }

                        private void setNextItemIfNeeded() {
                                if (!isScrollStateSettling()) {
                                        handleSetNextItem();
                                }
                        }

                        private boolean isScrollStateSettling() {
                                return mScrollState == ViewPager.SCROLL_STATE_SETTLING;
                        }

                        private void handleSetNextItem() {
                                final int lastPosition = viewPager.getAdapter().getCount() - 1;
                                if(mCurrentPosition == 0) {
                                        viewPager.setCurrentItem(lastPosition, false);
                                } else if(mCurrentPosition == lastPosition) {
                                        viewPager.setCurrentItem(0, false);
                                }
                        }

                        @Override
                        public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
                        }


                });


                return rootView;
        }
        public void setbackgrounImage(){
                imageView1.setImageResource(R.drawable.circulargray2);
                imageView2.setImageResource(R.drawable.circulargray2);



        }
}
