package com.innovellent.curight.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.ViewPagerAdapter;

/**
 * Created by sagar on 10/12/2017.
 */

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager vpAutoScrollViewPager;
    private int mCurrentPosition = 1;
    int[] luckyNumbers = {R.drawable.ic_inst, R.drawable.into_1, R.drawable.into_2,R.drawable.into_3, R.drawable.intro_4,R.drawable.intro_5,R.drawable.into_3};

   // private int[] luckyNumbers = {333,111,222,333,111}; // 333,111 at the beginning and the end for circular swipe purpose
    private int lastPageIndex = luckyNumbers.length - 1;
    private Handler autoScrollHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        initViewPager();
        initAutoScroll();
    }

    private void initViewPager() {
        vpAutoScrollViewPager = (ViewPager) findViewById(R.id.vp_auto_scroll_view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), luckyNumbers);
        vpAutoScrollViewPager.setAdapter(viewPagerAdapter);
        vpAutoScrollViewPager.setCurrentItem(1);
        vpAutoScrollViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position; // Declare mCurrentPosition as a global variable to track the current position of the item in the ViewPager
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // For going from the first item to the last item, Set the current item to the item before the last item if the current position is 0
                if (mCurrentPosition == 0)
                 vpAutoScrollViewPager.setCurrentItem(lastPageIndex - 1, false); // lastPageIndex is the index of the last item, in this case is pointing to the 2nd A on the list. This variable should be declared and initialzed as a global variable

                // For going from the last item to the first item, Set the current item to the second item if the current position is on the last
                if (mCurrentPosition == lastPageIndex+1)
                  vpAutoScrollViewPager.setCurrentItem(1, false);
            }
        });

        // reset the slider when the ViewPager is scrolled manually to prevent the quick slide after it is scrolled.
        vpAutoScrollViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                  //  initAutoScroll();
                } else {

                    if (autoScrollHandler != null) {
                        autoScrollHandler.removeCallbacksAndMessages(null);
                        autoScrollHandler = null;
                    }
                }
                return false;
            }
        });

    }

    // Handler to make the view pager to scroll automatically
    private void initAutoScroll() {
        final int nCount = luckyNumbers.length;
        try {
            if (autoScrollHandler == null) autoScrollHandler = new Handler();
            autoScrollHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int curPos = vpAutoScrollViewPager.getCurrentItem();
                    curPos++;
                    if (curPos == nCount) curPos = 0;
                    vpAutoScrollViewPager.setCurrentItem(curPos, true);
                    autoScrollHandler.postDelayed(this, 3000); // 3 seconds
                }
            }, 3000);

        } catch (Exception e) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (autoScrollHandler != null) autoScrollHandler.removeCallbacksAndMessages(null);
    }
}