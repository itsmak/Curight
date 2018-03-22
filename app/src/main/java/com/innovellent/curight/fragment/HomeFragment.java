package com.innovellent.curight.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.innovellent.curight.R;
import com.innovellent.curight.activities.DiagnosticCentersActivity;
import com.innovellent.curight.activities.DiagnosticTestListActivity;
import com.innovellent.curight.activities.DoctorAppointmentActivity;
import com.innovellent.curight.activities.HomeActivity;
import com.innovellent.curight.activities.SearchActivity;
import com.innovellent.curight.activities.YourReportsActivity;
import com.innovellent.curight.adapter.CustomAdapter;
import com.innovellent.curight.adapter.SearchAdapter;
import com.innovellent.curight.adapter.ViewPagerAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.Search;
import com.innovellent.curight.model.SearchingCenter;
import com.innovellent.curight.model.ServerSearchPage;
import com.innovellent.curight.utility.Config;
import com.innovellent.curight.utility.DividerItemDecoration;
import com.innovellent.curight.utility.RecyclerItemClickListener;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment  implements View.OnClickListener{
    private static final String TAG = "CuRight";
    public static EditText editMobileNo;
    RelativeLayout rl_location;
    ImageView ivHealthPackage,imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9;
    ViewPager viewPager;
    ImageView iv_home_icon,iv_remainder_icon,iv_article_icon,iv_track_icon,iv_profile_icon,ivAddprofile;
    TextView tv_home_txt,tv_remainder_txt,tv_article_txt,tv_track_txt,tv_profile_txt;
    RecyclerView recycler_view_searchhome;
    TextView tvTitle,titleOne,titleThree,tv_locationtxt,tv_locationsymbl,tv_locality;
    RelativeLayout rlBookTest,rlFood,rlHealthPackage,rlDoctorAppoinment,rlYoursReports;
    LinearLayout llSliderdotpanel;
   // int[] luckyNumbers = {R.drawable.ic_inst, R.drawable.into_1, R.drawable.into_2,R.drawable.into_3, R.drawable.intro_4,R.drawable.intro_5,R.drawable.into_3,R.drawable.ic_inst};
    int[] luckyNumbers = {R.drawable.into_1, R.drawable.into_1, R.drawable.into_3, R.drawable.into_2, R.drawable.intro_4, R.drawable.intro_5, R.drawable.intro_4};
    Search search;
    String category;
    SearchAdapter searchAdapter;
    ArrayList<String> searchArrayList = new ArrayList<String>();
    ArrayList<Search> searchingCenterObjcts= new ArrayList<Search>();
    ServerSearchPage diagCenterByTest;
    private int   mScrollState;
    private int dotscount;
    private ImageView[] dots;
    private ViewPager vpAutoScrollViewPager;
    private int mCurrentPosition = 1;
    // private int[] luckyNumbers = {333,111,222,333,111}; // 333,111 at the beginning and the end for circular swipe purpose
    private int lastPageIndex = luckyNumbers.length;
    private Handler autoScrollHandler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.home_fragment,container,false);
        init(rootView);
        initonClick();
        String source = Prefs.getString("source","");
        if(source.equalsIgnoreCase("consumption"))
        {
            ((HomeActivity)getActivity()).foodfitness();
            Fragment fragment = new FoodFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.rlMainFragment, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else if(source.equalsIgnoreCase("exersize"))
        {
            ((HomeActivity)getActivity()).foodfitness();
            Fragment fragment = new ExerciseFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.rlMainFragment, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else{
            initViewPager();
            String locationtext = Prefs.getString("location","");
            if(locationtext.equals("")){
                tv_locality.setText("Location");
            }else {
                tv_locality.setText(locationtext);
            }

            editMobileNo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    int count =0;
                    count = editable.toString().trim().length();
                    if(count>2)
                    {
                        recycler_view_searchhome.setVisibility(View.VISIBLE);
                        Search();

                    }else if(count==0){
                        recycler_view_searchhome.setVisibility(View.GONE);
                    }
                }
            });

            recycler_view_searchhome.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recycler_view_searchhome, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    //Toast.makeText(SearchActivity.this, "item clicked", Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"searched id "+searchAdapter.getsearchedid(position));
                    if(searchAdapter.getcatgory(position).equals("DR")) {
                        Intent intent = new Intent(getActivity(), DoctorAppointmentActivity.class);
                        intent.putExtra("testid",searchAdapter.getsearchedid(position));
                        startActivity(intent);
                    }else if(searchAdapter.getcatgory(position).equals("DC")){
                        Intent intent = new Intent(getActivity(), DiagnosticCentersActivity.class);
                        intent.putExtra("testid",searchAdapter.getsearchedid(position));
                        startActivity(intent);
                    }else if(searchAdapter.getcatgory(position).equals("TE")){
                        Intent intent = new Intent(getActivity(), DiagnosticTestListActivity.class);
                        intent.putExtra("testid",searchAdapter.getsearchedid(position));
                        startActivity(intent);
                    }
                }

                @Override
                public void onLongItemClick(View view, int position) {

                }
            }));
            PagerAdapter adapter = new CustomAdapter(getActivity());
            viewPager.setAdapter(adapter);
            // viewPager.setOnPageChangeListener(new CircularViewPagerHandler(viewPager));
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    mCurrentPosition = position;

                    if((position%6)==0){
                        // btnSkip.setVisibility(View.VISIBLE);
                        setbackgrounImage();
                        imageView1.setImageResource(R.drawable.circular_blue);

                    }else if((position%6) == 1){
                        //  btnSkip.setVisibility(View.VISIBLE);
                        setbackgrounImage();
                        imageView2.setImageResource(R.drawable.circular_blue);
                    }else if((position%6) == 2){
                        // btnSkip.setVisibility(View.VISIBLE);
                        setbackgrounImage();
                        imageView3.setImageResource(R.drawable.circular_blue);
                    }else if((position%6) == 3){
                        setbackgrounImage();
                        imageView4.setImageResource(R.drawable.circular_blue);
                    }else if((position%6) == 4){
                        // btnSkip.setVisibility(View.VISIBLE);
                        setbackgrounImage();
                        imageView5.setImageResource(R.drawable.circular_blue);
                    }else if((position%6) == 5){
                        setbackgrounImage();
                        imageView6.setImageResource(R.drawable.circular_blue);

                    }
                    if (position == 0) {
                        viewPager.setCurrentItem(lastPageIndex - 1, false);
                    }
                    if (mCurrentPosition == lastPageIndex) {
                        viewPager.setCurrentItem(1, false);
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
                        //  handleSetNextItem();
                    }
                }

                private boolean isScrollStateSettling() {
                    return mScrollState == ViewPager.SCROLL_STATE_SETTLING;
                }

                @Override
                public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
                }

            });
        }
        tvTitle.setVisibility(View.GONE);
        ivAddprofile.setVisibility(View.GONE);
        rl_location .setVisibility(View.VISIBLE);
        iv_home_icon.setImageResource(R.drawable.home_blue);
        iv_remainder_icon.setImageResource(R.drawable.reminder_grey);
        iv_article_icon.setImageResource(R.drawable.article_grey);
        iv_track_icon.setImageResource(R.drawable.track_grey);
        iv_profile_icon.setImageResource(R.drawable.profile_grey);

        tv_home_txt.setTextColor(Color.parseColor("#0B63F8"));
        tv_remainder_txt.setTextColor(Color.parseColor("#54666E"));
        tv_article_txt.setTextColor(Color.parseColor("#54666E"));
        tv_track_txt.setTextColor(Color.parseColor("#54666E"));
        tv_profile_txt.setTextColor(Color.parseColor("#54666E"));
        return rootView;

    }

    //search functionality
    private void Search(){
        searchAdapter = new SearchAdapter(getActivity(), searchArrayList, searchingCenterObjcts, category);
        searchArrayList.clear();
        searchingCenterObjcts.clear();
        searchAdapter.notifyDataSetChanged();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);


        final SearchingCenter centre = new SearchingCenter(HomeFragment.editMobileNo.getText().toString().trim());

        Call<ServerSearchPage> call = apiInterface.getSearch(centre);

        call.enqueue(new Callback<ServerSearchPage>() {
            @Override
            public void onResponse(Call<ServerSearchPage> call, Response<ServerSearchPage> response) {
                if (response.body() != null) {
                    diagCenterByTest = (ServerSearchPage) response.body();
                    String code = String.valueOf(diagCenterByTest.getCode());
                    Log.d(TAG, "search code::" + code);
                    if ("200".equals(code)) {

                        for (int j = 0; j < diagCenterByTest.getSearchResults().size(); j++) {
                            search = diagCenterByTest.getSearchResults().get(j);
                            Log.d("searchdata===", "" + search.getCategory());
                            searchingCenterObjcts.add(search);
                            try {
                                category = search.getCategory();
                                Log.d("categorydata===", category);
                                String name = search.getName();
                                Log.d("NAME==", name);
                                searchArrayList.add(name);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        if (searchArrayList.size() != 0) {
                            searchAdapter = new SearchAdapter(getActivity(), searchArrayList, searchingCenterObjcts, category);
                            recycler_view_searchhome.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler_view_searchhome.getContext());
                            recycler_view_searchhome.addItemDecoration(dividerItemDecoration);
                            recycler_view_searchhome.setAdapter(searchAdapter);
                        }
                    } else if ("403".equals(code)) {
                        Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerSearchPage> call, Throwable t) {
                Log.e("ERROR==", t.getMessage());
            }
        });
    }

    public void initonClick(){

        rlBookTest.setOnClickListener(this);
        rlFood.setOnClickListener(this);
        rlHealthPackage.setOnClickListener(this);
        imageView1.setImageResource(R.drawable.circular_blue);
        ivHealthPackage.setOnClickListener(this);
        rlDoctorAppoinment.setOnClickListener(this);
        rlYoursReports.setOnClickListener(this);
    }

    public void init(View rootView){
        rl_location = (RelativeLayout) getActivity().findViewById(R.id.rl_location);
        ivAddprofile = (ImageView) getActivity().findViewById(R.id.ivAddprofile);
        iv_home_icon = (ImageView) getActivity().findViewById(R.id.iv_home_icon);
        iv_remainder_icon = (ImageView) getActivity().findViewById(R.id.iv_remainder_icon);
        iv_article_icon = (ImageView) getActivity().findViewById(R.id.iv_article_icon);
        iv_track_icon = (ImageView) getActivity().findViewById(R.id.iv_track_icon);
        iv_profile_icon = (ImageView) getActivity().findViewById(R.id.iv_profile_icon);
        tv_home_txt = (TextView) getActivity().findViewById(R.id.tv_home_txt);
        tv_remainder_txt = (TextView) getActivity().findViewById(R.id.tv_remainder_txt);
        tv_article_txt = (TextView) getActivity().findViewById(R.id.tv_article_txt);
        tv_track_txt = (TextView) getActivity().findViewById(R.id.tv_track_txt);
        tv_profile_txt = (TextView) getActivity().findViewById(R.id.tv_profile_txt);

        tv_locality  = (TextView) getActivity().findViewById(R.id.tv_locality);
        titleOne=(TextView)rootView.findViewById(R.id.titleOne);
    tvTitle = (TextView) getActivity().findViewById(R.id.tvTitle);
    editMobileNo = (EditText)rootView.findViewById(R.id.editMobileNo);
    titleThree = (TextView)rootView.findViewById(R.id.titleThree);
    viewPager = (ViewPager)rootView.findViewById(R.id.viewPager);
    imageView1=(ImageView)rootView.findViewById(R.id.iv_image1);
    imageView2=(ImageView)rootView.findViewById(R.id.iv_image2);
    imageView3=(ImageView)rootView.findViewById(R.id.iv_image3);
    imageView4=(ImageView)rootView.findViewById(R.id.iv_image4);
    imageView5=(ImageView)rootView.findViewById(R.id.iv_image5);
    imageView6=(ImageView)rootView.findViewById(R.id.iv_image6);
    rlBookTest=(RelativeLayout)rootView.findViewById(R.id.rlBookTest);
    recycler_view_searchhome = (RecyclerView) rootView.findViewById(R.id.recycler_view_searchhome);
    rlFood=(RelativeLayout)rootView.findViewById(R.id.rlFood);
    rlDoctorAppoinment = (RelativeLayout)rootView.findViewById(R.id.rlDoctorAppointment);
    rlHealthPackage=(RelativeLayout)rootView.findViewById(R.id.rlHealthPackage);
    ivHealthPackage=(ImageView)rootView.findViewById(R.id.ivHealthPackage);
    rlYoursReports= (RelativeLayout)rootView.findViewById(R.id.rlYoursReports);
    tv_locationtxt = (TextView) getActivity().findViewById(R.id.tv_locationtxt);
    tv_locationsymbl = (TextView) getActivity().findViewById(R.id.tv_locationsymbl);

    tv_locationtxt.setVisibility(View.VISIBLE);
    tv_locationsymbl.setVisibility(View.GONE);
  // llSliderdotpanel=(LinearLayout)rootView.findViewById(R.id.llSliderDots);


    editMobileNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Intent i = new Intent(getActivity(), SearchActivity.class);
                startActivity(i);
                return true;
            }
            return false;
        }
    });
}


    public void setbackgrounImage(){
        imageView1.setImageResource(R.drawable.circulargray2);
        imageView2.setImageResource(R.drawable.circulargray2);
        imageView3.setImageResource(R.drawable.circulargray2);
        imageView4.setImageResource(R.drawable.circulargray2);
        imageView5.setImageResource(R.drawable.circulargray2);
        imageView6.setImageResource(R.drawable.circulargray2);

    }


    private void initViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), luckyNumbers);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                    viewPager.setCurrentItem(lastPageIndex - 1, false); // lastPageIndex is the index of the last item, in this case is pointing to the 2nd A on the list. This variable should be declared and initialzed as a global variable

                // For going from the last item to the first item, Set the current item to the second item if the current position is on the last
                if (mCurrentPosition == lastPageIndex)
                    viewPager.setCurrentItem(0, false);
            }
        });

        // reset the slider when the ViewPager is scrolled manually to prevent the quick slide after it is scrolled.


      /*  dotscount=viewPagerAdapter.getCount();
        dots=new ImageView[dotscount];
        for(int i=0;i<dotscount;i++){
            dots[i]=new ImageView(getActivity());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.circulargray2));
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);
            llSliderdotpanel.addView(dots[1],params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.circular_blue));
         viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

             @Override
             public void onPageSelected(int position) {
                 mCurrentPosition = position;

               for(int i=0;i<dotscount;i++){
                   dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.circulargray));
               }
                 dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.circular_blue));

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
         */
    }

/*public void timedatepicker(){
    new SingleDateAndTimePickerDialog.Builder(getActivity())
            // .bottomSheet()
            // .curved() .title("Select Date")
            .titleTextColor(getResources().getColor(R.color.cardview_dark_background))
            .minutesStep(1)
            .backgroundColor(getResources().getColor(R.color.common_signin_btn_dark_text_focused))
            .mainColor(getResources().getColor(R.color.colorAccent))
            .listener(new SingleDateAndTimePickerDialog.Listener()
            {
                @Override public void onDateSelected(Date date)
                {
                    // selectpackages();
                    String DATE_FORMAT_NOW = "dd-MM-yyyy HH:mm:ss";
                    // String DATE_FORMAT_NOW1 = "yyyy-MM-dd HH:mm:ss";

                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
                    //  SimpleDateFormat sdf1 = new SimpleDateFormat(DATE_FORMAT_NOW1);

                  String  stringDate = sdf.format(date);


                    // Toast.makeText(getActivity(),""+stringDate,Toast.LENGTH_SHORT).show();
                }
            }).display();
}*/
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlFood:
                ((HomeActivity)getActivity()).foodfitness();
                Fragment fragment = new TrackDataFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rlMainFragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.titleOne:
                Intent i=new Intent(getActivity(),DiagnosticTestListActivity.class);
                startActivity(i);
                break;


            case R.id.rlBookTest:
               Intent i1=new Intent(getActivity(),DiagnosticTestListActivity.class);
               startActivity(i1);
                break;

            case R.id.rlDoctorAppointment:
                Intent i_doctorappointment = new Intent(getActivity(),DoctorAppointmentActivity.class);
                startActivity(i_doctorappointment);
                break;
            case R.id.rlYoursReports:
                Intent i_yourreports = new Intent(getActivity(), YourReportsActivity.class);
                startActivity(i_yourreports);
                break;

            }

        }

    }

